package com.example.uni.drumpadapp;

import android.content.Intent;
import android.media.AudioManager;
import android.media.MediaPlayer;
import android.media.SoundPool;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.Spinner;
import android.widget.Toast;

public class DrumPadMainActivity_9B extends AppCompatActivity implements View.OnClickListener, AdapterView.OnItemSelectedListener {

    private Button b1, b2, b3, b4, b5, b6, b7, b8, b9;
    private ImageButton metronome, play;
    private SoundPool soundPool2;
    private int sound1, sound2, sound3, sound4, sound5, sound6, sound7, sound8, sound9, song;
    private boolean on = true;
    private Spinner layoutSpinner, songSpinner;
    private MediaPlayer mediaPlayer2;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.nine_buttons_layout);

        layoutSpinner = findViewById(R.id.spinner);
        songSpinner = findViewById(R.id.songs);
        // adds setOnItemSelectedListener to distinguish between options for further purposes
        if(loadOptions()) {
            layoutSpinner.setOnItemSelectedListener(this);
            songSpinner.setOnItemSelectedListener(this);
        }

        //setting onClickListener on all Buttons
        b1 = findViewById(R.id.b1);
        b2 = findViewById(R.id.b2);
        b3 = findViewById(R.id.b3);
        b4 = findViewById(R.id.b4);
        b5 = findViewById(R.id.b5);
        b6 = findViewById(R.id.b6);
        b7 = findViewById(R.id.b7);
        b8 = findViewById(R.id.b8);
        b9 = findViewById(R.id.b9);

        play = (ImageButton)findViewById(R.id.play);
        metronome = (ImageButton)findViewById(R.id.metronome);

        b1.setOnClickListener(this);
        b2.setOnClickListener(this);
        b3.setOnClickListener(this);
        b4.setOnClickListener(this);
        b5.setOnClickListener(this);
        b6.setOnClickListener(this);
        b7.setOnClickListener(this);
        b8.setOnClickListener(this);
        b9.setOnClickListener(this);

        play.setOnClickListener(this);
        metronome.setOnClickListener(this);

        soundSetUp();

    }

    /** plays sound based on which button was clicked
     *  plays song
     *  able to change to a mainActivity with more sound options, if desired
     * */
    @Override
    public void onClick(View v) {
        int sound = 0;
        //set sound, play rhythm or song
        switch(v.getId()){
            case R.id.b1: sound  = sound1; break;
            case R.id.b2: sound  = sound2; break;
            case R.id.b3: sound  = sound3; break;
            case R.id.b4: sound  = sound4; break;
            case R.id.b5: sound  = sound5; break;
            case R.id.b6: sound  = sound6; break;
            case R.id.b7: sound  = sound7; break;
            case R.id.b8: sound  = sound8; break;
            case R.id.b9: sound  = sound9; break;
            case R.id.metronome: rhythmBot(); break;
            case R.id.play: playSong(); break;
            default: break;
        }
        //play sound
        soundPool2.play(sound, 1.0f, 1.0f, 0, 0, 10f);
    }

    /** loads dropdown items from string-array in strings.xml file into spinner
     *  returns true if successful, else false*/
    private boolean loadOptions(){
        try {
            ArrayAdapter<String> myAdapter = new ArrayAdapter<String>(DrumPadMainActivity_9B.this,
                    R.layout.custom_spinner_item,
                    getResources().getStringArray(R.array.names));
            myAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
            layoutSpinner.setAdapter(myAdapter);

            myAdapter = new ArrayAdapter<String>(DrumPadMainActivity_9B.this,
                    R.layout.custom_spinner_item_2,
                    getResources().getStringArray(R.array.songs));
            myAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
            songSpinner.setAdapter(myAdapter);
            return true;
        } catch(Exception e) {
            return false;
        }
    }

    /** loops a sound or stop loop
     * */
    private void rhythmBot(){
        if (on) {
            Toast.makeText(DrumPadMainActivity_9B.this, "Rhythm Bot playing...", Toast.LENGTH_SHORT).show();
            soundPool2.play(sound1, 1.0f, 1.0f, 1,-1, 1.35f);
            on = false;
        }
        else {
            Toast.makeText(DrumPadMainActivity_9B.this, "Rhythm Bot halted...", Toast.LENGTH_SHORT).show();
            soundPool2.stop(sound1);
            on = true;
        }
    }

    /** plays selected song using a MediaPlayer object
     * */
    private void playSong(){
        if (!mediaPlayer2.isPlaying()) {
            mediaPlayer2 = MediaPlayer.create(getApplicationContext(), song);
            mediaPlayer2.start();
        }
        else {
            //songPool.stop(song);
            mediaPlayer2.stop();
            mediaPlayer2.release();
            mediaPlayer2 = MediaPlayer.create(getApplicationContext(), song);
        }
    }

    /** creates a stream for playing sounds and a media player to play song files
     *  loads sounds from raw folder
     * */
    private void soundSetUp(){
        soundPool2 = new SoundPool(2, AudioManager.STREAM_MUSIC, 0);
        mediaPlayer2 = MediaPlayer.create(getApplicationContext(), R.raw.song_despacito);

        sound1 = soundPool2.load(getApplicationContext(), R.raw.s1, 1);
        sound2 = soundPool2.load(getApplicationContext(), R.raw.s2, 1);
        sound3 = soundPool2.load(getApplicationContext(), R.raw.s3, 1);
        sound4 = soundPool2.load(getApplicationContext(), R.raw.s4, 1);
        sound5 = soundPool2.load(getApplicationContext(), R.raw.s5, 1);
        sound6 = soundPool2.load(getApplicationContext(), R.raw.s6, 1);
        sound7 = soundPool2.load(getApplicationContext(), R.raw.s7, 1);
        sound8 = soundPool2.load(getApplicationContext(), R.raw.s8, 1);
        sound9 = soundPool2.load(getApplicationContext(), R.raw.s9, 1);
        song = R.raw.song_despacito;
    }

    /** either sets song selection based on selected item from drop down menu
     *  or changes the app layout
     * @parent @view @position @id not in use
     * */
    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
        if(parent.getCount() == 3) {
            switch (position) {
                case 2: {
                    if(mediaPlayer2.isPlaying()){
                        mediaPlayer2.stop();
                        mediaPlayer2.release();
                        mediaPlayer2 = MediaPlayer.create(getApplicationContext(), song);
                    }
                    if(!on) {
                        soundPool2.stop(sound1);
                        on = true;
                    }
                    Toast.makeText(DrumPadMainActivity_9B.this, "Moving to 12 Buttons Layout...", Toast.LENGTH_SHORT).show();
                    startActivity(new Intent(getApplicationContext(), DrumPadMainActivity_12B.class));
                    break;
                }
                default:
                    break;
            }
        }
        else{
            switch (position) {
                case 1: song = R.raw.song_despacito; break;
                case 2: song = R.raw.song_gods_plan; break;
                case 3: song = R.raw.song_shape_of_you; break;
                default: break;
            }
        }
    }

    @Override
    public void onNothingSelected(AdapterView<?> parent) {

    }
}

