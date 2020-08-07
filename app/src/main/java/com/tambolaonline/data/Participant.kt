package com.tambolaonline.data

import com.tambolaonline.variations.VariationTypes

data class Participant(val participantID:Int,
                       val phone: String,
                       val name:String,
                       val ticket:Array<IntArray>,
                       val prize:HashSet<VariationTypes> = hashSetOf()) {

    override fun toString(): String {
        return "$name ($phone)"
    }
}

