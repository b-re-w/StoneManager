package io.github.irack.stonemanager

import io.github.irack.stonemanager.util.Greeting
import kotlin.test.Test
import kotlin.test.assertTrue

class DesktopGreetingTest {

    @Test
    fun testExample() {
        assertTrue(Greeting().greet().contains("Desktop"), "Check desktop is mentioned")
    }
}
