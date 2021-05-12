package com.cesarschool.storage

import android.content.Intent
import android.os.Bundle
import android.os.Environment
import android.text.method.ScrollingMovementMethod
import android.view.View
import android.widget.CheckBox
import android.widget.RadioButton
import androidx.appcompat.app.AppCompatActivity
import androidx.security.crypto.EncryptedFile
import androidx.security.crypto.MasterKeys
import kotlinx.android.synthetic.main.activity_second.*
import java.io.File
import java.io.FileOutputStream
import java.util.*

class SecondActivity : AppCompatActivity() {
	private val actionBarTitle = "Add File"
	private var fileStorageType = ""
	private var fileEncryption = FileData.FILE_DATA_NO_ENCRYPTION
	private val defaultFileName = "DefaultFileName"
	private val defaultContent = "This is the default file content!"

	override fun onCreate(savedInstanceState: Bundle?) {
		super.onCreate(savedInstanceState)
		setContentView(R.layout.activity_second)

		setSupportActionBar(findViewById(R.id.toolbar));
		supportActionBar?.setDisplayHomeAsUpEnabled(true);
		supportActionBar?.title = actionBarTitle;

		editTextFileContent.movementMethod = ScrollingMovementMethod()

		buttonFloatingActionInsert.setOnClickListener {
			var name = editTextFileName?.text.toString()
			if (name.isBlank()) {
				name = defaultFileName
			}

			var content = editTextFileContent?.text.toString()
			if (content.isBlank()) {
				content = defaultContent
			}

			val fileData = FileData(
				UUID.randomUUID().toString(),
				name,
				fileStorageType,
				fileEncryption,
			)

			if (fileStorageType == FileData.FILE_DATA_EXTERNAL_STORAGE) {
				writeFileExternalStorage(null, name, content, fileEncryption)
			}
			if (fileStorageType == FileData.FILE_DATA_INTERNAL_STORAGE) {
				writeFileInternalStorage(null, name, content, fileEncryption)
			}

			val resultIntent = Intent()
			resultIntent.putExtra(MainActivity.MAIN_ACTIVITY_EXTRA_PARCELABLE_ID, fileData)
			setResult(RESULT_OK, resultIntent)
			finish()
		}
	}

	fun onRadioButtonClicked(view: View) {
		if (view is RadioButton) {
			val checked = view.isChecked
			when (view.id) {
				R.id.radio_internal ->
					if (checked) {
						fileStorageType = FileData.FILE_DATA_INTERNAL_STORAGE
					}
				R.id.radio_external ->
					if (checked) {
						fileStorageType = FileData.FILE_DATA_EXTERNAL_STORAGE
					}
			}
		}
	}

	fun onCheckboxClicked(view: View) {
		if (view is CheckBox) {
			val checked: Boolean = view.isChecked

			when (view.id) {
				R.id.checkbox_use_jetpack_security ->
					if (checked) {
						fileEncryption = FileData.FILE_DATA_JETPACK_ENCRYPTION
					}
			}
		}
	}

	private fun writeFileInternalStorage(view: View?, name: String, content: String, fileEncryption: String) {
		try {
			val file = File(filesDir, name)
			if (fileEncryption != FileData.FILE_DATA_NO_ENCRYPTION) {
				val encryptedFile = encryptFile(file, fileEncryption)
				encryptedFile?.openFileOutput().use { writer ->
					writer?.write(content.toByteArray())
				}
			} else {
				openFileOutput(name, MODE_PRIVATE).use { writer ->
					writer.write(content.toByteArray())
				}
			}
		} catch (e: Exception) {
			e.printStackTrace()
		}
	}

	private fun isExternalStorageWritable(): Boolean {
		val state = Environment.getExternalStorageState()
		return Environment.MEDIA_MOUNTED == state
	}

	private fun writeFileExternalStorage(view: View?, name: String, content: String, fileEncryption: String) {
		if (!isExternalStorageWritable()) {
			return
		}

		try {
			var file = File(getExternalFilesDir(null), name)
			if (fileEncryption != FileData.FILE_DATA_NO_ENCRYPTION) {
				val encryptedFile = encryptFile(file, fileEncryption)
				encryptedFile?.openFileOutput().use { writer ->
					writer?.write(content.toByteArray())
				}
			} else {
				file.createNewFile()
				FileOutputStream(file, true).use { writer ->
					writer.write(content.toByteArray())
				}
			}
		} catch (e: Exception) {
			e.printStackTrace()
		}
	}

	private fun encryptFile(file: File, fileEncryption: String): EncryptedFile? {
		val keyGenParameterSpec = MasterKeys.AES256_GCM_SPEC
		val masterKeyAlias = MasterKeys.getOrCreate(keyGenParameterSpec)

		if (fileEncryption == FileData.FILE_DATA_JETPACK_ENCRYPTION)
			return EncryptedFile.Builder(
				file,
				this,
				masterKeyAlias,
				EncryptedFile.FileEncryptionScheme.AES256_GCM_HKDF_4KB
			).build()

		return null
	}
}
