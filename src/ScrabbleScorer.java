import java.io.File;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.Scanner;

public class ScrabbleScorer {

    private String alpha = "ABCDEFGHIJKLMNOPQRSTUVWXYZ";
    private int[] values = {1,3,3,2,1,4,2,4,1,8,5,1,3,1,1,3,10,1,1,1,1,4,4,8,4,10};
    private ArrayList<ArrayList<String>> dictionary;

    /**
     * simple constructer, initializes arrays
     */

    public ScrabbleScorer(){
        dictionary = new ArrayList<>();
        for( int i = 0; i < 26; i++){
            dictionary.add(new ArrayList<String>());
        }
        buildDictionary();
    }

    /**
     * Builds the dictionary for fast searching, sorts the dictionary into an arrayList with 26 arrayLists
     * in alphabetical order
     */
    public void buildDictionary(){
        try{
            Scanner in = new Scanner(new File("SCRABBLE_WORDS.txt"));
            while(in.hasNext()){
                String word = in.nextLine();
                //System.out.println("DEBUG: " + word);
                //System.out.println("DEBUG: " + alpha.indexOf(word.substring(0,1)));
                dictionary.get(alpha.indexOf(word.substring(0,1))).add(word);
            }
            in.close();
            for(int i = 0; i < dictionary.size(); i++){
                Collections.sort(dictionary.get(i));
            }
            //for(ArrayList<String> bucket : dictionary)
            //System.out.println(bucket);
        }
        catch(Exception e){
            System.out.println("Error here: " + e);
            e.printStackTrace();
        }
    }


    public boolean isValidWord(String word){
        //find the bucket starting with the correct letter
        //System.out.println(Collections.binarySearch(dictionary.get(alpha.indexOf(word.substring(0,1))), word ) < 0);
        try {
            if (Collections.binarySearch(dictionary.get(alpha.indexOf(word.substring(0, 1))), word) < 0)
                return false;
            return true;
        }
        catch(Exception e) {
            return false;
        }
    }


    public int getWordScore(String word){
        int score = 0;
        for(int i = 0; i < word.length(); i++){
            score += values[alpha.indexOf(word.substring(i, i+1))];
        }
        return score;
    }


    public static void main(String[] args){
        ScrabbleScorer app = new ScrabbleScorer();
        System.out.println("* Welcome to the Scrabble Word Scorer app *");
        Scanner in = new Scanner(System.in);
        while(true){
            System.out.print("Enter a word to score or 0 to quit: ");
            String word = in.nextLine().toUpperCase();
            if (word.equals("0")){
                break;
            }
            //System.out.println(app.isValidWord(word));
            if(app.isValidWord(word)){
                System.out.println(word + " = " + app.getWordScore(word) + " points");
            }
            else
                System.out.println(word + " is not a valid word");
        }
        System.out.println("Exiting the program thanks for playing");
    }
}
