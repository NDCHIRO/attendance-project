package com.example.attendancev5.ui;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.Toast;

import com.example.attendancev5.R;

public class creat_or_join_room extends AppCompatActivity {
    ImageButton addRoom;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_creat_or_join_room);

        addRoom=findViewById(R.id.addRoomButton);
        addRoom.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //Toast.makeText(MainActivity.this,"Login Successfully",Toast.LENGTH_LONG).show();
                //Intent intent=new Intent(MainActivity.this,DashboardActivity.class);
                //startActivity(intent);
                //startActivityForResult(intent, NEW_WORD_ACTIVITY_REQUEST_CODE);
            }
        });
    }
}