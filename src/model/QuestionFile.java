package model;


import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.URL;


/**
 *This class handles the query file.
 * Provides methods for reading lines and is used by Game
 * to fill in the questionList.
 *
 */
public class QuestionFile {

    private final String[] questionFile;
    private int lines;
    private int curLine;

    public QuestionFile(String filename) throws IOException {

        URL url = getClass().getResource("/resources/MCQ/" + filename);

        compNumOfLines(url); //Calculate the number of lines in the file

        url = getClass().getResource("/resources/MCQ/" + filename);


        //In case the file does not exist in the specific path
        if (url == null) {
            throw new FileNotFoundException();

        }

        InputStream input = url.openStream();

        BufferedReader reader = new BufferedReader(new InputStreamReader(input, "ISO8859_7"));
        questionFile = new String[lines];

        String line;
        int i = 0;
        while ((line = reader.readLine()) != null) {
            this.questionFile[i++] = line;

        }

        reader.close();
        curLine = 0; //Initial position of the index

    }

    private void compNumOfLines(URL url) throws IOException {

        if (url == null) {
            throw new FileNotFoundException();
        }

        InputStream input = url.openStream();

        BufferedReader reader = new BufferedReader(new InputStreamReader(input, "ISO8859_7"));
        while (reader.readLine() != null) {
            lines++;
        }

        reader.close();
    }

    /**
     *
     * Returns the next row of the table where it is each time
     * questions are saved (questionFile). Once the lines are finished,
     * starts again from the beginning of the table and returns the questions.
     */
    public String getNextLine() {
        if (curLine == questionFile.length) {
            curLine = 0;
        }

        return questionFile[curLine++];
    }

}
