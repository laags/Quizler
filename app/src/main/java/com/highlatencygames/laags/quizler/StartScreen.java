package com.highlatencygames.laags.quizler;

import android.content.Intent;
import android.graphics.drawable.AnimationDrawable;
import android.media.MediaPlayer;
import android.os.Handler;
import android.support.constraint.ConstraintLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.ImageView;

import com.highlatencygames.laags.assignment2.R;

// This activity represents the starting screen with the logo and a play button
public class StartScreen extends AppCompatActivity {

    private AnimationDrawable backgroundAnim;
    private Button play;
    private MediaPlayer bgm;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_start_screen);
        getSupportActionBar().hide();

        // Get our views
        ConstraintLayout layout = (ConstraintLayout) findViewById(R.id.mainLayout);
        play = (Button) findViewById(R.id.playBtn);
        ImageView logo = (ImageView) findViewById(R.id.logo);
        ImageView bulb = (ImageView) findViewById(R.id.bulb);

        // Load animations
        final Animation bulbAnim = AnimationUtils.loadAnimation(this, R.anim.wobbletwo);
        final Animation slideLeftAnim = AnimationUtils.loadAnimation(this, R.anim.slideleft);
        final Animation logoAnim = AnimationUtils.loadAnimation(this, R.anim.wobble);
        backgroundAnim = (AnimationDrawable) layout.getBackground();
        backgroundAnim.setEnterFadeDuration(4000);
        backgroundAnim.setExitFadeDuration(2000);
        final Animation idleBounce = AnimationUtils.loadAnimation(this,R.anim.idlebounce);
        final Animation bounceAnim = AnimationUtils.loadAnimation(this, R.anim.bounce);
        Bounce interpolator = new Bounce(0.2, 20);
        bounceAnim.setInterpolator(interpolator);

        // Create and start background music
        bgm = MediaPlayer.create(this, R.raw.bgm);
        bgm.start();
        bgm.setLooping(true);
        final MediaPlayer correctSound = MediaPlayer.create(this, R.raw.correct);

        // Start logo animations
        logo.startAnimation(logoAnim);
        bulb.startAnimation(bulbAnim);

        // Start button slide in animation
        play.startAnimation(slideLeftAnim);

        // Start idle bounce animation, on a delay so it triggers after the slide
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                play.startAnimation(idleBounce);
            }
        }, 1000);

        play.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                correctSound.start();
                play.startAnimation(bounceAnim);
                Intent playIntent = new Intent(view.getContext(), PlayerScreen.class);
                startActivity(playIntent);
            }
        });
    }

    // On resume we start the background animation and music
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
