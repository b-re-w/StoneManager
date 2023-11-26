package io.github.irack.stonemanager

interface Platform {
    val name: String
}

expect fun getPlatform(): Platform
