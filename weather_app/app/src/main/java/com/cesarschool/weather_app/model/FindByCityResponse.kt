package com.cesarschool.weather_app.model

import com.google.gson.annotations.SerializedName

data class FindByCityResponse(
	@SerializedName("message")
	var message: String,

	@SerializedName("cod")
	var cod: String,

	@SerializedName("count")
	var count: Int,

	@SerializedName("list")
	var list: ArrayList<Cities>,
)
