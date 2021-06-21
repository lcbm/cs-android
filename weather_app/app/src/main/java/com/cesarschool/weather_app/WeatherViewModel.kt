package com.cesarschool.weather_app

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.cesarschool.weather_app.model.Cities

class WeatherViewModel : ViewModel() {
	var weatherList = MutableLiveData<ArrayList<Cities>>()
	var list = ArrayList<Cities>()

	internal fun updateCities(cities: ArrayList<Cities>) {
		list.clear()
		list = cities
		weatherList.value = list
	}
}
