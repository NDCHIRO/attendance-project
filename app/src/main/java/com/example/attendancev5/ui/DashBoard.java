package com.example.attendancev5.ui;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.solver.state.State;
import androidx.constraintlayout.widget.ConstraintLayout;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.example.attendancev5.R;
import com.example.attendancev5.authentication.MainActivity;
import com.example.attendancev5.location;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class DashBoard extends AppCompatActivity {
    TextView txt;
    ConstraintLayout layout;
    DatabaseReference myRef= FirebaseDatabase.getInstance().getReference();
    Button joinRoom;
    Button createRoom;
    String mail;
    Button signOut;
    //private StorageReference storageReference;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dash_board);
        createRoom=findViewById(R.id.createRoomButton);
        joinRoom=findViewById(R.id.joinRoomButton);

        //set background to white
        getWindow().getDecorView().setBackgroundColor(Color.WHITE);

        layout=findViewById(R.id.backGround);
        //layout.setBackground(white);
        signOut=findViewById(R.id.signOutbutton);
        txt=findViewById(R.id.textView2);
        Bundle bundle = getIntent().getExtras();
        if (bundle != null) {
            mail = bundle.getString("mail");
            txt.setText("welcome " + mail.toString());
        }

        createRoom.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent newRoomIntent = new Intent(DashBoard.this, create_new_room.class);
                newRoomIntent.putExtra("mail", mail);
                startActivity(newRoomIntent);
            }
        });

        joinRoom.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent joinRoomIntent = new Intent(DashBoard.this, location.class);
                joinRoomIntent.putExtra("mail", mail);
                startActivity(joinRoomIntent);
            }
        });
        signOut.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                FirebaseAuth.getInstance().signOut();
                Intent logInIntent = new Intent(DashBoard.this, MainActivity.class);
                logInIntent.putExtra("mail", mail);
                startActivity(logInIntent);
            }
        });

    }
}