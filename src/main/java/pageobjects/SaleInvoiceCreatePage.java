package pageobjects;

import common.Constant;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;

import java.util.List;

public class InvoiceCreatePage extends GeneralPage {
    
    // XPath chính xác cho form tạo hóa đơn
    
    // Form elements - Invoice Info (Left Panel)
    private final By invoiceCodeInput = By.xpath("//input[@name='ma_hoa_don']");
    private final By paymentMethodSelect = By.xpath("//select[@name='thanh_toan']");
    private final By saleDateInput = By.xpath("//input[@id='inputNgayBan']");
    private final By discountInput = By.xpath("//input[@name='chiet_khau']");
    private final By totalAmountDisplay = By.xpath("//input[@id='tongTienHienThi']");

    // Form buttons
    private final By cancelButton = By.xpath("//button[@class='thb-btn-huy']");
    private final By confirmPaymentButton = By.xpath("//button[@class='thb-btn-xacnhan']");
    
    // Product search
    private final By productSearchInput = By.xpath("//input[@id='searchSP']");
    private final By addProductButton = By.xpath("//button[@class='thb-btn-them']");
    private final By productDropdown = By.xpath("//div[@id='spDropdown']");
    
    // Customer Info (Right Panel)
    private final By customerPhoneInput = By.xpath("//input[@name='sdt_khach_hang']");
    
    public InvoiceCreatePage(org.openqa.selenium.WebDriver driver, org.openqa.selenium.support.ui.WebDriverWait wait) {
        super(driver, wait);
    }
    private final By customerCodeDisplay = By.xpath("//input[@id='dispMaKH']");
    private final By customerNameDisplay = By.xpath("//input[@id='dispTenKH']");
    private final By customerAddressDisplay = By.xpath("//input[@id='dispDiaChiKH']");
    private final By addNewCustomerLink = By.xpath("//a[@class='thb-link-new']");
    private final By phoneErrorMessage = By.xpath("//span[@id='sdtError']");
    
    // Product table
    private final By productTable = By.xpath("//table[@class='thb-table']");
    private final By productTableBody = By.xpath("//tbody[@id='spTableBody']");
    
    // Success message
    private final By successMessage = By.xpath("//div[@class='alert alert-success']");
    
    // Hidden inputs
    private final By totalAmountHiddenInput = By.xpath("//input[@name='tong_tien']");
    private final By statusHiddenInput = By.xpath("//input[@name='trang_thai']");
    
    // Form title
    private final By formTitle = By.xpath("//h2[contains(text(), 'TẠO HÓA ĐƠN BÁN')]");
    
    // Method để kiểm tra có đang ở trang tạo hóa đơn không
    public boolean isAt() {
        return isElementDisplayed(formTitle) && isElementDisplayed(invoiceCodeInput);
    }
    
    // Invoice info methods
    public String getInvoiceCode() {
        return Constant.WEBDRIVER.findElement(invoiceCodeInput).getAttribute("value");
    }
    
    public void selectPaymentMethod(String method) {
        clickElement(paymentMethodSelect);
        By paymentOption = By.xpath("//option[@value='" + method + "']");
        clickElement(paymentOption);
    }
    
    public void enterDiscount(String discount) {
        enterText(discountInput, discount);
    }
    
    public String getTotalAmount() {
        try {
            Thread.sleep(1000); // Wait for calculation
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        return Constant.WEBDRIVER.findElement(totalAmountDisplay).getAttribute("value");
    }
    
    public String getSaleDate() {
        return Constant.WEBDRIVER.findElement(saleDateInput).getAttribute("value");
    }
    
    public boolean isSaleDateReadonly() {
        String readonlyAttr = Constant.WEBDRIVER.findElement(saleDateInput).getAttribute("readonly");
        return readonlyAttr != null && (readonlyAttr.equals("true") || readonlyAttr.equals("readonly"));
    }
    
    // Customer info methods
    public void enterCustomerPhone(String phone) {
        enterText(customerPhoneInput, phone);
    }
    
    public String getCustomerCode() {
        try {
            Thread.sleep(1000); // Wait for auto-fill
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        return Constant.WEBDRIVER.findElement(customerCodeDisplay).getAttribute("value");
    }
    
    public String getCustomerName() {
        return Constant.WEBDRIVER.findElement(customerNameDisplay).getAttribute("value");
    }
    
    public String getCustomerAddress() {
        return Constant.WEBDRIVER.findElement(customerAddressDisplay).getAttribute("value");
    }
    
    public boolean isPhoneErrorDisplayed() {
        return isElementDisplayed(phoneErrorMessage);
    }
    
    public void clickAddNewCustomer() {
        clickElement(addNewCustomerLink);
    }
    
    // Product search methods
    public void searchProduct(String productName) {
        enterText(productSearchInput, productName);
        try {
            Thread.sleep(1000); // Wait for search results
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
    
    public void clickAddProduct() {
        clickElement(addProductButton);
    }
    
    public boolean isProductDropdownDisplayed() {
        return isElementDisplayed(productDropdown);
    }
    
    public void selectProductFromDropdown(String productName) {
        By productOption = By.xpath("//div[@id='spDropdown']//div[contains(text(), '" + productName + "')]");
        clickElement(productOption);
    }
    
    // Form action methods
    public void clickCancel() {
        clickElement(cancelButton);
    }
    
    public boolean isCancelButtonDisplayed() {
        return isElementDisplayed(cancelButton);
    }
    
    public void clickConfirmPayment() {
        clickElement(confirmPaymentButton);
    }
    
    // Success verification
    public boolean isSuccessMessageDisplayed() {
        return isElementDisplayed(successMessage);
    }
    
    public String getSuccessMessage() {
        return getElementText(successMessage);
    }
    
    // Product table methods
    public boolean isProductTableDisplayed() {
        return isElementDisplayed(productTable);
    }
    
    public int getProductCount() {
        try {
            return Constant.WEBDRIVER.findElements(By.xpath("//tbody[@id='spTableBody']/tr")).size();
        } catch (Exception e) {
            return 0;
        }
    }
    
    // Methods for product search and dropdown
    public boolean isProductDropdownEmpty() {
        try {
            Thread.sleep(1000); // Wait for dropdown to load
            String dropdownText = getElementText(productDropdown);
            return dropdownText.contains("Không tìm thấy sản phẩm") || dropdownText.trim().isEmpty();
        } catch (Exception e) {
            return true;
        }
    }
    
    public boolean isProductInDropdown(String productName) {
        try {
            Thread.sleep(1000); // Wait for dropdown to load
            
            // Kiểm tra dropdown có hiển thị không
            By productInDropdown = By.xpath("//div[@id='spDropdown']");
            boolean dropdownVisible = isElementDisplayed(productInDropdown);
            
            if (dropdownVisible) {
                String dropdownContent = getElementText(productInDropdown);
                return dropdownContent.toLowerCase().contains(productName.toLowerCase());
            }
            
            return false;
        } catch (Exception e) {
            return false;
        }
    }
    
    public void selectSpecificProductFromDropdown(String exactProductName) {
        try {
            Thread.sleep(1000);
            
            // XPath chính xác theo cấu trúc HTML thực tế
            By productOption = By.xpath("//div[@id='spDropdown']//div[@class='thb-dd-item'][.//span[contains(text(), '" + exactProductName + "')]]");
            
            // Kiểm tra element có tồn tại không
            if (isElementDisplayed(productOption)) {
                clickElement(productOption);
            } else {
                // Fallback: thử XPath đơn giản hơn
                By fallbackOption = By.xpath("//div[@class='thb-dd-item'][contains(., '" + exactProductName + "')]");
                clickElement(fallbackOption);
            }
            
        } catch (Exception e) {
            // Fallback: click item đầu tiên trong dropdown
            try {
                List<WebElement> allItems = Constant.WEBDRIVER.findElements(By.xpath("//div[@id='spDropdown']//div[@class='thb-dd-item']"));
                if (!allItems.isEmpty()) {
                    allItems.get(0).click();
                }
            } catch (Exception debugError) {
                // Silent fail
            }
            
            throw e;
        }
    }
    
    public int getProductStockFromDropdown(String productName) {
        try {
            Thread.sleep(1000);
            
            // Tìm element chứa thông tin tồn kho: (Tồn: 150)
            By stockElement = By.xpath("//div[@id='spDropdown']//div[@class='thb-dd-item'][.//span[contains(text(), '" + productName + "')]]//span[contains(text(), 'Tồn:')]");
            
            if (isElementDisplayed(stockElement)) {
                String stockText = getElementText(stockElement);
                // Extract số từ text "(Tồn: 150)" -> 150
                String stockNumber = stockText.replaceAll(".*Tồn:\\s*(\\d+).*", "$1");
                return Integer.parseInt(stockNumber);
            }
            
            return -1; // Không tìm thấy thông tin tồn kho
        } catch (Exception e) {
            return -1;
        }
    }
    
    public String getSearchInputValue() {
        return Constant.WEBDRIVER.findElement(productSearchInput).getAttribute("value");
    }
    
    public void clearSearchInput() {
        Constant.WEBDRIVER.findElement(productSearchInput).clear();
    }
    
    // Methods for product table operations
    public boolean isProductInTable(String productName) {
        try {
            By productInTable = By.xpath("//tbody[@id='spTableBody']//td[contains(text(), '" + productName + "')]");
            return isElementDisplayed(productInTable);
        } catch (Exception e) {
            return false;
        }
    }
    
    public String getProductQuantityInTable(String productName) {
        try {
            // Tìm row chứa sản phẩm, sau đó lấy input số lượng
            By quantityInput = By.xpath("//tbody[@id='spTableBody']//tr[td[contains(text(), '" + productName + "')]]//input[@name='so_luong[]']");
            return Constant.WEBDRIVER.findElement(quantityInput).getAttribute("value");
        } catch (Exception e) {
            return "0";
        }
    }
    
    public String getProductPriceInTable(String productName) {
        try {
            // Lấy text từ td có class td-dg
            By priceCell = By.xpath("//tbody[@id='spTableBody']//tr[td[contains(text(), '" + productName + "')]]//td[@class='td-dg']");
            return getElementText(priceCell);
        } catch (Exception e) {
            return "0";
        }
    }
    
    public String getProductTotalInTable(String productName) {
        try {
            // Lấy text từ td có class td-tt
            By totalCell = By.xpath("//tbody[@id='spTableBody']//tr[td[contains(text(), '" + productName + "')]]//td[@class='td-tt']");
            return getElementText(totalCell);
        } catch (Exception e) {
            return "0";
        }
    }
    
    public void removeProductFromTable(String productName) {
        try {
            By removeButton = By.xpath("//tbody[@id='spTableBody']//tr[td[contains(text(), '" + productName + "')]]//button[@class='thb-btn-xoa-row']");
            clickElement(removeButton);
        } catch (Exception e) {
            // Silent fail
        }
    }
    
    public void updateProductQuantity(String productName, String newQuantity) {
        try {
            By quantityInput = By.xpath("//tbody[@id='spTableBody']//tr[td[contains(text(), '" + productName + "')]]//input[@name='so_luong[]']");
            enterText(quantityInput, newQuantity);
        } catch (Exception e) {
            // Silent fail
        }
    }
    
    // Comprehensive method to create invoice
    public void createInvoice(String customerPhone, String productName, String paymentMethod, String discount) {
        // Enter customer phone
        enterCustomerPhone(customerPhone);
        
        // Search and add product
        searchProduct(productName);
        clickAddProduct();
        
        // Set payment method
        if (paymentMethod != null && !paymentMethod.isEmpty()) {
            selectPaymentMethod(paymentMethod);
        }
        
        // Apply discount if provided
        if (discount != null && !discount.isEmpty()) {
            enterDiscount(discount);
        }
        
        // Confirm payment
        clickConfirmPayment();
    }
    
    // Method to create simple invoice with minimal data
    public void createSimpleInvoice(String customerPhone, String productName) {
        createInvoice(customerPhone, productName, Constant.PAYMENT_METHOD_CASH, null);
    }
}