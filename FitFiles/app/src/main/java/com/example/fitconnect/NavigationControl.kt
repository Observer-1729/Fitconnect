package com.example.fitconnect


import android.app.Application
import android.util.Log
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.platform.LocalContext
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.navArgument

@Composable
fun AppNavigation(navController: NavHostController,viewModel: MainViewModel,pd : PaddingValues) {
    val userInputViewModel: UserInputViewModel = viewModel()
    val currentRoute = remember { mutableStateOf("") }

    val dietViewModel: DietViewModel = viewModel()
    NavHost(navController, startDestination = "splash") {
        composable("splash") { SplashScreen(navController) }

        composable("login") {
            LoginScreen(
                onNavigateToSignUp = {
                    navController.navigate("signup") {
                        popUpTo("login") { inclusive = true }  // Clears back stack
                    }
                },
                onNavigateToProfile = {
                    navController.navigate("profile") {
                        popUpTo("login") { inclusive = true }
                    }
                },
                onNavigateToReset = { navController.navigate("reset") },
                onNavigateToLoading = { navController.navigate("finalLoading") }

            )
        }
        composable("signup") {
            SignUpScreen(
                onNavigateToLogin = {
                    navController.navigate("login") {
                        popUpTo("signup") { inclusive = true }
                    }
                }
            )
        }

        composable("reset"){
            EnterEmail(
                onNavigateToLogin = {navController.navigate("login")}
            )
        }

        composable("profile"){
            ProfileSetupScreen (
                viewModel = userInputViewModel,
                onNextClicked = {navController.navigate("gender")}
            )
        }

        composable("gender") {
            GenderSelector(
                onNavigateToProfile = {navController.navigate("profile")},
                onNavigateToAge = { navController.navigate("age") },
                viewModel = userInputViewModel
            )
        }

        composable("age") {
            AgeSelectionScreen(
                onNavigateToGender = { navController.navigate("gender") },
                onNavigateToHeight = { navController.navigate("height") },
                viewModel = userInputViewModel
            )
        }
        
        composable("height") {
            HeightSelectionScreen(
                onNavigateToAge = { navController.navigate("age") },
                onNavigateToWeight = { navController.navigate("weight") },
                viewModel = userInputViewModel
            )
        }
        composable("weight") {
            WeightSelectionScreen(
                onNavigateToHeight = { navController.navigate("height") },
                onBMISelect = { navController.navigate("bmi") },
                viewModel = userInputViewModel

            )
        }
        composable("bmi") {
            GoalSelectionScreen(
                viewModel = userInputViewModel,
                onGPSelect = {navController.navigate("finalLoading") {
//                    popUpTo("gender") { inclusive = true }
//                    launchSingleTop = true
                      // This ensures that the "Main View Screen" becomes the only screen at the top of the stack
                }
                }
            )
        }

        composable("finalLoading") {
            val context = LocalContext.current
            val dietViewModel: DietViewModel = viewModel(factory = DietViewModelFactory(context.applicationContext as Application))
            val exerciseViewModel: ExerciseViewModel = viewModel(factory = ExerciseViewModelFactory(context.applicationContext as Application))
            val mainViewModel: MainViewModel = viewModel()
            val userDao = AppDatabase.getDatabase(context).userDao()

            LoadingScreen(
                userId = getCurrentUserId(), // Add your Firebase Auth userId fetch here
                onLoadingFinished = {
                    navController.navigate("main view") {
                        popUpTo("profile") { inclusive = true }
                        launchSingleTop = true
                    }
                },
                mainViewModel = mainViewModel,
                dietViewModel = dietViewModel,
                exerciseViewModel = exerciseViewModel
            )
        }



        composable(Screen.DrawerScreen.Account.dRoute){
            AccountDetailsScreen(viewModel = userInputViewModel)

        }
        composable(Screen.DrawerScreen.Help.dRoute){
            HelpScreen()
        }

//
//        composable(Screen.DrawerScreen.EditGoals.route){
//            EditDetailsScreen(
//                viewModel = userInputViewModel
//            )
//
//        }
        composable(Screen.DrawerScreen.Reminder.dRoute){

        }
        composable(Screen.DrawerScreen.AboutUs.dRoute){
            AboutScreen()

        }
        composable("main view"){
            MainView()
        }

        composable(Screen.DrawerScreen.Reminder.dRoute) {

            val context = LocalContext.current
            val taskViewModel: TaskViewModel = viewModel(
                factory = TaskViewModelFactory(context.applicationContext as Application)
            )
            TaskScreen(viewModel = taskViewModel)
        }

        composable(Screen.DrawerScreen.Exercise.dRoute){
            val context = LocalContext.current
            val exerciseViewModel: ExerciseViewModel = viewModel(
                factory = ExerciseViewModelFactory(context.applicationContext as Application)
            )

            ExerciseScreen(exerciseState = exerciseViewModel.exerciseState,
                exerciseViewModel = exerciseViewModel,
                onNavigateToDetail = {
                        exerciseItemId ->
                    navController.navigate("exercise_description/$exerciseItemId")
                }
            )
        }

        composable(
            route = Screen.DescScreen.ExerciseDescription.sRoute,
            arguments = listOf(navArgument("itemId") { type = NavType.StringType })
        ) { backStackEntry ->
            val itemId = backStackEntry.arguments?.getString("itemId") ?: ""
            val viewModel: ExerciseViewModel = viewModel()

            // Fetch the full DietItem from DAO using the itemId
            val item by viewModel.getExerciseItemById(itemId).collectAsState(initial = null)

            item?.let {
                ExerciseDescription(it)
            }
        }

        composable(Screen.DrawerScreen.Diet.dRoute){
            val context = LocalContext.current
            val dietViewModel: DietViewModel = viewModel(
                factory = DietViewModelFactory(context.applicationContext as Application)
            )
            DietScreen(dietState = dietViewModel.dietState,
                dietViewModel = dietViewModel,
                onNavigateToRecipe = {
                        foodItemId ->
                    navController.navigate("diet_description/$foodItemId")
            })
        }
        composable(
            route = Screen.DescScreen.DietDescription.sRoute,
            arguments = listOf(navArgument("itemId") { type = NavType.StringType })
        ) { backStackEntry ->
            val itemId = backStackEntry.arguments?.getString("itemId") ?: ""
            val viewModel: DietViewModel = viewModel()

            // Fetch the full DietItem from DAO using the itemId
            val item by viewModel.getDietItemById(itemId).collectAsState(initial = null)

            item?.let {
                DietDescription(it)
            }
        }
    }
}


