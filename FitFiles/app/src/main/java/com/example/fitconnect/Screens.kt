package com.example.fitconnect


import androidx.annotation.DrawableRes


sealed class Screen(val title: String, val route: String) {

    sealed class DrawerScreen(val dTitle:String,val dRoute:String, @DrawableRes val dIcon:Int)
        :Screen(dTitle,dRoute) {
        object Account : DrawerScreen("Account", "account", R.drawable.ic_account)
        object Exercise : DrawerScreen("Exercise", "exercise", R.drawable.ic_exercise)
//        object EditGoals : DrawerScreen("EditGoals", "editgoals", R.drawable.ic_editgoals)
        object Diet : DrawerScreen("Diet", "diet", R.drawable.ic_diet)
        object Reminder : DrawerScreen("Tasks", "tasks", R.drawable.ic_reminder)
        object AboutUs : DrawerScreen("About Us", "aboutus", R.drawable.ic_aboutus)
        object Help : DrawerScreen("Help", "help", R.drawable.ic_help)
        object Logout : DrawerScreen("Logout", "logout", R.drawable.ic_logout)
        }
    sealed class BottomScreen(val bTitle:String,val bRoute:String, @DrawableRes val bIcon:Int)
        :Screen(bTitle, bRoute) {
        object Exercise : BottomScreen("Exercise", "exercise", R.drawable.ic_exercise)
        object Diet : BottomScreen("Diet", "diet", R.drawable.ic_diet)
        object Tasks : BottomScreen("Tasks", "tasks", R.drawable.ic_reminder)
    }
    sealed class DescScreen(val sTitle:String,val sRoute:String)
        :Screen(sTitle, sRoute) {
    object ExerciseDescription : DescScreen("Exercise Description", "exercise_description/{itemId}")
        object DietDescription : DescScreen("Recipes", "diet_description/{itemId}")
    }
}
val screensInDrawer = listOf(
    Screen.DrawerScreen.Account,
    Screen.DrawerScreen.Exercise,
//    Screen.DrawerScreen.EditGoals,
    Screen.DrawerScreen.Diet,
    Screen.DrawerScreen.Reminder,
    Screen.DrawerScreen.AboutUs,
    Screen.DrawerScreen.Help,
    Screen.DrawerScreen.Logout
)
val screenInBottomBar = listOf(
    Screen.BottomScreen.Diet,
    Screen.BottomScreen.Exercise,
    Screen.BottomScreen.Tasks
)

