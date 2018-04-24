package com.acadview.mini_project;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

public class Main extends AppCompatActivity {

    Button submitBtn;
    EditText userNameEdt,pwdEdt;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity1);

            userNameEdt = (EditText) findViewById(R.id.predit1);
            pwdEdt = (EditText) findViewById(R.id.predit2);

            submitBtn = (Button) findViewById(R.id.prbutton);
            submitBtn.setOnClickListener(new View.OnClickListener() {

                @Override
                public void onClick(View view) {
                    //this line change arg0 >> view

                    if(view.getId() == R.id.prbutton);

                    String userName = userNameEdt.getText().toString();
                    String pwd = pwdEdt.getText().toString();

                    if(userName.equalsIgnoreCase("gagan") && pwd.equals("gagan")){

                        Intent intent = new Intent(Main.this, Home.class);
                        intent.putExtra("userName", userName);
                        startActivity(intent);






                    }



                }
            });
        }








    }
