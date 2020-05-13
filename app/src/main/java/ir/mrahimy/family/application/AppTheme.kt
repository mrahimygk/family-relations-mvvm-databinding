package ir.mrahimy.family.application

import androidx.annotation.StringRes
import ir.mrahimy.family.R

enum class AppTheme(@StringRes val text: Int) {
    DARK(R.string.dark), LIGHT(R.string.light)
}