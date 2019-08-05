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
                Log.d("KeyPadView","Clicked: " + key.getKey());
            }
        });

        keypad.setOnLongClickListener(new KeyPad.KeyLongClickListener() {
            @Override public boolean onLongClick(Key key) {
                Log.d("KeyPadView","Long clicked: " + key.getKey());

                return true;
            }
        });
    }
}
