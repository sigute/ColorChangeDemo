package com.github.sigute.colorchangedemo

import android.graphics.Color
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

class RandomColorViewModel(private val repository: RandomColorRepository) : ViewModel() {

    val randomColorLiveData = MutableLiveData<Pair<Int, String>>()

    fun colorTapped() {
        val color = repository.getColor()
        randomColorLiveData.value = Pair(color, colorToHex(color))
    }

    //https://stackoverflow.com/a/46457464/14048539
    private fun colorToHex(color: Int): String {
        val alpha = Color.alpha(color)
        val blue = Color.blue(color)
        val green = Color.green(color)
        val red = Color.red(color)

        val alphaHex = to00Hex(alpha)
        val blueHex = to00Hex(blue)
        val greenHex = to00Hex(green)
        val redHex = to00Hex(red)

        // hexBinary value: aabbggrr
        val str = StringBuilder("#")
        str.append(alphaHex)
        str.append(blueHex)
        str.append(greenHex)
        str.append(redHex)
        return str.toString()
    }

    private fun to00Hex(value: Int): String {
        val hex = "00" + Integer.toHexString(value)
        return hex.substring(hex.length - 2, hex.length)
    }
}
