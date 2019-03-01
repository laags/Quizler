package com.highlatencygames.laags.quizler;

import android.animation.ValueAnimator;
import android.content.Context;
import android.content.Intent;
import android.graphics.drawable.AnimationDrawable;
import android.media.MediaPlayer;
import android.support.constraint.ConstraintLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.highlatencygames.laags.assignment2.R;

// This Activity represents the results screen, showing the users result
public class ResultsScreen extends AppCompatActivity {

    private AnimationDrawable backgroundAnim;
    private MediaPlayer bgm, progressFill;
    private TextView percent;
    private ProgressBar resultsBar;
    private Animation bounceAnim;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_results_screen);
        getSupportActionBar().hide(); // Hide the action bar
        Context context = getApplicationContext();

        // Get our views
        TextView scoreLine = (TextView) findViewById(R.id.scoreLine);
        TextView nameLine = (TextView) findViewById(R.id.nameLine);
        resultsBar = (ProgressBar) findViewById(R.id.resultsBar);
        percent = (TextView) findViewById(R.id.percentage);
        final Button playAgain = (Button) findViewById(R.id.playAgainBtn);
        final Button backBtn = (Button) findViewById(R.id.backBtn);
        ConstraintLayout layout = (ConstraintLayout) findViewById(R.id.mainLayout);
        final ConstraintLayout resultsLayout = (ConstraintLayout) findViewById(R.id.resultsLayout);
        TextView resultsTitle = (TextView) findViewById(R.id.resultsTitle);

        //Get users name from previous screen and set it
        final String userName = getIntent().getStringExtra("NAME");
        nameLine.setText("NAME: " + userName);

        //Get users score from previous screen and set it
        final int score = getIntent().getIntExtra("SCORE", 0);
        scoreLine.setText("SCORE: " + score + " / 10");

        // Create our sounds
        progressFill = MediaPlayer.create(this, R.raw.progress);
        bgm = MediaPlayer.create(this, R.raw.bgm);

        // Start background music
        bgm.start();
        bgm.setLooping(true);
        final MediaPlayer correctSound = MediaPlayer.create(this, R.raw.correct);

        // Animate the filling of the progress bar
        progressFill.start();
        ValueAnimator animator = ValueAnimator.ofInt(0, score*10);
        animator.setDuration(3000);
        animator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator animation){
                resultsBar.setProgress((Integer)animation.getAnimatedValue());
                percent.setText((Integer)animation.getAnimatedValue() + "%");
                // Stop if score is reached
                if((Integer)animation.getAnimatedValue()==(score*10))
                    progressFill.stop();
            }
        });
        animator.start();

        //Create our Bounce Animation for the button click
        bounceAnim = AnimationUtils.loadAnimation(this, R.anim.bounce);
        Bounce interpolator = new Bounce(0.2, 20);
        bounceAnim.setInterpolator(interpolator);


        // Background changing animation
        backgroundAnim = (AnimationDrawable) layout.getBackground();
        backgroundAnim.setEnterFadeDuration(5000);
        backgroundAnim.setExitFadeDuration(2000);
        final Animation slideRightAnim = AnimationUtils.loadAnimation(this, R.anim.slideright);
        resultsTitle.startAnimation(slideRightAnim);
        resultsLayout.startAnimation(slideRightAnim);

        // Listener for retry button
        playAgain.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                correctSound.start();
                playAgain.startAnimation(bounceAnim);
                Intent beginIntent = new Intent(view.getContext(), QuizActivity.class);
                // Pass name to next activity
                beginIntent.putExtra("NAME", userName);
                startActivity(beginIntent);
            }
        });

        // Listener for retry button
        backBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                correctSound.start();
                backBtn.startAnimation(bounceAnim);
                Intent beginIntent = new Intent(view.getContext(), StartScreen.class);
                startActivity(beginIntent);
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
