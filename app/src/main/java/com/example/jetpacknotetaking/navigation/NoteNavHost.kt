package com.example.jetpacknotetaking.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import com.example.navigation.AppComposeNavigator
import com.example.navigation.NoteScreens

@Composable
fun NoteNavHost(
  navHostController: NavHostController,
  composeNavigator: AppComposeNavigator
) {
  NavHost(
    navController = navHostController,
    startDestination = NoteScreens.Home.route
  ) {
    noteNavigation(
      composeNavigator = composeNavigator
    )
  }
}
