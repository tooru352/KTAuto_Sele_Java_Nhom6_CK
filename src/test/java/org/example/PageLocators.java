package org.example;

import org.openqa.selenium.By;

/**
 * Centralized locators for the Invoice Edit Page
 * Update these locators based on your application's HTML structure
 */
public class PageLocators {
    
    // Invoice List Page
    public static final By INVOICE_TABLE = By.xpath("//table[@class='invoice-table']");
    public static final By INVOICE_ROWS = By.xpath("//tr[@class='invoice-row']");
    public static final By VIEW_DETAILS_BUTTON = By.xpath("//button[contains(text(), 'Xem chi tiết')]");
    
    // Invoice Detail Popup
    public static final By EDIT_BUTTON = By.xpath("//button[contains(text(), 'Sửa')]");
    public static final By SAVE_BUTTON = By.xpath("//button[contains(text(), 'Lưu')]");
    public static final By CANCEL_BUTTON = By.xpath("//button[contains(text(), 'Hủy')]");
    
    // Form Fields
    public static final By QUANTITY_INPUT = By.xpath("//input[@name='quantity']");
    public static final By UNIT_PRICE_FIELD = By.xpath("//input[@name='unitPrice']");
    public static final By INVOICE_CODE_FIELD = By.xpath("//input[@name='invoiceCode']");
    public static final By SALE_DATE_FIELD = By.xpath("//input[@name='saleDate']");
    public static final By PAYMENT_METHOD_SELECT = By.xpath("//select[@name='paymentMethod']");
    public static final By DISCOUNT_INPUT = By.xpath("//input[@name='discount']");
    public static final By CUSTOMER_INFO_FIELD = By.xpath("//input[@name='customerInfo']");
    
    // Display Elements
    public static final By TOTAL_AMOUNT_DISPLAY = By.xpath("//span[@class='total-amount']");
    public static final By SUCCESS_MESSAGE = By.xpath("//div[contains(@class, 'success')]");
    public static final By ERROR_MESSAGE = By.xpath("//div[contains(@class, 'error')]");
    public static final By ALERT_MESSAGE = By.xpath("//div[@role='alert']");
    
    // Alternative locators (if above don't work, try these)
    public static final By EDIT_BUTTON_ALT = By.xpath("//button[@id='editBtn']");
    public static final By SAVE_BUTTON_ALT = By.xpath("//button[@id='saveBtn']");
    public static final By CANCEL_BUTTON_ALT = By.xpath("//button[@id='cancelBtn']");
    
    // Popup/Modal
    public static final By MODAL_POPUP = By.xpath("//div[@class='modal']");
    public static final By MODAL_CONTENT = By.xpath("//div[@class='modal-content']");
}
