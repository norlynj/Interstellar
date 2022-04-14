package model;


/**
 *
 * The ImageQuestion class implements the "Image Query" in the game. Inherited from Question.
 * The question with 4 possible correct answers one of
 * which is the correct one, accompanied by a source code photo.
 */
public class ImageQuestion extends Question {

    private String imageurl;

    public ImageQuestion(String category, String question, String correct, String[] wrongs, String imageurl) {

        super(category, question, correct, wrongs);
        this.imageurl = imageurl;
    }

    public ImageQuestion(ImageQuestion question) {
        this( question.getCategory(), question.getQuestion(), question.getCorrect(), question.getAllWrongs(), question.getImage());

    }


    /**
     * Returns the photo url of this question
     */
    public String getImage() {
        return this.imageurl;
    }

}
