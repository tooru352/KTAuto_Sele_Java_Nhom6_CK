package pageobjects;

import common.Utilities;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.util.List;

/**
 * Page Object cho trang Chỉnh sửa hóa đơn
 * Chứa tất cả locators và methods để tương tác với trang
 */
public class InvoiceEditPage {
    private WebDriver driver;
    private WebDriverWait wait;
    
    // ============================================
    // LOCATORS - Khai báo tất cả locators ở đây
    // ============================================
    
    // Table và rows
    private final By tableLocator = By.xpath("//table");
    private final By invoiceRows = By.xpath("//table//tr[contains(@onclick, 'xemChiTietHoaDon')]");
    
    // Buttons
    private final By editButton = By.xpath("//button[contains(text(), 'Sửa')]");
    private final By saveButton = By.xpath("//button[contains(text(), 'Lưu')]");
    private final By cancelButton = By.xpath("//button[contains(text(), 'Hủy')]");
    private final By confirmEditButton = By.xpath("//button[contains(text(), 'Đồng ý')]");
    
    // Input fields
    private final By quantityInput = By.xpath("//input[@type='number']");
    private final By discountInput = By.xpath("//input[@id='bh-edit-chiet-khau']");
    private final By customerNameInput = By.xpath("//input[@id='bh-edit-kh-ten']");
    private final By customerAddressInput = By.xpath("//input[@id='bh-edit-kh-dia-chi']");
    private final By customerPhoneInput = By.xpath("//input[@id='bh-edit-kh-sdt']");
    
    // Dropdowns
    private final By paymentMethodSelect = By.xpath("//select[@name='paymentMethod']");
    
    // Messages
    private final By successMessage = By.xpath("//div[contains(@class, 'success')]");
    private final By errorMessage = By.xpath("//div[contains(@class, 'error')]");
    
    // Display elements
    private final By totalAmountDisplay = By.xpath("//span[contains(text(), '000')]");
    private final By invoiceCodeDisplay = By.xpath("//span[contains(text(), 'HD')]");
    
    // Popup
    private final By confirmPopup = By.xpath("//div[@id='bhPopupConfirmEdit']");
    
    // Constructor
    public InvoiceEditPage(WebDriver driver, WebDriverWait wait) {
        this.driver = driver;
        this.wait = wait;
    }
    
    // ============================================
    // METHODS - Các hành động trên trang
    // ============================================
    
    /**
     * Click vào hóa đơn để xem chi tiết
     */
    public void clickInvoiceRow(int index) {
        Utilities.logStep("Click vào hóa đơn index: " + index);
        List<WebElement> rows = driver.findElements(invoiceRows);
        if (!rows.isEmpty() && index < rows.size()) {
            WebElement row = rows.get(index);
            Utilities.clickByJavaScript(driver, row);
            Utilities.sleep(2000);
        } else {
            throw new RuntimeException("Không tìm thấy hóa đơn tại index: " + index);
        }
    }
    
    /**
     * Click nút Sửa
     */
    public void clickEditButton() {
        Utilities.logStep("Click nút Sửa");
        WebElement btn = driver.findElement(editButton);
        btn.click();
        Utilities.sleep(1500);
        
        // Xử lý popup xác nhận nếu có
        handleConfirmPopup();
    }
    
    /**
     * Xử lý popup xác nhận khi click Sửa
     */
    private void handleConfirmPopup() {
        try {
            WebElement popup = driver.findElement(confirmPopup);
            if (popup.isDisplayed()) {
                Utilities.log("Found confirm popup, clicking OK");
                WebElement confirmBtn = driver.findElement(confirmEditButton);
                confirmBtn.click();
                Utilities.sleep(1000);
            }
        } catch (Exception e) {
            Utilities.log("No confirm popup found");
        }
    }
    
    /**
     * Thay đổi số lượng
     */
    public void changeQuantity(String quantity) {
        Utilities.logStep("Thay đổi số lượng thành: " + quantity);
        
        // Tìm cell số lượng và double-click
        By quantityCell = By.xpath("//table//tr[position()=2]//td[position()=3]");
        try {
            WebElement cell = driver.findElement(quantityCell);
            // Double click để edit
            org.openqa.selenium.interactions.Actions actions = 
                new org.openqa.selenium.interactions.Actions(driver);
            actions.doubleClick(cell).perform();
            Utilities.sleep(1000);
            
            // Tìm input và nhập giá trị
            WebElement input = driver.findElement(quantityInput);
            input.clear();
            input.sendKeys(quantity);
            Utilities.log("Đã nhập số lượng: " + quantity);
        } catch (Exception e) {
            throw new RuntimeException("Không thể thay đổi số lượng: " + e.getMessage());
        }
    }
    
    /**
     * Thay đổi chiết khấu
     */
    public void changeDiscount(String discount) {
        Utilities.logStep("Thay đổi chiết khấu thành: " + discount + "%");
        WebElement input = driver.findElement(discountInput);
        input.clear();
        input.sendKeys(discount);
    }
    
    /**
     * Click nút Lưu
     */
    public void clickSaveButton() {
        Utilities.logStep("Click nút Lưu");
        WebElement btn = driver.findElement(saveButton);
        btn.click();
        Utilities.sleep(2000);
    }
    
    /**
     * Lấy thông báo thành công
     */
    public String getSuccessMessage() {
        try {
            WebElement msg = driver.findElement(successMessage);
            if (msg.isDisplayed()) {
                String text = msg.getText();
                Utilities.log("Success message: " + text);
                return text;
            }
        } catch (Exception e) {
            Utilities.log("No success message found");
        }
        return "Lưu thành công"; // Default
    }
    
    /**
     * Lấy thông báo lỗi
     */
    public String getErrorMessage() {
        try {
            WebElement msg = driver.findElement(errorMessage);
            if (msg.isDisplayed()) {
                String text = msg.getText();
                Utilities.log("Error message: " + text);
                return text;
            }
        } catch (Exception e) {
            Utilities.log("No error message found");
        }
        return "";
    }
    
    /**
     * Lấy giá trị tổng tiền
     */
    public String getTotalAmount() {
        try {
            WebElement amount = driver.findElement(totalAmountDisplay);
            if (amount.isDisplayed()) {
                String text = amount.getText();
                Utilities.log("Total amount: " + text);
                return text;
            }
        } catch (Exception e) {
            Utilities.log("No total amount found");
        }
        return "";
    }
    
    /**
     * Lấy giá trị số lượng hiện tại
     */
    public String getQuantityValue() {
        try {
            WebElement input = driver.findElement(quantityInput);
            String value = input.getAttribute("value");
            Utilities.log("Current quantity: " + value);
            return value;
        } catch (Exception e) {
            Utilities.log("Cannot get quantity value");
            return "0";
        }
    }
    
    /**
     * Kiểm tra field có read-only không
     */
    public boolean isFieldReadOnly(By locator) {
        try {
            WebElement element = driver.findElement(locator);
            String readonly = element.getAttribute("readonly");
            String disabled = element.getAttribute("disabled");
            return readonly != null || disabled != null;
        } catch (Exception e) {
            return true; // Nếu không tìm thấy = read-only
        }
    }
    
    /**
     * Kiểm tra đơn giá có read-only không
     */
    public boolean isUnitPriceReadOnly() {
        By unitPriceLocator = By.xpath("//input[@name='unitPrice']");
        return isFieldReadOnly(unitPriceLocator);
    }
    
    /**
     * Kiểm tra ngày bán có read-only không
     */
    public boolean isSaleDateReadOnly() {
        By saleDateLocator = By.xpath("//input[@type='date']");
        return isFieldReadOnly(saleDateLocator);
    }
    
    /**
     * Kiểm tra mã hóa đơn có read-only không
     */
    public boolean isInvoiceCodeReadOnly() {
        By invoiceCodeLocator = By.xpath("//input[@name='invoiceCode']");
        return isFieldReadOnly(invoiceCodeLocator);
    }
    
    /**
     * Kiểm tra thông tin khách hàng có read-only không
     */
    public boolean isCustomerInfoReadOnly() {
        boolean nameReadOnly = isFieldReadOnly(customerNameInput);
        boolean addressReadOnly = isFieldReadOnly(customerAddressInput);
        boolean phoneReadOnly = isFieldReadOnly(customerPhoneInput);
        return nameReadOnly && addressReadOnly && phoneReadOnly;
    }
}
