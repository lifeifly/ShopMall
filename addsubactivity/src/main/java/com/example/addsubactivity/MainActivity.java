package com.example.addsubactivity;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;

public class MainActivity extends AppCompatActivity {
private AddSubLayout addSubLayout;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        addSubLayout=findViewById(R.id.add_sub_layout);
        addSubLayout.setListener(new AddSubLayout.NumberChangeListener() {
            @Override
            public void onNumberChange(int value) {

            }
        });
    }
}
