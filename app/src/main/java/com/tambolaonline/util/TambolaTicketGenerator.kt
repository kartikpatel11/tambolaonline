package com.tambolaonline.util

import java.util.*
import java.util.stream.Collectors
import kotlin.collections.ArrayList
import kotlin.collections.HashSet
import kotlin.random.Random

class TambolaTicketGenerator {
    private val NUMBERS_TO_GENERATE =15

    private val TICKET_ROW = 3
    private val TICKET_COL = 9
    fun generateTicket(): Unit {
        val ticket =  Array(TICKET_ROW){IntArray(TICKET_COL)}
        val ticketMap = HashMap<Int,ArrayList<Int>>()

        val randomValues:List<Int> = getRandomValues()

        for(number in randomValues) {
            var key = Math.abs(number/10)
            if(ticketMap.get(key) == null)
                ticketMap.put(key,ArrayList<Int>(3))
            ticketMap.get(key)?.add(number)
        }

        for((key, value) in ticketMap) {


            if (value.size == 1) {
                var rowPos = Random.nextInt(0, TICKET_ROW-1)
                value.add(rowPos, 0)
            }
            if (value.size == 2) {
                var rowPos = Random.nextInt(0, TICKET_ROW)
                value.add(rowPos, 0)
            }

            for (i in 0..2)
                ticket[i][key]=value.get(i)
        }


        for(i in ticket.indices) {
            println(ticket[i].contentToString())
        }
    }


    private fun getRandomValues(): List<Int> {
        val num:ArrayList<Int> = ArrayList<Int>()
        var LOWER_RANGE=1
        var UPPER_RANGE=9

        for(i in 0..8)
        {
            var s = mutableSetOf<Int>()
            while (s.size < 3) { s.add((LOWER_RANGE..UPPER_RANGE).random()) }
            num.addAll(s)
            LOWER_RANGE+=10
            UPPER_RANGE+=10
        }
        num.shuffle()

        return num.take(NUMBERS_TO_GENERATE).sorted()


    }


}

fun main(args:Array<String>)
{
    TambolaTicketGenerator().generateTicket()
}