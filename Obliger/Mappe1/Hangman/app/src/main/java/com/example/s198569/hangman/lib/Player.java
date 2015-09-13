package com.example.s198569.hangman.lib;


public class Player {
    private String name;
    private int score, won, lost;

    public int getScore() {
        return score;
    }

    public void setScore(int score) {
        this.score = score;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getLost() {
        return lost;
    }

    public void setLost(int lost) {
        this.lost = lost;
    }

    public int getWon() {
        return won;
    }

    public void setWon(int won) {
        this.won = won;
    }

    @Override
    public String toString() {
        return "Player{" +
                "lost=" + lost +
                ", name='" + name + '\'' +
                ", score=" + score +
                ", won=" + won +
                '}';
    }

}
