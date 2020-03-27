package com.example.firebaseissues.Data

import com.google.gson.annotations.SerializedName

/**
 * Data model to hold the issue related data
 * @param title : the title of the issue
 * @param descriptor : the description of the issue
 * @param commentsUrl: the url of the  comment section
 */
data class IssueDataModel(
    @SerializedName("title")
    val title: String,
    @SerializedName("body")
    val descriptor: String,
    @SerializedName("comments_url")
    val commentsUrl: String
)