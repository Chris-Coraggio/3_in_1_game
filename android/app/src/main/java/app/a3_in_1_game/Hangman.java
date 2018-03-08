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
import java.util.Arrays;
import java.util.Random;

public class Hangman {

    //TODO guessing the same letter is fine
    //TODO check to see if word is already complete before prompting

    private static int MAX_WORD_SIZE = 30; //how big (including spaces) the word or phrase to guess can be
    private static String TEXT_FILE =  "C:/Users/cccpo/Desktop/3_in_1_game/android/app/src/main/assets/HangmanWordList.txt";
    private static int MAX_NUM_WORDS_MULTIPLAYER = 20;
    private static double ROUND_TIME_SECS = 120;
    private static int NUM_APPENDAGES = 9;                  //how many appendages are in the hangman


    public static char[] currentWord = new char[MAX_WORD_SIZE];			//stores the word to be guessed (all uppercase)
    public static boolean[] guessed = new boolean[MAX_WORD_SIZE];	//stores the status of each character in the word (true: has been guessed)
    public static char[] lettersGuessed = new char[26];			//stores all letters that the user has guessed (all uppercase)
    public static char[] lettersWrong = new char[26];				//stores all letters that the user has guessed that haven't shown up in the word (all uppercase)
    public static int numWrongWordGuesses = 0;                     //number of word guesses made
    public static long startTime;                                   //timer
    public static int numWordsGuessed = 0;                          //score for multiplayer games
    public static int totalNumWrongGuesses = 0;                     //tiebreaker for multiplayer

    public enum Mode{
        SINGLE_PLAYER, MULTI_PLAYER
    }
    public static Mode playerMode;

    public static char[][] wordsList = new char[MAX_WORD_SIZE][MAX_NUM_WORDS_MULTIPLAYER];

    public static void init(Mode mode, int firstTime){
        //run this at the start of each game
        currentWord = new char[MAX_WORD_SIZE];
        guessed = new boolean[MAX_WORD_SIZE];
        lettersGuessed = new char[26];
        lettersWrong = new char[26];
        numWrongWordGuesses = 0;
        playerMode = mode;
        if(mode == Mode.SINGLE_PLAYER){
            //single player
            currentWord = getRandomWord();
        }else if(mode == Mode.MULTI_PLAYER){
            wordsList = getRandomWords(MAX_NUM_WORDS_MULTIPLAYER);
            currentWord = getNextWord();
        }
    }

    public static void resetWord(){
        //get ready for the next word in multiplayer
        currentWord = getNextWord();
        guessed = new boolean[MAX_WORD_SIZE];
        lettersGuessed = new char[26];
        lettersWrong = new char[26];
        totalNumWrongGuesses += getNumAppendages();
        numWrongWordGuesses = 0;
    }

    public static boolean inLettersGuessed(char c){
        //returns whether or not c is in the lettersGuessed array
        //true: c is in the array
        //false: c is not in the array

        c = Character.toUpperCase(c);

        for(char ch: lettersGuessed){
            if(ch == c){
                return true;
            }
        }

        return false;
    }

    public static char[] getRandomWord(){
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

        //start the buffer back out at the top of the file
        try {
            fileReader = new FileReader(TEXT_FILE);
            bufferedReader = new BufferedReader(fileReader);
        }catch(Exception e){
            e.printStackTrace();
        }

        //extracts a random line as the word
        int randomLine = rand.nextInt(numLinesInFile);
        String trmp = "";
        for(int i = 0; i < randomLine; i++){
            try {
                trmp = bufferedReader.readLine();
            }catch(IOException e){
                e.printStackTrace();
            }
        }
        return trmp.toCharArray();
        }catch(FileNotFoundException e){
            System.out.println("Can't find the hangman word file.");
        }
        return null;
    }

    public static char[][] getRandomWords(int numWords){
        char[][] toReturn = new char[MAX_WORD_SIZE][MAX_NUM_WORDS_MULTIPLAYER];
        for(int i = 0; i < numWords; i++){
            toReturn[i] = getRandomWord();
        }
        return toReturn;
    }

    public static char[] getNextWord(){
        return wordsList[numWordsGuessed];
    }

    public static void addToLettersGuessed(char c){
		/*
		Finds the next available spot in lettersGuessed and adds c there
		*/
        int i;
        for(i = 0; i < lettersGuessed.length; i++){
            if(lettersGuessed[i] == '\u0000'){
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
            if(lettersWrong[i] == '\u0000'){
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

        for(i = 0; i < currentWord.length; i++){
            if(currentWord[i] == c){
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
                checkHangmanComplete();
                return false;
            }else if (n > 0){
                if(wordIsComplete()){
                    guessWord(new String(currentWord));
                }else{
                  System.out.println("Nice job, keep going!");
                }
                return true;
            }
        }

        return false;
    }

    public static boolean guessWord(String s){
        //TODO handle a word longer than solution
        char [] guessString = s.toCharArray();
        boolean match = true;
        for(int i = 0; i < currentWord.length; i++){
            if(guessString[i] != currentWord[i]){
                match = false;
            }
        }
        if(match){
            System.out.println("Congrats! You got it");
            numWordsGuessed++;
            if(playerMode == Mode.MULTI_PLAYER){
                resetWord();
            }
            return true;
        }else{
            System.out.println("Sorry! Try again");
            numWrongWordGuesses++;
            checkHangmanComplete();
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

    public static void printWord(){
        int index = 0;
        for(char c: currentWord){
            if(guessed[index++]){
                System.out.print("" + c + " ");
            }else{
                System.out.print("_ ");
            }
        }
        System.out.println();
    }

    public static void printNumAppendages(){
        System.out.println(getNumAppendages() + " wrong guesses!");
    }

    public static boolean wordIsComplete(){
        //check along the guessed array
        //if there's a false value in the word, the word is not complete
        //System.out.println(Arrays.toString(guessed));
        int count = 0;
        for(boolean b: guessed){
            if(!b){
                return false;
            }
            if(++count == currentWord.length){
                break;
            }
        }
        return true;
    }

    public static void checkHangmanComplete(){
        if(getNumAppendages() >= NUM_APPENDAGES){
            if(playerMode == Mode.SINGLE_PLAYER){
                System.out.printf("You lose, the word was %s\n", currentWord);
            }else{
                System.out.printf("The word was %s. Keep going!\n", currentWord);
                resetWord();
            }
        }
    }

    public static double getElapsedTime() {
        long now = System.currentTimeMillis();
        return (now - startTime) / 1000.0;
    }

    public static void main(String [] args){
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        char c = ' ';
        int gameMode = 0;
        System.out.println("1. Single Player    2. Multiplayer");
        try {
            gameMode = Integer.parseInt(br.readLine());
        }catch(Exception e){
            e.printStackTrace();
        }
        if(gameMode == 1) {
            //single player
            init(Mode.SINGLE_PLAYER, 1);
            System.out.println(currentWord);
            while (true) {
                System.out.println("Enter a letter (1 to quit, 2 to solve, 3 to play again): ");
                try {
                    c = (char) br.read();
                    br.readLine();
                } catch (IOException e) {
                    e.printStackTrace();
                }
                if (!Character.isLetter(c)) {
                    if (c == '1') {
                        System.out.println("Thanks for playing");
                        System.exit(0);
                    } else if (c == '2') {
                        System.out.println("Enter the word");
                        String s;
                        try {
                            s = br.readLine();
                            if (guessWord(s)) {
                                currentWord = getRandomWord();
                            }
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                    } else if (c == '3') {
                        init(Mode.SINGLE_PLAYER, 1);
                    } else {
                        System.out.println("Invalid input! Try a letter");
                    }
                } else {
                    guessLetter(c);
                    printWord();
                    printNumAppendages();
                }
            }
        }else if(gameMode == 2){
            //multiplayer
            startTime = System.currentTimeMillis();
            init(Mode.MULTI_PLAYER, 1);
            System.out.println(currentWord);
            while (getElapsedTime() < ROUND_TIME_SECS) {
                System.out.println(ROUND_TIME_SECS - getElapsedTime());
                System.out.println("Enter a letter (1 to quit, 2 to solve): ");
                try {
                    c = (char) br.read();
                    br.readLine();
                } catch (IOException e) {
                    e.printStackTrace();
                }
                if (!Character.isLetter(c)) {
                    if (c == '1') {
                        System.out.println("Thanks for playing");
                        System.exit(0);
                    } else if (c == '2') {
                        System.out.println("Enter the word");
                        String s;
                        try {
                            s = br.readLine();
                            if (guessWord(s)) {
                                resetWord();
                            }
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                    } else {
                        System.out.println("Invalid input! Try a letter");
                    }
                } else {
                    guessLetter(c);
                    printWord();
                    printNumAppendages();
                }
            }
            System.out.printf("POST TO SERVER: %s, %s, %s", "username", numWordsGuessed, totalNumWrongGuesses + getNumAppendages());
            //In the server, the user with the highest numWordsGuessed will win
            //If they both have the same numWordsGuessed, the user with the lowest numWrongWordGuesses wins
            //If they both also have the same numWrongWordGuesses, they tie
        }else{
            //error
            System.out.println("Problem");
        }
    }
}
