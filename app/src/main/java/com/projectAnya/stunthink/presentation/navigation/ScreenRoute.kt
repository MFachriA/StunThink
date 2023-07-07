package com.projectAnya.stunthink.presentation.navigation

const val CHILD_ID_KEY = "id"


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
    object ChildMonitoringMain: ScreenRoute(route = "child_monitoring_main_screen/")
    object ChildNutrition: ScreenRoute(route = "child_nutrition_screen")
    object ChildStunting: ScreenRoute(route = "child_stunting_screen")
    object FoodDetail: ScreenRoute(route = "food_detail_screen")
    object EducationDetail: ScreenRoute(route = "education_detail_screen")
    object ChildRegister: ScreenRoute(route = "child_register_screen")
    object StuntingDetail: ScreenRoute(route = "stunting_detail")
    object ChildFoodDetection: ScreenRoute(route = "child_food_detection")
    object ChildMonitoringNavigation: ScreenRoute(route = "child_monitoring_navigation")
    object ChildStuntingDetection: ScreenRoute(route = "child_stunting_detection")
    object ChildStuntingCamera: ScreenRoute(route = "child_stunting_camera")


}