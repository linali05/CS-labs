import java.nio.charset.StandardCharsets;
import java.util.Locale;
import java.util.Scanner;

public class CaesarCipherLab {

    private static final String ALPHABET =
            "AĂÂBCDEFGHIÎJKLMNOPQRSȘTȚUVWXYZ";

    private static final int N = 31;

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in, StandardCharsets.UTF_8);

        while (true) {
            System.out.println();
            System.out.println("Caesar Cipher Laboratory. Choose a task:");
            System.out.println("1. Task 1.1 - Caesar Cipher");
            System.out.println("2. Task 1.2 - Caesar Cipher with permutation");
            System.out.println("0. Exit");
            System.out.print("Choose an option: ");

            String choice = scanner.nextLine();

            switch (choice) {
                case "1":
                    runTask11(scanner);
                    break;

                case "2":
                    runTask12(scanner);
                    break;

                case "0":
                    System.out.println("Program finished.");
                    scanner.close();
                    return;

                default:
                    System.out.println(
                            "Invalid option. Enter 0, 1 or 2."
                    );
            }
        }
    }

    private static void runTask11(Scanner scanner) {
        System.out.println();
        System.out.println("Task 1.1 - Caesar Cipher");

        boolean encrypt = readOperation(scanner);
        int key = readKey(scanner);
        String text = readText(scanner);

        if (encrypt) {
            String ciphertext = encrypt(text, key, ALPHABET);

            System.out.println();
            System.out.println("Plaintext:  " + text);
            System.out.println("Key:        " + key);
            System.out.println("Ciphertext: " + ciphertext);
        } else {
            String plaintext = decrypt(text, key, ALPHABET);

            System.out.println();
            System.out.println("Ciphertext: " + text);
            System.out.println("Key:        " + key);
            System.out.println("Plaintext:  " + plaintext);
        }
    }

    private static void runTask12(Scanner scanner) {
        System.out.println();
        System.out.println(
                "Task 1.2 - Caesar Cipher with Permutation"
        );

        boolean encrypt = readOperation(scanner);
        int key = readKey(scanner);
        String keyword = readKeyword(scanner);

        String permutedAlphabet = buildPermutedAlphabet(keyword);

        System.out.println();
        System.out.println("Original alphabet:");
        System.out.println(ALPHABET);

        System.out.println("Permuted alphabet:");
        System.out.println(permutedAlphabet);

        System.out.println();
        System.out.println("Selected operation: "
                + (encrypt ? "Encryption" : "Decryption"));
        System.out.println("Key 1: " + key);
        System.out.println("Key 2: " + keyword);
    }

    private static boolean readOperation(Scanner scanner) {
        while (true) {
            System.out.println();
            System.out.println("Choose operation:");
            System.out.println("1. Encryption");
            System.out.println("2. Decryption");
            System.out.print("Option: ");

            String operation = scanner.nextLine();

            if (operation.equals("1")) {
                return true;
            }

            if (operation.equals("2")) {
                return false;
            }

            System.out.println(
                    "Invalid operation. Enter 1 or 2."
            );
        }
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

    private static String readKeyword(Scanner scanner) {
        while (true) {
            System.out.print(
                    "Enter keyword (minimum 7 Romanian letters): "
            );

            String keyword = scanner.nextLine();

            keyword = normalizeRomanian(keyword);
            keyword = keyword.toUpperCase(Locale.ROOT);

            if (keyword.length() < 7) {
                System.out.println(
                        "Invalid keyword. It must contain at least 7 characters."
                );
                continue;
            }

            Character invalid = findInvalidCharacter(keyword);

            if (invalid != null) {
                System.out.println(
                        "Invalid character in keyword: '" + invalid + "'."
                );
                System.out.println(
                        "The keyword may contain only Romanian alphabet letters."
                );
                continue;
            }

            return keyword;
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

    private static String encrypt(
            String text,
            int key,
            String alphabet
    ) {
        StringBuilder result = new StringBuilder();

        for (char ch : text.toCharArray()) {
            int position = alphabet.indexOf(ch);

            int newPosition = (position + key) % N;

            result.append(alphabet.charAt(newPosition));
        }

        return result.toString();
    }

    private static String decrypt(
            String text,
            int key,
            String alphabet
    ) {
        StringBuilder result = new StringBuilder();

        for (char ch : text.toCharArray()) {
            int position = alphabet.indexOf(ch);

            int newPosition = (position - key) % N;

            if (newPosition < 0) {
                newPosition += N;
            }

            result.append(alphabet.charAt(newPosition));
        }

        return result.toString();
    }

    private static String buildPermutedAlphabet(String keyword) {
        StringBuilder result = new StringBuilder();

        for (char ch : keyword.toCharArray()) {
            if (result.indexOf(String.valueOf(ch)) == -1) {
                result.append(ch);
            }
        }

        for (char ch : ALPHABET.toCharArray()) {
            if (result.indexOf(String.valueOf(ch)) == -1) {
                result.append(ch);
            }
        }

        return result.toString();
    }
}