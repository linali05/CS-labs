import java.nio.charset.StandardCharsets;
import java.util.Locale;
import java.util.Scanner;

public class CaesarCipherLab {

    private static final String ALPHABET =
            "AĂÂBCDEFGHIÎJKLMNOPQRSȘTȚUVWXYZ";

    private static final int N = 31;

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in, StandardCharsets.UTF_8);

        System.out.println("Caesar Cipher - Laboratory 1");

        int key = readKey(scanner);
        String text = readText(scanner);

        System.out.println("Selected key: " + key);
        System.out.println("Prepared text: " + text);

        scanner.close();
    }

    private static int readKey(Scanner scanner) {
        while (true) {
            System.out.print("Enter key (1-30): ");

            String input = scanner.nextLine();

            try {
                int key = Integer.parseInt(input);

                if (key >= 1 && key <= 30) {
                    return key;
                }

                System.out.println(
                        "Invalid key. Enter a value between 1 and 30."
                );

            } catch (NumberFormatException e) {
                System.out.println(
                        "Invalid key. Enter an integer between 1 and 30."
                );
            }
        }
    }

    private static String readText(Scanner scanner) {
        while (true) {
            System.out.print("Enter text: ");

            String text = scanner.nextLine();

            text = normalizeRomanian(text);
            text = text.toUpperCase(Locale.ROOT);
            text = text.replace(" ", "");

            if (text.isEmpty()) {
                System.out.println(
                        "Invalid text. The text cannot be empty."
                );
                continue;
            }

            Character invalid = findInvalidCharacter(text);

            if (invalid == null) {
                return text;
            }

            System.out.println(
                    "Invalid character: '" + invalid + "'."
            );
            System.out.println(
                    "Only Romanian alphabet letters and spaces are allowed."
            );
        }
    }

    private static String normalizeRomanian(String text) {
        return text
                .replace('Ş', 'Ș')
                .replace('ş', 'ș')
                .replace('Ţ', 'Ț')
                .replace('ţ', 'ț');
    }

    private static Character findInvalidCharacter(String text) {
        for (char ch : text.toCharArray()) {
            if (ALPHABET.indexOf(ch) == -1) {
                return ch;
            }
        }

        return null;
    }
}