package ir.mrahimy.family.data.pojo

import androidx.annotation.StringRes
import com.google.android.material.snackbar.Snackbar

/**
 * Using composition to be able to work with string messages and string resources messages.
 * Means developers can toast "string" or R.string.string
 */
data class SnackMessage(
    @StringRes val message: Int,
    val duration: Int = Snackbar.LENGTH_SHORT,
    val action: SnackMessageAction? = null
) {
}

data class SnackMessageAction(
    @StringRes val title: Int,
    val action: () -> Unit
)

data class SnackMessageString(
    val message: String,
    val duration: Int = Snackbar.LENGTH_SHORT,
    val action: SnackMessageAction? = null
) {
}