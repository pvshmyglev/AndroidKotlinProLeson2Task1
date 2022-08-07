package ru.netology.nmedia

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Update

@Dao
interface PostDao {

    @Query("SELECT * FROM posts ORDER BY id DESC")
    fun getAll(): List<PostEntity>

    @Insert
    fun insertPost(post: PostEntity) : Long

    @Update
    fun updatePost(post: PostEntity)

    @Query("DELETE FROM posts WHERE id = :id")
    fun removeById(id: Int)

    fun save(post: PostEntity): PostEntity {

        return if (post.id == 0) {

            val newId = insertPost(post)

            post.copy(id = newId.toInt())

        } else {

            updatePost(post)

            post

        }

    }

}