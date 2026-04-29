package org.example;

import io.github.bonigarcia.wdm.WebDriverManager;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;

import java.time.Duration;

public class BaseTest {
    protected WebDriver driver;
    protected WebDriverWait wait;
    protected static final String BASE_URL = "http://127.0.0.1:8000";
    protected static final String LOGIN_URL = BASE_URL + "/login/";
    protected static final String SALES_URL = BASE_URL + "/ban-hang/";
    protected static final String USERNAME = "admin";
    protected static final String PASSWORD = "admin123";

    @BeforeMethod
    public void setUp() {
        WebDriverManager.chromedriver().setup();
        driver = new ChromeDriver();
        wait = new WebDriverWait(driver, Duration.ofSeconds(10));
        driver.manage().window().maximize();
    }

    @AfterMethod
    public void tearDown() {
        if (driver != null) {
            driver.quit();
        }
    }

    protected void login() {
        driver.get(LOGIN_URL);
        driver.findElement(By.name("username")).sendKeys(USERNAME);
        driver.findElement(By.name("password")).sendKeys(PASSWORD);
        driver.findElement(By.xpath("//button[contains(text(), 'Đăng nhập')]")).click();
        wait.until(ExpectedConditions.urlContains("/trang-chu/"));
    }

    protected void navigateToSalesPage() {
        driver.get(SALES_URL);
    }
}
