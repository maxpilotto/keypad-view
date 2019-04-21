package com.maxpilotto.keypadviewdemo;

import android.graphics.Color;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.Toast;

import com.maxpilotto.keypadview.Key;
import com.maxpilotto.keypadview.KeyPad;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        KeyPad keyPad1 = findViewById(R.id.keypad1);
        keyPad1.setKeysTextColor(Color.BLUE);
        keyPad1.setRightButton("OK");
        keyPad1.setLeftButton("DEL");

        KeyPad keyPad2 = findViewById(R.id.keypad2);
        keyPad2.setKeyListener(new KeyPad.OnKeyClickListener() {
            @Override public void onKeyClick(Key key) {
                Toast.makeText(MainActivity.this, key.getKey() + " pressed", Toast.LENGTH_SHORT).show();
            }
        });
    }
}
