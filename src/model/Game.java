package model;

import java.io.IOException;
import java.util.*;

/*
 * This class manages the player, question and question list objects
 * It provides the main logic of the game - loading the questions from the file,
 * passing it to GameView, checking the answer of the player,
 * and passing the scores and rounds passed to the game
 *
 */
public class Game {
    private List<String> rounds;
    private String category;
    private Player player;
    private QuestionFile questionFile;
    private QuestionList questionList;
    private Question currentQ;
    private boolean answerCorrect;
    private boolean doneFirstRound;
    private int questionCountShown;
    private int totalScore = 0;

    //initializes the object used and loads the questions afterwards
    public void initialize() throws IOException {
        this.rounds = Arrays.asList("Blackbird", "Quinjet", "Sanctuary 2", "Benatar", "Milano");
        this.doneFirstRound = true;
        this.player = new Player();
        this.questionList = new QuestionList();
        this.questionFile = new QuestionFile("questions.txt");
        this.loadQuestions();
    }

    //adds the players name
    public void addPlayer(String name){
        this.player.setName(name);
    }

    //returns the player name
    public String getPlayerName(){
        return this.player.getName();
    }

    //returns the player score
    public int getScore(){
        return player.getScore();
    }

    //sets the category of the question
    public void setCategory(String category){
        this.category = category;
    }

    //returns true if player has passed
    public boolean nextRound(){
        return player.playerPassed();
    }

    //sets the score back to 0, increments round passed in player if the score >= 6
    public void newRound(){
        this.questionCountShown = 0;
        player.newRound();
    }

    //returns the total number of rounds the player passed
    public int getRoundsPassed(){
        return player.getRoundsPassed();
    }


    //returns the ships the player has saved
    public List<String> getShipsSaved(){

        List<String> ships = new ArrayList<>();
        for(int i = 0; i < player.getRoundsPassed(); i++)
            ships.add(rounds.get(i));
        return ships;
    }

    //gets the player's total score from the rounds answered
    public int getTotalScore(){
        return totalScore;
    }

    //if player has already started one game category
    public boolean isDoneFirstRound(){
        return this.doneFirstRound;
    }

    //checks if less than question 10 questions have been displayed
    public boolean hasNextQuestion(){
        return questionCountShown < 10;
    }

    //returns the question count shown
    public int getQuestionCountShown(){
        return this.questionCountShown;
    }

    //loads the questions from the questions.txt file. Here, each line is read and grouped according to its category
    private void loadQuestions(){
        for (int i = 0; i < 145; i++) {
            String[] currentQLine = this.questionFile.getNextLine().split("\t--\t"); //Separates the file bar based on "--"
            switch (currentQLine[0]) { //index 0 is the first word before --, this means that the type of questions is in here
                case "string_string_choices" -> //Create a Question object and add it to the QuestionList
                        this.questionList.addQuestion(new Question(
                                currentQLine[1],
                                currentQLine[2],
                                currentQLine[3],
                                new String[]{currentQLine[4], currentQLine[5], currentQLine[6]}));
                case "string_code_choices" -> this.questionList.addQuestion(new ImageChoices(
                        currentQLine[1],
                        currentQLine[2],
                        currentQLine[3],
                        new String[]{currentQLine[4], currentQLine[5], currentQLine[6]}));
                case "code_string_choices" -> //one image, 4 string choices
                        this.questionList.addQuestion(new ImageQuestion(
                                currentQLine[1],
                                currentQLine[2],
                                currentQLine[3],
                                new String[]{currentQLine[4], currentQLine[5], currentQLine[6]},
                                currentQLine[7]));
            }
        }
    }

    //returns the current question from the given category
    public Question getCurQuestion() {
        if (!category.equals("random"))
            this.currentQ = this.questionList.nextQuestion(category);
        else
            this.currentQ = this.questionList.nextQuestion();

        if (currentQ instanceof ImageQuestion) {
            return new ImageQuestion((ImageQuestion) this.currentQ);
        } else if (currentQ instanceof ImageChoices){
            return new ImageChoices((ImageChoices) this.currentQ);
        }
        else {
            return new Question(this.currentQ);
        }
    }

    //checks the answer of the current question
    public void checkAnswer(String answer){
        questionCountShown++;
        boolean correct = currentQ.isCorrect(answer);
        if (correct) {
            this.win();
        } else
            this.lose();

    }

    //checks if the last answer is correct
    public boolean isAnswerCorrect() {
        return this.answerCorrect;
    }

    //makes the last answer equal to false
    private void lose() {
        this.answerCorrect = false;

    }

    //makes the last answer equal to false, adds a point if the user is correct, and increments total score
    private void win() {
        this.answerCorrect = true;
        player.scorePoint();
        totalScore++;
    }

    //returns the category of the questions
    public String getCategory(){
        return this.category;
    }

}
