package com.github.sigute.colorchangedemo

import com.github.lzyzsd.randomcolor.RandomColor

class RandomColorRepository {

    private var randomColor = RandomColor()

    fun getColor() = randomColor.randomColor()
}
