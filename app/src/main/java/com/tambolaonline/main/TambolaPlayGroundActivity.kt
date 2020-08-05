package com.tambolaonline.main

import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.Gravity
import android.view.View
import android.widget.*
import androidx.annotation.RequiresApi
import com.tambolaonline.data.Game
import com.tambolaonline.util.TambolaConstants
import com.tambolaonline.util.TambolaSharedPreferencesManager

class TambolaPlayGroundActivity : AppCompatActivity() {
    @RequiresApi(Build.VERSION_CODES.M)

    lateinit var game: Game

    @RequiresApi(Build.VERSION_CODES.M)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_tambola_play_ground)

        //Get Game object from shared preferences
        TambolaSharedPreferencesManager.with(this.application)
        game = TambolaSharedPreferencesManager.get<Game>(TambolaConstants.TAMBOLA_GAME_SHAREDPREF_KEY)!!

        //Create tambola board to keep track of numbers that are called
        createTambolaBoad(this)




    }

    @RequiresApi(Build.VERSION_CODES.M)
    private fun createTambolaBoad(tambolaPlayGroundActivity: TambolaPlayGroundActivity) {

        var boardLayout = findViewById<LinearLayout>(R.id.tambola_board_layout)

        var table = TableLayout(this)
        table.setShrinkAllColumns(true);
        table.setStretchAllColumns(true);


        boardLayout.addView(table)

        var params : TableLayout.LayoutParams = TableLayout.LayoutParams(
            TableLayout.LayoutParams.MATCH_PARENT, // This will define text view width
            TableLayout.LayoutParams.MATCH_PARENT // This will define text view height
        )

        // Add margin to the text view
        params.setMargins(10,10,10,10)

        table.layoutParams = params

        for(i in 0..8) {
            var row = TableRow(this)
            for(j in 1..10) {
                var txt = TextView(this)
                var cellnum: Int = (i*10)+j
                txt.text = (cellnum.toString())
                txt.setId(cellnum)
                txt.setBackgroundResource(R.drawable.ticket_table_cell)
                txt.setTextColor(this.getColor(R.color.black))
                txt.setGravity(Gravity.CENTER)
                row.addView(txt)
            }
            table.addView(row)
        }
    }

    @RequiresApi(Build.VERSION_CODES.M)
    fun nextRandomNumber(view: View) {
        var currState = game.currentState
        var currNumber: Int
        do {
            currNumber = (1..90).random()
        } while (currState.contains(currNumber))

        var currNumberTxtBox = findViewById<TextView>(R.id.txt_currentnumber)
        currNumberTxtBox.text = currNumber.toString()

        //Add current number to hashMap
        currState.add(currNumber)

        //Highlight the number cell
        var numcell = findViewById<TextView>(currNumber)
        numcell.setBackgroundColor(this.getColor(R.color.colorAccent))


    }
}