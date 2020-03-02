package com.arctouch.codechallenge.core.utils

import androidx.test.espresso.idling.CountingIdlingResource

object EspressoIdlingResource {
    private const val RESOURCE = "GLOBAL"

    @JvmField
    val countIdlingResource = CountingIdlingResource(RESOURCE)

    fun increment() {
        countIdlingResource.increment()
    }

    fun decrement() {
        if (!countIdlingResource.isIdleNow) {
            countIdlingResource.decrement()
        }
    }
}