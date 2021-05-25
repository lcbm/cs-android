package com.cesarschool.shared_preferences

import android.content.SharedPreferences
import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {
	private val actionBarTitle = "Files"
	private val editTextIsEmptyErr = "Por favor, preencha todos os campos!"

	companion object {
		const val PREFS_USER = "PREFS_USER"
		const val PREFS_USER_NAME = "PREFS_USER_NAME"
		const val PREFS_USER_AGE = "PREFS_USER_AGE"
	}

	override fun onCreate(savedInstanceState: Bundle?) {
		super.onCreate(savedInstanceState)
		setContentView(R.layout.activity_main)

		setSupportActionBar(findViewById(R.id.toolbar))
		supportActionBar?.title = actionBarTitle

		buttonWrite.setOnClickListener {
			val name = editTextName?.text.toString()
			if (name.isBlank()) {
				Toast.makeText(this, editTextIsEmptyErr, Toast.LENGTH_SHORT).show();
				return@setOnClickListener
			}

			val age = editTextAge?.text.toString()
			if (age.isBlank()) {
				Toast.makeText(this, editTextIsEmptyErr, Toast.LENGTH_SHORT).show();
				return@setOnClickListener
			}

			val prefs: SharedPreferences = this.getSharedPreferences(PREFS_USER, MODE_PRIVATE)
			prefs.edit().apply {
				putString(PREFS_USER_NAME, name)
				putInt(PREFS_USER_AGE, Integer.parseInt(age))
				apply()
			}
		}

		buttonRead.setOnClickListener {
			val prefs: SharedPreferences = this.getSharedPreferences(PREFS_USER, MODE_PRIVATE)
			val name = prefs.getString(PREFS_USER_NAME, "")
			val age = prefs.getInt(PREFS_USER_AGE, -1)
			textViewContent.text = "O seu nome é $name e sua idade é $age."
		}
	}
}
