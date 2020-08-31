package com.tambolaonline.main

import android.content.*
import android.opengl.Visibility
import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.Gravity
import android.view.View
import android.widget.*
import androidx.annotation.RequiresApi
import androidx.localbroadcastmanager.content.LocalBroadcastManager
import androidx.recyclerview.widget.LinearLayoutManager
import com.google.firebase.messaging.FirebaseMessaging
import com.tambolaonline.adapters.MyTicketViewRecyclerAdapter
import com.tambolaonline.data.Game
import com.tambolaonline.data.Host
import com.tambolaonline.services.ServiceBuilder
import com.tambolaonline.services.TambolaEndPoints
import com.tambolaonline.util.TambolaConstants
import com.tambolaonline.util.TambolaSharedPreferencesManager
import kotlinx.android.synthetic.main.activity_tambola_my_ticket_view.*
import org.w3c.dom.Text
import retrofit2.Call
import retrofit2.Response
import java.util.HashMap

class TambolaMyTicketView : AppCompatActivity() {

    lateinit var game: Game
    lateinit var myTicketViewBroadcastReceiver: BroadcastReceiver

    @RequiresApi(Build.VERSION_CODES.M)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_tambola_my_ticket_view)


        TambolaSharedPreferencesManager.with(this.application)


        val host = TambolaSharedPreferencesManager.get<Host>(TambolaConstants.HOST_KEY_NAME)
        val gameId = intent.getStringExtra(TambolaConstants.GAME_ID)
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

                        if(response.body()!=null) {
                            //Store response as game object in sharedpref
                            TambolaSharedPreferencesManager.put(response.body(),TambolaConstants.TAMBOLA_GAME_SHAREDPREF_KEY)

                            game = TambolaSharedPreferencesManager.get<Game>(TambolaConstants.TAMBOLA_GAME_SHAREDPREF_KEY)!!

                            layoutManager = LinearLayoutManager(this@TambolaMyTicketView)
                            adapter =
                                MyTicketViewRecyclerAdapter(response.body()!!, this.context)

                            //Subscribe to GameID topic to get latest status
                            FirebaseMessaging.getInstance().subscribeToTopic(gameId)
                                .addOnCompleteListener { task ->
                                    var msg = getString(R.string.msg_subscribed)
                                    if (!task.isSuccessful) {
                                        msg = getString(R.string.msg_subscribe_failed)
                                    }
                                    Log.i("Test::", msg)
                                    Toast.makeText(baseContext, msg, Toast.LENGTH_LONG).show()
                                }

                        }
                    }

                }
            }
        })

        createTambolaBoard()

        var gameBoardSwitch = findViewById<Switch>(R.id.switch_boardlayout)
        var gameBoardLayout = findViewById<LinearLayout>(R.id.layout_board)


        gameBoardSwitch.setOnCheckedChangeListener{ compoundButton: CompoundButton, isChecked: Boolean ->
            gameBoardLayout.visibility = if (isChecked)  View.VISIBLE else View.GONE


        //Set Broadcast Receiver

             myTicketViewBroadcastReceiver = object : BroadcastReceiver() {
                override fun onReceive(context: Context?, intent: Intent?) {


                    var currentNumberText = findViewById<TextView>(R.id.txt_currentnumber1)
                    var lastFiveNumbers = findViewById<TextView>(R.id.txt_lastfivenumbers)

                    var nextNum = intent?.extras?.getInt(TambolaConstants.NEXT_NUMBER,0)

                    if(nextNum!=null && nextNum!=0) {
                        game.currentState.add(nextNum)

                        TambolaSharedPreferencesManager.put(
                            game,
                            TambolaConstants.TAMBOLA_GAME_SHAREDPREF_KEY
                        )

                        //Highlight the number cell
                        var numcell = findViewById<TextView>(nextNum)
                        numcell.setBackgroundColor(getColor(R.color.colorAccent))

                        currentNumberText.text = nextNum.toString()
                        lastFiveNumbers.text =
                            game.currentState.filter { num -> num != 0 }.toList().takeLast(5)
                                .toString()
                    }

                }

            }

            var intentFilter = IntentFilter(TambolaConstants.UPDATE_GAME_STATUS_INTENT)


            LocalBroadcastManager.getInstance(this).registerReceiver(myTicketViewBroadcastReceiver,intentFilter)

        }
    }


    override fun onStop() {
        super.onStop()
        unregisterReceiver(myTicketViewBroadcastReceiver)
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