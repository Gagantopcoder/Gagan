package com.acadview.login_signup;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.Toast;

public class Main extends AppCompatActivity implements View.OnClickListener {

    EditText User, Password, Email;
    RadioButton Gender;
    Button Login, Signup;
    int counter = 3;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Login = (Button) findViewById(R.id.login);
        Password = (EditText) findViewById(R.id.pass);
        User = (EditText) findViewById(R.id.user);
        Email = (EditText) findViewById(R.id.email);
        Gender = (RadioButton) findViewById(R.id.gender);
        Signup = (Button) findViewById(R.id.signup);

        Login.setOnClickListener(this);
        Signup.setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.login:
                if (User.getText().toString().equals("admin") && Password.getText().toString().equals("admin")) {
                    Toast.makeText(getApplicationContext(), "LOGIN  Successfull", Toast.LENGTH_SHORT).show();
                } else {
                    Toast.makeText(getApplicationContext(), "Wrong Credentials", Toast.LENGTH_SHORT).show();

                    counter--;
                    User.setText(Integer.toString(counter));

                    if (counter == 0) {
                        Login.setEnabled(false);
                    }
                }
                break;
            case R.id.signup:
                Intent intent = new Intent(getApplicationContext(),Sec.class);
                startActivity(intent);
                break;
        }
    }
}