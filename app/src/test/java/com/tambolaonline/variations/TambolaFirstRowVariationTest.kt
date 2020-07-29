package com.tambolaonline.variations

import junit.framework.Assert.assertFalse
import junit.framework.Assert.assertTrue
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.Mockito
import org.mockito.junit.MockitoJUnitRunner

//@RunWith(MockitoJUnitRunner::class.java)
public class TambolaFirstRowVariationTest {


        @Test
        fun testVariation_false() {
            var ticket= arrayOf(
                arrayOf(3,14,0,0,45,0,0,76,80).toIntArray(),
                arrayOf(0,0,25,37,0,0,66,78,84).toIntArray(),
                arrayOf(9,17,0,0,0,0,69,78,0).toIntArray()
            )
            var currentState = listOf<Int>(1,5,7,9,14,22,60,49,45,76,82)

            var tambolaFirstRowVariation = TambolaFirstRowVariation()
            assertFalse(tambolaFirstRowVariation.applyVariation(ticket,currentState))

    }

    @Test
    fun testVariation_true() {
        var ticket= arrayOf(
            arrayOf(3,14,0,0,45,0,0,76,80).toIntArray(),
            arrayOf(0,0,25,37,0,0,66,78,84).toIntArray(),
            arrayOf(9,17,0,0,0,0,69,78,0).toIntArray()
        )
        //need to add 0 explicitly as array of Int contains by default value of 0
        var currentState = listOf<Int>(0,1,3,7,9,14,22,60,49,45,76,80)

        var tambolaFirstRowVariation = TambolaFirstRowVariation()
        assertTrue(tambolaFirstRowVariation.applyVariation(ticket,currentState))

    }

    @Test
    fun testVariation_FirstRowBlank_false() {
        var ticket= arrayOf(
            arrayOf(0,0,0,0,0,0,0,0,0).toIntArray(),
            arrayOf(0,0,25,37,0,0,66,78,84).toIntArray(),
            arrayOf(9,17,0,0,0,0,69,78,0).toIntArray()
        )
        //need to add 0 explicitly as array of Int contains by default value of 0
        var currentState = listOf<Int>(0,1,3,7,9,14,22,60,49,45,76,80)

        var tambolaFirstRowVariation = TambolaFirstRowVariation()
        assertFalse(tambolaFirstRowVariation.applyVariation(ticket,currentState))

    }
}