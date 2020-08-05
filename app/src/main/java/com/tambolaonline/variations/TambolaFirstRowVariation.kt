package com.tambolaonline.variations

import com.tambolaonline.util.TambolaTicketGenerator
import java.util.function.Predicate

class TambolaFirstRowVariation : IGameVariation {
    override fun applyVariation(ticket: Array<IntArray>, currentState: List<Int>): Boolean {

        var firstrowHash = HashSet(ticket[0].asList())
        if(firstrowHash.size > 1 || !(firstrowHash.size ==1 && firstrowHash.contains(0))) {
            if (currentState.containsAll(ticket[0].asList()))
                return true
        }
        return false
    }

}



