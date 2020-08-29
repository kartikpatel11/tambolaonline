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
import com.tambolaonline.data.Game
import com.tambolaonline.data.Participant
import com.tambolaonline.main.R
import com.tambolaonline.util.TambolaTicketGenerator
import org.w3c.dom.Text

class MyTicketViewRecyclerAdapter(val game: Game, val mContext: Context) : RecyclerView.Adapter<MyTicketViewRecyclerAdapter.ViewHolder>() {

    class ViewHolder(myTicket: View):RecyclerView.ViewHolder(myTicket)
    {
        var ticketHeader = myTicket.findViewById<TextView>(R.id.txt_ticketheader)
        var myTicketLayout = myTicket.findViewById<LinearLayout>(R.id.myticket_layout)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyTicketViewRecyclerAdapter.ViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.myticketview_row_item, parent, false)
        return ViewHolder(view)
    }

    override fun getItemCount(): Int {
        return game.participants.size
    }

    @RequiresApi(Build.VERSION_CODES.M)
    override fun onBindViewHolder(holder: ViewHolder, position: Int) {

        holder.ticketHeader.text = game.participants[position].name

        if(holder.myTicketLayout.isNotEmpty()) {
            holder.myTicketLayout.removeAllViewsInLayout()
        }
        game.participants.forEach {
            var ticketTable =
                TambolaTicketGenerator.createTicketLayout(it.ticket, game.currentState, AppMode.PARTICIPANT, mContext)
            holder.myTicketLayout.addView(ticketTable)
        }


    }

}
