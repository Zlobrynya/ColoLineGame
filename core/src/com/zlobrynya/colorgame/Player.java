package com.zlobrynya.colorgame;

//in class contains player data

public class Player {
    private int score;
    private int maxScore;

    Player(int score){
        this.score = score;
        maxScore = 0;
    }

    public int getMaxScore() {
        return maxScore;
    }

    public int getScore() {
        return score;
    }

    public void setScore(int score) {
        this.score += score;
    }
}
