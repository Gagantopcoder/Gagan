package com.acadview.loginintent;

import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Toast;

/**
 * Created by gagan on 4/16/2018.
 */

public class Student extends AppCompatActivity {

    Button profileButton;
    ImageView imgView;
    Button call;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.student);


        findViewById(R.id.call).setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View arg0) {
                dialContactPhone("9995486411");

            }

            private void dialContactPhone(final String phoneNumber) {
                startActivity(new Intent(Intent.ACTION_DIAL, Uri.fromParts("tel", phoneNumber, null)));

            }

        });



        Intent i = getIntent();
        String userName = i.getStringExtra("userName");

        Toast.makeText(this, "Welcome:" +userName, Toast.LENGTH_SHORT).show();


        profileButton = (Button)findViewById(R.id.prbtn);
        imgView = (ImageView)findViewById(R.id.imageView1);
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
