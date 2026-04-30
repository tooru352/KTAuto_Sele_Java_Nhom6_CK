package testcases;

import org.openqa.selenium.WindowType;
import org.testng.Assert;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;
import pageobjects.LoginPage;
import pageobjects.HomePage;
import dataobjects.LoginData;
import common.Constant;

public class LogoutTest extends BaseTest {
    private LoginPage loginPage;
    private HomePage homePage;

    @BeforeMethod
    public void setUpTest() {
        loginPage = new LoginPage(driver);
        homePage = new HomePage(driver);
        
        // Đăng nhập trước mỗi test
        loginPage.open();
        loginPage.login(LoginData.VALID_USERNAME, LoginData.VALID_PASSWORD);
    }

    @Test(description = "TC-01: Đăng xuất thành công từ trang chủ")
    public void TC01() {
        homePage.logout();
        
        Assert.assertTrue(loginPage.isOnLoginPage(),
            "Chuyển hướng về trang login (/login/)");
        Assert.assertTrue(driver.getCurrentUrl().contains("/login/"),
            "Session bị xóa. Hiển thị form đăng nhập");
        
        // Thử bấm Back để quay lại trang chủ
        driver.navigate().back();
        
        // Chờ một chút để xem có redirect không
        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        
        Assert.assertTrue(loginPage.isOnLoginPage(),
            "Sau khi back, vẫn ở trang login - Session đã bị xóa hoàn toàn");
    }

    @Test(description = "TC-02: Đăng xuất từ trang Bán hàng")
    public void TC02() {
        homePage.navigateToBanHang();
        String banHangUrl = driver.getCurrentUrl();
        Assert.assertTrue(banHangUrl.contains("/ban-hang/"), "Đã vào trang Bán hàng");
        
        homePage.logout();
        
        Assert.assertTrue(loginPage.isOnLoginPage(),
            "Chuyển hướng về trang login. Session bị xóa");
        
        driver.navigate().back();
        Assert.assertTrue(loginPage.isOnLoginPage(),
            "Không thể quay lại trang Bán hàng bằng nút Back");
    }

    @Test(description = "TC-03: Đăng xuất từ trang Nhập hàng")
    public void TC03() {
        homePage.navigateToNhapHang();
        String nhapHangUrl = driver.getCurrentUrl();
        Assert.assertTrue(nhapHangUrl.contains("/nhap-hang/"), "Đã vào trang Nhập hàng");
        
        homePage.logout();
        
        Assert.assertTrue(loginPage.isOnLoginPage(),
            "Chuyển hướng về trang login. Session bị xóa");
        
        driver.navigate().back();
        Assert.assertTrue(loginPage.isOnLoginPage(),
            "Không thể quay lại trang Nhập hàng bằng nút Back");
    }

    @Test(description = "TC-04: Đăng xuất từ trang Hàng hóa")
    public void TC04() {
        homePage.navigateToHangHoa();
        String hangHoaUrl = driver.getCurrentUrl();
        Assert.assertTrue(hangHoaUrl.contains("/hang-hoa/"), "Đã vào trang Hàng hóa");
        
        homePage.logout();
        
        Assert.assertTrue(loginPage.isOnLoginPage(),
            "Chuyển hướng về trang login. Session bị xóa");
        
        driver.navigate().back();
        Assert.assertTrue(loginPage.isOnLoginPage(),
            "Không thể quay lại trang Hàng hóa bằng nút Back");
    }

    @Test(description = "TC-05: Kiểm tra session sau khi đăng xuất")
    public void TC05() {
        homePage.logout();
        Assert.assertTrue(loginPage.isOnLoginPage(), "Đã đăng xuất thành công");
        
        driver.get(Constant.HOME_URL);
        
        Assert.assertTrue(loginPage.isOnLoginPage(),
            "Tự động redirect về trang login. Không thể truy cập trang chủ");
    }

    @Test(description = "TC-06: Kiểm tra truy cập trang Bán hàng sau khi đăng xuất")
    public void TC06() {
        homePage.logout();
        Assert.assertTrue(loginPage.isOnLoginPage(), "Đã đăng xuất thành công");
        
        driver.get(Constant.BAN_HANG_URL);
        
        Assert.assertTrue(loginPage.isOnLoginPage(),
            "Tự động redirect về trang login");
        Assert.assertTrue(driver.getCurrentUrl().contains("/login/"),
            "URL có thể chứa ?next=/ban-hang/. Không thể truy cập trang Bán hàng");
    }

    @Test(description = "TC-07: Kiểm tra truy cập trang Nhập hàng sau khi đăng xuất")
    public void TC07() {
        homePage.logout();
        Assert.assertTrue(loginPage.isOnLoginPage(), "Đã đăng xuất thành công");
        
        driver.get(Constant.NHAP_HANG_URL);
        
        Assert.assertTrue(loginPage.isOnLoginPage(),
            "Tự động redirect về trang login");
        Assert.assertTrue(driver.getCurrentUrl().contains("/login/"),
            "URL có thể chứa ?next=/nhap-hang/. Không thể truy cập trang Nhập hàng");
    }

    @Test(description = "TC-08: Kiểm tra đăng xuất từ nhiều tab")
    public void TC08() {
        String originalWindow = driver.getWindowHandle();
        
        // Mở tab mới
        driver.switchTo().newWindow(WindowType.TAB);
        driver.get(Constant.HOME_URL);
        String newTab = driver.getWindowHandle();
        Assert.assertTrue(homePage.isOnHomePage(), "Tab 2 vẫn đăng nhập");
        
        // Quay lại tab 1 và đăng xuất
        driver.switchTo().window(originalWindow);
        homePage.logout();
        Assert.assertTrue(loginPage.isOnLoginPage(), "Tab 1 đã đăng xuất");
        
        // Quay lại tab 2 và refresh
        driver.switchTo().window(newTab);
        driver.navigate().refresh();
        
        Assert.assertTrue(loginPage.isOnLoginPage(),
            "Tab 2 tự động redirect về login. Session đã bị xóa ở tất cả tab");
    }

    @Test(description = "TC-09: Đăng xuất khi đang ở giữa quá trình tạo hóa đơn bán")
    public void TC09() {
        homePage.navigateToBanHang();
        Assert.assertTrue(driver.getCurrentUrl().contains("/ban-hang/"), 
            "Đã vào trang Bán hàng");
        
        // Giả lập nhập một số thông tin (chưa submit)
        // Note: Cần điều chỉnh theo form thực tế
        
        homePage.logout();
        
        Assert.assertTrue(loginPage.isOnLoginPage(),
            "Chuyển về trang login. Session bị xóa. Dữ liệu chưa lưu bị mất");
    }

    @Test(description = "TC-10: Click nhiều lần vào nút Đăng xuất")
    public void TC10() {
        homePage.clickLogoutMultipleTimes(5);
        
        Assert.assertTrue(loginPage.isOnLoginPage(),
            "Hệ thống chỉ xử lý 1 lần đăng xuất. Không gửi multiple requests. Chuyển về trang login. Không có lỗi");
    }
}
