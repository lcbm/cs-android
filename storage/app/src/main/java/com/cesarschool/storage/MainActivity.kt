package com.cesarschool.storage

import android.content.Intent
import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import kotlinx.android.synthetic.main.activity_main.*


class MainActivity : AppCompatActivity(), RecyclerViewerAdapter.OnItemClickListener {
	private val actionBarTitle = "Internal and External Storage"
	private var adapter = RecyclerViewerAdapter(this)
	private var list = ArrayList<FileData>()

	companion object {
		const val MAIN_ACTIVITY_FILE_STORAGE_TYPE_EXTERNAL = "External Storage"
		const val MAIN_ACTIVITY_FILE_STORAGE_TYPE_INTERNAL = "Internal Storage"
		const val MAIN_ACTIVITY_EXTRA_PARCELABLE_ID = "MAIN_ACTIVITY_EXTRA_PARCELABLE_ID"
		const val MAIN_ACTIVITY_EXTRA_ADD_FILE_CODE = 100
		const val MAIN_ACTIVITY_EXTRA_SHOW_DETAILS_CODE = 200
	}

	override fun onCreate(savedInstanceState: Bundle?) {
		super.onCreate(savedInstanceState)
		setContentView(R.layout.activity_main)

		setSupportActionBar(findViewById(R.id.toolbar))
		supportActionBar?.title = actionBarTitle

		adapter.setData(list)
		recyclerView.adapter = adapter
		recyclerView.layoutManager = LinearLayoutManager(this)
		recyclerView.setHasFixedSize(true)

		buttonFloatingActionToSecondActivity.setOnClickListener {
			val intent = Intent(this, SecondActivity::class.java)
			startActivityForResult(intent, MAIN_ACTIVITY_EXTRA_ADD_FILE_CODE)
		}
	}

	private fun insertItem(view: View?, newItem: FileData) {
		adapter.insertItem(view, newItem)
		recyclerView.scrollToPosition(0)
	}

	public override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
		super.onActivityResult(requestCode, resultCode, data)
		if (resultCode != RESULT_OK) {
			return
		}

		if (requestCode == MAIN_ACTIVITY_EXTRA_ADD_FILE_CODE) {
			val fileData: FileData? = data?.getParcelableExtra(MAIN_ACTIVITY_EXTRA_PARCELABLE_ID)
			if (fileData?.storage == MAIN_ACTIVITY_FILE_STORAGE_TYPE_EXTERNAL) {
				insertItem(null, fileData)
			}
			if (fileData?.storage == MAIN_ACTIVITY_FILE_STORAGE_TYPE_INTERNAL) {
				insertItem(null, fileData)
			}
		}
	}

	override fun onItemClick(position: Int) {
		val clickedItem = list[position]
		val intent = Intent(this, DetailsActivity::class.java)
		intent.putExtra(MAIN_ACTIVITY_EXTRA_PARCELABLE_ID, clickedItem)
		startActivity(intent)
	}
}

