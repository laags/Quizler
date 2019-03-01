package com.highlatencygames.laags.quizler;

//This class simulates a via interpolation
class Bounce implements android.view.animation.Interpolator {
    private double amp = 1;
    private double freq = 10;

    Bounce(double amplitude, double frequency) {
        amp = amplitude;
        freq = frequency;
    }

    public float getInterpolation(float time) {
        return (float) (-1 * Math.pow(Math.E, -time/ amp) * Math.cos(freq * time) + 1);
    }
}