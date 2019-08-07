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
import android.support.annotation.ColorInt
import android.support.annotation.DrawableRes
import android.support.constraint.ConstraintLayout
import android.util.AttributeSet
import android.util.TypedValue
import android.view.View
import android.view.ViewGroup
import kotlinx.android.synthetic.main.keypad.view.*

/**
 * View that shows and handles a customizable KeyPad
 *
 * Created on 07/08/2019 at 18:55
 */
class KeyPad : ConstraintLayout {
    /**
     * All the keys inside this Keypad
     */
    val keys: MutableList<Key>

    /**
     * Left key of this keypad, it might be invisible if not enabled
     */
    val leftKey: Key
        get() {
            return kl
        }

    /**
     * Right key of this keypad, it might be invisible if not enabled
     */
    val rightKey: Key
        get() {
            return kr
        }

    constructor(context: Context) : this(context, null, 0)

    constructor(context: Context, attrs: AttributeSet?) : this(context, attrs, 0)

    constructor(context: Context, attrs: AttributeSet?, defStyleAttr: Int) : super(context, attrs, defStyleAttr) {
        View.inflate(context, R.layout.keypad, this)

        keys = mutableListOf()
        findAllKeys(this, keys)

        keys.forEachIndexed { i, k ->
            k.position = i
        }

        if (attrs != null) {
            val array = context.obtainStyledAttributes(attrs, R.styleable.KeyPad, defStyleAttr, 0)
            val textSize = array.getDimensionPixelSize(R.styleable.KeyPad_keysTextSize, 0)
            val iconSize = array.getDimensionPixelSize(R.styleable.KeyPad_keysIconSize, 0)
            val tint = array.getColor(R.styleable.KeyPad_keysIconTint, 0)
            val wrapper = array.getResourceId(R.styleable.KeyPad_keysWrapperBackground, 0)
            val background = array.getResourceId(R.styleable.KeyPad_keysBackground, R.drawable.key_background)
            val textColor = array.getColor(R.styleable.KeyPad_keysTextColor, 0)
            val leftText = array.getString(R.styleable.KeyPad_leftKeyText)
            val rightText = array.getString(R.styleable.KeyPad_rightKeyText)
            val leftIcon = array.getResourceId(R.styleable.KeyPad_leftKeyIcon, 0)
            val rightIcon = array.getResourceId(R.styleable.KeyPad_rightKeyIcon, 0)

            setMargins(
                    array.getDimension(R.styleable.KeyPad_keysMarginLeft, 0f).toInt(),
                    array.getDimension(R.styleable.KeyPad_keysMarginTop, 0f).toInt(),
                    array.getDimension(R.styleable.KeyPad_keysMarginRight, 0f).toInt(),
                    array.getDimension(R.styleable.KeyPad_keysMarginBottom, 0f).toInt()
            )

            setMargins(array.getDimension(R.styleable.KeyPad_keysMargin, 0f).toInt())

            for (k in keys) {
                k.background = background
                k.textColor = textColor

                if (textSize != 0) {
                    k.getTextView().setTextSize(TypedValue.COMPLEX_UNIT_PX, textSize.toFloat())
                }

                if (tint != 0) {
                    k.iconTint = tint
                }

                if (wrapper != 0) {
                    k.wrapperBackground = wrapper
                }

                if (iconSize != 0) {
                    k.iconSize = iconSize
                }
            }

            if (leftText != null) {
                leftKey.text = leftText
                leftKey.show()
            } else {
                leftKey.hide()
            }

            if (rightText != null) {
                rightKey.text = rightText
                rightKey.show()
            } else {
                rightKey.hide()
            }

            if (leftIcon != 0) {
                leftKey.icon = leftIcon
                leftKey.show()
            }

            if (rightIcon != 0) {
                rightKey.icon = rightIcon
                rightKey.show()
            }

            array.recycle()
        }
    }

    /**
     * Sets the margins to all keys
     */
    fun setMargins(left: Int, top: Int, right: Int, bottom: Int) {
        for (k in keys) {
            (k.layoutParams as LayoutParams).setMargins(left, top, right, bottom)
        }
    }

    /**
     * Sets the background drawable to all keys
     *
     * @param background Background res
     */
    fun setKeysBackground(@DrawableRes background: Int) {
        for (k in keys) {
            k.background = background
        }
    }

    /**
     * Sets the key text color to all keys
     *
     * @param color ColorInt
     */
    fun setKeysTextColor(@ColorInt color: Int) {
        for (k in keys) {
            k.textColor = color
        }
    }

    /**
     * Sets the wrapper's background to all keys
     *
     * @param background Background res
     */
    fun setKeysWrapperBackground(@DrawableRes background: Int) {
        for (k in keys) {
            k.wrapperBackground = background
        }
    }

    /**
     * Sets the key text size to all keys
     *
     * @param sp Text size in SP
     */
    fun setKeysTextSize(sp: Float) {
        for (k in keys) {
            k.textSize = sp
        }
    }

    /**
     * Sets the tint to all keys that are icons
     *
     * @param color Color
     */
    fun setIconsTint(@ColorInt color: Int) {
        for (k in keys) {
            k.iconTint = color
        }
    }

    /**
     * Sets the size to all keys that are icons
     */
    fun setIconsSize(size: Int) {
        for (k in keys) {
            k.iconSize = size
        }
    }

    /**
     * Sets the margins to all keys
     */
    fun setMargins(margins: Int) {
        setMargins(margins, margins, margins, margins)
    }

    /**
     * Returns a key that has the given text or null if there's not key with that text
     */
    fun findKey(text: String): Key? {
        for (k in keys) {
            if (k.text == text) {
                return k
            }
        }

        return null
    }

    /**
     * Returns a key that has the given value or null if there's not key with that value
     */
    fun findKey(value: Int): Key? {
        return findKey(value.toString())
    }

    /**
     * Returns the key at the given position
     */
    fun keyAt(position: Int): Key? {
        if (position < keys.size) {
            return keys[position]
        }

        return null
    }

    /**
     * Sets the text or int value of the right key, this will only accept String and Int values
     */
    fun setRightKey(value: Any) {
        when (value) {
            is Int -> rightKey.icon = value
            is String -> rightKey.text = value
        }
    }

    /**
     * Sets the text or int value of the left key, this will only accept String and Int values
     */
    fun setLeftKey(value: Any) {
        when (value) {
            is Int -> leftKey.icon = value
            is String -> leftKey.text = value
        }
    }

    /**
     * Sets a click listener for all keys
     */
    fun onClick(action: (Key) -> Unit) {
        for (k in keys) {
            k.setOnClickListener {
                action.invoke(k)
            }
        }
    }

    /**
     * Sets a long click listener for all keys
     */
    fun onLongClick(action: (Key) -> Boolean) {
        for (k in keys) {
            k.setOnLongClickListener {
                action.invoke(k)
            }
        }
    }

    companion object {
        /**
         * Recursively searches for all Key views
         */
        @JvmStatic
        fun findAllKeys(root: ViewGroup, keys: MutableList<Key>) {
            for (i in 0..root.childCount) {
                val v = root.getChildAt(i)

                if (v is Key) {
                    keys.add(v)
                } else if (v is ViewGroup) {
                    findAllKeys(v, keys)
                }
            }
        }
    }
}