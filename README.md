# Login & Logout Automation Test Suite

## Mô tả
Automation test suite cho chức năng đăng nhập và đăng xuất:
- **Login**: 17 test cases
- **Logout**: 10 test cases

## Test Cases

### Login Tests (17 TCs)
| ID | Mô tả | Kết quả mong đợi |
|----|-------|------------------|
| TC_01 | Login thành công với thông tin hợp lệ | Chuyển đến trang chủ, hiển thị ADMIN |
| TC_02 | Sai username | Hiển thị lỗi |
| TC_03 | Sai password | Hiển thị lỗi |
| TC_04-06 | Validation các trường rỗng | HTML5 validation |
| TC_07-08 | Username với spaces/uppercase | Đăng nhập thất bại |
| TC_09-10 | Security testing (SQL Injection, XSS) | Hệ thống chống được attack |
| TC_11-13 | Boundary testing | Xử lý đúng |
| TC_14 | Copy-Paste password | Đăng nhập thành công |
| TC_16 | Multiple clicks | Xử lý đúng |
| TC_17 | Browser back button | Không quay lại login |

### Logout Tests (10 TCs)
| ID | Mô tả | Kết quả mong đợi |
|----|-------|------------------|
| TC_01 | Đăng xuất từ trang chủ | Redirect về login, session bị xóa |
| TC_02 | Đăng xuất từ trang Bán hàng | Không thể back về trang Bán hàng |
| TC_03 | Đăng xuất từ trang Nhập hàng | Không thể back về trang Nhập hàng |
| TC_04 | Đăng xuất từ trang Hàng hóa | Không thể back về trang Hàng hóa |
| TC_05 | Kiểm tra session sau logout | Không truy cập được trang chủ |
| TC_06 | Truy cập Bán hàng sau logout | Auto redirect về login |
| TC_07 | Truy cập Nhập hàng sau logout | Auto redirect về login |
| TC_08 | Đăng xuất từ nhiều tab | Session xóa ở tất cả tab |
| TC_09 | Đăng xuất khi đang tạo hóa đơn | Dữ liệu chưa lưu bị mất |
| TC_10 | Click nhiều lần nút Đăng xuất | Chỉ xử lý 1 lần |

## Cấu trúc dự án
```
src/
├── main/java/
│   ├── common/
│   │   ├── Constant.java          # Constants và URLs
│   │   └── Utilities.java         # Utility methods
│   ├── dataobjects/
│   │   └── LoginData.java         # Test data
│   └── pageobjects/
│       ├── LoginPage.java         # Login page object
│       └── HomePage.java          # Home page object
└── test/java/
    └── testcases/
        ├── BaseTest.java          # Base test class
        ├── LoginTest.java         # Login test cases (17 TCs)
        └── LogoutTest.java        # Logout test cases (10 TCs)
```

## Yêu cầu
- Java 25
- Maven
- Chrome/Firefox/Edge browser
- Server chạy tại http://127.0.0.1:8000/

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

# Chạy riêng Login tests
mvn test -Dtest=LoginTest

# Chạy riêng Logout tests
mvn test -Dtest=LogoutTest
```

## Lưu ý
- Đảm bảo server đang chạy tại http://127.0.0.1:8000/
- Có thể cần điều chỉnh XPath locators theo HTML thực tế
- WebDriverManager tự động download và quản lý driver
- Logout tests yêu cầu đăng nhập thành công trước mỗi test case
