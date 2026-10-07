import java.util.Scanner;

public class CaesarCipherLab {

    private static final String ALPHABET =
            "AĂÂBCDEFGHIÎJKLMNOPQRSȘTȚUVWXYZ";

    private static final int N = 31;

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);

        System.out.println("Caesar Cipher - Laboratory 1");

        int key = readKey(scanner);

        System.out.println("Selected key: " + key);

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
}