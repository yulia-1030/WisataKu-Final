package com.example.wisataku.ui.customview

import android.content.Context
import android.graphics.Canvas
import android.util.AttributeSet
import android.view.Gravity
import androidx.appcompat.widget.AppCompatButton
import androidx.core.content.ContextCompat
import com.example.wisataku.R

class LoginButton : AppCompatButton {
    private var textColor: Int = 0

    constructor(context: Context) : super(context) {
        init()
    }

    constructor(context: Context, attrs: AttributeSet) : super(context, attrs) {
        init()
    }

    constructor(context: Context, attrs: AttributeSet, defStyleAttr: Int) : super(context, attrs, defStyleAttr) {
        init()
    }

    private fun init() {
        textColor = ContextCompat.getColor(context, android.R.color.background_light)
    }

    override fun onDraw(canvas: Canvas) {
        super.onDraw(canvas)
        setTextColor(textColor)
        textSize = 12f
        gravity = Gravity.CENTER
        text = if(isEnabled) resources.getString(R.string.login) else resources.getString(R.string.data_not_valid)
    }
}