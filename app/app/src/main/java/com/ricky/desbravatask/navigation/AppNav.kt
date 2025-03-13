package com.ricky.desbravatask.navigation

import androidx.compose.animation.ExperimentalAnimationApi
import androidx.compose.animation.core.tween
import androidx.compose.animation.slideInHorizontally
import androidx.compose.animation.slideOutHorizontally
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavGraphBuilder
import com.google.accompanist.navigation.animation.AnimatedNavHost
import com.google.accompanist.navigation.animation.composable
import com.google.accompanist.navigation.animation.rememberAnimatedNavController
import com.ricky.desbravatask.presentation.auth.forget_password.ForgetPasswordScreen
import com.ricky.desbravatask.presentation.auth.forget_password.ForgetPasswordViewModel
import com.ricky.desbravatask.presentation.auth.login.LoginScreen
import com.ricky.desbravatask.presentation.auth.login.LoginViewModel
import com.ricky.desbravatask.presentation.auth.register.RegisterScreen
import com.ricky.desbravatask.presentation.auth.register.RegisterViewModel
import com.ricky.desbravatask.presentation.main.MainScreen
import com.ricky.desbravatask.presentation.main.MainViewModel
import com.ricky.desbravatask.presentation.splash.SplashScreen
import com.ricky.desbravatask.presentation.splash.SplashViewModel

@OptIn(ExperimentalAnimationApi::class)
@Composable
fun AppNav() {
    val navController = rememberAnimatedNavController()

    AnimatedNavHost(
        navController = navController,
        startDestination = Screens.SplashScreen.route
    ) {
        composableSlideHorizontally(route = Screens.SplashScreen.route) {
            val viewModel = hiltViewModel<SplashViewModel>()
            val state by viewModel.state.collectAsState()
            SplashScreen(
                navController = navController,
                state = state
            )
        }

        composableSlideHorizontally(route = Screens.LoginScreen.route) {
            val viewModel = hiltViewModel<LoginViewModel>()
            val state by viewModel.state.collectAsState()
            LoginScreen(
                navController = navController,
                state = state,
                onEvent = viewModel::onEvent
            )
        }

        composableSlideHorizontally(route = Screens.RegisterScreen.route) {
            val viewModel = hiltViewModel<RegisterViewModel>()
            val state by viewModel.state.collectAsState()
            RegisterScreen(
                navController = navController,
                state = state,
                onEvent = viewModel::onEvent
            )
        }

        composableSlideHorizontally(route = Screens.ForgetPasswordScreen.route) {
            val viewModel = hiltViewModel<ForgetPasswordViewModel>()
            val state by viewModel.state.collectAsState()
            ForgetPasswordScreen(
                navController = navController,
                state = state,
                onEvent = viewModel::onEvent
            )
        }

        composableSlideHorizontally(route = Screens.MainScreen.route) {
            val viewModel = hiltViewModel<MainViewModel>()
            val state by viewModel.state.collectAsState()
            MainScreen(
                navController = navController,
                state = state,
                onEvent = viewModel::onEvent
            )
        }
    }
}

@OptIn(ExperimentalAnimationApi::class)
fun NavGraphBuilder.composableSlideHorizontally(
    route: String,
    duration: Int = 400, // 1000 - 400
    enterOffset: Int = 700, // 300 - 1000
    exitOffset: Int = -700,
    popEnterOffset: Int = -700,
    popExitOffset: Int = 700,
    screen: @Composable () -> Unit
) {
    composable(
        route = route,
        enterTransition = { slideInHorizontally(tween(duration)) { enterOffset } },
        exitTransition = { slideOutHorizontally(tween(duration)) { exitOffset } },
        popEnterTransition = { slideInHorizontally(tween(duration)) { popEnterOffset } },
        popExitTransition = { slideOutHorizontally(tween(duration)) { popExitOffset } },
        content = { screen() }
    )
}