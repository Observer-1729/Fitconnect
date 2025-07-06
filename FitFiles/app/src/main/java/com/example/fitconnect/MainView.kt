package com.example.fitconnect


import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.widthIn
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material.Icon
import androidx.compose.material.MaterialTheme
import androidx.compose.material.ScaffoldState
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Menu
import androidx.compose.material.rememberScaffoldState
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.IconButton
import androidx.compose.material.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.BottomNavigation
import androidx.compose.material.BottomNavigationItem
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.SideEffect
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.draw.clip
import androidx.compose.ui.platform.LocalContext
import androidx.core.content.ContextCompat
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import com.google.accompanist.systemuicontroller.rememberSystemUiController

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun MainView() {
    val scaffoldState: ScaffoldState = rememberScaffoldState()
    val scope: CoroutineScope = rememberCoroutineScope()
    val viewModel: MainViewModel = viewModel()
    val controller: NavHostController = rememberNavController()
    val navBackStackEntry by controller.currentBackStackEntryAsState()
    val currentRoute = navBackStackEntry?.destination?.route

    val userViewModel: UserInputViewModel = viewModel()

    val currentScreen = remember { mutableStateOf(viewModel.currentScreen.value) }
    val title = remember { mutableStateOf(currentScreen.value.title) }

    val dialogOpen = remember { mutableStateOf(false) } // ✅ Declare logout dialog state

    val noScaffoldScreens = listOf("login", "signup", "gender", "age", "height", "weight", "bmi", "reset", "profile", "finalLoading","splash")
    val context = LocalContext.current
    val violet = Color(ContextCompat.getColor(context, R.color.violet))

    val systemUiController = rememberSystemUiController()
    val statusBarColor = Color(ContextCompat.getColor(context, R.color.subcard)) // Or any color you want

    SideEffect {
        // Set status bar color
        systemUiController.setStatusBarColor(
            color = statusBarColor

        )
    }

    LaunchedEffect(currentRoute) { // ✅ Updates title dynamically
        val newScreen = screenInBottomBar.find { it.bRoute == currentRoute }
            ?: screensInDrawer.find { it.dRoute == currentRoute }
            ?: if (currentRoute?.startsWith("diet_description/") == true) {
                Screen.DescScreen.DietDescription
            } else if(currentRoute?.startsWith("exercise_description/") == true){
                Screen.DescScreen.ExerciseDescription
            }else null

        title.value = newScreen?.title ?: "Exercise"
    }

    LaunchedEffect(Unit) {
        controller.navigate(Screen.BottomScreen.Exercise.bRoute) {
            popUpTo(0)  // Clears any previous navigation stack
        }
    }
//    LaunchedEffect(Unit) {
//        userViewModel.getUserData()
//    }

    if (currentRoute !in noScaffoldScreens) {
        Scaffold(
            topBar = {
                val showBackButtonScreens = listOf(
//                    Screen.DrawerScreen.EditGoals.dRoute,
                    Screen.DrawerScreen.AboutUs.dRoute,
                    Screen.DrawerScreen.Account.dRoute,
                    Screen.DescScreen.DietDescription.sRoute,
                    Screen.DescScreen.ExerciseDescription.sRoute,
                    Screen.DrawerScreen.Help.dRoute
                )

                TopAppBar(
                    title = { Text(title.value) }, // ✅ Title dynamically updates
                    navigationIcon = {
                        if (currentRoute in showBackButtonScreens) {
                            IconButton(onClick = { controller.popBackStack() }) {
                                Icon(Icons.Default.ArrowBack, contentDescription = "Back")
                            }
                        } else {
                            IconButton(onClick = { scope.launch { scaffoldState.drawerState.open() } }) {
                                Icon(Icons.Default.Menu, contentDescription = "Menu")
                            }
                        }
                    },
                    colors = TopAppBarDefaults.topAppBarColors(
                        containerColor = violet,
                        titleContentColor = Color.Black,
                        navigationIconContentColor = Color.Black
                    )
                )
            },
            scaffoldState = scaffoldState,
            bottomBar = {
                val showBottomBarScreens = listOf(
                    Screen.BottomScreen.Exercise.bRoute,
                    Screen.BottomScreen.Diet.bRoute,
                    Screen.BottomScreen.Tasks.bRoute,
                    "main view"
                )
                if (currentRoute in showBottomBarScreens) {
                    BottomNavigation(Modifier.wrapContentSize(), backgroundColor = violet) {
                        screenInBottomBar.forEach { item ->
                            val isSelected = currentRoute == item.bRoute
                            val selectedColor = if (isSelected) Color.White else Color.Black
                            BottomNavigationItem(
                                selected = isSelected,
                                onClick = {
                                    controller.navigate(item.bRoute) {
                                        popUpTo(Screen.BottomScreen.Exercise.bRoute)
                                    }
                                    currentScreen.value = item
                                    title.value = item.bTitle
                                },
                                icon = { Icon(painter = painterResource(id = item.bIcon), contentDescription = item.bTitle) },
                                label = { Text(item.bTitle, color = selectedColor) },
                                selectedContentColor = Color.White,
                                unselectedContentColor = Color.Black
                            )
                        }
                    }
                }
            },
            drawerContent = {
                Column(modifier = Modifier
                        .fillMaxHeight()
                        .background(Color.Black)
                        .padding(16.dp)) {

                        Spacer(modifier = Modifier.height(50.dp))
                        LazyColumn {
                            items(screensInDrawer) { item ->
                                DrawerScreen(selected = currentRoute == item.dRoute, item = item) {
                                    scope.launch { scaffoldState.drawerState.close() }
                                    if (item.dRoute == "logout") {
                                        dialogOpen.value = true
                                    } else {
                                        controller.navigate(item.dRoute) { launchSingleTop = true }
                                    }
                                }
                            }
                        }
                }
            },
            drawerBackgroundColor = Color.Black,
            drawerContentColor = Color.White,
            modifier = Modifier.fillMaxSize(),
            backgroundColor = Color.Black,
        ) { paddingValues ->
            AppNavigation(navController = controller, viewModel = viewModel, pd = paddingValues)
        }
    } else {
        AppNavigation(navController = controller, viewModel = viewModel, pd = PaddingValues(0.dp))
    }

    // ✅ Show Logout Dialog
    if (dialogOpen.value) {
        LogoutDialog(navController = controller) {
            dialogOpen.value = false
        }
    }
}



@Composable
fun DrawerScreen(
    selected: Boolean,
    item: Screen.DrawerScreen,
    onDrawerItemClicked: () -> Unit
){
    val context = LocalContext.current
    val select = Color(ContextCompat.getColor(context, R.color.bulu))
    val textselect = Color(ContextCompat.getColor(context, R.color.subcard))
    val violet = Color(ContextCompat.getColor(context, R.color.heads))

    val background = if (selected) select else Color.Transparent  // 🔹 Black background
    val textColor = if (selected) textselect else violet
    Row(
        modifier = Modifier.fillMaxWidth()
            .padding(horizontal = 8.dp, vertical = 16.dp)
            .background(background, RoundedCornerShape(8.dp))
            .clip(RoundedCornerShape(8.dp))
            .clickable{
                onDrawerItemClicked()
            })
    {
        Icon(painter = painterResource(id = item.dIcon),
            contentDescription = item.dTitle,
            modifier = Modifier.padding(end = 8.dp , top = 4.dp),
            textColor)
        Text(text = item.dTitle, style = MaterialTheme.typography.h6, color = textColor)
    }

}