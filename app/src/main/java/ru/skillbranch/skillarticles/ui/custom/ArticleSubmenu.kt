package ru.skillbranch.skillarticles.ui.custom

import android.content.Context
import android.util.AttributeSet
import android.view.View
import androidx.constraintlayout.widget.ConstraintLayout
import com.google.android.material.shape.MaterialShapeDrawable
import ru.skillbranch.skillarticles.R
import ru.skillbranch.skillarticles.extensions.dpToPx

class ArticleSubmenu @JvmOverloads constructor(
        context: Context,
        attrs: AttributeSet?,
        defStyleAttr : Int = 0
) : ConstraintLayout(context, attrs, defStyleAttr) {
    var isOpen = false
    private var centerX: Float = context.dpToPx(100)
    private var centerY: Float = context.dpToPx(96)
    init {
        View.inflate(context, R.layout.layout_submenu, this)
        val materialBg = MaterialShapeDrawable.createWithElevationOverlay(context)
        materialBg.elevation = elevation
        background = materialBg
    }

    fun open(){
        if (isOpen) return
        isOpen = true
        visibility = View.VISIBLE
    }
    fun close(){
        if (!isOpen)return
        isOpen = false
        visibility = View.GONE
    }
}