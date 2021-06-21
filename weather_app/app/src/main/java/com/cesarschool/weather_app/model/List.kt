package com.cesarschool.weather_app.model

import com.google.gson.annotations.SerializedName
import kotlin.collections.List as KList

data class List(
	@SerializedName("id")
	var id: Int,

	@SerializedName("name")
	var name: String,

	@SerializedName("coord")
	var coord: Coord,

	@SerializedName("dt")
	var dt: Long,

	@SerializedName("wind")
	var wind: Wind,

	@SerializedName("sys")
	var sys: Sys,

	@SerializedName("rain")
	var rain: String,

	@SerializedName("snow")
	var snow: String,

	@SerializedName("clouds")
	var clouds: Clouds,

	@SerializedName("main")
	var main: Main,

	@SerializedName("weather")
	var weather: KList<Weather>,
)
