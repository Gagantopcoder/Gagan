package com.acadview.login_signup;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

public class Sec extends AppCompatActivity implements View.OnClickListener{

    Button Register;
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sec);
        Register = (Button)findViewById(R.id.button);

        Register.setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        Toast.makeText(getApplicationContext(),"User Registered",Toast.LENGTH_LONG).show();
    }
}
