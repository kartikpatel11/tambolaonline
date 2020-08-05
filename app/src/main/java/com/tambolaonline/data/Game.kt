package com.tambolaonline.data

import com.tambolaonline.variations.VariationTypes
import java.util.*
import kotlin.collections.ArrayList
import kotlin.collections.HashMap

data class Game(var gameID: UUID = UUID.randomUUID() , val participants: ArrayList<Participants> = ArrayList<Participants>(), val variations: HashMap<VariationTypes, Boolean> = HashMap<VariationTypes,Boolean>(), val currentState:HashSet<Int> =  hashSetOf<Int>(0))


fun Game.notDone(): Boolean {
    val  completedVariations = variations.values
    return completedVariations.contains(false)

}
