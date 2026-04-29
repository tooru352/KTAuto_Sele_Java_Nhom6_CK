package org.example;

import org.openqa.selenium.By;
import org.testng.Assert;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

public class InvoiceDeleteTest extends BaseTest {
    private InvoiceDeletePage deletePage;

    @BeforeMethod
    @Override
    public void setUp() {
        super.setUp();
        deletePage = new InvoiceDeletePage(driver, wait);
        login();
        navigateToSalesPage();
    }

    @Test(description = "TC-01: Xóa hóa đơn bán thành công")
    public void testDeleteInvoiceSuccess() {
        System.out.println("=== TC-01: XÓA HÓA ĐƠN THÀNH CÔNG ===");
        System.out.println("⚠️ LƯU Ý: Test này sẽ chọn hóa đơn CUỐI CÙNG trong danh sách để xóa");
        System.out.println("⚠️ Không xóa hóa đơn đầu tiên (HD015) để không ảnh hưởng test khác");
        
        // Bước 0: Đếm số hóa đơn trong danh sách
        int totalInvoices = deletePage.getTotalInvoices();
        System.out.println("Tổng số hóa đơn trong danh sách: " + totalInvoices);
        
        if (totalInvoices < 2) {
            System.out.println("⚠️ CẢNH BÁO: Chỉ có " + totalInvoices + " hóa đơn trong danh sách!");
            System.out.println("⚠️ Khuyến nghị: Tạo thêm hóa đơn để test không ảnh hưởng dữ liệu khác");
            System.out.println("⚠️ Test sẽ SKIP để bảo vệ dữ liệu");
            throw new org.testng.SkipException("Không đủ hóa đơn để test xóa an toàn");
        }
        
        // Chọn hóa đơn CUỐI CÙNG để xóa (không phải hóa đơn đầu tiên)
        int invoiceIndexToDelete = totalInvoices - 1;
        System.out.println("Sẽ xóa hóa đơn thứ " + (invoiceIndexToDelete + 1) + " (index: " + invoiceIndexToDelete + ")");
        
        // Bước 1: Xem chi tiết hóa đơn CUỐI CÙNG
        System.out.println("Bước 1: Click vào hóa đơn CUỐI CÙNG để xem chi tiết");
        deletePage.clickViewDetails(invoiceIndexToDelete);
        
        // Lưu lại mã hóa đơn để kiểm tra sau khi xóa
        String invoiceCode = deletePage.getInvoiceCode();
        System.out.println("Mã hóa đơn cần xóa: " + invoiceCode);
        
        // Bước 2: Nhấn nút "Xóa"
        System.out.println("Bước 2: Nhấn nút 'Xóa'");
        deletePage.clickDeleteButton();
        
        // Bước 3: Kiểm tra popup xác nhận xuất hiện
        System.out.println("Bước 3: Kiểm tra popup xác nhận");
        boolean isConfirmPopupDisplayed = deletePage.isConfirmPopupDisplayed();
        Assert.assertTrue(isConfirmPopupDisplayed, 
            "Popup xác nhận phải hiển thị");
        
        String confirmMessage = deletePage.getConfirmMessage();
        System.out.println("Nội dung popup title: " + confirmMessage);
        // Kiểm tra popup có chứa từ liên quan đến xóa (xóa, Xoá, delete, xác nhận)
        Assert.assertTrue(
            confirmMessage.toLowerCase().contains("xóa") || 
            confirmMessage.toLowerCase().contains("xoá") || 
            confirmMessage.toLowerCase().contains("delete") ||
            confirmMessage.toLowerCase().contains("xác nhận"), 
            "Popup phải chứa nội dung xác nhận xóa. Nội dung thực tế: " + confirmMessage);
        
        // Bước 4: Nhấn nút "Đồng ý"
        System.out.println("Bước 4: Nhấn nút 'Đồng ý' để xác nhận xóa");
        deletePage.clickConfirmButton();
        
        // Chờ xử lý xóa
        try {
            Thread.sleep(2000);
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }
        
        // Bước 5: Kiểm tra thông báo xóa thành công (nếu có)
        System.out.println("Bước 5: Kiểm tra thông báo xóa thành công (nếu có)");
        String successMessage = deletePage.getSuccessMessage();
        System.out.println("Thông báo: " + successMessage);
        
        // Hệ thống có thể hiển thị message hoặc redirect trực tiếp
        if (!successMessage.isEmpty()) {
            System.out.println("✅ Có thông báo xóa thành công");
            Assert.assertTrue(successMessage.contains("Xóa thành công") || 
                             successMessage.contains("xóa thành công") ||
                             successMessage.contains("Đã xóa"), 
                "Thông báo phải chứa 'Xóa thành công'");
        } else {
            System.out.println("⚠️ Không có thông báo - Hệ thống có thể redirect trực tiếp");
            System.out.println("⚠️ Sẽ kiểm tra hóa đơn đã bị xóa ở bước tiếp theo");
        }
        
        // Bước 6: Kiểm tra hóa đơn không còn trong danh sách (QUAN TRỌNG NHẤT)
        System.out.println("Bước 6: Kiểm tra hóa đơn đã bị xóa khỏi danh sách");
        
        // Quay về trang danh sách hóa đơn
        navigateToSalesPage();
        
        // Chờ trang load
        try {
            Thread.sleep(2000);
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }
        
        // Kiểm tra hóa đơn không còn tồn tại
        boolean isInvoiceDeleted = deletePage.isInvoiceDeleted(invoiceCode);
        Assert.assertTrue(isInvoiceDeleted, 
            "Hóa đơn " + invoiceCode + " phải không còn trong danh sách sau khi xóa");
        
        System.out.println("✅ TC-01 PASSED: Xóa hóa đơn thành công!");
    }

    @Test(description = "TC-02: Hủy xóa hóa đơn")
    public void testCancelDeleteInvoice() {
        System.out.println("=== TC-02: HỦY XÓA HÓA ĐƠN ===");
        
        // Bước 1: Xem chi tiết hóa đơn
        System.out.println("Bước 1: Click vào hóa đơn để xem chi tiết");
        deletePage.clickViewDetails(0);
        
        // Lưu lại mã hóa đơn để kiểm tra sau
        String invoiceCode = deletePage.getInvoiceCode();
        System.out.println("Mã hóa đơn: " + invoiceCode);
        
        // Bước 2: Nhấn nút "Xóa"
        System.out.println("Bước 2: Nhấn nút 'Xóa'");
        deletePage.clickDeleteButton();
        
        // Bước 3: Kiểm tra popup xác nhận xuất hiện
        System.out.println("Bước 3: Kiểm tra popup xác nhận");
        boolean isConfirmPopupDisplayed = deletePage.isConfirmPopupDisplayed();
        Assert.assertTrue(isConfirmPopupDisplayed, 
            "Popup xác nhận phải hiển thị");
        
        String confirmMessage = deletePage.getConfirmMessage();
        System.out.println("Nội dung popup title: " + confirmMessage);
        // Kiểm tra popup có chứa từ liên quan đến xóa (xóa, Xoá, delete, xác nhận)
        Assert.assertTrue(
            confirmMessage.toLowerCase().contains("xóa") || 
            confirmMessage.toLowerCase().contains("xoá") || 
            confirmMessage.toLowerCase().contains("delete") ||
            confirmMessage.toLowerCase().contains("xác nhận"), 
            "Popup phải chứa nội dung xác nhận xóa. Nội dung thực tế: " + confirmMessage);
        
        // Bước 4: Nhấn nút "Hủy"
        System.out.println("Bước 4: Nhấn nút 'Hủy' để hủy xóa");
        deletePage.clickCancelButton();
        
        // Chờ popup đóng
        try {
            Thread.sleep(2000);
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }
        
        // Bước 5: Kiểm tra hệ thống không xóa hóa đơn
        System.out.println("Bước 5: Kiểm tra hệ thống không xóa hóa đơn");
        
        // Kiểm tra không có thông báo xóa thành công
        String successMessage = deletePage.getSuccessMessage();
        System.out.println("Thông báo (nếu có): " + successMessage);
        
        // Không được có thông báo "Xóa thành công"
        if (!successMessage.isEmpty()) {
            Assert.assertFalse(successMessage.contains("Xóa thành công") || 
                              successMessage.contains("xóa thành công") ||
                              successMessage.contains("Đã xóa"), 
                "Không được hiển thị thông báo xóa thành công khi nhấn Hủy");
        }
        
        // Bước 6: Kiểm tra vẫn ở màn hình chi tiết hóa đơn
        System.out.println("Bước 6: Kiểm tra vẫn ở màn hình chi tiết hóa đơn");
        String currentUrl = driver.getCurrentUrl();
        System.out.println("URL hiện tại: " + currentUrl);
        
        // Kiểm tra vẫn có thể thấy thông tin hóa đơn
        boolean isInvoiceDetailsVisible = deletePage.isInvoiceDetailsVisible();
        Assert.assertTrue(isInvoiceDetailsVisible, 
            "Phải vẫn hiển thị màn hình chi tiết hóa đơn sau khi nhấn Hủy");
        
        // Bước 7: Quay về danh sách và kiểm tra hóa đơn vẫn tồn tại
        System.out.println("Bước 7: Quay về danh sách và kiểm tra hóa đơn vẫn tồn tại");
        navigateToSalesPage();
        
        // Chờ trang load
        try {
            Thread.sleep(2000);
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }
        
        // Kiểm tra hóa đơn vẫn còn trong danh sách
        boolean isInvoiceStillExists = deletePage.isInvoiceExists(invoiceCode);
        Assert.assertTrue(isInvoiceStillExists, 
            "Hóa đơn " + invoiceCode + " phải vẫn tồn tại trong danh sách sau khi hủy xóa");
        
        System.out.println("✅ TC-02 PASSED: Hủy xóa hóa đơn thành công!");
    }
}
