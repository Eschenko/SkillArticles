package ru.skillbranch.skillarticles.ui.delegates

import android.content.Context
import android.content.res.Resources
import android.util.TypedValue
import androidx.annotation.AttrRes
import kotlin.properties.ReadOnlyProperty
import kotlin.reflect.KProperty

class AttrValue(@AttrRes private val res: Int) : ReadOnlyProperty<Context, Int>{
    private var _value : Int? = null
    override fun getValue(thisRef: Context, property: KProperty<*>): Int {
        val tv = TypedValue()
        if (_value == null){

            if (thisRef.theme.resolveAttribute(res, tv, true))
                _value = tv.data
            else throw Resources.NotFoundException("Resource with id $res not found")
        }
        if (thisRef.theme.resolveAttribute(res, tv, true))_value = tv.data

        return _value!!
    }

}