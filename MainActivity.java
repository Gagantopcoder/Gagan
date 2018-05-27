package com.acadview.tarana;

import android.Manifest;
import android.app.Activity;
import android.app.Service;
import android.content.BroadcastReceiver;
import android.content.ComponentName;
import android.content.ContentResolver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.ServiceConnection;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.media.AudioManager;
import android.media.MediaMetadataRetriever;
import android.media.MediaPlayer;
import android.net.Uri;
import android.nfc.Tag;
import android.os.Handler;
import android.os.IBinder;
import android.provider.MediaStore;
import android.support.annotation.NonNull;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v4.content.LocalBroadcastManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.AppCompatSeekBar;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.MediaController;
import android.widget.SeekBar;
import android.widget.TextView;
import android.widget.Toast;

import org.w3c.dom.Text;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;


public class MainActivity extends AppCompatActivity implements View.OnClickListener {
    boolean isBinded = false;
    MediaPlaybackService mediaPlaybackService;
    ServiceConnection connection = new ServiceConnection() {
        @Override
        public void onServiceConnected(ComponentName name, IBinder service) {
            mediaPlaybackService = ((MediaPlaybackService.IDBinder) service).getService();
            isBinded = true;
            initInfos(mediaPlaybackService.getFile());
        }

        @Override
        public void onServiceDisconnected(ComponentName name) {
            isBinded = false;
        }
    };
    BroadcastReceiver receiverElapsedTime;
    BroadcastReceiver receiverCompleted;

    ViewGroup rootView;
    ImageView albumArt;
    TextView titleTextView;
    TextView artistTextView;
    TextView elapsedTimeTextView;
    TextView durationTextView;
    AppCompatSeekBar elapsedTimeSeekBar;
    FloatingActionButton fab;

    int elapsedTime = 0;
    ImageButton play, pause;
    SeekBar seekBar;
    FloatingActionButton floatingActionButton;
    MediaPlayer mediaPlaybackServices;
    ArrayList<String> arrayList;
    TextView time;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        setContentView(R.layout.activity_main);
        rootView = (ViewGroup) findViewById(android.R.id.content);

        receiverElapsedTime = new BroadcastReceiver() {
            @Override
            public void onReceive(Context context, Intent intent) {
                elapsedTime = intent.getIntExtra(MediaPlaybackService.MPS_MESSAGE, 0);
                updateElapsedTime(elapsedTime);
            }
        };

        receiverCompleted = new BroadcastReceiver() {
            @Override
            public void onReceive(Context context, Intent intent) {
                clearInfos();
            }
        };

        if (ContextCompat.checkSelfPermission(MainActivity.this, Manifest.permission.READ_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED) {
            if (ActivityCompat.shouldShowRequestPermissionRationale(MainActivity.this, Manifest.permission.READ_EXTERNAL_STORAGE)) {
                ActivityCompat.requestPermissions(MainActivity.this, new String[]{Manifest.permission.READ_EXTERNAL_STORAGE}, 1);
            } else {
                ActivityCompat.requestPermissions(MainActivity.this, new String[]{Manifest.permission.READ_EXTERNAL_STORAGE}, 1);
            }
        }


        ImageButton play = (ImageButton) findViewById(R.id.play);
        ImageButton pause = (ImageButton) findViewById(R.id.pause);
        ImageButton frw = (ImageButton) findViewById(R.id.forw);
        elapsedTimeTextView = (TextView) findViewById(R.id.textViewElapsedTime);
        durationTextView = (TextView) findViewById(R.id.textViewDuration);
        elapsedTimeSeekBar = (AppCompatSeekBar) findViewById(R.id.seekBar);
        ImageButton back = (ImageButton) findViewById(R.id.back);
        SeekBar seekBar = (SeekBar) findViewById(R.id.seekBar);
        FloatingActionButton floatingActionButton = (FloatingActionButton) findViewById(R.id.fab);
        TextView time = (TextView) findViewById(R.id.time);

        floatingActionButton.setOnClickListener(this);
        play.setOnClickListener(this);
        pause.setOnClickListener(this);
        frw.setOnClickListener(this);
        back.setOnClickListener(this);

        elapsedTimeSeekBar.setEnabled(false);
        elapsedTimeSeekBar.setProgress(0);
        elapsedTimeSeekBar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener()

        {


            @Override
            public void onProgressChanged (SeekBar seekBar,int progress, boolean fromUser){

            }

            @Override
            public void onStartTrackingTouch (SeekBar seekBar){

            }
            @Override
            public void onStopTrackingTouch (SeekBar seekBar){
                mediaPlaybackService.seekTo(seekBar.getProgress());
            }
        });
    }


    public void onClick(View v) {

        switch (v.getId()) {
            case R.id.fab:
                /*Uri songUri = MediaStore.Audio.Media.EXTERNAL_CONTENT_URI;*/
                Intent i = new Intent(Intent.ACTION_PICK, android.provider.MediaStore.Audio.Media.EXTERNAL_CONTENT_URI);
                startActivityForResult(i, 0011);
                break;


            case R.id.play:
                int resId;
                if (mediaPlaybackServices.isPlaying()) {
                    mediaPlaybackServices.pause();
                } else {
                    mediaPlaybackServices.isPlaying();
                }
                mediaPlaybackServices.start();
                break;


            case R.id.pause:
                mediaPlaybackServices.pause();
                break;


        }

    }


    @Override
    protected void onResume() {

        getApplicationContext().bindService(new Intent(getApplicationContext(),
                MediaPlaybackService.class), connection, BIND_AUTO_CREATE);

        LocalBroadcastManager.getInstance(this).registerReceiver(receiverElapsedTime,
                new IntentFilter(MediaPlaybackService.MPS_RESULT)
        );
        LocalBroadcastManager.getInstance(this).registerReceiver(receiverCompleted,
                new IntentFilter(MediaPlaybackService.MPS_COMPLETED)
        );

        super.onResume();
    }

    private String secondsToString(int time) {
        time = time / 1000;
        return String.format("%2d:%02d", time / 60, time % 60);
    }

    public void initInfos(Uri uri) {
        if (uri != null) {
            MediaMetadataRetriever mData = new MediaMetadataRetriever();
            mData.setDataSource(this, uri);

            int duration = Integer.parseInt(mData.extractMetadata(MediaMetadataRetriever.METADATA_KEY_DURATION));

            titleTextView.setText(mData.extractMetadata(MediaMetadataRetriever.METADATA_KEY_TITLE));
            artistTextView.setText(mData.extractMetadata(MediaMetadataRetriever.METADATA_KEY_ARTIST));
            durationTextView.setText(secondsToString(duration));

            elapsedTimeSeekBar.setMax(duration);
            elapsedTimeSeekBar.setEnabled(true);
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == RESULT_OK) {
            Uri selectedtrack = data.getData();
            mediaPlaybackService.init(selectedtrack);
            initInfos(selectedtrack);
        }
    }

    private void updateElapsedTime(int elapsedTime) {
        elapsedTimeSeekBar.setProgress(elapsedTime);
        elapsedTimeTextView.setText(secondsToString(elapsedTime));

        if (mediaPlaybackService.isPlaying()) {
            play.setEnabled(true);
        }
    }

    private void clearInfos() {
        durationTextView.setText("");
        elapsedTimeTextView.setText("");
        titleTextView.setText("-");
        artistTextView.setText("-");
        elapsedTime = 0;
        elapsedTimeSeekBar.setEnabled(false);
        elapsedTimeSeekBar.setProgress(0);
        play.setEnabled(false);
    }
}

