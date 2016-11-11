package wheeloffortunegame;

import java.util.Random;
import javafx.animation.RotateTransition;
import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyCode;
import javafx.scene.layout.Pane;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.paint.CycleMethod;
import javafx.scene.paint.LinearGradient;
import javafx.scene.paint.Stop;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Font;
import javafx.stage.Stage;
import javafx.util.Duration;

public class WheelOfFortuneGame extends Application {
    TextField userInput = new TextField();
    TextField phraseInput = new TextField();
    
    Random random = new Random();
    
    int arrayCount = 0;
    int playerScore = 0;
    int computerScore = 0;
    int spinWheelValue;
    int gameRound = 0;
    boolean gameOn = true;
    boolean playerTurn = true;
    boolean computerTurn = false;
    boolean flag = false;
    boolean hasSpun = false;
    
    SpinWheel spin = new SpinWheel();    
    PhraseBank phrase = new PhraseBank();
    Random randomGenerator = new Random();
    
    char[] phraseCharArray = phrase.getPhraseArray();
    boolean[] phraseBooleanArray = phrase.getBooleanArray();
    
    Rectangle playerBox = new Rectangle(100, 50);
    Rectangle playerScoreBox = new Rectangle(325, 70);
    Rectangle computerScoreBox = new Rectangle(325, 70);
    Rectangle wordIsBox = new Rectangle(650, 63);
    
    ScrollPane sp = new ScrollPane();
    
    VBox vBox = new VBox();
    
    Label textBox;
    Label wordIsText = new Label();
    Label playerName = new Label("PlayerScore: " + playerScore);
    Label computerName =new Label("ComputerScore: " + computerScore);
    
    Stop[] stops = new Stop[] { new Stop(0, Color.BLACK), new Stop(1, Color.LIGHTBLUE)};
    Stop[] stopsCPU = new Stop[] { new Stop(0, Color.BLACK), new Stop(1, Color.LIGHTCORAL)};
    Stop[] stopsWord = new Stop[] { new Stop(0, Color.BLACK), new Stop(1, Color.GAINSBORO)};
    
    LinearGradient scoreGrad = new LinearGradient(0, 3, 0, 0, true, CycleMethod.NO_CYCLE, stops);
    LinearGradient scoreGradCPU = new LinearGradient(0, 3, 0, 0, true, CycleMethod.NO_CYCLE, stopsCPU);
    LinearGradient wordIsGrad = new LinearGradient(0, 3, 0, 0, true, CycleMethod.NO_CYCLE, stopsWord);
    
    Button spinBtn = new Button("Spin");
    Button guessPhraseBtn = new Button("Guess Phrase?");
    Button replayBtn = new Button("Replay?");
    
    ImageView wheelImage = new ImageView();
    Image wheel = new Image("file:src/wheeloffortunegame/Wheel.jpg");
    
    RotateTransition rotate = new RotateTransition(Duration.seconds(1), wheelImage);
    
    public void initEventHandlers() throws Exception{
        
        replayBtn.setOnAction(event ->{
            gameOn = true;            
            replayBtn.setDisable(true);
            computerScore = 0;
            computerName.setText("ComputerScore: " + computerScore);
            playerScore = 0;
            playerName.setText("PlayerScore: " + playerScore);
            
            spin = new SpinWheel();
            phrase = new PhraseBank();
            randomGenerator = new Random(); 
        
            phraseCharArray = phrase.getPhraseArray();
            phraseBooleanArray = phrase.getBooleanArray();
            wordIsText.setText("The word is: " + phrase.getHidePhrase());
            spinBtn.setDisable(false);
            //VBox newGame = new VBox();
            //sp.setContent(newGame);
        });
 
        spinBtn.setOnAction(event ->{          
            
            if(gameOn == false){
                spinBtn.setDisable(true);
            }
            
            rotate.setByAngle(360);
            rotate.play();
            spinWheelValue = spin.getWheelValue(randomGenerator.nextInt(spin.getSize()));           
            if(spinWheelValue == 0){               
                textBox = new Label ("Player spins bankruptcy!");
                textBox.setFont(Font.font(20));
                vBox.getChildren().add(textBox);
                sp.setContent(vBox);
                playerScore = 0;
                playerName.setText("PlayerScore: " + playerScore);
                spinBtn.setDisable(true);
                hasSpun = true;
                computerTurn = true;
                computerTurnSpin(computerTurn);                
            }
            else if(spinWheelValue < 0){
                textBox = new Label ("Player spins a " + spinWheelValue);
                textBox.setFont(Font.font(20));
                vBox.getChildren().add(textBox);
                sp.setContent(vBox);
                textBox = new Label ("Player loses " + spinWheelValue + " from his total");
                textBox.setFont(Font.font(20));
                vBox.getChildren().add(textBox);
                sp.setContent(vBox);
                playerScore = playerScore + spinWheelValue;
                playerName.setText("PlayerScore: " + playerScore);
                if(playerScore < 0){
                    playerScore = 0;
                    playerName.setText("PlayerScore: " + playerScore);
                }
                spinBtn.setDisable(true);
                hasSpun = true;
                computerTurn = true;
                computerTurnSpin(computerTurn);               
            }
            else if(spinWheelValue > 0){
                textBox = new Label ("Player spins a " + spinWheelValue);
                textBox.setFont(Font.font(20));
                vBox.getChildren().add(textBox);
                sp.setContent(vBox);
                hasSpun = true;
                guessPhraseBtn.setDisable(false);
                userInput.setDisable(false);
                spinBtn.setDisable(true);
            }           
        });
        
        userInput.setOnKeyPressed(event ->{
            guessPhraseBtn.setDisable(true);
            if(hasSpun){
                userInput.setDisable(false);
            }
            else
                userInput.setDisable(true);
            if(event.getCode().equals(KeyCode.ENTER)){            
                try{
                    if(Character.isLetter(userInput.getText().charAt(0))){
                        char secretLetter = userInput.getText().charAt(0);
                        textBox = new Label("Player Guessed " + userInput.getText().charAt(0));                                              
                        textBox.setFont(Font.font(20));
                        vBox.getChildren().add(textBox);
                        sp.setContent(vBox);

                        userInput.setText(null);

                        if(phrase.getPhrase().contains(secretLetter + "")){           
                            if(gameOn){
                                for(int i = 0; i < phrase.getPhraseArray().length; i++){
                                spinBtn.setDisable(true);
                                if(phraseCharArray[i] == secretLetter){
                                    flag = true;
                                    if(phraseBooleanArray[i]){
                                        userInput.setDisable(true);
                                        flag = false;
                                        hasSpun = false;
                                        textBox = new Label("That letter has been guessed!");
                                        textBox.setFont(Font.font(20));
                                        vBox.getChildren().add(textBox);
                                        computerTurn = true;
                                        computerTurnSpin(computerTurn);
                                        break;
                                    }
                                    else{                                    
                                        arrayCount += 1;
                                        phraseBooleanArray[i] = true;
                                        wordIsText.setText(hidePhrase());
                                    
                                        if(arrayCount == 1){
                                            playerScore = playerScore + spinWheelValue;
                                            playerName.setText("PlayerScore: " + playerScore);
                                            arrayCount = 0;
                                        }
                        
                                        if(arrayCount > 1){
                                            playerScore = playerScore + (arrayCount*spinWheelValue);
                                            playerName.setText("PlayerScore: " + playerScore);
                                            arrayCount = 0;
                                        }                                    
                                    }  
                                }                               
                            }
                            }
                            if(flag){
                                userInput.setDisable(true);
                                hasSpun = false;
                                textBox = new Label("Player is correct!");
                                textBox.setTextFill(Color.GREEN);
                                textBox.setFont(Font.font(20));
                                vBox.getChildren().add(textBox);
                            }
                        }
                        else{
                            userInput.setDisable(true);
                            hasSpun = false;
                            textBox = new Label("Player is incorrect!");
                            textBox.setTextFill(Color.BLUE);
                            textBox.setFont(Font.font(20));
                            vBox.getChildren().add(textBox);
                            computerTurn = true;
                            computerTurnSpin(computerTurn);
                        }
                        if(flag){
                            spinBtn.setDisable(false);
                        }
                        if(areAllTrue(phraseBooleanArray)){
                            gameOn = false;
                            replayBtn.setDisable(false);                          
                            phraseInput.setText(null);
                            phraseInput.setDisable(true);
                            spinBtn.setDisable(true);
                            wordIsText.setText(phrase.getPhrase());
                            textBox = new Label ("You Win!");
                            textBox.setFont(Font.font(20));
                            textBox.setTextFill(Color.DARKORCHID);
                            vBox.getChildren().add(textBox);
                        }
                    }      
                    else{
                        userInput.setText(null);
                        throw new Exception();                        
                    }
                }
                catch(Exception ex){                   
                    textBox = new Label ("INVALID INPUT");
                    textBox.setFont(Font.font(20));
                    textBox.setTextFill(Color.RED);
                    vBox.getChildren().add(textBox);
                }
            }
            
        });
        
        guessPhraseBtn.setOnAction(event ->{
            
            if(hasSpun){
                phraseInput.setDisable(false);
                guessPhraseBtn.setDisable(false);
            }
            else{
                guessPhraseBtn.setDisable(true);
                phraseInput.setDisable(true);
            } 
            userInput.setDisable(true);
            guessPhraseBtn.setDisable(true);
        });
        
        phraseInput.setOnKeyPressed(event ->{
            if(event.getCode().equals(KeyCode.ENTER)){
                textBox = new Label("Player Guessed " + phraseInput.getText());                                              
                textBox.setFont(Font.font(20));
                vBox.getChildren().add(textBox);
                sp.setContent(vBox);               
                if(phraseInput.getText().equalsIgnoreCase(phrase.getPhrase())){
                    gameOn = false;
                    replayBtn.setDisable(false);
                    phraseInput.setText(null);
                    phraseInput.setDisable(true);
                    wordIsText.setText(phrase.getPhrase());
                    textBox = new Label ("You Win!");
                    textBox.setFont(Font.font(20));
                    textBox.setTextFill(Color.DARKORCHID);
                    vBox.getChildren().add(textBox);
                }
                else{
                    phraseInput.setText(null);
                    phraseInput.setDisable(true);
                    hasSpun = false;
                    textBox = new Label ("Player is incorrect");
                    textBox.setFont(Font.font(20));
                    textBox.setTextFill(Color.BLUE);
                    vBox.getChildren().add(textBox);
                    computerTurn = true;
                    computerTurnSpin(computerTurn);
                }
            }
        });
    }
    
    @Override
    public void start(Stage primaryStage) throws Exception {
        userInput.setPrefWidth(350);
        phraseInput.setPrefWidth(350);
        
        phraseInput.setDisable(true);
        userInput.setDisable(true);
        guessPhraseBtn.setDisable(true);
        
        wordIsBox.setArcHeight(20);
        wordIsBox.setArcWidth(20);
        wordIsBox.setFill(scoreGrad);
        wordIsText.setText(hidePhrase());
        wordIsText.setFont(Font.font(20));
        
        computerScoreBox.setArcHeight(20);
        computerScoreBox.setArcWidth(20);
        computerScoreBox.setFill(scoreGradCPU);
        computerName.setFont(Font.font(20));
        
        playerScoreBox.setArcHeight(20);
        playerScoreBox.setArcWidth(20);
        playerScoreBox.setFill(scoreGrad);
        playerName.setFont(Font.font(20));     
        
        sp.setHbarPolicy(ScrollPane.ScrollBarPolicy.NEVER);
        sp.setVbarPolicy(ScrollPane.ScrollBarPolicy.AS_NEEDED);
        sp.setPrefSize(350, 605);

        initEventHandlers();

        spinBtn.setPrefSize(350, 70);
        spinBtn.setFont(Font.font(20));
        
        replayBtn.setPrefSize(100, 100);
        replayBtn.setFont(Font.font(20));
        
        replayBtn.setDisable(true);
        
        guessPhraseBtn.setPrefSize(100, 100);
        guessPhraseBtn.setFont(Font.font(13));
        
        wheelImage.setImage(wheel);
        
        StackPane playerScoreStack = new StackPane();
        StackPane computeScoreStack = new StackPane();
        StackPane wordIsStack = new StackPane();
        
        playerScoreStack.getChildren().addAll(playerScoreBox, playerName);
        computeScoreStack.getChildren().addAll(computerScoreBox, computerName);
        wordIsStack.getChildren().addAll(wordIsBox, wordIsText);
        
        Pane pane = new Pane();       
        pane.getChildren().addAll(wheelImage, userInput, phraseInput, spinBtn, replayBtn, guessPhraseBtn, sp, playerScoreStack, computeScoreStack, wordIsStack);
        
        userInput.relocate(0, 0);
        phraseInput.relocate(0, 0);
        sp.relocate(0, 25);
        playerScoreStack.relocate(350, 630);
        computeScoreStack.relocate(675, 630);
        wordIsStack.relocate(350, 0);
        spinBtn.relocate(0, 630);
        guessPhraseBtn.relocate(350, 63);
        wheelImage.relocate(420, 90);
        replayBtn.relocate(350, 530);
  
        wheelImage.setScaleX(1.1);
        wheelImage.setScaleY(1.1);
      
        Scene inputArea = new Scene(pane, 1000, 700);
        
        primaryStage.setTitle("Wheel Of Fortune");
        primaryStage.setScene(inputArea);
        primaryStage.show();                
    }
    
    public void computerTurnSpin(boolean computerTurn){
        rotate.setByAngle(360);
        rotate.play();
        spinWheelValue = spin.getWheelValue(randomGenerator.nextInt(spin.getSize()));
        if(spinWheelValue == 0){
            textBox = new Label ("Computer spins bankruptcy!");
            textBox.setFont(Font.font(20));
            vBox.getChildren().add(textBox);
            sp.setContent(vBox);
            computerScore = 0;
            computerName.setText("ComputerScore: " + computerScore);
            flag = false;
            hasSpun = false;
            spinBtn.setDisable(false);
            computerScore = 0;
        }
        else if(spinWheelValue < 0){
            textBox = new Label ("Computer spins a " + spinWheelValue);
            textBox.setFont(Font.font(20));
            vBox.getChildren().add(textBox);
            sp.setContent(vBox);
            textBox = new Label ("Computer loses " + spinWheelValue + " from his total");
            textBox.setFont(Font.font(20));
            vBox.getChildren().add(textBox);
            sp.setContent(vBox);
            computerScore = computerScore + spinWheelValue;
            computerName.setText("ComputerScore: " + computerScore);
            if(computerScore < 0){
                computerScore = 0;
                computerName.setText("ComputerScore: " + computerScore);
            }
            hasSpun = false;
            spinBtn.setDisable(false);
            flag = false;           
        }
        else if(spinWheelValue > 0){
            textBox = new Label ("Computer spins a " + spinWheelValue);
            textBox.setFont(Font.font(20));
            vBox.getChildren().add(textBox);
            flag = true;
            sp.setContent(vBox);
        }
        
        if(flag){
            char computerGuess = (char)(random.nextInt(26) + 'a');
            textBox = new Label("Computer Guessed " + computerGuess);                                              
            textBox.setFont(Font.font(20));
            vBox.getChildren().add(textBox);
            sp.setContent(vBox);
            char secretLetter = computerGuess;
            if(phrase.getPhrase().contains(secretLetter + "")){
                
                if(areAllTrue(phraseBooleanArray)){
                    gameOn = false;
                    replayBtn.setDisable(false);                   
                    phraseInput.setText(null);
                    phraseInput.setDisable(true);
                    spinBtn.setDisable(true);
                    wordIsText.setText(phrase.getPhrase());
                    textBox = new Label ("Computer Wins!");
                    textBox.setFont(Font.font(20));
                    textBox.setTextFill(Color.DARKORCHID);
                    vBox.getChildren().add(textBox);
                }
                
                for (int i = 0; i < phraseCharArray.length; i++) { 
                    if (phraseCharArray[i] == secretLetter) {
                        flag = true;
                        if(phraseBooleanArray[i]){
                            flag = false;
                            hasSpun = false;
                            spinBtn.setDisable(false);
                            textBox = new Label("That letter has been guessed!");
                            textBox.setFont(Font.font(20));
                            vBox.getChildren().add(textBox);
                            break;
                        }
                        else{
                            arrayCount += 1;                                     
                            phraseBooleanArray[i] = true;
                            wordIsText.setText(hidePhrase());
                                    
                            if(arrayCount == 1){
                                computerScore = computerScore + spinWheelValue;
                                computerName.setText("ComputerScore: " + computerScore);
                                arrayCount = 0;
                            }
                        
                            if(arrayCount > 1){
                                computerScore = computerScore + (arrayCount*spinWheelValue);
                                computerName.setText("ComputerScore: " + computerScore);
                                arrayCount = 0;
                            }  
                        }                                
                    }               
                }
                if(flag){
                    spinBtn.setDisable(true);
                    textBox = new Label("Computer is correct!");
                    textBox.setTextFill(Color.GREEN);
                    textBox.setFont(Font.font(20));
                    vBox.getChildren().add(textBox);
                    computerTurnSpin(computerTurn);
                }            
            }
        
            else{
                hasSpun = false;
                spinBtn.setDisable(false);
                textBox = new Label("Computer is incorrect!");
                textBox.setTextFill(Color.BLUE);
                textBox.setFont(Font.font(20));
                vBox.getChildren().add(textBox);
            }
        }
    }
    
    public String hidePhrase(){
        String hidePhrase = "The word is: ";
        for(int i = 0; i < phraseCharArray.length; i++){
            if(phraseBooleanArray[i]){
                hidePhrase = hidePhrase + phraseCharArray[i];
            }
                            
            else{
                hidePhrase = hidePhrase + "_" + " ";
            }
        }
        return hidePhrase;
    }
    
    public boolean areAllTrue(boolean[] array){
        for(boolean b : array) if(!b) return false;
            return true;
    }   

    public static void main(String[] args) {
        launch(args);
    }
    
}
