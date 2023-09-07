package com.example.navigation

import androidx.navigation.NamedNavArgument
import androidx.navigation.NavType
import androidx.navigation.navArgument

sealed class NoteScreens(
  val route: String,
  val navArguments: List<NamedNavArgument> = emptyList()
) {
  val name: String = route.appendArguments(navArguments)

  object Home : NoteScreens("home")

  object CreateNote : NoteScreens(
    route = "createNote",
    navArguments = listOf(navArgument(argument_note_id) { type = NavType.LongType })
  ) {
    fun createRoute(noteId: Long) =
      name.replace("{${navArguments.first().name}}", noteId.toString())
  }

  companion object {
    const val argument_note_id = "noteId"
  }
}

private fun String.appendArguments(navArguments: List<NamedNavArgument>): String {
  val mandatoryArguments = navArguments.filter { it.argument.defaultValue == null }
    .takeIf { it.isNotEmpty() }
    ?.joinToString(separator = "/", prefix = "/") { "{${it.name}}" }
    .orEmpty()
  val optionalArguments = navArguments.filter { it.argument.defaultValue != null }
    .takeIf { it.isNotEmpty() }
    ?.joinToString(separator = "&", prefix = "?") { "${it.name}={${it.name}}" }
    .orEmpty()
  return "$this$mandatoryArguments$optionalArguments"
}
