package com.cesarschool.recycler_view_list

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import kotlinx.android.synthetic.main.activity_details.*
import kotlinx.android.synthetic.main.item.view.*

class RecyclerViewerAdapter(private val listener: OnItemClickListener)
	: RecyclerView.Adapter<RecyclerViewerAdapter.ViewHolder>() {

	interface OnItemClickListener {
		fun onItemClick(position: Int)
	}

	private var list: ArrayList<Item>? = ArrayList()

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

	fun setData(data: ArrayList<Item>) {
		this.list = data
	}

	fun removeItem(view: View?, item: Item) {
		val index = this.list?.indexOf(item)
		this.list?.removeIf { obj -> obj.uuid == item.uuid }
		if (index != null) {
			this.notifyItemRemoved(index)
		}
	}

	fun insertItem(view: View?, newItem: Item) {
		this.list?.add(0, newItem)
		this.notifyItemInserted(0)
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
		val currentItem = list?.get(position)!!
		holder.textViewTitle.text = currentItem.title
		holder.textViewDescription.text = currentItem.description
		holder.imageView.setImageURI(currentItem.imageUri)
		if (currentItem?.imageUri != null) {
			holder.imageView.setImageURI(currentItem?.imageUri)
		} else {
			holder.imageView.setImageResource(R.drawable.ic_image)
		}
	}

	override fun getItemCount(): Int = list?.size!!
}
