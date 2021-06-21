package com.cesarschool.weather_app

import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class RetrofitManager {
	private val BASE_URL = "https://api.openweathermap.org/data/2.5/"
	private val instance = Retrofit.Builder()
		.baseUrl(BASE_URL)
		.addConverterFactory(GsonConverterFactory.create())
		.build()

	fun getWeatherService(): WeatherService = instance.create(WeatherService::class.java)
}
