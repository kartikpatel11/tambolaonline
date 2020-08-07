package com.tambolaonline.util

import android.os.Build
import android.util.Log
import com.tambolaonline.variations.TambolaFirstRowVariation
import java.util.*
import java.util.stream.Collectors
import kotlin.collections.ArrayList
import kotlin.collections.HashSet
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

    }
}

/*fun main(args:Array<String>)
{
    TambolaTicketGenerator().generateTicket()
}*/