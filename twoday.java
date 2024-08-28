import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.Select;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import java.time.Duration;

public class twoday {

    private WebDriver driver;
    private WebDriverWait wait;
    private JavascriptExecutor jsExecutor;
    private Actions actions;

    @BeforeClass
    public void setUp() {
        driver = new ChromeDriver();

        // Set WebDriverWait with a timeout of 15 seconds
        wait = new WebDriverWait(driver, Duration.ofSeconds(15));
        jsExecutor = (JavascriptExecutor) driver;
        actions = new Actions(driver);
        driver.manage().window().maximize();

        // Navigate to the online shop
        driver.get("https://magento.softwaretestingboard.com/");
    }

    @Test
    public void completeOrderProcess() {
        // Step 1: Navigate to Men's Hoodies & Sweatshirts section
        WebElement menMenu = wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("ui-id-5")));
        actions.moveToElement(menMenu).perform();

        WebElement topsLink = wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("ui-id-17")));
        actions.moveToElement(topsLink).perform();

        WebElement hoodiesAndSweatshirtsLink = wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("ui-id-20")));
        hoodiesAndSweatshirtsLink.click();

        // Step 2: Wait until the page is loaded
        wait.until(ExpectedConditions.presenceOfElementLocated(By.cssSelector(".product-item")));

        // Step 3: Scroll to the bottom of the page
        jsExecutor.executeScript("window.scrollTo(0, document.body.scrollHeight);");

        // Wait for the page to be fully loaded after scrolling
        wait.until(ExpectedConditions.presenceOfElementLocated(By.cssSelector(".footer")));

        // Step 4: Locate and click on "Frankie Sweatshirt"
        WebElement frankieSweatshirtLink = wait.until(ExpectedConditions.elementToBeClickable(By.cssSelector("a.product-item-link[href*='frankie-sweatshirt.html']")));
        frankieSweatshirtLink.click();

        // Step 5: Select size "XS"
        WebElement sizeXS = wait.until(ExpectedConditions.elementToBeClickable(By.id("option-label-size-143-item-166")));
        sizeXS.click();

        // Step 6: Select color "White"
        WebElement colorWhite = wait.until(ExpectedConditions.elementToBeClickable(By.id("option-label-color-93-item-59")));
        colorWhite.click();

        // Step 7: Click "Add to Cart"
        WebElement addToCartButton = wait.until(ExpectedConditions.elementToBeClickable(By.id("product-addtocart-button")));
        addToCartButton.click();

        // Step 8: Wait for the success message to appear
        WebElement successMessage = wait.until(ExpectedConditions.visibilityOfElementLocated(By.cssSelector(".message-success.success.message")));

        // Step 9: Wait for the "shopping cart" link within the success message
        WebElement shoppingCartLink = wait.until(ExpectedConditions.elementToBeClickable(By.cssSelector(".message-success.success.message a[href*='checkout/cart']")));

        // Step 10: Click on "shopping cart" link
        shoppingCartLink.click();

        // Step 11: Wait for the shopping cart page to load
        WebElement proceedToCheckoutButton = wait.until(ExpectedConditions.elementToBeClickable(By.cssSelector("button[data-role='proceed-to-checkout']")));

        // Step 12: Scroll to the "Proceed to Checkout" button
        jsExecutor.executeScript("arguments[0].scrollIntoView(true);", proceedToCheckoutButton);

        // Step 13: Introduce a small delay to ensure everything is ready
        try {
            Thread.sleep(2000); // Wait for 2 seconds
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        // Step 14: Click "Proceed to Checkout"
        proceedToCheckoutButton.click();

        // Step 15: Fill in the shipping address form
        fillShippingAddressForm();

        // Step 18: Place the order
        placeOrder();
    }

    private void fillShippingAddressForm() {
        // Wait until the email input field is visible
        WebElement emailField = wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("customer-email")));
        emailField.sendKeys("twoday@example.com");  // Email Address

        // Fill out the rest of the form
        driver.findElement(By.name("firstname")).sendKeys("Dovydas");             // First Name
        driver.findElement(By.name("lastname")).sendKeys("Dapkunas");               // Last Name
        driver.findElement(By.name("company")).sendKeys("TwoDay");       // Company
        driver.findElement(By.name("street[0]")).sendKeys("Giedraiciu 3");      // Street Address
        driver.findElement(By.name("city")).sendKeys("New York");              // City

        // Select State/Province
        WebElement stateDropdown = driver.findElement(By.name("region_id"));
        Select stateSelect = new Select(stateDropdown);
        stateSelect.selectByVisibleText("New York");

        driver.findElement(By.name("postcode")).sendKeys("10001");             // Zip/Postal Code

        // Select Country
        WebElement countryDropdown = driver.findElement(By.name("country_id"));
        Select countrySelect = new Select(countryDropdown);
        countrySelect.selectByVisibleText("United States");

        driver.findElement(By.name("telephone")).sendKeys("1234567890");       // Phone Number

        // Step 16: Choose the shipping method
        WebElement shippingMethod = driver.findElement(By.cssSelector("input[name='ko_unique_1']"));
        shippingMethod.click();

        // Step 17: Click "Next" to proceed
        WebElement nextButton = driver.findElement(By.cssSelector("button.continue"));
        nextButton.click();
    }

    private void placeOrder() {
        // Wait until the "Place Order" button is clickable
        WebElement placeOrderButton = wait.until(ExpectedConditions.elementToBeClickable(By.cssSelector("button.action.primary.checkout")));

        // Scroll to the "Place Order" button to ensure visibility
        jsExecutor.executeScript("arguments[0].scrollIntoView(true);", placeOrderButton);

        // Click the "Place Order" button
        placeOrderButton.click();

    }
}
