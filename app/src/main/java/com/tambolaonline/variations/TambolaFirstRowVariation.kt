package com.tambolaonline.variations

import com.tambolaonline.util.TambolaTicketGenerator
import java.util.function.Predicate

class TambolaFirstRowVariation : IGameVariation {
    override fun applyVariation(ticket: Array<IntArray>, currentState: List<Int>): Boolean {
        if(ticket[0].asList().containsAll(currentState))
            return true
        return false
    }

}



