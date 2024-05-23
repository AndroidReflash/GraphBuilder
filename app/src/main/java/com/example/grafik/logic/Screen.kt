package com.example.grafik.logic


//contains addresses of screens, which are needed to navigate between
sealed class Screen(val route: String) {
    object MainScreen: Screen("main_screen")
    object ScreenWithGraphs: Screen("screen_with_graphs")
    object RegisterScreen: Screen("register_screen")

    //function provides ability to pass data by adding it do addresses of screens
    //can get multiple arguments
    fun withArgs(vararg args: String): String{
        return buildString {
            append(route)
            args.forEach {arg->
                append("/$arg")
            }
        }
    }
}

