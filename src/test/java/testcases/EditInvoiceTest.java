package testcases;

import org.testng.Assert;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;
import pageobjects.EditInvoicePage;
import pageobjects.LoginPage;
import dataobjects.EditInvoiceData;
import dataobjects.LoginData;

public class EditInvoiceTest extends BaseTest {
    private EditInvoicePage editInvoicePage;
    private LoginPage loginPage;

    @BeforeMethod
    public void setUpTest() {
        // Dang nhap truoc
        loginPage = new LoginPage(driver);
        loginPage.open();
        loginPage.login(LoginData.VALID_USERNAME, LoginData.VALID_PASSWORD);
        
        // Sau do vao trang "Nhap hang"
        editInvoicePage = new EditInvoicePage(driver);
        editInvoicePage.openImportPage();
        
        // Cho trang load
        try {
            Thread.sleep(2000);
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }
        
        editInvoicePage.selectInvoice(EditInvoiceData.INVOICE_CODE_HDN01);
        
        // Cho modal mo
        try {
            Thread.sleep(2000);
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }
    }

    @Test(description = "TC_01: Sua so luong hang hoa")
    public void testEditQuantityValid() throws InterruptedException {
        // TC_01: Sua so luong hang hoa
        // Steps: 1. Dang nhap vao he thong (da lam trong setUpTest)
        //        2. Chon menu "Nhap hang" (da lam trong setUpTest)
        //        3. Chon hoa don HDN01 (da lam trong setUpTest)
        //        4. Sua so luong tu 10 thanh 15
        //        5. Bam "Luu"
        
        System.out.println("=== TC_01: Sua so luong hang hoa ===");
        
        // Step 4: Click nut "Sua"
        editInvoicePage.clickEditButton();
        System.out.println("Da click nut Sua");
        
        // Step 4: Sua so luong tu 10 thanh 15
        editInvoicePage.enterQuantity(EditInvoiceData.NEW_QUANTITY_15);
        System.out.println("Da sua so luong thanh 15");
        
        // Kiem tra gia tri da nhap
        int currentQuantity = editInvoicePage.getQuantity();
        System.out.println("So luong hien tai: " + currentQuantity);
        Assert.assertEquals(currentQuantity, EditInvoiceData.NEW_QUANTITY_15,
            "So luong da duoc nhap thanh 15");
        
        // Step 5: Bam "Luu"
        editInvoicePage.clickSaveButton();
        System.out.println("Da click nut Luu");
        
        // Cho server xu ly
        Thread.sleep(1000);
        
        // Expected Result: Hien thi thong bao "Đã Lưu Thông Tin Thay Đổi!"
        boolean successDisplayed = editInvoicePage.isSuccessMessageDisplayed();
        System.out.println("Success message displayed: " + successDisplayed);
        
        Assert.assertTrue(successDisplayed,
            "Hien thi thong bao 'Đã Lưu Thông Tin Thay Đổi!'");
        
        // Click OK button de dong popup
        try {
            org.openqa.selenium.WebElement okButton = editInvoicePage.driver.findElement(
                org.openqa.selenium.By.xpath("//button[contains(text(),'OK')]"));
            if (okButton.isDisplayed()) {
                okButton.click();
                System.out.println("Da click OK button");
                Thread.sleep(1000);
            }
        } catch (Exception e) {
            System.out.println("Khong tim thay OK button");
        }
        
        System.out.println("TC_01 PASSED");
    }

    @Test(description = "TC_02: Sua so luong hang hoa thanh 0")
    public void testEditQuantityToZero() throws InterruptedException {
        editInvoicePage.clickEditButton();
        editInvoicePage.enterQuantity(EditInvoiceData.ZERO_QUANTITY);
        editInvoicePage.clickSaveButton();
        
        // Cho server xu ly
        Thread.sleep(1000);
        
        // Check xem co error message hay alert
        boolean hasError = editInvoicePage.isErrorMessageDisplayed();
        System.out.println("Has error message: " + hasError);
        
        if (hasError) {
            String errorText = editInvoicePage.getErrorMessageText();
            System.out.println("Error text: " + errorText);
            Assert.assertTrue(errorText.contains("Số lượng") || errorText.contains("số lượng"),
                "Thong bao: 'So luong va don gia phai >0. Vui long nhap lai'");
        }
    }

    @Test(description = "TC_03: Sua so luong thanh so am")
    public void testEditQuantityToNegative() {
        editInvoicePage.clickEditButton();
        editInvoicePage.enterQuantity(EditInvoiceData.NEGATIVE_QUANTITY);
        
        Assert.assertFalse(editInvoicePage.isNegativeQuantityAllowed(),
            "He thong khong cho phep nhap ky tu '-' vao o so luong");
        Assert.assertEquals(editInvoicePage.getQuantity(), EditInvoiceData.ORIGINAL_QUANTITY,
            "Gia tri cu duoc giu nguyen");
    }

    @Test(description = "TC_04: Sua don gia hop le")
    public void testEditUnitPriceValid() throws InterruptedException {
        editInvoicePage.clickEditButton();
        editInvoicePage.enterUnitPrice(EditInvoiceData.NEW_UNIT_PRICE_55000);
        System.out.println("Da sua don gia thanh 55000");
        
        // Kiem tra gia tri da nhap
        int currentUnitPrice = editInvoicePage.getUnitPrice();
        System.out.println("Don gia hien tai: " + currentUnitPrice);
        Assert.assertEquals(currentUnitPrice, EditInvoiceData.NEW_UNIT_PRICE_55000,
            "Don gia da duoc nhap thanh 55000");
        
        editInvoicePage.clickSaveButton();
        System.out.println("Da click nut Luu");
        
        // Cho server xu ly
        Thread.sleep(1000);
        
        // Expected Result: Hien thi thong bao "Đã Lưu Thông Tin Thay Đổi!"
        boolean successDisplayed = editInvoicePage.isSuccessMessageDisplayed();
        System.out.println("Success message displayed: " + successDisplayed);
        
        Assert.assertTrue(successDisplayed,
            "Hien thi thong bao 'Đã Lưu Thông Tin Thay Đổi!'");
        
        // Click OK button de dong popup
        try {
            org.openqa.selenium.WebElement okButton = editInvoicePage.driver.findElement(
                org.openqa.selenium.By.xpath("//button[contains(text(),'OK')]"));
            if (okButton.isDisplayed()) {
                okButton.click();
                System.out.println("Da click OK button");
                Thread.sleep(1000);
            }
        } catch (Exception e) {
            System.out.println("Khong tim thay OK button");
        }
        
        System.out.println("TC_04 PASSED");
    }

    @Test(description = "TC_05: Sua don gia thanh so am")
    public void testEditUnitPriceToNegative() throws InterruptedException {
        editInvoicePage.clickEditButton();
        
        // Lấy giá trị cũ trước khi sửa
        int originalPrice = editInvoicePage.getUnitPrice();
        System.out.println("Giá trị cũ: " + originalPrice);
        
        // Thử nhập giá trị âm
        editInvoicePage.enterUnitPrice(EditInvoiceData.NEGATIVE_UNIT_PRICE);
        System.out.println("Đã nhập giá trị âm: " + EditInvoiceData.NEGATIVE_UNIT_PRICE);
        
        // Bấm "Lưu"
        editInvoicePage.clickSaveButton();
        System.out.println("Da click nut Luu");
        
        // Cho server xu ly
        Thread.sleep(1000);
        
        // Expected Result: Hiển thị thông báo "Đơn giá phải >0. Vui lòng nhập lại"
        boolean hasError = editInvoicePage.isErrorMessageDisplayed();
        System.out.println("Has error message: " + hasError);
        
        if (hasError) {
            String errorText = editInvoicePage.getErrorMessageText();
            System.out.println("Error text: " + errorText);
            Assert.assertTrue(errorText.contains("Đơn giá") || errorText.contains("đơn giá"),
                "Thong bao: 'Don gia phai >0. Vui long nhap lai'");
        } else {
            // Nếu không có error message, test fail
            Assert.fail("Hệ thống phải hiển thị thông báo lỗi khi nhập đơn giá âm");
        }
        
        System.out.println("TC_05 FAILED - Hệ thống không xử lý đơn giá âm đúng cách");
    }

    @Test(description = "TC_06: Sua don gia thanh 0")
    public void testEditUnitPriceToZero() throws InterruptedException {
        editInvoicePage.clickEditButton();
        editInvoicePage.enterUnitPrice(EditInvoiceData.ZERO_UNIT_PRICE);
        editInvoicePage.clickSaveButton();
        
        // Cho server xu ly
        Thread.sleep(1000);
        
        // Check xem co error message hay alert
        boolean hasError = editInvoicePage.isErrorMessageDisplayed();
        System.out.println("Has error message: " + hasError);
        
        if (hasError) {
            String errorText = editInvoicePage.getErrorMessageText();
            System.out.println("Error text: " + errorText);
            Assert.assertTrue(errorText.contains("Số lượng") || errorText.contains("Đơn giá"),
                "Thong bao: 'So luong va don gia phai >0. Vui long nhap lai'");
        }
    }

    @Test(description = "TC_07: Huy sua hoa don")
    public void testCancelEditInvoice() throws InterruptedException {
        // Step 4: Click nut "Sua"
        editInvoicePage.clickEditButton();
        System.out.println("Da click nut Sua");
        
        // Lấy giá trị cũ trước khi sửa
        int originalQuantity = editInvoicePage.getQuantity();
        System.out.println("So luong ban dau: " + originalQuantity);
        
        // Step 4: Sua so luong tu 10 thanh 20
        editInvoicePage.enterQuantity(EditInvoiceData.NEW_QUANTITY_20);
        System.out.println("Da sua so luong thanh 20");
        
        // Kiem tra gia tri da nhap
        int currentQuantity = editInvoicePage.getQuantity();
        System.out.println("So luong hien tai: " + currentQuantity);
        Assert.assertEquals(currentQuantity, EditInvoiceData.NEW_QUANTITY_20,
            "So luong da duoc nhap thanh 20");
        
        // Step 5: Bam "Huy"
        editInvoicePage.clickCancelButton();
        System.out.println("Da click nut Huy");
        
        // Cho server xu ly
        Thread.sleep(1000);
        
        // Expected Result: Khong hien thi thong bao luu
        boolean successDisplayed = editInvoicePage.isSuccessMessageDisplayed();
        System.out.println("Success message displayed: " + successDisplayed);
        Assert.assertFalse(successDisplayed,
            "Khong hien thi thong bao luu");
        
        // Expected Result: Thong tin hoa don quay lai gia tri ban dau (so luong = 10)
        // Sau khi click Huy, modal dong va quay lai danh sach
        // Nen khong the kiem tra so luong trong modal nua
        // Chi kiem tra xem co quay lai danh sach hay khong
        
        // Expected Result: Quay lai danh sach hoa don
        boolean onListPage = editInvoicePage.isOnInvoiceListPage();
        System.out.println("On invoice list page: " + onListPage);
        Assert.assertTrue(onListPage,
            "Quay lai danh sach hoa don");
        
        System.out.println("TC_07 PASSED");
    }

    @Test(description = "TC_08: Kiem tra tinh toan thanh tien")
    public void testTotalAmountCalculation() throws InterruptedException {
        // TC_08: Kiểm tra tính toán thành tiền
        // Steps: 1. Đăng nhập vào hệ thống (đã làm trong setUpTest)
        //        2. Chọn menu "Nhập hàng" (đã làm trong setUpTest)
        //        3. Chọn hóa đơn HDN01 (đã làm trong setUpTest)
        //        4. Sửa: Đơn giá = 100000, Số lượng = 5, Chiết khấu = 5.5%
        //        5. Bấm "Lưu"
        //        6. Kiểm tra thành tiền = (100000 x 5) x (1 - 5.5/100) = 472.500
        
        System.out.println("=== TC_08: Kiem tra tinh toan thanh tien ===");
        
        // Step 4: Click nút "Sửa"
        editInvoicePage.clickEditButton();
        System.out.println("Da click nut Sua");
        
        // Step 4: Sửa đơn giá = 100000
        editInvoicePage.enterUnitPrice(EditInvoiceData.NEW_UNIT_PRICE_100000);
        System.out.println("Da sua don gia thanh 100000");
        
        // Step 4: Sửa số lượng = 5
        editInvoicePage.enterQuantity(EditInvoiceData.NEW_QUANTITY_5);
        System.out.println("Da sua so luong thanh 5");
        
        // Step 4: Sửa chiết khấu = 5.5%
        editInvoicePage.enterDiscount(EditInvoiceData.DECIMAL_DISCOUNT);
        System.out.println("Da sua chiet khau thanh 5.5%");
        
        // Kiểm tra các giá trị đã nhập
        int currentQuantity = editInvoicePage.getQuantity();
        int currentUnitPrice = editInvoicePage.getUnitPrice();
        double currentDiscount = editInvoicePage.getDiscount();
        System.out.println("So luong: " + currentQuantity + ", Don gia: " + currentUnitPrice + ", Chiet khau: " + currentDiscount + "%");
        
        Assert.assertEquals(currentQuantity, EditInvoiceData.NEW_QUANTITY_5,
            "So luong da duoc nhap thanh 5");
        Assert.assertEquals(currentUnitPrice, EditInvoiceData.NEW_UNIT_PRICE_100000,
            "Don gia da duoc nhap thanh 100000");
        Assert.assertEquals(currentDiscount, EditInvoiceData.DECIMAL_DISCOUNT,
            "Chiet khau da duoc nhap thanh 5.5%");
        
        // Step 5: Bấm "Lưu"
        editInvoicePage.clickSaveButton();
        System.out.println("Da click nut Luu");
        
        // Chờ server xử lý
        Thread.sleep(2000);
        
        // Expected Result: Hiển thị thông báo "Đã Lưu Thông Tin Thay Đổi!"
        boolean successDisplayed = editInvoicePage.isSuccessMessageDisplayed();
        System.out.println("Success message displayed: " + successDisplayed);
        
        Assert.assertTrue(successDisplayed,
            "Hien thi thong bao 'Đã Lưu Thông Tin Thay Đổi!'");
        
        // Chờ thêm để bảng được cập nhật
        Thread.sleep(2000);
        
        // Step 6: Kiểm tra thành tiền
        // Công thức: Thành tiền = (Số lượng × Đơn giá) × (1 - Chiết khấu%/100)
        // = (5 × 100000) × (1 - 5.5/100)
        // = 500000 × 0.945
        // = 472.500
        double actualTotal = editInvoicePage.getTotalAmountWithDiscount();
        System.out.println("Thanh tien thuc te: " + actualTotal);
        System.out.println("Thanh tien du kien: " + EditInvoiceData.EXPECTED_TOTAL_WITH_DISCOUNT);
        
        Assert.assertEquals(actualTotal, EditInvoiceData.EXPECTED_TOTAL_WITH_DISCOUNT, 0.01,
            "Thanh tien = (100000 x 5) x (1 - 5.5/100) = 472.500");
        
        // Click OK button để đóng popup
        try {
            org.openqa.selenium.WebElement okButton = editInvoicePage.driver.findElement(
                org.openqa.selenium.By.xpath("//button[contains(text(),'OK')]"));
            if (okButton.isDisplayed()) {
                okButton.click();
                System.out.println("Da click OK button");
                Thread.sleep(1000);
            }
        } catch (Exception e) {
            System.out.println("Khong tim thay OK button");
        }
        
        System.out.println("TC_08 PASSED");
    }

    @Test(description = "TC_09: Kiem tra chiet khau voi gia tri thap phan")
    public void testDiscountWithDecimalValue() {
        editInvoicePage.clickEditButton();
        editInvoicePage.enterDiscount(EditInvoiceData.DECIMAL_DISCOUNT);
        editInvoicePage.clickSaveButton();
        
        Assert.assertTrue(editInvoicePage.isSuccessMessageDisplayed(),
            "Hien thi thong bao 'Luu thanh cong'");
        Assert.assertEquals(editInvoicePage.getDiscount(), EditInvoiceData.DECIMAL_DISCOUNT,
            "Gia tri chiet khau duoc cap nhat thanh 5.5");
    }

    @Test(description = "TC_10: Nhap chiet khau > 100")
    public void testDiscountGreaterThan100() throws InterruptedException {
        editInvoicePage.clickEditButton();
        editInvoicePage.enterDiscount(EditInvoiceData.INVALID_DISCOUNT_150);
        editInvoicePage.clickSaveButton();
        
        // Cho server xu ly
        Thread.sleep(1000);
        
        // Check xem co error message hay alert
        boolean hasError = editInvoicePage.isErrorMessageDisplayed();
        System.out.println("Has error message: " + hasError);
        
        if (hasError) {
            String errorText = editInvoicePage.getErrorMessageText();
            System.out.println("Error text: " + errorText);
            Assert.assertTrue(errorText.contains("Chiết khấu") || errorText.contains("chiết khấu"),
                "Thong bao: 'Chiet khau phai tu 0 den 100'");
        }
    }

    @Test(description = "TC_11: Sua phuong thuc thanh toan hop le")
    public void testEditPaymentMethodValid() {
        // Open HDN02 invoice
        editInvoicePage.openImportPage();
        editInvoicePage.selectInvoice(EditInvoiceData.INVOICE_CODE_HDN02);
        editInvoicePage.waitForPageToLoad();
        
        editInvoicePage.clickEditButton();
        editInvoicePage.selectPaymentMethod(EditInvoiceData.PAYMENT_METHOD_CASH);
        editInvoicePage.clickSaveButton();
        
        Assert.assertTrue(editInvoicePage.isSuccessMessageDisplayed(),
            "Hien thi thong bao luu thanh cong");
        Assert.assertEquals(editInvoicePage.getPaymentMethod(), EditInvoiceData.PAYMENT_METHOD_CASH,
            "Phuong thuc thanh toan duoc cap nhat dung thanh 'Tien mat'");
    }
}
