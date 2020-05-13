package ir.mrahimy.family.util.ktx

import java.lang.reflect.Field

inline fun <reified T> Field.getAnnotation(): T? {
    return annotations.firstOrNull {
        (it.annotationClass == T::class)
    } as? T
}