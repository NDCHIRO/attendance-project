package com.example.attendancev5;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.example.attendancev5.ui.DashBoard;
import com.example.attendancev5.ui.create_new_room;
import com.google.firebase.auth.EmailAuthProvider;
import com.google.zxing.integration.android.IntentIntegrator;
import com.google.zxing.integration.android.IntentResult;

public class QRScanner extends AppCompatActivity {
    Button QRbtn;
    String mail;
    String roomNameResult;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_q_r_scanner);

        getWindow().getDecorView().setBackgroundColor(Color.WHITE);

        Bundle bundle = getIntent().getExtras();
        if (bundle != null) {
            mail = bundle.getString("mail");
        }

        QRbtn=findViewById(R.id.QRButton);
        QRbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                scanCode();
            }
        });
    }

    private void scanCode() {
        IntentIntegrator integrator=new IntentIntegrator(this);
        integrator.setCaptureActivity(CaptureAct.class);
        integrator.setOrientationLocked(false);
        integrator.setDesiredBarcodeFormats(IntentIntegrator.ALL_CODE_TYPES);
        integrator.setPrompt("Scanning Code");
        integrator.initiateScan();
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        //super.onActivityResult(requestCode, resultCode, data);
        IntentResult result = IntentIntegrator.parseActivityResult(requestCode, resultCode, data);
        if (result != null) {
            if (result.getContents() != null) {
                AlertDialog.Builder builder = new AlertDialog.Builder(this);
                builder.setMessage("room name: "+result.getContents());
                roomNameResult=result.getContents();
                builder.setTitle("Scanning Result");
                builder.setPositiveButton("finish", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which)
                    {
                        Intent finishIntent = new Intent(QRScanner.this, finishScreen.class);
                        finishIntent.putExtra("room", roomNameResult);
                        finishIntent.putExtra("mail",mail);
                        //finishIntent.putExtra(Intent.EXTRA_EMAIL, mail);
                        startActivity(finishIntent);
                    }
                });
                builder.setNegativeButton("Scan again!", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which)
                    {
                        scanCode();
                    }
                });
                AlertDialog dialog=builder.create();
                dialog.show();
            }
            else
            {
                Toast.makeText(this,"No results",Toast.LENGTH_LONG).show();
            }
        }
    }
}