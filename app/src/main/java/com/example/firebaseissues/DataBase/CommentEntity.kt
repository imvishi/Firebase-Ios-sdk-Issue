package com.example.firebaseissues.DataBase

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.PrimaryKey

@Entity(tableName = "comment_table")
data class CommentEntity(
    @PrimaryKey(autoGenerate = true) var commentId: Int = 0,
    @ColumnInfo(name = "comment") var comment: String,
    @ForeignKey(
        entity = IssueEntity::class,
        parentColumns = arrayOf("commentUrl"),
        childColumns = arrayOf("comment_Url"),
        onDelete = ForeignKey.CASCADE
    ) @ColumnInfo(name = "comment_Url") var comment_Url: String
)