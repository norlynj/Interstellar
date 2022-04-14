package model;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

/**
 * The QuestionList class manages a list of Question objects. Its purpose is to create
 *  Question items and provides method for randomly showing the list of questions
 */
public class QuestionList {

    private final List<Question> questions;

    public QuestionList() {
        this.questions = new ArrayList<>();
    }

    //This function accepts a positive number as a parameter and returns a random number
    private int getRandomIndexOfQuestion(int range) {
        Random ran = new Random();
        return ran.nextInt(Math.abs(range));
    }

    
    //returns a random question from the given category
    public Question nextQuestion(String category) {

        List<Question> questionsOfCategory = new ArrayList<>();

        for (Question question : this.questions) {
            if (question.getCategory().equals(category.toUpperCase())){
                questionsOfCategory.add(question);
            }
        }

        //Checks if the question table for a specific category is empty or not
        if (questionsOfCategory.isEmpty()) {
            return null;
        } else {

            // Returns a random question from the question box of a specific category
            int rand = this.getRandomIndexOfQuestion(questionsOfCategory.size());
            Question randQ = questionsOfCategory.get(rand);
            this.questions.remove(randQ); //removes the random question
            return randQ;
        }
    }

    //returns a random question from the file
    public Question nextQuestion() {

        if (!(this.questions.isEmpty())) {
            int rand = this.getRandomIndexOfQuestion(this.questions.size());
            Question randQ = this.questions.get(rand);
            return randQ;
        }

        return null;

    }

    //adds a Question object to the query list.
    public void addQuestion(Question question) {
        if (question != null) {
            this.questions.add(question);
        }
    }

}
