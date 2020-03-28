package com.example.firebaseissues.DataBase

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase

private const val DATABASE_NAME = "issue_database.db"

@Database(entities = [IssueEntity::class, CommentEntity::class], version = 1)
abstract class IssueDataBase : RoomDatabase() {
    abstract fun issuesDao(): IssuesDao
    abstract fun commentDao(): CommentDao

    companion object {
        @Volatile
        private var instance: IssueDataBase? = null
        private val LOCK = Any()

        operator fun invoke(context: Context) = instance ?: synchronized(LOCK) {
            instance ?: buildDatabase(context).also { instance = it }
        }

        private fun buildDatabase(context: Context) = Room.databaseBuilder(
            context,
            IssueDataBase::class.java,
            DATABASE_NAME
        ).build()
    }
}