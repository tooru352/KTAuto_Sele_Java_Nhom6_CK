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
    private By rememberMeCheckbox = By.xpath("//input[@type='checkbox']");
    private By forgotPasswordLink = By.xpath("//a[contains(text(),'Quên mật khẩu')]");
    private By signUpLink = By.xpath("//a[contains(text(),'Đăng ký')]");
    private By passwordVisibilityToggle = By.xpath("//button[@class='password-toggle']");
    private By form = By.xpath("//form[@class='login-form']");

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

    public void checkRememberMeCheckbox() {
        WebElement checkbox = wait.until(ExpectedConditions.visibilityOfElementLocated(rememberMeCheckbox));
        if (!checkbox.isSelected()) {
            checkbox.click();
        }
    }

    public boolean isRememberMeChecked() {
        try {
            return driver.findElement(rememberMeCheckbox).isSelected();
        } catch (Exception e) {
            return false;
        }
    }

    public boolean isForgotPasswordLinkDisplayed() {
        try {
            return wait.until(ExpectedConditions.visibilityOfElementLocated(forgotPasswordLink)).isDisplayed();
        } catch (Exception e) {
            return false;
        }
    }

    public void clickForgotPasswordLink() {
        wait.until(ExpectedConditions.elementToBeClickable(forgotPasswordLink)).click();
    }

    public boolean isOnForgotPasswordPage() {
        return getCurrentUrl().contains("/forgot-password/");
    }

    public boolean isSignUpLinkDisplayed() {
        try {
            return wait.until(ExpectedConditions.visibilityOfElementLocated(signUpLink)).isDisplayed();
        } catch (Exception e) {
            return false;
        }
    }

    public void clickSignUpLink() {
        wait.until(ExpectedConditions.elementToBeClickable(signUpLink)).click();
    }

    public boolean isOnSignUpPage() {
        return getCurrentUrl().contains("/signup/");
    }

    public void clickPasswordVisibilityToggle() {
        wait.until(ExpectedConditions.elementToBeClickable(passwordVisibilityToggle)).click();
    }

    public boolean isPasswordFieldMasked() {
        try {
            WebElement passwordInput = driver.findElement(passwordField);
            return passwordInput.getAttribute("type").equals("password");
        } catch (Exception e) {
            return false;
        }
    }

    public boolean isPasswordFieldVisible() {
        try {
            WebElement passwordInput = driver.findElement(passwordField);
            return passwordInput.getAttribute("type").equals("text");
        } catch (Exception e) {
            return false;
        }
    }

    public void clickUsernameField() {
        wait.until(ExpectedConditions.elementToBeClickable(usernameField)).click();
    }

    public void clickPasswordField() {
        wait.until(ExpectedConditions.elementToBeClickable(passwordField)).click();
    }

    public boolean isUsernameFieldFocused() {
        try {
            WebElement element = driver.findElement(usernameField);
            return driver.switchTo().activeElement().equals(element);
        } catch (Exception e) {
            return false;
        }
    }

    public boolean isPasswordFieldFocused() {
        try {
            WebElement element = driver.findElement(passwordField);
            return driver.switchTo().activeElement().equals(element);
        } catch (Exception e) {
            return false;
        }
    }

    public void pressTabKey() {
        driver.switchTo().activeElement().sendKeys(org.openqa.selenium.Keys.TAB);
    }

    public void pressEnterKey() {
        driver.switchTo().activeElement().sendKeys(org.openqa.selenium.Keys.ENTER);
    }

    public boolean isLoginButtonFocused() {
        try {
            WebElement element = driver.findElement(loginButton);
            return driver.switchTo().activeElement().equals(element);
        } catch (Exception e) {
            return false;
        }
    }

    public String getUsernamePlaceholder() {
        try {
            return driver.findElement(usernameField).getAttribute("placeholder");
        } catch (Exception e) {
            return "";
        }
    }

    public String getPasswordPlaceholder() {
        try {
            return driver.findElement(passwordField).getAttribute("placeholder");
        } catch (Exception e) {
            return "";
        }
    }

    public String getLoginButtonText() {
        try {
            return driver.findElement(loginButton).getText();
        } catch (Exception e) {
            return "";
        }
    }

    public boolean isFormCentered() {
        try {
            WebElement formElement = driver.findElement(form);
            int formX = formElement.getLocation().getX();
            int windowWidth = driver.manage().window().getSize().getWidth();
            int formWidth = formElement.getSize().getWidth();
            int expectedX = (windowWidth - formWidth) / 2;
            return Math.abs(formX - expectedX) < 50; // Allow 50px tolerance
        } catch (Exception e) {
            return false;
        }
    }

    public boolean isFormDisplayedCorrectly() {
        try {
            WebElement formElement = driver.findElement(form);
            return formElement.isDisplayed() && formElement.getSize().getHeight() > 0;
        } catch (Exception e) {
            return false;
        }
    }

    public boolean isErrorMessageRed() {
        try {
            WebElement errorMsg = driver.findElement(errorMessage);
            String color = errorMsg.getCssValue("color");
            // Check if color contains red (rgb(255, 0, 0) or similar)
            return color.contains("255") && color.contains("0") && color.contains("0");
        } catch (Exception e) {
            return false;
        }
    }

    public boolean isErrorMessageVisible() {
        try {
            WebElement errorMsg = driver.findElement(errorMessage);
            int fontSize = Integer.parseInt(errorMsg.getCssValue("font-size").replaceAll("[^0-9]", ""));
            return fontSize >= 12; // Minimum readable font size
        } catch (Exception e) {
            return false;
        }
    }

    public boolean isSuccessMessageGreen() {
        try {
            WebElement successMsg = driver.findElement(successMessage);
            String color = successMsg.getCssValue("color");
            // Check if color contains green
            return color.contains("0") && color.contains("128") || color.contains("0") && color.contains("255");
        } catch (Exception e) {
            return false;
        }
    }

    public boolean isNCCFeatureDisplayed() {
        try {
            return wait.until(ExpectedConditions.visibilityOfElementLocated(
                By.xpath("//*[contains(text(),'NCC')]"))).isDisplayed();
        } catch (Exception e) {
            return false;
        }
    }

    public boolean isHDN01FeatureDisplayed() {
        try {
            return wait.until(ExpectedConditions.visibilityOfElementLocated(
                By.xpath("//*[contains(text(),'HDN01')]"))).isDisplayed();
        } catch (Exception e) {
            return false;
        }
    }

    public boolean isInputFeatureDisplayed() {
        try {
            return wait.until(ExpectedConditions.visibilityOfElementLocated(
                By.xpath("//*[contains(text(),'Nhập')]"))).isDisplayed();
        } catch (Exception e) {
            return false;
        }
    }

    public boolean isHistoryDataDisplayed() {
        try {
            return wait.until(ExpectedConditions.visibilityOfElementLocated(
                By.xpath("//*[contains(text(),'lịch sử')]"))).isDisplayed();
        } catch (Exception e) {
            return false;
        }
    }

    public boolean isEditRealTimeFeatureDisplayed() {
        try {
            return wait.until(ExpectedConditions.visibilityOfElementLocated(
                By.xpath("//*[contains(text(),'Edit')]"))).isDisplayed();
        } catch (Exception e) {
            return false;
        }
    }

    public boolean isInputDateFeatureDisplayed() {
        try {
            return wait.until(ExpectedConditions.visibilityOfElementLocated(
                By.xpath("//input[@type='date']"))).isDisplayed();
        } catch (Exception e) {
            return false;
        }
    }

    public boolean isInputStatusFeatureDisplayed() {
        try {
            return wait.until(ExpectedConditions.visibilityOfElementLocated(
                By.xpath("//*[contains(text(),'trạng thái')]"))).isDisplayed();
        } catch (Exception e) {
            return false;
        }
    }

    public boolean isWriteErrorDisplayed() {
        try {
            return wait.until(ExpectedConditions.visibilityOfElementLocated(
                By.xpath("//*[contains(text(),'lỗi ghi')]"))).isDisplayed();
        } catch (Exception e) {
            return false;
        }
    }

    public boolean isInputFromBeginningFeatureDisplayed() {
        try {
            return wait.until(ExpectedConditions.visibilityOfElementLocated(
                By.xpath("//*[contains(text(),'đầu hàng')]"))).isDisplayed();
        } catch (Exception e) {
            return false;
        }
    }
}
