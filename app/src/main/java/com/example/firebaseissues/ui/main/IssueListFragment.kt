package com.example.firebaseissues.ui.main

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.firebaseissues.R
import kotlinx.android.synthetic.main.issue_fragment.*

class IssueListFragment : Fragment(), OnItemClickListener {

    companion object {
        fun newInstance() = IssueListFragment()
    }

    private lateinit var viewModel: IssueViewModel
    private lateinit var issueAdapter: IssueAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        issueAdapter = IssueAdapter(requireContext(), this)
        viewModel = ViewModelProvider(requireActivity()).get(IssueViewModel::class.java).also {
            it.liveData.observe(this, Observer {
                issueAdapter.setIssueDataModel(it)
                progress.visibility = View.GONE
                issuesRecycleview.visibility = View.VISIBLE
            })
        }
        if (savedInstanceState == null) {
            viewModel.getFirebaseIssues()
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ) = inflater.inflate(R.layout.issue_fragment, container, false)

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        issuesRecycleview.apply {
            adapter = issueAdapter
            layoutManager = LinearLayoutManager(requireContext())
        }

        if (viewModel.liveData.value != null) {
            progress.visibility = View.GONE
            issuesRecycleview.visibility = View.VISIBLE
        }
    }

    override fun onItemClicked(position: Int) {
        val commentUrl = issueAdapter.getItemAtPosition(position).commentsUrl
        viewModel.commentLiveData.value = null
        parentFragmentManager.beginTransaction()
            .replace(R.id.container, CommentsFragment.newInstance(commentUrl))
            .addToBackStack("Issue")
            .commit()
    }
}
