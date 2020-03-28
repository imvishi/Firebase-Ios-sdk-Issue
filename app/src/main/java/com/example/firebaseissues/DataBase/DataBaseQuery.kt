package com.example.firebaseissues.DataBase

import android.content.Context
import com.example.firebaseissues.Data.CommentDataModel
import com.example.firebaseissues.Data.IssueDataModel
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

/**
 * Abstract layer between database and the code.
 */
class DataBaseQuery(private val context: Context) {
    private val coroutineScope: CoroutineScope = CoroutineScope(Dispatchers.IO)
    private val dataBase = IssueDataBase(context)

    /**
     * method to insert issues fetched from server into the Issue Table.
     */
    fun insertIntoIssueTable(issueDataModel: List<IssueDataModel>) {
        coroutineScope.launch {
            issueDataModel
                .map {
                    IssueEntity(
                        title = it.title,
                        descriptor = it.descriptor,
                        commentUrl = it.commentsUrl
                    )
                }
                .forEach { dataBase.issuesDao().insertIssue(it) }
        }
    }

    /**
     * method to insert comments fetched from server into the table
     */
    fun insertIntoCommentTable(comments: List<CommentDataModel>, commentUrl: String) {
        coroutineScope.launch {
            comments
                .map { CommentEntity(comment = it.body, comment_Url = commentUrl) }
                .forEach { dataBase.commentDao().insertComment(it) }
        }
    }
}