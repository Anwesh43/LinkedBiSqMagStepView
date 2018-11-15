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
val STROKE_FACTOR : Int = 80
val FORE_GROUND_COLOR : Int = Color.parseColor("#283593")

fun Int.getInverse() : Float = 1f / this

fun Float.divideScale(i : Int, n : Int) : Float = Math.min(n.getInverse(), Math.max(0f, this - i * n.getInverse())) * n

fun Float.getScaleFactor() : Float = Math.floor(this / scDiv).toFloat()

fun Int.getMirror(k : Float) : Float = getInverse() * (1 - k) + k

fun Float.updateScale(dir : Float, a : Int) : Float = scGap * dir * a.getMirror(getScaleFactor())

fun Int.rFact() : Int = this % 2

fun Int.iFact() : Int = this / 2

fun Int.irFact() : Int = (rFact() + iFact()).rFact()

fun Canvas.drawBSMSNode(i : Int, scale : Float, paint : Paint) {
    val w : Float = width.toFloat()
    val h : Float = height.toFloat()
    val gap : Float = w / (nodes + 1)
    val sc1 : Float = scale.divideScale(0, 2)
    val sc2 : Float = scale.divideScale(1, 2)
    val size : Float = Math.min(w, h) / SIZE_FACTOR
    paint.strokeWidth = Math.min(w, h) / STROKE_FACTOR
    paint.color = FORE_GROUND_COLOR
    paint.strokeCap = Paint.Cap.ROUND
    save()
    translate(gap * (i + 1), h/2)
    rotate(90f * sc2)
    drawLine(0f, -size, 0f, size, paint)
    for (j in 0..(lines - 1)) {
        val sc : Float = sc1.divideScale(j, lines)
        save()
        scale(j.irFact().toFloat(), j.iFact().toFloat())
        drawLine(0f, size, size * sc, size, paint)
        restore()
    }
    restore()
}

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

    data class Animator(var view : View, var animated : Boolean = false) {

        fun start() {
            if (!animated) {
                animated = true
                view.postInvalidate()
            }
        }

        fun animate(cb : () -> Unit) {
            if (animated) {
                cb()
                try {
                    Thread.sleep(50)
                    view.invalidate()
                } catch(ex : Exception) {

                }
            }
        }

        fun stop() {
            if (animated) {
                animated = false
            }
        }
    }

    data class BSMSNode(var i : Int, val state : State = State()) {

        private var next : BSMSNode? = null

        private var prev : BSMSNode? = null

        init {
            addNeighbor()
        }

        fun addNeighbor() {
            if (i < nodes - 1) {
                next = BSMSNode(i + 1)
                next?.prev = this
            }
        }

        fun draw(canvas : Canvas, paint : Paint) {
            canvas.drawBSMSNode(i, state.scale, paint)
            next?.draw(canvas, paint)
        }

        fun update(cb : (Int, Float) -> Unit) {
            state.update {
                cb(i, it)
            }
        }

        fun startUpdating(cb : () -> Unit) {
            state.startUpdating(cb)
        }

        fun getNext(dir : Int, cb : () -> Unit) : BSMSNode {
            var curr : BSMSNode? = prev
            if (dir == 1) {
                curr = next
            }
            if (curr != null) {
                return curr
            }
            cb()
            return this
        }
    }
}
