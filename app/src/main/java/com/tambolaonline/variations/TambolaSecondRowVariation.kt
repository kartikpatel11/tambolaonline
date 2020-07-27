package com.tambolaonline.variations

class TambolaSecondRowVariation: IGameVariation {
    override fun applyVariation(ticket: Array<IntArray>, currentState: List<Int>): Boolean {
        if(ticket[1].asList().containsAll(currentState))
            return true
        return false
    }
}