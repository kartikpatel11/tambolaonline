package com.tambolaonline.variations

class TambolaThirdRowVariation :IGameVariation{
    override fun applyVariation(ticket: Array<IntArray>, currentState: List<Int>): Boolean {
        if(ticket[2].asList().containsAll(currentState))
            return true
        return false
    }
}