package za.ac.cput.util;

import java.time.LocalDate;

public class Helper {

    public static boolean isNullOrEmpty(String str) {
        return str == null || str.isEmpty();
    }
    public static boolean isValidYear(String year) {
        return year.matches("\\d{4}");
    }
    public static boolean isValidMonth(String month) {
        return month != null && month.matches("(?i)^(January|February|March|April|May|June|July|August|September|October|November|December)$");
    }
    public static boolean isValidLimitAmount(double amount) {
        return amount >= 0;
    }

    public static boolean isValidTargetAmount(double targetAmount) {
        return targetAmount > 0;
    }
    public static boolean isValidCurrentAmount(double currentAmount) {
        return currentAmount > 0;
    }
    public static boolean isValidDeadline(LocalDate deadline) {
        return deadline != null && deadline.isAfter(LocalDate.now());
    }
    public static boolean isValidNextExecution(LocalDate nextExecution) {
        return nextExecution != null && !nextExecution.isBefore(LocalDate.now());
    }

    public static boolean isValidEmail(String email) {
        String emailRegex = "^[a-zA-Z0-9._%+-]+@[a-zA-Z0-9.-]+\\.[a-zA-Z]{2,}$";
        return email != null && email.matches(emailRegex);
    }

    // Password must be at least 8 characters long, contain at least one uppercase letter,
    public static boolean isValidPassword(String password) {
        String passwordRegex = "^(?=.*[a-z])(?=.*[A-Z])(?=.*\\d)(?=.*[@$!%*?&])[A-Za-z\\d@$!%*?&]{8,}$";
        return password != null && password.matches(passwordRegex);
    }
}
