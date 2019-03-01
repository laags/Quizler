package com.highlatencygames.laags.quizler;

import android.content.Intent;
import android.graphics.drawable.AnimationDrawable;
import android.media.MediaPlayer;
import android.support.constraint.ConstraintLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;

import android.widget.Toast;

import com.highlatencygames.laags.assignment2.R;

// This activity represents a name entry screen
public class PlayerScreen extends AppCompatActivity {

    private AnimationDrawable backgroundAnim;
    private Button begin;
    private MediaPlayer bgm;
    private EditText name;
    private Animation bounceAnim;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_player_screen);
        getSupportActionBar().hide(); // Hide the action bar, we dont need it

        // Get our views
        ConstraintLayout layout = (ConstraintLayout) findViewById(R.id.mainLayout);
        ConstraintLayout nameInputLayout = (ConstraintLayout) findViewById(R.id.nameInputLayout);
        name = (EditText) findViewById(R.id.nameInput);
        begin = (Button) findViewById(R.id.beginBtn);

        // Create our background changing animation
        backgroundAnim = (AnimationDrawable) layout.getBackground();

        //Create our Bounce Animation for the button click
        bounceAnim = AnimationUtils.loadAnimation(this, R.anim.bounce);
        Bounce interpolator = new Bounce(0.2, 20);
        bounceAnim.setInterpolator(interpolator);

        // Create our slide in animation and start it
        final Animation slideRightAnim = AnimationUtils.loadAnimation(this, R.anim.slideright);
        nameInputLayout.startAnimation(slideRightAnim);

        // setting enter fade animation duration to 5 seconds
        backgroundAnim.setEnterFadeDuration(4000);
        // setting exit fade animation duration to 2 seconds
        backgroundAnim.setExitFadeDuration(2000);

        // Create media player for background music, start it and set it to loop
        bgm = MediaPlayer.create(this, R.raw.bgm);
        bgm.start();
        bgm.setLooping(true);
        final MediaPlayer correctSound = MediaPlayer.create(this, R.raw.correct);
        final MediaPlayer incorrectSound = MediaPlayer.create(this, R.raw.incorrect);

        // Listener for the "Begin" button
        begin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                begin.startAnimation(bounceAnim);
                // If the user entered a name, store it in a string, and send it to the next activity
                if(!name.getText().toString().equals("")){
                    correctSound.start();
                    String input = name.getText().toString();
                    Intent beginIntent = new Intent(view.getContext(), QuizActivity.class);
                    beginIntent.putExtra("NAME", input);
                    startActivity(beginIntent);
                }
                else {
                    incorrectSound.start();
                    Toast.makeText(PlayerScreen.this, "Must enter name!", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

    // On pause we start the background animation and music
    @Override
    protected void onResume() {
        super.onResume();
        if (backgroundAnim != null && !backgroundAnim.isRunning()) {
            // start the animation
            backgroundAnim.start();
        }
        bgm.start();

    }

    // On pause we stop the background animation and music
    @Override
    protected void onPause() {
        super.onPause();
        if (backgroundAnim != null && backgroundAnim.isRunning()) {
            // stop the animation
            backgroundAnim.stop();
        }
        bgm.pause();
    }
}
