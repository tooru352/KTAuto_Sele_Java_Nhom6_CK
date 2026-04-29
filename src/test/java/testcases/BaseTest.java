package testcases;

import common.Constant;
import common.Utilities;
import io.github.bonigarcia.wdm.WebDriverManager;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;

import java.time.Duration;

/**
 * Base Test Class
 * Chứa setup, teardown và các methods dùng chung cho tất cả test cases
 */
public class BaseTest {
    protected WebDriver driver;
    protected WebDriverWait wait;

    @BeforeMethod
    public void setUp() {
        Utilities.logStep("SETUP - Khởi tạo WebDriver");
        WebDriverManager.chromedriver().setup();
        driver = new ChromeDriver();
        wait = new WebDriverWait(driver, Duration.ofSeconds(Constant.EXPLICIT_WAIT));
        driver.manage().window().maximize();
        driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(Constant.IMPLICIT_WAIT));
        driver.manage().timeouts().pageLoadTimeout(Duration.ofSeconds(Constant.PAGE_LOAD_TIMEOUT));
    }

    @AfterMethod
    public void tearDown() {
        Utilities.logStep("TEARDOWN - Đóng WebDriver");
        if (driver != null) {
            driver.quit();
        }
    }

    /**
     * Login vào hệ thống
     */
    protected void login() {
        Utilities.logStep("LOGIN - Đăng nhập với " + Constant.ADMIN_USERNAME);
        driver.get(Constant.LOGIN_URL);
        driver.findElement(By.name("username")).sendKeys(Constant.ADMIN_USERNAME);
        driver.findElement(By.name("password")).sendKeys(Constant.ADMIN_PASSWORD);
        driver.findElement(By.xpath("//button[contains(text(), 'Đăng nhập')]")).click();
        wait.until(ExpectedConditions.urlContains("/trang-chu/"));
        Utilities.log("Đăng nhập thành công");
    }

    /**
     * Điều hướng đến trang Bán hàng
     */
    protected void navigateToSalesPage() {
        Utilities.logStep("NAVIGATE - Đi đến trang Bán hàng");
        driver.get(Constant.SALES_URL);
        Utilities.sleep(1000);
    }
}
