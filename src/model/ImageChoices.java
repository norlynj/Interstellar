package model;

/**
 *
 * The ImageChoices class implements the "Image Query Choices" in the game. Inherited from Question.
 * The question with 4 possible correct photo answers/codes one of
 * which is the correct one.
 */

public class ImageChoices extends Question {

    public ImageChoices(String category, String question, String correct, String[] wrongs) {
        super(category, question, correct, wrongs);
    }

    public ImageChoices(ImageChoices question) {
        this( question.getCategory(), question.getQuestion(), question.getCorrect(), question.getAllWrongs());

    }
}