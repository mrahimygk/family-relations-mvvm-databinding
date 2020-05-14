package ir.mrahimy.family.data.pojo

import androidx.annotation.StringRes

data class SnackMessageCapsule(
    @StringRes val message: Int,
    val duration: Int?,
    val action: SnackMessageActionCapsule?
) {
}

data class SnackMessageActionCapsule(
    @StringRes val title: Int,
    val action: (() -> Unit)?
)