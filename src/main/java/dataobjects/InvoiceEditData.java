package dataobjects;

/**
 * Data object cho Invoice Edit
 * Chứa dữ liệu test cho module Chỉnh sửa hóa đơn
 */
public class InvoiceEditData {
    private String invoiceCode;
    private String quantity;
    private String discount;
    private String paymentMethod;
    private String customerName;
    private String customerAddress;
    private String customerPhone;
    
    // Constructor
    public InvoiceEditData() {
    }
    
    public InvoiceEditData(String invoiceCode, String quantity) {
        this.invoiceCode = invoiceCode;
        this.quantity = quantity;
    }
    
    // Getters and Setters
    public String getInvoiceCode() {
        return invoiceCode;
    }
    
    public void setInvoiceCode(String invoiceCode) {
        this.invoiceCode = invoiceCode;
    }
    
    public String getQuantity() {
        return quantity;
    }
    
    public void setQuantity(String quantity) {
        this.quantity = quantity;
    }
    
    public String getDiscount() {
        return discount;
    }
    
    public void setDiscount(String discount) {
        this.discount = discount;
    }
    
    public String getPaymentMethod() {
        return paymentMethod;
    }
    
    public void setPaymentMethod(String paymentMethod) {
        this.paymentMethod = paymentMethod;
    }
    
    public String getCustomerName() {
        return customerName;
    }
    
    public void setCustomerName(String customerName) {
        this.customerName = customerName;
    }
    
    public String getCustomerAddress() {
        return customerAddress;
    }
    
    public void setCustomerAddress(String customerAddress) {
        this.customerAddress = customerAddress;
    }
    
    public String getCustomerPhone() {
        return customerPhone;
    }
    
    public void setCustomerPhone(String customerPhone) {
        this.customerPhone = customerPhone;
    }
    
    // Builder pattern for easy object creation
    public static class Builder {
        private InvoiceEditData data = new InvoiceEditData();
        
        public Builder invoiceCode(String invoiceCode) {
            data.invoiceCode = invoiceCode;
            return this;
        }
        
        public Builder quantity(String quantity) {
            data.quantity = quantity;
            return this;
        }
        
        public Builder discount(String discount) {
            data.discount = discount;
            return this;
        }
        
        public Builder paymentMethod(String paymentMethod) {
            data.paymentMethod = paymentMethod;
            return this;
        }
        
        public Builder customerName(String customerName) {
            data.customerName = customerName;
            return this;
        }
        
        public Builder customerAddress(String customerAddress) {
            data.customerAddress = customerAddress;
            return this;
        }
        
        public Builder customerPhone(String customerPhone) {
            data.customerPhone = customerPhone;
            return this;
        }
        
        public InvoiceEditData build() {
            return data;
        }
    }
}
