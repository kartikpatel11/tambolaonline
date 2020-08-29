package com.tambolaonline.adapters

import android.content.Context
import android.content.Intent
import android.graphics.Color
import android.os.Build
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.annotation.RequiresApi
import androidx.core.content.ContextCompat.startActivity
import androidx.recyclerview.widget.RecyclerView
import com.github.akashandroid90.imageletter.MaterialLetterIcon
import com.tambolaonline.data.ActiveGames
import com.tambolaonline.data.Host
import com.tambolaonline.main.R
import com.tambolaonline.main.TambolaMyTicketView
import kotlin.random.Random


class MyActiveGamesRecyclerAdapter(
    val activegames: List<ActiveGames>,
    val host: Host,
    val mContext: Context
) : RecyclerView.Adapter<MyActiveGamesRecyclerAdapter.ViewHolder>()  {

    class ViewHolder(view: View):RecyclerView.ViewHolder(view) {
        var gameid = view.findViewById<TextView>(R.id.txt_gameid)
        var initialsImg = view.findViewById<MaterialLetterIcon>(R.id.img_mygames)
        var hostname = view.findViewById<TextView>(R.id.txt_gamehostname)
        var participants = view.findViewById<TextView>(R.id.txt_numberofparticipants)
        var prizes = view.findViewById<TextView>(R.id.txt_numberofvariations)

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(
            R.layout.myactivegames_row_item,
            parent,
            false
        )

        return MyActiveGamesRecyclerAdapter.ViewHolder(view)


    }

    @RequiresApi(Build.VERSION_CODES.M)
    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.initialsImg.setLetter(activegames[position].host.nickname.substring(0, 1))

        holder.gameid.text = activegames[position].gameid

        val color: Int = Color.argb(
            255, Random.nextInt(256), Random.nextInt(256), Random.nextInt(
                256
            )
        )
        holder.initialsImg.setShapeColor(color)
        
        if(host.hostPhNo!=activegames[position].host.hostPhNo) {
            holder.hostname.text = "Hosted By " + activegames[position].host.nickname
        }
        else
        {
            holder.hostname.text = "Hosted By You"
            holder.hostname.setTextColor(mContext.getColor(R.color.colorAccent))
        }
        holder.participants.text = activegames[position].numberOfParticipants.toString() + " Participants"
        holder.prizes.text = activegames[position].numberOfVariations.toString() + " Prizes"

        //Set onclick listener
        holder.itemView.setOnClickListener{
            val intent = Intent(mContext, TambolaMyTicketView::class.java)
            intent.putExtra("GAME_ID", activegames[position].gameid)
            mContext.startActivity(intent)
        }

    }

    override fun getItemCount(): Int {
        return activegames.size
    }
}