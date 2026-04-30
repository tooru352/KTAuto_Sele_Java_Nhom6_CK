package pageobjects;

import common.Constant;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;

import java.util.List;

public class InvoiceDetailPage extends GeneralPage {
    
    // Modal elements
    private final By modal = By.xpath("//div[@id='modalChiTiet']");
    private final By modalHeader = By.xpath("//div[@class='modal-header']/h2");
    private final By closeButton = By.xpath("//button[@id='btnDongChiTiet']");
    
    // Thông tin hóa đơn (cột trái)
    private final By invoiceCodeValue = By.xpath("//span[text()='Mã Hóa Đơn:']/following-sibling::span");
    private final By paymentMethodValue = By.xpath("//span[@id='bh-edit-thanh-toan']");
    private final By saleDateValue = By.xpath("//span[@id='bh-edit-ngay-ban']");
    private final By discountValue = By.xpath("//span[@id='bh-edit-chiet-khau']");
    private final By totalAmountValue = By.xpath("//span[@id='bh-edit-tong-tien']");
    
    // Thông tin khách hàng (cột phải)
    private final By customerCodeValue = By.xpath("//span[text()='Khách Hàng:']/following-sibling::span");
    private final By customerNameValue = By.xpath("//span[@id='bh-edit-kh-ten']");
    
    public InvoiceDetailPage(org.openqa.selenium.WebDriver driver, org.openqa.selenium.support.ui.WebDriverWait wait) {
        super(driver, wait);
    }
    private final By customerAddressValue = By.xpath("//span[@id='bh-edit-kh-dia-chi']");
    private final By customerPhoneValue = By.xpath("//span[@id='bh-edit-kh-sdt']");
    
    // Bảng sản phẩm
    private final By productTable = By.xpath("//table[@class='bh-ct-table']");
    private final By productTableBody = By.xpath("//tbody[@id='bhCtTableBody']");
    private final By productTableHeader = By.xpath("//table[@class='bh-ct-table']/thead");
    
    // Action buttons
    private final By editButton = By.xpath("//button[contains(@onclick, 'batDauSuaBH')]");
    private final By deleteButton = By.xpath("//button[contains(@onclick, 'xoaHoaDonBanModal')]");
    
    // Method để kiểm tra modal có hiển thị không
    public boolean isAt() {
        return isElementDisplayed(modal) && isElementDisplayed(modalHeader);
    }
    
    public boolean isModalDisplayed() {
        return isElementDisplayed(modal);
    }
    
    public String getModalTitle() {
        return getElementText(modalHeader);
    }
    
    // Invoice information methods
    public String getInvoiceCode() {
        return getElementText(invoiceCodeValue);
    }
    
    public String getPaymentMethod() {
        return getElementText(paymentMethodValue);
    }
    
    public String getSaleDate() {
        return getElementText(saleDateValue);
    }
    
    public String getDiscount() {
        return getElementText(discountValue);
    }
    
    public String getTotalAmount() {
        return getElementText(totalAmountValue);
    }
    
    // Customer information methods
    public String getCustomerCode() {
        return getElementText(customerCodeValue);
    }
    
    public String getCustomerName() {
        return getElementText(customerNameValue);
    }
    
    public String getCustomerAddress() {
        return getElementText(customerAddressValue);
    }
    
    public String getCustomerPhone() {
        return getElementText(customerPhoneValue);
    }
    
    // Product table methods
    public boolean isProductTableDisplayed() {
        return isElementDisplayed(productTable);
    }
    
    public boolean isProductInTable(String productName) {
        By productCell = By.xpath("//tbody[@id='bhCtTableBody']//td[contains(text(), '" + productName + "')]");
        return isElementDisplayed(productCell);
    }
    
    public String getProductCode(String productName) {
        try {
            By productCodeCell = By.xpath("//tbody[@id='bhCtTableBody']//tr[td[contains(text(), '" + productName + "')]]//td[2]");
            return getElementText(productCodeCell);
        } catch (Exception e) {
            return "";
        }
    }
    
    public String getProductQuantity(String productName) {
        try {
            By quantityCell = By.xpath("//tbody[@id='bhCtTableBody']//tr[td[contains(text(), '" + productName + "')]]//td[@class='bh-ct-sl']");
            return getElementText(quantityCell);
        } catch (Exception e) {
            return "";
        }
    }
    
    public String getProductUnitPrice(String productName) {
        try {
            By priceCell = By.xpath("//tbody[@id='bhCtTableBody']//tr[td[contains(text(), '" + productName + "')]]//td[@class='bh-ct-dg']");
            return getElementText(priceCell);
        } catch (Exception e) {
            return "";
        }
    }
    
    public String getProductTotal(String productName) {
        try {
            By totalCell = By.xpath("//tbody[@id='bhCtTableBody']//tr[td[contains(text(), '" + productName + "')]]//td[@class='bh-ct-tt']");
            return getElementText(totalCell);
        } catch (Exception e) {
            return "";
        }
    }
    
    public int getProductCount() {
        try {
            return Constant.WEBDRIVER.findElements(By.xpath("//tbody[@id='bhCtTableBody']/tr")).size();
        } catch (Exception e) {
            return 0;
        }
    }
    
    public List<WebElement> getAllProducts() {
        return Constant.WEBDRIVER.findElements(By.xpath("//tbody[@id='bhCtTableBody']/tr"));
    }
    
    // Action methods
    public void clickEdit() {
        clickElement(editButton);
    }
    
    public void clickDelete() {
        clickElement(deleteButton);
    }
    
    public boolean isEditButtonDisplayed() {
        return isElementDisplayed(editButton);
    }
    
    public boolean isDeleteButtonDisplayed() {
        return isElementDisplayed(deleteButton);
    }
    
    // Modal control methods
    public void closeModal() {
        clickElement(closeButton);
        try {
            Thread.sleep(500); // Wait for modal to close
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
    
    public SalesPage backToSalesPage() {
        closeModal();
        return new SalesPage(driver, wait);
    }
    
    // Verification methods
    public boolean verifyInvoiceDetails(String expectedCode, String expectedCustomerName, String expectedTotal) {
        String actualCode = getInvoiceCode();
        String actualCustomerName = getCustomerName();
        String actualTotal = getTotalAmount();
        
        return actualCode.equals(expectedCode) && 
               actualCustomerName.equals(expectedCustomerName) && 
               actualTotal.equals(expectedTotal);
    }
    
    public boolean verifyProductInDetails(String productName, String expectedQuantity, String expectedPrice) {
        if (!isProductInTable(productName)) {
            return false;
        }
        
        String actualQuantity = getProductQuantity(productName);
        String actualPrice = getProductUnitPrice(productName);
        
        return actualQuantity.equals(expectedQuantity) && actualPrice.equals(expectedPrice);
    }
    
    // Column verification methods for TC-07 - dựa trên HTML thực tế
    public boolean hasProductCodeColumn() {
        By productCodeHeader = By.xpath("//th[contains(text(), 'Mã hàng hóa')]");
        return isElementDisplayed(productCodeHeader);
    }
    
    public boolean hasProductNameColumn() {
        By productNameHeader = By.xpath("//th[contains(text(), 'Tên hàng hóa')]");
        return isElementDisplayed(productNameHeader);
    }
    
    public boolean hasQuantityColumn() {
        By quantityHeader = By.xpath("//th[contains(text(), 'Số lượng')]");
        return isElementDisplayed(quantityHeader);
    }
    
    public boolean hasUnitPriceColumn() {
        By unitPriceHeader = By.xpath("//th[contains(text(), 'Đơn giá')]");
        return isElementDisplayed(unitPriceHeader);
    }
    
    public boolean hasDiscountColumn() {
        By discountHeader = By.xpath("//th[contains(text(), 'Chiết khấu')]");
        return isElementDisplayed(discountHeader);
    }
    
    public boolean hasTotalColumn() {
        By totalHeader = By.xpath("//th[contains(text(), 'Thành tiền')]");
        return isElementDisplayed(totalHeader);
    }
    
    public boolean hasAllRequiredColumns() {
        // Dựa trên hình ảnh thực tế, chỉ có 5 cột: Mã hàng hóa, Tên hàng hóa, Số lượng, Đơn giá, Thành tiền
        return hasProductCodeColumn() && hasProductNameColumn() && hasQuantityColumn() && 
               hasUnitPriceColumn() && hasTotalColumn();
    }
    
    // Method để kiểm tra có đúng 5 cột như trong hình ảnh
    public boolean hasCorrectColumnStructure() {
        return hasProductCodeColumn() && hasProductNameColumn() && hasQuantityColumn() && 
               hasUnitPriceColumn() && hasTotalColumn();
    }
    
    // Protected methods
    protected WebElement getModalElement() {
        return Constant.WEBDRIVER.findElement(modal);
    }
    
    protected WebElement getCloseButton() {
        return Constant.WEBDRIVER.findElement(closeButton);
    }
    
    protected WebElement getProductTable() {
        return Constant.WEBDRIVER.findElement(productTable);
    }
}