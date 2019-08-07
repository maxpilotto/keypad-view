/*
 * Copyright 2018 Max Pilotto
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *       http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.maxpilotto.keypadview

import android.content.Context
import android.graphics.PorterDuff
import android.support.annotation.ColorInt
import android.support.annotation.DrawableRes
import android.util.AttributeSet
import android.util.TypedValue
import android.view.View
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.TextView
import kotlinx.android.synthetic.main.key.view.*

/**
 * Representation of a single Keypad key
 *
 * Created on 07/08/2019 at 15:55
 */
class Key : LinearLayout {
    /**
     * Position of this Key inside a KeyPad, this could be used as a unique identifier
     */
    var position: Int

    /**
     * Key's text, this will hide the icon
     */
    var text: String
        set(value) {
            hideIcon()

            textView?.text = value
            field = value
        }

    /**
     * Key's text color
     */
    var textColor: Int
        set(@ColorInt value) {
            textView?.setTextColor(value)
            field = value
        }

    /**
     * Key's text size
     */
    var textSize: Float
        set(value) {
            textView?.textSize = value
            field = value
        }

    /**
     * Key's icon, this will hide the text
     */
    var icon: Int
        set(@DrawableRes value) {
            hideText()

            iconView?.setImageResource(value)
            field = value
        }

    /**
     * Key's icon size
     */
    var iconSize: Int
        set(value) {
            val params = iconView?.layoutParams

            params?.height = value
            params?.width = value
            field = value
        }

    /**
     * Key's icon tint
     */
    var iconTint: Int
        set(@ColorInt value) {
            iconView?.setColorFilter(value, PorterDuff.Mode.SRC_IN)
            field = value
        }

    /**
     * Key background
     */
    var background: Int
        set(@DrawableRes value) {
            textView?.setBackgroundResource(value)
            iconView?.setBackgroundResource(value)
            field = value
        }

    /**
     * Key wrapper background, this background won't overwrite the [background] and it should be used
     * to enable the ripple effect
     */
    var wrapperBackground: Int
        set(@DrawableRes value) {
            setBackgroundResource(value)
            field = value
        }

    init {
        position = 0
        text = ""
        textColor = 0
        textSize = DEFAULT_TEXT_SIZE
        icon = 0
        iconSize = 0
        iconTint = 0
        background = R.drawable.key_background
        wrapperBackground = 0
    }

    constructor(context: Context) : this(context, null, 0)

    constructor(context: Context, attrs: AttributeSet?) : this(context, attrs, 0)

    constructor(context: Context, attrs: AttributeSet?, defStyleAttr: Int) : super(context, attrs, defStyleAttr) {
        View.inflate(context, R.layout.key, this)

        if (attrs != null) {
            val array = context.obtainStyledAttributes(attrs, R.styleable.Key, defStyleAttr, 0)
            val textSize = array.getDimensionPixelSize(R.styleable.Key_keyTextSize, 0)
            val icon = array.getResourceId(R.styleable.Key_keyIcon, 0)
            val iconSize = array.getDimensionPixelSize(R.styleable.Key_keyIconSize, 0)
            val tint = array.getColor(R.styleable.Key_keyTint, 0)
            val wrapper = array.getResourceId(R.styleable.Key_keyWrapperBackground, 0)

            text = array.getString(R.styleable.Key_keyValue)
            textColor = array.getColor(R.styleable.Key_keyTextColor, 0)
            background = array.getResourceId(R.styleable.Key_keyBackground, R.drawable.key_background)

            if (textSize != 0) {
                textView.setTextSize(TypedValue.COMPLEX_UNIT_PX, textSize.toFloat())
            }

            if (icon != 0) {
                this.icon = icon
            }

            if (iconSize != 0) {
                this.iconSize = iconSize
            }

            if (tint != 0) {
                this.iconTint = tint
            }

            if (wrapper != 0) {
                this.wrapperBackground = wrapper
            }

            array.recycle()
        }
    }

    override fun setOnClickListener(l: OnClickListener?) {
        root.setOnClickListener(l)
    }

    override fun setOnLongClickListener(l: OnLongClickListener?) {
        root.setOnLongClickListener(l)
    }

    /**
     * Returns the view used for the text, this can be used for further customization
     */
    fun getTextView(): TextView {
        return textView
    }

    /**
     * Returns the view used for the icon, this can be used for further customization
     */
    fun getIconView(): ImageView {
        return iconView
    }

    /**
     * Replaces the text with an icon
     */
    fun hideIcon() {
        if (textView?.visibility == View.GONE && iconView?.visibility == View.VISIBLE) {
            textView?.visibility = View.VISIBLE
            iconView?.visibility = View.GONE
        }
    }

    /**
     * Replaces the icon with a text
     */
    fun hideText() {
        if (textView?.visibility == View.VISIBLE && iconView?.visibility == View.GONE) {
            textView?.visibility = View.GONE
            iconView?.visibility = View.VISIBLE
        }
    }

    /**
     * Hides the key
     */
    fun hide() {
        visibility = View.INVISIBLE
    }

    /**
     * Shows the key
     */
    fun show() {
        visibility = View.VISIBLE
    }

    companion object {
        const val DEFAULT_TEXT_SIZE = 20F
    }
}