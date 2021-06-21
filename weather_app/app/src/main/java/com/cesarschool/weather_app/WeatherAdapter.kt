package com.cesarschool.weather_app

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.cesarschool.weather_app.model.Cities
import kotlinx.android.synthetic.main.weather_card.view.*

class WeatherAdapter() :
	ListAdapter<Cities, WeatherAdapter.WeatherHolder>(WeatherDiffCallback()) {

	override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): WeatherHolder {
		val itemView = LayoutInflater.from(parent.context).inflate(
			R.layout.weather_card, parent,
			false
		)

		return WeatherHolder(itemView)
	}

	override fun onBindViewHolder(holder: WeatherHolder, position: Int) {
		holder.bindTo(getItem(position))
	}

	class WeatherHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
		private val cityTitleView: TextView = itemView.city_name_text
		private val tempTitleView: TextView = itemView.temp_text
		private val weatherIcon: ImageView = itemView.weather_image_view

		fun bindTo(cities: Cities) {
			cityTitleView.text = cities.name
			tempTitleView.text = cities.main.temp.toString()
			val url = "https://openweathermap.org/img/wn/${cities.weather[0].icon}@4x.png"
			Glide.with(itemView.context).load(url).into(weatherIcon)
		}
	}

	class WeatherDiffCallback : DiffUtil.ItemCallback<Cities>() {
		override fun areItemsTheSame(oldItem: Cities, newItem: Cities): Boolean {
			return oldItem.id == newItem.id
		}

		@SuppressLint("DiffUtilEquals")
		override fun areContentsTheSame(
			oldItem: Cities,
			newItem: Cities
		): Boolean {
			return oldItem == newItem
		}
	}
}
