package com.maxpilotto.keypadviewdemo;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;

import com.maxpilotto.keypadview.Key;
import com.maxpilotto.keypadview.KeyPad;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        KeyPad keypad = findViewById(R.id.keypad1);

        keypad.setOnClickListener(new KeyPad.KeyClickListener() {
            @Override public void onClick(Key key) {
                Log.d("KeyPadView", "Clicked: " + key.getText());
            }
        });

        keypad.setOnLongClickListener(new KeyPad.KeyLongClickListener() {
            @Override public boolean onLongClick(Key key) {
                Log.d("KeyPadView", "Long clicked: " + key.getText());

                return true;
            }
        });

        keypad.getKey(1).setKeyBackground(R.drawable.bg_green);
        keypad.getKey(4).setKeyBackground(R.drawable.bg_green);
        keypad.getKey(7).setKeyBackground(R.drawable.bg_green);
        keypad.getKey(2).setKeyBackground(R.drawable.key_background);
        keypad.getKey(5).setKeyBackground(R.drawable.key_background);
        keypad.getKey(8).setKeyBackground(R.drawable.key_background);
        keypad.getKey(3).setKeyBackground(R.drawable.bg_red);
        keypad.getKey(6).setKeyBackground(R.drawable.bg_red);
        keypad.getKey(9).setKeyBackground(R.drawable.bg_red);
        keypad.getKey(0).setKeyBackground(R.drawable.bg_blue);

        keypad.getRightKey().setKeyBackground(0);

        keypad.setKeysMargins(30);
    }
}

