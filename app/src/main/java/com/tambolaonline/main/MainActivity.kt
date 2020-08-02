package com.tambolaonline.main

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import com.tambolaonline.data.Game
import com.tambolaonline.util.TambolaConstants
import com.tambolaonline.util.TambolaSharedPreferencesManager
import java.util.*

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        TambolaSharedPreferencesManager.with(this.application)

        val startgameBtn = findViewById<Button>(R.id.startGameButton)
        startgameBtn.setOnClickListener{

            //Initialize Game Object and put in sharedpreference to use in next screen
            var game = Game()
            TambolaSharedPreferencesManager.put(game, TambolaConstants.TAMBOLA_GAME_SHAREDPREF_KEY)

            var intent: Intent = Intent(applicationContext, VariationsListActivity::class.java)

            startActivity(intent)
        }
    }
}
