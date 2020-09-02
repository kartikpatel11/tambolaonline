package com.tambolaonline.main

import android.content.Context
import android.os.Build
import android.os.Bundle
import android.util.Log
import android.view.Gravity
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.*
import androidx.annotation.RequiresApi
import androidx.fragment.app.Fragment
import com.google.gson.Gson
import com.tambolaonline.data.Game
import com.tambolaonline.data.notDone
import com.tambolaonline.services.ServiceBuilder
import com.tambolaonline.services.TambolaEndPoints
import com.tambolaonline.util.TambolaConstants
import com.tambolaonline.util.TambolaSharedPreferencesManager
import retrofit2.Call
import retrofit2.Response

// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"
private val TAG = "PlaygroundHomeFrg::"

/**
 * A simple [Fragment] subclass.
 * Use the [TambolaPlaygroundHomeFragment.newInstance] factory method to
 * create an instance of this fragment.
 */
class TambolaPlaygroundHomeFragment : Fragment() {
    // TODO: Rename and change types of parameters
    private var param1: String? = null
    private var param2: String? = null

    lateinit var game: Game
    lateinit var mContext: Context

    @RequiresApi(Build.VERSION_CODES.M)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            param1 = it.getString(ARG_PARAM1)
            param2 = it.getString(ARG_PARAM2)
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return  inflater.inflate(R.layout.fragment_tambola_playground_home, container, false)

       }

    @RequiresApi(Build.VERSION_CODES.M)
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)



        //Create tambola board to keep track of numbers that are called
        createTambolaBoard(view)

    }

    override fun onAttach(context: Context) {
        super.onAttach(context)
        mContext = context
    }


    companion object {
        /**
         * Use this factory method to create a new instance of
         * this fragment using the provided parameters.
         *
         * @param param1 Parameter 1.
         * @param param2 Parameter 2.
         * @return A new instance of fragment TambolaPlaygroundHomeFragment.
         */
        // TODO: Rename and change types and number of parameters
        @JvmStatic
        fun newInstance(param1: String, param2: String) =
            TambolaPlaygroundHomeFragment().apply {
                arguments = Bundle().apply {
                    putString(ARG_PARAM1, param1)
                    putString(ARG_PARAM2, param2)
                }
            }
    }

    @RequiresApi(Build.VERSION_CODES.M)
    private fun createTambolaBoard(view: View) {

        var boardLayout = view.findViewById<LinearLayout>(R.id.tambola_board_layout)

        var table = TableLayout(mContext)
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
            var row = TableRow(mContext)
            for(j in 1..10) {
                var txt = TextView(mContext)
                var cellnum: Int = (i*10)+j
                txt.text = (cellnum.toString())
                txt.setId(cellnum)
                txt.setBackgroundResource(R.drawable.ticket_table_cell)
                txt.setTextColor(mContext.getColor(R.color.black))
                txt.setGravity(Gravity.CENTER)
                row.addView(txt)
            }
            table.addView(row)
        }

        view.findViewById<ImageButton>(R.id.btn_generaterandnumber).setOnClickListener{nextRandomNumber(view)}
    }

    @RequiresApi(Build.VERSION_CODES.M)
    public fun nextRandomNumber(view: View) {

        //Get Game object from shared preferences
        this.activity?.application?.let { TambolaSharedPreferencesManager.with(it) }
        game = TambolaSharedPreferencesManager.get<Game>(TambolaConstants.TAMBOLA_GAME_SHAREDPREF_KEY)!!


        var gson = Gson()
        Log.i("JSON STRING:" ,gson.toJson(game, Game::class.java))

        var currState = game.currentState
        var currNumber: Int

        // Generate random number
        do {
            currNumber = (1..90).random()
        } while (currState.contains(currNumber))

        //Show number in Current Number Text
        var currNumberTxtBox = view.findViewById<TextView>(R.id.txt_currentnumber)
        currNumberTxtBox.text = currNumber.toString()

        //Add current number to list and add it to sharedpref
        currState.add(currNumber)
        TambolaSharedPreferencesManager.put(game,TambolaConstants.TAMBOLA_GAME_SHAREDPREF_KEY)

        // Has Anyone won
        hasAnyoneWon()

        //Show number in recent list of numbers
        var listofrecentnumsTxtBox = view.findViewById<TextView>(R.id.txt_listofnums)
        listofrecentnumsTxtBox.text = currState.filter{num -> num!=0}.toList().takeLast(5).toString()

        //Highlight the number cell
        var numcell = view.findViewById<TextView>(currNumber)
        numcell.setBackgroundColor(view.context.getColor(R.color.colorAccent))

        //Send detail to Server
        val request = ServiceBuilder.buildService(TambolaEndPoints::class.java)
        val call = request.storeGameStatus(game.gameid,currNumber)


        //Populate games as host
        call.enqueue(object: retrofit2.Callback<String> {
            override fun onFailure(call: Call<String>, t: Throwable) {
                Toast.makeText(mContext, "${t.message}", Toast.LENGTH_SHORT).show()
            }

            override fun onResponse(call: Call<String>, response: Response<String>) {
             //DO NOTHING
                Toast.makeText(mContext, "${response}", Toast.LENGTH_SHORT).show()
            }
        })

        //Disable button if game is complete
        if(!game.notDone()) {
            var randomNumberGenBtn = view.findViewById<ImageButton>(R.id.btn_generaterandnumber)
            randomNumberGenBtn.setClickable(false)
            randomNumberGenBtn.setImageResource(R.drawable.ic_baseline_play_circle_filled_24)
        }

    }

    private fun hasAnyoneWon(){

        val participants = game.participants
        val variations = game.variations
        val currState = game.currentState
        var msgTxt = ""

        for(participant in participants) {
            //go across all variations
            for((variationType, hasPrizeWon) in variations) {
                if(!hasPrizeWon) {
                    //Check if variation is completed for particular participant
                    if(variationType.variationclass.applyVariation(participant.ticket,currState)) {
                        participant.prize.add(variationType)
                        //Update Game variation to signify that the particular variation is completed

                        msgTxt = "$msgTxt \n${participant.name} has won ${variationType.variationname}"
                        Log.i(TAG,"Participant: ${participant.name} has won ${variationType.variationname}")
                        variations.put(variationType, true)

                        val toast = Toast.makeText(mContext, msgTxt, Toast.LENGTH_SHORT)
                        toast.show()

                    }
                }
            }

            //Store Game in sharedpref
            TambolaSharedPreferencesManager.put(game,TambolaConstants.TAMBOLA_GAME_SHAREDPREF_KEY)


        }

    }

}