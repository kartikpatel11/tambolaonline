package com.tambolaonline.main

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.AbsListView.CHOICE_MODE_MULTIPLE
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.CheckBox
import android.widget.ListView
import androidx.core.view.size
import com.tambolaonline.adapters.VariationListAdapter
import com.tambolaonline.data.Game
import com.tambolaonline.data.VariationListAdapterDataSet
import com.tambolaonline.util.TambolaConstants
import com.tambolaonline.util.TambolaSharedPreferencesManager
import com.tambolaonline.variations.VariationTypes
import kotlinx.android.synthetic.main.variationslist_item_view.view.*
import java.util.*
import java.util.stream.Collectors
import kotlin.collections.ArrayList

class VariationsListActivity : AppCompatActivity() {

    lateinit var game: Game

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
         setContentView(R.layout.activity_variations_list)

        //Get Game object from shared preferences
        TambolaSharedPreferencesManager.with(this.application)
        game = TambolaSharedPreferencesManager.get<Game>(TambolaConstants.TAMBOLA_GAME_SHAREDPREF_KEY)!!

        // access the listView from xml file
        var mListView = findViewById<ListView>(R.id.variationlist)
        mListView.choiceMode = CHOICE_MODE_MULTIPLE

        var dataSet: ArrayList<VariationListAdapterDataSet> = ArrayList()
        VariationTypes.values().forEach { dataSet.add(VariationListAdapterDataSet(it.variationname,false)) }

        val variationAdapter = VariationListAdapter( dataSet, this)
        mListView.adapter = variationAdapter

        //add on item click event , update the adapter
        mListView.onItemClickListener = AdapterView.OnItemClickListener{_,_,position,_ ->
            val dataModel: VariationListAdapterDataSet = dataSet!![position] as VariationListAdapterDataSet
            dataModel.isChecked = !dataModel.isChecked
            variationAdapter.notifyDataSetChanged()
        }

    }

    /**
     * Add selected variation to game object on click of next button
     */
    public fun selectVariations(view: View) {
        Log.i("Tambola","In selectVariations")

        var mListView = findViewById<ListView>(R.id.variationlist)
        var adapter = mListView.adapter as VariationListAdapter
        for( i in 0..adapter.count-1) {
            if(adapter.getItem(i).isChecked) {
                Log.i("Tambola","Adding variation ${adapter.getItem(i).variationName} to Game Object")
                var variationType = VariationTypes.fromVariationName(adapter.getItem(i).variationName)
                if( variationType!= null) {
                    game.variations.put(variationType, false)
                }
            }
        }

        //Store updated Game object
        TambolaSharedPreferencesManager.put(game, TambolaConstants.TAMBOLA_GAME_SHAREDPREF_KEY)

        // Goto next activity
        var intent: Intent = Intent(applicationContext, ContactListActivity::class.java)
        startActivity(intent)

    }

}

