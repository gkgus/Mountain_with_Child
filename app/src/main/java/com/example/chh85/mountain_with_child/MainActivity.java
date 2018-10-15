package com.example.chh85.mountain_with_child;

import android.app.ActivityGroup;
import android.content.Intent;
import android.media.Image;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Button mt_edu = (Button) findViewById(R.id.mt_edu);
        Button recreation_forest_btn = (Button) findViewById(R.id.recreation_forest_btn);
        mt_edu.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent mt_eduintent = new Intent(getApplicationContext(),mt_edu2.class);
                startActivity(mt_eduintent);
            }

        });

        recreation_forest_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent mt_childintent = new Intent(getApplicationContext(),recreation_forest.class);
                startActivity(mt_childintent);
            }
        });

    }
}
