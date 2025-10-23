package com.calyrsoft.ucbp1.navigation

sealed class Screen(val route: String) {
    object Home: Screen("home")
    object Github: Screen("github")
    object Profile: Screen("profile")

    object CardExamples: Screen("card")
    object Dollar: Screen("dollar")
    object PopularMovies: Screen("popularMovies")
<<<<<<< HEAD
    object MovieDetail : Screen("movie_detail")
=======
    object MovieDetail: Screen("movieDetail")
    object Atulado: Screen("atulado")
>>>>>>> feature/moviedetail
}