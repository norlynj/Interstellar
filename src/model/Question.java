package model;


/**
 *This class handles the question.
 * It provides methods for knowing the category,
 * correct answers, and wrong answers of
 * that specific question
 */
public class Question {

    private static final String[] CATEGORIES = {"A", "B", "C", "D", "E", "F", "G", "JAVA", "CLANG", "PYTHON", "JAVASCRIPT"};
    public static final int ANSWERS = 4;

    private String question;
    private String correctAnswer;
    private String[] wrongs;
    private String category;

    private Question(String question) {
        this.wrongs = new String[Question.ANSWERS - 1];
        this.question = question;
    }

    private Question(String question, String correct) {
        this(question);
        this.correctAnswer = correct;
    }

    private Question(String category, String question, String correct) {
        this(question, correct);

        if (Question.belongs(category)) { //checks whether the given question in the file belongs to the list of categories
            this.category = category.toUpperCase();
        } else {
            this.category = "OTHERS"; //else store it in OTHERS category
        }

    }

    public Question(Question question) {
        this(question.getCategory(), question.getQuestion(), question.getCorrect(), question.getAllWrongs());

    }

    public Question(String category, String question, String correct, String[] wrongs) {

        this(category, question, correct);
        /* Copy the table of wrongs to
        this.wrongs (table of incorrect answers to a question)*/
        System.arraycopy(wrongs, 0, this.wrongs, 0, Question.ANSWERS - 1);

    }

    //returns true if answer is correct
    public boolean isCorrect(String answer) {
        return this.correctAnswer.equals(answer);
    }

    //returns the question
    public String getQuestion() {
        return this.question;
    }

    //returns the category of the given question
    public String getCategory() {
        return this.category;
    }

    //returns the correct answer
    public String getCorrect() {
        return this.correctAnswer;
    }


    public String getWrong(int index) {
        if (index < Question.ANSWERS - 1) {
            return this.wrongs[index];
        } else {
            return null;
        }
    }

    /*
     *returns a new string table with the wrong ones
     */
    public String[] getAllWrongs() {
        return this.wrongs.clone();
    }

    /*
     *returns all possible options
     */
    public String[] getAllAnswers() {
        String[] allAnswers = new String[Question.ANSWERS];
        allAnswers[0] = this.getCorrect();
        for (int i = 1; i < Question.ANSWERS; i++) {
            allAnswers[i] = this.getWrong(i - 1);
        }
        return allAnswers;
    }

    /**
     * Checks if a category given as a parameter actually exists in
     * Question CATEGORIES table
     */
    public static boolean belongs(String category) {

        for (String cat : Question.CATEGORIES) {
            if (cat.equals(category.toUpperCase())) {
                return true;
            }
        }
        return false;
    }

    //returns the question category, the question, answers. Note: the first one is the correct answer
    @Override
    public String toString() {

        return "Category: " + this.getCategory() + "\n"
                + "Question: " + this.getQuestion() + "\n"
                + "Α) " + this.getCorrect() + "\n"
                + "B) " + this.getAllWrongs()[0] + "\n"
                + "C) " + this.getAllWrongs()[1] + "\n"
                + "D) " + this.getAllWrongs()[2] + "\n";

    }
}



