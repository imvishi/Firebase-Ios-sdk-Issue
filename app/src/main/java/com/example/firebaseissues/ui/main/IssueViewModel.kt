package com.example.firebaseissues.ui.main

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.MutableLiveData
import com.example.firebaseissues.Data.CommentDataModel
import com.example.firebaseissues.Data.IssueDataModel
import com.example.firebaseissues.Data.IssueDataProvider

class IssueViewModel(
    app: Application
) : AndroidViewModel(app), IssueDataProvider.Callback {

    private var dataProvider: IssueDataProvider = IssueDataProvider(app.applicationContext, this)
    val liveData = MutableLiveData<List<IssueDataModel>>()
    val commentLiveData = MutableLiveData<List<CommentDataModel>>()

    /**
     * method used to get the firebase issues
     */
    fun getFirebaseIssues() {
        dataProvider.getIssues()
    }

    /**
     * method used to get the issue's comments
     */
    fun getIssueComments(commentUrl: String) {
        dataProvider.getComments(commentUrl)
    }

    override fun onIssuesFetched(model: List<IssueDataModel>) {
        liveData.value = model
    }

    override fun onCommentsFetched(comments: List<CommentDataModel>) {
        commentLiveData.value = comments
    }
}
