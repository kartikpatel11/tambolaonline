package com.tambolaonline.adapters

import android.content.Context
import android.os.Build
import android.view.Gravity
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.LinearLayout
import android.widget.TableLayout
import android.widget.TableRow
import android.widget.TextView
import androidx.annotation.RequiresApi
import androidx.recyclerview.widget.RecyclerView
import com.tambolaonline.data.Participant
import com.tambolaonline.main.R
import com.tambolaonline.util.TambolaTicketGenerator

class ParticipantTicketRecyclerViewAdapter(val participants: ArrayList<Participant>, val currentState: ArrayList<Int>, val mContext:Context): RecyclerView.Adapter<ParticipantTicketRecyclerViewAdapter.ViewHolder>()  {

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
        holder.ticketlayout.addView(createTicket())
    }

    @RequiresApi(Build.VERSION_CODES.M)
    private fun createTicket(): TableLayout {
        var ticket = TambolaTicketGenerator.generateTicket()


        var table = TableLayout(mContext)
        table.setShrinkAllColumns(true);
        table.setStretchAllColumns(true);


        var params : TableLayout.LayoutParams = TableLayout.LayoutParams(
            TableLayout.LayoutParams.MATCH_PARENT, // This will define text view width
            TableLayout.LayoutParams.MATCH_PARENT // This will define text view height
        )

        // Add margin to the text view
        params.setMargins(10,10,10,10)

        table.layoutParams = params

        for(i in 0..2) {
            var row = TableRow(mContext)
            for(j in 0..8) {
                var txt = TextView(mContext)
                var cellnum: Int = ticket[i][j]
                txt.text = (cellnum.toString())
                //txt.setId(cellnum)
                txt.setBackgroundResource(R.drawable.ticket_table_cell)
                txt.setTextColor(mContext.getColor(R.color.black))
                txt.setGravity(Gravity.CENTER)
                row.addView(txt)

                //Change color of cell if number already called out
                if(cellnum!=0 && currentState.contains(cellnum)) {
                    txt.setBackgroundColor(mContext.getColor(R.color.colorAccent))
                }
            }
            table.addView(row)
        }

        return table

    }
}