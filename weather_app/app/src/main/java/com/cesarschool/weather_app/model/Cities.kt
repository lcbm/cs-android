package com.cesarschool.weather_app.model

import com.google.gson.annotations.SerializedName

data class Cities(
	@SerializedName("id")
	var id: Int,

	@SerializedName("name")
	var name: String,

	@SerializedName("main")
	var main: Main,

	@SerializedName("weather")
	var weather: List<Weather>,
)
