package com.tambolaonline.data

import com.tambolaonline.variations.VariationTypes

data class Participant(val participantID:Int,
                       val phno: String,
                         val name:String,
                         val ticket:Array<IntArray>,
                         val prize:HashSet<VariationTypes> = hashSetOf())