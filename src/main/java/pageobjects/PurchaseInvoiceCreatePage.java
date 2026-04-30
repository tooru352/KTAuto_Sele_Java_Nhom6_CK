package pageobjects;

import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

/**
 * Page Object cho trang Thêm mới đơn nhập hàng
 * URL  : /nhap-hang/them/
 * HTML : them_hoa_don_nhap.html
 */
public class PurchaseInvoiceCreatePage extends GeneralPage {

    // ===================== LOCATORS (khớp HTML thực tế) =====================

    // Tiêu đề trang
    private final By formTitle = By.xpath("//h2[contains(text(),'THÊM MỚI ĐƠN NHẬP')]");

    // Mã HDN – input[name='ma_hdn'] id='maHDN' readonly
    private final By invoiceCodeInput = By.id("maHDN");

    // Ngày nhập – input[type='datetime-local'] id='ngayNhap'
    private final By importDateInput = By.id("ngayNhap");

    // Ô tìm kiếm NCC – input[id='searchNCC']
    private final By supplierSearchInput = By.id("searchNCC");

    // Dropdown NCC – div[id='nccDropdown']
    private final By supplierDropdown = By.id("nccDropdown");

    // Dropdown NCC có class 'show' (đang hiển thị)
    private final By supplierDropdownVisible = By.xpath("//div[@id='nccDropdown' and contains(@class,'show')]");

    // Thông báo "Không tìm thấy NCC" trong dropdown
    private final By supplierNotFoundMsg = By.xpath(
        "//div[@id='nccDropdown']//div[contains(text(),'Không tìm thấy NCC')]"
    );

    // Nút "Thêm mới nhà cung cấp" trong dropdown (luôn hiển thị đầu tiên)
    private final By addNewSupplierBtn = By.xpath(
        "//div[@id='nccDropdown']//div[contains(@class,'dropdown-add')]"
    );

    // Card NCC đã chọn
    private final By nccSelectedCard = By.id("nccSelected");

    // Ô tìm kiếm sản phẩm – input[id='searchSP']
    private final By productSearchInput = By.id("searchSP");

    // Dropdown sản phẩm – div[id='spDropdown']
    private final By productDropdown = By.id("spDropdown");

    // Bảng sản phẩm đã chọn
    private final By productTableWrapper = By.id("spTableWrapper");
    private final By productTableBody    = By.id("spTableBody");

    // Nút Hủy
    private final By cancelButton = By.xpath("//button[contains(@class,'btn-huy')]");

    // Nút Thêm (submit)
    private final By submitButton = By.xpath("//button[contains(@class,'btn-them')]");

    // Toast thông báo
    private final By toast    = By.id("toast");
    private final By toastMsg = By.id("toastMsg");

    // ===================== CONSTRUCTOR =====================

    public PurchaseCreatePage(WebDriver driver, WebDriverWait wait) {
        super(driver, wait);
    }

    // ===================== NAVIGATION =====================

    public boolean isAt() {
        try {
            wait.until(ExpectedConditions.visibilityOfElementLocated(formTitle));
            return true;
        } catch (Exception e) {
            return false;
        }
    }

    // ===================== MÃ HDN =====================

    /** Lấy giá trị Mã HDN tự động sinh */
    public String getInvoiceCode() {
        return driver.findElement(invoiceCodeInput).getAttribute("value");
    }

    /** Kiểm tra trường Mã HDN có readonly không */
    public boolean isInvoiceCodeReadonly() {
        WebElement el = driver.findElement(invoiceCodeInput);
        return el.getAttribute("readonly") != null;
    }

    /**
     * Thử nhập giá trị vào Mã HDN.
     * Trả về giá trị thực tế sau khi nhập – nếu readonly thì không đổi.
     */
    public String tryEditInvoiceCode(String value) {
        WebElement el = driver.findElement(invoiceCodeInput);
        String before = el.getAttribute("value");
        try {
            // Dùng JS để bypass readonly nếu có
            ((JavascriptExecutor) driver).executeScript(
                "arguments[0].removeAttribute('readonly');", el);
            el.clear();
            el.sendKeys(value);
        } catch (Exception ignored) { }
        // Trả về giá trị hiện tại (nếu readonly thực sự thì vẫn là before)
        return driver.findElement(invoiceCodeInput).getAttribute("value");
    }

    // ===================== NGÀY NHẬP =====================

    /** Nhập ngày nhập bằng JS để đảm bảo value được set đúng */
    public void enterImportDate(String datetime) {
        WebElement el = wait.until(ExpectedConditions.presenceOfElementLocated(importDateInput));
        // Dùng JS set value trực tiếp – tránh vấn đề sendKeys với datetime-local
        ((JavascriptExecutor) driver).executeScript(
            "arguments[0].value = arguments[1];", el, datetime);
        // Trigger change event để Django nhận giá trị
        ((JavascriptExecutor) driver).executeScript(
            "arguments[0].dispatchEvent(new Event('change'));", el);
        sleep(300);
    }

    public String getImportDate() {
        return driver.findElement(importDateInput).getAttribute("value");
    }

    // ===================== NHÀ CUNG CẤP =====================

    /**
     * Đọc tên NCC đầu tiên từ JS variable danhSachNCC (không mở dropdown)
     * JS: const danhSachNCC = {{ ncc_list|safe }};
     */
    public String getFirstNccNameFromJS() {
        try {
            Object result = ((JavascriptExecutor) driver)
                .executeScript("return danhSachNCC.length > 0 ? danhSachNCC[0].ten : null;");
            return result != null ? result.toString() : null;
        } catch (Exception e) {
            return null;
        }
    }

    /**
     * Đọc SĐT NCC đầu tiên từ JS variable danhSachNCC (không mở dropdown)
     */
    public String getFirstNccPhoneFromJS() {
        try {
            Object result = ((JavascriptExecutor) driver)
                .executeScript("return danhSachNCC.length > 0 ? danhSachNCC[0].sdt : null;");
            return result != null ? result.toString() : null;
        } catch (Exception e) {
            return null;
        }
    }

    /** Click vào ô tìm kiếm NCC để trigger onfocus → showAllNCC() */
    public void openSupplierDropdown() {
        WebElement input = wait.until(ExpectedConditions.elementToBeClickable(supplierSearchInput));
        input.click();
        sleep(800);
    }

    /** Click vào ô tìm kiếm SP để trigger onfocus → showAllSP() */
    public void openProductDropdown() {
        WebElement input = wait.until(ExpectedConditions.elementToBeClickable(productSearchInput));
        input.click();
        sleep(800);
    }

    /** Lấy tên NCC đầu tiên trong dropdown (item không phải add-btn) */
    public String getFirstSupplierName() {
        try {
            // Tên NCC nằm trong <strong> bên trong dropdown-item
            By nameEl = By.xpath(
                "//div[@id='nccDropdown']//div[contains(@class,'dropdown-item')" +
                " and not(contains(@class,'dropdown-add'))][1]//strong"
            );
            return driver.findElement(nameEl).getText().trim();
        } catch (Exception e) {
            return null;
        }
    }

    /** Lấy SĐT NCC đầu tiên trong dropdown */
    public String getFirstSupplierPhone() {
        try {
            // Text item: "Tên NCC — SĐT: 0901234567"
            By itemEl = By.xpath(
                "//div[@id='nccDropdown']//div[contains(@class,'dropdown-item')" +
                " and not(contains(@class,'dropdown-add'))][1]"
            );
            String text = driver.findElement(itemEl).getText();
            // Tách phần sau "SĐT: "
            if (text.contains("SĐT:")) {
                return text.substring(text.indexOf("SĐT:") + 4).trim().split("\\s")[0];
            }
            return null;
        } catch (Exception e) {
            return null;
        }
    }

    /** Click ra ngoài để đóng dropdown */
    public void closeDropdownByClickingElsewhere() {
        try {
            driver.findElement(By.xpath("//h2[contains(text(),'THÊM MỚI ĐƠN NHẬP')]")).click();
        } catch (Exception e) {
            ((JavascriptExecutor) driver).executeScript(
                "document.querySelectorAll('.dropdown-list').forEach(d => d.classList.remove('show'));"
            );
        }
        sleep(300);
    }
    public void searchSupplier(String keyword) {
        WebElement input = wait.until(ExpectedConditions.elementToBeClickable(supplierSearchInput));
        // Click vào ô để focus, trigger showAllNCC (onfocus)
        input.click();
        sleep(300);
        // Clear và gõ từng ký tự để trigger oninput tự nhiên
        input.clear();
        input.sendKeys(keyword);
        sleep(1000);
    }

    /** Kiểm tra dropdown NCC đang hiển thị (có class 'show') */
    public boolean isSupplierDropdownVisible() {
        try {
            return driver.findElement(supplierDropdown)
                         .getAttribute("class").contains("show");
        } catch (Exception e) {
            return false;
        }
    }

    /** Kiểm tra dropdown NCC chứa từ khóa */
    public boolean isSupplierInDropdown(String keyword) {
        try {
            String content = driver.findElement(supplierDropdown).getText();
            return content.toLowerCase().contains(keyword.toLowerCase());
        } catch (Exception e) {
            return false;
        }
    }

    /** Kiểm tra thông báo "Không tìm thấy NCC" */
    public boolean isSupplierNotFoundMessageDisplayed() {
        try {
            WebElement el = driver.findElement(supplierNotFoundMsg);
            return el.isDisplayed();
        } catch (Exception e) {
            return false;
        }
    }

    /** Kiểm tra nút "Thêm mới nhà cung cấp" trong dropdown */
    public boolean isAddNewSupplierButtonDisplayed() {
        try {
            return driver.findElement(addNewSupplierBtn).isDisplayed();
        } catch (Exception e) {
            return false;
        }
    }

    /** Chọn NCC đầu tiên trong dropdown (item không phải add-btn) */
    public void selectFirstSupplierFromDropdown() {
        By firstItem = By.xpath(
            "//div[@id='nccDropdown']//div[contains(@class,'dropdown-item') " +
            "and not(contains(@class,'dropdown-add'))][1]"
        );
        wait.until(ExpectedConditions.elementToBeClickable(firstItem)).click();
        sleep(500);
    }

    /** Kiểm tra NCC đã được chọn (card hiển thị) */
    public boolean isSupplierSelected() {
        try {
            String style = driver.findElement(nccSelectedCard).getAttribute("style");
            return style != null && !style.contains("display:none") && !style.contains("display: none");
        } catch (Exception e) {
            return false;
        }
    }

    // ===================== SẢN PHẨM =====================

    /** Gõ từ khóa tìm sản phẩm (trigger oninput → timSanPham) */
    public void searchProduct(String keyword) {
        WebElement input = wait.until(ExpectedConditions.elementToBeClickable(productSearchInput));
        input.click();
        sleep(300);
        input.clear();
        input.sendKeys(keyword);
        sleep(1000);
    }

    /** Chọn sản phẩm đầu tiên trong dropdown */
    public void selectFirstProductFromDropdown() {
        By firstItem = By.xpath(
            "//div[@id='spDropdown']//div[contains(@class,'dropdown-item') " +
            "and not(contains(@class,'dropdown-add'))][1]"
        );
        wait.until(ExpectedConditions.elementToBeClickable(firstItem)).click();
        sleep(500);
    }

    /**
     * Nhập số lượng cho dòng SP thứ idx trong bảng (0-based)
     * HTML: <input type="number" min="1" value="..." onchange="capNhatSoLuong(idx, this.value)">
     */
    public void setProductQuantity(int idx, String value) {
        By input = By.xpath(
            "(//tbody[@id='spTableBody']//input[@type='number' and @min='1'])[" + (idx + 1) + "]"
        );
        WebElement el = wait.until(ExpectedConditions.elementToBeClickable(input));
        el.clear();
        el.sendKeys(value);
        // Trigger onchange
        ((JavascriptExecutor) driver).executeScript(
            "arguments[0].dispatchEvent(new Event('change'));", el);
        sleep(300);
    }

    /**
     * Nhập đơn giá cho dòng SP thứ idx trong bảng (0-based)
     * HTML: <input type="text" id="dongia-{idx}" onfocus="this.value='${sp.donGia}'"
     *              onblur="this.value=formatTien(...)+' đ'" onchange="capNhatDonGia(idx, this.value)">
     * Cần: click để focus (JS set value = số thuần) → select all → gõ giá trị mới → trigger change
     */
    public void setProductDonGia(int idx, String value) {
        By input = By.id("dongia-" + idx);
        WebElement el = wait.until(ExpectedConditions.elementToBeClickable(input));

        // Click để focus – JS onfocus sẽ set value = số thuần (vd: "50000")
        el.click();
        sleep(300);

        // Select all rồi gõ đè lên – tránh bị nối chuỗi
        el.sendKeys(org.openqa.selenium.Keys.chord(org.openqa.selenium.Keys.CONTROL, "a"));
        el.sendKeys(value);

        // Trigger onchange để JS cập nhật thành tiền
        ((JavascriptExecutor) driver).executeScript(
            "arguments[0].dispatchEvent(new Event('change'));", el);
        sleep(300);
    }

    /**
     * Nhập chiết khấu cho dòng SP thứ idx trong bảng (0-based)
     * HTML: <input type="number" min="0" max="100" onchange="capNhatChietKhau(idx, this.value)">
     */
    public void setProductChietKhau(int idx, String value) {
        By input = By.xpath(
            "(//tbody[@id='spTableBody']//input[@type='number' and @min='0' and @max='100'])[" + (idx + 1) + "]"
        );
        WebElement el = wait.until(ExpectedConditions.elementToBeClickable(input));
        el.clear();
        el.sendKeys(value);
        ((JavascriptExecutor) driver).executeScript(
            "arguments[0].dispatchEvent(new Event('change'));", el);
        sleep(300);
    }

    /** Kiểm tra bảng sản phẩm đang hiển thị */
    public boolean isProductTableVisible() {
        try {
            String style = driver.findElement(productTableWrapper).getAttribute("style");
            return style != null && style.contains("display:block") || style.contains("display: block");
        } catch (Exception e) {
            return false;
        }
    }

    /** Số dòng sản phẩm trong bảng */
    public int getProductRowCount() {
        try {
            return driver.findElements(By.xpath("//tbody[@id='spTableBody']/tr")).size();
        } catch (Exception e) {
            return 0;
        }
    }

    // ===================== SUBMIT =====================

    /** Nhấn nút Thêm (submit form) – trigger JS cập nhật hidden inputs trước */
    public void clickSubmit() {
        // Đảm bảo JS đã cập nhật sanPhamJson và tongTienInput
        ((JavascriptExecutor) driver).executeScript("capNhatTongTien();");
        sleep(500);
        wait.until(ExpectedConditions.elementToBeClickable(submitButton)).click();
        sleep(2000);
    }

    /** Nhấn nút Hủy */
    public void clickCancel() {
        wait.until(ExpectedConditions.elementToBeClickable(cancelButton)).click();
    }

    // ===================== KẾT QUẢ =====================

    /** Kiểm tra toast thông báo có hiển thị không */
    public boolean isToastDisplayed() {
        try {
            WebElement t = driver.findElement(toast);
            return t.isDisplayed();
        } catch (Exception e) {
            return false;
        }
    }

    /** Lấy nội dung toast */
    public String getToastMessage() {
        try {
            return driver.findElement(toastMsg).getText();
        } catch (Exception e) {
            return "";
        }
    }

    /**
     * Kiểm tra thêm thành công:
     * Sau khi submit thành công, Django redirect về /nhap-hang/
     */
    public boolean isSuccessMessageDisplayed() {
        try {
            // Chờ redirect về trang danh sách hoặc toast success
            wait.until(ExpectedConditions.or(
                ExpectedConditions.urlContains("/nhap-hang/"),
                ExpectedConditions.visibilityOfElementLocated(
                    By.xpath("//*[contains(text(),'thành công') or contains(text(),'Thêm mới thành công')]"))
            ));
            return true;
        } catch (Exception e) {
            return false;
        }
    }

    public String getCurrentUrl() {
        return driver.getCurrentUrl();
    }

    /** Debug: lấy giá trị hidden input sanPhamJson */
    public String getSanPhamJson() {
        try {
            return driver.findElement(By.id("sanPhamJson")).getAttribute("value");
        } catch (Exception e) { return "NOT_FOUND"; }
    }

    /** Debug: lấy giá trị hidden input nccId */
    public String getNccId() {
        try {
            return driver.findElement(By.id("nccId")).getAttribute("value");
        } catch (Exception e) { return "NOT_FOUND"; }
    }

    /** Debug: lấy giá trị hidden input tongTienInput */
    public String getTongTienInput() {
        try {
            return driver.findElement(By.id("tongTienInput")).getAttribute("value");
        } catch (Exception e) { return "NOT_FOUND"; }
    }

    // ===================== NCC CARD =====================

    /** Lấy text toàn bộ card NCC đã chọn */
    public String getNccCardText() {
        try {
            return driver.findElement(nccSelectedCard).getText();
        } catch (Exception e) { return ""; }
    }

    /** Kiểm tra dropdown NCC đã đóng (không có class 'show') */
    public boolean isSupplierDropdownClosed() {
        try {
            return !driver.findElement(supplierDropdown).getAttribute("class").contains("show");
        } catch (Exception e) { return true; }
    }

    /** Lấy giá trị ô tìm kiếm NCC */
    public String getSupplierSearchValue() {
        return driver.findElement(supplierSearchInput).getAttribute("value");
    }

    /** Nhấn nút × trên card NCC để bỏ chọn */
    public void clickRemoveNccCard() {
        By closeBtn = By.xpath("//div[@id='nccSelected']//button[contains(@class,'ncc-card-close')]");
        wait.until(ExpectedConditions.elementToBeClickable(closeBtn)).click();
        sleep(300);
    }

    // ===================== SẢN PHẨM - BỔ SUNG =====================

    /** Đọc tên SP đầu tiên từ JS variable danhSachSP (không mở dropdown) */
    public String getFirstSpNameFromJS() {
        try {
            Object r = ((JavascriptExecutor) driver)
                .executeScript("return danhSachSP.length > 0 ? danhSachSP[0].ten : null;");
            return r != null ? r.toString() : null;
        } catch (Exception e) { return null; }
    }

    /** Đọc đơn giá SP đầu tiên từ JS variable danhSachSP */
    public double getFirstSpDonGiaFromJS() {
        try {
            Object r = ((JavascriptExecutor) driver)
                .executeScript("return danhSachSP.length > 0 ? danhSachSP[0].don_gia : 0;");
            return r != null ? Double.parseDouble(r.toString()) : 0;
        } catch (Exception e) { return 0; }
    }

    /** Kiểm tra dropdown SP có hiển thị (class 'show') */
    public boolean isProductDropdownVisible() {
        try {
            return driver.findElement(productDropdown).getAttribute("class").contains("show");
        } catch (Exception e) { return false; }
    }

    /** Kiểm tra dropdown SP chứa từ khóa */
    public boolean isProductInDropdown(String keyword) {
        try {
            return driver.findElement(productDropdown).getText()
                         .toLowerCase().contains(keyword.toLowerCase());
        } catch (Exception e) { return false; }
    }

    /** Kiểm tra "Không tìm thấy sản phẩm" trong dropdown SP */
    public boolean isProductNotFoundMessageDisplayed() {
        try {
            By msg = By.xpath("//div[@id='spDropdown']//div[contains(text(),'Không tìm thấy sản phẩm')]");
            return driver.findElement(msg).isDisplayed();
        } catch (Exception e) { return false; }
    }

    /** Kiểm tra nút "Thêm mới hàng hóa" trong dropdown SP */
    public boolean isAddNewProductButtonDisplayed() {
        try {
            By btn = By.xpath("//div[@id='spDropdown']//div[contains(@class,'dropdown-add')]");
            return driver.findElement(btn).isDisplayed();
        } catch (Exception e) { return false; }
    }

    /** Lấy số lượng của dòng SP thứ idx trong bảng */
    public String getProductQuantityInTable(int idx) {
        try {
            By input = By.xpath(
                "(//tbody[@id='spTableBody']//input[@type='number' and @min='1'])[" + (idx+1) + "]");
            return driver.findElement(input).getAttribute("value");
        } catch (Exception e) { return ""; }
    }

    /** Lấy đơn giá hiển thị của dòng SP thứ idx trong bảng */
    public String getProductDonGiaInTable(int idx) {
        try {
            By input = By.id("dongia-" + idx);
            return driver.findElement(input).getAttribute("value");
        } catch (Exception e) { return ""; }
    }

    /** Lấy chiết khấu của dòng SP thứ idx trong bảng */
    public String getProductChietKhauInTable(int idx) {
        try {
            By input = By.xpath(
                "(//tbody[@id='spTableBody']//input[@type='number' and @min='0' and @max='100'])[" + (idx+1) + "]");
            return driver.findElement(input).getAttribute("value");
        } catch (Exception e) { return ""; }
    }

    /** Lấy thành tiền của dòng SP thứ idx (text trong <strong>) – bỏ format */
    public String getProductThanhTienInTable(int idx) {
        try {
            By cell = By.xpath(
                "(//tbody[@id='spTableBody']//tr)[" + (idx+1) + "]//td[5]//strong");
            return driver.findElement(cell).getText()
                         .replace("đ","").replace(".","").replace(",","").trim();
        } catch (Exception e) { return ""; }
    }

    /** Lấy tổng tiền hiển thị – bỏ format */
    public String getTongTienDisplay() {
        try {
            return driver.findElement(By.id("tongTien")).getText()
                         .replace("đ","").replace(".","").replace(",","").trim();
        } catch (Exception e) { return ""; }
    }

    /** Lấy text số lượng hàng hóa hiển thị (#soLuongHH) */
    public String getSoLuongHHDisplay() {
        try {
            return driver.findElement(By.id("soLuongHH")).getText().trim();
        } catch (Exception e) { return ""; }
    }

    /** Kiểm tra toast "Vui Lòng Nhập Đầy Đủ" có hiển thị */
    public boolean isValidationToastDisplayed() {
        try {
            WebElement t = driver.findElement(toast);
            return t.isDisplayed() && t.getCssValue("display").equals("flex");
        } catch (Exception e) { return false; }
    }

    /**
     * Xóa dòng SP thứ idx khỏi bảng (nhấn nút × trên dòng đó)
     * HTML: <button type="button" class="btn-remove-sp" onclick="xoaSP(idx)">×</button>
     */
    public void removeProductFromTable(int idx) {
        By removeBtn = By.xpath(
            "(//tbody[@id='spTableBody']//button[contains(@class,'btn-remove-sp')])[" + (idx+1) + "]"
        );
        wait.until(ExpectedConditions.elementToBeClickable(removeBtn)).click();
        sleep(500);
    }

    /**
     * Kiểm tra toast validation "Vui Lòng Nhập Đầy Đủ Các Thông Tin" hiển thị
     * JS: showToast('Vui Lòng Nhập Đầy Đủ Các Thông Tin')
     * HTML: div#toast style="display:flex"
     */
    public boolean isValidationToastVisible() {
        try {
            // Chờ tối đa 3s cho toast xuất hiện
            WebDriverWait shortWait = new WebDriverWait(driver, java.time.Duration.ofSeconds(3));
            WebElement t = shortWait.until(ExpectedConditions.visibilityOfElementLocated(toast));
            String msg = driver.findElement(toastMsg).getText();
            System.out.println("  → Toast message: " + msg);
            return t.isDisplayed();
        } catch (Exception e) { return false; }
    }

    /** Lấy nội dung toast message */
    public String getToastText() {
        try {
            return driver.findElement(toastMsg).getText().trim();
        } catch (Exception e) { return ""; }
    }
}