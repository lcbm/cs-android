package com.cesarschool.weather_app

import android.content.Context
import android.net.ConnectivityManager
import android.net.NetworkCapabilities
import android.os.Build
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.Toast
import androidx.fragment.app.Fragment
import kotlinx.android.synthetic.main.fragment_search.*

class SearchFragment : Fragment() {

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
			val text = if (city.isBlank()) { "Please enter a city" } else { "Searching data for $city" }
			Toast.makeText(activity, text, Toast.LENGTH_SHORT).show()
		}
	}
}
