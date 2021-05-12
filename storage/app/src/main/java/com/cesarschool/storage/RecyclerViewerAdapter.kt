package com.cesarschool.storage

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import kotlinx.android.synthetic.main.item.view.*
import java.io.File
import java.io.IOException

class RecyclerViewerAdapter(private val listener: OnItemClickListener)
	: RecyclerView.Adapter<RecyclerViewerAdapter.ViewHolder>() {

	interface OnItemClickListener {
		fun onItemClick(position: Int)
	}

	private var list: ArrayList<FileData>? = ArrayList()

	inner class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView), View.OnClickListener {
		val textViewFileName: TextView = itemView.textViewFileName
		val textViewFileStorageType: TextView = itemView.textViewFileStorageType
		val textViewFileEncryption: TextView = itemView.textViewFileEncryption
		val imageViewRemove: ImageView = itemView.imageViewRemove

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

	fun setData(data: ArrayList<FileData>) {
		this.list = data
	}

	private fun removeItem(view: View?, fileData: FileData) {
		val index = this.list?.indexOf(fileData)
		this.list?.removeIf { obj -> obj.uuid == fileData.uuid }
		if (index != null) {
			this.notifyItemRemoved(index)
		}
	}

	fun insertItem(view: View?, newItem: FileData) {
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
		holder.textViewFileName.text = currentItem.name
		holder.textViewFileStorageType.text = currentItem.storage
		holder.textViewFileEncryption.text = currentItem.encryption
		holder.imageViewRemove.setOnClickListener {
			val fileData = this.list!![position]
			try {
				var file: File? = null
				if (fileData.storage == FileData.FILE_DATA_INTERNAL_STORAGE) {
					file = File(holder.itemView.context.filesDir, fileData.name)
				}
				if (fileData.storage == FileData.FILE_DATA_EXTERNAL_STORAGE) {
					file = File(holder.itemView.context.getExternalFilesDir(null), fileData.name)
				}
				if (file?.exists() == true) {
					file.delete()
				}
			} catch (e: IOException) {
				e.printStackTrace()
			}
			removeItem(holder.itemView, fileData)
		}
	}

	override fun getItemCount(): Int = list?.size!!
}

