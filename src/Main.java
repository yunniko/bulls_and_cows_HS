import java.util.*;

public class Main {

    public static void main(String[] args) {
        Scanner in = new Scanner(System.in);
        int length = 0, symbolsCount = 0;
        System.out.println("Please, enter the secret code's length:");
        if (in.hasNextInt()) {
            length = in.nextInt();
            if (length > 36) {
                System.out.println("Error: can't generate a secret number with a length of %d because there aren't enough unique symbols.".formatted(length));
                return;
            } else if (length <= 0) {
                System.out.println("Error: length should be larger than 0.");
                return;
            }
        } else {
            System.out.println("Error: \"%s\" isn't a valid number.".formatted(in.nextLine()));
            return;
        }
        System.out.println("Input the number of possible symbols in the code:");

        if (in.hasNextInt()) {
            symbolsCount = in.nextInt();
            if (symbolsCount < length) {
                System.out.println("Error: it's not possible to generate a code with a length of %d with %d unique symbols.".formatted(length, symbolsCount));
                return;
            } else if (symbolsCount > 36) {
                System.out.println("Error: maximum number of possible symbols in the code is 36 (0-9, a-z).");
                return;
            }
        } else {
            System.out.println("Error: \"%s\" isn't a valid number.".formatted(in.nextLine()));
            return;
        }
        Game game = new Game(length, symbolsCount);
        System.out.println("The secret is prepared: %s %s.".formatted(game.showCode(), game.getHint()));
        System.out.println("Okay, let's start a game!");
        while(!game.isSolved()) {
            System.out.println("Turn %d:".formatted(game.getTurn()));
            String answer = in.next();
            System.out.println(game.check(answer).result());
        }
        System.out.println("Congratulations! You guessed the secret code.");
    }
}

