package com.ynchinamobile.hexinlvxing

import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.widget.ImageView
import android.widget.Toast

class WelcomeActivity : AppCompatActivity() {

    lateinit var mImageView : ImageView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_welcome)
        mImageView = findViewById(R.id.imageView)
        mImageView.setOnClickListener({
            Toast.makeText(this,"Hello",Toast.LENGTH_SHORT).show()
        })
    }
}
