package com.cesarschool.weather_app

import android.app.ProgressDialog
import android.content.Context
import android.content.SharedPreferences
import android.net.ConnectivityManager
import android.net.NetworkCapabilities
import android.os.Build
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.cesarschool.weather_app.model.FindByCityResponse
import kotlinx.android.synthetic.main.fragment_search.*
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response


class SearchFragment : Fragment() {

	private val APP_ID = "8af80b5c7842aeb46c0367e599703d50"
	private lateinit var weatherViewModel: WeatherViewModel
	private var adapter = WeatherAdapter()

	override fun onCreateView(
		inflater: LayoutInflater,
		container: ViewGroup?,
		savedInstanceState: Bundle?
	): View? {
		val view: View = inflater.inflate(R.layout.fragment_search, container, false)
		renderConnectionToast()
		initButtonListeners(view)
		return view
	}

	override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
		super.onViewCreated(view, savedInstanceState)
		weather_recycler_view.adapter = adapter
		weather_recycler_view.layoutManager = LinearLayoutManager(activity)
		weatherViewModel = ViewModelProvider(this).get(WeatherViewModel::class.java)
		weatherViewModel.weatherList.observe(requireActivity(), Observer {
			adapter.submitList(it)
		})
	}

	private fun renderConnectionToast() {
		val text: String = if (isInternetAvailable()) { "Online" } else { "Offline" }
		Toast.makeText(activity, text, Toast.LENGTH_SHORT).show()
	}

	private fun isInternetAvailable(): Boolean {
		var result = false
		val connectivityManager =
			activity?.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager

		if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
			connectivityManager.getNetworkCapabilities(connectivityManager.activeNetwork)?.run {
				result = when {
					hasTransport(NetworkCapabilities.TRANSPORT_WIFI) -> true
					hasTransport(NetworkCapabilities.TRANSPORT_CELLULAR) -> true
					else -> false
				}
			}
		} else {
			connectivityManager.activeNetworkInfo?.run {
				if (type == ConnectivityManager.TYPE_WIFI) {
					result = true
				} else if (type == ConnectivityManager.TYPE_MOBILE) {
					result = true
				}
			}
		}

		return result
	}

	private fun initButtonListeners(view: View) {
		// Search Button
		val bSearch =
			view.findViewById<Button>(R.id.buttonSearch)

		bSearch.setOnClickListener {
			val city = editTextCity?.text.toString()
			if (city.isBlank()) {
				Toast.makeText(activity, "Please enter a city", Toast.LENGTH_SHORT).show()
			} else {
				getWeatherByCity(city)
			}
		}
	}

	private fun getSettingsSharedPreferences(): SharedPreferences {
		return this.requireActivity()
			.getSharedPreferences(SettingsFragment.PREFS_SETTINGS, Context.MODE_PRIVATE)
	}

	private fun getProgressBar(): ProgressDialog {
		val dialog = ProgressDialog(activity) // this = YourActivity
		dialog.setProgressStyle(ProgressDialog.STYLE_SPINNER)
		dialog.setMessage("Loading. Please wait...")
		dialog.isIndeterminate = true
		dialog.setCanceledOnTouchOutside(false)

		return dialog
	}

	private fun getWeatherByCity(city: String) {
		val progressDialog = getProgressBar()
		progressDialog.show()

		val prefs = getSettingsSharedPreferences()
		val temperatureUnit = prefs.getString(
			SettingsFragment.PREFS_SETTINGS_TEMPERATURE_UNIT,
			SettingsFragment.PREFS_SETTINGS_TEMPERATURE_UNIT_CELSIUS
		).toString()
		val descriptionLanguage = prefs.getString(
			SettingsFragment.PREFS_SETTINGS_DESCRIPTION_LANGUAGE,
			SettingsFragment.PREFS_SETTINGS_DESCRIPTION_LANGUAGE_ENGLISH
		).toString()

		val service = RetrofitManager().getWeatherService()
		val cities: Call<FindByCityResponse> = service.findByCityResponse(
			city,
			temperatureUnit,
			descriptionLanguage,
			APP_ID
		)

		cities.enqueue(object : Callback<FindByCityResponse> {
			override fun onResponse(
				call: Call<FindByCityResponse>,
				response: Response<FindByCityResponse>
			) {
				progressDialog.dismiss()
				val city: FindByCityResponse? = response.body()
				if (city != null) {
					weatherViewModel.updateCities(city.list)
					adapter.notifyDataSetChanged()
				}
			}

			override fun onFailure(call: Call<FindByCityResponse>, t: Throwable) {
				progressDialog.dismiss()
				Toast.makeText(activity, t.toString(), Toast.LENGTH_SHORT).show()
				Log.e("SEARCH_FRAGMENT", t.toString())
			}
		})
	}
}
