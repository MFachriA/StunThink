package com.projectAnya.stunthink.presentation.navigation

import android.os.Build
import androidx.annotation.RequiresApi
import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.projectAnya.stunthink.data.remote.dto.education.EducationDto
import com.projectAnya.stunthink.data.remote.dto.nutrition.FoodDto
import com.projectAnya.stunthink.domain.model.stunting.Stunting
import com.projectAnya.stunthink.presentation.navigation.start.StartScreen
import com.projectAnya.stunthink.presentation.screen.food.FoodDetailScreen
import com.projectAnya.stunthink.presentation.screen.login.LoginScreen
import com.projectAnya.stunthink.presentation.screen.main.MainScreen
import com.projectAnya.stunthink.presentation.screen.main.camera.CameraScreen
import com.projectAnya.stunthink.presentation.screen.main.education.EducationScreen
import com.projectAnya.stunthink.presentation.screen.main.education.detail.EducationDetailScreen
import com.projectAnya.stunthink.presentation.screen.main.home.HomeScreen
import com.projectAnya.stunthink.presentation.screen.main.profile.ProfileScreen
import com.projectAnya.stunthink.presentation.screen.monitoring.child.list.ChildListScreen
import com.projectAnya.stunthink.presentation.screen.monitoring.child.main.ChildMonitoringMainScreen
import com.projectAnya.stunthink.presentation.screen.monitoring.child.main.nutrition.ChildNutritionScreen
import com.projectAnya.stunthink.presentation.screen.monitoring.child.main.stunting.ChildStuntingScreen
import com.projectAnya.stunthink.presentation.screen.monitoring.child.register.ChildRegisterScreen
import com.projectAnya.stunthink.presentation.screen.register.RegisterScreen
import com.projectAnya.stunthink.presentation.screen.stunting.StuntingDetailScreen
import com.projectAnya.stunthink.presentation.screen.welcome.WelcomeScreen

@RequiresApi(Build.VERSION_CODES.O)
@Composable
fun ApplicationNavHost(
    navController: NavHostController = rememberNavController(),
) {
    NavHost(
        navController = navController,
        startDestination = ScreenRoute.Start.route
    ) {
        composable(
            route = ScreenRoute.Start.route
        ) {
            StartScreen(navController = navController)
        }
        composable(
            route = ScreenRoute.Welcome.route
        ) {
            WelcomeScreen(navController = navController)
        }
        composable(
            route = ScreenRoute.Register.route
        ) {
            RegisterScreen(navController = navController)
        }
        composable(
            route = ScreenRoute.Login.route
        ) {
            LoginScreen(navController = navController)
        }
        composable(
            route = ScreenRoute.Main.route
        ) {
            MainScreen(navController = navController)
        }
        composable(
            route = ScreenRoute.Home.route
        ) {
            HomeScreen(navController = navController)
        }
        composable(
            route = ScreenRoute.Camera.route
        ) {
            CameraScreen(navController = navController)
        }
        composable(
            route = ScreenRoute.Education.route
        ) {
            EducationScreen(navController = navController)
        }
        composable(
            route = ScreenRoute.Profile.route
        ) {
            ProfileScreen(navController = navController)
        }
        composable(
            route = ScreenRoute.ChildList.route
        ) {
            ChildListScreen(navController = navController)
        }
        composable(
            route = ScreenRoute.ChildMonitoringMain.route,
            arguments = listOf(
                navArgument(CHILD_ID_KEY) {
                    type = NavType.StringType
                },
                navArgument(CHILD_NAME_KEY) {
                    type = NavType.StringType
                }
            )
        ) {
            ChildMonitoringMainScreen(
                navController = navController,
                id = it.arguments?.getString(CHILD_ID_KEY) ?: "",
                name = it.arguments?.getString(CHILD_NAME_KEY) ?: ""
            )
        }
        composable(
            route = ScreenRoute.ChildNutrition.route,
            arguments = listOf(
                navArgument(CHILD_ID_KEY) {
                    type = NavType.StringType
                }
            )
        ) {
            ChildNutritionScreen(navController = navController)
        }
        composable(
            route = ScreenRoute.ChildStunting.route
        ) {
            ChildStuntingScreen(navController = navController)
        }
        composable(
            route = ScreenRoute.FoodDetail.route
        ) {
            val food =
                navController.previousBackStackEntry?.savedStateHandle?.get<FoodDto>("food")
            FoodDetailScreen(navController = navController, food = food)
        }
        composable(
            route = ScreenRoute.EducationDetail.route
        ) {
            val education =
                navController.previousBackStackEntry?.savedStateHandle?.get<EducationDto>("education")
            EducationDetailScreen(navController = navController, education = education)
        }
        composable(
            route = ScreenRoute.ChildRegister.route
        ) {
            ChildRegisterScreen(navController = navController)
        }
        composable(
            route = ScreenRoute.StuntingDetail.route
        ) {
            val stunting =
                navController.previousBackStackEntry?.savedStateHandle?.get<Stunting>("stunting")
            StuntingDetailScreen(navController = navController, stunting = stunting)
        }
    }
}