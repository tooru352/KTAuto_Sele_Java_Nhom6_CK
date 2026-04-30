package pageobjects;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import common.Constant;
import java.time.Duration;

public class HomePage {
    private WebDriver driver;
    private WebDriverWait wait;

    private By adminDropdown = By.xpath("//*[contains(text(),'ADMIN')]");
    private By logoutButton = By.xpath("//*[contains(text(),'Đăng xuất')]");
    private By banHangMenu = By.xpath("//*[contains(text(),'Bán hàng')]");
    private By nhapHangMenu = By.xpath("//*[contains(text(),'Nhập hàng')]");
    private By hangHoaMenu = By.xpath("//*[contains(text(),'Hàng hóa')]");

    public HomePage(WebDriver driver) {
        this.driver = driver;
        this.wait = new WebDriverWait(driver, Duration.ofSeconds(Constant.EXPLICIT_WAIT));
    }

    public void clickAdminDropdown() {
        wait.until(ExpectedConditions.elementToBeClickable(adminDropdown)).click();
    }

    public void clickLogout() {
        wait.until(ExpectedConditions.elementToBeClickable(logoutButton)).click();
    }

    public void logout() {
        clickAdminDropdown();
        clickLogout();
    }

    public void clickLogoutMultipleTimes(int times) {
        clickAdminDropdown();
        for (int i = 0; i < times; i++) {
            try {
                wait.until(ExpectedConditions.elementToBeClickable(logoutButton)).click();
            } catch (Exception e) {
                break;
            }
        }
    }

    public void navigateToBanHang() {
        wait.until(ExpectedConditions.elementToBeClickable(banHangMenu)).click();
    }

    public void navigateToNhapHang() {
        wait.until(ExpectedConditions.elementToBeClickable(nhapHangMenu)).click();
    }

    public void navigateToHangHoa() {
        wait.until(ExpectedConditions.elementToBeClickable(hangHoaMenu)).click();
    }

    public boolean isOnHomePage() {
        return driver.getCurrentUrl().contains("/trang-chu/");
    }

    public String getCurrentUrl() {
        return driver.getCurrentUrl();
    }
}
