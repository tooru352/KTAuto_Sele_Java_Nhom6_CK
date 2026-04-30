package common;
import org.openqa.selenium.WebDriver;
public class Constant {
    public static WebDriver WEBDRIVER;
    public static final String BASE_URL = "http://127.0.0.1:8000/login/";
    public static final String HOME_URL = "http://127.0.0.1:8000/trang-chu/";
    public static final String BAN_HANG_URL = "http://127.0.0.1:8000/ban-hang/";
    public static final String NHAP_HANG_URL = "http://127.0.0.1:8000/nhap-hang/";
    public static final String HANG_HOA_URL = "http://127.0.0.1:8000/hang-hoa/";
    public static final String VALID_USERNAME = "admin";
    public static final String VALID_PASSWORD = "admin";
    public static final int IMPLICIT_WAIT = 10;
    public static final int EXPLICIT_WAIT = 15;
    public static final String BROWSER = System.getProperty("browser", "chrome");
}
