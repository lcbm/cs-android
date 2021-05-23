package com.cesarschool.shared_storage

import android.Manifest
import android.content.ContentUris
import android.content.pm.PackageManager
import android.os.Bundle
import android.provider.MediaStore
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.GridLayoutManager
import kotlinx.android.synthetic.main.activity_main.*


class MainActivity : AppCompatActivity() {
	var imageArray = ArrayList<Item>()
	private val actionBarTitle = "Shared Storage"
	private val adapter = RecyclerViewAdapter()

	companion object {
		private const val STORAGE_PERMISSION_REQUEST_CODE = 100;
	}

	override fun onCreate(savedInstanceState: Bundle?) {
		super.onCreate(savedInstanceState)
		setContentView(R.layout.activity_main)

		setSupportActionBar(findViewById(R.id.toolbar))
		supportActionBar?.title = actionBarTitle

		if (!hasStoragePermissionGranted()) {
			requestStoragePermissions()
		}

		addAllImagesToImagesArray()
		adapter.setData(imageArray)

		recyclerView.adapter = adapter
		recyclerView.layoutManager = GridLayoutManager(this, 2)
		recyclerView.setHasFixedSize(true)
	}

	private fun addAllImagesToImagesArray() {
		val projection = arrayOf(
			MediaStore.Images.Media._ID,
			MediaStore.Images.Media.DISPLAY_NAME,
		)

		applicationContext.contentResolver.query(
			MediaStore.Images.Media.EXTERNAL_CONTENT_URI,
			projection,      // Which columns to return
			null,    // Which rows to return (all rows)
			null, // Selection arguments (none)
			null     // Ordering
		)?.use { cursor ->
			val idxColumn = cursor.getColumnIndexOrThrow(MediaStore.Images.Media._ID)
			val idxName = cursor.getColumnIndexOrThrow(MediaStore.Images.Media.DISPLAY_NAME)
			while (cursor.moveToNext()) {
				val id = cursor.getLong(idxColumn)
				val title = cursor.getString(idxName)
				var item = Item(
					title,
					ContentUris.withAppendedId(MediaStore.Images.Media.EXTERNAL_CONTENT_URI, id),
				)
				imageArray.add(item)
			}
		}
	}

	private fun hasStoragePermissionGranted(): Boolean {
		return ContextCompat.checkSelfPermission(
			this,
			Manifest.permission.READ_EXTERNAL_STORAGE
		) == PackageManager.PERMISSION_GRANTED
	}

	private fun requestStoragePermissions() {
		ActivityCompat.requestPermissions(
			this,
			arrayOf(Manifest.permission.READ_EXTERNAL_STORAGE),
			STORAGE_PERMISSION_REQUEST_CODE
		)
	}

	override fun onRequestPermissionsResult(
		requestCode: Int,
		permissions: Array<out String>,
		grantResults: IntArray
	) {
		super.onRequestPermissionsResult(requestCode, permissions, grantResults)
		if (requestCode == STORAGE_PERMISSION_REQUEST_CODE)
			if (grantResults[0] == PackageManager.PERMISSION_DENIED) {
				Toast.makeText(
					this,
					"Permission denied, please click on 'Allow'' to grant storage access.",
					Toast.LENGTH_SHORT
				).show()
			} else {
				finish();
				startActivity(intent)
			}
	}
}
