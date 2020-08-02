package com.tambolaonline.main

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.AbsListView.CHOICE_MODE_MULTIPLE
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.ListView
import androidx.core.view.size
import com.tambolaonline.adapters.VariationListAdapter
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



        // access the listView from xml file
        var mListView = findViewById<ListView>(R.id.variationlist)
        mListView.choiceMode = CHOICE_MODE_MULTIPLE


        val variationAdapter = VariationListAdapter( VariationTypes.values(), this)
        mListView.adapter = variationAdapter

        }

    public fun selectVariations(view: View) {
        var mListView = findViewById<ListView>(R.id.variationlist)
        for (i in 0..mListView.size) {

            print((mListView.adapter.getItem(i) as VariationTypes).variationname)

        }
    }

}