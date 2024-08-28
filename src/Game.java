import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Random;
import java.util.stream.IntStream;

public class Game {
    class Result {
        int bulls;
        int cows;
        Result(int bulls, int cows) {
            this.bulls = bulls;
            this.cows = cows;
        }
        boolean isCorrect() {
            return bulls == code.length();
        }
        public String result(String prefix) {
            List<String> s = new ArrayList<>();
            if (bulls > 0) {
                s.add("%d %s".formatted(bulls, bulls == 1 ? "bull" : "bulls"));
            }
            if (cows > 0) {
                s.add("%d %s".formatted(cows, cows == 1 ? "cow" : "cows"));
            }
            if (bulls == 0 && cows == 0) {
                s.add("None");
            }
            return prefix + String.join(" and ", s.toArray(String[]::new)) + ".";
        }

        public String result() {
            return result("Grade: ");
        }
    }

    final private String code;
    final private String symbols;
    private String symbolsHint;
    private boolean isSolved = false;
    private int turn = 1;

    private String generate(int l) {
        Random rand = new Random();
        char[] arr = symbols.toCharArray();
        for (int i = 0; i < 10000; i++) {
            int x = rand.nextInt(symbols.length());
            int y = rand.nextInt(symbols.length());
            char tmp = arr[x];
            arr[x] = arr[y];
            arr[y] = tmp;
        }
        return new String(Arrays.copyOfRange(arr, 0, l));
    }

    Game(int length, String symbols) {
        IntStream distinctSymbols = symbols.codePoints().distinct();
        this.symbols = new String(distinctSymbols.toArray(), 0, symbols.length());
        length = Math.max(0, length);
        length = Math.min(length, symbols.length());
        code = generate(length);
        symbolsHint = "(%s)".formatted(symbols);
    }

    Game(String code, String symbols) {
        IntStream distinctSymbols = symbols.codePoints().distinct();
        this.symbols = new String(distinctSymbols.toArray(), 0, symbols.length());
        this.code = code;
    }

    Game(int length) {
        this(length, 36);
    }

    Game(int codeLength, int symbolsCount) {
        this(codeLength, "0123456789abcdefghijklmnopqrstuvwxyz".substring(0,symbolsCount));
        if (symbolsCount > 10) {
            symbolsHint = "(0-9, a-%c)";
        } else {
            symbolsHint = "(0-%c)";
        }
        symbolsHint = symbolsHint.formatted(symbols.charAt(symbolsCount - 1));
    }

    public Result check(String answer) {
        if (code.equals(answer)) {
            isSolved = true;
            return new Result(code.length(), 0);
        }
        int bulls = 0;
        int cows = 0;
        if (answer.length() == code.length()) {
            for (int i = 0; i < code.length(); i++) {
                if (answer.charAt(i) == code.charAt(i)) {
                    bulls++;
                } else if (code.contains(String.valueOf(answer.charAt(i)))) {
                    cows++;
                }
            }
            turn++;
        }
        return new Result(bulls, cows);
    }

    public String showCode() {
        return isSolved ? code : "*".repeat(code.length());
    }

    public int getTurn() {
        return turn;
    }

    public boolean isSolved() {
        return isSolved;
    }

    public String getAllowedSymbols() {
        return symbols;
    }

    public String getHint() {
        return symbolsHint;
    }
}
