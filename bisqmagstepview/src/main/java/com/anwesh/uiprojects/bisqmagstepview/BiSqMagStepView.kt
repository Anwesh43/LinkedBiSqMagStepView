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

val nodes : Int = 5
val lines : Int = 4
val parts : Int = 2
val scGap : Float = 0.1f / parts
val scDiv : Double = 1.0 / parts
val SIZE_FACTOR : Int = 3
val STRKE_FACTOR : Int = 80
val FORE_GROUND_COLOR : Int = Color.parseColor("#283593")

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
}
