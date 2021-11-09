package com.yefimoyevhen.appspot.ui

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.yefimoyevhen.appspot.R
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class AppspotActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_appspot)
    }
}