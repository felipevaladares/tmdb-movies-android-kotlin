package com.arctouch.codechallenge.core.extensions

import kotlinx.coroutines.runBlocking

fun runTesting(f: suspend () -> Unit) = runBlocking {
    f()
    Unit
}