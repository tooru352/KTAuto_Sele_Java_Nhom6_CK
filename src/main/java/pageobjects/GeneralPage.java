package pageobjects;

import common.Constant;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;

public class GeneralPage {
    
    protected WebDriverWait wait;
    
    public GeneralPage() {
        this.wait = new WebDriverWait(Constant.WEBDRIVER, Duration.ofSeconds(Constant.DEFAULT_TIMEOUT));
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
        wait.until(ExpectedConditions.elementToBeClickable(locator));
        Constant.WEBDRIVER.findElement(locator).click();
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
}