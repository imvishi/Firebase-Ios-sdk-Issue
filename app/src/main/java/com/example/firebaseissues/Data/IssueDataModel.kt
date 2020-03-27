package com.example.firebaseissues.Data

import java.io.FileDescriptor

/**
 * Data model to hold the issue related data
 * @param title : the title of the issue
 * @param descriptor : the description of the issue
 */
data class IssueDataModel(
    val title: String,
    val descriptor: String
)