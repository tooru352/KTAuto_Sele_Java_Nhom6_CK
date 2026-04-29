package org.example;

import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

public class DebugTest extends BaseTest {
    private InvoiceEditPage invoicePage;

    @BeforeMethod
    @Override
    public void setUp() {
        super.setUp();
        invoicePage = new InvoiceEditPage(driver, wait);
        login();
        navigateToSalesPage();
    }

    @Test(description = "Debug: Khám phá cấu trúc popup chi tiết hóa đơn")
    public void debugInvoiceDetailPopup() {
        System.out.println("=== STARTING DEBUG TEST ===");
        
        // Click vào hóa đơn để mở popup
        invoicePage.clickViewDetails(0);
        
        // Wait for popup to appear
        try {
            Thread.sleep(3000);
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }
        
        System.out.println("=== ANALYZING POPUP STRUCTURE ===");
        
        // Find all visible popups/modals
        java.util.List<WebElement> popups = driver.findElements(By.xpath("//div[contains(@class, 'popup') or contains(@class, 'modal')]"));
        System.out.println("Found " + popups.size() + " popup/modal elements");
        
        for (int i = 0; i < popups.size(); i++) {
            WebElement popup = popups.get(i);
            try {
                if (popup.isDisplayed()) {
                    System.out.println("\n--- POPUP " + i + " (VISIBLE) ---");
                    System.out.println("Class: " + popup.getAttribute("class"));
                    System.out.println("ID: " + popup.getAttribute("id"));
                    System.out.println("Style: " + popup.getAttribute("style"));
                    
                    // Look for tables inside this popup
                    java.util.List<WebElement> tables = popup.findElements(By.xpath(".//table"));
                    System.out.println("Tables in popup: " + tables.size());
                    
                    for (int j = 0; j < tables.size(); j++) {
                        WebElement table = tables.get(j);
                        System.out.println("  Table " + j + " rows: " + table.findElements(By.xpath(".//tr")).size());
                        
                        // Print table content
                        java.util.List<WebElement> rows = table.findElements(By.xpath(".//tr"));
                        for (int k = 0; k < Math.min(rows.size(), 5); k++) { // Limit to first 5 rows
                            System.out.println("    Row " + k + ": " + rows.get(k).getText());
                        }
                    }
                    
                    // Look for buttons in this popup
                    java.util.List<WebElement> buttons = popup.findElements(By.xpath(".//button"));
                    System.out.println("Buttons in popup: " + buttons.size());
                    for (WebElement btn : buttons) {
                        System.out.println("  Button: '" + btn.getText() + "' (class: " + btn.getAttribute("class") + ")");
                    }
                }
            } catch (Exception e) {
                System.out.println("Error analyzing popup " + i + ": " + e.getMessage());
            }
        }
        
        // Now click Edit button
        System.out.println("\n=== CLICKING EDIT BUTTON ===");
        invoicePage.clickEditButton();
        
        // Wait for edit mode
        try {
            Thread.sleep(3000);
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }
        
        // Analyze the page after clicking edit
        System.out.println("\n=== AFTER CLICKING EDIT ===");
        
        // Look for all tables on the page
        java.util.List<WebElement> allTables = driver.findElements(By.xpath("//table"));
        System.out.println("Total tables on page: " + allTables.size());
        
        for (int i = 0; i < allTables.size(); i++) {
            WebElement table = allTables.get(i);
            try {
                if (table.isDisplayed()) {
                    System.out.println("\n--- TABLE " + i + " (VISIBLE) ---");
                    java.util.List<WebElement> rows = table.findElements(By.xpath(".//tr"));
                    System.out.println("Rows: " + rows.size());
                    
                    // Check if this table has editable cells
                    java.util.List<WebElement> editableCells = table.findElements(By.xpath(".//td[contains(@onclick, 'edit') or contains(@class, 'editable')]"));
                    System.out.println("Editable cells: " + editableCells.size());
                    
                    // Check for input fields in this table
                    java.util.List<WebElement> inputs = table.findElements(By.xpath(".//input"));
                    System.out.println("Input fields in table: " + inputs.size());
                    
                    for (WebElement input : inputs) {
                        if (input.isDisplayed()) {
                            System.out.println("  Input - Type: " + input.getAttribute("type") + 
                                             ", Name: " + input.getAttribute("name") + 
                                             ", Value: " + input.getAttribute("value"));
                        }
                    }
                    
                    // Print first few rows of content
                    for (int j = 0; j < Math.min(rows.size(), 3); j++) {
                        System.out.println("  Row " + j + ": " + rows.get(j).getText());
                        
                        // Check if row has onclick for editing
                        String onclick = rows.get(j).getAttribute("onclick");
                        if (onclick != null && !onclick.isEmpty()) {
                            System.out.println("    Row onclick: " + onclick);
                        }
                        
                        // Check each cell in the row for onclick
                        java.util.List<WebElement> cells = rows.get(j).findElements(By.xpath(".//td"));
                        for (int l = 0; l < cells.size(); l++) {
                            WebElement cell = cells.get(l);
                            String cellOnclick = cell.getAttribute("onclick");
                            if (cellOnclick != null && !cellOnclick.isEmpty()) {
                                System.out.println("      Cell " + l + " onclick: " + cellOnclick);
                                System.out.println("      Cell " + l + " text: '" + cell.getText() + "'");
                            }
                        }
                    }
                }
            } catch (Exception e) {
                System.out.println("Error analyzing table " + i + ": " + e.getMessage());
            }
        }
        
        System.out.println("\n=== LOOKING FOR CLICKABLE QUANTITY CELLS ===");
        
        // Look for cells that might contain quantity that can be clicked to edit
        String[] quantityPatterns = {
            "//td[contains(text(), '10')]", // Assuming quantity is 10
            "//td[contains(@onclick, 'quantity')]",
            "//td[contains(@onclick, 'so_luong')]",
            "//td[contains(@onclick, 'edit')]",
            "//td[@onclick]" // Any clickable cell
        };
        
        for (String pattern : quantityPatterns) {
            try {
                java.util.List<WebElement> cells = driver.findElements(By.xpath(pattern));
                System.out.println("Pattern '" + pattern + "' found " + cells.size() + " cells");
                
                for (int i = 0; i < Math.min(cells.size(), 5); i++) {
                    WebElement cell = cells.get(i);
                    if (cell.isDisplayed()) {
                        System.out.println("  Cell " + i + ":");
                        System.out.println("    Text: '" + cell.getText() + "'");
                        System.out.println("    onclick: " + cell.getAttribute("onclick"));
                        System.out.println("    class: " + cell.getAttribute("class"));
                    }
                }
            } catch (Exception e) {
                System.out.println("Error with pattern '" + pattern + "': " + e.getMessage());
            }
        }
        
        System.out.println("\n=== DEBUG TEST COMPLETED ===");
    }
}