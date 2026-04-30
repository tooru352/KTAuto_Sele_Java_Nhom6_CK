package pageobjects;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

/**
 * Base Page Object class
 * Chứa các methods dùng chung cho tất cả page objects
 */
public class GeneralPage {
    
    protected WebDriver driver;
    protected WebDriverWait wait;
    
    public GeneralPage(WebDriver driver, WebDriverWait wait) {
        this.driver = driver;
        this.wait = wait;
    }
    
    // Method để tạo dynamic locator cho menu tab
    protected By getDynamicMenuTab(String tabName) {
        return By.xpath("//a[contains(text(), '" + tabName + "') or contains(@title, '" + tabName + "')]");
    }
    
    // Method để tạo dynamic locator cho button
    protected By getDynamicButton(String buttonText) {
        return By.xpath("//button[contains(text(), '" + buttonText + "') or contains(@value, '" + buttonText + "')]");
    }
    
    // Method để tạo dynamic locator cho input
    protected By getDynamicInput(String inputName) {
        return By.xpath("//input[@name='" + inputName + "' or @id='" + inputName + "' or contains(@placeholder, '" + inputName + "')]");
    }
    
    // Method để wait và click element
    protected void clickElement(By locator) {
        wait.until(ExpectedConditions.elementToBeClickable(locator)).click();
    }
    
    // Method để wait và nhập text
    protected void enterText(By locator, String text) {
        WebElement element = wait.until(ExpectedConditions.elementToBeClickable(locator));
        element.clear();
        element.sendKeys(text);
    }
    
    // Method để kiểm tra element có hiển thị không
    protected boolean isElementDisplayed(By locator) {
        try {
            return wait.until(ExpectedConditions.visibilityOfElementLocated(locator)).isDisplayed();
        } catch (Exception e) {
            return false;
        }
    }
    
    // Method để lấy text của element
    protected String getElementText(By locator) {
        return wait.until(ExpectedConditions.visibilityOfElementLocated(locator)).getText();
    }
    
    // Method để lấy attribute của element
    protected String getElementAttribute(By locator, String attribute) {
        return wait.until(ExpectedConditions.visibilityOfElementLocated(locator)).getAttribute(attribute);
    }
    
    // Method để kiểm tra element có enabled không
    protected boolean isElementEnabled(By locator) {
        try {
            return driver.findElement(locator).isEnabled();
        } catch (Exception e) {
            return false;
        }
    }
    
    // Method để sleep
    protected void sleep(long milliseconds) {
        try {
            Thread.sleep(milliseconds);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}