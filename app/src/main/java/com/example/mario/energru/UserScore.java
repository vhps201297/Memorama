package com.example.mario.energru;

public class UserScore {

    private int score;

    public UserScore(int score) {
        this.score = score;
    }

    @Override
    public String toString() {
        return "UserScore{" +
                "score='" + score + '\'' +
                '}';
    }
}
