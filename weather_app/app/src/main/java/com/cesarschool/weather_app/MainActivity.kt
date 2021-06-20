package com.cesarschool.weather_app

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.MenuItem
import com.google.android.material.bottomnavigation.BottomNavigationView
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity(),
	BottomNavigationView.OnNavigationItemSelectedListener {

	override fun onCreate(savedInstanceState: Bundle?) {
		super.onCreate(savedInstanceState)
		setContentView(R.layout.activity_main)
		initBottomNav()
	}

	private fun initBottomNav() {
		bottom_navigation.setOnNavigationItemSelectedListener(this)
		bottom_navigation.selectedItemId = R.id.searchMenu
	}

	override fun onNavigationItemSelected(item: MenuItem): Boolean {
		when (item.itemId) {
			R.id.searchMenu -> {
				updateFragment(SearchFragment())
			}
			R.id.favoriteMenu -> {
				updateFragment(FavoriteFragment())
			}
			R.id.settingsMenu -> {
				updateFragment(SettingsFragment())
			}
		}

		return true
	}

	private fun updateFragment(fragment: Fragment) {
		supportFragmentManager
			.beginTransaction()
			.replace(R.id.container, fragment)
			.commit()
	}
}
