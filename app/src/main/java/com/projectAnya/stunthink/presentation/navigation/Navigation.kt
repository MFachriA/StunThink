package com.projectAnya.stunthink.presentation.navigation

import android.os.Build
import androidx.annotation.RequiresApi
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.navigation
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.projectAnya.stunthink.data.remote.dto.education.EducationDto
import com.projectAnya.stunthink.data.remote.dto.height.HeightDto
import com.projectAnya.stunthink.data.remote.dto.nutrition.FoodDto
import com.projectAnya.stunthink.data.remote.dto.nutrition.NutritionDetailDto
import com.projectAnya.stunthink.domain.model.stunting.Stunting
import com.projectAnya.stunthink.presentation.navigation.start.StartScreen
import com.projectAnya.stunthink.presentation.screen.food.FoodDetailScreen
import com.projectAnya.stunthink.presentation.screen.food.monitoring.MonitoringFoodDetailScreen
import com.projectAnya.stunthink.presentation.screen.login.LoginScreen
import com.projectAnya.stunthink.presentation.screen.main.MainScreen
import com.projectAnya.stunthink.presentation.screen.main.camera.CameraScreen
import com.projectAnya.stunthink.presentation.screen.main.education.EducationScreen
import com.projectAnya.stunthink.presentation.screen.main.education.detail.EducationDetailScreen
import com.projectAnya.stunthink.presentation.screen.main.home.HomeScreen
import com.projectAnya.stunthink.presentation.screen.main.profile.ProfileScreen
import com.projectAnya.stunthink.presentation.screen.monitoring.child.camera.StuntingCameraScreen
import com.projectAnya.stunthink.presentation.screen.monitoring.child.camera.guide.FoodCameraGuideScreen
import com.projectAnya.stunthink.presentation.screen.monitoring.child.camera.guide.StuntingCameraGuideScreen
import com.projectAnya.stunthink.presentation.screen.monitoring.child.food.ChildFoodDetectionScreen
import com.projectAnya.stunthink.presentation.screen.monitoring.child.list.ChildListScreen
import com.projectAnya.stunthink.presentation.screen.monitoring.child.main.ChildMonitoringMainScreen
import com.projectAnya.stunthink.presentation.screen.monitoring.child.main.ChildMonitoringMainViewModel
import com.projectAnya.stunthink.presentation.screen.monitoring.child.main.child_detail.ChildMonitoringDetailScreen
import com.projectAnya.stunthink.presentation.screen.monitoring.child.main.nutrition.ChildNutritionScreen
import com.projectAnya.stunthink.presentation.screen.monitoring.child.main.nutrition_detail.NutritionDetailScreen
import com.projectAnya.stunthink.presentation.screen.monitoring.child.main.stunting.ChildStuntingScreen
import com.projectAnya.stunthink.presentation.screen.monitoring.child.register.ChildRegisterScreen
import com.projectAnya.stunthink.presentation.screen.monitoring.child.stunting.StuntingDetectionScreen
import com.projectAnya.stunthink.presentation.screen.monitoring.mother.main.MotherMonitoringMainScreen
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
        composable(
            route = ScreenRoute.FoodDetail.route
        ) {
            val food =
                navController.previousBackStackEntry?.savedStateHandle?.get<FoodDto>("food")
            FoodDetailScreen(
                navController = navController,
                food = food
            )
        }
        composable(
            route = ScreenRoute.MotherFoodDetail.route
        ) {
            val food =
                navController.previousBackStackEntry?.savedStateHandle?.get<FoodDto>("food")
            MonitoringFoodDetailScreen(
                navController = navController,
                food = food
            )
        }
        composable(
            route = ScreenRoute.MotherMonitoringMain.route
        ) {
            MotherMonitoringMainScreen(navController = navController)
        }
        composable(route = ScreenRoute.FoodDetection.route) {
            ChildFoodDetectionScreen(
                navController = navController
            )
        }
        composable(route = ScreenRoute.StuntingCameraGuide.route) {
            StuntingCameraGuideScreen(
                navController = navController
            )
        }
        composable(route = ScreenRoute.FoodCameraGuide.route) {
            FoodCameraGuideScreen(
                navController = navController
            )
        }
        composable(route = ScreenRoute.NutritionDetail.route) {
            val nutritionStart =
                navController.previousBackStackEntry?.savedStateHandle?.get<NutritionDetailDto>("nutritionStart")
            val nutritionTarget =
                navController.previousBackStackEntry?.savedStateHandle?.get<NutritionDetailDto>("nutritionTarget")
            NutritionDetailScreen(
                navController = navController,
                nutritionStart = nutritionStart,
                nutritionTarget = nutritionTarget
            )
        }
        navigation(
            startDestination = ScreenRoute.ChildList.route,
            route = ScreenRoute.ChildMonitoringNavigation.route
        ) {
            composable(route = ScreenRoute.ChildList.route) { backStackEntry ->
                val parentEntry = remember(backStackEntry) {
                    navController.getBackStackEntry(ScreenRoute.ChildList.route)
                }
                val childMonitoringMainViewModel = hiltViewModel<ChildMonitoringMainViewModel>(parentEntry)
                ChildListScreen(
                    navController = navController,
                    childMonitoringViewModel = childMonitoringMainViewModel
                )
            }
            composable(route = ScreenRoute.ChildMonitoringMain.route) { backStackEntry ->
                val parentEntry = remember(backStackEntry) {
                    navController.getBackStackEntry(ScreenRoute.ChildList.route)
                }
                val childMonitoringMainViewModel = hiltViewModel<ChildMonitoringMainViewModel>(parentEntry)
                ChildMonitoringMainScreen(
                    navController = navController,
                    viewModel = childMonitoringMainViewModel
                )
            }
            composable(route = ScreenRoute.ChildNutrition.route) { backStackEntry ->
                val parentEntry = remember(backStackEntry) {
                    navController.getBackStackEntry(ScreenRoute.ChildList.route)
                }
                val childMonitoringMainViewModel = hiltViewModel<ChildMonitoringMainViewModel>(parentEntry)
                ChildNutritionScreen(
                    navController = navController,
                    mainViewModel = childMonitoringMainViewModel
                )
            }
            composable(route = ScreenRoute.ChildStunting.route) { backStackEntry ->
                val parentEntry = remember(backStackEntry) {
                    navController.getBackStackEntry(ScreenRoute.ChildList.route)
                }
                val childMonitoringMainViewModel = hiltViewModel<ChildMonitoringMainViewModel>(parentEntry)
                ChildStuntingScreen(
                    navController = navController,
                    mainViewModel = childMonitoringMainViewModel
                )
            }
            composable(route = ScreenRoute.ChildFoodDetection.route) { backStackEntry ->
                val parentEntry = remember(backStackEntry) {
                    navController.getBackStackEntry(ScreenRoute.ChildList.route)
                }
                val childMonitoringMainViewModel = hiltViewModel<ChildMonitoringMainViewModel>(parentEntry)
                ChildFoodDetectionScreen(
                    navController = navController,
                    viewModel = childMonitoringMainViewModel
                )
            }
            composable(
                route = ScreenRoute.ChildFoodDetail.route
            ) { backStackEntry ->
                val food =
                    navController.previousBackStackEntry?.savedStateHandle?.get<FoodDto>("food")
                val parentEntry = remember(backStackEntry) {
                    navController.getBackStackEntry(ScreenRoute.ChildList.route)
                }
                val childMonitoringMainViewModel = hiltViewModel<ChildMonitoringMainViewModel>(parentEntry)
                MonitoringFoodDetailScreen(
                    navController = navController,
                    food = food,
                    viewModel = childMonitoringMainViewModel
                )
            }
            composable(route = ScreenRoute.ChildStuntingCamera.route) { backStackEntry ->
                val parentEntry = remember(backStackEntry) {
                    navController.getBackStackEntry(ScreenRoute.ChildList.route)
                }
                val childMonitoringMainViewModel = hiltViewModel<ChildMonitoringMainViewModel>(parentEntry)
                StuntingCameraScreen(
                    navController = navController,
                    mainViewModel = childMonitoringMainViewModel
                )
            }
            composable(route = ScreenRoute.ChildStuntingDetection.route) { backStackEntry ->
                val height =
                    navController.previousBackStackEntry?.savedStateHandle?.get<HeightDto>("height")
                val parentEntry = remember(backStackEntry) {
                    navController.getBackStackEntry(ScreenRoute.ChildList.route)
                }
                val childMonitoringMainViewModel = hiltViewModel<ChildMonitoringMainViewModel>(parentEntry)
                StuntingDetectionScreen(
                    navController = navController,
                    height = height,
                    mainViewModel = childMonitoringMainViewModel
                )
            }
            composable(route = ScreenRoute.ChildMonitoringDetail.route) { backStackEntry ->
                val parentEntry = remember(backStackEntry) {
                    navController.getBackStackEntry(ScreenRoute.ChildList.route)
                }
                val childMonitoringMainViewModel = hiltViewModel<ChildMonitoringMainViewModel>(parentEntry)
                ChildMonitoringDetailScreen(
                    navController = navController,
                    mainViewModel = childMonitoringMainViewModel
                )
            }
        }
    }
}