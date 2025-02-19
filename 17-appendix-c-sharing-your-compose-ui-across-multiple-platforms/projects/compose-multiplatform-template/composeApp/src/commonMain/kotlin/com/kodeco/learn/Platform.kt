package com.kodeco.learn

interface Platform {
    val name: String
}

expect fun getPlatform(): Platform