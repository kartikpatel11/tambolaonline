package com.tambolaonline.util

import java.util.*
import kotlin.random.Random

class TambolaRandomTicketNumberGenerator {
    val numbersToGenerate =15

    val ticket_row = 3
    val ticket_col = 10
    fun generateRandomNumbersForTicket(): Unit {
        val ticket =  arrayOf<Array<Int>>()
        val randomValues = getRandomValues()

        print(randomValues)
        //TODO: Add numbers in array
        for(i in randomValues)
        {
            for(j in 0..9)
            {

            }
        }
    }

    private fun getRandomValues(): SortedSet<Int> {
        val num:MutableList<Int> = mutableListOf()
        var lower_range=0
        var upper_range=9

        for(i in 0..9)
        {
            num.addAll(List(3){ Random.nextInt(lower_range,upper_range)})
            lower_range+=10
            upper_range+=10
        }
        return num.distinct().take(15).toSortedSet()
    }

    fun main(args: Array<String>)
    {
        var obj:TambolaRandomTicketNumberGenerator = TambolaRandomTicketNumberGenerator()
        obj.generateRandomNumbersForTicket()
    }
}