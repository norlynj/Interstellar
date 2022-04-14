package model;

public class Player {
    // represents name
    private String name;
    // represents  score
    private int score;
    private int roundsPassed;

    // generates a player with a given name and a score of 0
    public Player() {
        score = 0;
    }

    public String getName() {
        return name;
    }

    public int getScore() {
        return score;
    }

    public int getRoundsPassed(){
        return roundsPassed;
    }

    public void setName(String name) {
        this.name = name;
    }

    // increases score of player
    public void scorePoint() {
        score++;
    }

    public boolean playerPassed(){
        return score >= 6;
    }

    public void newRound(){
        if(playerPassed()){
            roundsPassed++;
        }
        score = 0; //bring back score back to zero

    }

}
