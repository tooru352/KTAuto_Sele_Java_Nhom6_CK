# CONU Bookstore - Invoice Edit Automation Tests

This project contains automated tests for the invoice editing feature in the CONU Bookstore application using Selenium WebDriver and Java.

## Prerequisites

- Java 25 or higher
- Maven 3.6+
- Chrome browser installed
- CONU Bookstore application running on `http://127.0.0.1:8000`

## Project Structure

```
src/
├── main/java/org/example/
│   └── Main.java
└── test/java/org/example/
    ├── BaseTest.java              # Base test class with setup/teardown
    ├── InvoiceEditPage.java       # Page Object Model for invoice page
    └── InvoiceEditTest.java       # Test cases (18 test cases)
testng.xml                          # TestNG configuration
pom.xml                             # Maven configuration
```

## Test Cases Covered

1. **TC_01**: Edit valid quantity
2. **TC_02**: Edit quantity exceeding inventory
3. **TC_03**: Edit quantity to zero
4. **TC_04**: Edit quantity to negative
5. **TC_05**: Cancel edit operation
6. **TC_06**: Edit multiple fields at once
7. **TC_07**: Auto calculation verification
8. **TC_08**: Inventory update after edit
9. **TC_09**: Unit price read-only check
10. **TC_10**: Sale date read-only check
11. **TC_11**: Edit payment method successfully
12. **TC_12**: Invoice code read-only check
13. **TC_13**: Edit discount successfully
14. **TC_14**: Edit negative discount
15. **TC_15**: Edit discount equal to total amount
16. **TC_16**: Save without changes
17. **TC_17**: Customer info read-only check
18. **TC_18**: Enter text in discount field

## Setup Instructions

### 1. Install Dependencies

```bash
mvn clean install
```

### 2. Configure Application URL

Edit `BaseTest.java` and update the `BASE_URL` if your application is running on a different URL:

```java
protected static final String BASE_URL = "http://127.0.0.1:8000";
```

### 3. Update Login Credentials

If your login credentials are different, update them in `BaseTest.java`:

```java
protected static final String USERNAME = "admin";
protected static final String PASSWORD = "admin123";
```

## Running Tests

### Run All Tests

```bash
mvn test
```

### Run Specific Test Class

```bash
mvn test -Dtest=InvoiceEditTest
```

### Run Specific Test Method

```bash
mvn test -Dtest=InvoiceEditTest#testEditValidQuantity
```

### Run Tests with TestNG XML

```bash
mvn test -Dsuites=testng.xml
```

### Run Tests from IntelliJ IDEA

1. Right-click on `testng.xml` → Run 'testng.xml'
2. Or right-click on `InvoiceEditTest.java` → Run 'InvoiceEditTest'
3. Or right-click on a specific test method → Run

## Key Features

- **Page Object Model**: Clean separation of test logic and page interactions
- **WebDriverManager**: Automatic ChromeDriver management
- **Explicit Waits**: Proper wait conditions for element visibility and clickability
- **TestNG Framework**: Organized test execution with detailed reporting
- **Logging**: Support for Log4j logging (can be extended)

## Troubleshooting

### Tests fail with "Element not found"

- Verify the application is running on the correct URL
- Check that the XPath locators match your application's HTML structure
- Update locators in `InvoiceEditPage.java` if needed

### Login fails

- Verify username and password are correct
- Check that the login page URL is accessible
- Ensure the login button XPath is correct

### Chrome driver issues

- WebDriverManager should automatically download the correct ChromeDriver
- If issues persist, manually download ChromeDriver matching your Chrome version
- Place it in your system PATH or specify the path in `BaseTest.java`

## Extending the Tests

To add more test cases:

1. Add new test methods in `InvoiceEditTest.java`
2. Add corresponding locators and methods in `InvoiceEditPage.java`
3. Update `testng.xml` to include new test methods

## Notes

- Tests are designed to run sequentially (parallel=false in testng.xml)
- Each test is independent and performs login before execution
- Screenshots can be added to failed tests by extending the `BaseTest` class
- Test data can be parameterized using TestNG @DataProvider annotation
