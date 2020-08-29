package com.tambolaonline.util

import android.content.Context
import android.os.Build
import android.util.Log
import android.util.TypedValue
import android.view.Gravity
import android.widget.TableLayout
import android.widget.TableRow
import android.widget.TextView
import androidx.annotation.RequiresApi
import com.tambolaonline.data.AppMode
import com.tambolaonline.main.R
import kotlin.collections.ArrayList
import kotlin.random.Random

class TambolaTicketGenerator {

    companion object {
        private val TICKET_ROW = 3
        private val TICKET_COL = 9
        private val NUMBERS_TO_GENERATE =15

        val TAG = "TambolaTicketGen::"

        fun generateTicket(): Array<IntArray> {
            val ticket = Array(TICKET_ROW) { IntArray(TICKET_COL) }

            val randomValues: List<Int> = getRandomValues()

            var ticketMap: MutableMap<Int, MutableList<Int>> =
                randomValues.groupByTo(mutableMapOf(), { Math.abs(it / 10) })



            for ((key, value) in ticketMap) {


                if (value.size == 1) {
                    var rowPos = Random.nextInt(0, TICKET_ROW - 1)
                    value.add(rowPos, 0)
                }
                if (value.size == 2) {
                    var rowPos = Random.nextInt(0, TICKET_ROW)
                    value.add(rowPos, 0)
                }

                for (i in 0..2)
                    ticket[i][key] = value.get(i)
            }


            Log.i(TAG, ticket.contentDeepToString())

            return ticket


        }


        private fun getRandomValues(): List<Int> {
            val num: ArrayList<Int> = ArrayList<Int>()
            var LOWER_RANGE = 1
            var UPPER_RANGE = 9

            for (i in 0..8) {
                var s = mutableSetOf<Int>()
                while (s.size < 3) {
                    s.add((LOWER_RANGE..UPPER_RANGE).random())
                }
                num.addAll(s)
                LOWER_RANGE += 10
                UPPER_RANGE += 10
            }
            num.shuffle()

            return num.take(NUMBERS_TO_GENERATE).sorted()


        }

        /**
         * There are two use case of ticket layout
         * APP_MODE.HOST : Host want to see as game progresses, what all numbers are selected for every participant,
         * App_MODE.PARTICIPANT : Participant want to view ticket and click on cell to have it highlighted
         */
        @RequiresApi(Build.VERSION_CODES.M)
        fun createTicketLayout(ticket: Array<IntArray>, currentState: ArrayList<Int>, appMode: AppMode, mContext: Context): TableLayout {

            var table = TableLayout(mContext)
            table.setShrinkAllColumns(true);
            table.setStretchAllColumns(true);


            var params : TableLayout.LayoutParams = TableLayout.LayoutParams(
                TableLayout.LayoutParams.MATCH_PARENT, // This will define text view width
                TableLayout.LayoutParams.MATCH_PARENT // This will define text view height
            )

            // Add margin to the text view
            params.setMargins(15,15,15,15)

            table.layoutParams = params

            for(i in 0..2) {
                var row = TableRow(mContext)
                for(j in 0..8) {
                    var txt = TextView(mContext)
                    var cellnum: Int = ticket[i][j]
                    txt.text = (cellnum.toString())
                    txt.setTextSize(TypedValue.COMPLEX_UNIT_SP, 20F)
                    txt.setBackgroundResource(R.drawable.ticket_table_cell)
                    txt.setTextColor(mContext.getColor(R.color.black))
                    txt.setGravity(Gravity.CENTER)
                    row.addView(txt)

                    //APPMODE_HOST: Change color of cell if number already called out
                    if(appMode == AppMode.HOST && cellnum!=0 && currentState.contains(cellnum)) {
                        txt.setBackgroundColor(mContext.getColor(R.color.colorAccent))
                    }
                    //APPMODE_PARTICIPANT: Put onclick listener on cell to highlight cell
                    if(appMode == AppMode.PARTICIPANT) {
                        txt.setOnClickListener{
                            txt.setBackgroundColor(mContext.getColor(R.color.colorAccent))
                        }
                    }
                }
                table.addView(row)
            }

            return table

        }

    }
}

/*fun main(args:Array<String>)
{
    TambolaTicketGenerator().generateTicket()
}*/