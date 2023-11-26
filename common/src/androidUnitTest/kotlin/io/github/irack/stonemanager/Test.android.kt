package io.github.irack.stonemanager

import io.github.irack.stonemanager.util.Greeting
import org.junit.Assert.assertTrue
import org.junit.Test

class AndroidGreetingTest {

    @Test
    fun testExample() {
        assertTrue("Check Android is mentioned", Greeting().greet().contains("Android"))
    }
}