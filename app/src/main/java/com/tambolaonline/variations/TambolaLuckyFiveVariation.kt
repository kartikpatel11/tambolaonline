package com.tambolaonline.variations

import android.R.array
import java.util.*
import java.util.stream.Collectors


class TambolaLuckyFiveVariation : IGameVariation{
    override fun applyVariation(ticket: Array<IntArray>, currentState: List<Int>): Boolean {
       //convert 2d array to list
        var flatList = HashSet<Int>()
       for(i in 0..2) {
           flatList.addAll(ticket[i].asList())
       }
        flatList.retainAll(currentState)
        if(flatList.size >=6)
            return true
        return false
    }
}