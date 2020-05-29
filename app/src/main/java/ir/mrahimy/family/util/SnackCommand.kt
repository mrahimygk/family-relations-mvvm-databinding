package ir.mrahimy.family.util

import androidx.annotation.StringRes
import com.google.android.material.snackbar.Snackbar
import ir.mrahimy.family.data.pojo.SnackMessageAction

sealed class SnackCommand(
) {
    data class StringResSnackCommand(
        @StringRes val message: Int, val duration: Int = Snackbar.LENGTH_SHORT
    ) : SnackCommand()

    data class ActionedStringResSnackCommand(
        @StringRes val message: Int,
        val duration: Int = Snackbar.LENGTH_SHORT,
        val action: SnackMessageAction
    ) : SnackCommand()

    data class StringSnackCommand(
        val message: String, val duration: Int = Snackbar.LENGTH_SHORT
    ) : SnackCommand()

    data class ActionedStringSnackCommand(
        val message: String,
        val duration: Int = Snackbar.LENGTH_SHORT,
        val action: SnackMessageAction
    ) : SnackCommand()
}