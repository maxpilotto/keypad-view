package com.maxpilotto.keypadview;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.PorterDuff;
import android.graphics.drawable.Drawable;
import android.support.annotation.ColorInt;
import android.support.annotation.DrawableRes;
import android.util.AttributeSet;
import android.util.TypedValue;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

public class Key extends LinearLayout {
    public static final float DEFAULT_TEXT_SIZE_SP = 20;

    private LinearLayout root;
    private TextView text;
    private ImageView icon;
    private int position;

    public Key(Context context) {
        this(context, null, 0);
    }

    public Key(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public Key(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);

        inflate(context, R.layout.key, this);

        root = findViewById(R.id.root);
        text = findViewById(R.id.text);
        icon = findViewById(R.id.icon);

        if (attrs != null) {
            TypedArray a = context.obtainStyledAttributes(attrs, R.styleable.Key, defStyleAttr, 0);
            int txtSize = a.getDimensionPixelSize(R.styleable.Key_keyTextSize, 0);

            setText(a.getString(R.styleable.Key_keyValue));
            setTextColor(a.getColor(
                    R.styleable.Key_keyTextColor,
                    getResources().getColor(android.R.color.darker_gray)
            ));
            setKeyBackground(a.getResourceId(R.styleable.Key_keyBackground, R.drawable.key_background));

            if (txtSize != 0) {
                text.setTextSize(TypedValue.COMPLEX_UNIT_PX, txtSize);
            } else {
                text.setTextSize(DEFAULT_TEXT_SIZE_SP);
            }

            if (a.getDimensionPixelSize(R.styleable.Key_keyIconSize, 0) != 0) {
                setIconSize(a.getDimensionPixelSize(R.styleable.Key_keyIconSize, 0));
            }

            if (a.getResourceId(R.styleable.Key_keyWrapperBackground, 0) != 0) {
                setWrapperBackground(a.getResourceId(R.styleable.Key_keyWrapperBackground, 0));
            }

            if (a.getResourceId(R.styleable.Key_keyIcon, 0) != 0) {
                setIcon(a.getResourceId(R.styleable.Key_keyIcon, 0));
            }

            if (a.getColor(R.styleable.Key_keyTint, 0) != 0) {
                setIconTint(a.getColor(R.styleable.Key_keyTint, 0));
            }

            a.recycle();
        }
    }

    @Override public void setOnLongClickListener(View.OnLongClickListener l) {
        //NOTE: Without this the view won't be clickable

        root.setOnLongClickListener(l);
    }

    @Override public void setOnClickListener(View.OnClickListener l) {
        //NOTE: Without this the view won't be clickable

        root.setOnClickListener(l);
    }

    /**
     * Returns the position of the Key inside the Keypad, this can be used as a unique identifier
     *
     * @return Position of the Key inside the Keypad
     */
    public int getPosition() {
        return position;
    }

    /**
     * Sets the position of the Key, this will just set the position index and NOT change the layout
     *
     * @param position Position
     */
    public void setPosition(int position) {
        this.position = position;
    }

    /**
     * Sets the text of the key, this will hide the icon
     *
     * @param value Key's text
     */
    public void setText(String value) {
        hideIcon();

        text.setText(value);
    }

    /**
     * Returns the text of the key
     *
     * @return Key's text
     */
    public String getText() {
        return text.getText().toString();
    }

    /**
     * Sets the icon to this Key, this will hide the text
     *
     * @param iconRes Icon res
     */
    public void setIcon(@DrawableRes int iconRes) {
        hideText();

        icon.setImageResource(iconRes);
    }

    /**
     * Sets the tint to the icon
     */
    public void setIconTint(@ColorInt int color) {
        icon.setColorFilter(color, PorterDuff.Mode.SRC_IN);
    }

    /**
     * Sets the key background, by default it is a gray circle
     *
     * @param res Background
     */
    public void setKeyBackground(@DrawableRes int res) {
        text.setBackgroundResource(res);
        icon.setBackgroundResource(res);
    }

    /**
     * Returns the key background
     *
     * @return Background
     */
    public Drawable getKeyBackground() {
        return text.getBackground();
    }

    /**
     * Sets the key text color, gray by default
     *
     * @param color ColorInt
     */
    public void setTextColor(@ColorInt int color) {
        text.setTextColor(color);
    }

    /**
     * Returns the wrapper layout's background
     *
     * @return Background
     */
    public Drawable getWrapperBackground() {
        return root.getBackground();
    }

    /**
     * Sets the wrapper layout's background, this will disable the ripple effect
     * If you want to set your own background and keep the ripple effect, you have to implement the ripple animation by yourself
     *
     * @param res Background
     */
    public void setWrapperBackground(@DrawableRes int res) {
        root.setBackgroundResource(res);
    }

    /**
     * Sets the text size of the key
     *
     * @param sp Size in SP
     */
    public void setTextSize(float sp) {
        text.setTextSize(sp);
    }

    /**
     * Sets the sizes of the key, this will set both width and height
     *
     * @param size Sizes (width and height) in PX
     */
    public void setIconSize(int size) {
        LayoutParams params = (LayoutParams) icon.getLayoutParams();

        params.width = size;
        params.height = size;
    }

    /**
     * Returns the TextView that's used to display text, this is used for further customization
     *
     * @return Text's view
     */
    public TextView getTextView() {
        return text;
    }

    /**
     * Returns the ImageView that's used as icon, this is used for further customization
     *
     * @return Icon's view
     */
    public ImageView getImageView() {
        return icon;
    }

    /**
     * Replaces the text with an icon
     */
    public void hideIcon() {
        if (text.getVisibility() == GONE && icon.getVisibility() == VISIBLE) {
            text.setVisibility(VISIBLE);
            icon.setVisibility(GONE);
        }
    }

    /**
     * Replaces the icon with a text
     */
    public void hideText() {
        if (text.getVisibility() == VISIBLE && icon.getVisibility() == GONE) {
            text.setVisibility(GONE);
            icon.setVisibility(VISIBLE);
        }
    }
}
