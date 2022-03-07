package com.raywenderlich.findtime

class Greeting {
    fun greeting(): String {
        return "Hello, ${Platform().platform}!"
    }
}