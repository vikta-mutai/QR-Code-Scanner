package com.victor.qrcode;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.google.zxing.integration.android.IntentIntegrator;
import com.google.zxing.integration.android.IntentResult;

public class MainActivity extends AppCompatActivity {
    //initialize variable
    private Button scan;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        //asigning variable
        scan = findViewById(R.id.scanBtn);
        scan.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //initialize intent integrator
                IntentIntegrator intentIntegrator = new IntentIntegrator(MainActivity.this);
                //setting prompt
                intentIntegrator.setPrompt("For flash use volume up button");
                //setting beep
                intentIntegrator.setBeepEnabled(true);
                //locked orientation
                intentIntegrator.setOrientationLocked(true);
                //setting capture activity
                intentIntegrator.setCaptureActivity(Capture.class);
                //initiate scan
                intentIntegrator.initiateScan();

            }
        });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        //initialize intent result
        IntentResult intentResult = IntentIntegrator.parseActivityResult(
                requestCode, resultCode, data
        );
        //check condition
        if (intentResult.getContents() != null){
            //when result content is not null
            //initialize alert dialog
            AlertDialog.Builder builder = new AlertDialog.Builder(MainActivity.this);
            //set title
            builder.setTitle("Result");
            //set message
            builder.setMessage(intentResult.getContents());
            //set positive button
            builder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialogInterface, int i) {
                    //dismiss dialog
                    dialogInterface.dismiss();
                }
            });
            //show alert dialog
            builder.show();

        }else{
            //when result content is null
            //display a toast
            Toast.makeText(getApplicationContext(),
                    "OOPS.....You did not scan anything", Toast.LENGTH_SHORT).show();
        }
    }
}