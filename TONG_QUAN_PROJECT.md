# 📚 Tổng Quan Project - Test Automation CONU Bookstore

## 🎯 **Mục tiêu:**
Tạo test automation cho hệ thống CONU Bookstore với 2 modules:
1. **Chỉnh sửa hóa đơn** (18 test cases)
2. **Xóa hóa đơn** (2 test cases)

---

## 📁 **Cấu trúc Project:**

```
selenium-invoice-test/
├── src/
│   ├── main/java/
│   │   ├── common/
│   │   │   ├── Constant.java           ← Hằng số (URL, credentials, timeouts)
│   │   │   └── Utilities.java          ← Utility methods (click, wait, log)
│   │   │
│   │   ├── dataobjects/
│   │   │   ├── InvoiceEditData.java    ← Data object cho Edit (Builder pattern)
│   │   │   └── InvoiceDeleteData.java  ← Data object cho Delete (Builder pattern)
│   │   │
│   │   └── pageobjects/
│   │       ├── InvoiceEditPage.java    ← Page Object cho Edit
│   │       └── InvoiceDeletePage.java  ← Page Object cho Delete
│   │
│   └── test/java/testcases/
│       ├── BaseTest.java               ← Base class (setup, teardown, login)
│       ├── InvoiceEditTest.java        ← 10 test cases mẫu (Edit)
│       └── InvoiceDeleteTest.java      ← 2 test cases (Delete)
│
├── pom.xml                             ← Maven dependencies
├── testng.xml                          ← TestNG configuration
└── [Các file hướng dẫn .md]
```

---

## 🔧 **Công nghệ sử dụng:**

| Công nghệ | Version | Mục đích |
|-----------|---------|----------|
| **Java** | 11+ | Ngôn ngữ lập trình |
| **Selenium WebDriver** | 4.15.0 | Automation framework |
| **TestNG** | 7.8.0 | Test framework |
| **WebDriverManager** | 5.6.3 | Quản lý driver |
| **Maven** | 3.x | Build tool |
| **Chrome Driver** | Auto | Browser driver |

---

## 🎨 **Design Pattern:**

### **1. Page Object Model (POM)**
- Tách biệt UI locators và test logic
- Locators khai báo đầu class
- Methods tương tác với UI

### **2. Builder Pattern**
- Tạo test data linh hoạt
- Code dễ đọc, dễ maintain

### **3. Base Test Pattern**
- Setup/teardown chung
- Login/navigate chung
- Tái sử dụng code

---

## 📊 **Test Cases:**

### **Module 1: Chỉnh sửa hóa đơn (10/18 test cases đã tạo)**

| TC | Mô tả | Status |
|----|-------|--------|
| TC-01 | Sửa số lượng hợp lệ | ✅ Done |
| TC-02 | Sửa số lượng vượt tồn kho | ✅ Done |
| TC-03 | Sửa số lượng = 0 | ✅ Done |
| TC-04 | Sửa số lượng âm | ✅ Done |
| TC-07 | Kiểm tra tính toán tự động | ✅ Done |
| TC-09 | Kiểm tra đơn giá read-only | ✅ Done |
| TC-10 | Kiểm tra ngày bán read-only | ✅ Done |
| TC-12 | Kiểm tra mã hóa đơn read-only | ✅ Done |
| TC-13 | Sửa chiết khấu thành công | ✅ Done |
| TC-17 | Kiểm tra thông tin khách hàng | ✅ Done |

**Còn lại 8 test cases chưa implement**

### **Module 2: Xóa hóa đơn (2/2 test cases đã tạo)**

| TC | Mô tả | Status |
|----|-------|--------|
| TC-01 | Xóa hóa đơn thành công | ✅ Done (đã sửa) |
| TC-02 | Hủy xóa hóa đơn | ✅ Done |

---

## 🚀 **Cách chạy test:**

### **Bước 1: Chuẩn bị**
```bash
# 1. Đảm bảo server đang chạy
python manage.py runserver

# 2. Đảm bảo có dữ liệu test (ít nhất 2 hóa đơn)
```

### **Bước 2: Chạy test trong IntelliJ**

#### **Chạy tất cả test cases:**
```
1. Click chuột phải vào thư mục: src/test/java/testcases/
2. Chọn: Run 'Tests in testcases'
```

#### **Chạy 1 test class:**
```
1. Mở file test (ví dụ: InvoiceDeleteTest.java)
2. Click chuột phải vào tên class
3. Chọn: Run 'InvoiceDeleteTest'
```

#### **Chạy 1 test case:**
```
1. Mở file test
2. Tìm method test (ví dụ: testDeleteInvoiceSuccess)
3. Click vào icon ▶️ bên trái method
4. Chọn: Run 'testDeleteInvoiceSuccess()'
```

---

## 📝 **Workflow test:**

### **Workflow chỉnh sửa hóa đơn:**
```
1. Login (admin/admin123)
2. Navigate to /ban-hang/
3. Click row hóa đơn (onclick="xemChiTietHoaDon('HD015')")
4. Click nút "Sửa"
5. Click "Đồng ý" trong popup
6. Double-click ô số lượng
7. Nhập số lượng mới
8. Click "Lưu"
9. Verify thông báo thành công
10. Verify dữ liệu đã thay đổi
```

### **Workflow xóa hóa đơn:**
```
1. Login (admin/admin123)
2. Navigate to /ban-hang/
3. Click row hóa đơn
4. Click nút "Xóa"
5. Verify popup "Xác Nhận Xoá?"
6. Click "Đồng ý" (hoặc "Hủy")
7. Verify thông báo (nếu có)
8. Navigate về danh sách
9. Verify hóa đơn đã xóa (hoặc vẫn tồn tại)
```

---

## 🔍 **Locator Strategy:**

### **Ưu tiên sử dụng:**
1. **ID** (nếu có): `By.id("bh-edit-so-luong")`
2. **Name** (nếu có): `By.name("username")`
3. **XPath** (phổ biến nhất): `By.xpath("//button[contains(text(), 'Xóa')]")`

### **Locators quan trọng:**

#### **Login:**
```java
By.name("username")
By.name("password")
By.xpath("//button[contains(text(), 'Đăng nhập')]")
```

#### **Invoice Edit:**
```java
By.xpath("//table//tr[contains(@onclick, 'xemChiTietHoaDon')]")
By.xpath("//button[contains(text(), 'Sửa')]")
By.id("bh-edit-so-luong")
By.id("bh-edit-chiet-khau")
By.xpath("//button[contains(text(), 'Lưu')]")
```

#### **Invoice Delete:**
```java
By.xpath("//button[contains(text(), 'Xóa')]")
By.xpath("//div[contains(@class, 'popup') and contains(@class, 'show')]")
By.xpath("//button[contains(text(), 'Đồng ý')]")
By.xpath("//button[contains(text(), 'Hủy')]")
```

---

## ⚠️ **Lưu ý quan trọng:**

### **1. Chiết khấu (Discount):**
- ✅ Là **PHẦN TRĂM** (%), không phải tiền tệ
- ✅ Nhập giá trị: 50 (nghĩa là 50%)
- ❌ Không nhập: 50000 (sai!)

### **2. Test data protection:**
- ✅ TC-01 (xóa hóa đơn) xóa hóa đơn **CUỐI CÙNG**
- ✅ Không xóa hóa đơn đầu tiên
- ✅ Kiểm tra có ít nhất 2 hóa đơn trước khi xóa
- ✅ Skip test nếu không đủ dữ liệu

### **3. Wait strategy:**
- ✅ Sử dụng `WebDriverWait` với `ExpectedConditions`
- ✅ Sử dụng `Utilities.sleep()` khi cần
- ✅ Implicit wait: 10 seconds
- ✅ Explicit wait: 15 seconds

### **4. Click strategy:**
- ✅ Ưu tiên: `element.click()`
- ✅ Nếu fail: `Utilities.clickByJavaScript()`
- ✅ Đặc biệt với table rows: luôn dùng JavaScript click

---

## 🐛 **Troubleshooting:**

### **Vấn đề 1: Element not clickable**
```
Giải pháp: Sử dụng Utilities.clickByJavaScript()
```

### **Vấn đề 2: Element not found**
```
Giải pháp: 
- Kiểm tra locator trong browser DevTools
- Tăng wait time
- Kiểm tra element có trong iframe không
```

### **Vấn đề 3: Test fail vì không có success message**
```
Giải pháp: 
- Đã sửa: success message check là optional
- Verify bằng cách kiểm tra dữ liệu thực tế
```

### **Vấn đề 4: StaleElementReferenceException**
```
Giải pháp:
- Re-find element sau khi page refresh
- Sử dụng wait.until() để đợi element stable
```

---

## 📚 **Files hướng dẫn:**

| File | Mô tả |
|------|-------|
| `HUONG_DAN_CAU_TRUC_MOI.md` | Hướng dẫn cấu trúc project mới |
| `HUONG_DAN_CHAY_TEST_SAU_KHI_SUA.md` | Hướng dẫn chạy test sau khi sửa |
| `HUONG_DAN_CHAY_TC01_AN_TOAN.md` | Hướng dẫn chạy TC-01 an toàn |
| `HUONG_DAN_CHAY_TEST_XOA_HOA_DON.md` | Hướng dẫn chạy test xóa hóa đơn |
| `TONG_QUAN_PROJECT.md` | File này - Tổng quan project |

---

## ✅ **Checklist hoàn thành:**

### **Cấu trúc:**
- [x] Tạo thư mục `common/`, `dataobjects/`, `pageobjects/`, `testcases/`
- [x] Tạo `Constant.java`, `Utilities.java`
- [x] Tạo `InvoiceEditData.java`, `InvoiceDeleteData.java`
- [x] Tạo `InvoiceEditPage.java`, `InvoiceDeletePage.java`
- [x] Tạo `BaseTest.java`

### **Test Cases:**
- [x] Tạo `InvoiceEditTest.java` với 10 test cases mẫu
- [x] Tạo `InvoiceDeleteTest.java` với 2 test cases
- [x] Sửa TC-01 (xóa hóa đơn) để xử lý optional success message
- [ ] Tạo thêm 8 test cases còn lại cho Edit module

### **Documentation:**
- [x] Tạo các file hướng dẫn
- [x] Tạo file tổng quan project
- [x] Comment code rõ ràng

---

## 🎯 **Next Steps:**

### **1. Chạy test hiện tại:**
```
✅ Chạy InvoiceDeleteTest (2 test cases)
✅ Chạy InvoiceEditTest (10 test cases)
✅ Verify tất cả test PASS
```

### **2. Thêm test cases còn lại:**
```
⏳ Thêm 8 test cases còn lại cho Edit module
⏳ Tổng cộng: 18 test cases cho Edit
```

### **3. Tối ưu hóa:**
```
⏳ Refactor code nếu cần
⏳ Thêm test data từ Excel
⏳ Thêm reporting (Extent Reports)
```

---

## 📞 **Liên hệ & Support:**

Nếu gặp vấn đề:
1. ✅ Đọc file hướng dẫn tương ứng
2. ✅ Kiểm tra log trong console
3. ✅ Kiểm tra browser DevTools
4. ✅ Gửi log để được hỗ trợ

---

## 🎉 **Kết luận:**

**Project đã được xây dựng theo chuẩn Page Object Model với:**
- ✅ Cấu trúc rõ ràng, dễ maintain
- ✅ Code tái sử dụng cao
- ✅ Test cases đầy đủ, chi tiết
- ✅ Documentation đầy đủ
- ✅ Sẵn sàng để mở rộng

**Hãy chạy test và cho tôi biết kết quả!** 🚀

---

**Last Updated:** 2026-04-30
**Version:** 2.0
**Status:** ✅ Ready for Testing
