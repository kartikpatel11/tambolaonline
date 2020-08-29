package com.tambolaonline.adapters

import android.content.Context
import android.os.Build
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.LinearLayout
import android.widget.TextView
import androidx.annotation.RequiresApi
import androidx.core.view.isNotEmpty
import androidx.recyclerview.widget.RecyclerView
import com.tambolaonline.data.AppMode
import com.tambolaonline.data.Participant
import com.tambolaonline.main.R
import com.tambolaonline.util.TambolaTicketGenerator

class ParticipantTicketRecyclerViewAdapter(val participants: ArrayList<Participant>, var currentState: ArrayList<Int>, val mContext:Context): RecyclerView.Adapter<ParticipantTicketRecyclerViewAdapter.ViewHolder>()  {

    class ViewHolder(ticketView : View):RecyclerView.ViewHolder(ticketView){
        var name = ticketView.findViewById<TextView>(R.id.participant_name)
        var ticketlayout = ticketView.findViewById<LinearLayout>(R.id.participant_ticket_layout)

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent?.context).inflate(R.layout.participant_ticket_layout, parent, false)
        return ViewHolder(view)
    }

    override fun getItemCount(): Int {
        return participants.size
    }

    @RequiresApi(Build.VERSION_CODES.M)
    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.name.text =   participants[position].name

        if(holder.ticketlayout.isNotEmpty()) {
            holder.ticketlayout.removeAllViewsInLayout()
        }
        holder.ticketlayout.addView(TambolaTicketGenerator.createTicketLayout(participants[position].ticket, currentState, AppMode.HOST, mContext))
    }


}