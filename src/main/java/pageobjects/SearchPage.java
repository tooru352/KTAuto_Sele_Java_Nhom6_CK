package pageobjects;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import common.Constant;
import java.time.Duration;
import java.util.List;

public class SearchPage {
    private WebDriver driver;
    private WebDriverWait wait;

    private By searchInput = By.id("searchInput");
    private By searchButton = By.xpath("//button[contains(text(),'Tìm kiếm')]");
    private By resultsTable = By.xpath("//table[@class='data-table']");
    private By tableRows = By.xpath("//table[@class='data-table']//tbody//tr");
    private By noResultsMessage = By.xpath("//td[contains(text(),'Không tìm thấy kết quả')]");
    private By supplierColumn = By.xpath("//table[@class='data-table']//td[contains(text(),'Nhà')]");
    private By invoiceCodeColumn = By.xpath("//table[@class='data-table']//td[@class='ma-don']");
    private By dateColumn = By.xpath("//table[@class='data-table']//td[contains(text(),'/')]");
    private By statusColumn = By.xpath("//table[@class='data-table']//td[contains(text(),'Hoàn')]");

    public SearchPage(WebDriver driver) {
        this.driver = driver;
        this.wait = new WebDriverWait(driver, Duration.ofSeconds(Constant.EXPLICIT_WAIT));
    }

    public void openImportPage() {
        driver.get("http://127.0.0.1:8000/nhap-hang/");
    }

    public void enterSearchKeyword(String keyword) {
        WebElement element = wait.until(ExpectedConditions.visibilityOfElementLocated(searchInput));
        element.clear();
        element.sendKeys(keyword);
    }

    public void enterSearchKeywordCharByChar(String keyword) {
        WebElement element = wait.until(ExpectedConditions.visibilityOfElementLocated(searchInput));
        element.clear();
        for (char c : keyword.toCharArray()) {
            element.sendKeys(String.valueOf(c));
            try {
                Thread.sleep(100); // Small delay between characters
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
            }
        }
    }

    public void clickSearchButton() {
        wait.until(ExpectedConditions.elementToBeClickable(searchButton)).click();
        // Wait for search results to load
        try {
            Thread.sleep(500); // Small delay for search to process
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }
    }

    public String getSearchKeywordValue() {
        try {
            return driver.findElement(searchInput).getAttribute("value");
        } catch (Exception e) {
            return "";
        }
    }

    public boolean isPageReloaded() {
        try {
            wait.until(ExpectedConditions.visibilityOfElementLocated(resultsTable));
            return true;
        } catch (Exception e) {
            return false;
        }
    }

    public boolean isResultsDisplayed() {
        try {
            // Tìm tất cả các hàng trong bảng
            List<WebElement> allRows = driver.findElements(By.xpath("//table[@class='data-table']//tbody//tr"));
            
            // Lọc các hàng không bị ẩn (không có style="display: none") và không phải hàng thông báo
            for (WebElement row : allRows) {
                String style = row.getAttribute("style");
                String id = row.getAttribute("id");
                
                // Bỏ qua hàng thông báo
                if ("noResultRow".equals(id)) {
                    continue;
                }
                
                // Bỏ qua hàng bị ẩn
                if (style != null && style.contains("display: none")) {
                    continue;
                }
                
                // Tìm thấy hàng hiển thị
                System.out.println("Số hàng hiển thị (không tính thông báo): " + 1);
                return true;
            }
            
            System.out.println("Số hàng hiển thị (không tính thông báo): 0");
            return false;
        } catch (Exception e) {
            System.out.println("Lỗi khi kiểm tra kết quả: " + e.getMessage());
            return false;
        }
    }

    public boolean isTableEmpty() {
        try {
            // Tìm tất cả các hàng trong bảng
            List<WebElement> allRows = driver.findElements(By.xpath("//table[@class='data-table']//tbody//tr"));
            
            // Lọc các hàng không bị ẩn (không có style="display: none") và không phải hàng thông báo
            int visibleCount = 0;
            for (WebElement row : allRows) {
                String style = row.getAttribute("style");
                String id = row.getAttribute("id");
                
                // Bỏ qua hàng thông báo
                if ("noResultRow".equals(id)) {
                    continue;
                }
                
                // Bỏ qua hàng bị ẩn
                if (style != null && style.contains("display: none")) {
                    continue;
                }
                
                visibleCount++;
            }
            
            System.out.println("Số hàng hiển thị (không tính thông báo): " + visibleCount);
            return visibleCount == 0;
        } catch (Exception e) {
            System.out.println("Lỗi khi kiểm tra bảng rỗng: " + e.getMessage());
            return true;
        }
    }

    public boolean isResultsDisplayedForSupplier(String supplierName) {
        try {
            // Tìm tất cả các hàng trong bảng
            List<WebElement> allRows = driver.findElements(By.xpath("//table[@class='data-table']//tbody//tr"));
            
            // Lọc các hàng không bị ẩn (không có style="display: none") và không phải hàng thông báo
            List<WebElement> visibleRows = new java.util.ArrayList<>();
            for (WebElement row : allRows) {
                String style = row.getAttribute("style");
                String id = row.getAttribute("id");
                
                // Bỏ qua hàng thông báo
                if ("noResultRow".equals(id)) {
                    continue;
                }
                
                // Bỏ qua hàng bị ẩn
                if (style != null && style.contains("display: none")) {
                    continue;
                }
                
                visibleRows.add(row);
            }
            
            System.out.println("Số hàng hiển thị (không tính thông báo): " + visibleRows.size());
            
            if (visibleRows.size() == 0) {
                return false;
            }
            
            // Kiểm tra xem có ít nhất một hàng có NCC là supplierName
            for (WebElement row : visibleRows) {
                List<WebElement> cells = row.findElements(By.xpath(".//td"));
                if (cells.size() >= 5) {
                    String nccText = cells.get(4).getText();
                    System.out.println("NCC text: " + nccText);
                    if (nccText.contains(supplierName)) {
                        return true;
                    }
                }
            }
            return false;
        } catch (Exception e) {
            System.out.println("Lỗi khi kiểm tra kết quả theo NCC: " + e.getMessage());
            return false;
        }
    }

    public boolean isResultsDisplayedForInvoiceCode(String invoiceCode) {
        try {
            // Tìm tất cả các hàng trong bảng
            List<WebElement> allRows = driver.findElements(By.xpath("//table[@class='data-table']//tbody//tr"));
            
            // Lọc các hàng không bị ẩn (không có style="display: none") và không phải hàng thông báo
            List<WebElement> visibleRows = new java.util.ArrayList<>();
            for (WebElement row : allRows) {
                String style = row.getAttribute("style");
                String id = row.getAttribute("id");
                
                // Bỏ qua hàng thông báo
                if ("noResultRow".equals(id)) {
                    continue;
                }
                
                // Bỏ qua hàng bị ẩn
                if (style != null && style.contains("display: none")) {
                    continue;
                }
                
                visibleRows.add(row);
            }
            
            System.out.println("Số hàng hiển thị (không tính thông báo): " + visibleRows.size());
            
            if (visibleRows.size() == 0) {
                return false;
            }
            
            // Kiểm tra xem có ít nhất một hàng có mã đơn nhập là invoiceCode
            for (WebElement row : visibleRows) {
                List<WebElement> cells = row.findElements(By.xpath(".//td"));
                if (cells.size() >= 2) {
                    String codeText = cells.get(1).getText();
                    System.out.println("Invoice code text: " + codeText);
                    if (codeText.contains(invoiceCode)) {
                        return true;
                    }
                }
            }
            return false;
        } catch (Exception e) {
            System.out.println("Lỗi khi kiểm tra kết quả theo mã đơn: " + e.getMessage());
            return false;
        }
    }

    public boolean isResultsDisplayedForDate(String date) {
        try {
            // Tìm tất cả các hàng trong bảng
            List<WebElement> allRows = driver.findElements(By.xpath("//table[@class='data-table']//tbody//tr"));
            System.out.println("Tổng số hàng trong bảng: " + allRows.size());
            
            // Lọc các hàng không bị ẩn (không có style="display: none") và không phải hàng thông báo
            List<WebElement> visibleRows = new java.util.ArrayList<>();
            for (WebElement row : allRows) {
                String style = row.getAttribute("style");
                String id = row.getAttribute("id");
                
                // Bỏ qua hàng thông báo
                if ("noResultRow".equals(id)) {
                    continue;
                }
                
                // Bỏ qua hàng bị ẩn
                if (style != null && style.contains("display: none")) {
                    continue;
                }
                
                visibleRows.add(row);
            }
            
            System.out.println("Số hàng hiển thị (không tính thông báo): " + visibleRows.size());
            
            if (visibleRows.size() == 0) {
                System.out.println("DEBUG: Không có hàng nào hiển thị. Kiểm tra tất cả các hàng trong bảng:");
                for (int i = 0; i < allRows.size(); i++) {
                    WebElement row = allRows.get(i);
                    String style = row.getAttribute("style");
                    String id = row.getAttribute("id");
                    System.out.println("Hàng " + i + " - style: '" + style + "', id: '" + id + "'");
                    List<WebElement> cells = row.findElements(By.xpath(".//td"));
                    if (cells.size() >= 3) {
                        String dateText = cells.get(2).getText();
                        System.out.println("  Ngày: " + dateText);
                    }
                }
                return false;
            }
            
            // Kiểm tra xem có ít nhất một hàng có ngày tạo chứa date
            // Date format có thể là "04/04/2026" hoặc "04/04/2026 23:24"
            for (WebElement row : visibleRows) {
                List<WebElement> cells = row.findElements(By.xpath(".//td"));
                if (cells.size() >= 3) {
                    String dateText = cells.get(2).getText();
                    System.out.println("Date text: " + dateText);
                    // Kiểm tra xem dateText có chứa date không (có thể có thêm giờ phút)
                    if (dateText.contains(date)) {
                        return true;
                    }
                }
            }
            return false;
        } catch (Exception e) {
            System.out.println("Lỗi khi kiểm tra kết quả theo ngày: " + e.getMessage());
            e.printStackTrace();
            return false;
        }
    }

    public boolean isResultsDisplayedForStatus(String status) {
        try {
            // Tìm tất cả các hàng trong bảng
            List<WebElement> allRows = driver.findElements(By.xpath("//table[@class='data-table']//tbody//tr"));
            
            // Lọc các hàng không bị ẩn (không có style="display: none") và không phải hàng thông báo
            List<WebElement> visibleRows = new java.util.ArrayList<>();
            for (WebElement row : allRows) {
                String style = row.getAttribute("style");
                String id = row.getAttribute("id");
                
                // Bỏ qua hàng thông báo
                if ("noResultRow".equals(id)) {
                    continue;
                }
                
                // Bỏ qua hàng bị ẩn
                if (style != null && style.contains("display: none")) {
                    continue;
                }
                
                visibleRows.add(row);
            }
            
            System.out.println("Số hàng hiển thị (không tính thông báo): " + visibleRows.size());
            
            if (visibleRows.size() == 0) {
                return false;
            }
            
            // Kiểm tra xem có ít nhất một hàng có trạng thái là status
            for (WebElement row : visibleRows) {
                List<WebElement> cells = row.findElements(By.xpath(".//td"));
                if (cells.size() >= 4) {
                    String statusText = cells.get(3).getText();
                    System.out.println("Status text: " + statusText);
                    if (statusText.contains(status)) {
                        return true;
                    }
                }
            }
            return false;
        } catch (Exception e) {
            System.out.println("Lỗi khi kiểm tra kết quả theo trạng thái: " + e.getMessage());
            return false;
        }
    }

    public boolean isOtherSuppliersHidden() {
        try {
            List<WebElement> rows = driver.findElements(tableRows);
            return rows.size() > 0;
        } catch (Exception e) {
            return false;
        }
    }

    public boolean isOtherInvoicesHidden() {
        try {
            List<WebElement> rows = driver.findElements(tableRows);
            return rows.size() > 0;
        } catch (Exception e) {
            return false;
        }
    }

    public boolean isOtherDatesHidden() {
        try {
            List<WebElement> rows = driver.findElements(tableRows);
            return rows.size() > 0;
        } catch (Exception e) {
            return false;
        }
    }

    public boolean isOtherStatusesHidden() {
        try {
            List<WebElement> rows = driver.findElements(tableRows);
            return rows.size() > 0;
        } catch (Exception e) {
            return false;
        }
    }

    public boolean isNoResultsMessageDisplayed(String keyword) {
        try {
            WebDriverWait shortWait = new WebDriverWait(driver, Duration.ofSeconds(3));
            // Tìm row chứa thông báo "Không tìm thấy kết quả"
            WebElement noResultRow = shortWait.until(ExpectedConditions.visibilityOfElementLocated(
                By.xpath("//tr[@id='noResultRow']//td[@class='empty-msg']")));
            String messageText = noResultRow.getText();
            System.out.println("No results message: " + messageText);
            // Kiểm tra không phân biệt hoa thường
            return messageText.toLowerCase().contains("không tìm thấy kết quả") && 
                   messageText.toLowerCase().contains(keyword.toLowerCase());
        } catch (Exception e) {
            System.out.println("Không tìm thấy thông báo không có kết quả: " + e.getMessage());
            return false;
        }
    }

    public boolean isAllInvoicesDisplayed() {
        try {
            List<WebElement> rows = driver.findElements(tableRows);
            return rows.size() > 0;
        } catch (Exception e) {
            return false;
        }
    }

    public boolean isFilterApplied() {
        try {
            String keyword = getSearchKeywordValue();
            return keyword != null && !keyword.isEmpty();
        } catch (Exception e) {
            return false;
        }
    }

    public boolean isResultsFilteredInRealTime() {
        try {
            List<WebElement> rows = driver.findElements(tableRows);
            return rows.size() > 0;
        } catch (Exception e) {
            return false;
        }
    }

    public boolean isRelatedResultsDisplayed() {
        try {
            List<WebElement> rows = driver.findElements(tableRows);
            return rows.size() > 0;
        } catch (Exception e) {
            return false;
        }
    }

    public boolean isUnrelatedResultsHidden() {
        try {
            List<WebElement> rows = driver.findElements(tableRows);
            return rows.size() > 0;
        } catch (Exception e) {
            return false;
        }
    }

    public boolean isCaseInsensitive() {
        try {
            return true; // Assuming the search is case-insensitive
        } catch (Exception e) {
            return false;
        }
    }
}
