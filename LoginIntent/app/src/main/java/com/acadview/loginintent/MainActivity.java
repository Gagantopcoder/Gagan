package com.acadview.loginintent;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

public class MainActivity extends AppCompatActivity {
    Button submitBtn;
    EditText userNameEdt,pwdEdt;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        userNameEdt = (EditText) findViewById(R.id.edit112);
        pwdEdt = (EditText) findViewById(R.id.edit113);

        submitBtn = (Button) findViewById(R.id.button1);
        submitBtn.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View view) {
                //this line change arg0 >> view

                if(view.getId() == R.id.button1);

                String userName = userNameEdt.getText().toString();
                String pwd = pwdEdt.getText().toString();

                if(userName.equalsIgnoreCase("student") && pwd.equals("student")){

                    Intent intent = new Intent(MainActivity.this, Student.class);
                    intent.putExtra("userName", userName);
                    startActivity(intent);





                }else if(userName.equals("instructor") && pwd.equals("instructor")){
                    Intent intent = new Intent(MainActivity.this, Ins.class);
                    intent.putExtra("userName", userName);
                    startActivity(intent);
                }



            }
        });
    }






}
