package com.anwesh.uiprojects.bisqmagstepview

/**
 * Created by anweshmishra on 15/11/18.
 */

import android.view.View
import android.view.MotionEvent
import android.graphics.Canvas
import android.graphics.Paint
import android.graphics.Color
import android.app.Activity
import android.content.Context
import android.util.Log

val nodes : Int = 5
val lines : Int = 4
val parts : Int = 2
val scGap : Float = 0.1f / parts
val scDiv : Double = 1.0 / parts
val SIZE_FACTOR : Int = 3
val STRKE_FACTOR : Int = 80
val FORE_GROUND_COLOR : Int = Color.parseColor("#283593")

fun Int.getInverse() : Float = 1f / this

fun Float.divideScale(i : Int, n : Int) : Float = Math.min(n.getInverse(), Math.max(0f, this - i * n.getInverse())) * n

fun Float.getScaleFactor() : Float = Math.floor(this / scDiv).toFloat()

fun Int.getMirror(k : Float) : Float = getInverse() * (1 - k) + k

fun Float.updateScale(dir : Float, a : Int) : Float = scGap * dir * a.getMirror(getScaleFactor())

class BiSqMagStepView(ctx : Context) : View(ctx) {

    private val paint : Paint = Paint(Paint.ANTI_ALIAS_FLAG)

    override fun onDraw(canvas : Canvas) {

    }

    override fun onTouchEvent(event : MotionEvent) : Boolean {
        when (event.action) {
            MotionEvent.ACTION_DOWN -> {

            }
        }
        return true
    }

    data class State(var scale : Float = 0f, var dir : Float = 0f, var prevScale : Float = 0f) {

        fun update(cb : (Float) -> Unit) {
            val k : Float = scale.updateScale(dir, lines)
            scale += k
            Log.d("update scale is ", "$k")
            if (Math.abs(scale - prevScale) > 1) {
                scale = prevScale + dir
                dir = 0f
                prevScale = scale
                cb(prevScale)
            }
        }

        fun startUpdating(cb : () -> Unit) {
            if (dir == 0f) {
                dir = 1f - 2 * prevScale
                cb()
            }
        }
    }
}
