package com.example.firebaseissues.DataBase

import android.content.Context
import com.example.firebaseissues.Data.CommentDataModel
import com.example.firebaseissues.Data.IssueDataModel
import com.example.firebaseissues.ui.main.Callback
import kotlinx.coroutines.*

/**
 * Abstract layer between database and the code.
 */
class DataBaseQuery(context: Context, val listener: Callback) {
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

    /**
     * method used to selct all issues from table
     */
    fun selectAllFromIssueTable() {
        coroutineScope.launch {
            val issues = async {
                dataBase.issuesDao().getIssues()
            }.await()
            withContext(Dispatchers.Main) {
                listener.onIssuesFetched(
                    issues.map { IssueDataModel(it.title, it.descriptor, it.commentUrl) }
                )
            }
        }
    }

    /**
     * method used to selct issue's comment from table
     */
    fun selectCommentFromTable(commentUrl: String) {
        coroutineScope.launch {
            val comments = async {
                dataBase.commentDao().getComments(commentUrl)
            }.await()
            withContext(Dispatchers.Main) {
                listener.onCommentsFetched(
                    comments.map { CommentDataModel(it.comment) },
                    commentUrl
                )
            }
        }
    }
}