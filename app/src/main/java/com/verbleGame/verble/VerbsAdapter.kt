package com.verbleGame.verble

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.core.view.isVisible
import androidx.recyclerview.widget.RecyclerView


class VerbsAdapter(private val lsVerbData: List<VerbModel>) :
    RecyclerView.Adapter<VerbsAdapter.ViewHolder>() {


    class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val tvVerb: TextView = view.findViewById(R.id.tvVerb)
        val tvHint: TextView = view.findViewById(R.id.tvHint)

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.verb_item, parent, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val currentItem = lsVerbData[position]
        holder.tvVerb.text = currentItem.verb
        holder.tvHint.text = currentItem.hint

        holder.itemView.setOnClickListener {

            /*When a RecyclerView item goes off-screen, its view is recycled and reused for another item.
            This means that if you change the state of a UI component in a recycled view and then scroll
            to a new position where that view is reused for another item, the UI state will still be the
            same as when it was last used.*/
            holder.setIsRecyclable(false) //stops the clicked view from being recycled, if not reset to be recyclable again and multiple items are clicked, performance could be impacted.
            if (holder.tvHint.isVisible) {
                holder.tvHint.visibility = View.GONE
                holder.setIsRecyclable(true) //when the clicked view is set back to the default settings, it is allowed to be recycled again.
            } else {
                holder.tvHint.visibility = View.VISIBLE
            }
        }
    }

    override fun getItemCount() = lsVerbData.size
}