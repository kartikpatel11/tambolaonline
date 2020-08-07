package com.tambolaonline.main

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.ArrayAdapter
import android.widget.Button
import com.tambolaonline.data.Game
import com.tambolaonline.data.Participant
import com.tambolaonline.util.TambolaConstants
import com.tambolaonline.util.TambolaSharedPreferencesManager
import com.tambolaonline.variations.VariationTypes
import kotlinx.android.synthetic.main.activity_selection_summery.*

class SelectionSummeryActivity : AppCompatActivity() {
    lateinit var game: Game

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_selection_summery)

        game = TambolaSharedPreferencesManager.get<Game>(TambolaConstants.TAMBOLA_GAME_SHAREDPREF_KEY)!!

        var adapter = ArrayAdapter<VariationTypes>(this, android.R.layout.simple_list_item_1, ArrayList<VariationTypes>(game.variations.keys))
        selectedVariationList.adapter = adapter

        var participantAdapter = ArrayAdapter<Participant>(this, android.R.layout.simple_list_item_1, ArrayList<Participant>(game.participants))
        selectedParticipantList.adapter = participantAdapter

        val startgameBtn = findViewById<Button>(R.id.btn_startGame)
        startgameBtn.setOnClickListener{
            var intent: Intent = Intent(applicationContext, TambolaPlayGroundActivity::class.java)
            startActivity(intent)
        }
    }
}