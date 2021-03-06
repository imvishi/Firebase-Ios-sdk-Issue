package com.example.firebaseissues.Data

import android.content.Context
import android.content.SharedPreferences
import com.example.firebaseissues.Constants.sharedPrefFile
import com.example.firebaseissues.DataBase.DataBaseQuery
import com.example.firebaseissues.ui.main.Callback

private const val ISSUE_FETCHED_TIME = "issue_fetched_time"
private const val ONE_DAY_IN_MILLIS = 86400000L

/**
 * Data Provider class responsible to provide firebase Issue
 */
class IssueDataProvider(context: Context, val listener: Callback) : Callback {

    private val database = DataBaseQuery(context, this)
    private val dataFetcher = IssueDataFetcher(context, this)
    val sharedPref: SharedPreferences =
        context.getSharedPreferences(sharedPrefFile, Context.MODE_PRIVATE)

    /**
     * Callback used to get the issues available
     */
    interface Callback {
        /**
         * method will be called on issues fetched
         */
        fun onIssuesFetched(model: List<IssueDataModel>)

        /**
         * method will be called on issue's comments fetched
         */
        fun onCommentsFetched(comments: List<CommentDataModel>)
    }

    /**
     *  method used to provide the issues
     */
    fun getIssues() {
        val lastFetchedTime = sharedPref.getLong(ISSUE_FETCHED_TIME, 0)
        if (lastFetchedTime + ONE_DAY_IN_MILLIS < System.currentTimeMillis()) {
            // Fetched Issues are invalid now. Fetch again
            dataFetcher.fetchIssue()
            val editor = sharedPref.edit()
            editor.putLong(ISSUE_FETCHED_TIME, System.currentTimeMillis()).apply()
        } else {
            database.selectAllFromIssueTable()
        }
    }

    /**
     * method used to provide the issue's comment
     */
    fun getComments(commentUrl: String) {
        val lastFetchedTime = sharedPref.getLong(commentUrl, 0)
        if (lastFetchedTime + ONE_DAY_IN_MILLIS < System.currentTimeMillis()) {
            // Fetched comments are invalid now. Fetch again
            dataFetcher.fetchComments(commentUrl)
            val editor = sharedPref.edit()
            editor.putLong(commentUrl, System.currentTimeMillis()).apply()
        } else {
            database.selectCommentFromTable(commentUrl)
        }
    }

    override fun onCommentsFetched(
        comments: List<CommentDataModel>,
        commentUrl: String,
        isCachingNeeded: Boolean
    ) {
        listener.onCommentsFetched(comments)
        if (comments.isEmpty()) {
            val editor = sharedPref.edit()
            editor.putLong(commentUrl, 0).apply()
        }
        if (isCachingNeeded) {
            database.insertIntoCommentTable(comments, commentUrl)
        }
    }

    override fun onIssuesFetched(model: List<IssueDataModel>, isCachingNeeded: Boolean) {
        listener.onIssuesFetched(model)
        if (model.isEmpty()) {
            val editor = sharedPref.edit()
            editor.putLong(ISSUE_FETCHED_TIME, 0).apply()
        }
        if (isCachingNeeded) {
            database.insertIntoIssueTable(model)
        }
    }
}