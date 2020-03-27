package com.example.firebaseissues.ui.main

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.firebaseissues.Data.IssueDataModel
import com.example.firebaseissues.R
import kotlinx.android.synthetic.main.issue_fragment.*

class IssueListFragment : Fragment() {

    companion object {
        fun newInstance() = IssueListFragment()
    }

    private lateinit var viewModel: IssueViewModel
    private lateinit var issueAdapter: IssueAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        viewModel = ViewModelProvider(this).get(IssueViewModel::class.java)
        issueAdapter = IssueAdapter(requireContext())
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ) = inflater.inflate(R.layout.issue_fragment, container, false)

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        issueAdapter.setIssueDataModel(mockIssue())
        issuesRecycleview.apply {
            adapter = issueAdapter
            layoutManager = LinearLayoutManager(requireContext())
        }
    }

    fun mockIssue() = mutableListOf<IssueDataModel>().apply {
        add(IssueDataModel("Test123", "Hello"))
        add(IssueDataModel("Test123", "Hello"))
        add(IssueDataModel("Test123", "Hello"))
    }
}
