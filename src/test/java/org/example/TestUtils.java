package org.example;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;

/**
 * Utility class for common test operations
 */
public class TestUtils {
    
    private WebDriver driver;
    private WebDriverWait wait;
    
    public TestUtils(WebDriver driver, WebDriverWait wait) {
        this.driver = driver;
        this.wait = wait;
    }
    
    /**
     * Wait for element to be visible and return it
     */
    public WebElement waitForElement(By locator) {
        return wait.until(ExpectedConditions.visibilityOfElementLocated(locator));
    }
    
    /**
     * Wait for element to be clickable and click it
     */
    public void waitAndClick(By locator) {
        wait.until(ExpectedConditions.elementToBeClickable(locator)).click();
    }
    
    /**
     * Clear field and enter text
     */
    public void clearAndType(By locator, String text) {
        WebElement element = waitForElement(locator);
        element.clear();
        element.sendKeys(text);
    }
    
    /**
     * Get text from element
     */
    public String getText(By locator) {
        return waitForElement(locator).getText();
    }
    
    /**
     * Check if element is read-only or disabled
     */
    public boolean isReadOnly(By locator) {
        WebElement element = driver.findElement(locator);
        String readonly = element.getAttribute("readonly");
        String disabled = element.getAttribute("disabled");
        return readonly != null || disabled != null;
    }
    
    /**
     * Wait for element to disappear
     */
    public void waitForElementToDisappear(By locator) {
        wait.until(ExpectedConditions.invisibilityOfElementLocated(locator));
    }
    
    /**
     * Get attribute value
     */
    public String getAttribute(By locator, String attributeName) {
        return driver.findElement(locator).getAttribute(attributeName);
    }
    
    /**
     * Wait for URL to contain text
     */
    public void waitForUrlContains(String urlPart) {
        wait.until(ExpectedConditions.urlContains(urlPart));
    }
    
    /**
     * Take screenshot (requires additional setup)
     */
    public void takeScreenshot(String filename) {
        // Implementation would go here
        System.out.println("Screenshot saved: " + filename);
    }
}
