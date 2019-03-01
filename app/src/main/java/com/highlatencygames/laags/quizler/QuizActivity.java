package com.highlatencygames.laags.quizler;

import android.content.Context;
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
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.highlatencygames.laags.assignment2.R;
import com.wajahatkarim3.easyflipview.EasyFlipView;

import java.util.ArrayList;

//This activity represents the main quiz activity
public class QuizActivity extends AppCompatActivity {

    private Questions questions;
    private TextView scoreView, questionView;
    private EasyFlipView flip;
    private Button buttonTerm1, buttonTerm2, buttonTerm3, buttonTerm4;
    private String answer, userName;
    private int score = 0, questionNumber = 0;
    private AnimationDrawable backgroundAnim;
    private Animation slideRightAnim, slideLeftAnim;
    private ProgressBar progressBar;
    private MediaPlayer bgm;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_quiz);
        getSupportActionBar().hide(); // Hide the action bar

        // Get contexts
        Context context = getApplicationContext();
        questions = new Questions(context);

        // Get our views
        progressBar = (ProgressBar) findViewById(R.id.progressBar);
        scoreView = (TextView)findViewById(R.id.score);
        questionView = (TextView)findViewById(R.id.definition);
        buttonTerm1 = (Button) findViewById(R.id.term1);
        buttonTerm2 = (Button)findViewById(R.id.term2);
        buttonTerm3 = (Button)findViewById(R.id.term3);
        buttonTerm4 = (Button)findViewById(R.id.term4);
        flip = findViewById(R.id.easyFlipView);
        ConstraintLayout mainLayout = (ConstraintLayout) findViewById(R.id.linearLayout);

        // Get users name from previous screen
        userName= getIntent().getStringExtra("NAME");

        // Create animations
        backgroundAnim = (AnimationDrawable) mainLayout.getBackground();
        backgroundAnim.setEnterFadeDuration(5000);
        backgroundAnim.setExitFadeDuration(2000);
        slideLeftAnim = AnimationUtils.loadAnimation(this, R.anim.slideleft);
        slideRightAnim = AnimationUtils.loadAnimation(this, R.anim.slideright);
        final Animation bounceAnim = AnimationUtils.loadAnimation(this, R.anim.bounce);
        Bounce interpolator = new Bounce(0.2, 20);
        bounceAnim.setInterpolator(interpolator);

        // Create our sounds
        final MediaPlayer correctSound = MediaPlayer.create(this, R.raw.correct);
        final MediaPlayer incorrectSound = MediaPlayer.create(this, R.raw.incorrect);
        bgm = MediaPlayer.create(this, R.raw.bgm);

        // Start the background music
        bgm.start();
        bgm.setLooping(true);

        // Update the questions
        updateQuestion();

        // Update the score
        updateScore(score);

        // Listener for terms button one
        buttonTerm1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // If term matches answer
                // Increment the score, and update the questions
                if(buttonTerm1.getText() == answer){
                    buttonTerm1.startAnimation(bounceAnim);
                    score++;
                    updateScore(score);
                    updateQuestion();
                    correctSound.start();
                    Toast.makeText(QuizActivity.this, "Correct!", Toast.LENGTH_SHORT).show();

                }
                else {
                    buttonTerm1.startAnimation(bounceAnim);
                    updateQuestion();
                    incorrectSound.start();
                    Toast.makeText(QuizActivity.this, "Wrong!", Toast.LENGTH_SHORT).show();
                }
            }
        });

        // Listener for terms button two
        buttonTerm2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // If term matches answer
                // Increment the score, and update the questions
                if(buttonTerm2.getText()== answer){
                    buttonTerm2.startAnimation(bounceAnim);
                    score++;
                    updateScore(score);
                    updateQuestion();
                    correctSound.start();
                    Toast.makeText(QuizActivity.this, "Correct!", Toast.LENGTH_SHORT).show();

                }
                else {
                    buttonTerm2.startAnimation(bounceAnim);
                    updateQuestion();
                    incorrectSound.start();
                    Toast.makeText(QuizActivity.this, "Wrong!", Toast.LENGTH_SHORT).show();
                }
            }
        });

        // Listener for terms button three
        buttonTerm3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // If term matches answer
                // Increment the score, and update the questions
                if(buttonTerm3.getText()== answer){
                    buttonTerm3.startAnimation(bounceAnim);
                    score++;
                    updateScore(score);
                    updateQuestion();
                    correctSound.start();
                    Toast.makeText(QuizActivity.this, "Correct!", Toast.LENGTH_SHORT).show();

                }
                else {
                    buttonTerm3.startAnimation(bounceAnim);
                    updateQuestion();
                    incorrectSound.start();
                    Toast.makeText(QuizActivity.this, "Wrong!", Toast.LENGTH_SHORT).show();
                }
            }
        });

        // Listener for terms button four
        buttonTerm4.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // If term matches answer
                // Increment the score, and update the questions
                if(buttonTerm4.getText()== answer){
                    buttonTerm4.startAnimation(bounceAnim);
                    score++;
                    updateScore(score);
                    updateQuestion();
                    correctSound.start();
                    Toast.makeText(QuizActivity.this, "Correct!", Toast.LENGTH_SHORT).show();

                }
                else {
                    buttonTerm4.startAnimation(bounceAnim);
                    updateQuestion();
                    incorrectSound.start();
                    Toast.makeText(QuizActivity.this, "Wrong!", Toast.LENGTH_SHORT).show();
                }
            }
        });

    }

    // Updates the question and answers being displayed
    private void updateQuestion(){

        // Update the progress bar
        progressBar.setProgress(questionNumber);

        // Get the list of answer options
        ArrayList<String> options = questions.getOptions(questions.getQuestion(questionNumber));

        // Animate the buttons
        buttonTerm1.startAnimation(slideRightAnim);
        buttonTerm2.startAnimation(slideLeftAnim);
        buttonTerm3.startAnimation(slideRightAnim);
        buttonTerm4.startAnimation(slideLeftAnim);

        // Set the options
        buttonTerm1.setText(options.get(0));
        buttonTerm2.setText(options.get(1));
        buttonTerm3.setText(options.get(2));
        buttonTerm4.setText(options.get(3));

        // Set the correct answer
        answer = questions.getAnswer(questions.getQuestion(questionNumber));

        // Animate the card flip with a delay so it doesn't look weird, increment the question number
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                flip.flipTheView();
                //Get the question
                questionView.setText(questions.getQuestion(questionNumber));
                questionNumber++;
            }
        }, 600);

        // Check if quiz is over
        if(questionNumber!=10)
            flip.flipTheView();
        else{
            Intent resultsIntent = new Intent(getApplicationContext(), ResultsScreen.class);
            // Pass next activity the name and score
            resultsIntent.putExtra("NAME", userName);
            resultsIntent.putExtra("SCORE", score);
            startActivity(resultsIntent);
        }
    }

    // Updates the score
    private void updateScore(int points){
        scoreView.setText(""+score);
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
