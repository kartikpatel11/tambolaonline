package com.tambolaonline.variations

class TambolaFullHouseVariation: IGameVariation {
    override fun applyVariation(ticket: Array<IntArray>, currentState: List<Int>): Boolean {

        for (i in 0..2) {
            if (!ticket[i].asList().containsAll(currentState))
                return false
        }
        return true;
    }
}