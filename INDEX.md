# CONU Bookstore Automation Test Suite - Complete Index

## 📋 Documentation Files

### Getting Started
1. **README.md** - Project overview, setup instructions, and key features
2. **TONG_QUAN_PROJECT.md** - 🆕 **QUAN TRỌNG** - Tổng quan toàn bộ project (Tiếng Việt)
3. **QUICKSTART.md** - Quick start guide for running tests in IntelliJ IDEA
4. **SETUP_CHECKLIST.md** - Pre-execution checklist to verify everything is configured

### Guides & References
4. **HUONG_DAN_CHAY_TEST_SAU_KHI_SUA.md** - 🆕 **MỚI NHẤT** - Hướng dẫn chạy test sau khi sửa (Tiếng Việt)
5. **HUONG_DAN_CAU_TRUC_MOI.md** - Hướng dẫn cấu trúc project mới theo POM (Tiếng Việt)
6. **LOCATOR_UPDATE_GUIDE.md** - Detailed guide to update XPath locators for your application
7. **TEST_EXECUTION_GUIDE.md** - How to run tests, troubleshoot, and interpret results
8. **TROUBLESHOOTING_REFERENCE.md** - Quick reference for common errors and solutions
9. **TEST_FLOW_DIAGRAM.md** - Visual diagrams of test flow and architecture
10. **HUONG_DAN_CHAY_TEST_XOA_HOA_DON.md** - Hướng dẫn chạy test xóa hóa đơn (Tiếng Việt)
11. **HUONG_DAN_CHAY_TC01_AN_TOAN.md** - Hướng dẫn chạy TC-01 an toàn (Tiếng Việt)

### Project Information
12. **PROJECT_SUMMARY.md** - Complete project overview and next steps
13. **INDEX.md** - This file

---

## 🧪 Test Source Files

### Core Test Framework
- **src/test/java/org/example/BaseTest.java**
  - Base class for all tests
  - Handles WebDriver setup/teardown
  - Login functionality
  - Configuration constants

- **src/test/java/org/example/InvoiceEditTest.java**
  - 18 test cases (TC_01 through TC_18)
  - Tests for quantity, fields, discounts, and read-only validation
  - Uses Page Object Model pattern

### Page Object Model
- **src/test/java/org/example/InvoiceEditPage.java**
  - Page Object for invoice editing page
  - Methods for user interactions
  - Element locators and waits
  - Message retrieval methods

- **src/test/java/org/example/PageLocators.java**
  - Centralized XPath locators
  - Easy to update and maintain
  - Alternative locators provided
  - Well-documented

### Utilities
- **src/test/java/org/example/TestUtils.java**
  - Common utility methods
  - Wait operations
  - Element interactions
  - Reusable across tests

---

## ⚙️ Configuration Files

- **pom.xml** - Maven configuration with dependencies
  - Selenium WebDriver 4.15.0
  - TestNG 7.8.1
  - WebDriverManager 5.6.3
  - Log4j logging

- **testng.xml** - TestNG test suite configuration
  - All 18 test cases
  - Sequential execution
  - Test grouping

---

## 📊 Test Cases Overview

### Quantity Tests (4 cases)
- TC_01: Edit valid quantity
- TC_02: Edit quantity exceeding inventory
- TC_03: Edit quantity to zero
- TC_04: Edit quantity to negative

### Edit Operations (3 cases)
- TC_05: Cancel edit operation
- TC_06: Edit multiple fields
- TC_16: Save without changes

### Calculations & Updates (2 cases)
- TC_07: Auto calculation verification
- TC_08: Inventory update after edit

### Read-Only Fields (4 cases)
- TC_09: Unit price read-only
- TC_10: Sale date read-only
- TC_12: Invoice code read-only
- TC_17: Customer info read-only

### Payment & Discount (5 cases)
- TC_11: Edit payment method
- TC_13: Edit discount successfully
- TC_14: Edit negative discount
- TC_15: Edit discount equal to total
- TC_18: Enter text in discount field

---

## 🚀 Quick Start

### 1. Setup
```bash
# Install dependencies
mvn clean install

# Verify compilation
mvn clean compile -DskipTests
```

### 2. Configure
- Update `BaseTest.java` with correct URL and credentials
- Update `PageLocators.java` with correct XPath locators
- See `LOCATOR_UPDATE_GUIDE.md` for detailed instructions

### 3. Run Tests
```bash
# Run all tests
mvn test

# Run specific test
mvn test -Dtest=InvoiceEditTest#testEditValidQuantity

# Run via TestNG XML
mvn test -Dsuites=testng.xml
```

### 4. Review Results
- Check test output for pass/fail status
- Review `target/surefire-reports/` for detailed reports
- Fix any failures using `TROUBLESHOOTING_REFERENCE.md`

---

## 📁 File Structure

```
project-root/
├── src/
│   ├── main/java/org/example/
│   │   └── Main.java
│   └── test/java/org/example/
│       ├── BaseTest.java
│       ├── InvoiceEditPage.java
│       ├── InvoiceEditTest.java
│       ├── PageLocators.java
│       └── TestUtils.java
├── pom.xml
├── testng.xml
├── README.md
├── QUICKSTART.md
├── SETUP_CHECKLIST.md
├── LOCATOR_UPDATE_GUIDE.md
├── TEST_EXECUTION_GUIDE.md
├── TROUBLESHOOTING_REFERENCE.md
├── TEST_FLOW_DIAGRAM.md
├── PROJECT_SUMMARY.md
└── INDEX.md (this file)
```

---

## 🔧 Key Technologies

- **Selenium WebDriver 4.15.0** - Browser automation
- **TestNG 7.8.1** - Test framework
- **WebDriverManager 5.6.3** - Automatic driver management
- **Java 25** - Programming language
- **Maven** - Build tool

---

## 📖 Documentation Guide

### For First-Time Users
1. Start with **README.md**
2. Follow **QUICKSTART.md**
3. Use **SETUP_CHECKLIST.md** to verify setup
4. Run first test

### For Locator Issues
1. Read **LOCATOR_UPDATE_GUIDE.md**
2. Use browser DevTools to inspect elements
3. Update **PageLocators.java**
4. Re-run tests

### For Execution Issues
1. Check **TEST_EXECUTION_GUIDE.md**
2. Review **TROUBLESHOOTING_REFERENCE.md**
3. Check test output for error messages
4. Use **TEST_FLOW_DIAGRAM.md** to understand flow

### For Understanding Architecture
1. Review **PROJECT_SUMMARY.md**
2. Study **TEST_FLOW_DIAGRAM.md**
3. Review source code comments
4. Check **PageLocators.java** for locator patterns

---

## ✅ Pre-Execution Checklist

Before running tests:

- [ ] Java 25+ installed
- [ ] Maven installed
- [ ] Chrome browser installed
- [ ] Application running on http://127.0.0.1:8000
- [ ] Can login with admin/admin123
- [ ] Dependencies downloaded (mvn clean install)
- [ ] XPath locators updated in PageLocators.java
- [ ] BaseTest.java has correct URL and credentials
- [ ] Single test passes (mvn test -Dtest=InvoiceEditTest#testEditValidQuantity)
- [ ] Ready to run full suite (mvn test)

See **SETUP_CHECKLIST.md** for detailed checklist.

---

## 🐛 Troubleshooting Quick Links

| Issue | Solution |
|-------|----------|
| Element not found | See LOCATOR_UPDATE_GUIDE.md |
| Login fails | See TROUBLESHOOTING_REFERENCE.md |
| Timeout errors | See TEST_EXECUTION_GUIDE.md |
| Tests fail together | See TROUBLESHOOTING_REFERENCE.md |
| Compilation errors | See QUICKSTART.md |
| Need to understand flow | See TEST_FLOW_DIAGRAM.md |

---

## 📞 Support Resources

1. **LOCATOR_UPDATE_GUIDE.md** - How to find and update XPath locators
2. **TROUBLESHOOTING_REFERENCE.md** - Common errors and solutions
3. **TEST_EXECUTION_GUIDE.md** - How to run and debug tests
4. **TEST_FLOW_DIAGRAM.md** - Visual architecture and flow
5. **QUICKSTART.md** - Quick setup in IntelliJ

---

## 🎯 Next Steps

1. ✅ Read this INDEX.md
2. ✅ Follow QUICKSTART.md
3. ✅ Complete SETUP_CHECKLIST.md
4. ✅ Update locators using LOCATOR_UPDATE_GUIDE.md
5. ✅ Run tests using TEST_EXECUTION_GUIDE.md
6. ✅ Fix issues using TROUBLESHOOTING_REFERENCE.md
7. ✅ Extend tests as needed

---

## 📝 Notes

- All tests are independent and can run in any order
- Tests use Page Object Model for maintainability
- Locators are centralized in PageLocators.java
- Comprehensive documentation provided
- Ready for CI/CD integration

---

## 📅 Project Information

- **Created:** April 2026
- **Framework:** Selenium WebDriver + TestNG
- **Language:** Java
- **Test Cases:** 18
- **Status:** Ready for execution

---

## 🔗 File Cross-References

### If you need to...

**🆕 Hiểu toàn bộ project (Tiếng Việt):**
- See: TONG_QUAN_PROJECT.md ⭐

**🆕 Chạy test sau khi sửa (Tiếng Việt):**
- See: HUONG_DAN_CHAY_TEST_SAU_KHI_SUA.md ⭐

**Run tests:**
- See: QUICKSTART.md, TEST_EXECUTION_GUIDE.md

**Update locators:**
- See: LOCATOR_UPDATE_GUIDE.md, PageLocators.java

**Fix errors:**
- See: TROUBLESHOOTING_REFERENCE.md, TEST_EXECUTION_GUIDE.md

**Understand architecture:**
- See: PROJECT_SUMMARY.md, TEST_FLOW_DIAGRAM.md, README.md, TONG_QUAN_PROJECT.md

**Verify setup:**
- See: SETUP_CHECKLIST.md, QUICKSTART.md

**Add new tests:**
- See: PROJECT_SUMMARY.md, InvoiceEditTest.java

**Hiểu cấu trúc mới (Tiếng Việt):**
- See: HUONG_DAN_CAU_TRUC_MOI.md

**Chạy test xóa hóa đơn (Tiếng Việt):**
- See: HUONG_DAN_CHAY_TEST_XOA_HOA_DON.md, HUONG_DAN_CHAY_TC01_AN_TOAN.md

---

**Last Updated:** April 2026
**Version:** 1.0
**Status:** Complete and Ready to Use
