package com.williamv.debtmake.data

interface GreetingService {
    fun greet(): String
}

class GreetingServiceImpl : GreetingService {
    override fun greet() = "Hello from Hilt!"
}
