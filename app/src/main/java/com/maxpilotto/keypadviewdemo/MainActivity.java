package com.maxpilotto.keypadviewdemo;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.util.TypedValue;

import com.maxpilotto.keypadview.KeyPad;

import kotlin.Unit;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        KeyPad keypad = findViewById(R.id.keypad1);

        keypad.onClick(key -> {
            Log.d("KeyPadView", "Clicked: " + key.getText());

            return Unit.INSTANCE;   // Kotlin compatibility
        });

        keypad.onLongClick(key -> {
            Log.d("KeyPadView", "Long clicked: " + key.getText());

            return true;
        });
        keypad.setKeysBackground(android.R.color.transparent);
        keypad.getRightKey().setIconSize(
                (int) TypedValue.applyDimension(
                        TypedValue.COMPLEX_UNIT_DIP,
                        30,
                        getResources().getDisplayMetrics()
                )
        );

        keypad.setMargins(30);
    }
}

