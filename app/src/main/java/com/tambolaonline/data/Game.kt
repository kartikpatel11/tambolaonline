package com.tambolaonline.data

import com.tambolaonline.variations.IGameVariation

data class Game (var gameID: Int, val participants : Map<String, Array<IntArray>>, val variations: List<IGameVariation>, val currentState:List<Int>)
