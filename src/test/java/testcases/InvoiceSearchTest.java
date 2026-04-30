package testcases;

import org.testng.Assert;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;
import pageobjects.InvoiceDetailPage;
import pageobjects.SalesPage;

/**
 * Test Cases cho module Tìm kiếm hóa đơn
 * Tổng cộng: 7 test cases
 */
public class InvoiceSearchTest extends BaseTest {
    private SalesPage salesPage;
    private InvoiceDetailPage detailPage;

    @BeforeMethod
    @Override
    public void setUp() {
        super.setUp();
        salesPage = new SalesPage(driver, wait);
        detailPage = new InvoiceDetailPage(driver, wait);
        login();
        navigateToSalesPage();
    }

    @Test(description = "TC-01: Tìm kiếm hóa đơn theo mã hợp lệ")
    public void TC01() {
        System.out.println("TC-01 - Tìm kiếm hóa đơn theo mã hợp lệ");
        
        salesPage.searchInvoice("HD001");
        salesPage.clickSearchButton();
        
        try {
            Thread.sleep(2000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        
        Assert.assertTrue(salesPage.isInvoiceInTable("HD001"), 
            "Hệ thống không hiển thị đúng hóa đơn HD001");
        
        System.out.println("✓ TC-01 PASS: Hệ thống hiển thị đúng hóa đơn HD001");
    }

    @Test(description = "TC-02: Tìm kiếm mã hóa đơn không tồn tại")
    public void TC02() {
        System.out.println("TC-02 - Tìm kiếm mã hóa đơn không tồn tại");
        
        salesPage.searchInvoice("HD999");
        salesPage.clickSearchButton();
        
        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        
        boolean hasNoResultMessage = salesPage.isNoResultMessageDisplayed();
        int invoiceCount = salesPage.getInvoiceCount();
        
        Assert.assertTrue(hasNoResultMessage && invoiceCount == 0, 
            "Hệ thống không hiển thị thông báo không tìm thấy hoặc danh sách không trống");
        
        System.out.println("✓ TC-02 PASS: Hệ thống hiển thị thông báo 'Không tìm thấy hóa đơn khớp với HD999', danh sách trống");
    }

    @Test(description = "TC-03: Tìm kiếm với mã hóa đơn viết thường")
    public void TC03() {
        System.out.println("TC-03 - Tìm kiếm với mã hóa đơn viết thường");
        
        salesPage.searchInvoice("hd001");
        salesPage.clickSearchButton();
        
        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        
        Assert.assertTrue(salesPage.isInvoiceInTable("HD001"), 
            "Hệ thống không tìm thấy HD001 khi tìm kiếm với chữ thường");
        
        System.out.println("✓ TC-03 PASS: Hệ thống tìm thấy HD001");
    }

    @Test(description = "TC-04: Tìm kiếm với mã hóa đơn có khoảng trắng")
    public void TC04() {
        System.out.println("TC-04 - Tìm kiếm với mã hóa đơn có khoảng trắng");
        
        salesPage.searchInvoice(" HD001 ");
        salesPage.clickSearchButton();
        
        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        
        boolean hasNoResultMessage = salesPage.isNoResultMessageDisplayed();
        int invoiceCount = salesPage.getInvoiceCount();
        
        Assert.assertTrue(hasNoResultMessage && invoiceCount == 0, 
            "Hệ thống không hiển thị thông báo không tìm thấy hoặc danh sách không trống");
        
        System.out.println("✓ TC-04 PASS: Hệ thống hiển thị thông báo 'Không tìm thấy hóa đơn khớp với  HD001 ', danh sách trống");
    }

    @Test(description = "TC-05: Tìm kiếm với mã hóa đơn một phần")
    public void TC05() {
        System.out.println("TC-05 - Tìm kiếm với mã hóa đơn một phần");
        
        salesPage.searchInvoice("15");
        salesPage.clickSearchButton();
        
        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        
        int invoiceCount = salesPage.getInvoiceCount();
        boolean foundHD015 = salesPage.isInvoiceInTable("HD015");
        
        Assert.assertTrue(invoiceCount > 0 && foundHD015, 
            "Hệ thống không hiển thị hóa đơn chứa '15' như HD015");
        
        System.out.println("✓ TC-05 PASS: Hệ thống hiển thị tất cả hóa đơn có mã chứa '15'");
    }

    @Test(description = "TC-06: Click vào dòng hóa đơn để xem chi tiết")
    public void TC06() {
        System.out.println("TC-06 - Click vào dòng hóa đơn để xem chi tiết");
        
        salesPage.clickInvoiceRow("HD001");
        
        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        
        Assert.assertTrue(detailPage.isModalDisplayed(), 
            "Modal chi tiết hóa đơn không hiển thị");
        
        String invoiceCode = detailPage.getInvoiceCode();
        Assert.assertTrue(invoiceCode.contains("HD001"), 
            "Chi tiết không phải của hóa đơn HD001");
        
        System.out.println("✓ TC-06 PASS: Hiển thị chi tiết hóa đơn HD001");
        
        detailPage.closeModal();
    }

    @Test(description = "TC-07: Kiểm tra cột hiển thị trong bảng sản phẩm")
    public void TC07() {
        System.out.println("TC-07 - Kiểm tra cột hiển thị trong bảng sản phẩm");
        
        salesPage.clickInvoiceRow("HD001");
        
        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        
        boolean hasCorrectColumns = detailPage.hasCorrectColumnStructure();
        
        Assert.assertTrue(hasCorrectColumns, 
            "Bảng sản phẩm không có đủ các cột yêu cầu");
        
        System.out.println("✓ TC-07 PASS: Bảng có các cột: Mã hàng hóa, Tên hàng hóa, Số lượng, Đơn giá, Thành tiền");
        
        detailPage.closeModal();
    }
}
