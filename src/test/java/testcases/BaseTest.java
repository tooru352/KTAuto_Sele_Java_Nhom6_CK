package testcases;

import io.github.bonigarcia.wdm.WebDriverManager;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.edge.EdgeDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import common.Constant;
import common.Utilities;
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
        String browser = Constant.BROWSER.toLowerCase();
        
        switch (browser) {
            case "firefox":
                WebDriverManager.firefoxdriver().setup();
                driver = new FirefoxDriver();
                break;
            case "edge":
                WebDriverManager.edgedriver().setup();
                driver = new EdgeDriver();
                break;
            default:
                WebDriverManager.chromedriver().setup();
                driver = new ChromeDriver();
        }
        
        wait = new WebDriverWait(driver, Duration.ofSeconds(Constant.EXPLICIT_WAIT));
        driver.manage().window().maximize();
        driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(Constant.IMPLICIT_WAIT));
        driver.manage().timeouts().pageLoadTimeout(Duration.ofSeconds(Constant.PAGE_LOAD_TIMEOUT));
        Constant.WEBDRIVER = driver;
    }

    @AfterMethod
    public void tearDown() {
        if (driver != null) {
            driver.quit();
        }
    }

    /**
     * Login vào hệ thống
     */
    protected void login() {
        driver.get(Constant.LOGIN_URL);
        driver.findElement(By.name("username")).sendKeys(Constant.ADMIN_USERNAME);
        driver.findElement(By.name("password")).sendKeys(Constant.ADMIN_PASSWORD);
        driver.findElement(By.xpath("//button[contains(text(), 'Đăng nhập')]")).click();
        wait.until(ExpectedConditions.urlContains("/trang-chu/"));
    }

    /**
     * Điều hướng đến trang Bán hàng
     */
    protected void navigateToSalesPage() {
        driver.get(Constant.SALES_URL);
        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}
