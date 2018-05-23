package com.acadview.tarana;

import android.Manifest;
import android.app.Activity;
import android.content.ContentResolver;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.media.MediaPlayer;
import android.net.Uri;
import android.nfc.Tag;
import android.os.Handler;
import android.provider.MediaStore;
import android.support.annotation.NonNull;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.SeekBar;
import android.widget.TextView;
import android.widget.Toast;

import org.w3c.dom.Text;

import java.util.ArrayList;
import java.util.List;


public class MainActivity extends AppCompatActivity implements View.OnClickListener {
    ImageButton play, pause, frw, back;
    SeekBar seekBar;
    FloatingActionButton floatingActionButton;
    MediaPlayer mp;
    ArrayList<String> arrayList;
    ListView list;
    ArrayAdapter<String> adapter;
    TextView time;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        if(ContextCompat.checkSelfPermission(MainActivity.this, Manifest.permission.READ_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED){
            if(ActivityCompat.shouldShowRequestPermissionRationale(MainActivity.this,Manifest.permission.READ_EXTERNAL_STORAGE)) {
                ActivityCompat.requestPermissions(MainActivity.this, new String[]{Manifest.permission.READ_EXTERNAL_STORAGE}, 1);
            }else{
                ActivityCompat.requestPermissions(MainActivity.this,new String[]{Manifest.permission.READ_EXTERNAL_STORAGE},1);
            }
        }


        ImageButton play = (ImageButton) findViewById(R.id.play);
        ImageButton pause = (ImageButton) findViewById(R.id.pause);
        ImageButton frw = (ImageButton) findViewById(R.id.forw);
        ImageButton back = (ImageButton) findViewById(R.id.back);
        SeekBar seekBar = (SeekBar) findViewById(R.id.seekBar);
        FloatingActionButton floatingActionButton = (FloatingActionButton) findViewById(R.id.fab);
        ListView list = (ListView) findViewById(R.id.list);
        TextView time = (TextView) findViewById(R.id.time);
        arrayList = new ArrayList<>();

        floatingActionButton.setOnClickListener(this);
        play.setOnClickListener(this);
        pause.setOnClickListener(this);
        frw.setOnClickListener(this);
        back.setOnClickListener(this);
    }

    public void onClick(View v) {

        switch (v.getId()) {
            case R.id.fab:
                /*Uri songUri = MediaStore.Audio.Media.EXTERNAL_CONTENT_URI;*/
                Intent i = new Intent(Intent.ACTION_PICK, android.provider.MediaStore.Audio.Media.EXTERNAL_CONTENT_URI);
                startActivityForResult(i,0011);
                break;


            case R.id.play:
                mp.start();
                break;


            case R.id.pause:
                mp.pause();
                break;


            case R.id.forw:
                break;


            case R.id.back:
                break;

        }

    }

}

