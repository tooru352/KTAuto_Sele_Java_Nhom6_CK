# Login Automation Test Suite

## Mô tả
Automation test suite cho chức năng đăng nhập, bao gồm 17 test cases kiểm tra các tình huống:
- Đăng nhập thành công/thất bại
- Validation các trường input
- Security testing (SQL Injection, XSS)
- Boundary testing (độ dài input)
- UI behavior testing

## Cấu trúc dự án
```
src/
├── main/java/
│   ├── common/
│   │   ├── Constant.java          # Constants và configuration
│   │   └── Utilities.java         # Utility methods
│   ├── dataobjects/
│   │   └── LoginData.java         # Test data
│   └── pageobjects/
│       └── LoginPage.java         # Page Object Model cho Login page
└── test/java/
    └── testcases/
        ├── BaseTest.java          # Base test class với setup/teardown
        └── LoginTest.java         # Login test cases
```

## Yêu cầu
- Java 25
- Maven
- Chrome/Firefox/Edge browser

## Cài đặt
```bash
mvn clean install
```

## Chạy tests
```bash
# Chạy tất cả tests
mvn test

# Chạy với browser cụ thể
mvn test -Dbrowser=chrome
mvn test -Dbrowser=firefox
mvn test -Dbrowser=edge

# Chạy với TestNG XML
mvn test -DsuiteXmlFile=testng.xml
```

## Test Cases
| ID | Mô tả | Kết quả mong đợi |
|----|-------|------------------|
| TC_01 | Login thành công với thông tin hợp lệ | Chuyển đến trang chủ |
| TC_02 | Sai username | Hiển thị lỗi, ở lại trang login |
| TC_03 | Sai password | Hiển thị lỗi, ở lại trang login |
| TC_04 | Không nhập username | Không cho phép đăng nhập |
| TC_05 | Không nhập password | Không cho phép đăng nhập |
| TC_06 | Không nhập cả username và password | Không cho phép đăng nhập |
| TC_07 | Username có khoảng trắng | Đăng nhập thất bại |
| TC_08 | Username viết hoa | Đăng nhập thất bại (case-sensitive) |
| TC_09 | SQL Injection | Hệ thống chống được attack |
| TC_10 | XSS Attack | Hệ thống chống được attack |
| TC_11 | Password có ký tự đặc biệt | Đăng nhập thất bại |
| TC_12 | Username quá dài (>50 ký tự) | Đăng nhập thất bại |
| TC_13 | Password quá dài (>50 ký tự) | Đăng nhập thất bại |
| TC_14 | Copy-Paste password | Đăng nhập thành công |
| TC_16 | Click nhiều lần nút Login | Xử lý đúng, không bị lỗi |
| TC_17 | Browser back button sau login | Không quay lại trang login |

## Lưu ý
- Đảm bảo server đang chạy tại http://127.0.0.1:8000/login/
- Có thể cần điều chỉnh locators trong LoginPage.java theo HTML thực tế của ứng dụng
- WebDriverManager tự động download và quản lý driver
