package com.acadview.registration_form;

import android.content.Intent;
import android.provider.ContactsContract;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

public class MainActivity extends AppCompatActivity implements View.OnClickListener{

    EditText name,email,city,phone;
    Button submit;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        submit = (Button) findViewById(R.id.button);
        name = (EditText) findViewById(R.id.user);
        phone = (EditText) findViewById(R.id.phone);
        email = (EditText) findViewById(R.id.Email);
        city = (EditText) findViewById(R.id.city);

        submit.setOnClickListener(this);
    }

    public void onClick(View v) {
        Intent intent = new Intent(getApplicationContext(), Sec.class);

        Bundle b = new Bundle();
        b.putString("name", name.getText().toString());
        b.putString("phone", phone.getText().toString());
        b.putString("email", email.getText().toString());
        b.putString("city", city.getText().toString());

        //Add the bundle to the intent.
        intent.putExtras(b);

        //start the SecActivity
        startActivity(intent);


    }


}
