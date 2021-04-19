package com.cesarschool.recycler_view_list

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import kotlinx.android.synthetic.main.item.view.*

class RecyclerViewerAdapter(
	private val list: List<Item>,
	private val listener: OnItemClickListener
) : RecyclerView.Adapter<RecyclerViewerAdapter.ViewHolder>() {

	interface OnItemClickListener {
		fun onItemClick(position: Int)
	}

	inner class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView), View.OnClickListener {
		val imageView: ImageView = itemView.imageView
		val textViewTitle: TextView = itemView.textViewTitle
		val textViewDescription: TextView = itemView.textViewDescription

		init {
			itemView.setOnClickListener(this)
		}

		override fun onClick(v: View?) {
			val position = adapterPosition
			if (position != RecyclerView.NO_POSITION) {
				listener.onItemClick(position)
			}
		}
	}

	override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
		val itemView = LayoutInflater.from(parent.context).inflate(
			R.layout.item,
			parent,
			false,
		)

		return ViewHolder(itemView)
	}

	override fun onBindViewHolder(holder: ViewHolder, position: Int) {
		val currentItem = list[position]
		holder.textViewTitle.text = currentItem.title
		holder.textViewDescription.text = currentItem.description
		holder.imageView.setImageURI(currentItem.imageUri)
	}

	override fun getItemCount() = list.size
}
