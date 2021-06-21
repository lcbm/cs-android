package com.cesarschool.weather_app

import com.cesarschool.weather_app.model.FindByCityResponse
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Query

interface WeatherService {
	@GET("find")
	fun findByCityResponse(
		@Query("q") city: String,
		@Query("units") units: String,
		@Query("lang") language: String,
		@Query("appid") apiKey: String,
	): Call<FindByCityResponse>
}
