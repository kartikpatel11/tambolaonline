package com.tambolaonline.main

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.AbsListView.CHOICE_MODE_MULTIPLE
import android.widget.ArrayAdapter
import android.widget.ListView
import com.tambolaonline.data.Game
import com.tambolaonline.util.TambolaConstants
import com.tambolaonline.util.TambolaSharedPreferencesManager
import com.tambolaonline.variations.VariationTypes
import java.util.*
import java.util.stream.Collectors

class VariationsListActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
         setContentView(R.layout.activity_variations_list)

        //Get Game object from shared preferences
        TambolaSharedPreferencesManager.with(this.application)
        val game =  TambolaSharedPreferencesManager.get<Game>(TambolaConstants.TAMBOLA_GAME_SHAREDPREF_KEY)

        // use arrayadapter and define an array
        val arrayAdapter: ArrayAdapter<*>

        val variationNames = VariationTypes.values().map{ e ->e.variationname }

        // access the listView from xml file
        var mListView = findViewById<ListView>(R.id.variationlist)
        mListView.choiceMode = CHOICE_MODE_MULTIPLE

        arrayAdapter = ArrayAdapter(this,
            android.R.layout.simple_list_item_multiple_choice, variationNames)
        mListView.adapter = arrayAdapter

        mListView.setOnItemClickListener()
    }

}