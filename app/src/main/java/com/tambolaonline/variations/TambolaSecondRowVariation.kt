package com.tambolaonline.variations

class TambolaSecondRowVariation: IGameVariation {
    override fun applyVariation(ticket: Array<IntArray>, currentState: List<Int>): Boolean {
        var secondrowHash = HashSet(ticket[1].asList())
        if(secondrowHash.size > 1 || !(secondrowHash.size ==1 && secondrowHash.contains(0))) {
           if (currentState.containsAll(ticket[1].asList()))
                return true
        }
        return false
    }
}