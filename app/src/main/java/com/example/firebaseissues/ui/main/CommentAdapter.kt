package com.example.firebaseissues.ui.main

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.firebaseissues.Data.CommentDataModel
import com.example.firebaseissues.R
import com.example.firebaseissues.ui.main.CommentAdapter.CommentViewHolder
import kotlinx.android.synthetic.main.comment_view.view.*

/**
 * Adapter used to render the comment section
 */
class CommentAdapter(val context: Context) : RecyclerView.Adapter<CommentViewHolder>() {

    private var dataModel: List<CommentDataModel> = emptyList()

    /**
     * 'method to set the list of all the comments for the issue
     * @param models: the list of all the comments
     */
    fun setDataModel(models: List<CommentDataModel>) {
        dataModel = models
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CommentViewHolder {
        val view = LayoutInflater.from(context).inflate(R.layout.comment_view, parent, false)
        return CommentViewHolder(view)
    }

    override fun getItemCount() = dataModel.size

    override fun onBindViewHolder(holder: CommentViewHolder, position: Int) {
        holder.commentView.text = dataModel[position].body
    }

    class CommentViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val commentView = view.commentText
    }
}