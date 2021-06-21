package com.cesarschool.weather_app

import android.content.Context
import android.content.SharedPreferences
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.RadioGroup
import androidx.fragment.app.Fragment

class SettingsFragment : Fragment() {

	private var temperatureUnit: String = ""
	private var descriptionLanguage: String = ""

	companion object {
		const val PREFS_SETTINGS = "PREFS_SETTINGS"
		const val PREFS_SETTINGS_TEMPERATURE_UNIT = "PREFS_SETTINGS_TEMPERATURE_UNIT"
		const val PREFS_SETTINGS_TEMPERATURE_UNIT_CELSIUS = "metric"
		const val PREFS_SETTINGS_TEMPERATURE_UNIT_FAHRENHEIT = "imperial"
		const val PREFS_SETTINGS_DESCRIPTION_LANGUAGE = "PREFS_SETTINGS_DESCRIPTION_LANGUAGE"
		const val PREFS_SETTINGS_DESCRIPTION_LANGUAGE_ENGLISH = "en"
		const val PREFS_SETTINGS_DESCRIPTION_LANGUAGE_PORTUGUESE = "pt"
	}

	override fun onCreateView(
		inflater: LayoutInflater,
		container: ViewGroup?,
		savedInstanceState: Bundle?
	): View? {
		val view: View = inflater.inflate(R.layout.fragment_settings, container, false)
		initButtons(view)
		initButtonListeners(view)
		return view
	}

	private fun initButtons(view: View) {
		val prefs: SharedPreferences = getSettingsSharedPreferences()

		// Check temperature unit from shared preferences
		val rGroupTemperatureUnit =
			view.findViewById<RadioGroup>(R.id.radioGroupTemperatureUnit)

		temperatureUnit =
			prefs.getString(PREFS_SETTINGS_TEMPERATURE_UNIT, PREFS_SETTINGS_TEMPERATURE_UNIT_CELSIUS)
				.toString()

		if (temperatureUnit == PREFS_SETTINGS_TEMPERATURE_UNIT_FAHRENHEIT) {
			rGroupTemperatureUnit.check(R.id.rButtonTemperatureUnitFahrenheit)
		} else {
			rGroupTemperatureUnit.check(R.id.rButtonTemperatureUnitCelsius)
		}

		// Check description language from shared preferences
		val rGroupDescriptionLanguage =
			view.findViewById<RadioGroup>(R.id.radioGroupDescriptionLanguage)

		descriptionLanguage =
			prefs.getString(PREFS_SETTINGS_DESCRIPTION_LANGUAGE, PREFS_SETTINGS_DESCRIPTION_LANGUAGE_ENGLISH)
			.toString()

		if (descriptionLanguage == PREFS_SETTINGS_DESCRIPTION_LANGUAGE_PORTUGUESE) {
			rGroupDescriptionLanguage.check(R.id.rButtonDescriptionLanguagePortuguese)
		} else {
			rGroupDescriptionLanguage.check(R.id.rButtonDescriptionLanguageEnglish)
		}
	}

	private fun initButtonListeners(view: View) {
		// Temperature Unit buttons
		val buttonTemperatureUnitCelsius =
			view.findViewById<Button>(R.id.rButtonTemperatureUnitCelsius)
		buttonTemperatureUnitCelsius.setOnClickListener {
			temperatureUnit = PREFS_SETTINGS_TEMPERATURE_UNIT_CELSIUS
		}

		val buttonTemperatureUnitFahrenheit =
			view.findViewById<Button>(R.id.rButtonTemperatureUnitFahrenheit)
		buttonTemperatureUnitFahrenheit.setOnClickListener {
			temperatureUnit = PREFS_SETTINGS_TEMPERATURE_UNIT_FAHRENHEIT
		}

		// Description Language buttons
		val buttonDescriptionLanguageEnglish =
			view.findViewById<Button>(R.id.rButtonDescriptionLanguageEnglish)
		buttonDescriptionLanguageEnglish.setOnClickListener {
			descriptionLanguage = PREFS_SETTINGS_DESCRIPTION_LANGUAGE_ENGLISH
		}

		val buttonDescriptionLanguagePortuguese =
			view.findViewById<Button>(R.id.rButtonDescriptionLanguagePortuguese)
		buttonDescriptionLanguagePortuguese.setOnClickListener{
			descriptionLanguage = PREFS_SETTINGS_DESCRIPTION_LANGUAGE_PORTUGUESE
		}

		// Save button
		val buttonSave =
			view.findViewById<Button>(R.id.buttonSave)
		buttonSave.setOnClickListener{
			val prefs: SharedPreferences = getSettingsSharedPreferences()
			prefs.edit().apply {
				putString(PREFS_SETTINGS_TEMPERATURE_UNIT, temperatureUnit)
				putString(PREFS_SETTINGS_DESCRIPTION_LANGUAGE, descriptionLanguage)
				apply()
			}
		}
	}

	private fun getSettingsSharedPreferences() : SharedPreferences{
		return this.requireActivity().getSharedPreferences(PREFS_SETTINGS, Context.MODE_PRIVATE)
	}
}
