import java.util.*;

public class Wordle {
    boolean done;
    int attempt;
    final String answer;
    String[] guessedWords;
    ArrayList<String> usedLetters;

    final String GREEN = "\u001b[42m";
    final String YELLOW = "\u001b[43m";
    final String RESET = "\u001b[0m";

    public Wordle(String s) {
        answer = s;
        done = false;
        attempt = 0;
        guessedWords = new String[6];
        usedLetters = new ArrayList<String>();
    }

    public void makeGuess(String guess) {
        if (!checkLength(guess))
            System.out.println("Invalid guess!\n");
        else if (checkIfUsed(guess))
            System.out.println("You already used that word!\n");
        else {
            guessedWords[attempt] = guess;
            attempt++;
            // this.displayGuess(guess);
            for (String a : guess.split("")) {
                if (!usedLetters.contains(a))
                    usedLetters.add(a);
            }
            this.displayGuesses();
            if (guess.equals(answer)) {
                this.win();
            } else if (attempt == 6) {
                this.lose();
            }
        }
        System.out.println("\n---------------------------");
    }

    public void displayGuesses() {
        System.out.println();
        for (int i = 0; i < attempt; i++) {
            this.displayGuess(guessedWords[i]);
        }
    }

    public void displayGuess(String guess) {
        String[] lets = answer.split("");
        ArrayList<String> letters = new ArrayList<>();
        for (String x : lets) {
            letters.add(x);
        }

        for (int i = 0; i < 5; i++) {
            if (guess.substring(i, i + 1).equals(answer.substring(i, i + 1))) {
                System.out.print(GREEN + guess.substring(i, i + 1) + RESET);
                letters.remove(answer.substring(i, i + 1));
            } else if ((answer.indexOf(guess.substring(i, i + 1)) > -1)
                    && (letters.contains(guess.substring(i, i + 1))))
                System.out.print(YELLOW + guess.substring(i, i + 1) + RESET);
            else
                System.out.print(guess.substring(i, i + 1));
        }
        System.out.println();
    }

    public boolean checkLength(String guess) {
        return guess.length() == 5;
    }

    public boolean checkIfUsed(String guess) {
        for (String word : guessedWords)
            if (guess.equals(word))
                return true;
        return false;
    }

    public void win() {
        System.out.println("\nYou guessed the word!");
        done = true;
    }

    public void lose() {
        System.out.println("\nYou ran out of guesses!\nThe word was " + answer);
        done = true;
    }

}
