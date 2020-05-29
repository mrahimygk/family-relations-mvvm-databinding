package ir.mrahimy.family.data.pojo

import androidx.annotation.StringRes

/**
 * Using composition to be able to work with string messages and string resources messages.
 * Means developers can toast "string" or R.string.string
 */

data class SnackMessageAction(
    @StringRes val title: Int,
    val action: () -> Unit
) {
    fun paired() = Pair(title, action)
}