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
        dataFetcher.fetchComments(commentUrl)
    }

    override fun onCommentsFetched(comments: List<CommentDataModel>, commentUrl: String) {
        listener.onCommentsFetched(comments)
        database.insertIntoCommentTable(comments, commentUrl)
    }

    override fun onIssuesFetched(model: List<IssueDataModel>) {
        listener.onIssuesFetched(model)
        database.insertIntoIssueTable(model)
    }
}