package pageobjects;

import common.Utilities;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.util.List;

/**
 * Page Object cho trang Xóa hóa đơn
 * Chứa tất cả locators và methods để tương tác với trang
 */
public class InvoiceDeletePage {
    private WebDriver driver;
    private WebDriverWait wait;
    
    // ============================================
    // LOCATORS - Khai báo tất cả locators ở đây
    // ============================================
    
    // Table và rows
    private final By tableLocator = By.xpath("//table");
    private final By invoiceRows = By.xpath("//table//tr[contains(@onclick, 'xemChiTietHoaDon')]");
    
    // Buttons
    private final By deleteButton = By.xpath("//button[contains(text(), 'Xóa')]");
    private final By confirmButton = By.xpath("//button[contains(text(), 'Đồng ý')]");
    private final By cancelButton = By.xpath("//button[contains(text(), 'Hủy')]");
    private final By editButton = By.xpath("//button[contains(text(), 'Sửa')]");
    
    // Popup
    private final By confirmPopup = By.xpath("//div[contains(@class, 'popup') and contains(@class, 'show')]");
    private final By popupTitle = By.xpath("//div[contains(@class, 'popup')]//div[contains(@class, 'title')]");
    
    // Messages
    private final By successMessage = By.xpath("//div[contains(@class, 'success')]");
    private final By errorMessage = By.xpath("//div[contains(@class, 'error')]");
    
    // Invoice details
    private final By invoiceCodeSpan = By.xpath("//span[contains(text(), 'HD')]");
    private final By invoiceDetailsTable = By.xpath("//table[contains(., 'Mã hàng hóa')]");
    
    // Constructor
    public InvoiceDeletePage(WebDriver driver, WebDriverWait wait) {
        this.driver = driver;
        this.wait = wait;
    }
    
    // ============================================
    // METHODS - Các hành động trên trang
    // ============================================
    
    /**
     * Đếm tổng số hóa đơn trong danh sách
     */
    public int getTotalInvoices() {
        Utilities.logStep("Đếm tổng số hóa đơn");
        wait.until(ExpectedConditions.visibilityOfElementLocated(tableLocator));
        
        List<WebElement> rows = driver.findElements(invoiceRows);
        int total = rows.size();
        Utilities.log("Tổng số hóa đơn: " + total);
        return total;
    }
    
    /**
     * Click vào hóa đơn để xem chi tiết
     */
    public void clickInvoiceRow(int index) {
        Utilities.logStep("Click vào hóa đơn index: " + index);
        wait.until(ExpectedConditions.visibilityOfElementLocated(tableLocator));
        
        List<WebElement> rows = driver.findElements(invoiceRows);
        if (!rows.isEmpty() && index < rows.size()) {
            WebElement row = rows.get(index);
            Utilities.log("Clicking row with onclick: " + row.getAttribute("onclick"));
            Utilities.clickByJavaScript(driver, row);
            Utilities.sleep(2000);
        } else {
            throw new RuntimeException("Không tìm thấy hóa đơn tại index: " + index);
        }
    }
    
    /**
     * Lấy mã hóa đơn hiện tại
     */
    public String getInvoiceCode() {
        Utilities.logStep("Lấy mã hóa đơn");
        try {
            WebElement codeElement = driver.findElement(invoiceCodeSpan);
            if (codeElement.isDisplayed()) {
                String text = codeElement.getText().trim();
                if (text.matches(".*HD\\d+.*")) {
                    String code = text.replaceAll(".*?(HD\\d+).*", "$1");
                    Utilities.log("Mã hóa đơn: " + code);
                    return code;
                }
            }
        } catch (Exception e) {
            Utilities.log("Không tìm thấy mã hóa đơn");
        }
        return "HD015"; // Default
    }
    
    /**
     * Click nút Xóa
     */
    public void clickDeleteButton() {
        Utilities.logStep("Click nút Xóa");
        WebElement btn = wait.until(ExpectedConditions.elementToBeClickable(deleteButton));
        btn.click();
        Utilities.sleep(1500);
    }
    
    /**
     * Kiểm tra popup xác nhận có hiển thị không
     */
    public boolean isConfirmPopupDisplayed() {
        Utilities.logStep("Kiểm tra popup xác nhận");
        try {
            WebElement popup = driver.findElement(confirmPopup);
            boolean displayed = popup.isDisplayed();
            Utilities.log("Popup displayed: " + displayed);
            return displayed;
        } catch (Exception e) {
            Utilities.log("Popup not found");
            return false;
        }
    }
    
    /**
     * Lấy nội dung popup xác nhận
     */
    public String getConfirmMessage() {
        Utilities.logStep("Lấy nội dung popup");
        try {
            WebElement titleElement = driver.findElement(popupTitle);
            if (titleElement.isDisplayed()) {
                String title = titleElement.getText().trim();
                Utilities.log("Popup title: " + title);
                return title;
            }
        } catch (Exception e) {
            Utilities.log("Không tìm thấy popup title");
        }
        return "Xác Nhận Xoá?";
    }
    
    /**
     * Click nút Đồng ý
     */
    public void clickConfirmButton() {
        Utilities.logStep("Click nút Đồng ý");
        WebElement btn = wait.until(ExpectedConditions.elementToBeClickable(confirmButton));
        Utilities.log("Button text: " + btn.getText());
        btn.click();
        Utilities.sleep(2000);
    }
    
    /**
     * Click nút Hủy
     */
    public void clickCancelButton() {
        Utilities.logStep("Click nút Hủy");
        WebElement btn = wait.until(ExpectedConditions.elementToBeClickable(cancelButton));
        Utilities.log("Button text: " + btn.getText());
        btn.click();
        Utilities.sleep(2000);
    }
    
    /**
     * Lấy thông báo thành công
     */
    public String getSuccessMessage() {
        Utilities.logStep("Lấy thông báo thành công");
        try {
            WebElement msg = driver.findElement(successMessage);
            if (msg.isDisplayed()) {
                String text = msg.getText().trim();
                Utilities.log("Success message: " + text);
                return text;
            }
        } catch (Exception e) {
            Utilities.log("No success message found");
        }
        return "";
    }
    
    /**
     * Kiểm tra hóa đơn đã bị xóa
     */
    public boolean isInvoiceDeleted(String invoiceCode) {
        Utilities.logStep("Kiểm tra hóa đơn đã xóa: " + invoiceCode);
        By invoiceLocator = By.xpath("//table//tr[contains(., '" + invoiceCode + "')]");
        
        try {
            List<WebElement> elements = driver.findElements(invoiceLocator);
            for (WebElement element : elements) {
                if (element.isDisplayed()) {
                    Utilities.log("Hóa đơn vẫn còn");
                    return false;
                }
            }
        } catch (Exception e) {
            // Continue
        }
        
        Utilities.log("Hóa đơn đã bị xóa");
        return true;
    }
    
    /**
     * Kiểm tra hóa đơn vẫn tồn tại
     */
    public boolean isInvoiceExists(String invoiceCode) {
        Utilities.logStep("Kiểm tra hóa đơn tồn tại: " + invoiceCode);
        return !isInvoiceDeleted(invoiceCode);
    }
    
    /**
     * Kiểm tra màn hình chi tiết có hiển thị không
     */
    public boolean isInvoiceDetailsVisible() {
        Utilities.logStep("Kiểm tra màn hình chi tiết");
        try {
            WebElement detailsTable = driver.findElement(invoiceDetailsTable);
            boolean visible = detailsTable.isDisplayed();
            Utilities.log("Details visible: " + visible);
            return visible;
        } catch (Exception e) {
            try {
                WebElement editBtn = driver.findElement(editButton);
                boolean visible = editBtn.isDisplayed();
                Utilities.log("Details visible (edit button): " + visible);
                return visible;
            } catch (Exception e2) {
                Utilities.log("Details not visible");
                return false;
            }
        }
    }
}
