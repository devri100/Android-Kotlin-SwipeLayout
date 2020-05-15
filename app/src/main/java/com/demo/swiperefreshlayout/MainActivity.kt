package com.demo.swiperefreshlayout

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.demo.swiperefreshlayout.ui.SwipeFragment

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        if(savedInstanceState == null){
            supportFragmentManager.beginTransaction()
                    .replace(R.id.fragment_container, SwipeFragment())
                    .commitNow()
        }
    }
}
