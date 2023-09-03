package com.kodeco.findtime

interface Platform {
    val name: String
}

expect fun getPlatform(): Platform