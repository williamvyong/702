package com.williamv.debtmake.data

import javax.inject.Inject

class GreetingServiceImpl @Inject constructor(): GreetingService {
    override fun greet(): String = "Hello from Hilt!"
}
