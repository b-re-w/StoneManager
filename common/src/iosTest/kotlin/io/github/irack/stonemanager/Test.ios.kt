package io.github.irack.stonemanager

import io.github.irack.stonemanager.util.Greeting
import kotlin.test.Test
import kotlin.test.assertTrue

class IosGreetingTest {

    @Test
    fun testExample() {
        assertTrue(Greeting().greet().contains("iOS"), "Check iOS is mentioned")
    }
}