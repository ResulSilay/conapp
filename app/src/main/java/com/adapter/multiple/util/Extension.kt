package com.adapter.multiple.util

import android.app.Activity
import android.content.Context
import android.content.res.ColorStateList
import android.content.res.Resources
import android.view.View
import android.view.inputmethod.InputMethodManager
import androidx.constraintlayout.widget.ConstraintLayout
import com.adapter.multiple.R


fun Context.inputMethodManager(view: View) {
    val inputMethodManager: InputMethodManager? = this.getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager?
    inputMethodManager?.hideSoftInputFromWindow(view.windowToken, 0)
}

fun Activity.hideKeyboard() {
    val inputManager = getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager?
    inputManager!!.hideSoftInputFromWindow(currentFocus?.windowToken, InputMethodManager.HIDE_NOT_ALWAYS)
}

fun ConstraintLayout.margin(top: Int = 0, left: Int = 0, right: Int = 0, bottom: Int = 0) {
    val newLayoutParams: ConstraintLayout.LayoutParams = this.layoutParams as ConstraintLayout.LayoutParams
    newLayoutParams.topMargin = top
    newLayoutParams.leftMargin = left
    newLayoutParams.rightMargin = right
    newLayoutParams.bottomToBottom = bottom
    this.layoutParams = newLayoutParams
}

fun ConstraintLayout.padding(top: Int? = null, left: Int? = null, right: Int? = null, bottom: Int? = null) {
    var topPadding = top
    var leftPadding = left
    var rightPadding = right
    var bottomPadding = bottom

    if (top == null)
        topPadding = this.paddingTop

    if (left == null)
        leftPadding = this.paddingLeft

    if (right == null)
        rightPadding = this.paddingRight

    if (bottom == null)
        bottomPadding = this.paddingBottom

    this.setPadding(leftPadding!!, topPadding!!, rightPadding!!, bottomPadding!!)
}

fun View.tint(position: Int) {
    val colors = this.context.resources.getIntArray(R.array.tint)
    colors.let { colorList ->
        val pos = if (colorList.size > position) position else 0
        this.backgroundTintList = ColorStateList.valueOf(colorList[pos])
    }
}

fun String.sizePhoto(size: Int): String {
    return this.replace("100x100", "${size}x${size}")
}

val Int.dp: Int get() = (this / Resources.getSystem().displayMetrics.density).toInt()

val Int.px: Int get() = (this * Resources.getSystem().displayMetrics.density).toInt()