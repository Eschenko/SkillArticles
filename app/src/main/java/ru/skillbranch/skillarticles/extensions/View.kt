package ru.skillbranch.skillarticles.extensions

import android.view.View
import android.view.ViewGroup
import androidx.core.view.*
import androidx.core.widget.NestedScrollView
import ru.skillbranch.skillarticles.R

fun View.setMarginOptionally(
    left:Int = marginLeft,
    top : Int = marginTop,
    right : Int = marginRight,
    bottom : Int = marginBottom
){
    val param = layoutParams as ViewGroup.MarginLayoutParams
    param.setMargins(left,top, right, bottom)
    layoutParams = param
}

