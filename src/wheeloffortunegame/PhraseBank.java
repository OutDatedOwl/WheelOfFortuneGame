package wheeloffortunegame;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Scanner;

public class PhraseBank {
    private ArrayList<String> phraseBank = new ArrayList<String>();
    private String phraseChosen;
    
    public PhraseBank(){
        try{
            Scanner files = new Scanner(new File("test.txt"));
            while(files.hasNext()){
                phraseBank.add(files.nextLine());
            }
            phraseChosen = (phraseBank.get((int)(Math.random() * phraseBank.size())).toLowerCase());
        }
        catch(FileNotFoundException ExamplesNotFound){
            System.err.println("PhraseBank file not found!");
        }
    }
    
    public String getPhrase(){
        return phraseChosen;
    }
    
    public String getHidePhrase(){
        String hidePhrase = phraseChosen.replaceAll("[A-z]", "_" + " ");
        return hidePhrase;
    }
    
    public char[] getPhraseArray(){
        char[] phraseArray = phraseChosen.toCharArray();
        return phraseArray;
    }
    
    public boolean[] getBooleanArray(){
        boolean[] booleanArray = new boolean[phraseChosen.length()];
        for(int i = 0; i < phraseChosen.length(); i++){
            if(phraseChosen.charAt(i) == " ".charAt(0)){
                booleanArray[i] = true;
            }
        }
        return booleanArray;
    }
}
