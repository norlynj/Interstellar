package view;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.io.IOException;
import java.util.*;
import java.util.List;
import model.*;

//gui code credits: inspired by https://github.com/TeamLS/Buzz/tree/master/src/gui
public class GameView implements ActionListener {
    private final Frame frame;

    private JButton saveNameButton;
    private JButton nextRoundButton;
    private JButton backToMainScrPanelButton;

    //theoretical questions buttons
    private JButton tIntroButton;
    private JButton tProceduralButton;
    private JButton tFunctionButton;
    private JButton tOOPButton;
    private JButton tEventButton;
    private JButton tImperativeVsDeclarativeButton;
    private JButton tDesignPatternsButton;
    private JButton randomButton;

    //programming questions button
    private JButton pJavaButton;
    private JButton pCButton;
    private JButton pPythonButton;
    private JButton pJavaScriptButton;
    private JButton showImageButtonA;
    private JButton showImageButtonB;
    private JButton showImageButtonC;
    private JButton showImageButtonD;
    private JPanel showCodePanel;
    private JPanel codeImage;

    //questions labels and buttons
    private JButton buttonA;
    private JButton buttonB;
    private JButton buttonC;
    private JButton buttonD;
    private JLabel answer_labelA;
    private JLabel answer_labelB;
    private JLabel answer_labelC;
    private JLabel answer_labelD;

    //pimage button
    private ImageButton mute;
    private ImageButton unmute;

    //panels
    private JPanel menuPanel;
    private JPanel playersNamePanel;
    private JPanel storyBoardPanel;
    private JPanel instructionsPanel;
    private JPanel scoresPanel;
    private JPanel chooseQPanel;
    private JPanel theoreticalQPanel;
    private JPanel programmingQPanel;
    private JPanel mainQuestionsPanel;
    private JPanel questionCodePanel;

    private JTextField inputNameTextField;
    private JLabel scoresTextLabel;
    private JLabel playerScoreLabel;
    private JLabel questionLabel;

    private Player player;
    Question currentQ;
    Game game;
    Messages text;

    private AudioPlayer audio;
    private AudioPlayer spaceAudio;
    List<String> theoreticalQlist = Arrays.asList("A", "B", "C", "D", "E", "F", "G"); //creating a list for theoretical category


    //constructor
    public GameView() {
        frame = new Frame("Interstellar");
        initComponents();
    }

    //gui components
    public void initComponents(){
        game = new Game();

        //GET PLAYERS' NAME PANEL --
        playersNamePanel = new Panel(true, "/interstellar/enterName.png");
        player = new Player();
        JLabel inputNameLabel = new Label("Hello there! Please enter your name");
        inputNameLabel.setForeground(new Color(34,0,48));
        inputNameLabel.setBounds(240, -100, 500, 660);


        inputNameTextField = new TextField("Enter name");
        inputNameTextField.setBounds((frame.getWidth() - inputNameTextField.getWidth()) / 2,250, inputNameTextField.getWidth(), inputNameTextField.getHeight());
        inputNameTextField.addActionListener(e-> saveNameButton.doClick());

        player.setName(inputNameTextField.getText());
        saveNameButton = new Button("save my name");
        saveNameButton.setBounds((frame.getWidth() - saveNameButton.getWidth()) / 2, 450, saveNameButton.getWidth(), saveNameButton.getHeight());
        saveNameButton.addActionListener(e->{
                player.setName(inputNameTextField.getText());
                switchPanel(playersNamePanel, storyBoardPanel); });

        //STORYBOARD PANEL---
        storyBoardPanel = new Panel("/interstellar/storybg.png");
        text = new Messages();

        JLabel storyBoardTitleLabel = new Label("STORY BOARD");
        storyBoardTitleLabel.setFont(new Font("Source Sans Pro", Font.BOLD, 40));
        storyBoardTitleLabel.setBounds(frame.getWidth()/4, -100, 500, 300);
        JLabel storyboardLabel = new Label((text.storyboard()), true, SwingConstants.CENTER);
        storyboardLabel.setBounds(frame.getWidth()/4, -90, 500, 700);
        JButton letsGo = new Button("let's go!");
        letsGo.setBounds((frame.getWidth() - letsGo.getWidth()) / 2, 450, letsGo.getWidth(), letsGo.getHeight());

        letsGo.addActionListener(e-> switchPanel(storyBoardPanel, menuPanel));

        //MAIN MENU PANEL---
        menuPanel = new Panel(false, "/interstellar/menu.png");

        //menu panel buttons
        JButton startButton = new Button("START");
        JButton instructionsButton = new Button("INSTRUCTIONS");
        JButton scoresButton = new Button("SCORES");
        JButton quitButton = new Button("QUIT");
        mute = new ImageButton("/interstellar/mute.png");
        unmute = new ImageButton("/interstellar/unmute.png");
        unmute.setVisible(false);

        startButton.setBounds(frame.getWidth() - 370, 113, 300, 70);
        instructionsButton.setBounds(frame.getWidth() - 370, 213, 300, 70);
        scoresButton.setBounds(frame.getWidth() - 370, 313, 300, 70);
        quitButton.setBounds(frame.getWidth() - 370, 413, 300, 70);
        mute.setBounds(10, 390, 128, 128);
        unmute.setBounds(10, 390, 128, 128);


        //button listeners in main menu; start, instruction, scores, and quit button
        startButton.addActionListener(e-> switchPanel(menuPanel, chooseQPanel));
        instructionsButton.addActionListener(e-> switchPanel(menuPanel, instructionsPanel));
        scoresButton.addActionListener(e-> switchPanel(menuPanel, scoresPanel));
        quitButton.addActionListener(e-> System.exit(0));
        mute.addActionListener(e-> {
                audio.stop();
                mute.setVisible(false);
                unmute.setVisible(true); });
        unmute.addActionListener(e-> {
            audio.play();
            unmute.setVisible(false);
            mute.setVisible(true); });

        //INSTRUCTIONS PANEL---
        instructionsPanel= new Panel(false, "/interstellar/textbg.png");
        JLabel instructionsTitleLabel = new Label("INSTRUCTIONS", false, SwingConstants.CENTER);
        instructionsTitleLabel.setFont(new Font("Source Sans Pro", Font.BOLD, 40));
        instructionsTitleLabel.setBounds(frame.getWidth()/4, -100, 500, 300);

        JLabel instructionsLabel = new Label(text.instructions("Norlyn"), true, SwingConstants.CENTER);
        instructionsLabel.setBounds(frame.getWidth()/4, -90, 600, 700);
        JButton backToMainInstPanelButton = new Button("back to main");
        backToMainInstPanelButton.setBounds((frame.getWidth() -  backToMainInstPanelButton.getWidth()) / 2, 460, backToMainInstPanelButton.getWidth(), backToMainInstPanelButton.getHeight());
        backToMainInstPanelButton.addActionListener(e-> switchPanel(instructionsPanel, menuPanel));


        //SCORES PANEL---
        scoresPanel= new Panel(false, "/interstellar/textbg.png");
        JLabel scoresLabel = new Label("SCORES");
        scoresLabel.setFont(new Font("Source Sans Pro", Font.BOLD, 40));
        scoresLabel.setBounds(240, -100, 500, 300);

        scoresTextLabel = new Label("", true, SwingConstants.CENTER);
        scoresTextLabel.setBounds(frame.getWidth()/4,130, 500, 300);
        scoresTextLabel.setFont(new Font("Arial", Font.BOLD, 30));

        nextRoundButton = new Button("next round");
        nextRoundButton.addActionListener(this);
        nextRoundButton.setBounds((frame.getWidth() -  nextRoundButton.getWidth()) / 2, 400, nextRoundButton.getWidth(), nextRoundButton.getHeight());

        backToMainScrPanelButton = new Button("back to main");
        backToMainScrPanelButton.setBounds((frame.getWidth() -  backToMainScrPanelButton.getWidth()) / 2, 460, backToMainScrPanelButton.getWidth(), backToMainScrPanelButton.getHeight());
        backToMainScrPanelButton.addActionListener(this);


        //CHOOSE QUESTIONS PANEL---
        //here the user choose a question; theoretical or programming
        chooseQPanel = new Panel(false, "/interstellar/chooseQ.png");

        //labels
        JLabel theoreticalQLabel = new Label("THEORETICAL");
        JLabel programmingQLabel = new Label("PROGRAMMING");

        theoreticalQLabel.setFont(new Font("Source Sans Pro", Font.BOLD, 30));
        theoreticalQLabel.setForeground(new Color(34,0,48));
        theoreticalQLabel.setBounds(frame.getWidth() / 2 - 430, 275, 300, 100);

        programmingQLabel.setFont(new Font("Source Sans Pro", Font.BOLD, 30));
        programmingQLabel.setForeground(new Color(34,0,48));
        programmingQLabel.setBounds(frame.getWidth() / 2 + 140, 275, 300, 100);

        //Image button for theoretical
        ImageButton theoretical = new ImageButton("/interstellar/theoreticalButton.png");
        theoretical.setBounds(frame.getWidth() / 2 - 400, 160, 200, 200);
        theoretical.addActionListener(e-> switchPanel(chooseQPanel, theoreticalQPanel));

        //Image button for theoretical
        ImageButton programming = new ImageButton("/interstellar/programmingButton.png");
        programming.setBounds(frame.getWidth() / 2 + 170, 160, 200, 200);
        programming.addActionListener(e-> switchPanel(chooseQPanel, programmingQPanel));

        //back to main button from questions panel
        //back to main menu buttons
        JButton backToMainQPanelButton = new Button("back to main");
        backToMainQPanelButton.setBounds((frame.getWidth() -  backToMainQPanelButton.getWidth()) / 2, 460, backToMainQPanelButton.getWidth(), backToMainQPanelButton.getHeight());
        backToMainQPanelButton.addActionListener(e -> switchPanel(chooseQPanel, menuPanel));

        //THEORETICAL QUESTIONS PANEL---
        //here the user choose the topic;
        // (A) Intro (B) Procedural (C) Functional (D) OOP
        // (E) Event (F) Imperative VS Declarative (G) DesignPattern
        theoreticalQPanel = new Panel(false, "/interstellar/chooseTheoreticalQ.png");

        //random button
        randomButton = new Button("Randomize!", new Color(34,0,48), new Color(209, 151, 16));
        randomButton.setBounds((frame.getWidth() -  randomButton.getWidth()) / 2, 20, randomButton.getWidth(), randomButton.getHeight());
        randomButton.addActionListener(this);

        //theoretical buttons
        tIntroButton = new Button("Introduction", new Color(34,0,48), new Color(209, 151, 16));
        tProceduralButton = new Button("Procedural", new Color(34,0,48), new Color(209, 151, 16));
        tFunctionButton = new Button("Functional", new Color(34,0,48), new Color(209, 151, 16));
        tOOPButton = new Button("OOP", new Color(34,0,48), new Color(209, 151, 16));
        tEventButton = new Button("Event-driven", new Color(34,0,48), new Color(209, 151, 16));
        tImperativeVsDeclarativeButton = new Button("Imperative vs Declarative", new Color(34,0,48), new Color(209, 151, 16));
        tDesignPatternsButton = new Button("Design patterns", new Color(34,0,48), new Color(209, 151, 16));

        //setting the bounds for the buttons
        tIntroButton.setBounds(frame.getWidth()/2 - 435, 80, 250, 70);
        tProceduralButton.setBounds(frame.getWidth()/2 - 435, 180, 250, 70);
        tFunctionButton.setBounds(frame.getWidth()/2 - 435, 280, 250, 70);
        tOOPButton.setBounds(frame.getWidth()/2 - 435, 380, 250, 70);
        tEventButton.setBounds(frame.getWidth()/2 + 130, 80, 250, 70);
        tImperativeVsDeclarativeButton.setBounds(frame.getWidth()/2 + 115, 180, 300, 70);
        tDesignPatternsButton.setBounds(frame.getWidth()/2 + 130, 280, 250, 70);

        //action listeners for the theoretical question topics buttons
        tIntroButton.addActionListener(this);
        tProceduralButton.addActionListener(this);
        tFunctionButton.addActionListener(this);
        tOOPButton.addActionListener(this);
        tEventButton.addActionListener(this);
        tImperativeVsDeclarativeButton.addActionListener(this);
        tDesignPatternsButton.addActionListener(this);

        JButton backTQButton = new Button("back");
        backTQButton.setBounds((frame.getWidth() -  backTQButton.getWidth()) / 2, 460, backTQButton.getWidth(), backTQButton.getHeight());
        backTQButton.addActionListener(e-> switchPanel(theoreticalQPanel, chooseQPanel));

        //PROGRAMMING QUESTIONS PANEL---
        //here the user choose the topic;
        // Java, C, Python, JavaScript
        programmingQPanel = new Panel(false, "/interstellar/chooseProgrammingQ.png");

        pJavaButton = new Button("JAVA", new Color(34,0,48), new Color(209, 151, 16));
        pCButton = new Button("C", new Color(34,0,48), new Color(209, 151, 16));
        pPythonButton = new Button("PYTHON", new Color(34,0,48), new Color(209, 151, 16));
        pJavaScriptButton = new Button("JAVASCRIPT", new Color(34,0,48), new Color(209, 151, 16));

        pJavaButton.setBounds(frame.getWidth()/2 - 350, 180, 200, 70);
        pCButton.setBounds(frame.getWidth()/2 - 350, 270, 200, 70);
        pPythonButton.setBounds(frame.getWidth()/2 + 130, 180, 200, 70);
        pJavaScriptButton.setBounds(frame.getWidth()/2 + 130, 270, 200, 70);

        //action listeners for the programming question topics buttons
        pJavaButton.addActionListener(this);
        pCButton.addActionListener(this);
        pPythonButton.addActionListener(this);
        pJavaScriptButton.addActionListener(this);

        JButton backPQButton = new Button("back");
        backPQButton.setBounds((frame.getWidth() -  backPQButton.getWidth()) / 2, 460, backPQButton.getWidth(), backPQButton.getHeight());
        backPQButton.addActionListener(e-> switchPanel(programmingQPanel, chooseQPanel));


        //THE MAIN GAME, SHOWS THE QUESTIONS
        mainQuestionsPanel = new Panel(false, "/interstellar/mcqBG.png");
        questionCodePanel = new Panel(40, 125, 410, 410);
        showCodePanel = new Panel("/interstellar/codeImageBg.png");
        codeImage = new Panel(220, 20, 634, 519);

        JButton backToQ = new Button("back", new Color(147, 140, 167), new Color(209, 151, 16));
        backToQ.setBounds(80, 30, 100, 70);
        backToQ.addActionListener(e -> switchPanel(showCodePanel, mainQuestionsPanel));

        questionLabel = new Label("", true,SwingConstants.CENTER);
        questionLabel.setBounds(130, -70, 700, 300);
        questionLabel.setForeground(new Color(34,0,48));

        playerScoreLabel = new Label();
        playerScoreLabel.setBounds(30, -60, 100, 200);
        playerScoreLabel.setFont(new Font("Arial", Font.PLAIN, 50));


        //THE MAIN QUESTIONS/ THE QUIZ GAME STARTS GUI
        buttonA = new Button("A", new Color(147,140,167), new Color(209, 151, 16));
        buttonB = new Button("B", new Color(147,140,167), new Color(209, 151, 16));
        buttonC = new Button("C", new Color(147,140,167), new Color(209, 151, 16));
        buttonD = new Button("D", new Color(147,140,167), new Color(209, 151, 16));

        buttonA.setFont(new Font("Arial", Font.BOLD, 35));
        buttonB.setFont(new Font("Arial", Font.BOLD, 35));
        buttonC.setFont(new Font("Arial", Font.BOLD, 35));
        buttonD.setFont(new Font("Arial", Font.BOLD, 35));

        buttonA.addActionListener(this);
        buttonB.addActionListener(this);
        buttonC.addActionListener(this);
        buttonD.addActionListener(this);

        answer_labelA = new Label();
        answer_labelB = new Label();
        answer_labelC = new Label();
        answer_labelD = new Label();

        answer_labelA.setForeground(new Color(34,0,48));
        answer_labelB.setForeground(new Color(34,0,48));
        answer_labelC.setForeground(new Color(34,0,48));
        answer_labelD.setForeground(new Color(34,0,48));

        showImageButtonA = new Button("show code A");
        showImageButtonB = new Button("show code B");
        showImageButtonC = new Button("show code C");
        showImageButtonD = new Button("show code D");

        showImageButtonA.setBounds(500, 134, 180, 80);
        showImageButtonB.setBounds(500, 233, 180, 80);
        showImageButtonC.setBounds(500, 334, 180, 80);
        showImageButtonD.setBounds(500, 430, 180, 80);

        showImageButtonA.setVisible(false);
        showImageButtonB.setVisible(false);
        showImageButtonC.setVisible(false);
        showImageButtonD.setVisible(false);


        //ADDING ALL THE COMPONENTS TO THE PANELS
        playersNamePanel.add(inputNameLabel);
        playersNamePanel.add(inputNameTextField);
        playersNamePanel.add(saveNameButton);

        storyBoardPanel.add(storyboardLabel);
        storyBoardPanel.add(storyBoardTitleLabel);
        storyBoardPanel.add(letsGo);

        menuPanel.add(startButton);
        menuPanel.add(instructionsButton);
        menuPanel.add(scoresButton);
        menuPanel.add(quitButton);
        menuPanel.add(mute);
        menuPanel.add(unmute);

        instructionsPanel.add(instructionsLabel);
        instructionsPanel.add(instructionsTitleLabel);
        instructionsPanel.add(backToMainInstPanelButton);

        scoresPanel.add(backToMainScrPanelButton);
        scoresPanel.add(scoresLabel);
        scoresPanel.add(scoresTextLabel);

        chooseQPanel.add(theoreticalQLabel);
        chooseQPanel.add(programmingQLabel);
        chooseQPanel.add(theoretical);
        chooseQPanel.add(programming);
        chooseQPanel.add(backToMainQPanelButton);
        chooseQPanel.add(randomButton);

        theoreticalQPanel.add(tIntroButton);
        theoreticalQPanel.add(tProceduralButton);
        theoreticalQPanel.add(tFunctionButton);
        theoreticalQPanel.add(tOOPButton);
        theoreticalQPanel.add(tEventButton);
        theoreticalQPanel.add(tImperativeVsDeclarativeButton);
        theoreticalQPanel.add(tDesignPatternsButton);
        theoreticalQPanel.add(backTQButton);

        programmingQPanel.add(pJavaButton);
        programmingQPanel.add(pCButton);
        programmingQPanel.add(pPythonButton);
        programmingQPanel.add(pJavaScriptButton);
        programmingQPanel.add(backPQButton);


        mainQuestionsPanel.add(questionLabel);
        mainQuestionsPanel.add(questionCodePanel);
        mainQuestionsPanel.add(buttonA);
        mainQuestionsPanel.add(buttonB);
        mainQuestionsPanel.add(buttonC);
        mainQuestionsPanel.add(buttonD);
        mainQuestionsPanel.add(answer_labelA);
        mainQuestionsPanel.add(answer_labelB);
        mainQuestionsPanel.add(answer_labelC);
        mainQuestionsPanel.add(answer_labelD);
        mainQuestionsPanel.add(playerScoreLabel);
        mainQuestionsPanel.add(showImageButtonA);
        mainQuestionsPanel.add(showImageButtonB);
        mainQuestionsPanel.add(showImageButtonC);
        mainQuestionsPanel.add(showImageButtonD);

        showCodePanel.add(backToQ);
        showCodePanel.add(codeImage);
        showCodePanel.setVisible(false);

        //ADDING ALL THE PANELS TO FRAME
        frame.add(playersNamePanel);
        frame.add(storyBoardPanel);
        frame.add(menuPanel);
        frame.add(chooseQPanel);
        frame.add(theoreticalQPanel);
        frame.add(programmingQPanel);
        frame.add(instructionsPanel);
        frame.add(scoresPanel);
        frame.add(mainQuestionsPanel);
        frame.add(showCodePanel);
        frame.setVisible(true);

        audio = new AudioPlayer("bgmusic.wav");
        spaceAudio = new AudioPlayer("intro.wav");
        audio.loop();
    }

    //switches panels. one makes visibility false, the other true
    private void switchPanel(JPanel one, JPanel two){
        one.setVisible(false);
        two.setVisible(true);
    }

    //initializes game if first round is not done, adds the player, and gets the the next question
    private void startGame(JPanel panel){
        switchPanel(panel, mainQuestionsPanel);
        if(!game.isDoneFirstRound()) {
            try {
                game.initialize();
                game.addPlayer(player.getName());
            } catch (IOException ioException) {
                ioException.printStackTrace();
            }
        }
        nextQuestion();
    }

    //calls beginGame() if no question has been displayed, else if the list has next
    // question it gets the currentQ and loads the question
    //else if 10 questions has already been displayed, it shows the result
    private void nextQuestion(){
        audio.stop();
        spaceAudio.loop();

        if (game.getQuestionCountShown() == 0) {
            beginGame();
        }
        else if(game.hasNextQuestion()) {
            currentQ = game.getCurQuestion();
            frame.requestFocus();
            loadQuestions();
        }
        else {
            showResult();
        }

        if(!game.hasNextQuestion())
            game.newRound();//resets question shown count
    }

    //gets the current question, sets the layout if question is theoretical,
    // and loads the questions
    private void beginGame(){
        new Thread(() -> {
            currentQ = game.getCurQuestion();
            loadQuestions();
        }).start();

    }

    //changes mainQuestionsPanel bg depending on the type of question
    private void loadQuestions(){
        String bg;
       if(currentQ instanceof ImageQuestion){
            bg = "/interstellar/imageQuestionBg.png";
            ((Panel)questionCodePanel).setImage(((ImageQuestion) currentQ).getImage());
            questionCodePanel.setVisible(true);
        } else if (currentQ instanceof ImageChoices){
            bg = "/interstellar/codeChoicesBg.png";
            questionCodePanel.setVisible(false);
        } else {
            bg = "/interstellar/mcqBG.png";
            questionCodePanel.setVisible(false);
        }
        ((Panel)mainQuestionsPanel).setImage(bg);
        frame.update(frame.getGraphics());
        loadAnswers(currentQ);
    }

    //loads the answer by changing the positions of text labels and buttons,
    // it randomizes the choices and order of questions,
    //and displays it accordingly to the jlabels
    private void loadAnswers(Question question){
        ArrayList<Integer> rand = new ArrayList<>();
        for (int i = 0; i < currentQ.getAllAnswers().length; i++) {
            rand.add(i);
        }
        long seed = System.nanoTime();
        Collections.shuffle(rand, new Random(seed));
        //guide for debugging: System.out.println(currentQ.toString());

        if(!theoreticalQlist.contains(currentQ.getCategory())) {
            if (currentQ instanceof ImageQuestion) {
                showImageButtonA.setVisible(false);
                showImageButtonB.setVisible(false);
                showImageButtonC.setVisible(false);
                showImageButtonD.setVisible(false);

                answer_labelA.setVisible(true);
                answer_labelB.setVisible(true);
                answer_labelC.setVisible(true);
                answer_labelD.setVisible(true);

                answer_labelA.setBounds(480, 126, 500, 100);
                answer_labelB.setBounds(480, 236, 500, 100);
                answer_labelC.setBounds(480, 339, 500, 100);
                answer_labelD.setBounds(480, 448, 500, 100);

                buttonA.setBounds(472, 145, 80, 80);
                buttonB.setBounds(472, 253, 80, 80);
                buttonC.setBounds(472, 357, 80, 80);
                buttonD.setBounds(472, 463, 80, 80);
            }
            else {
                //hides the labels and question panel from image question
                showImageButtonA.setVisible(true);
                showImageButtonB.setVisible(true);
                showImageButtonC.setVisible(true);
                showImageButtonD.setVisible(true);

                answer_labelA.setVisible(false);
                answer_labelB.setVisible(false);
                answer_labelC.setVisible(false);
                answer_labelD.setVisible(false);


                showImageButtonA.addActionListener(e -> showCode(question.getAllAnswers()[rand.get(0)]));
                showImageButtonB.addActionListener(e -> showCode(question.getAllAnswers()[rand.get(1)]));
                showImageButtonC.addActionListener(e -> showCode(question.getAllAnswers()[rand.get(2)]));
                showImageButtonD.addActionListener(e -> showCode(question.getAllAnswers()[rand.get(3)]));

                buttonA.setBounds(370, 134, 80, 80);
                buttonB.setBounds(370, 233, 80, 80);
                buttonC.setBounds(370, 334, 80, 80);
                buttonD.setBounds(370, 430, 80, 80);
            }
        } else{
            answer_labelA.setVisible(true);
            answer_labelB.setVisible(true);
            answer_labelC.setVisible(true);
            answer_labelD.setVisible(true);

            showImageButtonA.setVisible(false);
            showImageButtonB.setVisible(false);
            showImageButtonC.setVisible(false);
            showImageButtonD.setVisible(false);

            answer_labelA.setBounds(125, 144, 800, 100);
            answer_labelB.setBounds(125, 244, 800, 100);
            answer_labelC.setBounds(125, 352, 800, 100);
            answer_labelD.setBounds(125, 462, 800, 100);

            buttonA.setBounds(260, 152, 80, 80);
            buttonB.setBounds(260, 254, 80, 80);
            buttonC.setBounds(260, 362, 80, 80);
            buttonD.setBounds(260, 470, 80, 80);
        }

        //sets the texts to the jlabels
        questionLabel.setText(question.getQuestion());
        answer_labelA.setText(question.getAllAnswers()[rand.get(0)]);
        answer_labelB.setText(question.getAllAnswers()[rand.get(1)]);
        answer_labelC.setText(question.getAllAnswers()[rand.get(2)]);
        answer_labelD.setText(question.getAllAnswers()[rand.get(3)]);
        playerScoreLabel.setText(String.valueOf(game.getScore()));
        frame.update(frame.getGraphics());
    }

    //shows the code choice
    private void showCode(String imgurl){
        ((Panel) codeImage).setImage(imgurl);
        switchPanel(mainQuestionsPanel, showCodePanel);
    }

    //plays correct sound if answer is check, else wrong sound
    private  void afterAnswer(String answer){
        game.checkAnswer(answer);
        if(game.isAnswerCorrect()){
            new AudioPlayer("correct.wav").play();
        }else{
            new AudioPlayer("wrong.wav").play();
        }
    }

    //display results once 10 questions have already been displayed
    //displays save or lost message depending on the rounds passed
    private void showResult(){
        spaceAudio.stop();
        if(game.nextRound()) {
            if(game.getRoundsPassed() >= 4)//+1 because count starts at 0 in game
                scoresTextLabel.setText(text.saved(true, game.getShipsSaved(),game.getPlayerName(), String.valueOf(game.getScore())));
            else
                scoresTextLabel.setText(text.saved(game.getPlayerName(), String.valueOf(game.getScore())));
            scoresPanel.add(nextRoundButton);
            new AudioPlayer("correct.wav").play();
        } else{
            scoresTextLabel.setText(text.lost(game.getPlayerName(), String.valueOf(game.getScore())));
        }
        switchPanel(mainQuestionsPanel, scoresPanel);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        Object src = e.getSource();

        //for button A, B, C, D
        if (src == buttonA) {
            afterAnswer(answer_labelA.getText());
            nextQuestion();
        } else if (src == buttonB) {
            afterAnswer(answer_labelB.getText());
            nextQuestion();
        } else if (src == buttonC) {
            afterAnswer(answer_labelC.getText());
            nextQuestion();
        } else if (src == buttonD) {
            afterAnswer(answer_labelD.getText());
            nextQuestion();
        }

        //if random
        if(src == randomButton){
            game.setCategory("random");
            startGame(chooseQPanel);
        }

        //for theoretical topics
        else if (src == tIntroButton) {
            game.setCategory("A");
            startGame(theoreticalQPanel);
        } else if (src == tProceduralButton) {
            game.setCategory("B");
            startGame(theoreticalQPanel);
        } else if (src == tFunctionButton) {
            game.setCategory("C");
            startGame(theoreticalQPanel);
        } else if (src == tOOPButton) {
            game.setCategory("D");
            startGame(theoreticalQPanel);
        } else if (src == tEventButton) {
            game.setCategory("E");
            startGame(theoreticalQPanel);
        } else if (src == tImperativeVsDeclarativeButton) {
            game.setCategory("F");
            startGame(theoreticalQPanel);
        } else if (src == tDesignPatternsButton) {
            game.setCategory("G");
            startGame(theoreticalQPanel);
        }

        //for programming topics
        else if (src == pJavaButton) {
            game.setCategory("JAVA");
            startGame(programmingQPanel);
        } else if (src == pCButton) {
            game.setCategory("CLANG");
            startGame(programmingQPanel);
        } else if (src == pPythonButton) {
            game.setCategory("PYTHON");
            startGame(programmingQPanel);
        } else if (src == pJavaScriptButton) {
            game.setCategory("JAVASCRIPT");
            startGame(programmingQPanel);
        }

        //after score display buttons: next round and back to main
        else if (src == nextRoundButton ) {
            scoresPanel.remove(nextRoundButton);
            buttonRemoval();
            if (theoreticalQlist.contains(game.getCategory())) //the category is theoretical then switch to TQpanel
                switchPanel(scoresPanel, theoreticalQPanel);
            else //if the category is programming
                switchPanel(scoresPanel, programmingQPanel);
        } else if(src == backToMainScrPanelButton){
            scoresPanel.remove(nextRoundButton);
            if(game.getCategory()!=null) {
                buttonRemoval();
                if (game.getRoundsPassed() >= 4)//+1 because count starts at 0
                    scoresTextLabel.setText(text.saved(true, game.getShipsSaved(), game.getPlayerName(), String.valueOf(game.getTotalScore())));
                else if (game.getRoundsPassed() > 0)
                    scoresTextLabel.setText(text.saved(game.getShipsSaved(), game.getPlayerName(), String.valueOf(game.getTotalScore())));
                else
                    scoresTextLabel.setText(text.lost(game.getPlayerName(), String.valueOf(game.getTotalScore())));
                audio.play();
            }
            switchPanel(scoresPanel, menuPanel);
        }
    }

    //removes the topic once it's already been taken
    public void buttonRemoval(){
        switch (game.getCategory()) {
            case "A" -> theoreticalQPanel.remove(tIntroButton);
            case "B" -> theoreticalQPanel.remove(tProceduralButton);
            case "C" -> theoreticalQPanel.remove(tFunctionButton);
            case "D" -> theoreticalQPanel.remove(tOOPButton);
            case "E" -> theoreticalQPanel.remove(tEventButton);
            case "F" -> theoreticalQPanel.remove(tImperativeVsDeclarativeButton);
            case "G" -> theoreticalQPanel.remove(tDesignPatternsButton);
            case "JAVA" -> programmingQPanel.remove(pJavaButton);
            case "CLANG" -> programmingQPanel.remove(pCButton);
            case "PYTHON" -> programmingQPanel.remove(pPythonButton);
            case "JAVASCRIPT" -> programmingQPanel.remove(pJavaScriptButton);
        }
    }
}

