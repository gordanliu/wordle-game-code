import java.util.Scanner;

class Main {
    public static void main(String[] args) {
        Scanner console = new Scanner(System.in);
        Wordle game = new Wordle("words");

        while (game.attempt < 6) {
            System.out.println();
            System.out.print("Guess word: ");
            game.makeGuess(console.nextLine());
        }
    }
}
