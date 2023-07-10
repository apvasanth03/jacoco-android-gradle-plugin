package com.vasanth.jacocoandroid.library1.util

import org.junit.Assert.assertEquals
import org.junit.Before
import org.junit.Test

class Library1UtilTest {

    private lateinit var lib1Util: Library1Util

    @Before
    fun setUp() {
        lib1Util = Library1Util()
    }

    @Test
    fun testSum() {
        // Given
        val a = 10
        val b = 20

        // When
        val result = lib1Util.sum(a = a, b = b)

        // Then
        val expectedResult = 30
        assertEquals(expectedResult, result)
    }
}