package wheeloffortunegame;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Scanner;

public class SpinWheel{
    private ArrayList<Integer> spinValues = new ArrayList<Integer>();
    private Integer wheelValues;
    private int size;
    
    public SpinWheel(){
        
        try{
            Scanner fileSpin = new Scanner(new File("spin.txt"));
            while(fileSpin.hasNextInt()){
                spinValues.add(fileSpin.nextInt());
                size++;
            }
        }
        catch(FileNotFoundException ex){
            System.err.println("SpinWheel file not found!");
        }
    }
    
    public int getSize(){
        return size;
    }
    
    public void setSize(int size){
        this.size = size;
    }
    
    public int getWheelValue(int index){
        return spinValues.get(index);
    }
}
