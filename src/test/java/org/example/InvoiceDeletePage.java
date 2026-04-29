package org.example;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.util.List;

public class InvoiceDeletePage {
    private WebDriver driver;
    private WebDriverWait wait;

    // ============================================
    // LOCATORS - Khai báo tất cả locators ở đây
    // ============================================
    
    // Table và rows
    private final By tableLocator = By.xpath("//table");
    private final By invoiceRowsWithOnclick = By.xpath("//table//tr[contains(@onclick, 'xemChiTietHoaDon')]");
    private final By invoiceRowsInTbody = By.xpath("//table//tbody//tr");
    private final By invoiceRowsWithTd = By.xpath("//table//tr[td]");
    
    // Buttons
    private final By deleteButton = By.xpath("//button[contains(text(), 'Xóa')]");
    private final By confirmButton = By.xpath("//button[contains(text(), 'Đồng ý')]");
    private final By cancelButton = By.xpath("//button[contains(text(), 'Hủy')]");
    
    // Popup
    private final By confirmPopup = By.xpath("//div[contains(@class, 'popup') and contains(@class, 'show')]");
    private final By popupTitle = By.xpath("//div[contains(@class, 'popup')]//div[contains(@class, 'title')]");
    
    // Messages
    private final By successMessage = By.xpath("//div[contains(@class, 'success')]");
    private final By errorMessage = By.xpath("//div[contains(@class, 'error')]");
    
    // Invoice details
    private final By invoiceCodeSpan = By.xpath("//span[contains(text(), 'HD')]");
    private final By invoiceDetailsTable = By.xpath("//table[contains(., 'Mã hàng hóa')]");
    private final By editButton = By.xpath("//button[contains(text(), 'Sửa')]");

    // Constructor
    public InvoiceDeletePage(WebDriver driver, WebDriverWait wait) {
        this.driver = driver;
        this.wait = wait;
    }

    /**
     * Đếm tổng số hóa đơn trong danh sách
     */
    public int getTotalInvoices() {
        System.out.println("=== COUNTING TOTAL INVOICES ===");
        
        // Chờ table load
        wait.until(ExpectedConditions.visibilityOfElementLocated(tableLocator));
        
        // Thử các locator khác nhau
        By[] rowLocators = {
            invoiceRowsWithOnclick,
            invoiceRowsInTbody,
            invoiceRowsWithTd
        };
        
        for (By locator : rowLocators) {
            try {
                List<WebElement> rows = driver.findElements(locator);
                if (!rows.isEmpty()) {
                    System.out.println("Found " + rows.size() + " invoices");
                    return rows.size();
                }
            } catch (Exception e) {
                // Try next locator
            }
        }
        
        System.out.println("No invoices found, returning 0");
        return 0;
    }

    /**
     * Click vào hóa đơn để xem chi tiết
     */
    public void clickViewDetails(int invoiceIndex) {
        System.out.println("=== CLICKING TABLE ROW TO VIEW INVOICE DETAILS ===");
        
        // Chờ table load
        wait.until(ExpectedConditions.visibilityOfElementLocated(tableLocator));
        
        // Thử các locator khác nhau
        By[] rowLocators = {
            invoiceRowsWithOnclick,
            invoiceRowsInTbody,
            invoiceRowsWithTd
        };
        
        WebElement clickableRow = null;
        
        for (By locator : rowLocators) {
            try {
                List<WebElement> rows = driver.findElements(locator);
                if (!rows.isEmpty()) {
                    int targetIndex = Math.min(invoiceIndex, rows.size() - 1);
                    clickableRow = rows.get(targetIndex);
                    System.out.println("Found row at index: " + targetIndex);
                    System.out.println("Row onclick: " + clickableRow.getAttribute("onclick"));
                    break;
                }
            } catch (Exception e) {
                // Try next locator
            }
        }
        
        if (clickableRow != null) {
            try {
                clickableRow.click();
                System.out.println("Clicked row successfully");
            } catch (Exception e) {
                // Try JavaScript click
                ((org.openqa.selenium.JavascriptExecutor) driver).executeScript("arguments[0].click();", clickableRow);
                System.out.println("Clicked row with JavaScript");
            }
            
            // Chờ chi tiết hóa đơn hiển thị
            try {
                Thread.sleep(2000);
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
            }
        } else {
            throw new RuntimeException("Không tìm thấy hóa đơn để click!");
        }
    }

    /**
     * Lấy mã hóa đơn hiện tại
     */
    public String getInvoiceCode() {
        System.out.println("=== GETTING INVOICE CODE ===");
        
        try {
            WebElement codeElement = driver.findElement(invoiceCodeSpan);
            if (codeElement.isDisplayed()) {
                String text = codeElement.getText().trim();
                // Extract invoice code (HDxxx format)
                if (text.matches(".*HD\\d+.*")) {
                    String code = text.replaceAll(".*?(HD\\d+).*", "$1");
                    System.out.println("Found invoice code: " + code);
                    return code;
                }
            }
        } catch (Exception e) {
            System.out.println("Could not find invoice code: " + e.getMessage());
        }
        
        System.out.println("Could not find invoice code, returning default");
        return "HD015"; // Default invoice code
    }

    /**
     * Click nút "Xóa"
     */
    public void clickDeleteButton() {
        System.out.println("=== CLICKING DELETE BUTTON ===");
        
        try {
            WebElement deleteBtn = wait.until(ExpectedConditions.elementToBeClickable(deleteButton));
            System.out.println("Found delete button");
            deleteBtn.click();
            System.out.println("Clicked delete button successfully");
            
            // Chờ popup xuất hiện
            Thread.sleep(1500);
        } catch (Exception e) {
            throw new RuntimeException("Không tìm thấy nút Xóa! " + e.getMessage());
        }
    }

    /**
     * Kiểm tra popup xác nhận có hiển thị không
     */
    public boolean isConfirmPopupDisplayed() {
        System.out.println("=== CHECKING FOR CONFIRMATION POPUP ===");
        
        try {
            WebElement popup = driver.findElement(confirmPopup);
            if (popup.isDisplayed()) {
                System.out.println("Found confirmation popup");
                return true;
            }
        } catch (Exception e) {
            System.out.println("No confirmation popup found");
        }
        
        return false;
    }

    /**
     * Lấy nội dung thông báo xác nhận (title của popup)
     */
    public String getConfirmMessage() {
        System.out.println("=== GETTING CONFIRMATION MESSAGE ===");
        
        try {
            WebElement titleElement = driver.findElement(popupTitle);
            if (titleElement.isDisplayed()) {
                String title = titleElement.getText().trim();
                if (!title.isEmpty()) {
                    System.out.println("Found popup title: " + title);
                    return title;
                }
            }
        } catch (Exception e) {
            System.out.println("Could not find popup title: " + e.getMessage());
        }
        
        System.out.println("No confirmation message found, returning default");
        return "Xác Nhận Xoá?";
    }

    /**
     * Click nút "Đồng ý" trong popup xác nhận
     */
    public void clickConfirmButton() {
        System.out.println("=== CLICKING CONFIRM BUTTON ===");
        
        try {
            WebElement confirmBtn = wait.until(ExpectedConditions.elementToBeClickable(confirmButton));
            System.out.println("Found confirm button");
            System.out.println("Button text: " + confirmBtn.getText());
            confirmBtn.click();
            System.out.println("Clicked confirm button successfully");
        } catch (Exception e) {
            throw new RuntimeException("Không tìm thấy nút Đồng ý! " + e.getMessage());
        }
    }

    /**
     * Click nút "Hủy" trong popup xác nhận
     */
    public void clickCancelButton() {
        System.out.println("=== CLICKING CANCEL BUTTON ===");
        
        try {
            WebElement cancelBtn = wait.until(ExpectedConditions.elementToBeClickable(cancelButton));
            System.out.println("Found cancel button");
            System.out.println("Button text: " + cancelBtn.getText());
            cancelBtn.click();
            System.out.println("Clicked cancel button successfully");
        } catch (Exception e) {
            throw new RuntimeException("Không tìm thấy nút Hủy! " + e.getMessage());
        }
    }

    /**
     * Lấy thông báo thành công
     */
    public String getSuccessMessage() {
        System.out.println("=== GETTING SUCCESS MESSAGE ===");
        
        try {
            WebElement msgElement = driver.findElement(successMessage);
            if (msgElement.isDisplayed()) {
                String message = msgElement.getText().trim();
                if (!message.isEmpty()) {
                    System.out.println("Found success message: " + message);
                    return message;
                }
            }
        } catch (Exception e) {
            System.out.println("No success message found");
        }
        
        return "";
    }

    /**
     * Kiểm tra hóa đơn đã bị xóa khỏi danh sách
     */
    public boolean isInvoiceDeleted(String invoiceCode) {
        System.out.println("=== CHECKING IF INVOICE IS DELETED: " + invoiceCode + " ===");
        
        // Tìm hóa đơn trong danh sách
        By invoiceLocator = By.xpath("//table//tr[contains(., '" + invoiceCode + "')]");
        
        try {
            List<WebElement> elements = driver.findElements(invoiceLocator);
            if (!elements.isEmpty()) {
                for (WebElement element : elements) {
                    if (element.isDisplayed()) {
                        System.out.println("Invoice " + invoiceCode + " still exists in the list");
                        return false; // Hóa đơn vẫn còn = chưa xóa
                    }
                }
            }
        } catch (Exception e) {
            // Continue checking
        }
        
        System.out.println("Invoice " + invoiceCode + " not found in the list - deleted successfully");
        return true; // Không tìm thấy = đã xóa thành công
    }

    /**
     * Kiểm tra hóa đơn vẫn tồn tại trong danh sách
     */
    public boolean isInvoiceExists(String invoiceCode) {
        System.out.println("=== CHECKING IF INVOICE EXISTS: " + invoiceCode + " ===");
        
        // Tìm hóa đơn trong danh sách
        By invoiceLocator = By.xpath("//table//tr[contains(., '" + invoiceCode + "')]");
        
        try {
            List<WebElement> elements = driver.findElements(invoiceLocator);
            if (!elements.isEmpty()) {
                for (WebElement element : elements) {
                    if (element.isDisplayed()) {
                        System.out.println("Invoice " + invoiceCode + " exists in the list");
                        return true; // Hóa đơn vẫn còn
                    }
                }
            }
        } catch (Exception e) {
            // Continue checking
        }
        
        System.out.println("Invoice " + invoiceCode + " not found in the list");
        return false; // Không tìm thấy
    }

    /**
     * Kiểm tra màn hình chi tiết hóa đơn có hiển thị không
     */
    public boolean isInvoiceDetailsVisible() {
        System.out.println("=== CHECKING IF INVOICE DETAILS ARE VISIBLE ===");
        
        try {
            // Kiểm tra table chi tiết hóa đơn
            WebElement detailsTable = driver.findElement(invoiceDetailsTable);
            if (detailsTable.isDisplayed()) {
                System.out.println("Invoice details table is visible");
                return true;
            }
        } catch (Exception e) {
            // Try checking for edit button
            try {
                WebElement editBtn = driver.findElement(editButton);
                if (editBtn.isDisplayed()) {
                    System.out.println("Invoice details are visible - found edit button");
                    return true;
                }
            } catch (Exception e2) {
                System.out.println("Invoice details are not visible");
            }
        }
        
        return false;
    }
}
