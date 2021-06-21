package com.cesarschool.weather_app.model

import com.google.gson.annotations.SerializedName

data class Main(
	@SerializedName("temp")
	var temp: Float,

	@SerializedName("feels_like")
	var feels_like: Float,

	@SerializedName("temp_min")
	var temp_min: Float,

	@SerializedName("temp_max")
	var temp_max: Float,

	@SerializedName("pressure")
	var pressure: Int,

	@SerializedName("humidity")
	var humidity: Int,
)
