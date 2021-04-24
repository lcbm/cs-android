package com.cesarschool.recycler_view_list

import android.content.Intent
import android.os.Bundle
import android.text.method.ScrollingMovementMethod
import androidx.appcompat.app.AppCompatActivity
import kotlinx.android.synthetic.main.activity_details.*


class DetailsActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_details)

//		val position = intent.getIntExtra(MainActivity.MAIN_ACTIVITY_EXTRA_ITEM_INDEX_ID, 0)
		val item: Item? = intent.getParcelableExtra(MainActivity.MAIN_ACTIVITY_EXTRA_PARCELABLE_ID)

		setSupportActionBar(findViewById(R.id.toolbar));
		supportActionBar?.setDisplayHomeAsUpEnabled(true);
		supportActionBar?.title = item?.title;

		textViewTitle.text = item?.title
		textViewDescription.text = item?.description
		textViewDescription.movementMethod = ScrollingMovementMethod()
		if (item?.imageUri != null) {
			imageView.setImageURI(item?.imageUri)
		} else {
			imageView.setImageResource(R.drawable.ic_image)
		}

		buttonFloatingActionRemove.setOnClickListener {
			val resultIntent = Intent()
			resultIntent.putExtra(MainActivity.MAIN_ACTIVITY_EXTRA_ITEM_INDEX_ID, item)
			setResult(RESULT_OK, resultIntent)
			finish()
		}
    }
}
