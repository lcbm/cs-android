package com.cesarschool.storage

import android.os.Bundle
import android.os.Environment
import android.text.method.ScrollingMovementMethod
import android.view.MenuItem
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.security.crypto.EncryptedFile
import androidx.security.crypto.MasterKeys
import kotlinx.android.synthetic.main.activity_details.*
import java.io.File
import java.io.FileInputStream

class DetailsActivity : AppCompatActivity() {
	override fun onCreate(savedInstanceState: Bundle?) {
		super.onCreate(savedInstanceState)
		setContentView(R.layout.activity_details)

		val fileData: FileData? = intent.getParcelableExtra(MainActivity.MAIN_ACTIVITY_EXTRA_PARCELABLE_ID)

		setSupportActionBar(findViewById(R.id.toolbar));
		supportActionBar?.setDisplayHomeAsUpEnabled(true);
		supportActionBar?.title = fileData?.name;

		textViewFileName.text = fileData?.name
		textViewFileStorageType.text = fileData?.storage
		textViewFileEncryption.text = fileData?.encryption
		textViewFileContent.movementMethod = ScrollingMovementMethod()

		if (fileData?.storage == FileData.FILE_DATA_INTERNAL_STORAGE) {
			readFileInternalStorage(null, fileData)
		}

		if (fileData?.storage == FileData.FILE_DATA_EXTERNAL_STORAGE) {
			readFileExternalStorage(null, fileData)
		}
	}

	override fun onOptionsItemSelected(item: MenuItem): Boolean {
		when (item.itemId) {
			android.R.id.home -> {
				onBackPressed()
				return true
			}
		}
		return super.onOptionsItemSelected(item)
	}

	private fun readFileInternalStorage(view: View?, fileData: FileData) {
		try {
			val file = File(filesDir, fileData.name)
			if (fileData.encryption != FileData.FILE_DATA_NO_ENCRYPTION) {
				val encryptedFile = encryptFile(file, fileData.encryption)
				encryptedFile?.openFileInput().use{ inputStream ->
					textViewFileContent.text = inputStream?.readBytes()?.decodeToString()
				}
			} else {
				FileInputStream(file).use { stream ->
					textViewFileContent.text = stream.bufferedReader().use { it.readText() }
				}
			}
		} catch (e: Exception) {
			e.printStackTrace()
		}
	}

	private fun isExternalStorageReadable(): Boolean {
		val state = Environment.getExternalStorageState()
		return Environment.MEDIA_MOUNTED == state || Environment.MEDIA_MOUNTED_READ_ONLY == state
	}

	private fun readFileExternalStorage(view: View?, fileData: FileData) {
		if (!isExternalStorageReadable()) {
			return
		}

		try {
			val file = File(getExternalFilesDir(null), fileData.name)
			if (fileData.encryption != FileData.FILE_DATA_NO_ENCRYPTION) {
				val encryptedFile = encryptFile(file, fileData.encryption)
				encryptedFile?.openFileInput().use{ inputStream ->
					textViewFileContent.text = inputStream?.readBytes()?.decodeToString()
				}
			} else {
				FileInputStream(file).use { stream ->
					textViewFileContent.text = stream.bufferedReader().use { it.readText() }
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

