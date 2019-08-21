package com.arctouch.codechallenge.core.extensions

import android.support.annotation.LayoutRes
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup

fun ViewGroup.inflate(@LayoutRes res: Int, attachToRoot: Boolean = false): View {
    return LayoutInflater.from(this.context).inflate(res, this, attachToRoot)
}
