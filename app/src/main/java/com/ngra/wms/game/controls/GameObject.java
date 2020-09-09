package com.ngra.wms.game.controls;

import android.graphics.Rect;

public abstract class GameObject {

    protected int x;
    protected int y;
    protected int dy;
    protected int dx;
    protected int width;
    protected int height;
    protected int scoreForSpeed;
    protected int score;


    public Rect getRectangle() {
        return new Rect(x, y, x+width, y+height);
    }

    public int getX() {
        return x;
    }

    public void setX(int x) {
        this.x = x;
    }

    public int getY() {
        return y;
    }

    public void setY(int y) {
        this.y = y;
    }

    public int getDy() {
        return dy;
    }

    public void setDy(int dy) {
        this.dy = dy;
    }

    public int getDx() {
        return dx;
    }

    public void setDx(int dx) {
        this.dx = dx;
    }

    public int getWidth() {
        return width;
    }

    public void setWidth(int width) {
        this.width = width;
    }

    public int getHeight() {
        return height;
    }

    public void setHeight(int height) {
        this.height = height;
    }

    public int getScoreForSpeed() {
        return scoreForSpeed;
    }

    public void setScoreForSpeed(int scoreForSpeed) {
        this.scoreForSpeed = scoreForSpeed;
    }

    public int getScore() {
        return score;
    }

    public void setScore(int score) {
        this.score = score;
    }
}
