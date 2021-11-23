package com.example.attendancev5.ui;

import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import com.example.attendancev5.R;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;

public class create_new_room extends AppCompatActivity {
    Button doneButton;
    ImageButton back;
    EditText roomEditTxt;
    FirebaseDatabase database;
    DatabaseReference myRef;
    String creatorMail;
    DatabaseReference reff;
    //String roomName;
    FirebaseDatabase dbRef;
    private ProgressDialog progressDialog;
    ArrayList<String> array  = new ArrayList<>();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_new_room);

        //getWindow().getDecorView().setBackgroundColor(Color.WHITE);

        Bundle bundle = getIntent().getExtras();
        if (bundle != null) {
            creatorMail = bundle.getString("mail");
        }
        back=findViewById(R.id.backButtonNewRoom);
        doneButton=findViewById(R.id.doneButton);
        roomEditTxt =findViewById(R.id.editTextTextRoomName);

        doneButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String roomName=roomEditTxt.getText().toString();
                if(roomName.equals(""))
                    Toast.makeText(create_new_room.this, "please enter room name", Toast.LENGTH_SHORT).show();
                else
                    {
                    database = FirebaseDatabase.getInstance();
                    myRef = database.getReference("rooms");
                    writeNewRooms(roomName, creatorMail);
                    finish();
                    Toast.makeText(create_new_room.this, "created", Toast.LENGTH_SHORT).show();
                    Intent DashbardIntent = new Intent(create_new_room.this, DashBoard.class);
                    DashbardIntent.putExtra("mail", creatorMail);
                    startActivity(DashbardIntent);
                }

            }
        });

        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
                Intent DashbardIntent = new Intent(create_new_room.this, DashBoard.class);
                DashbardIntent.putExtra("mail", creatorMail);
                startActivity(DashbardIntent);
            }
        });
    }

    private void writeNewRooms( String name, String email) {
        RoomDetails user = new RoomDetails( email);
        myRef.child(name).setValue(user);

    }

}