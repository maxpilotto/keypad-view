# keypad-view
An Android view that can be used to display a keypad

<img src="https://github.com/maxpilotto/keypad-view/blob/master/.github/s1.png" width="200">
<img src="https://github.com/maxpilotto/keypad-view/blob/master/.github/s2.png" width="200">
<img src="https://github.com/maxpilotto/keypad-view/blob/master/.github/s3.png" width="200">
<img src="https://github.com/maxpilotto/keypad-view/blob/master/.github/s4.png" width="200">

# Getting started
```gradle 
dependencies {
	implementation 'com.maxpilotto:keypad-view:3.0'
	
	// One of these might be needed
    implementation 'com.android.support.constraint:constraint-layout:1.1.3'		// If you ARE NOT using AndroidX
	implementation 'androidx.constraintlayout:constraintlayout:2.0.0-alpha3'	// If you ARE using AndroidX
}
```

# Usage
### XML
```Xml
<com.maxpilotto.keypadview.KeyPad
    android:id="@+id/keypad1"
    android:layout_width="match_parent"
    android:layout_height="wrap_content"
    app:keysBackground="@drawable/bg_green"
    app:keysTextColor="@android:color/holo_green_dark" />

<com.maxpilotto.keypadview.KeyPad
    android:id="@+id/keypad2"
    android:layout_width="match_parent"
    android:layout_height="wrap_content"
    app:showLeftButton="true"
    app:leftButtonText="DEL"
    app:showRightButton="true"
    app:rightButtonText="OK"
    app:keysTextColor="@android:color/holo_red_dark"
    app:keysBackground="@drawable/bg_red"
    app:keysWrapperBackground="?attr/selectableItemBackground" />
```

### Java
```Java
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
```

# Designing your own keypad
Use the Key view to display the key of a keypad
```xml
<com.maxpilotto.keypadview.Key
    android:layout_width="60dp"
    android:layout_height="60dp"
    app:keyValue="1"/>
```
To create a row you can use any method you like (GridLayout,TableLayout ..)  
The old Keypad layout used to have a wrapper around the Key and then a horizontal linear layout as a row, like the following
```xml
<LinearLayout
    android:layout_width="wrap_content"
    android:layout_height="wrap_content"
    android:layout_weight="1"
    android:gravity="center_horizontal">

    <com.maxpilotto.keypadview.Key
        android:layout_width="60dp"
        android:layout_height="60dp"
        app:keyText="1"/>

</LinearLayout>
```
The row could look like this
```xml
<LinearLayout
    android:layout_width="match_parent"
    android:layout_height="wrap_content"
    android:orientation="horizontal"
    android:layout_marginTop="10dp">

    <LinearLayout
        android:layout_width="wrap_content"
        android:layout_height="wrap_content"
        android:layout_weight="1"
        android:gravity="center_horizontal">

        <com.maxpilotto.keypadview.Key
            android:layout_width="60dp"
            android:layout_height="60dp"
            app:keyText="1"/>

    </LinearLayout>

    <LinearLayout
        android:layout_width="wrap_content"
        android:layout_height="wrap_content"
        android:layout_weight="1"
        android:gravity="center_horizontal">

        <com.maxpilotto.keypadview.Key
            android:layout_width="60dp"
            android:layout_height="60dp"
            app:keyText="2"/>

    </LinearLayout>

    <LinearLayout
        android:layout_width="wrap_content"
        android:layout_height="wrap_content"
        android:layout_weight="1"
        android:gravity="center_horizontal">

        <com.maxpilotto.keypadview.Key
            android:layout_width="60dp"
            android:layout_height="60dp"
            app:keyText="3"/>

    </LinearLayout>

</LinearLayout>
```

## Handling click events with a custom Keypad
The Keypad class has a static method `findAllKeys(ViewGroup,List<Key>)` that searches recursively for all Key view instances, this should be used like this
```java
public void setAllKeysClickListener(){
    List<Key> keys = new ArrayList<>();

    Keypad.findAllKeys(yourLayoutRoot,keys);

    for (Key k : keys){
        k.setOnClickListener(new OnClickListener() {
            @Override public void onClick(View v) {
                Log.d("Debug","Key was pressed");
            }
        });
    }
}
```

# Check out the [Demo](https://github.com/maxpilotto/keypad-view/releases)

# Javadoc (v 1.0)
You can find the Javadoc [here](http://maxpilotto.com/docs/keypad-view/index.html)

# License
Copyright 2018 Max Pilotto

Licensed under the Apache License, Version 2.0 (the "License");
you may not use this file except in compliance with the License.
You may obtain a copy of the License at

    http://www.apache.org/licenses/LICENSE-2.0

Unless required by applicable law or agreed to in writing, software
distributed under the License is distributed on an "AS IS" BASIS,
WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
See the License for the specific language governing permissions and
limitations under the License.
