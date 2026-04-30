# Quick Start Guide - Running Tests in IntelliJ IDEA

## Step 1: Open Project in IntelliJ

1. Open IntelliJ IDEA
2. Click `File` → `Open`
3. Navigate to your project folder and click `Open`
4. Wait for IntelliJ to index the project

## Step 2: Configure Maven

1. Go to `File` → `Settings` → `Build, Execution, Deployment` → `Build Tools` → `Maven`
2. Ensure Maven home path is set correctly
3. Click `OK`

## Step 3: Download Dependencies

1. Right-click on `pom.xml` in the project root
2. Select `Maven` → `Reload Projects`
3. Wait for all dependencies to download

## Step 4: Update Locators (Important!)

The XPath locators in `InvoiceEditPage.java` need to be updated to match your application's HTML structure:

1. Open your application in a browser
2. Right-click on elements and select "Inspect" to view their HTML
3. Update the locators in `InvoiceEditPage.java` accordingly

Example locators that may need updating:
- `viewDetailsButton` - Button to view invoice details
- `editButton` - Edit button in the popup
- `quantityInput` - Quantity input field
- `saveButton` - Save button
- `discountInput` - Discount input field

## Step 5: Run Tests

### Option A: Run All Tests
1. Right-click on `testng.xml`
2. Select `Run 'testng.xml'`

### Option B: Run Specific Test Class
1. Right-click on `InvoiceEditTest.java`
2. Select `Run 'InvoiceEditTest'`

### Option C: Run Specific Test Method
1. Right-click on a test method (e.g., `testEditValidQuantity`)
2. Select `Run 'InvoiceEditTest.testEditValidQuantity'`

### Option D: Run via Maven
1. Open Terminal in IntelliJ (View → Tool Windows → Terminal)
2. Run: `mvn test`
3. Or run specific test: `mvn test -Dtest=InvoiceEditTest#testEditValidQuantity`

## Step 6: View Test Results

After running tests:
1. Results appear in the `Run` tool window at the bottom
2. Green checkmark = Test passed
3. Red X = Test failed
4. Click on failed tests to see error details

## Troubleshooting

### Issue: "Cannot find symbol" errors
- Right-click on project → `Maven` → `Reload Projects`
- Go to `File` → `Invalidate Caches` → `Invalidate and Restart`

### Issue: Tests fail with "Element not found"
- The XPath locators don't match your application
- Use browser DevTools to inspect elements and update locators
- Common issues:
  - Button text might be different
  - Element IDs or classes might be different
  - Elements might be in different containers

### Issue: Login fails
- Verify credentials in `BaseTest.java`
- Check that application is running on `http://127.0.0.1:8000`
- Verify login button XPath is correct

### Issue: Chrome driver not found
- WebDriverManager should handle this automatically
- If it fails, download ChromeDriver from: https://chromedriver.chromium.org/
- Match the version with your Chrome browser version

## Key Files to Modify

1. **BaseTest.java** - Update BASE_URL, USERNAME, PASSWORD
2. **InvoiceEditPage.java** - Update all XPath locators to match your application
3. **InvoiceEditTest.java** - Add/modify test cases as needed

## Next Steps

1. Update all locators in `InvoiceEditPage.java` to match your application
2. Run a single test to verify setup is correct
3. Run all tests and review results
4. Add more test cases as needed
5. Consider adding screenshot capture on test failure

## Tips

- Use `wait.until()` for elements that take time to load
- Use explicit waits instead of Thread.sleep()
- Keep test data separate from test logic
- Use descriptive test names and descriptions
- Add comments for complex test logic
