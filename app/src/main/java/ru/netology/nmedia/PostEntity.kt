package ru.netology.nmedia

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "posts")
data class PostEntity(
    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = "id")
    val id: Int,
    @ColumnInfo(name = "author")
    val author: String,
    @ColumnInfo(name = "content")
    val content: String,
    @ColumnInfo(name = "video")
    val video: String,
    @ColumnInfo(name = "publishedDate")
    val publishedDate: String,
    @ColumnInfo(name = "likeByMe")
    val likeByMe: Boolean = false,
    @ColumnInfo(name = "countLikes")
    val countLikes: Int = 0,
    @ColumnInfo(name = "countShare")
    val countShare: Int = 0,
    @ColumnInfo(name = "countVisibility")
    val countVisibility: Int = 0

)
