package dataobjects;

/**
 * Data object cho Invoice Delete
 * Chứa dữ liệu test cho module Xóa hóa đơn
 */
public class InvoiceDeleteData {
    private String invoiceCode;
    private int invoiceIndex;
    private boolean shouldDelete;
    
    // Constructor
    public InvoiceDeleteData() {
    }
    
    public InvoiceDeleteData(String invoiceCode, int invoiceIndex, boolean shouldDelete) {
        this.invoiceCode = invoiceCode;
        this.invoiceIndex = invoiceIndex;
        this.shouldDelete = shouldDelete;
    }
    
    // Getters and Setters
    public String getInvoiceCode() {
        return invoiceCode;
    }
    
    public void setInvoiceCode(String invoiceCode) {
        this.invoiceCode = invoiceCode;
    }
    
    public int getInvoiceIndex() {
        return invoiceIndex;
    }
    
    public void setInvoiceIndex(int invoiceIndex) {
        this.invoiceIndex = invoiceIndex;
    }
    
    public boolean isShouldDelete() {
        return shouldDelete;
    }
    
    public void setShouldDelete(boolean shouldDelete) {
        this.shouldDelete = shouldDelete;
    }
    
    // Builder pattern
    public static class Builder {
        private InvoiceDeleteData data = new InvoiceDeleteData();
        
        public Builder invoiceCode(String invoiceCode) {
            data.invoiceCode = invoiceCode;
            return this;
        }
        
        public Builder invoiceIndex(int invoiceIndex) {
            data.invoiceIndex = invoiceIndex;
            return this;
        }
        
        public Builder shouldDelete(boolean shouldDelete) {
            data.shouldDelete = shouldDelete;
            return this;
        }
        
        public InvoiceDeleteData build() {
            return data;
        }
    }
}
