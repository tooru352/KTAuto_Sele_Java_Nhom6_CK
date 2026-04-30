package testcases;

import org.testng.Assert;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;
import pageobjects.PurchaseInvoiceSearchPage;
import pageobjects.LoginPage;
import dataobjects.SearchData;
import dataobjects.LoginData;

public class PurchaseInvoiceSearchTest extends BaseTest {
    private PurchaseInvoiceSearchPage purchaseInvoiceSearchPage;
    private LoginPage loginPage;

    @BeforeMethod
    public void setUpTest() {
        // Đăng nhập trước
        loginPage = new LoginPage(driver);
        loginPage.open();
        loginPage.login(LoginData.VALID_USERNAME, LoginData.VALID_PASSWORD);
        
        // Sau đó vào trang "Nhập hàng"
        purchaseInvoiceSearchPage = new PurchaseInvoiceSearchPage(driver);
        purchaseInvoiceSearchPage.openImportPage();
    }

    @Test(description = "TC_01: Kiểm tra tìm kiếm theo tên NCC")
    public void testSearchBySupplierName() {
        // TC_01: Kiểm tra tìm kiếm theo tên NCC
        // Expected: Không có kết quả. Bảng không có dòng dữ liệu và hiển thị thông báo "Không tìm thấy kết quả cho "Kim Đồng"
        // Actual: Hệ thống hiển thị kết quả (không tuân theo business rule)
        // Business Rule: Hệ thống chỉ hỗ trợ tìm kiếm theo Mã đơn nhập hàng hoặc Ngày tạo hóa đơn
        // Status: FAIL
        
        System.out.println("=== TC_01: Kiem tra tim kiem theo ten NCC ===");
        
        purchaseInvoiceSearchPage.enterSearchKeyword(SearchData.SUPPLIER_NAME_KIM_DONG);
        System.out.println("Da nhap tu khoa: " + SearchData.SUPPLIER_NAME_KIM_DONG);
        
        purchaseInvoiceSearchPage.clickSearchButton();
        System.out.println("Da click nut Tim kiem");
        
        // Kiểm tra kết quả
        boolean isTableEmpty = purchaseInvoiceSearchPage.isTableEmpty();
        boolean isResultsDisplayed = purchaseInvoiceSearchPage.isResultsDisplayed();
        
        System.out.println("Bảng rỗng: " + isTableEmpty);
        System.out.println("Kết quả được hiển thị: " + isResultsDisplayed);
        
        // Expected: Bảng rỗng (không có kết quả)
        // Actual: Bảng không rỗng (hệ thống hiển thị kết quả - không đúng business rule)
        Assert.assertTrue(isTableEmpty,
            "Bảng không có dòng dữ liệu (hệ thống không hỗ trợ tìm kiếm theo tên NCC)");
        
        System.out.println("TC_01 PASSED - Hệ thống không hỗ trợ tìm kiếm theo tên NCC (không theo business rule)");
    }

    @Test(description = "TC_02: Kiểm tra tìm kiếm theo mã hàng hóa")
    public void testSearchByInvoiceCode() {
        // TC_02: Kiểm tra tìm kiếm theo mã hàng hóa
        // Expected: Hiển thị các hóa đơn có mã là HDN01
        // Actual: Hệ thống lọc đúng theo mã đơn nhập HDN01. Chỉ hiển thị hóa đơn phù hợp, các hóa đơn khác bị ẩn
        // Status: PASS
        
        System.out.println("=== TC_02: Kiem tra tim kiem theo ma hoa don ===");
        
        purchaseInvoiceSearchPage.enterSearchKeyword(SearchData.INVOICE_CODE_HDN01);
        System.out.println("Da nhap tu khoa: " + SearchData.INVOICE_CODE_HDN01);
        
        purchaseInvoiceSearchPage.clickSearchButton();
        System.out.println("Da click nut Tim kiem");
        
        // Kiểm tra kết quả
        boolean isResultsDisplayed = purchaseInvoiceSearchPage.isResultsDisplayedForInvoiceCode(SearchData.INVOICE_CODE_HDN01);
        boolean isOtherInvoicesHidden = purchaseInvoiceSearchPage.isOtherInvoicesHidden();
        
        System.out.println("Kết quả được hiển thị: " + isResultsDisplayed);
        System.out.println("Các hóa đơn khác bị ẩn: " + isOtherInvoicesHidden);
        
        Assert.assertTrue(isResultsDisplayed,
            "Hiển thị các hóa đơn có mã là HDN01");
        Assert.assertTrue(isOtherInvoicesHidden,
            "Chỉ hiển thị hóa đơn phù hợp, các hóa đơn khác bị ẩn");
        
        System.out.println("TC_02 PASSED");
    }

    @Test(description = "TC_03: Tìm kiếm từ khóa một phần")
    public void testSearchPartialKeyword() {
        // TC_03: Tìm kiếm từ khóa một phần
        // Expected: Không có kết quả. Bảng không có dòng dữ liệu và hiển thị thông báo "Không tìm thấy kết quả cho "kim"
        // Actual: Hệ thống hiển thị kết quả (không tuân theo business rule)
        // Business Rule: Hệ thống chỉ hỗ trợ tìm kiếm theo Mã đơn nhập hàng hoặc Ngày tạo hóa đơn
        // Status: FAIL
        
        System.out.println("=== TC_03: Tim kiem tu khoa mot phan ===");
        
        purchaseInvoiceSearchPage.enterSearchKeyword(SearchData.PARTIAL_KEYWORD_KIM);
        System.out.println("Da nhap tu khoa: " + SearchData.PARTIAL_KEYWORD_KIM);
        
        purchaseInvoiceSearchPage.clickSearchButton();
        System.out.println("Da click nut Tim kiem");
        
        // Kiểm tra kết quả
        boolean isTableEmpty = purchaseInvoiceSearchPage.isTableEmpty();
        boolean isResultsDisplayed = purchaseInvoiceSearchPage.isResultsDisplayed();
        
        System.out.println("Bảng rỗng: " + isTableEmpty);
        System.out.println("Kết quả được hiển thị: " + isResultsDisplayed);
        
        // Expected: Bảng rỗng (không có kết quả)
        // Actual: Bảng không rỗng (hệ thống hiển thị kết quả - không đúng business rule)
        Assert.assertTrue(isTableEmpty,
            "Bảng không có dòng dữ liệu (hệ thống không hỗ trợ tìm kiếm từ khóa một phần)");
        
        System.out.println("TC_03 PASSED - Hệ thống không hỗ trợ tìm kiếm từ khóa một phần (không theo business rule)");
    }

    @Test(description = "TC_04: Tìm kiếm từ khóa không tồn tại")
    public void testSearchNonExistentKeyword() {
        // TC_04: Tìm kiếm từ khóa không tồn tại
        // Expected: Không có kết quả. Bảng không có dòng dữ liệu và hiển thị thông báo "Không tìm thấy kết quả cho "xyz123"
        // Actual: Hệ thống không tìm thấy dữ liệu phù hợp. Bảng không có dòng dữ liệu và hiển thị thông báo "Không tìm thấy kết quả cho "xyz123"
        // Status: PASS
        
        System.out.println("=== TC_04: Tim kiem tu khoa khong ton tai ===");
        
        purchaseInvoiceSearchPage.enterSearchKeyword(SearchData.NON_EXISTENT_KEYWORD);
        System.out.println("Da nhap tu khoa: " + SearchData.NON_EXISTENT_KEYWORD);
        
        purchaseInvoiceSearchPage.clickSearchButton();
        System.out.println("Da click nut Tim kiem");
        
        // Kiểm tra kết quả
        boolean isTableEmpty = purchaseInvoiceSearchPage.isTableEmpty();
        boolean isNoResultsMessageDisplayed = purchaseInvoiceSearchPage.isNoResultsMessageDisplayed(SearchData.NON_EXISTENT_KEYWORD);
        
        System.out.println("Bảng rỗng: " + isTableEmpty);
        System.out.println("Thông báo không tìm thấy: " + isNoResultsMessageDisplayed);
        
        Assert.assertFalse(purchaseInvoiceSearchPage.isResultsDisplayed(),
            "Không có kết quả");
        Assert.assertTrue(isTableEmpty,
            "Bảng không có dòng dữ liệu");
        Assert.assertTrue(isNoResultsMessageDisplayed,
            "Hiển thị thông báo 'Không tìm thấy kết quả cho xyz123'");
        
        System.out.println("TC_04 PASSED");
    }

    @Test(description = "TC_05: Tìm kiếm với từ khóa rỗng")
    public void testSearchWithEmptyKeyword() {
        // TC_05: Tìm kiếm với từ khóa rỗng
        // Expected: Hiển thị tất cả hóa đơn. Không áp dụng filter
        // Actual: Hệ thống không áp dụng điều kiện lọc và hiển thị đầy đủ toàn bộ danh sách hóa đơn nhập hàng
        // Status: PASS
        
        System.out.println("=== TC_05: Tim kiem voi tu khoa rong ===");
        
        purchaseInvoiceSearchPage.enterSearchKeyword("");
        System.out.println("Da nhap tu khoa rong");
        
        purchaseInvoiceSearchPage.clickSearchButton();
        System.out.println("Da click nut Tim kiem");
        
        // Kiểm tra kết quả
        boolean isAllInvoicesDisplayed = purchaseInvoiceSearchPage.isAllInvoicesDisplayed();
        boolean isFilterApplied = purchaseInvoiceSearchPage.isFilterApplied();
        
        System.out.println("Tất cả hóa đơn được hiển thị: " + isAllInvoicesDisplayed);
        System.out.println("Filter được áp dụng: " + isFilterApplied);
        
        Assert.assertTrue(isAllInvoicesDisplayed,
            "Hiển thị tất cả hóa đơn");
        Assert.assertFalse(isFilterApplied,
            "Không áp dụng filter");
        
        System.out.println("TC_05 PASSED");
    }


    @Test(description = "TC_06: Tìm kiếm theo ngày tạo")
    public void testSearchByCreationDate() {
        // TC_07: Tìm kiếm theo ngày tạo
        // Expected: Hiển thị hóa đơn đúng ngày
        // Actual: Hệ thống lọc đúng các hóa đơn có ngày tạo 04/04/2026, các ngày khác bị ẩn
        // Status: PASS
        
        System.out.println("=== TC_07: Tim kiem theo ngay tao ===");
        
        purchaseInvoiceSearchPage.enterSearchKeyword(SearchData.CREATION_DATE_04_04_2026);
        System.out.println("Da nhap tu khoa: " + SearchData.CREATION_DATE_04_04_2026);
        
        purchaseInvoiceSearchPage.clickSearchButton();
        System.out.println("Da click nut Tim kiem");
        
        // Kiểm tra kết quả
        boolean isResultsDisplayedForDate = purchaseInvoiceSearchPage.isResultsDisplayedForDate(SearchData.CREATION_DATE_04_04_2026);
        boolean isOtherDatesHidden = purchaseInvoiceSearchPage.isOtherDatesHidden();
        
        System.out.println("Kết quả được hiển thị: " + isResultsDisplayedForDate);
        System.out.println("Các ngày khác bị ẩn: " + isOtherDatesHidden);
        
        Assert.assertTrue(isResultsDisplayedForDate,
            "Hiển thị hóa đơn đúng ngày");
        Assert.assertTrue(isOtherDatesHidden,
            "Các ngày khác bị ẩn");
        
        System.out.println("TC_07 PASSED");
    }

    @Test(description = "TC_07: Tìm kiếm theo trạng thái")
    public void testSearchByStatus() {
        // TC_08: Tìm kiếm theo trạng thái
        // Expected: Không có kết quả. Bảng không có dòng dữ liệu và hiển thị thông báo "Không tìm thấy kết quả cho "Hoàn thành"
        // Actual: Hệ thống chỉ hiển thị các hóa đơn có trạng thái "Hoàn thành", các trạng thái khác không hiển thị
        // Tuy nhiên chức năng đang tìm kiếm theo tên nhà cung cấp, không đúng business rule chỉ hỗ trợ tìm theo Mã đơn nhập hàng hoặc Ngày tạo hóa đơn
        // Status: FAIL
        
        System.out.println("=== TC_08: Tim kiem theo trang thai ===");
        
        purchaseInvoiceSearchPage.enterSearchKeyword(SearchData.STATUS_COMPLETED);
        System.out.println("Da nhap tu khoa: " + SearchData.STATUS_COMPLETED);
        
        purchaseInvoiceSearchPage.clickSearchButton();
        System.out.println("Da click nut Tim kiem");
        
        // Kiểm tra kết quả
        boolean isTableEmpty = purchaseInvoiceSearchPage.isTableEmpty();
        boolean isNoResultsMessageDisplayed = purchaseInvoiceSearchPage.isNoResultsMessageDisplayed(SearchData.STATUS_COMPLETED);
        
        System.out.println("Bảng rỗng: " + isTableEmpty);
        System.out.println("Thông báo không tìm thấy: " + isNoResultsMessageDisplayed);
        
        // Expected: Không có kết quả
        Assert.assertTrue(isTableEmpty,
            "Bảng không có dòng dữ liệu");
        Assert.assertTrue(isNoResultsMessageDisplayed,
            "Hiển thị thông báo 'Không tìm thấy kết quả cho Hoàn thành'");
        
        System.out.println("TC_08 PASSED - Hệ thống không hỗ trợ tìm kiếm theo trạng thái");
    }

    @Test(description = "TC_08: Tìm kiếm từ khóa in hoa")
    public void testSearchUppercaseKeyword() {
        // TC_09: Tìm kiếm từ khóa in hoa
        // Expected: Không có kết quả. Bảng không có dòng dữ liệu và hiển thị thông báo "Không tìm thấy kết quả cho "ABCXYZ"
        // Actual: Hệ thống không tìm thấy dữ liệu phù hợp. Bảng không có dòng dữ liệu và hiển thị thông báo "Không tìm thấy kết quả cho "ABCXYZ"
        // Status: PASS
        
        System.out.println("=== TC_09: Tim kiem tu khoa in hoa ===");
        
        purchaseInvoiceSearchPage.enterSearchKeyword(SearchData.UPPERCASE_KEYWORD);
        System.out.println("Da nhap tu khoa: " + SearchData.UPPERCASE_KEYWORD);
        
        purchaseInvoiceSearchPage.clickSearchButton();
        System.out.println("Da click nut Tim kiem");
        
        // Kiểm tra kết quả
        boolean isTableEmpty = purchaseInvoiceSearchPage.isTableEmpty();
        boolean isNoResultsMessageDisplayed = purchaseInvoiceSearchPage.isNoResultsMessageDisplayed(SearchData.UPPERCASE_KEYWORD);
        
        System.out.println("Bảng rỗng: " + isTableEmpty);
        System.out.println("Thông báo không tìm thấy: " + isNoResultsMessageDisplayed);
        
        Assert.assertFalse(purchaseInvoiceSearchPage.isResultsDisplayed(),
            "Không có kết quả");
        Assert.assertTrue(isTableEmpty,
            "Bảng không có dòng dữ liệu");
        Assert.assertTrue(isNoResultsMessageDisplayed,
            "Hiển thị thông báo 'Không tìm thấy kết quả cho ABCXYZ'");
        
        System.out.println("TC_09 PASSED");
    }

    @Test(description = "TC_9: Tìm kiếm theo số lượng nhập")
    public void testSearchByQuantity() {
        // TC_10: Tìm kiếm theo số lượng nhập
        // Expected: Không có kết quả. Bảng không có dòng dữ liệu và hiển thị thông báo "Không tìm thấy kết quả cho "163"
        // Actual: Hệ thống chỉ hiển thị các hóa đơn có số lượng "163", các số lượng khác không hiển thị
        // Tuy nhiên chức năng đang tìm kiếm theo tên nhà cung cấp, không đúng business rule chỉ hỗ trợ tìm theo Mã đơn nhập hàng hoặc Ngày tạo hóa đơn
        // Status: FAIL
        
        System.out.println("=== TC_10: Tim kiem theo so luong nhap ===");
        
        purchaseInvoiceSearchPage.enterSearchKeyword(SearchData.QUANTITY_163);
        System.out.println("Da nhap tu khoa: " + SearchData.QUANTITY_163);
        
        purchaseInvoiceSearchPage.clickSearchButton();
        System.out.println("Da click nut Tim kiem");
        
        // Kiểm tra kết quả
        boolean isTableEmpty = purchaseInvoiceSearchPage.isTableEmpty();
        boolean isNoResultsMessageDisplayed = purchaseInvoiceSearchPage.isNoResultsMessageDisplayed(SearchData.QUANTITY_163);
        
        System.out.println("Bảng rỗng: " + isTableEmpty);
        System.out.println("Thông báo không tìm thấy: " + isNoResultsMessageDisplayed);
        
        // Expected: Không có kết quả
        Assert.assertFalse(purchaseInvoiceSearchPage.isResultsDisplayed(),
            "Không có kết quả");
        Assert.assertTrue(isTableEmpty,
            "Bảng không có dòng dữ liệu");
        Assert.assertTrue(isNoResultsMessageDisplayed,
            "Hiển thị thông báo 'Không tìm thấy kết quả cho 163'");
        
        System.out.println("TC_10 PASSED - Hệ thống không hỗ trợ tìm kiếm theo số lượng");
    }

    @Test(description = "TC_10: Tìm kiếm theo giá trị đơn hàng")
    public void testSearchByOrderValue() {
        // TC_11: Tìm kiếm theo giá trị đơn hàng
        // Expected: Không có kết quả. Bảng không có dòng dữ liệu và hiển thị thông báo "Không tìm thấy kết quả cho 2,416,000đ"
        // Actual: Hệ thống hiển thị các hóa đơn có giá trị đơn hàng "2,416,000đ", các giá trị khác không hiển thị
        // Tuy nhiên chức năng đang tìm kiếm theo tên nhà cung cấp, không đúng business rule chỉ hỗ trợ tìm theo Mã đơn nhập hàng hoặc Ngày tạo hóa đơn
        // Status: FAIL - Hệ thống không tuân theo business rule
        
        System.out.println("=== TC_11: Tim kiem theo gia tri don hang ===");
        
        purchaseInvoiceSearchPage.enterSearchKeyword(SearchData.ORDER_VALUE_2416000);
        System.out.println("Da nhap tu khoa: " + SearchData.ORDER_VALUE_2416000);
        
        purchaseInvoiceSearchPage.clickSearchButton();
        System.out.println("Da click nut Tim kiem");
        
        // Kiểm tra kết quả
        boolean isTableEmpty = purchaseInvoiceSearchPage.isTableEmpty();
        boolean isResultsDisplayed = purchaseInvoiceSearchPage.isResultsDisplayed();
        
        System.out.println("Bảng rỗng: " + isTableEmpty);
        System.out.println("Kết quả được hiển thị: " + isResultsDisplayed);
        
        // Expected (theo business rule): Không có kết quả
        // Actual: Hệ thống hiển thị kết quả (không tuân theo business rule)
        // Test này FAIL vì hệ thống không tuân theo business rule
        Assert.assertTrue(isTableEmpty,
            "Bảng không có dòng dữ liệu (hệ thống không hỗ trợ tìm kiếm theo giá trị đơn hàng theo business rule)");
        Assert.assertFalse(isResultsDisplayed,
            "Không có kết quả (hệ thống chỉ hỗ trợ tìm theo Mã đơn nhập hàng hoặc Ngày tạo)");
        
        System.out.println("TC_11 PASSED - Hệ thống không hỗ trợ tìm kiếm theo giá trị đơn hàng");
    }
}
