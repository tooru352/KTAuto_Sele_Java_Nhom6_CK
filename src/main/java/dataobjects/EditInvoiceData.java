package dataobjects;

public class EditInvoiceData {
    // Invoice codes
    public static final String INVOICE_CODE_HDN01 = "HDN01";
    public static final String INVOICE_CODE_HDN02 = "HDN02";
    
    // Quantities
    public static final int ORIGINAL_QUANTITY = 10;
    public static final int NEW_QUANTITY_15 = 15;
    public static final int ZERO_QUANTITY = 0;
    public static final int NEGATIVE_QUANTITY = -2;
    public static final int NEW_QUANTITY_20 = 20;
    public static final int NEW_QUANTITY_5 = 5;
    
    // Unit prices
    public static final int ORIGINAL_UNIT_PRICE = 5000;
    public static final int NEW_UNIT_PRICE_55000 = 55000;
    public static final int NEGATIVE_UNIT_PRICE = -5000;
    public static final int ZERO_UNIT_PRICE = 0;
    public static final int NEW_UNIT_PRICE_100000 = 100000;
    
    // Discounts
    public static final double DECIMAL_DISCOUNT = 5.5;
    public static final int INVALID_DISCOUNT_150 = 150;
    public static final int VALID_DISCOUNT_MIN = 0;
    public static final int VALID_DISCOUNT_MAX = 100;
    
    // Payment methods
    public static final String PAYMENT_METHOD_CASH = "Tiền mặt";
    public static final String PAYMENT_METHOD_BANK = "Chuyển khoản";
    
    // Expected calculations
    public static final int EXPECTED_TOTAL_15_ITEMS = 75000; // 5000 * 15
    public static final int EXPECTED_TOTAL_55000_PRICE = 550000; // 55000 * 10
    public static final int EXPECTED_TOTAL_100000_5_ITEMS = 500000; // 100000 * 5
    public static final double EXPECTED_TOTAL_WITH_DISCOUNT = 472500; // (100000 * 5) * (1 - 5.5/100) = 472500
    
    // Error messages
    public static final String ERROR_QUANTITY_ZERO = "Số lượng và đơn giá phải >0. Vui lòng nhập lại";
    public static final String ERROR_QUANTITY_NEGATIVE = "Số lượng phải >0. Vui lòng nhập lại";
    public static final String ERROR_UNIT_PRICE_NEGATIVE = "Đơn giá phải >0. Vui lòng nhập lại";
    public static final String ERROR_UNIT_PRICE_ZERO = "Số lượng và đơn giá phải >0. Vui lòng nhập lại";
    public static final String ERROR_DISCOUNT_INVALID = "Chiết khấu phải từ 0 đến 100";
    public static final String SUCCESS_MESSAGE = "Lưu thành công";
    public static final String SAVE_SUCCESS_MESSAGE = "Đã lưu thông tin thay đổi";
}
