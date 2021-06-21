package com.cesarschool.weather_app.model

import com.google.gson.annotations.SerializedName

data class Sys(
	@SerializedName("country")
	var country: String,
)
