package ru.netology.nmedia

data class Post(

    val id: Int,
    val author: String,
    val content: String,
    val video: String,
    val publishedDate: String,
    val likeByMe: Boolean,
    val countLikes: Int,
    val countShare: Int,
    val countVisibility: Int

    )
