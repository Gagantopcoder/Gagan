package com.acadview.registration_form;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

public class Sec extends AppCompatActivity implements View.OnClickListener{
    Button Edit,Finish;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sec);


        Button Edit = (Button) findViewById(R.id.button2);
        Edit.setOnClickListener(this);

        Button Finish = (Button) findViewById(R.id.Finish);
        Finish.setOnClickListener(this);


        Bundle b = getIntent().getExtras();
        TextView name = (TextView) findViewById(R.id.uservalue);
        TextView phone = (TextView) findViewById(R.id.phonevalue);
        TextView email = (TextView) findViewById(R.id.emailvalue);
        TextView city = (TextView) findViewById(R.id.cityvalue);


        name.setText(b.getCharSequence("name"));
        phone.setText(b.getCharSequence("phone"));
        email.setText(b.getCharSequence("email"));
        city.setText(b.getCharSequence("city"));
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {

            case R.id.button2:
                Intent intent = new Intent(getApplicationContext(),MainActivity.class);
                startActivity(intent);
                break;

            case R.id.Finish:
                Intent intent1 =new Intent(getApplicationContext(),Final.class);
                startActivity(intent1);
                break;
        }

    }


}
