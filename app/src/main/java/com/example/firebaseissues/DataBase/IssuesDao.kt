package com.example.firebaseissues.DataBase

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query

@Dao interface IssuesDao {

    @Query("SELECT * FROM issue_table")
    fun getIssues(): List<IssueEntity>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertIssue(issue: IssueEntity)
}