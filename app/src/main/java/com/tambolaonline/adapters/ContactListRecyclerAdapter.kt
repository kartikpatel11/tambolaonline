package com.tambolaonline.adapters

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.*
import androidx.recyclerview.widget.RecyclerView
import com.tambolaonline.data.Participant
import com.tambolaonline.main.R


class ContactListRecyclerAdapter (val contacts: ArrayList<Participant>, val participants:ArrayList<Participant>, val mContext: Context): RecyclerView.Adapter<ContactListRecyclerAdapter.ViewHolder>() , Filterable{

    var contactListFiltered = ArrayList<Participant>()

    init {
        contactListFiltered = contacts
    }

    class ViewHolder(contactListItemView : View) : RecyclerView.ViewHolder(contactListItemView){
        var contactName = contactListItemView.findViewById<TextView>(R.id.txt_contactname)
        var contactPhone= contactListItemView.findViewById<TextView>(R.id.txt_contactphone)
        var incrBtn = contactListItemView.findViewById<ImageButton>(R.id.btn_incr)
        var decrBtn = contactListItemView.findViewById<ImageButton>(R.id.btn_decr)
        var ticketcntr= contactListItemView.findViewById<TextView>(R.id.txt_ticketcounter)
    }




    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent?.context).inflate(R.layout.contactlist_row_item, parent, false)
        return ContactListRecyclerAdapter.ViewHolder(view)
    }

    override fun getItemCount(): Int {
       return contactListFiltered.size
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {

        holder.contactName.text = contactListFiltered[position].name
        holder.contactPhone.text= contactListFiltered[position].phone

        holder.incrBtn.setOnClickListener{

            val value = Integer.parseInt(holder.ticketcntr.text.toString()) + 1

            if(value <= 5) {
                holder.ticketcntr.text = Integer.toString(value)

                // Add it to participant list
                participants.add(contactListFiltered[position])

                notifyDataSetChanged()
            }
            else {
                var toast = Toast.makeText(mContext, "Maximum 5 tickets allowed per person", Toast.LENGTH_LONG)
                toast.show()
            }

        }

        holder.decrBtn.setOnClickListener{

            val value = Integer.parseInt(holder.ticketcntr.text.toString()) - 1

            if(value>= 0) {
                holder.ticketcntr.text = Integer.toString(value)

                //Remove is value is 0
                if(value ==0) {
                    participants.remove(contactListFiltered[position])
                }
                notifyDataSetChanged()
            }
            else {
                var toast = Toast.makeText(mContext, "Can't have less than 0 tickets", Toast.LENGTH_LONG)
                toast.show()
            }

        }
    }

    override fun getFilter(): Filter {
        return object : Filter() {
            override fun performFiltering(charSequence: CharSequence): FilterResults? {
                val charString = charSequence.toString()
                if (charString.isEmpty()) {
                    contactListFiltered = contacts
                } else {
                    val filteredList: ArrayList<Participant> = ArrayList()
                    for (row in contacts) {

                        // name match condition. this might differ depending on your requirement
                        // here we are looking for name or phone number match
                        if (row.name.toLowerCase()
                                .contains(charString.toLowerCase()) || row.phone
                                .contains(charSequence)
                        ) {
                            filteredList.add(row)
                        }
                    }
                    contactListFiltered = filteredList
                }
                val filterResults = FilterResults()
                filterResults.values = contactListFiltered
                return filterResults
            }

            override fun publishResults(
                charSequence: CharSequence?,
                filterResults: FilterResults
            ) {
                contactListFiltered = filterResults.values as ArrayList<Participant>
                notifyDataSetChanged()
            }
        }
    }
}

