package com.cesarschool.recycler_view_list

import android.net.Uri
import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

@Parcelize
data class Item(
	var imageUri: Uri?,
	var title: String,
	var description: String,
	var uuid: String,
): Parcelable
