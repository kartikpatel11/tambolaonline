package com.tambolaonline.variations

class TambolaSecondRowVariation: IGameVariation {
    override fun applyVariation(ticket: Array<IntArray>, currentState: List<Int>): Boolean {
        if(currentState.containsAll(ticket[1].asList()))
            return true
        return false
    }
}