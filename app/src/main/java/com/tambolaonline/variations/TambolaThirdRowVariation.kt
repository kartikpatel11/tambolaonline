package com.tambolaonline.variations

class TambolaThirdRowVariation :IGameVariation{
    override fun applyVariation(ticket: Array<IntArray>, currentState: List<Int>): Boolean {
        if(currentState.containsAll(ticket[2].asList()))
            return true
        return false
    }
}