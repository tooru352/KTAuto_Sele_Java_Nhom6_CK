package common;
import org.openqa.selenium.WebDriver;
public class Constant {
    public static WebDriver WEBDRIVER;
    public static final String BASE_URL = "http://127.0.0.1:8000/login/";
    public static final String VALID_USERNAME = "admin";
    public static final String VALID_PASSWORD = "admin";
    public static final int IMPLICIT_WAIT = 10;
    public static final int EXPLICIT_WAIT = 15;
    public static final String BROWSER = System.getProperty("browser", "chrome");
}
