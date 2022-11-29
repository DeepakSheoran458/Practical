package com.demo.practical.model

data class MoviesList(
    val description: String,
    val director: String,
    val favorited_by_users: Int,
    val genres: List<String>,
    val id: Int,
    val main_star: String,
    val name: String,
    val thumbnail: String,
    val year: String
)