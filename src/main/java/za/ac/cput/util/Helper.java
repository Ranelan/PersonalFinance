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
        return currentAmount >= 0;
    }
    public static boolean isValidDeadline(LocalDate deadline) {
        return deadline != null && deadline.isAfter(LocalDate.now());
    }




}
