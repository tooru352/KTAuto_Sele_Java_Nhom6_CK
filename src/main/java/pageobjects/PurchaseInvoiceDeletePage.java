package pageobjects;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

/**
 * Page Object cho trang Danh sách hóa đơn nhập + chức năng Xóa
 * URL: /nhap-hang/
 * HTML: nhap_hang.html + nhap-hang.js
 */
public class PurchaseInvoiceDeletePage extends GeneralPage {

    // ===================== LOCATORS =====================

    // Tiêu đề trang danh sách
    private final By pageTitle = By.xpath("//h2[contains(text(),'DANH SÁCH HÓA ĐƠN NHẬP')]");

    // Dòng đầu tiên có trạng thái "Hoàn thành" (mã đơn)
    private final By firstHoanThanhInvoiceCode = By.xpath(
        "//tbody[@id='tableBody']//tr[.//span[contains(text(),'Hoàn thành')]]//td[@class='ma-don'][1]"
    );

    // Trạng thái của hóa đơn "Hoàn thành" đầu tiên
    private final By firstHoanThanhStatus = By.xpath(
        "//tbody[@id='tableBody']//tr[.//span[contains(text(),'Hoàn thành')]][1]//span"
    );
    // Modal chi tiết
    private final By modalOverlay = By.id("modalOverlay");
    private final By modalBox     = By.xpath("//div[@class='modal-box']");

    // Trạng thái trong modal
    private final By modalTrangThai = By.id("edit-trang-thai");

    // Nút Xóa trong modal
    private final By deleteButton = By.xpath("//button[contains(@class,'modal-btn-delete')]");

    // Popup xác nhận xóa
    private final By confirmDeletePopup = By.id("popupConfirmDelete");
    private final By confirmDeleteOkBtn = By.id("popupConfirmDeleteOk");
    private final By confirmDeleteCancelBtn = By.xpath(
        "//div[@id='popupConfirmDelete']//button[contains(@class,'popup-btn-cancel')]"
    );

    // Popup xóa thành công
    private final By deleteSuccessPopup = By.id("popupDeleteSuccess");
    private final By deleteSuccessOkBtn = By.xpath(
        "//div[@id='popupDeleteSuccess']//button[contains(@class,'popup-btn-ok')]"
    );

    // Trạng thái trong bảng danh sách
    // Tìm span "Đang chờ xóa" trong dòng đầu tiên
    private final By firstRowStatus = By.xpath(
        "//tbody[@id='tableBody']//tr[1]//span"
    );

    // ===================== CONSTRUCTOR =====================

    public PurchaseDeletePage(WebDriver driver, WebDriverWait wait) {
        super(driver, wait);
    }

    // ===================== NAVIGATION =====================

    public boolean isAt() {
        try {
            wait.until(ExpectedConditions.visibilityOfElementLocated(pageTitle));
            return true;
        } catch (Exception e) { return false; }
    }

    // ===================== DANH SÁCH =====================

    /**
     * Lấy mã đơn của hóa đơn "Hoàn thành" đầu tiên
     */
    public String getFirstInvoiceCode() {
        try {
            return driver.findElement(firstHoanThanhInvoiceCode).getText().trim();
        } catch (Exception e) { return ""; }
    }

    /**
     * Lấy trạng thái của hóa đơn "Hoàn thành" đầu tiên
     */
    public String getFirstInvoiceStatus() {
        try {
            return driver.findElement(firstHoanThanhStatus).getText().trim();
        } catch (Exception e) { return ""; }
    }

    /**
     * Click vào mã đơn "Hoàn thành" đầu tiên để mở modal chi tiết
     */
    public void clickFirstInvoice() {
        wait.until(ExpectedConditions.elementToBeClickable(firstHoanThanhInvoiceCode)).click();
        sleep(800);
    }

    // ===================== MODAL =====================

    public boolean isModalVisible() {
        try {
            WebElement modal = driver.findElement(modalOverlay);
            return !modal.getAttribute("style").contains("display:none")
                && !modal.getAttribute("style").contains("display: none");
        } catch (Exception e) { return false; }
    }

    /**
     * Lấy trạng thái hiển thị trong modal
     */
    public String getModalTrangThai() {
        try {
            return driver.findElement(modalTrangThai).getText().trim();
        } catch (Exception e) { return ""; }
    }

    /**
     * Nhấn nút Xóa trong modal
     */
    public void clickDeleteButton() {
        wait.until(ExpectedConditions.elementToBeClickable(deleteButton)).click();
        sleep(500);
    }

    // ===================== POPUP XÁC NHẬN XÓA =====================

    /**
     * Kiểm tra popup xác nhận xóa có hiển thị không
     * HTML: div#popupConfirmDelete với class 'show'
     */
    public boolean isConfirmDeletePopupVisible() {
        try {
            return driver.findElement(confirmDeletePopup)
                         .getAttribute("class").contains("show");
        } catch (Exception e) { return false; }
    }

    /**
     * Nhấn "Đồng ý" trong popup xác nhận xóa
     */
    public void clickConfirmDeleteOk() {
        wait.until(ExpectedConditions.elementToBeClickable(confirmDeleteOkBtn)).click();
        sleep(2000); // Chờ AJAX + reload
    }

    /**
     * Nhấn "Hủy" trong popup xác nhận xóa
     */
    public void clickConfirmDeleteCancel() {
        wait.until(ExpectedConditions.elementToBeClickable(confirmDeleteCancelBtn)).click();
        sleep(500);
    }

    // ===================== POPUP XÓA THÀNH CÔNG =====================

    /**
     * Kiểm tra popup "Xoá Thành Công!" có hiển thị không
     */
    public boolean isDeleteSuccessPopupVisible() {
        try {
            return driver.findElement(deleteSuccessPopup)
                         .getAttribute("class").contains("show");
        } catch (Exception e) { return false; }
    }

    /**
     * Nhấn OK trên popup xóa thành công → trang reload
     */
    public void clickDeleteSuccessOk() {
        wait.until(ExpectedConditions.elementToBeClickable(deleteSuccessOkBtn)).click();
        sleep(1500); // Chờ reload
    }

    /**
     * Sau khi xóa, kiểm tra hóa đơn vừa xóa có trạng thái "Đang chờ xóa"
     * Tìm theo mã đơn cụ thể
     */
    public boolean isInvoiceStatusDangChoXoa(String invoiceCode) {
        try {
            By statusSpan = By.xpath(
                "//tbody[@id='tableBody']//tr[.//td[@class='ma-don' and text()='" + invoiceCode + "']]//span"
            );
            String status = driver.findElement(statusSpan).getText().trim();
            System.out.println("  → Trạng thái của " + invoiceCode + ": " + status);
            return status.contains("Đang chờ xóa");
        } catch (Exception e) { return false; }
    }

    /**
     * Kiểm tra hóa đơn có trạng thái "Hoàn thành" (dùng cho TC_02)
     */
    public boolean isInvoiceStatusHoanThanh(String invoiceCode) {
        try {
            By statusSpan = By.xpath(
                "//tbody[@id='tableBody']//tr[.//td[@class='ma-don' and text()='" + invoiceCode + "']]//span"
            );
            String status = driver.findElement(statusSpan).getText().trim();
            return status.contains("Hoàn thành");
        } catch (Exception e) { return false; }
    }

    /**
     * @deprecated dùng isInvoiceStatusDangChoXoa(code) thay thế
     */
    public boolean isFirstInvoiceStatusDangChoXoa() {
        return isInvoiceStatusDangChoXoa(getFirstInvoiceCode());
    }

    /**
     * @deprecated dùng isInvoiceStatusHoanThanh(code) thay thế
     */
    public boolean isFirstInvoiceStatusHoanThanh() {
        return isInvoiceStatusHoanThanh(getFirstInvoiceCode());
    }
}
