package com.maxpilotto.keypadview;

import android.content.Context;
import android.content.res.TypedArray;
import android.support.annotation.ColorInt;
import android.support.annotation.DrawableRes;
import android.util.AttributeSet;
import android.util.TypedValue;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;

import java.util.ArrayList;
import java.util.List;

public class KeyPad extends LinearLayout {
    public interface OnKeyClickListener {
        void onKeyClick(Key key);
    }

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

            left = findViewById(R.id.left);
            right = findViewById(R.id.right);

            left.setKey(array.getString(R.styleable.KeyPad_leftButtonText));
            right.setKey(array.getString(R.styleable.KeyPad_rightButtonText));

            if (array.getBoolean(R.styleable.KeyPad_showLeftButton,false)){
                ((LinearLayout)left.getParent()).setVisibility(VISIBLE);
            }else{
                ((LinearLayout)left.getParent()).setVisibility(INVISIBLE);
            }

            if (array.getBoolean(R.styleable.KeyPad_showRightButton,false)){
                ((LinearLayout)right.getParent()).setVisibility(VISIBLE);
            }else{
                ((LinearLayout)right.getParent()).setVisibility(INVISIBLE);
            }

            setAllKeysParams(array);

            array.recycle();
        }
    }

    /**
     * Sets the left button text and makes it visible
     * Use setLeftButton(null) to hide the button
     * @param text Text, null to hide
     */
    public void setLeftButton(String text){
        if (text != null){
            ((LinearLayout)left.getParent()).setVisibility(VISIBLE);
            left.setKey(text);
        }else{
            ((LinearLayout)left.getParent()).setVisibility(INVISIBLE);
        }
    }

    /**
     * Sets the right button text and makes it visible
     * Use setRightButton(null) to hide the button
     * @param text Text, null to hide
     */
    public void setRightButton(String text){
        if (text != null){
            ((LinearLayout)right.getParent()).setVisibility(VISIBLE);
            right.setKey(text);
        }else{
            ((LinearLayout)right.getParent()).setVisibility(INVISIBLE);
        }
    }

    /**
     * Sets the key listener for each key, this will fetch dynamically all Key views
     *
     * @param listener Listener
     */
    public void setKeyListener(final OnKeyClickListener listener) {
        List<Key> keys = new ArrayList<>();
        findAllKeys(this, keys);

        for (Key k : keys) {
            final Key key = k;
            k.setOnClickListener(new OnClickListener() {
                @Override public void onClick(View v) {
                    listener.onKeyClick(key);
                }
            });
        }
    }

    /**
     * Sets the background drawable to all keys
     * @param background Background res
     */
    public void setKeysBackground(@DrawableRes int background){
        List<Key> keys = new ArrayList<>();

        findAllKeys(this,keys);

        for (Key k : keys){
            k.setKeyBackground(background);
        }
    }

    /**
     * Sets the key text color to all keys
     * @param color ColorInt
     */
    public void setKeysTextColor(@ColorInt int color){
        List<Key> keys = new ArrayList<>();

        findAllKeys(this,keys);

        for (Key k : keys){
            k.setKeyTextColor(color);
        }
    }

    /**
     * Sets the wrapper's background to all keys
     * @param background Background res
     */
    public void setKeysWrapperBackground(@DrawableRes int background){
        List<Key> keys = new ArrayList<>();

        findAllKeys(this,keys);

        for (Key k : keys){
            k.setKeyWrapperBackground(background);
        }
    }

    /**
     * Sets the key text size to all keys
     * @param dp Text size in dp
     */
    public void setKeysTextSize(int dp){
        List<Key> keys = new ArrayList<>();

        findAllKeys(this,keys);

        for (Key k : keys){
            k.setKeyTextSize(dp);
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
    private void setAllKeysParams(TypedArray a){
        List<Key> keys = new ArrayList<>();
        int background = a.getResourceId(R.styleable.KeyPad_keysBackground,R.drawable.key_background);
        int textColor = a.getColor(R.styleable.KeyPad_keysTextColor,getResources().getColor(android.R.color.darker_gray) );
        int wrapperBackground = a.getResourceId(R.styleable.KeyPad_keysWrapperBackground,0);
        float textSize = a.getDimension(R.styleable.Key_keyTextSize,(int) (22 * getContext().getResources().getDisplayMetrics().density));

        findAllKeys(this,keys);

        for (Key k : keys){
            k.getText().setTextSize(
                    TypedValue.COMPLEX_UNIT_PX,
                    textSize
            );
            k.setKeyBackground(background);
            k.setKeyTextColor(textColor);

            if (wrapperBackground != 0) {
                k.setKeyWrapperBackground(wrapperBackground);
            }
        }
    }
}
