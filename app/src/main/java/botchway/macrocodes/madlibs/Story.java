package botchway.macrocodes.madlibs;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Scanner;
import stanford.androidlib.SimpleActivity;

/**
 * Created by Botchway on 7/11/2017.
 */

public class Story extends SimpleActivity{
    private Scanner scan;
    private int numberOfPlaceholders;
    private ArrayList<String> allStrings = new ArrayList<>();
    private ArrayList<String> placeHolderList = new ArrayList<>();
    private ArrayList<String> answers = new ArrayList<>();

    /*constructor*/
    public Story(Scanner file){
        this.scan = file;
        numberOfPlaceholders = 0;
        /*init itself to call placeholders to scan the file*/
        addPlaceHolders();
    }


    /*add words to the place holder array list if true*/
    private void addPlaceHolders(){
        while(scan.hasNextLine()){
            String line = scan.nextLine();
            String[] words = line.split(" ");

            countPlaceHolders(words);
        }
    }

    /*count individual place holders in the string array*/
    private void countPlaceHolders(String[] words){
        for(String word : words){
            if(isPLaceHolder(word)){
                placeHolderList.add(word);
                numberOfPlaceholders++;
                allStrings.add(word);
            }else{
                allStrings.add(word);
            }
        }
        allStrings.add("\n");
    }

    /*checks if the word is a placeholder*/
    private boolean isPLaceHolder(String word){
        if(word.charAt(0) == '<' && word.charAt(word.length()-1) == '>'){
            return true;
        }else{
            return false;
        }
    }

    /*map all the array list of place holders to the answers map*/
    public void setListToMap(ArrayList<String> replaced){
        for(int i=0; i<placeHolderList.size(); i++){
            String answer = replaced.get(i);
            answers.add(answer);
        }
    }

    /*gets the placeholder array list*/
    public ArrayList<String> getPlaceHolderList(){
        return this.placeHolderList;
    }

    /*returns the story with the replaced words*/
    private   String story(){
        String sentence = "";
        int index = 0;
        //replace using indexes
        for(String word : allStrings){
            if(isPLaceHolder(word)){
                String replacer = answers.get(index++);
                sentence = sentence+" " +replacer;
            }else{
                sentence =sentence+" " +word;
            }
        }
        return sentence;
    }



    /*create a string version of the extracted*/
    public String toString(){
        return story();
    }
}
