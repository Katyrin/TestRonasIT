package com.katyrin.testronasit.view.custom

import android.content.Context
import android.util.AttributeSet
import android.view.MotionEvent
import android.view.View
import android.view.View.OnTouchListener
import androidx.appcompat.widget.AppCompatTextView


class ClickableTextView @JvmOverloads constructor(
    context: Context, attrs: AttributeSet? = null, defStyleAttr: Int = 0
) : AppCompatTextView(context, attrs, defStyleAttr), OnTouchListener {

    override fun onTouch(v: View, event: MotionEvent): Boolean {
        if (hasOnClickListeners()) {
            when (event.action) {
                MotionEvent.ACTION_DOWN -> isSelected = true
                MotionEvent.ACTION_UP, MotionEvent.ACTION_CANCEL -> isSelected = false
            }
        }
        return false
    }
}