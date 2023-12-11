package io.github.irack.stonemanager.`interface`.resource.util

interface Platform {
    val name: String
}

expect fun getPlatform(): Platform
