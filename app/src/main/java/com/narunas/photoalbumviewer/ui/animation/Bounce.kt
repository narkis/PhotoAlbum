package com.narunas.photoalbumviewer.ui.animation

import android.view.animation.Interpolator

class Bounce: Interpolator {

    var amplitude: Double = 0.125
    var frequency: Double = 7.0

    override fun getInterpolation(t: Float): Float {
        return (-1 * Math.pow(Math.E, -t/ amplitude) * Math.cos(frequency * t) + 1).toFloat()
    }

}