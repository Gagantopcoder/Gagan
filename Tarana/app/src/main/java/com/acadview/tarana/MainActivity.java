package com.acadview.tarana;

import android.Manifest;
import android.content.BroadcastReceiver;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.ServiceConnection;
import android.content.pm.PackageManager;
import android.media.MediaMetadataRetriever;
import android.media.MediaPlayer;
import android.net.Uri;
import android.os.IBinder;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v4.content.LocalBroadcastManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.AppCompatSeekBar;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.SeekBar;
import android.widget.TextView;

import java.util.ArrayList;


public class MainActivity extends AppCompatActivity {
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
    ImageButton buttonPlayPause;
    ImageButton buttonStop;
    TextView titleTextView;
    TextView artistTextView;
    TextView elapsedTimeTextView;
    TextView durationTextView;
    AppCompatSeekBar elapsedTimeSeekBar;

    int elapsedTime = 0;
    FloatingActionButton floatingActionButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
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


        elapsedTimeTextView = (TextView) findViewById(R.id.textViewElapsedTime);
        durationTextView = (TextView) findViewById(R.id.textViewDuration);
        elapsedTimeSeekBar = (AppCompatSeekBar) findViewById(R.id.seekBar);
        titleTextView = (TextView) findViewById(R.id.textViewTitle);
        artistTextView = (TextView) findViewById(R.id.textViewArtist);
        buttonStop = (ImageButton) findViewById(R.id.imageButtonStop);
        FloatingActionButton floatingActionButton = (FloatingActionButton) findViewById(R.id.fab);
        buttonPlayPause = (ImageButton) findViewById(R.id.imageButtonPlayPause);
        buttonPlayPause.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                int restId;
                if (mediaPlaybackService.isPlaying()) {
                    restId=R.drawable.pauses;
                    mediaPlaybackService.pause();
                } else {
                    restId=R.drawable.plays;
                    mediaPlaybackService.play();
                }
                buttonPlayPause.setImageResource(restId);
            }
        });
        buttonPlayPause.setEnabled(true);

        buttonStop.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mediaPlaybackService.stop();
                buttonPlayPause.setImageResource(R.drawable.plays);
                buttonPlayPause.setEnabled(false);
                clearInfos();
            }
        });


        elapsedTimeSeekBar.setEnabled(false);
        elapsedTimeSeekBar.setProgress(0);
        elapsedTimeSeekBar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener()

        {


            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {

            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {
                mediaPlaybackService.seekTo(seekBar.getProgress());
            }
        });


        floatingActionButton.setOnClickListener(new View.OnClickListener()

        {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent();
                intent.setType("audio/*");
                intent.setAction(Intent.ACTION_GET_CONTENT);
                startActivityForResult(Intent.createChooser(intent, "Choose Track"), 1);
            }
        });
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
    @Override
    protected void onPause() {
        LocalBroadcastManager.getInstance(this).unregisterReceiver(receiverElapsedTime);
        LocalBroadcastManager.getInstance(this).unregisterReceiver(receiverCompleted);
        super.onPause();
    }



    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if(resultCode==RESULT_OK)
        {
            Uri selectedtrack = data.getData();
            mediaPlaybackService.init(selectedtrack);
            initInfos(selectedtrack);
        }
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


    private void updateElapsedTime(int elapsedTime) {
        elapsedTimeSeekBar.setProgress(elapsedTime);
        elapsedTimeTextView.setText(secondsToString(elapsedTime));

        if (mediaPlaybackService.isPlaying()) {
            buttonPlayPause.setEnabled(true);
            buttonPlayPause.setImageResource(R.drawable.plays);
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
        buttonPlayPause.setEnabled(false);
        buttonPlayPause.setImageResource(R.drawable.plays);
    }



}
