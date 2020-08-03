package com.tambolaonline.variations

enum class VariationTypes (val variationname: String, val variationclass:IGameVariation) {
    FIRST_ROW("First Row", TambolaFirstRowVariation()),
    SECOND_ROW("Second Row",TambolaSecondRowVariation()),
    THIRD_ROW("Third Row",TambolaThirdRowVariation()),
    //FOUR_CORNER("Four Corner".null),
    LUCKY_FIVE("Lucky Five", TambolaLuckyFiveVariation()),
    FULL_HOUSE("Full House", TambolaFullHouseVariation());

    companion object   {
        private val map = VariationTypes.values().associateBy { it.variationname }
        fun fromVariationName (varName: String) : VariationTypes? {
            return map[varName]
        }

    }
}

