package pageobjects;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import common.Constant;
import java.time.Duration;

public class LoginPage {
    private WebDriver driver;
    private WebDriverWait wait;

    private By usernameField = By.xpath("//input[@name='username']");
    private By passwordField = By.xpath("//input[@name='password']");
    private By loginButton = By.xpath("//button[contains(text(),'Đăng nhập')]");
    private By successMessage = By.xpath("//*[contains(text(),'Đăng nhập thành công')]");
    private By errorMessage = By.xpath("//*[contains(text(),'Mật khẩu hoặc tài khoản sai')]");
    private By userDisplayName = By.xpath("//*[contains(text(),'ADMIN')]");
    private By validationMessage = By.xpath("//*[contains(text(),'Please fill out this field')]");

    // XPath để kiểm tra đăng nhập thành công
    private final By homePageHeader = By.xpath("//header[@class='top-header']//h1[text()='Trang chủ']");
    private final By userInfo = By.xpath("//a[@class='user-info']//span[@class='user-name'][text()='admin']");
    private final By homePageTitle = By.xpath("//h1[text()='Trang chủ ']");

    public LoginPage(WebDriver driver) {
        this.driver = driver;
        this.wait = new WebDriverWait(driver, Duration.ofSeconds(Constant.EXPLICIT_WAIT));
    }

    public void open() {
        driver.get(Constant.LOGIN_URL);
    }

    public void enterUsername(String username) {
        WebElement element = wait.until(ExpectedConditions.visibilityOfElementLocated(usernameField));
        element.clear();
        element.sendKeys(username);
    }

    public void enterPassword(String password) {
        WebElement element = wait.until(ExpectedConditions.visibilityOfElementLocated(passwordField));
        element.clear();
        element.sendKeys(password);
    }

    public void clickLoginButton() {
        wait.until(ExpectedConditions.elementToBeClickable(loginButton)).click();
    }

    public void clickLoginButtonMultipleTimes(int times) {
        for (int i = 0; i < times; i++) {
            try {
                WebElement button = driver.findElement(loginButton);
                if (button.isDisplayed() && button.isEnabled()) {
                    button.click();
                    // Chờ một chút giữa các lần click
                    Thread.sleep(100);
                } else {
                    break;
                }
            } catch (Exception e) {
                // Nếu không tìm thấy button hoặc trang đã chuyển, dừng lại
                break;
            }
        }
    }

    public void login(String username, String password) {
        enterUsername(username);
        enterPassword(password);
        clickLoginButton();
    }

    public boolean isSuccessMessageDisplayed() {
        try {
            return wait.until(ExpectedConditions.visibilityOfElementLocated(successMessage)).isDisplayed();
        } catch (Exception e) {
            return false;
        }
    }

    public boolean isErrorMessageDisplayed() {
        try {
            return wait.until(ExpectedConditions.visibilityOfElementLocated(errorMessage)).isDisplayed();
        } catch (Exception e) {
            return false;
        }
    }

    public boolean isUserDisplayNameVisible() {
        try {
            return wait.until(ExpectedConditions.visibilityOfElementLocated(userDisplayName)).isDisplayed();
        } catch (Exception e) {
            return false;
        }
    }

    public boolean isValidationMessageDisplayed() {
        try {
            // Kiểm tra username field trước
            WebElement usernameInput = driver.findElement(usernameField);
            String usernameValidation = usernameInput.getAttribute("validationMessage");
            if (usernameValidation != null && !usernameValidation.isEmpty()) {
                return true;
            }
            
            // Nếu username đã có giá trị, kiểm tra password field
            WebElement passwordInput = driver.findElement(passwordField);
            String passwordValidation = passwordInput.getAttribute("validationMessage");
            return passwordValidation != null && !passwordValidation.isEmpty();
        } catch (Exception e) {
            return false;
        }
    }
    
    public boolean isUsernameRequired() {
        try {
            WebElement usernameInput = driver.findElement(usernameField);
            return "true".equals(usernameInput.getAttribute("required")) || 
                   usernameInput.getAttribute("required") != null;
        } catch (Exception e) {
            return false;
        }
    }
    
    public boolean isPasswordRequired() {
        try {
            WebElement passwordInput = driver.findElement(passwordField);
            return "true".equals(passwordInput.getAttribute("required")) || 
                   passwordInput.getAttribute("required") != null;
        } catch (Exception e) {
            return false;
        }
    }

    public String getCurrentUrl() {
        return driver.getCurrentUrl();
    }

    public String getPageTitle() {
        return driver.getTitle();
    }

    public boolean isOnLoginPage() {
        return getCurrentUrl().contains("/login/");
    }

    public boolean isOnHomePage() {
        return !getCurrentUrl().contains("/login/");
    }
}
