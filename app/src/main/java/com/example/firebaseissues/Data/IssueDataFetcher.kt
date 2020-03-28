package com.example.firebaseissues.Data

import android.content.Context
import com.android.volley.Request
import com.android.volley.RequestQueue
import com.android.volley.Response
import com.android.volley.toolbox.JsonArrayRequest
import com.android.volley.toolbox.Volley
import com.example.firebaseissues.Constants
import com.example.firebaseissues.ui.main.Callback
import com.google.gson.Gson
import kotlinx.coroutines.*
import org.json.JSONArray
import kotlin.coroutines.resume
import kotlin.coroutines.suspendCoroutine

/**
 * Data fetcher class used to fetch the data from server
 */
class IssueDataFetcher(context: Context, val listener: Callback) {

    private val requestQueue: RequestQueue = Volley.newRequestQueue(context)
    private val coroutineScope: CoroutineScope = CoroutineScope(Dispatchers.IO)

    /**
     * method used to fetch the issues from server
     */
    fun fetchIssue() {
        coroutineScope.launch {
            val response = CoroutineScope(Dispatchers.IO).async {
                fetchData(Constants.issuesUrl)
            }.await()
            val issueList = async {
                Gson().fromJson(response.toString(), Array<IssueDataModel>::class.java).toList()
            }.await()

            withContext(Dispatchers.Main) {
                listener.onIssuesFetched(issueList)
            }
        }
    }

    /**
     * method used to fetch the comments from server
     */
    fun fetchComments(commentUrl: String) {
        coroutineScope.launch {
            val response = async {
                fetchData(commentUrl)
            }.await()

            val comments = async {
                Gson().fromJson(response.toString(), Array<CommentDataModel>::class.java).toList()
            }.await()

            withContext(Dispatchers.Main) {
                listener.onCommentsFetched(comments, commentUrl)
            }
        }
    }

    private suspend fun fetchData(url: String) = suspendCoroutine<JSONArray> { cont ->
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