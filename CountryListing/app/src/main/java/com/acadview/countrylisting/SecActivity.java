package com.acadview.countrylisting;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;

public class SecActivity extends AppCompatActivity {

    String Afghanistan[] = {"Kabul", "Farah", "Khost", "Khandhar"};
    String India[] = {"YamunaNagar", "Chandigarh", "Jagadhri", "Saharanpur"};
    String Australia[] = {"Perth", "Brisbane", "Melbourne"};
    String France[] = {"Paris", "Nice", "Cannes"};
    ArrayAdapter<String> arrayAdapter;

    ListView Lv2;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sec);

        Lv2 = (ListView) findViewById(R.id.Lv2);
        Lv2.setAdapter(arrayAdapter);
        Lv2.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int position, long l) {

                if (getIntent().getIntExtra("Country", position) == position) {
                    arrayAdapter = new ArrayAdapter<String>(getApplicationContext(), R.layout.activity_sec, R.id.Lv2,Afghanistan);
                } else if (getIntent().getIntExtra("Country", position) == position) {
                    arrayAdapter = new ArrayAdapter<String>(getApplicationContext(), R.layout.activity_sec, R.id.Lv2,India);
                } else if (getIntent().getIntExtra("Country",position) == position) {
                    arrayAdapter = new ArrayAdapter<String>(getApplicationContext(), R.layout.activity_sec, R.id.Lv2,Australia);
                }else if (getIntent().getIntExtra("Country", position) == position) {
                    arrayAdapter = new ArrayAdapter<String>(getApplicationContext(), R.layout.activity_sec, R.id.Lv2,France);
                }
            }


        });
    }
}
