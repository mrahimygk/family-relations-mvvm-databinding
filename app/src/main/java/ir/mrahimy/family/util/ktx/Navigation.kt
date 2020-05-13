package ir.mrahimy.family.util.ktx

import androidx.fragment.app.FragmentActivity
import androidx.navigation.NavController

fun NavController.navigateUpOrFinish(activity: FragmentActivity?): Boolean {
    return if (navigateUp()) {
        true
    } else {
        activity?.finish()
        true
    }
}
