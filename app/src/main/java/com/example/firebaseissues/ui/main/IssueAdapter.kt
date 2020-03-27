package com.example.firebaseissues.ui.main

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.firebaseissues.Data.IssueDataModel
import com.example.firebaseissues.R
import com.example.firebaseissues.ui.main.IssueAdapter.IssueViewHolder
import kotlinx.android.synthetic.main.issue_view.view.*

/**
 * Adapter used to render the list of Firebase issues
 */
class IssueAdapter(val context: Context): RecyclerView.Adapter<IssueViewHolder>() {

    private lateinit var dataModel: List<IssueDataModel>

    /**
     * method to set the list of all the issues that will be render on screen
     * @param models: the list of all the known issues
     */
    fun setIssueDataModel(models: List<IssueDataModel>) {
        dataModel = models
        notifyDataSetChanged()
    }


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): IssueViewHolder {
        val view = LayoutInflater.from(context).inflate(R.layout.issue_view, parent, false)
        return IssueViewHolder(view)
    }

    override fun getItemCount() = dataModel.size

    override fun onBindViewHolder(holder: IssueViewHolder, position: Int) {
        holder.bind(dataModel[position])
    }

    class IssueViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val title = view.titleText
        val description = view.descriptionText

        fun bind(model: IssueDataModel) {
            title.text = model.title
            description.text = model.descriptor
        }
    }
}