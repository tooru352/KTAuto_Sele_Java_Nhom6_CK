package pageobjects;

import common.Constant;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;

import java.util.ArrayList;
import java.util.List;

public class SalesPage extends GeneralPage {
    
    // XPath cho navigation và trang bán hàng chính
    
    // Navigation menu
    private final By homeMenuLink = By.xpath("//a[@href='/trang-chu/']");
    private final By salesMenuLink = By.xpath("//a[@href='/ban-hang/']");
    private final By importMenuLink = By.xpath("//a[@href='/nhap-hang/']");
    private final By supplierMenuLink = By.xpath("//a[@href='/nha-cung-cap/']");
    private final By productMenuLink = By.xpath("//a[@href='/hang-hoa/']");
    private final By categoryMenuLink = By.xpath("//a[@href='/loai-hang/']");
    private final By logoutLink = By.xpath("//a[@href='/logout/']");
    
    // Sales page specific elements
    private final By addInvoiceButton = By.xpath("//button[@id='btnThemHoaDon']");
    private final By salesPageTitle = By.xpath("//h2[contains(text(), 'TẠO HÓA ĐƠN BÁN')]");
    
    // Search functionality
    private final By searchForm = By.xpath("//form[@class='search-box-bh']");
    private final By searchInput = By.xpath("//input[@id='searchInput']");
    private final By searchButton = By.xpath("//button[@class='btn-tim-kiem']");
    
    public SalesPage(org.openqa.selenium.WebDriver driver, org.openqa.selenium.support.ui.WebDriverWait wait) {
        super(driver, wait);
    }
    
    // Invoice table
    private final By invoiceTable = By.xpath("//table[@class='hoa-don-table']");
    private final By invoiceTableBody = By.xpath("//table[@class='hoa-don-table']/tbody");
    private final By invoiceTableHeader = By.xpath("//table[@class='hoa-don-table']/thead");
    
    // Logo and branding
    private final By logoSection = By.xpath("//div[@class='logo']");
    private final By companyName = By.xpath("//h2[text()='CONU']");
    private final By companySubtitle = By.xpath("//p[@class='subtitle'][text()='BOOKSTORE']");
    
    // Method để kiểm tra có đang ở trang bán hàng không
    public boolean isAt() {
        return isElementDisplayed(salesMenuLink) && 
               Constant.WEBDRIVER.findElement(salesMenuLink).getAttribute("class").contains("active");
    }
    
    // Navigation methods
    public void navigateToHome() {
        clickElement(homeMenuLink);
    }
    
    public SalesPage gotoSalesPage() {
        clickElement(salesMenuLink);
        try {
            Thread.sleep(2000); // Wait for page to load
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        return new SalesPage(driver, wait);
    }
    
    public InvoiceCreatePage gotoCreateInvoice() {
        try {
            Thread.sleep(1000); // Wait for button to be available
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        clickElement(addInvoiceButton);
        return new InvoiceCreatePage(driver, wait);
    }
    
    public void navigateToImport() {
        clickElement(importMenuLink);
    }
    
    public void navigateToSupplier() {
        clickElement(supplierMenuLink);
    }
    
    public void navigateToProduct() {
        clickElement(productMenuLink);
    }
    
    public void navigateToCategory() {
        clickElement(categoryMenuLink);
    }
    
    public void logout() {
        clickElement(logoutLink);
    }
    
    // Sales page actions
    public void clickAddInvoice() {
        clickElement(addInvoiceButton);
    }
    
    public boolean isAddInvoiceButtonDisplayed() {
        return isElementDisplayed(addInvoiceButton);
    }
    
    public boolean isSalesPageTitleDisplayed() {
        return isElementDisplayed(salesPageTitle);
    }
    
    // Search functionality methods
    public void searchInvoice(String searchTerm) {
        enterText(searchInput, searchTerm);
    }
    
    public void clickSearchButton() {
        clickElement(searchButton);
    }
    
    public void performSearch(String searchTerm) {
        searchInvoice(searchTerm);
        clickSearchButton();
        try {
            Thread.sleep(1000); // Wait for search results
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
    
    public void clearSearch() {
        enterText(searchInput, ""); // Clear using enterText with empty string
        clickSearchButton(); // Trigger search with empty value
    }
    
    public String getSearchInputValue() {
        return Constant.WEBDRIVER.findElement(searchInput).getAttribute("value");
    }
    
    // Invoice table methods
    public boolean isInvoiceTableDisplayed() {
        return isElementDisplayed(invoiceTable);
    }
    
    public boolean isInvoiceInTable(String invoiceCode) {
        try {
            Thread.sleep(1000);
            
            // Sử dụng XPath đã được xác nhận hoạt động
            By invoiceSelector = By.xpath("//tr[@onclick=\"xemChiTietHoaDon('" + invoiceCode + "')\"]");
            List<WebElement> elements = Constant.WEBDRIVER.findElements(invoiceSelector);
            
            for (WebElement element : elements) {
                if (element.isDisplayed()) {
                    return true;
                }
            }
            
            return false;
            
        } catch (InterruptedException e) {
            e.printStackTrace();
            return false;
        }
    }
    
    // Search result methods
    private final By noResultMessage = By.xpath("//tr[@id='emptySearchRow']");
    private final By emptySearchText = By.xpath("//tr[@id='emptySearchRow']//td");
    
    public boolean isNoResultMessageDisplayed() {
        try {
            // Kiểm tra xem emptySearchRow có hiển thị không (không có display: none)
            WebElement emptyRow = Constant.WEBDRIVER.findElement(noResultMessage);
            String style = emptyRow.getAttribute("style");
            return style == null || !style.contains("display: none");
        } catch (Exception e) {
            return false;
        }
    }
    
    public String getNoResultMessage() {
        return getElementText(emptySearchText);
    }
    
    public boolean allInvoicesContain(String searchText) {
        try {
            List<WebElement> allRows = Constant.WEBDRIVER.findElements(By.xpath("//tbody//tr"));
            List<WebElement> visibleRows = new ArrayList<>();
            
            // Lọc các row thực sự hiển thị
            for (WebElement row : allRows) {
                String id = row.getAttribute("id");
                if (!"emptySearchRow".equals(id) && row.isDisplayed()) {
                    visibleRows.add(row);
                }
            }
            
            if (visibleRows.isEmpty()) {
                return false;
            }
            
            for (WebElement row : visibleRows) {
                String rowText = row.getText().toLowerCase();
                if (!rowText.contains(searchText.toLowerCase())) {
                    return false;
                }
            }
            return true;
        } catch (Exception e) {
            return false;
        }
    }
    
    public void clickInvoiceRow(String invoiceCode) {
        By invoiceRow = By.xpath("//tr[@onclick=\"xemChiTietHoaDon('" + invoiceCode + "')\"]");
        clickElement(invoiceRow);
        try {
            Thread.sleep(1000); // Wait for modal to open
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
    
    public InvoiceDetailPage viewInvoiceDetail(String invoiceCode) {
        By invoiceRow = By.xpath("//tr[@onclick=\"xemChiTietHoaDon('" + invoiceCode + "')\"]");
        clickElement(invoiceRow);
        try {
            Thread.sleep(1000); // Wait for modal to open
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        return new InvoiceDetailPage(driver, wait);
    }
    
    public int getInvoiceCount() {
        try {
            // Đếm các row thực sự hiển thị (isDisplayed = true)
            List<WebElement> allRows = Constant.WEBDRIVER.findElements(By.xpath("//tbody//tr"));
            int visibleCount = 0;
            
            for (WebElement row : allRows) {
                String id = row.getAttribute("id");
                // Bỏ qua emptySearchRow và chỉ đếm row hiển thị
                if (!"emptySearchRow".equals(id) && row.isDisplayed()) {
                    visibleCount++;
                }
            }
            
            return visibleCount;
        } catch (Exception e) {
            return 0;
        }
    }
    
    public String getInvoiceDate(String invoiceCode) {
        try {
            By dateCell = By.xpath("//tr[@onclick=\"xemChiTietHoaDon('" + invoiceCode + "')\"]//td[2]");
            return getElementText(dateCell);
        } catch (Exception e) {
            return "";
        }
    }
    
    public String getInvoiceCustomerPhone(String invoiceCode) {
        try {
            By phoneCell = By.xpath("//tr[@onclick=\"xemChiTietHoaDon('" + invoiceCode + "')\"]//td[3]");
            return getElementText(phoneCell);
        } catch (Exception e) {
            return "";
        }
    }
    
    public String getInvoiceTotalAmount(String invoiceCode) {
        try {
            By totalCell = By.xpath("//tr[@onclick=\"xemChiTietHoaDon('" + invoiceCode + "')\"]//td[4]");
            return getElementText(totalCell);
        } catch (Exception e) {
            return "";
        }
    }
    
    public String getInvoiceStatus(String invoiceCode) {
        try {
            By statusCell = By.xpath("//tr[@onclick=\"xemChiTietHoaDon('" + invoiceCode + "')\"]//td[5]//span");
            return getElementText(statusCell);
        } catch (Exception e) {
            return "";
        }
    }
    
    // Utility methods
    public String getCurrentActiveMenu() {
        try {
            WebElement activeMenu = Constant.WEBDRIVER.findElement(By.xpath("//nav[@class='sidebar']//a[@class='active']"));
            return activeMenu.getText();
        } catch (Exception e) {
            return "";
        }
    }
    
    public boolean isMenuActive(String menuName) {
        By menuLocator = By.xpath("//nav[@class='sidebar']//a[contains(text(), '" + menuName + "')][@class='active']");
        return isElementDisplayed(menuLocator);
    }
    
    // Protected methods để lấy elements
    protected WebElement getAddInvoiceButton() {
        return Constant.WEBDRIVER.findElement(addInvoiceButton);
    }
    
    protected WebElement getSalesMenuLink() {
        return Constant.WEBDRIVER.findElement(salesMenuLink);
    }
    
    protected WebElement getLogoutLink() {
        return Constant.WEBDRIVER.findElement(logoutLink);
    }
}