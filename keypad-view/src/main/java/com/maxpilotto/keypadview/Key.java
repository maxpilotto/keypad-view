package com.maxpilotto.keypadview;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.drawable.Drawable;
import android.support.annotation.ColorInt;
import android.support.annotation.DrawableRes;
import android.util.AttributeSet;
import android.util.TypedValue;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

public class Key extends LinearLayout {
    private LinearLayout root;
    private TextView text;

    public Key(Context context) {
        this(context,null,0);
    }

    public Key(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public Key(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);

        inflate(context,R.layout.key, this);

        root = findViewById(R.id.root);
        text = findViewById(R.id.text);

        if (attrs != null){
            TypedArray a = context.obtainStyledAttributes(attrs, R.styleable.Key, defStyleAttr, 0);

            text.setText(a.getString(R.styleable.Key_keyValue));
            text.setTextColor(a.getColor(
                    R.styleable.Key_keyTextColor,
                    getResources().getColor(android.R.color.darker_gray)
            ));
            text.setBackgroundResource(a.getResourceId(R.styleable.Key_keyBackground,R.drawable.key_background));

            text.setTextSize(
                    TypedValue.COMPLEX_UNIT_PX,
                    a.getDimensionPixelSize(R.styleable.Key_keyTextSize, (int) (22 * context.getResources().getDisplayMetrics().density)));

            if (a.getResourceId(R.styleable.Key_keyWrapperBackground,0) != 0){
                root.setBackgroundResource(a.getResourceId(R.styleable.Key_keyWrapperBackground,0));
            }

            a.recycle();
        }
    }

    @Override public void setOnClickListener(View.OnClickListener l) {
        //NOTE: Without this the view won't be clickable

        root.setOnClickListener(l);
    }

    /**
     * Sets the text of the key
     * @param key Key's text
     */
    public void setKey(String key){
        text.setText(key);
    }

    /**
     * Returns the text of the key
     * @return Key's text
     */
    public String getKey(){
        return text.getText().toString();
    }

    /**
     * Sets the key background, by default it is a gray circle
     * @param res Background
     */
    public void setKeyBackground(@DrawableRes int res){
        text.setBackgroundResource(res);
    }

    /**
     * Returns the key background
     * @return Background
     */
    public Drawable getKeyBackground(){
        return text.getBackground();
    }

    /**
     * Sets the key text color, gray by default
     * @param color ColorInt
     */
    public void setKeyTextColor(@ColorInt int color){
        text.setTextColor(color);
    }

    /**
     * Returns the text color
     * @return ColorInt
     */
    public @ColorInt int getKetTextColor(){
        return text.getCurrentTextColor();
    }

    /**
     * Returns the wrapper layout's background
     * @return Background
     */
    public Drawable getKeyWrapperBackground(){
        return root.getBackground();
    }

    /**
     * Sets the wrapper layout's background, this will disable the ripple effect
     * If you want to set your own background and keep the ripple effect, you have to implement the ripple animation by yourself
     * @param res Background
     */
    public void setKeyWrapperBackground(@DrawableRes int res){
        root.setBackgroundResource(res);
    }

    /**
     * Sets the text size of the key
     * @param dp Size in DP
     */
    public void setKeyTextSize(int dp){
        text.setTextSize(dp * getContext().getResources().getDisplayMetrics().density);
    }

    /**
     * Returns the reference to the textview
     * @return Textview reference
     */
    public TextView getText(){
        return text;
    }
}
