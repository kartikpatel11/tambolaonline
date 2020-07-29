package com.tambolaonline.variations

import junit.framework.Assert.assertFalse
import junit.framework.Assert.assertTrue
import org.junit.Test

//@RunWith(MockitoJUnitRunner::class.java)
public class TambolaFullHouseVariationTest {

    var tambolaFullHouseVariation = TambolaFullHouseVariation()

    @Test
    fun testVariation_false() {
        var ticket= arrayOf(
            arrayOf(0,0,25,37,0,0,66,78,84).toIntArray(),
            arrayOf(9,17,0,0,0,0,69,78,0).toIntArray(),
            arrayOf(3,14,0,0,45,0,0,76,80).toIntArray()
        )
        var currentState = listOf<Int>(1,5,7,9,14,22,60,49,45,76,82)
        assertFalse(tambolaFullHouseVariation.applyVariation(ticket,currentState))
    }

    @Test
    fun testVariation_true() {
        var ticket= arrayOf(
            arrayOf(0,0,25,37,0,0,66,74,84).toIntArray(),
            arrayOf(9,17,0,0,0,0,69,78,0).toIntArray(),
            arrayOf(3,14,0,0,45,0,0,76,80).toIntArray()
        )
        //need to add 0 explicitly as array of Int contains by default value of 0
        var currentState = listOf<Int>(0,66,1,3,78,7,17,69,84,9,66,14,22,60,49,45,76,80,25,37,74)

        assertTrue(tambolaFullHouseVariation.applyVariation(ticket,currentState))

    }


}