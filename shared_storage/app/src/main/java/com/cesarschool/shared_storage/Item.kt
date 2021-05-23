package com.cesarschool.shared_storage

import android.net.Uri
import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

@Parcelize
data class Item(
	var title: String,
	var Uri: Uri?,
) : Parcelable
