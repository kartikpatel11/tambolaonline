package com.tambolaonline.variations

class TambolaThirdRowVariation :IGameVariation{
    override fun applyVariation(ticket: Array<IntArray>, currentState: List<Int>): Boolean {

        var thirdrowHash = HashSet(ticket[2].asList())
        if(thirdrowHash.size > 1 || !(thirdrowHash.size ==1 && thirdrowHash.contains(0))) {
            if (currentState.containsAll(ticket[2].asList()))
                return true
        }
        return false
    }
}