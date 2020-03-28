package com.example.firebaseissues.ui.main

import com.example.firebaseissues.Data.CommentDataModel
import com.example.firebaseissues.Data.IssueDataModel


/**
 * Callback used to get the issues available
 */
interface Callback {
    /**
     * method will be called on issues fetched
     */
    fun onIssuesFetched(model: List<IssueDataModel>, isCachingNeeded: Boolean)

    /**
     * method will be called on issue's comments fetched
     */
    fun onCommentsFetched(
        comments: List<CommentDataModel>,
        commentUrl: String,
        isCachingNeeded: Boolean
    )
}