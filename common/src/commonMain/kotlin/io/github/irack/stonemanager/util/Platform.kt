package io.github.irack.stonemanager.util

interface Platform {
    val name: String
}

expect fun getPlatform(): Platform
