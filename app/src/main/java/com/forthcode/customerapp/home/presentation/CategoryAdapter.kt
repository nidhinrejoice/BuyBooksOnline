package com.forthcode.customerapp.home.presentation

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.BaseAdapter
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.forthcode.customerapp.R
import com.forthcode.customerapp.models.Category

class CategoryAdapter(
    private val context: Context,
    private val list: List<Category>
) :
    RecyclerView.Adapter<CategoryAdapter.ViewHolder>() {

    var onItemClick: ((Category) -> Unit)? = null
    override fun getItemId(position: Int): Long {
        return 0
    }


    inner class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val textView: TextView
        init {
            textView = view.findViewById(R.id.name)
            view.setOnClickListener({
                onItemClick?.invoke(list[adapterPosition])
            })
        }
    }
    override fun onCreateViewHolder(viewGroup: ViewGroup, viewType: Int): ViewHolder {
        // Create a new view, which defines the UI of the list item
        val view = LayoutInflater.from(viewGroup.context)
            .inflate(R.layout.adapter_categories, viewGroup, false)
        return ViewHolder(view)
    }

    // Replace the contents of a view (invoked by the layout manager)
    override fun onBindViewHolder(viewHolder: ViewHolder, position: Int) {

        // Get element from your dataset at this position and replace the
        // contents of the view with that element
        viewHolder.textView.text = list[position].catName
    }

    // Return the size of your dataset (invoked by the layout manager)
    override fun getItemCount() = list.size
}