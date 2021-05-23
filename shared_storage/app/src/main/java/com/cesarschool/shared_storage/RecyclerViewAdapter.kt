package com.cesarschool.shared_storage

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import kotlinx.android.synthetic.main.item.view.*

class RecyclerViewAdapter() : RecyclerView.Adapter<RecyclerViewAdapter.ViewHolder>() {
	private var images: ArrayList<Item> = ArrayList()

	class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
		val imageView: ImageView = itemView.imageView
		val textView: TextView = itemView.textView
	}

	override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
		val itemView = LayoutInflater.from(parent.context).inflate(
			R.layout.item,
			parent,
			false,
		)

		return ViewHolder(itemView)
	}

	override fun onBindViewHolder(viewHolder: ViewHolder, position: Int) {
		val item = images[position]
		viewHolder.textView.text = item.title
		viewHolder.imageView.setImageURI(item.Uri)
	}

	override fun getItemCount(): Int {
		return images.size
	}

	fun setData(data: ArrayList<Item>) {
		this.images = data
	}
}
