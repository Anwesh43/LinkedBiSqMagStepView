package com.anwesh.uiprojects.linkedbisqmagstepview

import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import com.anwesh.uiprojects.bisqmagstepview.BiSqMagStepView

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        BiSqMagStepView.create(this)
    }
}
