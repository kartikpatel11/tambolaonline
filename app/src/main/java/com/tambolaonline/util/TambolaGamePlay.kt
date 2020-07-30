package com.tambolaonline.util

import com.tambolaonline.data.Game
import com.tambolaonline.data.Participants
import com.tambolaonline.data.notDone
import com.tambolaonline.variations.VariationTypes

class TambolaGamePlay {
    val LOWER_RANGE = 1
    val UPPER_RANGE = 90

    fun startGame (game: Game) {
        val participants = game.participants
        val variations = game.variations
        val currState = game.currentState

        while (game.notDone()) {
            //Generate Random number
            var currNumber = 0
            do {
                currNumber = (LOWER_RANGE..UPPER_RANGE).random()
            } while (currState.contains(currNumber))
        
            currState.add(currNumber)
            //Evaluate all participants for prize winner
            for(participant in participants) {
                //go across all variations
                for((variationType, completed) in variations) {
                    if(!completed) {
                        //Check if variation is completed for particular participant
                        if(variationType.variationclass.applyVariation(participant.ticket,currState)) {
                                participant.prize.add(variationType)
                            //Update Game variation to signify that the particular variation is completed
                                print("Participant: ${participant.name} has won ${variationType.variationname}")
                                variations.put(variationType, true)
                        }
                    }
                }
            }
        }

    }


}
