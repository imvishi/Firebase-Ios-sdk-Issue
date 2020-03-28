package com.example.firebaseissues.ui.main

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.firebaseissues.Data.CommentDataModel
import com.example.firebaseissues.R
import kotlinx.android.synthetic.main.comment_fragment.*

private const val COMMENT_URL = "comment_url"
/**
 * Fragment used to show the comment section of the given issue
 */
class CommentsFragment : Fragment() {

    companion object {
        fun newInstance(commentUrl: String) = CommentsFragment().apply {
            val bundle = Bundle().apply {
                putString(COMMENT_URL, commentUrl)
            }
            arguments = bundle
        }
    }

    private lateinit var commentAdapter: CommentAdapter
    private lateinit var viewModel: IssueViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        viewModel = ViewModelProvider(requireActivity()).get(IssueViewModel::class.java).also {
            it.commentLiveData.observe(this, Observer { commentsModel ->
                if (commentsModel == null) {
                    return@Observer
                }
                val comments = if (commentsModel.isEmpty()) {
                    listOf(CommentDataModel("Comments not found"))
                } else {
                    commentsModel
                }
                commentAdapter.setDataModel(comments)
                progress.visibility = View.GONE
                commentRecycleview.visibility = View.VISIBLE
            })
        }
        if (savedInstanceState == null) {
            val commentUrl = arguments!!.getString(COMMENT_URL, "")
            viewModel.getIssueComments(commentUrl)
        }
        commentAdapter = CommentAdapter(requireContext())
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ) = inflater.inflate(R.layout.comment_fragment, container, false)

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        commentRecycleview.apply {
            adapter = commentAdapter
            layoutManager = LinearLayoutManager(requireContext())
        }
    }
}