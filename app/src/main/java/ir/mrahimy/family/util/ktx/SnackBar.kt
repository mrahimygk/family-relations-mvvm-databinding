package ir.mrahimy.family.util.ktx

import android.view.View
import androidx.annotation.StringRes
import com.google.android.material.snackbar.Snackbar


/**
 * Shows a snackBar with the help of a view reference
 * There can be a named view in xml to get it by kotlin synthetics and call this function on it
 * Or we lazy people use getView()
 *
 * @param message: The string.xml id of the showing message
 * @param duration: defaults to SHORT. Will pass it to [SnackBar][com.google.android.material.snackbar.Snackbar]
 * @param action: the method to be invoked on SnackBar's action. It is a [Pair][kotlin.Pair] so that
 * we can get the title of action from string resources and the second part of pair is the function to be invoked.
 *
 */
fun View.showSnackBar(
    @StringRes message: Int,
    duration: Int = Snackbar.LENGTH_SHORT,
    action: Pair<Int, () -> Unit>? = null
) {
    val snackBar = Snackbar.make(this, message, duration)
    action?.let { act ->
        snackBar.setAction(act.first) {
            act.second()
        }
    }
    snackBar.show()
}


/**
 * Shows a snackBar with the help of a view reference
 * There should be a named view in xml to get it by kotlin synthetics and call this function on it
 * Or we lazy people use getView()
 *
 * @param message: The showing message as string
 * @param duration: defaults to SHORT. Will pass it to [SnackBar][com.google.android.material.snackbar.Snackbar]
 * @param action: the method to be invoked on SnackBar's action. It is a [Pair][kotlin.Pair] so that
 * we can get the title of action from string resources and the second part of pair is the function to be invoked.
 *
 */
fun View.showSnackBar(
    message: String,
    duration: Int = Snackbar.LENGTH_SHORT,
    action: Pair<Int, () -> Unit>? = null
) {
    val snackBar = Snackbar.make(this, message, duration)
    action?.let { act ->
        snackBar.setAction(act.first) {
            act.second()
        }
    }
    snackBar.show()
}
