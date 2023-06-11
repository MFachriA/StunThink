package com.example.stunthink.presentation.navigation

const val CHILD_KEY_ID = "id"
const val CHILD_KEY_NAME = "name"

sealed class ScreenRoute(val route: String) {
    object Start: ScreenRoute(route = "start_screen")
    object Welcome: ScreenRoute(route = "welcome_screen")
    object Register: ScreenRoute(route = "register_screen")
    object Login: ScreenRoute(route = "login_screen")
    object Main: ScreenRoute(route = "main_screen")
    object Home: ScreenRoute(route = "home_screen")
    object Camera: ScreenRoute(route = "camera_screen")
    object Education: ScreenRoute(route = "education_screen")
    object Profile: ScreenRoute(route = "profile_screen")
    object ChildList: ScreenRoute(route = "child_list_screen")
    object ChildMonitoringMain: ScreenRoute(route = "child_monitoring_main_screen/{$CHILD_KEY_ID}/{$CHILD_KEY_NAME}") {
        fun passIdAndName(
            id: String,
            name: String
        ): String {
            return "child_monitoring_main_screen/$id/$name"
        }
    }

    object ChildNutrition: ScreenRoute(route = "child_nutrition_screen")
    object ChildStunting: ScreenRoute(route = "child_stunting_screen")
}