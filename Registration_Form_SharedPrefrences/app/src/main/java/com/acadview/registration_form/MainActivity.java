package com.acadview.registration_form;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.provider.ContactsContract;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity implements View.OnClickListener{

    EditText name,email,city,phone;
    TextView value , secvalue, thirdvalue, fourthvalue;
    SharedPreferences sharedPreferences;
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
        sharedPreferences=getSharedPreferences("pref", Context.MODE_PRIVATE);

        value =  (TextView)findViewById(R.id.Spv);
        secvalue =  (TextView)findViewById(R.id.textView8);
        thirdvalue =  (TextView)findViewById(R.id.textView9);
        fourthvalue =  (TextView)findViewById(R.id.textView10);
        if (sharedPreferences.contains("name")){
            value.setText(sharedPreferences.getString("name","dafault"));
        }if (sharedPreferences.contains("phone")){
            secvalue.setText(sharedPreferences.getString("phone","default"));
        }if (sharedPreferences.contains("email")){
            thirdvalue.setText(sharedPreferences.getString("email","default"));
        }if (sharedPreferences.contains("city")){
            fourthvalue.setText(sharedPreferences.getString("city","default"));
        }



        submit.setOnClickListener(this);
    }

    public void onClick(View v) {

        Toast.makeText(MainActivity.this,"Submit is Clicked",Toast.LENGTH_LONG).show();
        String UN = name.getText().toString();
        String mobile = phone.getText().toString();
        String mail = email.getText().toString();
        String Ci = city.getText().toString();

        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putString("name",UN);
        editor.putString("phone",mobile);
        editor.putString("email",mail);
        editor.putString("city",Ci);
        editor.commit();




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
