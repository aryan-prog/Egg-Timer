package com.example.eggtimer;

import androidx.appcompat.app.AppCompatActivity;

import android.media.MediaPlayer;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.SeekBar;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {
    TextView timeText;
    SeekBar timerSeekBar;
    Button goButton;
    boolean counterisActive=false;
    CountDownTimer count;
    MediaPlayer mplayer;
    MediaPlayer ticktock;
    int f=0;
    public void resetTimer(){
        timeText.setText("00:30");
        timerSeekBar.setEnabled(true);
        timerSeekBar.setProgress(30);
        goButton.setText("START");
        count.cancel();
        counterisActive=false;
        ticktock.setLooping(false);
        ticktock.stop();
    }
    public void StartTimer(View view){
        if (counterisActive) {
            resetTimer();
        }else {
            counterisActive = true;
            timerSeekBar.setEnabled(false);
            goButton.setText("STOP!");

            if(f==1) {
                mplayer.stop();
                f=0;
            }
            ticktock=MediaPlayer.create(getApplicationContext(), R.raw.ticktock);
            ticktock.start();
            ticktock.setLooping(true);

            count = new CountDownTimer(timerSeekBar.getProgress() * 1000 + 100, 1000) {

                @Override
                public void onTick(long millisUntilFinished) {
                    updateTimer((int) millisUntilFinished / 1000);

                }

                @Override
                public void onFinish() {
                    ticktock.setLooping(false);
                    ticktock.stop();
                    mplayer = MediaPlayer.create(getApplicationContext(), R.raw.alarm);
                    mplayer.start();
                    resetTimer();
                    f=1;

                }
            }.start();
        }

    }
    public void updateTimer(int secondsLeft){
        int minutes=secondsLeft/60;
        int seconds=secondsLeft-(minutes*60);
        String secondString=Integer.toString(seconds);
        if(seconds<10)
            secondString="0"+Integer.toString(seconds);

        timeText.setText(Integer.toString(minutes)+":"+secondString);


    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        timerSeekBar=findViewById(R.id.seekBar);
         timeText=findViewById(R.id.timeView);
         goButton =findViewById(R.id.startButton);
        timerSeekBar.setMax(1200);
        timerSeekBar.setProgress(30);
        timerSeekBar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int i, boolean fromUser) {
                updateTimer(i);
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {

            }
        });
    }
}
