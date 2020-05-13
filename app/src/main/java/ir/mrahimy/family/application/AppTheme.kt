package ir.mrahimy.family.application

import androidx.annotation.StringRes
import ir.mrahimy.family.R

const val APP_THEME_KEY = "APP_THEME_KEY"
const val IS_THEME_AUTO_KEY = "IS_THEME_AUTO"

enum class AppTheme(@StringRes val text: Int) {
    DARK(R.string.dark), LIGHT(R.string.light)
}