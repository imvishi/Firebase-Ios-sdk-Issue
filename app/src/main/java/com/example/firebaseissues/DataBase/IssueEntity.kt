package com.example.firebaseissues.DataBase

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "issue_table")
data class IssueEntity(
    @PrimaryKey(autoGenerate = true) var issueId: Int = 0,
    @ColumnInfo(name = "title") var title: String,
    @ColumnInfo(name = "descriptor") var descriptor: String,
    @ColumnInfo(name = "commentUrl") var commentUrl: String
)