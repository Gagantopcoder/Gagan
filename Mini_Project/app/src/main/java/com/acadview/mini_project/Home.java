package com.acadview.mini_project;

import android.content.Intent;
import android.graphics.Bitmap;
import android.provider.MediaStore;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

public class Home extends AppCompatActivity{
    Button profileButton;
    ImageView imgView;
    Button exit;
    Button bt2;
    Button bt3;

    Button bt4;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

        bt3 = (Button) findViewById(R.id.calcubtn);
        bt3.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View arg0) {
                // TODO Auto-generated method stub
                Intent intent=new Intent(Home.this,Cal.class);


                startActivity(intent);
            }


        });

        bt4 = (Button) findViewById(R.id.currncybtn);
        bt4.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View arg0) {
                // TODO Auto-generated method stub
                Intent intent=new Intent(Home.this,Convert.class);


                startActivity(intent);
            }


        });


        Button exit = (Button)findViewById(R.id.exit);
        exit.setOnClickListener(new View.OnClickListener(){

            @Override
            public void onClick(View v) {
                // TODO Auto-generated method stub
                finish();
                System.exit(0);
            }

        });



        Intent i = getIntent();
        String userName = i.getStringExtra("userName");

        Toast.makeText(this, "Welcome:" +userName, Toast.LENGTH_SHORT).show();


        profileButton = (Button)findViewById(R.id.pro1btn);
        imgView = (ImageView)findViewById(R.id.imageVi1);
        profileButton.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View arg0) {
                Intent takepictureIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                startActivityForResult(takepictureIntent,101);
            }
        });


    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data){
        super.onActivityResult(requestCode, resultCode, data);
        if(requestCode  ==  101){
            if(resultCode == RESULT_OK);
            Bundle extraBundle = data.getExtras();
            Bitmap imageBitmap = (Bitmap)extraBundle.get("data");
            imgView.setImageBitmap(imageBitmap);
        }else if(resultCode == RESULT_CANCELED){;


            Toast.makeText(this, "User cancelled ", Toast.LENGTH_SHORT).show();


        }

    }



    }

