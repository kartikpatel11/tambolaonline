package com.tambolaonline.main

import android.content.Context
import android.os.Build
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.annotation.RequiresApi
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.tambolaonline.adapters.ParticipantTicketRecyclerViewAdapter
import com.tambolaonline.data.Game
import com.tambolaonline.data.Participants
import com.tambolaonline.util.TambolaConstants
import com.tambolaonline.util.TambolaSharedPreferencesManager
import com.tambolaonline.util.TambolaTicketGenerator

// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"

/**
 * A simple [Fragment] subclass.
 * Use the [TambolaParticipantTicketViewFragment.newInstance] factory method to
 * create an instance of this fragment.
 */
class TambolaParticipantTicketViewFragment : Fragment() {
    // TODO: Rename and change types of parameters
    private var param1: String? = null
    private var param2: String? = null
    lateinit var mContext: Context
    lateinit var game: Game


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
        return inflater.inflate(R.layout.fragment_tambola_participant_ticket_view, container, false)
    }

    @RequiresApi(Build.VERSION_CODES.M)
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        //Get Game object from shared preferences
        TambolaSharedPreferencesManager.with(activity!!.application)
        game = TambolaSharedPreferencesManager.get<Game>(TambolaConstants.TAMBOLA_GAME_SHAREDPREF_KEY)!!

        //Attach adapter to recycler view
        var ticketRecyclerView = view.findViewById<RecyclerView>(R.id.participant_ticket_recycler)
        ticketRecyclerView.layoutManager = LinearLayoutManager(mContext,LinearLayoutManager.VERTICAL,false)

        //        Create an arraylist
        val dataList = ArrayList<Participants>()
        dataList.add(Participants(1,"9820742767","Kartik",TambolaTicketGenerator.generateTicket()))
        dataList.add(Participants(2,"9820742766","Ruchi",TambolaTicketGenerator.generateTicket()))
        dataList.add(Participants(3,"9820742765","Nisha",TambolaTicketGenerator.generateTicket()))
        dataList.add(Participants(4,"9820742764","Me",TambolaTicketGenerator.generateTicket()))
//        pass the values to RvAdapter
        val participantTicketRecyclerViewAdapter = ParticipantTicketRecyclerViewAdapter(dataList, game.currentState, mContext)
//        set the recyclerView to the adapter
        ticketRecyclerView.adapter = participantTicketRecyclerViewAdapter;

    }

    override fun onAttach(context: Context) {
        super.onAttach(context)
        mContext = context

    }

    override fun onHiddenChanged(hidden: Boolean) {

    }
    companion object {
        /**
         * Use this factory method to create a new instance of
         * this fragment using the provided parameters.
         *
         * @param param1 Parameter 1.
         * @param param2 Parameter 2.
         * @return A new instance of fragment TambolaParticipantTicketViewFragment.
         */
        // TODO: Rename and change types and number of parameters
        @JvmStatic
        fun newInstance(param1: String, param2: String) =
            TambolaParticipantTicketViewFragment().apply {
                arguments = Bundle().apply {
                    putString(ARG_PARAM1, param1)
                    putString(ARG_PARAM2, param2)
                }
            }
    }


}