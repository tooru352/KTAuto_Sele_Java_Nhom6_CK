package pageobjects;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import common.Constant;
import java.time.Duration;

public class EditInvoicePage {
    public WebDriver driver;
    private WebDriverWait wait;

    // Buttons
    private By editButton = By.xpath("//button[@class='modal-btn modal-btn-edit' and contains(@onclick, 'batDauSua')]");
    private By saveButton = By.xpath("//button[@class='modal-btn modal-btn-edit' and contains(@onclick, 'luuSua')]");
    private By cancelButton = By.xpath("//button[@class='modal-btn modal-btn-delete' and contains(@onclick, 'huyySua')]");
    
    // Input fields in edit mode (for HH011 - first row, data-row="0")
    private By quantityField = By.xpath("//input[@class='modal-input-edit modal-num-input' and @data-row='0' and @data-field='sl']");
    private By unitPriceField = By.xpath("//input[@class='modal-input-edit modal-num-input' and @data-row='0' and @data-field='dg']");
    private By discountField = By.xpath("//input[@class='modal-input-edit modal-num-input' and @data-row='0' and @data-field='ck']");
    
    // Other elements
    private By paymentMethodDropdown = By.xpath("//select");
    private By totalAmountField = By.xpath("(//td[@class='ct-tt'])[1]");
    private By successMessage = By.xpath("//*[contains(text(),'Đã Lưu Thông Tin Thay Đổi') or contains(text(),'Lưu thành công') or contains(text(),'Đã lưu')]");
    private By errorMessage = By.xpath("//*[contains(text(),'lỗi') or contains(text(),'Lỗi') or contains(text(),'Error')]");
    private By successPopupOkButton = By.xpath("//button[contains(text(),'OK')]");
    private By modalDialog = By.xpath("//div[@class='modal-body']");
    private By invoiceListPage = By.xpath("//table");

    public EditInvoicePage(WebDriver driver) {
        this.driver = driver;
        this.wait = new WebDriverWait(driver, Duration.ofSeconds(Constant.EXPLICIT_WAIT));
    }

    public void openImportPage() {
        driver.get("http://127.0.0.1:8000/nhap-hang/");
    }

    public void selectInvoice(String invoiceCode) {
        try {
            System.out.println("Tìm kiếm hóa đơn: " + invoiceCode);
            // Chờ bảng load trước
            wait.until(ExpectedConditions.presenceOfElementLocated(invoiceListPage));
            System.out.println("Bảng đã load");
            
            // Tìm cell chứa mã hóa đơn và click vào nó
            By invoiceCell = By.xpath("//td[@class='ma-don' and contains(text(), '" + invoiceCode + "')]");
            WebElement element = wait.until(ExpectedConditions.elementToBeClickable(invoiceCell));
            System.out.println("Tìm thấy element, click vào...");
            element.click();
            System.out.println("Đã click, chờ modal mở...");
            
            // Chờ modal dialog xuất hiện
            wait.until(ExpectedConditions.visibilityOfElementLocated(modalDialog));
            System.out.println("Modal đã mở");
            
            // Chờ thêm để modal load xong input fields
            Thread.sleep(2000);
            System.out.println("Modal đã load xong");
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
            throw new RuntimeException(e);
        }
    }

    public void clickEditButton() {
        try {
            System.out.println("Tìm nút Sửa...");
            wait.until(ExpectedConditions.elementToBeClickable(editButton)).click();
            System.out.println("Đã click nút Sửa");
            
            // Chờ form chuyển sang chế độ edit
            Thread.sleep(1500);
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
            throw new RuntimeException(e);
        }
    }

    public void clickSaveButton() {
        try {
            System.out.println("Tìm nút Lưu...");
            wait.until(ExpectedConditions.elementToBeClickable(saveButton)).click();
            System.out.println("Đã click nút Lưu");
            
            // Chờ response từ server
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
            throw new RuntimeException(e);
        }
    }

    public void enterQuantity(int quantity) {
        try {
            // Tìm input field số lượng trong edit mode
            WebElement input = wait.until(ExpectedConditions.visibilityOfElementLocated(quantityField));
            System.out.println("Tìm thấy input field số lượng");
            
            // Clear và nhập giá trị mới
            input.clear();
            input.sendKeys(String.valueOf(quantity));
            System.out.println("Đã nhập số lượng: " + quantity);
        } catch (Exception e) {
            System.out.println("Lỗi khi nhập số lượng: " + e.getMessage());
            throw new RuntimeException(e);
        }
    }

    public void enterUnitPrice(int unitPrice) {
        try {
            // Tìm input field đơn giá trong edit mode
            WebElement input = wait.until(ExpectedConditions.visibilityOfElementLocated(unitPriceField));
            System.out.println("Tìm thấy input field đơn giá");
            
            // Clear và nhập giá trị mới
            input.clear();
            input.sendKeys(String.valueOf(unitPrice));
            System.out.println("Đã nhập đơn giá: " + unitPrice);
        } catch (Exception e) {
            System.out.println("Lỗi khi nhập đơn giá: " + e.getMessage());
            throw new RuntimeException(e);
        }
    }

    public void enterDiscount(double discount) {
        try {
            // Tìm input field chiết khấu trong edit mode
            WebElement input = wait.until(ExpectedConditions.visibilityOfElementLocated(discountField));
            System.out.println("Tìm thấy input field chiết khấu");
            
            // Clear và nhập giá trị mới
            input.clear();
            input.sendKeys(String.valueOf(discount));
            System.out.println("Đã nhập chiết khấu: " + discount);
        } catch (Exception e) {
            System.out.println("Lỗi khi nhập chiết khấu: " + e.getMessage());
            throw new RuntimeException(e);
        }
    }

    public void selectPaymentMethod(String paymentMethod) {
        WebElement dropdown = wait.until(ExpectedConditions.visibilityOfElementLocated(paymentMethodDropdown));
        dropdown.click();
        By option = By.xpath("//option[contains(text(),'" + paymentMethod + "')]");
        wait.until(ExpectedConditions.elementToBeClickable(option)).click();
    }

    public void clickCancelButton() {
        try {
            System.out.println("Tìm nút Hủy...");
            wait.until(ExpectedConditions.elementToBeClickable(cancelButton)).click();
            System.out.println("Đã click nút Hủy");
            
            // Chờ modal đóng
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
            throw new RuntimeException(e);
        }
    }

    public boolean isSuccessMessageDisplayed() {
        try {
            WebDriverWait shortWait = new WebDriverWait(driver, Duration.ofSeconds(5));
            WebElement message = shortWait.until(ExpectedConditions.visibilityOfElementLocated(successMessage));
            System.out.println("Thông báo thành công: " + message.getText());
            return message.isDisplayed();
        } catch (Exception e) {
            System.out.println("Không tìm thấy thông báo thành công: " + e.getMessage());
            return false;
        }
    }

    public String getSuccessMessageText() {
        try {
            WebDriverWait shortWait = new WebDriverWait(driver, Duration.ofSeconds(5));
            return shortWait.until(ExpectedConditions.visibilityOfElementLocated(successMessage)).getText();
        } catch (Exception e) {
            System.out.println("Lỗi khi lấy text thông báo thành công: " + e.getMessage());
            return "";
        }
    }

    private String lastAlertText = "";
    
    public boolean isErrorMessageDisplayed() {
        try {
            // Thử xử lý alert dialog nếu có
            try {
                org.openqa.selenium.Alert alert = driver.switchTo().alert();
                lastAlertText = alert.getText();
                System.out.println("Alert text: " + lastAlertText);
                alert.accept();
                System.out.println("Đã accept alert");
                return true;
            } catch (org.openqa.selenium.NoAlertPresentException e) {
                // Không có alert, tiếp tục tìm error message
            }
            
            WebDriverWait shortWait = new WebDriverWait(driver, Duration.ofSeconds(3));
            WebElement message = shortWait.until(ExpectedConditions.visibilityOfElementLocated(errorMessage));
            System.out.println("Thông báo lỗi: " + message.getText());
            return message.isDisplayed();
        } catch (Exception e) {
            System.out.println("Không tìm thấy thông báo lỗi: " + e.getMessage());
            return false;
        }
    }

    public String getErrorMessageText() {
        try {
            // Nếu có alert text từ lần gọi trước, trả về nó
            if (!lastAlertText.isEmpty()) {
                String text = lastAlertText;
                lastAlertText = "";
                return text;
            }
            
            // Thử xử lý alert dialog nếu có
            try {
                org.openqa.selenium.Alert alert = driver.switchTo().alert();
                String alertText = alert.getText();
                System.out.println("Alert text: " + alertText);
                alert.accept();
                return alertText;
            } catch (org.openqa.selenium.NoAlertPresentException e) {
                // Không có alert, tiếp tục tìm error message
            }
            
            WebDriverWait shortWait = new WebDriverWait(driver, Duration.ofSeconds(3));
            return shortWait.until(ExpectedConditions.visibilityOfElementLocated(errorMessage)).getText();
        } catch (Exception e) {
            System.out.println("Lỗi khi lấy text thông báo lỗi: " + e.getMessage());
            return "";
        }
    }

    public int getTotalAmount() {
        try {
            String totalText = driver.findElement(totalAmountField).getText();
            System.out.println("Text thành tiền: " + totalText);
            // Loại bỏ dấu chấm (.) và ký tự không phải số
            String cleanedText = totalText.replaceAll("[^0-9]", "");
            System.out.println("Text sau khi làm sạch: " + cleanedText);
            return cleanedText.isEmpty() ? 0 : Integer.parseInt(cleanedText);
        } catch (Exception e) {
            System.out.println("Lỗi khi lấy tổng tiền: " + e.getMessage());
            return 0;
        }
    }

    public int getQuantity() {
        try {
            String value = driver.findElement(quantityField).getAttribute("value");
            return value != null && !value.isEmpty() ? Integer.parseInt(value) : 0;
        } catch (Exception e) {
            System.out.println("Lỗi khi lấy số lượng: " + e.getMessage());
            return 0;
        }
    }

    public int getUnitPrice() {
        try {
            String value = driver.findElement(unitPriceField).getAttribute("value");
            return value != null && !value.isEmpty() ? Integer.parseInt(value) : 0;
        } catch (Exception e) {
            System.out.println("Lỗi khi lấy đơn giá: " + e.getMessage());
            return 0;
        }
    }

    public double getDiscount() {
        try {
            String value = driver.findElement(discountField).getAttribute("value");
            return value != null && !value.isEmpty() ? Double.parseDouble(value) : 0;
        } catch (Exception e) {
            System.out.println("Lỗi khi lấy chiết khấu: " + e.getMessage());
            return 0;
        }
    }

    public String getPaymentMethod() {
        try {
            WebElement dropdown = driver.findElement(paymentMethodDropdown);
            return dropdown.getAttribute("value");
        } catch (Exception e) {
            return "";
        }
    }

    public boolean isOnInvoiceDetailPage() {
        try {
            return wait.until(ExpectedConditions.visibilityOfElementLocated(modalDialog)).isDisplayed();
        } catch (Exception e) {
            return false;
        }
    }

    public boolean isOnInvoiceListPage() {
        try {
            return wait.until(ExpectedConditions.visibilityOfElementLocated(invoiceListPage)).isDisplayed();
        } catch (Exception e) {
            return false;
        }
    }

    public boolean isTotalAmountRecalculated(int expectedTotal) {
        try {
            int actualTotal = getTotalAmount();
            System.out.println("Tổng tiền dự kiến: " + expectedTotal + ", Tổng tiền thực tế: " + actualTotal);
            return actualTotal == expectedTotal;
        } catch (Exception e) {
            System.out.println("Lỗi khi kiểm tra tính toán lại tổng tiền: " + e.getMessage());
            return false;
        }
    }

    public boolean isQuantityFieldEmpty() {
        try {
            String value = driver.findElement(quantityField).getAttribute("value");
            return value == null || value.isEmpty();
        } catch (Exception e) {
            System.out.println("Lỗi khi kiểm tra field số lượng rỗng: " + e.getMessage());
            return false;
        }
    }

    public boolean isUnitPriceFieldEmpty() {
        try {
            String value = driver.findElement(unitPriceField).getAttribute("value");
            return value == null || value.isEmpty();
        } catch (Exception e) {
            System.out.println("Lỗi khi kiểm tra field đơn giá rỗng: " + e.getMessage());
            return false;
        }
    }

    public boolean isNegativeQuantityAllowed() {
        try {
            WebElement input = driver.findElement(quantityField);
            String value = input.getAttribute("value");
            return value != null && value.contains("-");
        } catch (Exception e) {
            System.out.println("Lỗi khi kiểm tra số lượng âm: " + e.getMessage());
            return false;
        }
    }

    public boolean isNegativeUnitPriceAllowed() {
        try {
            WebElement input = driver.findElement(unitPriceField);
            String value = input.getAttribute("value");
            return value != null && value.contains("-");
        } catch (Exception e) {
            System.out.println("Lỗi khi kiểm tra đơn giá âm: " + e.getMessage());
            return false;
        }
    }

    public void waitForPageToLoad() {
        // Chờ modal dialog xuất hiện
        wait.until(ExpectedConditions.visibilityOfElementLocated(modalDialog));
    }

    public double getTotalAmountWithDiscount() {
        try {
            System.out.println("=== Lấy thành tiền ===");
            
            // Lấy element cell thành tiền
            WebElement totalCell = driver.findElement(totalAmountField);
            System.out.println("Tìm thấy cell thành tiền");
            
            // Lấy text trực tiếp
            String totalText = totalCell.getText().trim();
            System.out.println("Text thành tiền: '" + totalText + "'");
            
            if (totalText.isEmpty()) {
                System.out.println("Text rỗng, thử lấy innerText...");
                totalText = (String) ((org.openqa.selenium.JavascriptExecutor) driver)
                    .executeScript("return arguments[0].innerText;", totalCell);
                totalText = totalText != null ? totalText.trim() : "";
                System.out.println("innerText: '" + totalText + "'");
            }
            
            if (totalText.isEmpty()) {
                System.out.println("Không tìm thấy text");
                return 0;
            }
            
            // Loại bỏ ký tự không phải số (giữ lại dấu chấm và dấu phẩy)
            String cleanedText = totalText.replaceAll("[^0-9.,]", "");
            System.out.println("Text sau khi làm sạch: '" + cleanedText + "'");
            
            if (cleanedText.isEmpty()) {
                return 0;
            }
            
            // Xử lý định dạng số
            // Ví dụ: "472.500" -> 472500 (dấu chấm là dấu phân cách hàng nghìn, 3 chữ số sau)
            // Hoặc: "472,500" -> 472500 (dấu phẩy là dấu phân cách hàng nghìn, 3 chữ số sau)
            // Hoặc: "472.5" -> 472.5 (dấu chấm là dấu thập phân, 1 chữ số sau)
            double result = 0;
            
            if (cleanedText.contains(".") || cleanedText.contains(",")) {
                // Tìm dấu phân cách cuối cùng
                int lastDotIndex = cleanedText.lastIndexOf(".");
                int lastCommaIndex = cleanedText.lastIndexOf(",");
                int lastSeparatorIndex = Math.max(lastDotIndex, lastCommaIndex);
                
                // Kiểm tra số chữ số sau dấu phân cách cuối cùng
                int digitsAfter = cleanedText.length() - lastSeparatorIndex - 1;
                System.out.println("Số chữ số sau dấu phân cách: " + digitsAfter);
                
                if (digitsAfter == 3) {
                    // Đó là dấu phân cách hàng nghìn (ví dụ: 472.500)
                    result = Double.parseDouble(cleanedText.replaceAll("[.,]", ""));
                    System.out.println("Coi là dấu phân cách hàng nghìn");
                } else if (digitsAfter >= 1 && digitsAfter <= 2) {
                    // Đó là dấu thập phân (ví dụ: 472.5 hoặc 472.50)
                    String beforeDecimal = cleanedText.substring(0, lastSeparatorIndex).replaceAll("[.,]", "");
                    String afterDecimal = cleanedText.substring(lastSeparatorIndex + 1);
                    result = Double.parseDouble(beforeDecimal + "." + afterDecimal);
                    System.out.println("Coi là dấu thập phân");
                } else {
                    // Loại bỏ tất cả dấu phân cách
                    result = Double.parseDouble(cleanedText.replaceAll("[.,]", ""));
                }
            } else {
                result = Double.parseDouble(cleanedText);
            }
            
            System.out.println("Kết quả: " + result);
            return result;
        } catch (Exception e) {
            System.out.println("Lỗi: " + e.getMessage());
            e.printStackTrace();
            return 0;
        }
    }
}
