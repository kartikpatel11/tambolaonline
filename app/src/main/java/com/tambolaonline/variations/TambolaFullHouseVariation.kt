package com.tambolaonline.variations

class TambolaFullHouseVariation: IGameVariation {
    override fun applyVariation(ticket: Array<IntArray>, currentState: List<Int>): Boolean {

        for (i in 0..2) {
            if (!currentState.containsAll(ticket[i].asList()))
                return false
        }
        return true;
    }
}