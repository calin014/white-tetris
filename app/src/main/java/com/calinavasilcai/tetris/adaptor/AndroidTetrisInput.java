package com.calinavasilcai.tetris.adaptor;

import android.view.MotionEvent;
import android.view.View;

import com.calinavasilcai.tetris.TetrisInput;
import com.calinavasilcai.tetris.adaptor.gestures.GestureDetector;
import com.calinavasilcai.tetris.adaptor.gestures.RotationDetector;
import com.calinavasilcai.tetris.adaptor.gestures.SwipeDetector;

public class AndroidTetrisInput implements TetrisInput, View.OnTouchListener {
    private static final int SLIDE_LEFT = 0;
    private static final int SLIDE_RIGHT = 1;
    private static final int FALL_DOWN = 2;
    private static final int ROTATE_CLOCKWISE = 3;
    private static final int ROTATE_COUNTER_CLOCKWISE = 4;

    private boolean gestureDetected;

    private GestureDetector[] detectors = {
            new SwipeDetector.Left(),
            new SwipeDetector.Right(),
            new SwipeDetector.Down(),
            new RotationDetector.Clockwise(),
            new RotationDetector.CounterClockwise()};

    private volatile boolean[] gestureFlags = {false, false, false, false, false};

    @Override
    public boolean slideLeft() {
        if (gestureFlags[SLIDE_LEFT]) {
            gestureFlags[SLIDE_LEFT] = false;
            return true;
        }
        return false;
    }

    @Override
    public boolean slideRight() {
        if (gestureFlags[SLIDE_RIGHT]) {
            gestureFlags[SLIDE_RIGHT] = false;
            return true;
        }
        return false;
    }

    @Override
    public boolean fallDown() {
        if (gestureFlags[FALL_DOWN]) {
            gestureFlags[FALL_DOWN] = false;
            return true;
        }
        return false;
    }

    @Override
    public boolean rotateClockwise() {
        if (gestureFlags[ROTATE_CLOCKWISE]) {
            gestureFlags[ROTATE_CLOCKWISE] = false;
            return true;
        }
        return false;
    }

    @Override
    public boolean rotateCounterClockwise() {
        if (gestureFlags[ROTATE_COUNTER_CLOCKWISE]) {
            gestureFlags[ROTATE_COUNTER_CLOCKWISE] = false;
            return true;
        }
        return false;
    }

    @Override
    public synchronized boolean onTouch(View view, MotionEvent event) {
        switch (event.getAction()) {
            case MotionEvent.ACTION_DOWN:
                gestureDetected = false;
                for (GestureDetector detector : detectors) {
                    detector.start(event.getX(), event.getY());
                }
                break;
            case MotionEvent.ACTION_MOVE:
                if (gestureDetected) break;

                for (int i = 0; i < detectors.length; i++) {
                    GestureDetector detector = detectors[i];
                    detector.step(event.getX(), event.getY());
                    if (detector.detected()) {
                        gestureFlags[i] = true;
                        gestureDetected = true;
                    }
                }
                break;
        }

        return true;
    }
}
