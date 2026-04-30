package common;

import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

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
     * Click element bằng JavaScript (dùng khi element bị che hoặc không clickable thông thường)
     */
    public static void clickByJavaScript(WebDriver driver, WebElement element) {
        ((JavascriptExecutor) driver).executeScript("arguments[0].click();", element);
    }

    /**
     * Generate UUID ngẫu nhiên
     */
    public static String generateUUID() {
        return UUID.randomUUID().toString();
    }
}
