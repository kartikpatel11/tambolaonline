package com.tambolaonline.data

import com.tambolaonline.variations.VariationTypes

data class Game (var gameID: Int, val participants : ArrayList<Participants>, val variations: HashMap<VariationTypes, Boolean>, val currentState:ArrayList<Int>)

fun Game.notDone(): Boolean {
    val  completedVariations = variations.values
    return completedVariations.contains(false)

}
