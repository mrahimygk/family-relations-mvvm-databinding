package ir.mrahimy.family.util

import androidx.navigation.NavDirections

/**
 * A class to implement command pattern for navigation process
 *
 * To() with the param directions (NavDirections) developers using sould this call *FragmentDirections action
 *
 */
sealed class NavigationCommand {
  data class To(val directions: NavDirections): NavigationCommand()
  data class ToWithFinish(val directions: NavDirections): NavigationCommand()
  data class ToAction(val actionId: Int): NavigationCommand()
  data class ToActionWithFinish(val actionId: Int): NavigationCommand()
  object Back: NavigationCommand()
  data class BackTo(val destinationId: Int, val inclusive: Boolean): NavigationCommand()
  data class ToRoot(val popNavIdOnFail: Int?) : NavigationCommand()
}