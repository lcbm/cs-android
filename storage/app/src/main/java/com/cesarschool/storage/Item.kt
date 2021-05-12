package com.cesarschool.storage

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

@Parcelize
data class FileData(
	var uuid: String,
	var name: String,
	var storage: String,
	var encryption: String,
): Parcelable {
	companion object {
		const val FILE_DATA_EXTERNAL_STORAGE = "External Storage"
		const val FILE_DATA_INTERNAL_STORAGE = "Internal Storage"
		const val FILE_DATA_NO_ENCRYPTION = "No Encryption"
		const val FILE_DATA_JETPACK_ENCRYPTION = "Jetpack Encryption"
	}
}
