package com.calinavasilcai.tetris;

public interface TetrisInput {
    boolean slideLeft();
    boolean slideRight();
    boolean fallDown();
    boolean rotateClockwise();
    boolean rotateCounterClockwise();
}
