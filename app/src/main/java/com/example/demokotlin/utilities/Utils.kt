package com.example.demokotlin.utilities

import java.util.*


fun getRandomNumber() : Int {

    val random = Random()
    return random.nextInt(999999)
}