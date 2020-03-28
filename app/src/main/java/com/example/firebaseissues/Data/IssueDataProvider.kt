package com.example.firebaseissues.Data

import android.content.Context
import com.android.volley.Request
import com.android.volley.RequestQueue
import com.android.volley.Response
import com.android.volley.toolbox.JsonArrayRequest
import com.android.volley.toolbox.Volley
import com.example.firebaseissues.Constants
import com.example.firebaseissues.DataBase.DataBaseQuery
import com.google.gson.Gson
import kotlinx.coroutines.*
import org.json.JSONArray
import kotlin.coroutines.resume
import kotlin.coroutines.suspendCoroutine

/**
 * Data Provider class responsible to provide firebase Issue
 */
class IssueDataProvider(
    context: Context,
    val listener: Callback) {

    private val coroutineScope: CoroutineScope = CoroutineScope(Dispatchers.IO)
    private val requestQueue: RequestQueue = Volley.newRequestQueue(context)
    private val database = DataBaseQuery(context)

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
        coroutineScope.launch {
            val response = async {
                fetchIssues()
            }.await()

            val issueList = async {
                Gson().fromJson(response.toString(), Array<IssueDataModel>::class.java).toList()
            }.await()

            database.insertIntoIssueTable(issueList)

            withContext(Dispatchers.Main) {
                listener.onIssuesFetched(issueList)
            }
        }
    }

    /**
     * method used to provide the issue's comment
     */
    fun getComments(commentUrl: String) {
        coroutineScope.launch {
            val response = async {
                fetchComments(commentUrl)
            }.await()

            val comments = async {
                Gson().fromJson(response.toString(), Array<CommentDataModel>::class.java).toList()
            }.await()

            database.insertIntoCommentTable(comments, commentUrl)
            withContext(Dispatchers.Main) {
                listener.onCommentsFetched(comments)
            }
        }
    }

    private suspend fun fetchIssues() = suspendCoroutine<JSONArray> { cont ->
        val jsonArrayRequest = JsonArrayRequest(
            Request.Method.GET,
            Constants.issuesUrl,
            null,
            Response.Listener { response -> cont.resume(response) },
            Response.ErrorListener { cont.resume(JSONArray()) }
        )
        requestQueue.add(jsonArrayRequest)
    }

    private suspend fun fetchComments(url: String) = suspendCoroutine<JSONArray> { cont ->
        val jsonArrayRequest = JsonArrayRequest(
            Request.Method.GET,
            url,
            null,
            Response.Listener { response -> cont.resume(response) },
            Response.ErrorListener { cont.resume(JSONArray()) }
        )
        requestQueue.add(jsonArrayRequest)
    }
}