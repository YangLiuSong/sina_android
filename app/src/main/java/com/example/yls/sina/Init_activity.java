package com.example.yls.sina;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.example.yls.sina.check.check_activity;
import com.example.yls.sina.classification.classification_activity;

public class Init_activity extends AppCompatActivity {
    private Button btn_begin;
    private Button btn_check;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.init_activity);

        btn_begin = (Button)findViewById(R.id.button_begin);
        btn_check = (Button)findViewById(R.id.button_check);

        btn_begin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent();
                intent.setClass(Init_activity.this,classification_activity.class);
                startActivity(intent);
            }
        });

        btn_check.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent();
                intent.setClass(Init_activity.this,check_activity.class);
                startActivity(intent);
            }
        });

    }
}
