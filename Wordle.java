import java.util.*;

public class Wordle {
    int attempt;
    final String answer;
    String[] guessedWords;
    String[] keyboard = { "Q W E R T Y U I O P", "A S D F G H J K L", "Z X C V B N M" };
    ArrayList<String> usedLetters;

    final String GREEN_BG = "\u001b[42m";
    final String GREEN_TEXT = "\u001B[32m";
    final String YELLOW_BG = "\u001b[43m";
    final String YELLOW_TEXT = "\u001B[33m";
    final String RESET = "\u001b[0m";
    final String UNDERLINE = "\u001b[4m";

    public Wordle(String s) {
        answer = s.toUpperCase();
        attempt = 0;
        guessedWords = new String[6];
        usedLetters = new ArrayList<String>();
    }

    public void makeGuess(String guess) {
        if (guess.length() != 5 || !isAlpha(guess))
            System.out.println("Invalid guess!");
        else if (checkIfUsed(guess.toUpperCase()))
            System.out.println("You already used that word!");
        else {
            guess = guess.toUpperCase();
            guessedWords[attempt] = guess;
            attempt++;
            for (String a : guess.split("")) {
                if (!usedLetters.contains(a.toUpperCase()))
                    usedLetters.add(a.toUpperCase());
            }
            this.displayGuesses();
            this.displayKeyboard();
            if (guess.equals(answer))
                this.win();
            else if (attempt == 6)
                this.lose();
            if (!guess.equals(answer))
                System.out.println("----------------------");
        }
    }

    public void displayGuesses() {
        System.out.println();
        for (int i = 0; i < 6; i++) {
            System.out.print("       ");
            if (guessedWords[i] != null) {
                this.displayGuess(guessedWords[i]);
            } else {
                System.out.println("_____");
            }

        }
        System.out.println();
    }

    public void displayGuess(String guess) {

        // create arrayList of chars for the answer; corrects any duplicates for YELLOW
        // letters
        String[] lets = answer.split("");
        ArrayList<String> letters = new ArrayList<>();
        for (String x : lets) {
            letters.add(x);
        }

        // for every letter in guess
        for (int i = 0; i < 5; i++) {

            // if letter is GREEN (in the word, right spot)
            if (guess.substring(i, i + 1).equals(answer.substring(i, i + 1))) {
                System.out.print(GREEN_BG + guess.substring(i, i + 1).toUpperCase() + RESET);

                // remove letter from arrayList
                letters.remove(answer.substring(i, i + 1));

                // if letter is YELLOW (in the word, wrong spot); second conditional checks if
                // the letter is remaining in arrayList
            } else if ((answer.indexOf(guess.substring(i, i + 1)) > -1)
                    && (letters.contains(guess.substring(i, i + 1)))) {
                System.out.print(YELLOW_BG + guess.substring(i, i + 1).toUpperCase() + RESET);
            }

            // if letter is CLEAR (not in the word)
            else
                System.out.print(guess.substring(i, i + 1).toUpperCase());
        }
        System.out.println();
    }

    public void displayKeyboard() {
        String spaces = "";

        for (int i = 0; i < 3; i++) {
            spaces += " ";
            System.out.print(spaces);

            // iterates through every row
            String[] rowArray = this.keyboard[i].split("");
            for (int j = 0; j < rowArray.length; j++) {
                String character = rowArray[j].toUpperCase();

                // character used
                if (this.usedLetters.contains(character)) {
                    boolean yellow = false;
                    boolean green = false;

                    // checks through guesses to see if character should be yellow, green, or
                    // neither
                    for (String word : this.guessedWords) {
                        if (word != null) {
                            if (word.contains(character)) {
                                if (word.indexOf(character) == answer.indexOf(character))
                                    green = true;
                                else if (answer.contains(character))
                                    yellow = true;
                            }
                        }
                    }
                    if (green)
                        System.out.print(UNDERLINE + GREEN_TEXT + character + RESET);
                    else if (yellow)
                        System.out.print(UNDERLINE + YELLOW_TEXT + character + RESET);
                    else
                        System.out.print(UNDERLINE + character + RESET);

                    // character not used
                } else
                    System.out.print(character);
            }
            System.out.println();
        }
        System.out.println();
    }

    public boolean checkIfUsed(String guess) {
        for (String word : guessedWords)
            if (guess.equals(word))
                return true;
        return false;
    }

    public boolean isAlpha(String guess) {
        return guess.matches("[a-zA-Z]+");
    }

    public void win() {
        System.out.println("You guessed the word!\n");
        this.attempt = 6;
    }

    public void lose() {
        System.out.println("\nYou ran out of guesses!\n  The word was " + GREEN_TEXT + answer + RESET + ".");
    }
}