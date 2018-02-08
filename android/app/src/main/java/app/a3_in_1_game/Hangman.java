package app.a3_in_1_game;

/**
 * Created by hamza on 2/2/2018.
 */

// TODO: Code this class in IntelliJ and copy the code over
// TODO: Use STDIN and STDOUT until we meet and implement the GUI
// TODO: Make sure code is commented and readable
// TODO: Make helper functions
// TODO: Use character array to store the word

import java.io.*;
import java.util.Random;

public class Hangman {

    private static int MAX_WORD_SIZE = 30; //how big (including spaces) the word or phrase to guess can be
    private static String TEXT_FILE = "";

    public static char[] word = new char[MAX_WORD_SIZE];			//stores the word to be guessed (all uppercase)
    public static boolean[] guessed = new boolean[MAX_WORD_SIZE];	//stores the status of each character in the word (true: has been guessed)
    public static char[] lettersGuessed = new char[26];			//stores all letters that the user has guessed (all uppercase)
    public static char[] lettersWrong = new char[26];				//stores all letters that the user has guessed that haven't shown up in the word (all uppercase)
    public static int numWrongWordGuesses = 0;                     //number of word guesses made

    public static boolean inLettersGuessed(char c){
        //returns whether or not c is in the lettersGuessed array
        //true: c is in the array
        //false: c is not in the array

        for(char ch: lettersGuessed){
            if(ch == c){
                return true;
            }
        }

        return false;
    }

    public static void getRandomWord(){
        //populates word with a random word from TEXT_FILE
        Random rand = new Random();
        try {
        FileReader fileReader;
            fileReader = new FileReader(TEXT_FILE);
        BufferedReader bufferedReader = new BufferedReader(fileReader);

        int numLinesInFile = 0;
        String line;

        try {
            while ((line = bufferedReader.readLine()) != null) {
                numLinesInFile++;
            }
        }catch(IOException e){
            System.out.println("IOException: Error reading the file");
        }

        rand.nextInt(numLinesInFile);
        }catch(FileNotFoundException e){
            System.out.println("Can't find the hangman word file.");
        }
    }

    public static void addToLettersGuessed(char c){
		/*
		Finds the next available spot in lettersGuessed and adds c there
		*/
        int i;
        for(i = 0; i < lettersGuessed.length; i++){
            if(word[i] == '\u0000'){
                break;
            }
        }
        if(lettersGuessed.length != i){
            lettersGuessed[i] = Character.toUpperCase(c);
        }else{
            System.out.println("Error: lettersGuessed array too full");
        }
    }

    public static void addToLettersWrong(char c){
		/*
		Finds the next available spot in lettersGuessed and adds c there
		*/
        int i;
        for(i = 0; i < lettersWrong.length; i++){
            if(word[i] == '\u0000'){
                break;
            }
        }
        if(lettersWrong.length != i){
            lettersWrong[i] = Character.toUpperCase(c);
        }else{
            System.out.println("Error: lettersWrong array too full");
        }
    }

    public static int getInstancesOfLetter(char c){
        //updates the guessed array with all instances of c
        //returns the number of changes made
        int i;
        int count = 0;

        for(i = 0; i < word.length; i++){
            if(word[i] == c){
                guessed[i] = true;
                count++;
            }
        }

        return count;
    }

    public static boolean guessLetter(char c){
        //User guesses letter c
        //true: c is in the word
        //false: c is not in the word

        if(inLettersGuessed(c)){
            System.out.println("Error: User has already guessed that letter");
            return false;
        }else{
            addToLettersGuessed(c);
            int n = getInstancesOfLetter(c);
            if(n == 0){ //if the guess was unsuccessful
                System.out.println("Guess unsuccessful, try again!");
                addToLettersWrong(c);
                return false;
            }else if (n > 0){
                System.out.println("Nice job, keep going!");
                return true;
            }
        }

        return false;
    }

    public static boolean guessWord(String s){
        if(s.equals(word)){
            System.out.println("Congrats! You got it");
            return true;
        }else{
            System.out.println("Sorry! Try again");
            numWrongWordGuesses++;
            return false;
        }
    }

    public static int getNumAppendages(){
        //returns the number of lines to draw on the hangman

        //get the effective length of lettersWrong
        int numLettersWrong = 0;
        for(char c: lettersWrong){
            if(c != '\u0000'){
                numLettersWrong++;
            }
        }

        return numLettersWrong + numWrongWordGuesses;
    }

    public static void main(String [] args){
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        char c = ' ';
        while(true){
            System.out.println("Enter a letter (1 to quit, 2 to solve): ");
            try {
                c = (char)br.read();
            }catch(IOException e){
                e.printStackTrace();
            }
            if(! Character.isLetter(c)){
                if(c == '1'){
                    System.out.println("Thanks for playing");
                    System.exit(0);
                }else if(c == '2'){
                    System.out.println("Enter the word");
                    String s;
                    try {
                        s = br.readLine();
                        if (guessWord(s)) {
                            getRandomWord();
                        }
                    }catch(IOException e){
                        e.printStackTrace();
                    }
                }else{
                    System.out.println("Invalid input! Try a letter");
                }
            }else{
                guessLetter(c);
            }
        }
    }
}
