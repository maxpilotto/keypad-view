package com.maxpilotto.keypadview;

import android.content.Context;
import android.content.res.TypedArray;
import android.os.Build;
import android.support.annotation.ColorInt;
import android.support.annotation.DrawableRes;
import android.support.constraint.ConstraintLayout;
import android.util.AttributeSet;
import android.util.DisplayMetrics;
import android.util.Log;
import android.util.TypedValue;
import android.view.Display;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewTreeObserver;
import android.view.WindowManager;

import java.util.ArrayList;
import java.util.List;

public class KeyPad extends ConstraintLayout {
    public interface KeyClickListener {
        void onClick(Key key);
    }

    public interface KeyLongClickListener {
        boolean onLongClick(Key key);
    }

    private ArrayList<Key> keys;
    private Key left;
    private Key right;

    public KeyPad(Context context) {
        super(context);
    }

    public KeyPad(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public KeyPad(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);

        if (attrs != null) {
            TypedArray array = context.obtainStyledAttributes(attrs, R.styleable.KeyPad, defStyleAttr, 0);

            inflate(context, R.layout.keypad, this);

            keys = new ArrayList<>();

            findAllKeys(this, keys);

            for (int i = 0; i < keys.size(); i++) {
                keys.get(i).setPosition(i);
            }

            left = findViewById(R.id.left);
            right = findViewById(R.id.right);

            if (array.getString(R.styleable.KeyPad_leftKeyText) != null){
                left.setText(array.getString(R.styleable.KeyPad_leftKeyText));
                left.setVisibility(VISIBLE);
            }

            if (array.getString(R.styleable.KeyPad_rightKeyText) != null){
                right.setText(array.getString(R.styleable.KeyPad_rightKeyText));
                right.setVisibility(VISIBLE);
            }

            setKeysMargins(
                    (int) array.getDimension(R.styleable.KeyPad_keysMarginLeft, 0),
                    (int) array.getDimension(R.styleable.KeyPad_keysMarginTop, 0),
                    (int) array.getDimension(R.styleable.KeyPad_keysMarginRight, 0),
                    (int) array.getDimension(R.styleable.KeyPad_keysMarginBottom, 0)
            );

            setKeysMargins(
                    (int) array.getDimension(R.styleable.KeyPad_keysMargin, 0)
            );

            if (array.getResourceId(R.styleable.KeyPad_leftKeyIcon, 0) != 0) {
                setLeftKey(array.getResourceId(R.styleable.KeyPad_leftKeyIcon, 0));
            }

            if (array.getResourceId(R.styleable.KeyPad_rightKeyIcon, 0) != 0) {
                setRightKey(array.getResourceId(R.styleable.KeyPad_rightKeyIcon, 0));
            }

            setAllKeysParams(array);

            array.recycle();
        }
    }

    /**
     * Sets the left key icon and makes it visible
     * Use setLeftKey(null) to hide the key
     *
     * @param iconRes Icon resource
     */
    public void setLeftKey(@DrawableRes int iconRes) {
        left.setVisibility(VISIBLE);
        left.setIcon(iconRes);
    }

    /**
     * Sets the right key icon and makes it visible
     * Use setRightKey(null) to hide the key
     *
     * @param iconRes Icon resource
     */
    public void setRightKey(@DrawableRes int iconRes) {
        right.setVisibility(VISIBLE);
        right.setIcon(iconRes);
    }

    /**
     * Sets the left key text and makes it visible
     * Use setLeftKey(null) to hide the key
     *
     * @param text Text, null to hide
     */
    public void setLeftKey(String text) {
        if (text != null) {
            left.setVisibility(VISIBLE);
            left.setText(text);
        } else {
            left.setVisibility(INVISIBLE);
        }
    }

    /**
     * Sets the right key text and makes it visible
     * Use setRightKey(null) to hide the key
     *
     * @param text Text, null to hide
     */
    public void setRightKey(String text) {
        if (text != null) {
            right.setVisibility(VISIBLE);
            right.setText(text);
        } else {
            right.setVisibility(INVISIBLE);
        }
    }

    /**
     * Returns the right key
     *
     * @return Right key
     */
    public Key getRightKey() {
        return right;
    }

    /**
     * Returns the left key
     *
     * @return Left key
     */
    public Key getLeftKey() {
        return left;
    }

    /**
     * Returns a key that has the given text value
     *
     * @param text Text value of the searched key
     * @return Key or null if not found
     */
    public Key getKey(String text) {
        for (Key k : keys) {
            if (k.getText().equals(text)) {
                return k;
            }
        }

        return null;
    }

    /**
     * Returns a key that has the given integer value <br>
     *
     * @param value Integer value of the searched key
     * @return Key or null if not found
     */
    public Key getKey(Integer value) {
        for (Key k : keys) {
            String str = String.valueOf(value);

            if (k.getText().equals(str)) {
                return k;
            }
        }

        return null;
    }

    /**
     * Returns the key at the given position
     *
     * @param position Position of the key (starting from 0)
     * @return Key or null if not found
     */
    public Key getKeyAt(int position) {
        if (position >= keys.size()) {
            return null;
        }

        return keys.get(position);
    }

    /**
     * Sets the margins of the given key
     *
     * @param text   Text of the key to change
     * @param left   Left margin
     * @param top    Top margin
     * @param right  Right margin
     * @param bottom Bottom margin
     */
    public void setKeyMargins(String text, int left, int top, int right, int bottom) {
        setKeyMargins(getKey(text), left, top, right, bottom);
    }

    /**
     * Sets the margins of the given key
     *
     * @param value  Value of the key to change
     * @param left   Left margin
     * @param top    Top margin
     * @param right  Right margin
     * @param bottom Bottom margin
     */
    public void setKeyMargins(Integer value, int left, int top, int right, int bottom) {
        setKeyMargins(getKey(value), left, top, right, bottom);
    }

    /**
     * Sets the margins of the given key
     *
     * @param key    Key to change
     * @param left   Left margin
     * @param top    Top margin
     * @param right  Right margin
     * @param bottom Bottom margin
     */
    public void setKeyMargins(Key key, int left, int top, int right, int bottom) {
        if (key != null) {
            ConstraintLayout.LayoutParams params = (LayoutParams) key.getLayoutParams();

            params.setMargins(left, top, right, bottom);
        }
    }

    /**
     * Fixes the width for when WRAP_CONTENT is used <br> <br>
     * Explanation: The view width won't work if WRAP_CONTENT is set, this is due to the ConstraintLayout, <br>
     * the only way of fixing this is by calculating the minimum width and setting it
     */
    private void fixWidth() {    //FIXME Doesn't work
        getViewTreeObserver().addOnGlobalLayoutListener(new ViewTreeObserver.OnGlobalLayoutListener() {
            @Override public void onGlobalLayout() {
                Display display = ((WindowManager) getContext().getSystemService(Context.WINDOW_SERVICE)).getDefaultDisplay();
                ViewGroup.LayoutParams params = getLayoutParams();
                DisplayMetrics metrics = new DisplayMetrics();

                display.getMetrics(metrics);

                int widthSpec = View.MeasureSpec.makeMeasureSpec(0, MeasureSpec.UNSPECIFIED);
                int heightSpec = View.MeasureSpec.makeMeasureSpec(metrics.heightPixels, MeasureSpec.AT_MOST);

                measure(widthSpec, heightSpec);

                params.width = getMeasuredWidth();
                setLayoutParams(params);

                if (Build.VERSION.SDK_INT < 16) {
                    getViewTreeObserver().removeGlobalOnLayoutListener(this);
                } else {
                    getViewTreeObserver().removeOnGlobalLayoutListener(this);
                }
            }
        });
    }

    /**
     * Sets the margins of the given key
     *
     * @param text   Text of the key to change
     * @param margin Margin of all sides
     */
    public void setKeyMargins(String text, int margin) {
        setKeyMargins(getKey(text), margin, margin, margin, margin);
    }

    /**
     * Sets the margins of the given key
     *
     * @param value  Value of the key to change
     * @param margin Margin of all sides
     */
    public void setKeyMargins(Integer value, int margin) {
        setKeyMargins(getKey(value), margin, margin, margin, margin);
    }

    /**
     * Sets the margins of the given key
     *
     * @param key    Key to change
     * @param margin Margin of all sides
     */
    public void setKeyMargins(Key key, int margin) {
        setKeyMargins(key, margin, margin, margin, margin);
    }

    /**
     * Sets the all the keys' margins
     *
     * @param left   Left margin
     * @param top    Top margin
     * @param right  Right margin
     * @param bottom Bottom margin
     */
    public void setKeysMargins(int left, int top, int right, int bottom) {
        for (Key k : keys) {
            setKeyMargins(k, left, top, right, bottom);
        }
    }

    /**
     * Sets the all the keys' margins
     *
     * @param margin Margin of all sides
     */
    public void setKeysMargins(int margin) {
        setKeysMargins(margin, margin, margin, margin);
    }

    /**
     * Sets the click listener, which will be called every time a key is clicked
     *
     * @param click {@link KeyClickListener}
     */
    public void setOnClickListener(final KeyClickListener click) {
        for (Key k : keys) {
            final Key key = k;

            key.setOnClickListener(new OnClickListener() {
                @Override public void onClick(View view) {
                    click.onClick(key);
                }
            });
        }
    }

    /**
     * Sets the long click listener, which will be called every time a key is long clicked
     *
     * @param click {@link KeyLongClickListener}
     */
    public void setOnLongClickListener(final KeyLongClickListener click) {
        for (Key k : keys) {
            final Key key = k;

            key.setOnLongClickListener(new OnLongClickListener() {
                @Override public boolean onLongClick(View view) {
                    return click.onLongClick(key);
                }
            });
        }
    }

    /**
     * Sets the background drawable to all keys
     *
     * @param background Background res
     */
    public void setKeysBackground(@DrawableRes int background) {
        for (Key k : keys) {
            k.setKeyBackground(background);
        }
    }

    /**
     * Sets the key text color to all keys
     *
     * @param color ColorInt
     */
    public void setKeysTextColor(@ColorInt int color) {
        for (Key k : keys) {
            k.setTextColor(color);
        }
    }

    /**
     * Sets the wrapper's background to all keys
     *
     * @param background Background res
     */
    public void setKeysWrapperBackground(@DrawableRes int background) {
        for (Key k : keys) {
            k.setWrapperBackground(background);
        }
    }

    /**
     * Sets the key text size to all keys
     *
     * @param sp Text size in SP
     */
    public void setKeysTextSize(int sp) {
        for (Key k : keys) {
            k.setTextSize(sp);
        }
    }

    /**
     * Recursively searches for all Key views
     *
     * @param root The root from which the search starts
     * @param keys List of Key
     */
    public static void findAllKeys(ViewGroup root, List<Key> keys) {
        for (int i = 0; i < root.getChildCount(); i++) {
            View v = root.getChildAt(i);

            if (v instanceof Key) {
                keys.add((Key) v);
            } else if (v instanceof ViewGroup) {
                findAllKeys((ViewGroup) v, keys);
            }
        }
    }

    /**
     * Applies the attributes to all keys
     */
    private void setAllKeysParams(TypedArray a) {
        int background = a.getResourceId(R.styleable.KeyPad_keysBackground, R.drawable.key_background);
        int textColor = a.getColor(R.styleable.KeyPad_keysTextColor, getResources().getColor(android.R.color.darker_gray));
        int wrapperBackground = a.getResourceId(R.styleable.KeyPad_keysWrapperBackground, 0);
        int txtSize = a.getDimensionPixelSize(R.styleable.KeyPad_keysTextSize, 0);
        int tint = a.getColor(R.styleable.KeyPad_keysIconTint,0);

        for (Key k : keys) {
            if (txtSize != 0){
                k.getTextView().setTextSize(TypedValue.COMPLEX_UNIT_PX, txtSize);
            }else{
                k.setTextSize(Key.DEFAULT_TEXT_SIZE_SP);
            }

            if (tint != 0){
                k.setIconTint(tint);
            }

            k.setKeyBackground(background);
            k.setTextColor(textColor);

            if (wrapperBackground != 0) {
                k.setWrapperBackground(wrapperBackground);
            }
        }
    }
}
