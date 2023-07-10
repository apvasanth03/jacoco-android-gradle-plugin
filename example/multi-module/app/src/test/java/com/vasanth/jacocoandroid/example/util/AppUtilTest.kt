package com.vasanth.jacocoandroid.example.util

import org.junit.Assert.assertEquals
import org.junit.Before
import org.junit.Test

class AppUtilTest {

    private lateinit var appUtil: AppUtil

    @Before
    fun setUp() {
        appUtil = AppUtil()
    }

    @Test
    fun testSum() {
        // Given
        val a = 10
        val b = 20

        // When
        val result = appUtil.sum(a = a, b = b)

        // Then
        val expectedResult = 30
        assertEquals(expectedResult, result)
    }
}