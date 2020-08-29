package com.tambolaonline.main

import android.opengl.Visibility
import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.Gravity
import android.view.View
import android.widget.*
import androidx.annotation.RequiresApi
import androidx.recyclerview.widget.LinearLayoutManager
import com.tambolaonline.adapters.MyTicketViewRecyclerAdapter
import com.tambolaonline.data.Game
import com.tambolaonline.data.Host
import com.tambolaonline.services.ServiceBuilder
import com.tambolaonline.services.TambolaEndPoints
import com.tambolaonline.util.TambolaConstants
import com.tambolaonline.util.TambolaSharedPreferencesManager
import kotlinx.android.synthetic.main.activity_tambola_my_ticket_view.*
import retrofit2.Call
import retrofit2.Response

class TambolaMyTicketView : AppCompatActivity() {

    lateinit var game: Game

    @RequiresApi(Build.VERSION_CODES.M)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_tambola_my_ticket_view)

        //TODO:Get gameid and participant id from Intent params
        TambolaSharedPreferencesManager.with(this.application)
      //  game = TambolaSharedPreferencesManager.get<Game>(TambolaConstants.TAMBOLA_GAME_SHAREDPREF_KEY)!!

        val host = TambolaSharedPreferencesManager.get<Host>(TambolaConstants.HOST_KEY_NAME)
        val gameId = intent.getStringExtra("GAME_ID")
        val participantId = host?.hostPhNo


        val request = ServiceBuilder.buildService(TambolaEndPoints::class.java)
        val call = request.getMyTicket(gameId!!, participantId!!)

        call.enqueue(object: retrofit2.Callback<Game> {
            override fun onFailure(call: Call<Game>, t: Throwable) {
                Toast.makeText(this@TambolaMyTicketView, "${t.message}", Toast.LENGTH_SHORT).show()
            }

            override fun onResponse(call: Call<Game>, response: Response<Game>) {
                if(response.isSuccessful) {
                    myticket_recycler.apply {
                        setHasFixedSize(true)
                        layoutManager = LinearLayoutManager(this@TambolaMyTicketView)
                        adapter =
                            MyTicketViewRecyclerAdapter(response.body()!!,this.context)
                    }

                }
            }
        })

        createTambolaBoard()

        var gameBoardSwitch = this.findViewById<Switch>(R.id.switch_boardlayout);
        var gameBoardLayout = this.findViewById<LinearLayout>(R.id.tambola_participant_board_layout)

        gameBoardSwitch.setOnCheckedChangeListener{ compoundButton: CompoundButton, isChecked: Boolean ->
            gameBoardLayout.visibility = if (isChecked)  View.VISIBLE else View.GONE


        }
    }


    @RequiresApi(Build.VERSION_CODES.M)
    private fun createTambolaBoard() {

        var boardLayout = findViewById<LinearLayout>(R.id.tambola_participant_board_layout)

        var table = TableLayout(this)
        table.setShrinkAllColumns(true);
        table.setStretchAllColumns(true);


        boardLayout.addView(table)

        var params: TableLayout.LayoutParams = TableLayout.LayoutParams(
            TableLayout.LayoutParams.MATCH_PARENT, // This will define text view width
            TableLayout.LayoutParams.MATCH_PARENT // This will define text view height
        )

        // Add margin to the text view
        params.setMargins(10, 10, 10, 10)

        table.layoutParams = params

        for (i in 0..8) {
            var row = TableRow(this)
            for (j in 1..10) {
                var txt = TextView(this)
                var cellnum: Int = (i * 10) + j
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
}