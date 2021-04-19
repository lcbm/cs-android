package com.cesarschool.recycler_view_list

import android.content.Intent
import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import kotlinx.android.synthetic.main.activity_main.*


class MainActivity : AppCompatActivity(), RecyclerViewerAdapter.OnItemClickListener {
	private val list = generateMockList(5)
	private val adapter = RecyclerViewerAdapter(list, this)

	companion object {
		const val MAIN_ACTIVITY_EXTRA_PARCELABLE_ID = "MAIN_ACTIVITY_EXTRA_PARCELABLE_ID"
		const val MAIN_ACTIVITY_EXTRA_ITEM_INDEX_ID = "MAIN_ACTIVITY_EXTRA_ANIME_ITEM_INDEX_ID"
		const val MAIN_ACTIVITY_EXTRA_INSERT_LIST_CODE = 1
		const val MAIN_ACTIVITY_EXTRA_REMOVE_LIST_CODE = 2
	}

	override fun onCreate(savedInstanceState: Bundle?) {
		super.onCreate(savedInstanceState)
		setContentView(R.layout.activity_main)

		recyclerView.adapter = adapter
		recyclerView.layoutManager = LinearLayoutManager(this)
		recyclerView.setHasFixedSize(true)

		buttonFloatingActionToSecondActivity.setOnClickListener {
			val intent = Intent(this, SecondActivity::class.java)
			startActivityForResult(intent, MAIN_ACTIVITY_EXTRA_INSERT_LIST_CODE)
		}
	}

	private fun removeItem(view: View?, index: Int) {
		list.removeAt(index)
		adapter.notifyItemRemoved(index)
	}

	private fun insertItem(view: View?, newItem: Item) {
		val index = 0
		list.add(index, newItem)
		adapter.notifyItemInserted(index)
	}

	override fun onItemClick(position: Int) {
		val clickedItem = list[position]
		val intent = Intent(this, DetailsActivity::class.java)
		intent.putExtra(MAIN_ACTIVITY_EXTRA_ITEM_INDEX_ID, position)
		intent.putExtra(MAIN_ACTIVITY_EXTRA_PARCELABLE_ID, clickedItem)
		startActivityForResult(intent, MAIN_ACTIVITY_EXTRA_REMOVE_LIST_CODE)
	}

	public override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
		super.onActivityResult(requestCode, resultCode, data)
		if (resultCode != RESULT_OK) {
			return
		}

		if (requestCode == MAIN_ACTIVITY_EXTRA_INSERT_LIST_CODE) {
			val newItem: Item? = data?.getParcelableExtra(MAIN_ACTIVITY_EXTRA_PARCELABLE_ID)
			if (newItem != null) {
				insertItem(null, newItem)
			}
		}

		if (requestCode == MAIN_ACTIVITY_EXTRA_REMOVE_LIST_CODE) {
			val index: Int? = data?.getIntExtra(MAIN_ACTIVITY_EXTRA_ITEM_INDEX_ID, 0)
			if (index != null) {
				removeItem(null, index)
			}
		}
	}

	private fun generateMockList(size: Int): ArrayList<Item> {
		val list = ArrayList<Item>()
		for (i in 0 until size) {
			val item = Item(
				null,
				"Item $i",
				"No description",
			)
			list += item
		}

		return list
	}
}
