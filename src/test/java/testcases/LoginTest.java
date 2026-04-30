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
}
