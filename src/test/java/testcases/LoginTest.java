package testcases;

import org.testng.Assert;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;
import pageobjects.LoginPage;
import dataobjects.LoginData;

public class LoginTest extends BaseTest {
    private LoginPage loginPage;

    @BeforeMethod
    public void setUpTest() {
        loginPage = new LoginPage(driver);
        loginPage.open();
    }

    @Test(description = "TC-01: Login thành công với thông tin hợp lệ")
    public void TC01() {
        loginPage.login(LoginData.VALID_USERNAME, LoginData.VALID_PASSWORD);
        
        Assert.assertTrue(loginPage.isOnHomePage(), 
            "Chuyển đến trang chủ sau khi đăng nhập thành công");
    }

    @Test(description = "TC-02: Đăng nhập không thành công vì sai username")
    public void TC02() {
        loginPage.login(LoginData.INVALID_USERNAME, LoginData.VALID_PASSWORD);
        
        Assert.assertTrue(loginPage.isErrorMessageDisplayed(),
            "Hiển thị thông báo 'Mật khẩu hoặc tài khoản sai vui lòng nhập lại !'");
        Assert.assertTrue(loginPage.isOnLoginPage(),
            "Vẫn ở trang login");
    }

    @Test(description = "TC-03: Đăng nhập không thành công vì sai password")
    public void TC03() {
        loginPage.login(LoginData.VALID_USERNAME, LoginData.INVALID_PASSWORD);
        
        Assert.assertTrue(loginPage.isErrorMessageDisplayed(),
            "Hiển thị thông báo 'Mật khẩu hoặc tài khoản sai vui lòng nhập lại !'");
        Assert.assertTrue(loginPage.isOnLoginPage(),
            "Vẫn ở trang login");
    }

    @Test(description = "TC-04: Không nhập username")
    public void TC04() {
        loginPage.enterPassword(LoginData.VALID_PASSWORD);
        loginPage.clickLoginButton();
        
        Assert.assertTrue(loginPage.isOnLoginPage(),
            "Không submit form - vẫn ở trang login");
    }

    @Test(description = "TC-05: Không nhập password")
    public void TC05() {
        loginPage.enterUsername(LoginData.VALID_USERNAME);
        loginPage.clickLoginButton();
        
        Assert.assertTrue(loginPage.isOnLoginPage(),
            "Không submit form - vẫn ở trang login");
    }

    @Test(description = "TC-06: Không nhập cả username và password")
    public void TC06() {
        loginPage.clickLoginButton();
        
        Assert.assertTrue(loginPage.isOnLoginPage(),
            "Không submit form - vẫn ở trang login");
    }

    @Test(description = "TC-07: Nhập username có khoảng trắng đầu/cuối")
    public void TC07() {
        loginPage.login(LoginData.USERNAME_WITH_SPACES, LoginData.VALID_PASSWORD);
        
        Assert.assertTrue(loginPage.isErrorMessageDisplayed(),
            "Hiển thị lỗi 'Mật khẩu hoặc tài khoản sai vui lòng nhập lại !'");
        Assert.assertTrue(loginPage.isOnLoginPage(),
            "Vẫn ở trang login");
    }

    @Test(description = "TC-08: Nhập username viết hoa (case-sensitive)")
    public void TC08() {
        loginPage.login(LoginData.USERNAME_UPPERCASE, LoginData.VALID_PASSWORD);
        
        Assert.assertTrue(loginPage.isErrorMessageDisplayed(),
            "Hiển thị thông báo 'Mật khẩu hoặc tài khoản sai vui lòng nhập lại !' (username phân biệt hoa thường)");
        Assert.assertTrue(loginPage.isOnLoginPage(),
            "Vẫn ở trang login");
    }

    @Test(description = "TC-09: SQL Injection Attack")
    public void TC09() {
        loginPage.login(LoginData.SQL_INJECTION, "anything");
        
        Assert.assertTrue(loginPage.isErrorMessageDisplayed(),
            "Hiển thị thông báo 'Mật khẩu hoặc tài khoản sai vui lòng nhập lại !' - KHÔNG đăng nhập thành công");
        Assert.assertTrue(loginPage.isOnLoginPage(),
            "Hệ thống bảo mật tốt, không bị SQL Injection");
    }

    @Test(description = "TC-10: XSS Attack")
    public void TC10() {
        loginPage.login(LoginData.XSS_ATTACK, LoginData.VALID_PASSWORD);
        
        Assert.assertTrue(loginPage.isErrorMessageDisplayed(),
            "Hiển thị thông báo lỗi - KHÔNG hiển thị alert popup");
        Assert.assertTrue(loginPage.isOnLoginPage(),
            "Script được escape/sanitize");
    }

    @Test(description = "TC-11: Nhập password có ký tự đặc biệt")
    public void TC11() {
        loginPage.login(LoginData.VALID_USERNAME, LoginData.PASSWORD_SPECIAL_CHARS);
        
        Assert.assertTrue(loginPage.isErrorMessageDisplayed() || loginPage.isOnLoginPage(),
            "Hệ thống xử lý bình thường - password sai nên báo lỗi");
    }

    @Test(description = "TC-12: Nhập username quá dài (>50 ký tự)")
    public void TC12() {
        loginPage.login(LoginData.LONG_STRING_50, LoginData.VALID_PASSWORD);
        
        Assert.assertTrue(loginPage.isErrorMessageDisplayed(),
            "Hiển thị thông báo 'Tên đăng nhập hoặc mật khẩu không đúng' hoặc 'Password quá dài'");
        Assert.assertTrue(loginPage.isOnLoginPage(),
            "Vẫn ở trang login");
    }

    @Test(description = "TC-13: Nhập password quá dài (>50 ký tự)")
    public void TC13() {
        loginPage.login(LoginData.VALID_USERNAME, LoginData.LONG_STRING_50);
        
        Assert.assertTrue(loginPage.isErrorMessageDisplayed(),
            "Hiển thị thông báo 'Tên đăng nhập hoặc mật khẩu không đúng' hoặc 'Password quá dài'");
        Assert.assertTrue(loginPage.isOnLoginPage(),
            "Vẫn ở trang login");
    }

    @Test(description = "TC-14: Copy-Paste password")
    public void TC14() {
        loginPage.enterUsername(LoginData.VALID_USERNAME);
        loginPage.enterPassword(LoginData.VALID_PASSWORD);
        loginPage.clickLoginButton();
        
        Assert.assertTrue(loginPage.isOnHomePage(),
            "Hệ thống đăng nhập thành công - Password được paste thành công - Chuyển đến trang chủ");
    }

    @Test(description = "TC-16: Click nhiều lần vào nút Login")
    public void TC16() {
        loginPage.enterUsername(LoginData.VALID_USERNAME);
        loginPage.enterPassword(LoginData.VALID_PASSWORD);
        loginPage.clickLoginButtonMultipleTimes(5);
        
        Assert.assertTrue(loginPage.isOnHomePage(),
            "Hệ thống chỉ xử lý 1 lần đăng nhập - Không gửi multiple requests - Chuyển đến trang chủ");
    }

    @Test(description = "TC-17: Kiểm tra browser back button sau khi đăng nhập")
    public void TC17() {
        loginPage.login(LoginData.VALID_USERNAME, LoginData.VALID_PASSWORD);
        Assert.assertTrue(loginPage.isOnHomePage(), "Đăng nhập thành công, chuyển đến trang chủ");
        
        driver.navigate().back();
        
        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        
        String currentUrl = loginPage.getCurrentUrl();
        boolean isValid = loginPage.isOnHomePage() || (!currentUrl.contains("/login/"));
        
        Assert.assertTrue(isValid,
            "Sau khi back: Không quay lại trang login HOẶC tự động redirect về trang chủ. URL hiện tại: " + currentUrl);
    }

    @Test(description = "TC_18: Kiểm tra Remember Me checkbox")
    public void testRememberMeCheckbox() {
        loginPage.checkRememberMeCheckbox();
        loginPage.login(LoginData.VALID_USERNAME, LoginData.VALID_PASSWORD);
        
        Assert.assertTrue(loginPage.isOnHomePage(),
            "Đăng nhập thành công");
        
        driver.navigate().back();
        
        Assert.assertTrue(loginPage.isRememberMeChecked(),
            "Remember Me checkbox vẫn được check");
    }

    @Test(description = "TC_19: Kiểm tra Forgot Password link")
    public void testForgotPasswordLink() {
        Assert.assertTrue(loginPage.isForgotPasswordLinkDisplayed(),
            "Hiển thị link 'Quên mật khẩu'");
        
        loginPage.clickForgotPasswordLink();
        
        Assert.assertTrue(loginPage.isOnForgotPasswordPage(),
            "Chuyển đến trang quên mật khẩu");
    }

    @Test(description = "TC_20: Kiểm tra Sign Up link")
    public void testSignUpLink() {
        Assert.assertTrue(loginPage.isSignUpLinkDisplayed(),
            "Hiển thị link 'Đăng ký'");
        
        loginPage.clickSignUpLink();
        
        Assert.assertTrue(loginPage.isOnSignUpPage(),
            "Chuyển đến trang đăng ký");
    }

    @Test(description = "TC_21: Kiểm tra password visibility toggle")
    public void testPasswordVisibilityToggle() {
        loginPage.enterPassword(LoginData.VALID_PASSWORD);
        
        Assert.assertTrue(loginPage.isPasswordFieldMasked(),
            "Password field hiển thị dạng masked (****)");
        
        loginPage.clickPasswordVisibilityToggle();
        
        Assert.assertTrue(loginPage.isPasswordFieldVisible(),
            "Password field hiển thị dạng plain text");
    }

    @Test(description = "TC_22: Kiểm tra input field focus")
    public void testInputFieldFocus() {
        loginPage.clickUsernameField();
        
        Assert.assertTrue(loginPage.isUsernameFieldFocused(),
            "Username field được focus (có border highlight)");
        
        loginPage.clickPasswordField();
        
        Assert.assertTrue(loginPage.isPasswordFieldFocused(),
            "Password field được focus (có border highlight)");
    }

    @Test(description = "TC_23: Kiểm tra keyboard navigation (Tab key)")
    public void testKeyboardNavigation() {
        loginPage.clickUsernameField();
        loginPage.pressTabKey();
        
        Assert.assertTrue(loginPage.isPasswordFieldFocused(),
            "Focus chuyển sang password field");
        
        loginPage.pressTabKey();
        
        Assert.assertTrue(loginPage.isLoginButtonFocused(),
            "Focus chuyển sang login button");
    }

    @Test(description = "TC_24: Kiểm tra Enter key để submit form")
    public void testEnterKeySubmitForm() {
        loginPage.enterUsername(LoginData.VALID_USERNAME);
        loginPage.enterPassword(LoginData.VALID_PASSWORD);
        loginPage.pressEnterKey();
        
        Assert.assertTrue(loginPage.isOnHomePage(),
            "Form được submit khi nhấn Enter - Đăng nhập thành công");
    }

    @Test(description = "TC_25: Kiểm tra placeholder text")
    public void testPlaceholderText() {
        Assert.assertEquals(loginPage.getUsernamePlaceholder(), "Tên đăng nhập",
            "Username field có placeholder 'Tên đăng nhập'");
        
        Assert.assertEquals(loginPage.getPasswordPlaceholder(), "Mật khẩu",
            "Password field có placeholder 'Mật khẩu'");
    }

    @Test(description = "TC_26: Kiểm tra button text")
    public void testLoginButtonText() {
        Assert.assertEquals(loginPage.getLoginButtonText(), "Đăng nhập",
            "Login button hiển thị text 'Đăng nhập'");
    }

    @Test(description = "TC_27: Kiểm tra form layout responsive")
    public void testFormLayoutResponsive() {
        // Test trên desktop
        Assert.assertTrue(loginPage.isFormCentered(),
            "Form được căn giữa trên desktop");
        
        // Resize window
        driver.manage().window().setSize(new org.openqa.selenium.Dimension(375, 667));
        
        Assert.assertTrue(loginPage.isFormDisplayedCorrectly(),
            "Form vẫn hiển thị đúng trên mobile (375x667)");
    }

    @Test(description = "TC_28: Kiểm tra error message styling")
    public void testErrorMessageStyling() {
        loginPage.login(LoginData.INVALID_USERNAME, LoginData.VALID_PASSWORD);
        
        Assert.assertTrue(loginPage.isErrorMessageDisplayed(),
            "Error message được hiển thị");
        
        Assert.assertTrue(loginPage.isErrorMessageRed(),
            "Error message có màu đỏ");
        
        Assert.assertTrue(loginPage.isErrorMessageVisible(),
            "Error message có font size đủ lớn để đọc");
    }

    @Test(description = "TC_29: Kiểm tra success message styling")
    public void testSuccessMessageStyling() {
        loginPage.login(LoginData.VALID_USERNAME, LoginData.VALID_PASSWORD);
        
        Assert.assertTrue(loginPage.isSuccessMessageDisplayed(),
            "Success message được hiển thị");
        
        Assert.assertTrue(loginPage.isSuccessMessageGreen(),
            "Success message có màu xanh");
    }

    @Test(description = "TC_30: Kiểm tra auto-dismiss error message")
    public void testAutoDismissErrorMessage() throws InterruptedException {
        loginPage.login(LoginData.INVALID_USERNAME, LoginData.VALID_PASSWORD);
        
        Assert.assertTrue(loginPage.isErrorMessageDisplayed(),
            "Error message được hiển thị");
        
        Thread.sleep(5000); // Wait 5 seconds
        
        Assert.assertFalse(loginPage.isErrorMessageDisplayed(),
            "Error message tự động biến mất sau 5 giây");
    }
}
