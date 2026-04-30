package common;
import org.openqa.selenium.WebDriver;

/**
 * Class chứa các hằng số dùng chung trong project
 */
public class Constant {
    public static WebDriver WEBDRIVER;
    
    // URL
    public static final String BASE_URL = "http://127.0.0.1:8000";
    public static final String LOGIN_URL = BASE_URL + "/login/";
    public static final String HOME_URL = BASE_URL + "/trang-chu/";
    public static final String BAN_HANG_URL = BASE_URL + "/ban-hang/";
    public static final String NHAP_HANG_URL = BASE_URL + "/nhap-hang/";
    public static final String HANG_HOA_URL = BASE_URL + "/hang-hoa/";
    public static final String SALES_URL = BASE_URL + "/ban-hang/";
    
    // Login credentials
    public static final String VALID_USERNAME = "admin";
    public static final String VALID_PASSWORD = "admin";
    public static final String ADMIN_USERNAME = "admin";
    public static final String ADMIN_PASSWORD = "admin123";
    
    // Timeouts
    public static final int IMPLICIT_WAIT = 10;
    public static final int EXPLICIT_WAIT = 15;
    public static final int PAGE_LOAD_TIMEOUT = 30;
    
    // Browser
    public static final String BROWSER = System.getProperty("browser", "chrome");
    
    // Messages
    public static final String SUCCESS_MESSAGE = "thành công";
    public static final String ERROR_MESSAGE = "lỗi";
    public static final String DELETE_SUCCESS = "Xóa thành công";
    public static final String SAVE_SUCCESS = "Lưu thành công";
    public static final String CONFIRM_DELETE = "Xác Nhận Xoá";
    
    // Invoice codes
    public static final String DEFAULT_INVOICE_CODE = "HD015";
}
