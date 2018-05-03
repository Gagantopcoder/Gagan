package com.acadview.sms;

import android.Manifest;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.telephony.SmsManager;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity implements View.OnClickListener{

    EditText name,email,city,phone;
    TextView value , secvalue, thirdvalue, fourthvalue,code;
    SharedPreferences sharedPreferences;
    Button submit,sendotp;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        submit = (Button) findViewById(R.id.button);
        sendotp = (Button) findViewById(R.id.OTP);
        name = (EditText) findViewById(R.id.user);
        phone = (EditText) findViewById(R.id.phone);
        email = (EditText) findViewById(R.id.Email);
        city = (EditText) findViewById(R.id.city);
        code = (TextView) findViewById(R.id.code);
        code.setText("8930");
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


        if(ActivityCompat.checkSelfPermission(this, Manifest.permission.SEND_SMS)
                != PackageManager.PERMISSION_GRANTED ) {
            Toast.makeText(this, "Permission is not yet Granted", Toast.LENGTH_LONG).show();
            ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.SEND_SMS}, 111);
            submit.setEnabled(false);
        }else{
            Toast.makeText(this,"Permission Granted",Toast.LENGTH_LONG).show();
            submit.setEnabled(true);
        }

        sendotp.setOnClickListener(this);
        submit.setOnClickListener(this);
    }

    public void onClick(View v) {

        switch (v.getId()) {
            case R.id.button:
                Toast.makeText(MainActivity.this, "Submit is Clicked", Toast.LENGTH_LONG).show();
                String UN = name.getText().toString();
                String mobile = phone.getText().toString();
                String mail = email.getText().toString();
                String Ci = city.getText().toString();

                SharedPreferences.Editor editor = sharedPreferences.edit();
                editor.putString("name", UN);
                editor.putString("phone", mobile);
                editor.putString("email", mail);
                editor.putString("city", Ci);
                editor.commit();


                Bundle b = new Bundle();
                b.putString("name", name.getText().toString());
                b.putString("phone", phone.getText().toString());
                b.putString("email", email.getText().toString());
                b.putString("city", city.getText().toString());
                break;

            case R.id.OTP:
                SmsManager sms = SmsManager.getDefault();
                sms.sendTextMessage(phone.getText().toString(),null, code.getText().toString(),null,null);
                break;


        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if(requestCode==111){
            if(grantResults.length>0 && grantResults[0]== PackageManager.PERMISSION_GRANTED){
                Toast.makeText(this,"Permission Granted",Toast.LENGTH_LONG).show();
                submit.setEnabled(true);
            }else{
                submit.setEnabled(false);
            }
        }
    }
}
