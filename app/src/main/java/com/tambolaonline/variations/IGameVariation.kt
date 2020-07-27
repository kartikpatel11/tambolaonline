package com.tambolaonline.variations

import java.util.function.Predicate

interface IGameVariation {
    fun applyVariation(ticket:Array<IntArray>, currentState:List<Int>):Boolean
}