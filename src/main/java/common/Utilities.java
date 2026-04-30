package common;

import java.util.UUID;

/**
 * Utility class chứa các helper methods
 */
public class Utilities {
    
    /**
     * Log một message ra console
     */
    public static void log(String message) {
        System.out.println("[LOG] " + message);
    }
    
    /**
     * Log một test step
     */
    public static void logStep(String step) {
        System.out.println("\n" + step);
    }
    
    /**
     * Sleep trong một khoảng thời gian
     */
    public static void sleep(long milliseconds) {
        try {
            Thread.sleep(milliseconds);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
    
    /**
     * Generate UUID ngẫu nhiên
     */
    public static String generateUUID() {
        return UUID.randomUUID().toString();
    }
}
