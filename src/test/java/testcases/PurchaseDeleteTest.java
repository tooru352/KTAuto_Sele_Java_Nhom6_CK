package testcases;

import common.Constant;
import org.openqa.selenium.By;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.testng.Assert;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;
import pageobjects.PurchaseDeletePage;

/**
 * Test Suite: Xóa hóa đơn nhập hàng
 * Luồng: Đăng nhập → Trang chủ → Click "Nhập hàng" → Chọn hóa đơn → Xóa
 *
 * TC_01 – Xóa hóa đơn (nhấn Đồng ý)
 * TC_02 – Xóa hóa đơn – chọn Hủy
 */
public class PurchaseDeleteTest extends BaseTest {

    private static final String NHAP_HANG_URL = Constant.BASE_URL + "/nhap-hang/";

    private PurchaseDeletePage page;

    // ===================== SETUP =====================

    @BeforeMethod
    @Override
    public void setUp() {
        super.setUp();
        page = new PurchaseDeletePage(driver, wait);

        // Bước 1: Đăng nhập
        login();

        // Bước 2: Click menu "Nhập hàng"
        By nhapHangMenu = By.xpath("//nav//a[.//span[text()='Nhập hàng']]");
        wait.until(ExpectedConditions.elementToBeClickable(nhapHangMenu)).click();
        sleep(800);
    }

    // ===================== TEST CASES =====================

    /**
     * TC_01 – Xóa hóa đơn (nhấn Đồng ý)
     *
     * Bước:
     *   1. Đăng nhập, vào trang Nhập hàng
     *   2. Chọn hóa đơn đầu tiên có trạng thái "Hoàn thành"
     *   3. Nhấn nút "Xóa"
     *   4. Popup xác nhận hiển thị
     *   5. Nhấn "Đồng ý"
     * Kết quả mong đợi:
     *   - Popup "Xoá Thành Công!" hiển thị
     *   - Trạng thái hóa đơn chuyển sang "Đang chờ xóa"
     *   - Hóa đơn vẫn còn trong danh sách
     */
    @Test(description = "TC_01 - Xóa hóa đơn nhấn Đồng ý")
    public void TC_01_DeleteInvoice_Confirm() {
        System.out.println("=== TC_01: Xóa hóa đơn – nhấn Đồng ý ===");

        Assert.assertTrue(page.isAt(), "[FAIL] Không hiển thị trang danh sách nhập hàng");

        // Lấy mã đơn và trạng thái ban đầu
        String invoiceCode = page.getFirstInvoiceCode();
        String statusBefore = page.getFirstInvoiceStatus();
        System.out.println("  → Hóa đơn: " + invoiceCode + ", trạng thái: " + statusBefore);

        Assert.assertFalse(invoiceCode.isEmpty(),
            "[SKIP] Không có hóa đơn 'Hoàn thành' nào trong danh sách");

        // Bước 3: Click vào hóa đơn để mở modal
        page.clickFirstInvoice();
        Assert.assertTrue(page.isModalVisible(),
            "[FAIL] Modal chi tiết không hiển thị");

        // Bước 4: Nhấn nút Xóa
        page.clickDeleteButton();

        // Bước 5: Popup xác nhận phải hiển thị
        Assert.assertTrue(page.isConfirmDeletePopupVisible(),
            "[FAIL] Popup xác nhận xóa không hiển thị");
        System.out.println("  → Popup xác nhận xóa hiển thị: OK");

        // Bước 6: Nhấn Đồng ý
        page.clickConfirmDeleteOk();

        // Popup "Xoá Thành Công!" phải hiển thị
        Assert.assertTrue(page.isDeleteSuccessPopupVisible(),
            "[FAIL] Popup 'Xoá Thành Công!' không hiển thị");
        System.out.println("  → Popup 'Xoá Thành Công!' hiển thị: OK");

        // Nhấn OK để reload trang
        page.clickDeleteSuccessOk();

        // Hóa đơn vẫn còn trong danh sách với trạng thái "Đang chờ xóa"
        Assert.assertTrue(page.isAt(),
            "[FAIL] Không ở trang danh sách sau khi xóa");

        String statusAfter = page.getFirstInvoiceStatus();
        System.out.println("  → Trạng thái sau xóa: " + statusAfter);

        Assert.assertTrue(page.isFirstInvoiceStatusDangChoXoa(),
            "[FAIL] Trạng thái không chuyển sang 'Đang chờ xóa'. Thực tế: " + statusAfter);

        System.out.println("✓ TC_01 PASS: Xóa thành công, trạng thái = 'Đang chờ xóa'");
    }

    /**
     * TC_02 – Xóa hóa đơn – chọn Hủy
     *
     * Bước:
     *   1. Đăng nhập, vào trang Nhập hàng
     *   2. Chọn hóa đơn có trạng thái "Hoàn thành"
     *   3. Nhấn nút "Xóa"
     *   4. Popup xác nhận hiển thị
     *   5. Nhấn "Hủy"
     * Kết quả mong đợi:
     *   - Hóa đơn không bị xóa
     *   - Trạng thái vẫn là "Hoàn thành"
     */
    @Test(description = "TC_02 - Xóa hóa đơn nhấn Hủy")
    public void TC_02_DeleteInvoice_Cancel() {
        System.out.println("=== TC_02: Xóa hóa đơn – nhấn Hủy ===");

        Assert.assertTrue(page.isAt(), "[FAIL] Không hiển thị trang danh sách nhập hàng");

        // Tìm hóa đơn có trạng thái "Hoàn thành"
        String invoiceCode = page.getFirstInvoiceCode();
        String statusBefore = page.getFirstInvoiceStatus();
        System.out.println("  → Hóa đơn: " + invoiceCode + ", trạng thái: " + statusBefore);

        Assert.assertFalse(invoiceCode.isEmpty(),
            "[SKIP] Không có hóa đơn 'Hoàn thành' nào trong danh sách");

        // Bước 3: Click vào hóa đơn để mở modal
        page.clickFirstInvoice();
        Assert.assertTrue(page.isModalVisible(),
            "[FAIL] Modal chi tiết không hiển thị");

        // Bước 4: Nhấn nút Xóa
        page.clickDeleteButton();

        // Bước 5: Popup xác nhận phải hiển thị
        Assert.assertTrue(page.isConfirmDeletePopupVisible(),
            "[FAIL] Popup xác nhận xóa không hiển thị");
        System.out.println("  → Popup xác nhận xóa hiển thị: OK");

        // Bước 6: Nhấn Hủy
        page.clickConfirmDeleteCancel();
        sleep(500);

        // Popup phải đóng lại
        Assert.assertFalse(page.isConfirmDeletePopupVisible(),
            "[FAIL] Popup vẫn hiển thị sau khi nhấn Hủy");

        // Đóng modal, quay về danh sách
        driver.get(NHAP_HANG_URL);
        sleep(800);

        // Trạng thái hóa đơn vẫn là "Hoàn thành"
        String statusAfter = page.getFirstInvoiceStatus();
        System.out.println("  → Trạng thái sau khi hủy: " + statusAfter);

        Assert.assertTrue(page.isInvoiceStatusHoanThanh(invoiceCode),
            "[FAIL] Trạng thái bị thay đổi dù đã nhấn Hủy. Trước: "
            + statusBefore + ", Sau: " + statusAfter);

        System.out.println("✓ TC_02 PASS: Hóa đơn không bị xóa, trạng thái = '" + statusAfter + "'");
    }

    // ===================== HELPER =====================

    private void sleep(long ms) {
        try { Thread.sleep(ms); } catch (InterruptedException ignored) { }
    }
}
