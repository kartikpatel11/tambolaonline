package com.tambolaonline.data

import com.tambolaonline.variations.VariationTypes
import java.util.*
import kotlin.collections.ArrayList
import kotlin.collections.HashMap

data class Game(var gameID: UUID = UUID.randomUUID(),
                val participants: ArrayList<Participant> = ArrayList<Participant>(),
                val variations: HashMap<VariationTypes, Boolean> = HashMap<VariationTypes,Boolean>(),
                val currentState:ArrayList<Int> =  arrayListOf<Int>(0))


fun Game.notDone(): Boolean {
    //IF all numbers are exhausted then we can mark game as done
    if(currentState.size == 91) {
        return false
    }

    //Check if any variation is pending. If so, then game is not yet over
    val  completedVariations = variations.values
    return completedVariations.contains(false)

}
