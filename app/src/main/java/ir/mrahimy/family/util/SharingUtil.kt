package ir.mrahimy.family.util

import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Observer
import ir.mrahimy.family.base.BaseViewModel
import ir.mrahimy.family.util.ktx.getAnnotation

@Retention(AnnotationRetention.RUNTIME)
@Target(AnnotationTarget.FIELD)
annotation class Shared(val name: String)

interface SharedAnnotation {
    fun share(lifecycleOwner: LifecycleOwner, vm: BaseViewModel, svm: BaseViewModel?)
    fun remove()
}

class SharedAnnotationDelegate : SharedAnnotation {

    private val removableObservers = mutableMapOf<MutableLiveData<Any>, Observer<Any>>()
    private val removableSharedObservers = mutableMapOf<MutableLiveData<Any>, Observer<Any>>()

    /**
     * posts every field annotated by @Shared() from vm to svm and vise versa,
     * Utilizing java reflection.
     */
    @Suppress("UNCHECKED_CAST")
    override fun share(lifecycleOwner: LifecycleOwner, vm: BaseViewModel, svm: BaseViewModel?) {
        //fixme: variables should be renamed. the staircase should be reduced
        vm.javaClass.declaredFields.forEach viewModelForEach@{ vf ->
            if (vf.isAnnotationPresent(Shared::class.java)) {
                val vAnnotation = vf.getAnnotation<Shared>() ?: return@viewModelForEach
                val vName = vAnnotation.name
                if (vf.type.isAssignableFrom(MutableLiveData::class.java)) {
                    vf.isAccessible = true
                    val vld = (vf.get(vm) as MutableLiveData<Any>)
                    svm?.javaClass?.declaredFields?.forEach { sf ->
                        if (sf.isAnnotationPresent(Shared::class.java)) {
                            val sName =
                                sf.getAnnotation<Shared>()?.name ?: return
                            if (sf.type.isAssignableFrom(MutableLiveData::class.java)) {
                                if (vName == sName) {
                                    sf.isAccessible = true
                                    val sld = (sf.get(svm) as MutableLiveData<Any>)

                                    vld.value = sld.value

                                    val observer = Observer<Any> {
                                        if (sld.value != it)
                                            sld.value = it
                                    }
                                    vld.observe(lifecycleOwner, observer)
                                    removableObservers[vld] = observer

                                    val sharedObserver = Observer<Any> {
                                        if (vld.value != it)
                                            vld.value = it
                                    }
                                    sld.observe(lifecycleOwner, sharedObserver)
                                    removableSharedObservers[sld] = sharedObserver

                                    return@viewModelForEach
                                }
                            } else {
                                throw Exception("Shared fields must be MutableLiveData")
                            }
                        }
                    }
                } else {
                    throw Exception("Shared fields must be MutableLiveData")
                }
            }
        }

    }

    override fun remove() {
        removableObservers.onEach {
            it.key.removeObserver(it.value)
        }
        removableSharedObservers.onEach {
            it.key.removeObserver(it.value)
        }
    }
}

fun sharedAnnotation(): Lazy<SharedAnnotation> = lazy {
    SharedAnnotationDelegate()
}