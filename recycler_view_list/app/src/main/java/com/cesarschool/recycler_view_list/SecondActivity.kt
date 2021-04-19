package com.cesarschool.recycler_view_list

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.provider.MediaStore
import android.text.method.ScrollingMovementMethod
import androidx.appcompat.app.AppCompatActivity
import kotlinx.android.synthetic.main.activity_second.*

class SecondActivity : AppCompatActivity() {
	private var imageUri: Uri? = null
	private val defaultTitle = "No title"
	private val defaultDescription = "No description"

	companion object {
		const val SECOND_ACTIVITY_SELECT_PICTURE_CODE = 300
	}

	override fun onCreate(savedInstanceState: Bundle?) {
		super.onCreate(savedInstanceState)
		setContentView(R.layout.activity_second)

		editTextDescription.movementMethod = ScrollingMovementMethod()

		buttonImageUpload.setOnClickListener {
			val imageIntent = Intent(Intent.ACTION_OPEN_DOCUMENT, MediaStore.Images.Media.INTERNAL_CONTENT_URI)
			startActivityForResult(imageIntent, SECOND_ACTIVITY_SELECT_PICTURE_CODE)
		}

		buttonFloatingActionInsert.setOnClickListener {
			var title = editTextTitle?.text.toString()
			if (title.isBlank()) {
				title = defaultTitle
			}

			var description = editTextDescription?.text.toString()
			if (description.isBlank()) {
				description = defaultDescription
			}

			val newItem = Item(
				imageUri,
				title,
				description,
			)

			val resultIntent = Intent()
			resultIntent.putExtra(MainActivity.MAIN_ACTIVITY_EXTRA_PARCELABLE_ID, newItem)
			setResult(RESULT_OK, resultIntent)
			finish()
		}
	}

	public override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
		super.onActivityResult(requestCode, resultCode, data)
		if (resultCode != RESULT_OK) {
			return
		}

		if (requestCode == SECOND_ACTIVITY_SELECT_PICTURE_CODE) {
			imageUri = data?.data
			val contentResolver = applicationContext.contentResolver
			val takeFlags: Int = Intent.FLAG_GRANT_READ_URI_PERMISSION or Intent.FLAG_GRANT_WRITE_URI_PERMISSION
			imageUri?.let { contentResolver.takePersistableUriPermission(it, takeFlags) }
			buttonImageUpload.setImageURI(imageUri)
		}
	}
}
