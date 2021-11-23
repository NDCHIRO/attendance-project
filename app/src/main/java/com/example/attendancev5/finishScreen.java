package com.example.attendancev5;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.graphics.Color;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.attendancev5.authentication.MainActivity;
import com.example.attendancev5.ui.RoomDetails;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import java.util.Map;

public class finishScreen extends AppCompatActivity {
    FirebaseDatabase database;
    DatabaseReference myRef;
    String mail;
    String roomNameResult;
    TextView finish;
    TextView testTextView;
    String test;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_finish_screen);

        getWindow().getDecorView().setBackgroundColor(Color.WHITE);

        testTextView=findViewById(R.id.textView6);
        database = FirebaseDatabase.getInstance();
        myRef = database.getReference("rooms");
        finish=findViewById(R.id.finishTextView);
        test="";





        Bundle bundle = getIntent().getExtras();
        if (bundle != null) {
            roomNameResult = bundle.getString("room");
            mail=bundle.getString("mail");
        }

        //RoomDetails user = new RoomDetails(mail);
        myRef.child(roomNameResult).child("email").setValue(mail);

        finish.setText("room: "+roomNameResult+"\n"+"mail: "+mail);
        //Toast.makeText(this,"attendance taken",Toast.LENGTH_LONG).show();
        myRef=FirebaseDatabase.getInstance().getReference("rooms");
        if(FirebaseDatabase.getInstance().getReference("rooms").child(roomNameResult).equals(null)==false)
        {
            Log.i("Send email", "");
            String query=FirebaseDatabase.getInstance().getReference("rooms").child(roomNameResult)+"";
            /*query.addListenerForSingleValueEvent(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot snapshot) {
                    if(snapshot.exists())
                    {
                        //get the value which is the mail
                        test+=snapshot.getValue();
                        finish.setText(test);

                    }
                }

                @Override
                public void onCancelled(@NonNull DatabaseError error) {

                }
            });*/
            String reciever="amrmohamed81298@gmail.com";
            sendEmail(mail);
        }
        else
        {
            Toast.makeText(this,"please scan again the right room",Toast.LENGTH_LONG).show();
        }
        //get first child in a node


        /*
        query.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                if(snapshot.exists())
                {
                    for(DataSnapshot snap: snapshot.getChildren())
                    {
                        //get the value which is the mail
                        test+=snapshot.getValue();
                        finish.setText(test+"  ggg");
                    }
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
         */
    }

    protected void sendEmail(String sender) {
        //Log.i("Send email", "");

        String[] TO = {"amrmohamed81298@gmail.com"};
        String[] CC = {sender};
        Intent emailIntent = new Intent(Intent.ACTION_SEND);
        emailIntent.setData(Uri.parse("mailto:"));
        emailIntent.setType("text/plain");


        emailIntent.putExtra(Intent.EXTRA_EMAIL, TO);
        //emailIntent.putExtra(Intent.EXTRA_CC, CC);
        emailIntent.putExtra(Intent.EXTRA_SUBJECT, roomNameResult+" attendance");
        emailIntent.putExtra(Intent.EXTRA_TEXT, "Done");

        try {
            startActivity(Intent.createChooser(emailIntent, "Send mail..."));
            finish();
            //Log.i("Finished sending email...", "");
        } catch (android.content.ActivityNotFoundException ex) {
            Toast.makeText(finishScreen.this,
                    "There is no email client installed.", Toast.LENGTH_SHORT).show();
        }
    }
}