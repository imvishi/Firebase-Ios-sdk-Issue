package com.example.firebaseissues.ui.main

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.MutableLiveData
import com.example.firebaseissues.Data.IssueDataModel
import com.example.firebaseissues.Data.IssueDataProvider

class IssueViewModel(
    app: Application
) : AndroidViewModel(app), IssueDataProvider.Callback {

    private var dataProvider: IssueDataProvider = IssueDataProvider(app.applicationContext, this)
    val liveData = MutableLiveData<List<IssueDataModel>>()

    init {
        getFirebaseIssues()
    }

    /**
     * method used to get the firebase issues
     */
    fun getFirebaseIssues() {
        dataProvider.getIssue()
    }

    override fun onIssuesFetched(model: List<IssueDataModel>) {
        liveData.value = model
    }
}
