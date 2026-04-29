package dataobjects;

public class LoginData {
    public static final String VALID_USERNAME = "admin";
    public static final String VALID_PASSWORD = "admin";
    public static final String INVALID_USERNAME = "wronguser";
    public static final String INVALID_PASSWORD = "wrongpass";
    public static final String USERNAME_WITH_SPACES = " admin ";
    public static final String USERNAME_UPPERCASE = "ADMIN";
    public static final String SQL_INJECTION = "admin' OR '1'='1";
    public static final String XSS_ATTACK = "<script>alert('XSS')</script>";
    public static final String PASSWORD_SPECIAL_CHARS = "admin@#$%123";
    public static final String LONG_STRING_50 = "a".repeat(50);
}
