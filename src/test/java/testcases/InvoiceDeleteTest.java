package testcases;

import common.Constant;
import common.Utilities;
import dataobjects.InvoiceDeleteData;
import org.testng.Assert;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;
import pageobjects.InvoiceDeletePage;

/**
 * Test Cases cho module Xóa hóa đơn
 * Tổng cộng: 2 test cases
 */
public class InvoiceDeleteTest extends BaseTest {
    private InvoiceDeletePage invoiceDeletePage;

    @BeforeMethod
    @Override
    public void setUp() {
        super.setUp();
        invoiceDeletePage = new InvoiceDeletePage(driver, wait);
        login();
        navigateToSalesPage();
    }

    @Test(description = "TC-01: Xóa hóa đơn bán thành công")
    public void TC01() {
        Utilities.logStep("=== TC-01: XÓA HÓA ĐƠN THÀNH CÔNG ===");
        Utilities.log("⚠️ Test này sẽ chọn hóa đơn CUỐI CÙNG để xóa");
        Utilities.log("⚠️ Không xóa hóa đơn đầu tiên để bảo vệ dữ liệu test");
        
        // Kiểm tra số lượng hóa đơn
        int totalInvoices = invoiceDeletePage.getTotalInvoices();
        Utilities.log("Tổng số hóa đơn: " + totalInvoices);
        
        if (totalInvoices < 2) {
            Utilities.log("⚠️ SKIP: Chỉ có " + totalInvoices + " hóa đơn");
            Utilities.log("⚠️ Cần ít nhất 2 hóa đơn để test an toàn");
            throw new org.testng.SkipException("Không đủ hóa đơn để test");
        }
        
        // Test data - Chọn hóa đơn cuối cùng
        int invoiceIndexToDelete = totalInvoices - 1;
        InvoiceDeleteData data = new InvoiceDeleteData.Builder()
                .invoiceIndex(invoiceIndexToDelete)
                .shouldDelete(true)
                .build();
        
        Utilities.log("Sẽ xóa hóa đơn thứ " + (invoiceIndexToDelete + 1));
        
        // Steps
        // Bước 1: Xem chi tiết hóa đơn cuối cùng
        invoiceDeletePage.clickInvoiceRow(data.getInvoiceIndex());
        
        // Lưu mã hóa đơn
        String invoiceCode = invoiceDeletePage.getInvoiceCode();
        Utilities.log("Mã hóa đơn cần xóa: " + invoiceCode);
        
        // Bước 2: Click nút Xóa
        invoiceDeletePage.clickDeleteButton();
        
        // Bước 3: Kiểm tra popup xác nhận
        boolean isPopupDisplayed = invoiceDeletePage.isConfirmPopupDisplayed();
        Assert.assertTrue(isPopupDisplayed, "Popup xác nhận phải hiển thị");
        
        String confirmMessage = invoiceDeletePage.getConfirmMessage();
        Utilities.log("Nội dung popup: " + confirmMessage);
        Assert.assertTrue(
            confirmMessage.toLowerCase().contains("xóa") || 
            confirmMessage.toLowerCase().contains("xoá") ||
            confirmMessage.toLowerCase().contains("xác nhận"),
            "Popup phải chứa nội dung xác nhận xóa");
        
        // Bước 4: Click Đồng ý
        invoiceDeletePage.clickConfirmButton();
        
        // Bước 5: Kiểm tra thông báo thành công (nếu có)
        String successMessage = invoiceDeletePage.getSuccessMessage();
        Utilities.log("Thông báo: " + successMessage);
        
        // Hệ thống có thể hiển thị message hoặc redirect trực tiếp
        if (!successMessage.isEmpty()) {
            Utilities.log("✅ Có thông báo xóa thành công");
            Assert.assertTrue(
                successMessage.contains("Xóa thành công") || 
                successMessage.contains("xóa thành công") ||
                successMessage.contains("Đã xóa"),
                "Thông báo phải chứa 'Xóa thành công'");
        } else {
            Utilities.log("⚠️ Không có thông báo - Hệ thống có thể redirect trực tiếp");
            // Không có message cũng OK, sẽ kiểm tra hóa đơn đã xóa ở bước sau
        }
        
        // Bước 6: Kiểm tra hóa đơn đã bị xóa
        navigateToSalesPage();
        Utilities.sleep(2000);
        
        boolean isDeleted = invoiceDeletePage.isInvoiceDeleted(invoiceCode);
        Assert.assertTrue(isDeleted, 
            "Hóa đơn " + invoiceCode + " phải không còn trong danh sách");
        
        Utilities.log("✅ TC-01 PASSED: Đã xóa hóa đơn " + invoiceCode);
    }

    @Test(description = "TC-02: Hủy xóa hóa đơn")
    public void TC02() {
        Utilities.logStep("=== TC-02: HỦY XÓA HÓA ĐƠN ===");
        
        // Test data
        InvoiceDeleteData data = new InvoiceDeleteData.Builder()
                .invoiceIndex(0)
                .shouldDelete(false)
                .build();
        
        // Steps
        // Bước 1: Xem chi tiết hóa đơn
        invoiceDeletePage.clickInvoiceRow(data.getInvoiceIndex());
        
        // Lưu mã hóa đơn
        String invoiceCode = invoiceDeletePage.getInvoiceCode();
        Utilities.log("Mã hóa đơn: " + invoiceCode);
        
        // Bước 2: Click nút Xóa
        invoiceDeletePage.clickDeleteButton();
        
        // Bước 3: Kiểm tra popup xác nhận
        boolean isPopupDisplayed = invoiceDeletePage.isConfirmPopupDisplayed();
        Assert.assertTrue(isPopupDisplayed, "Popup xác nhận phải hiển thị");
        
        String confirmMessage = invoiceDeletePage.getConfirmMessage();
        Utilities.log("Nội dung popup: " + confirmMessage);
        Assert.assertTrue(
            confirmMessage.toLowerCase().contains("xóa") || 
            confirmMessage.toLowerCase().contains("xoá") ||
            confirmMessage.toLowerCase().contains("xác nhận"),
            "Popup phải chứa nội dung xác nhận xóa");
        
        // Bước 4: Click Hủy
        invoiceDeletePage.clickCancelButton();
        
        // Bước 5: Kiểm tra không có thông báo xóa thành công
        String successMessage = invoiceDeletePage.getSuccessMessage();
        Utilities.log("Thông báo (nếu có): " + successMessage);
        
        if (!successMessage.isEmpty()) {
            Assert.assertFalse(
                successMessage.contains("Xóa thành công") || 
                successMessage.contains("xóa thành công"),
                "Không được hiển thị thông báo xóa thành công");
        }
        
        // Bước 6: Kiểm tra vẫn ở màn hình chi tiết
        boolean isDetailsVisible = invoiceDeletePage.isInvoiceDetailsVisible();
        Assert.assertTrue(isDetailsVisible, 
            "Phải vẫn hiển thị màn hình chi tiết hóa đơn");
        
        // Bước 7: Kiểm tra hóa đơn vẫn tồn tại
        navigateToSalesPage();
        Utilities.sleep(2000);
        
        boolean isExists = invoiceDeletePage.isInvoiceExists(invoiceCode);
        Assert.assertTrue(isExists, 
            "Hóa đơn " + invoiceCode + " phải vẫn tồn tại trong danh sách");
        
        Utilities.log("✅ TC-02 PASSED: Hóa đơn " + invoiceCode + " vẫn tồn tại");
    }
}
