package com.aplikasi.UASPCS

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.navigation.findNavController
import androidx.navigation.ui.setupWithNavController
import com.google.android.material.bottomnavigation.BottomNavigationView

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val bottonNavigationView = findViewById<BottomNavigationView>(R.id.bottonNavigationView)
        val navController = findNavController(R.id.nav_fragment)
        bottonNavigationView.setupWithNavController(navController)
    }


}