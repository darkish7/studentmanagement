package studentmanagement;

import java.util.NoSuchElementException;
import java.util.Scanner;

/**
 * Validates inputs for registration numbers, names, departments, semesters, and marks.
 */
public class InputValidator {

    private InputValidator() {
        // Private constructor to prevent instantiation
    }

    public static boolean isValidMarks(double marks) {
        return marks >= 0.0 && marks <= 100.0;
    }

    public static boolean isValidSemester(int semester) {
        return semester >= 1 && semester <= 8;
    }

    public static boolean isValidText(String text) {
        return text != null && !text.trim().isEmpty();
    }

    public static boolean isValidRegNo(String regNo) {
        return regNo != null && regNo.trim().matches("[A-Za-z0-9_-]{2,20}");
    }

    private static String safeReadLine(Scanner scanner) {
        if (scanner == null) {
            return null;
        }
        try {
            if (scanner.hasNextLine()) {
                return scanner.nextLine();
            }
        } catch (NoSuchElementException | IllegalStateException ignored) {
            // Scanner stream closed or exhausted
        }
        return null;
    }

    public static String readRegNo(Scanner scanner, String prompt) {
        while (true) {
            System.out.print(prompt);
            String input = safeReadLine(scanner);
            if (input == null) {
                System.out.println("\n[EOF received. Exiting input]");
                return "";
            }
            input = input.trim();
            if (isValidRegNo(input)) {
                return input;
            }
            System.out.println("(!) Invalid Registration Number. Please use 2-20 alphanumeric characters (e.g. REG101, CS01).");
        }
    }

    public static String readText(Scanner scanner, String prompt) {
        while (true) {
            System.out.print(prompt);
            String input = safeReadLine(scanner);
            if (input == null) {
                System.out.println("\n[EOF received. Exiting input]");
                return "";
            }
            input = input.trim();
            if (isValidText(input)) {
                return input;
            }
            System.out.println("(!) Field cannot be empty. Please enter a valid value.");
        }
    }

    public static int readSemester(Scanner scanner, String prompt) {
        while (true) {
            System.out.print(prompt);
            String input = safeReadLine(scanner);
            if (input == null) {
                return 1;
            }
            input = input.trim();
            try {
                int sem = Integer.parseInt(input);
                if (isValidSemester(sem)) {
                    return sem;
                }
                System.out.println("(!) Semester must be between 1 and 8.");
            } catch (NumberFormatException e) {
                System.out.println("(!) Invalid number. Please enter an integer between 1 and 8.");
            }
        }
    }

    public static double readMarks(Scanner scanner, String prompt) {
        while (true) {
            System.out.print(prompt);
            String input = safeReadLine(scanner);
            if (input == null) {
                return 0.0;
            }
            input = input.trim();
            try {
                double mark = Double.parseDouble(input);
                if (isValidMarks(mark)) {
                    return mark;
                }
                System.out.println("(!) Marks must be between 0.0 and 100.0.");
            } catch (NumberFormatException e) {
                System.out.println("(!) Invalid numeric value. Please enter a valid score (0-100).");
            }
        }
    }

    public static int readChoice(Scanner scanner, String prompt, int min, int max) {
        while (true) {
            System.out.print(prompt);
            String input = safeReadLine(scanner);
            if (input == null) {
                return 0; // Return exit option on stream close/EOF
            }
            input = input.trim();
            try {
                int val = Integer.parseInt(input);
                if (val >= min && val <= max) {
                    return val;
                }
                System.out.printf("(!) Please enter a choice between %d and %d.%n", min, max);
            } catch (NumberFormatException e) {
                System.out.println("(!) Invalid number. Please enter an integer.");
            }
        }
    }

    public static String readOptionalString(Scanner scanner, String prompt) {
        System.out.print(prompt);
        String input = safeReadLine(scanner);
        return input != null ? input.trim() : "";
    }
}
