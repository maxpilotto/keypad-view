# Keypad View
An Android view that can be used to display a keypad

<div float="left">
    <img src="https://github.com/maxpilotto/keypad-view/blob/master/.github/s1.png" width="300">
    <img src="https://github.com/maxpilotto/keypad-view/blob/master/.github/s2.png" width="300">
    <img src="https://github.com/maxpilotto/keypad-view/blob/master/.github/s3.png" width="300">
    <img src="https://github.com/maxpilotto/keypad-view/blob/master/.github/s4.png" width="300">
    <img src="https://github.com/maxpilotto/keypad-view/blob/master/.github/s5.png" width="300">
</div>

# Getting started
In your project's `build.gradle`
```gradle
repositories {
	maven { url "https://jitpack.io" }
}
```

In your modules's `build.gradle`
```gradle 
dependencies {
    implementation 'com.github.maxpilotto:keypad-view:4.0'
}
```

# Usage
### XML
```Xml
<com.maxpilotto.keypadview.KeyPad
    android:id="@+id/keypad1"
    android:layout_width="match_parent"
    android:layout_height="match_parent"
    app:keysBackground="@android:color/transparent"
    app:keysIconSize="30dp"
    app:keysTextColor="#000000"
    app:keysTextSize="20dp"
    app:leftKeyIcon="@drawable/fingerprint"
    app:rightKeyIcon="@drawable/check" 
    app:keysIconTint="?attr/colorPrimary"
    app:keysMargin="10dp" />
```

### Java
```Java
KeyPad keypad = findViewById(R.id.keypad1);

keypad.onClick(key -> {
    Log.d("KeyPadView", "Clicked: " + key.getText());

    return Unit.INSTANCE;   // Kotlin compatibility
});

keypad.onLongClick(key -> {
    Log.d("KeyPadView", "Long clicked: " + key.getText());

    return true;
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
