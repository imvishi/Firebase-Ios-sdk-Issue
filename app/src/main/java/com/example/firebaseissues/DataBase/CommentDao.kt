package com.example.firebaseissues.DataBase

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query

@Dao interface CommentDao {
    @Query("SELECT * FROM comment_table WHERE comment_Url == :commentUrl")
    fun getComments(commentUrl: String): List<CommentEntity>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertComment(comment: CommentEntity)
}