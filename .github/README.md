# keypad-view
An Android view that can be used to display a keypad

<img src="https://github.com/maxpilotto/keypad-view/blob/master/.github/s1.png" width="300">
<img src="https://github.com/maxpilotto/keypad-view/blob/master/.github/s2.png" width="300">
<img src="https://github.com/maxpilotto/keypad-view/blob/master/.github/s3.png" width="300">

# Getting started
```gradle 
dependencies {
	implementation 'com.maxpilotto:keypad-view:1.0'
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
    app:keysWrapperBackground="?attr/selectableItemBackground" />   //This is used to show the ripple effect, already set by default
```

### Java
```Java
KeyPad keyPad1 = findViewById(R.id.keypad1);
keyPad1.setKeysTextColor(Color.GREEN);
keypad1.setKeysBackground(R.drawable.bg_green);
keyPad1.setKeyListener(new KeyPad.OnKeyClickListener() {
    @Override public void onKeyClick(Key key) {
        Toast.makeText(MainActivity.this, key.getKey() + " pressed", Toast.LENGTH_SHORT).show();
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
The Keypad view uses a wrapper around the Key and then a horizontal linear layout as a row, like the following
```xml
<LinearLayout
    android:layout_width="wrap_content"
    android:layout_height="wrap_content"
    android:layout_weight="1"
    android:gravity="center_horizontal">

    <com.maxpilotto.keypadview.Key
        android:layout_width="60dp"
        android:layout_height="60dp"
        app:keyValue="1"/>

</LinearLayout>
```
The row will look like this
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
            app:keyValue="1"/>

    </LinearLayout>

    <LinearLayout
        android:layout_width="wrap_content"
        android:layout_height="wrap_content"
        android:layout_weight="1"
        android:gravity="center_horizontal">

        <com.maxpilotto.keypadview.Key
            android:layout_width="60dp"
            android:layout_height="60dp"
            app:keyValue="2"/>

    </LinearLayout>

    <LinearLayout
        android:layout_width="wrap_content"
        android:layout_height="wrap_content"
        android:layout_weight="1"
        android:gravity="center_horizontal">

        <com.maxpilotto.keypadview.Key
            android:layout_width="60dp"
            android:layout_height="60dp"
            app:keyValue="3"/>

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

# Documentation

## Key attributes
Xml Attribute | Value/Type | Description 
--------------|------------|------------
keyValue|string|Value of the key
keyBackground|DrawableRes|Background of the key
keyWrapperBackground|DrawableRes|Background of the outer key's layout
keyTextSize|dimension|Text size of the key
keyTextColor|ColorInt|Text color of the key

## Keypad attributes
Xml Attribute | Value/Type | Description 
--------------|------------|------------
showLeftButton|boolean|Show/Hide the button on the bottom left, hidden by default
showRightButton|boolean|Show/Hide the button on the bottom right, hidden by default
leftButtonText|string|Bottom left button's text
rightButtonText|string|Bottom right button's text
keysBackground|DrawableRes|Background of all keys
keysWrapperBackground|DrawableRes|Background of all keys' wrapper
keysTextSize|dimension|Text size of all keys
keysTextColor|ColorInt|Text color of all keys

## Javadoc
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