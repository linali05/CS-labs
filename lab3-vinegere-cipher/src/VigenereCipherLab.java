import java.util.Locale;
import java.util.Scanner;
import javax.swing.*;
import java.awt.*;
import java.lang.reflect.InvocationTargetException;
import java.util.concurrent.atomic.AtomicReference;

public class VigenereCipherLab {
    // Romanian alphabet with 31 letters, in the required order.
    private static final String ALPHABET = "AĂÂBCDEFGHIÎJKLMNOPQRSȘTȚUVWXYZ";
    private static final int ALPHABET_SIZE = ALPHABET.length();
    private static final int MIN_KEY_LENGTH = 7;

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in, "UTF-8");

        while (true) {
            System.out.println("\nVigenere cipher (Romanian alphabet, 31 letters)");
            System.out.println("1. Encryption");
            System.out.println("2. Decryption");
            System.out.println("0. Exit");
            System.out.print("Choose an option: ");
            String option = scanner.nextLine().trim();

            if (option.equals("0")) {
                System.out.println("Goodbye!");
                break;
            }
            if (!option.equals("1") && !option.equals("2")) {
                System.out.println("Invalid option. Choose 0, 1, or 2.");
                continue;
            }

            System.out.print("Enter key (at least 7 letters): ");
            String key = normalize(scanner.nextLine());
            if (key.length() < MIN_KEY_LENGTH || !isValid(key)) {
                System.out.println("Invalid key. Use at least 7 Romanian alphabet letters (A-Z, Ă, Â, Î, Ș, Ț).");
                continue;
            }

            System.out.print(option.equals("1") ? "Enter plaintext: " : "Enter ciphertext: ");
            String message = normalize(scanner.nextLine());
            if (message.isEmpty() || !isValid(message)) {
                System.out.println("Invalid text. Use only Romanian alphabet letters (A-Z, Ă, Â, Î, Ș, Ț); spaces are allowed.");
                continue;
            }

            String result = transform(message, key, option.equals("1"));
            System.out.println(option.equals("1") ? "Ciphertext: " + result : "Decrypted text: " + result);
            if (option.equals("2")) {
                System.out.print("Do you want to add spaces manually? (y/n): ");
                String answer = scanner.nextLine().trim().toLowerCase(Locale.ROOT);
                while (!answer.equals("y") && !answer.equals("n")) {
                    System.out.print("Please enter y or n: ");
                    answer = scanner.nextLine().trim().toLowerCase(Locale.ROOT);
                }
                if (answer.equals("y")) {
                    restoreSpaces(result);
                } else {
                    System.out.println("Space editing skipped.");
                }
            }
        }
        scanner.close();
    }

    private static void restoreSpaces(String plaintext) {
        if (GraphicsEnvironment.isHeadless()) {
            System.out.println("A graphical editor is unavailable in this environment.");
            return;
        }

        AtomicReference<String> finalText = new AtomicReference<>(null);
        try {
            // Build and display the editor on Swing's event dispatch thread.
            SwingUtilities.invokeAndWait(() -> {
                JDialog dialog = new JDialog((Frame) null, "Restore spaces manually", true);
                dialog.setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);
                dialog.setAlwaysOnTop(true);

                JTextArea editor = new JTextArea(plaintext, 5, 42);
                editor.setLineWrap(true);
                editor.setWrapStyleWord(true);
                editor.setFont(new Font(Font.MONOSPACED, Font.PLAIN, 16));

                JLabel instruction = new JLabel("Insert spaces into the existing text, then click Save.");
                JLabel error = new JLabel(" ");
                error.setForeground(Color.RED);
                JButton save = new JButton("Save");
                JButton cancel = new JButton("Cancel");

                save.addActionListener(event -> {
                    String edited = editor.getText();
                    if (!edited.replace(" ", "").equals(plaintext)) {
                        error.setText("Only spaces may be changed; keep every letter unchanged.");
                        return;
                    }
                    finalText.set(edited);
                    dialog.dispose();
                });
                cancel.addActionListener(event -> dialog.dispose());

                JPanel buttons = new JPanel(new FlowLayout(FlowLayout.RIGHT));
                buttons.add(cancel);
                buttons.add(save);
                JPanel content = new JPanel(new BorderLayout(8, 8));
                content.setBorder(BorderFactory.createEmptyBorder(12, 12, 12, 12));
                content.add(instruction, BorderLayout.NORTH);
                content.add(new JScrollPane(editor), BorderLayout.CENTER);
                JPanel bottom = new JPanel(new BorderLayout());
                bottom.add(error, BorderLayout.CENTER);
                bottom.add(buttons, BorderLayout.SOUTH);
                content.add(bottom, BorderLayout.SOUTH);
                dialog.setContentPane(content);
                dialog.pack();
                dialog.setLocationRelativeTo(null);
                dialog.addWindowListener(new java.awt.event.WindowAdapter() {
                    @Override public void windowOpened(java.awt.event.WindowEvent event) {
                        dialog.toFront();
                        editor.requestFocusInWindow();
                    }
                });
                dialog.setVisible(true);
            });

            if (finalText.get() == null) {
                System.out.println("Space editing canceled.");
            } else {
                System.out.println("Readable text: " + finalText.get());
            }
        } catch (InterruptedException ex) {
            Thread.currentThread().interrupt();
            System.out.println("Space editing interrupted.");
        } catch (InvocationTargetException | HeadlessException ex) {
            System.out.println("Could not open the editor: " + ex.getMessage());
        }
    }

    private static String normalize(String input) {
        return input.toUpperCase(Locale.ROOT)
                .replace('Ş', 'Ș')
                .replace('Ţ', 'Ț')
                .replace(" ", "");
    }

    private static boolean isValid(String input) {
        for (int i = 0; i < input.length(); i++) {
            if (ALPHABET.indexOf(input.charAt(i)) < 0) {
                return false;
            }
        }
        return true;
    }

    private static String transform(String message, String key, boolean encrypt) {
        StringBuilder result = new StringBuilder();
        for (int i = 0; i < message.length(); i++) {
            int m = ALPHABET.indexOf(message.charAt(i));
            int k = ALPHABET.indexOf(key.charAt(i % key.length()));
            int value = encrypt
                    ? (m + k) % ALPHABET_SIZE
                    : (m - k + ALPHABET_SIZE) % ALPHABET_SIZE;
            result.append(ALPHABET.charAt(value));
        }
        return result.toString();
    }
}
