package com.ngra.wms.game.scores;

import com.ngra.wms.game.controls.GameObject;

import java.util.ArrayList;

public class ScoreObject {

    private int score;

    private ArrayList<GameObject> objects;

    public ScoreObject(int score, ArrayList<GameObject> objects) {
        this.score = score;
        this.objects = objects;
    }

    public int getScore() {
        return score;
    }

    public void setScore(int score) {
        this.score = score;
    }

    public ArrayList<GameObject> getObjects() {
        return objects;
    }

    public void setObjects(ArrayList<GameObject> objects) {
        this.objects = objects;
    }
}
