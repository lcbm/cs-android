package com.cesarschool.recycler_view_list

import android.content.Intent
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.view.View
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import kotlinx.android.synthetic.main.activity_main.*
import java.util.*
import kotlin.collections.ArrayList

class MainActivity : AppCompatActivity(), RecyclerViewerAdapter.OnItemClickListener {
	private val actionBarTitle = "Item List"
	private val filterTitle = "Select filter options"

	private val removeDuplicatesOption = "Remove duplicates"
	private val sortInsertionOrderOption = "Sort by insertion"
	private val sortAlphabeticalOrderOption = "Sort alphabetically"
	private var selectedFilterOptions = BooleanArray(3)
	private val filterOptions = arrayOf(
		removeDuplicatesOption,
		sortInsertionOrderOption,
		sortAlphabeticalOrderOption,
	)
	private val filterRemoveDuplicatesIndex = 0
	private val filterSortInsertionOrderIndex = 1
	private val filterSortAlphabeticalOrderIndex = 2

	private var list = generateMockList(5)
	private var filteredList = list
	private var adapter = RecyclerViewerAdapter(this)

	companion object {
		const val MAIN_ACTIVITY_EXTRA_PARCELABLE_ID = "MAIN_ACTIVITY_EXTRA_PARCELABLE_ID"
		const val MAIN_ACTIVITY_EXTRA_ITEM_INDEX_ID = "MAIN_ACTIVITY_EXTRA_ITEM_INDEX_ID"
		const val MAIN_ACTIVITY_EXTRA_INSERT_LIST_CODE = 1
		const val MAIN_ACTIVITY_EXTRA_REMOVE_LIST_CODE = 2
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
			startActivityForResult(intent, MAIN_ACTIVITY_EXTRA_INSERT_LIST_CODE)
		}
	}

	private fun removeItem(view: View?, item: Item) {
		adapter.setData(list)
		adapter.removeItem(view, item)
		executeFilterOptions()
	}

	private fun insertItem(view: View?, newItem: Item) {
		adapter.setData(list)
		adapter.insertItem(view, newItem)
		recyclerView.scrollToPosition(0)
		executeFilterOptions()
	}

	override fun onItemClick(position: Int) {
		val clickedItem = filteredList[position]
		val intent = Intent(this, DetailsActivity::class.java)
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
			val item: Item? = data?.getParcelableExtra(MAIN_ACTIVITY_EXTRA_ITEM_INDEX_ID)
			if (item != null) {
				removeItem(null, item)
			}
		}
	}

	private fun updateAdapterAndRefreshView(data: ArrayList<Item>) {
		adapter.setData(data)
		adapter.notifyDataSetChanged()
	}

	private fun executeFilterOptions() {
		filteredList = list
		if (selectedFilterOptions[filterRemoveDuplicatesIndex]) {
			filteredList = filteredList.distinctBy { it.title } as ArrayList<Item>
		}

		if (selectedFilterOptions[filterSortInsertionOrderIndex]) {
			filteredList = filteredList.reversed() as ArrayList<Item>
		}

		if (selectedFilterOptions[filterSortAlphabeticalOrderIndex]) {
			filteredList.sortBy { it.title }
		}

		if (selectedFilterOptions.contains(true)) {
			updateAdapterAndRefreshView(filteredList)
		} else {
			updateAdapterAndRefreshView(list)
		}
	}

	private fun showFilterDialog() {
		AlertDialog.Builder(this)
			.setTitle(filterTitle)
			.setMultiChoiceItems(filterOptions, selectedFilterOptions) { _, which, isChecked ->
				if (isChecked) {
					selectedFilterOptions[which] = isChecked
				} else if (selectedFilterOptions[which]) {
					selectedFilterOptions[which] = !isChecked

				}
			}
			.setPositiveButton("Ok") { _, _ -> executeFilterOptions() }
			.setNegativeButton("Cancel") { _, _ -> }
			.create()
			.show()
	}

	override fun onCreateOptionsMenu(menu: Menu?): Boolean {
		menuInflater.inflate(R.menu.activity_main_menu, menu)
		return super.onCreateOptionsMenu(menu)
	}

	override fun onOptionsItemSelected(item: MenuItem): Boolean {
		val id: Int = item.itemId
		if (id == R.id.buttonFilter) {
			showFilterDialog()
		}

		return super.onOptionsItemSelected(item)
	}

	private fun generateMockList(size: Int): ArrayList<Item> {
		val list = ArrayList<Item>()
		for (index in (size -1) downTo 0) {
			val item = Item(null, "Item $index", "No description", UUID.randomUUID().toString())
			val duplicate = Item(null, "Item $index", "No description", UUID.randomUUID().toString())
			list.add(item)
			list.add(duplicate)
		}

		return list
	}
}
