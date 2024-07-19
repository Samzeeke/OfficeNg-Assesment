package org.example;

import io.github.bonigarcia.wdm.WebDriverManager;

import java.time.Duration;

import org.openqa.selenium.*;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
//import org.openqa.selenium.firefox.FirefoxDriver;
//import org.openqa.selenium.firefox.FirefoxOptions;
import org.testng.annotations.*;
import static org.testng.Assert.*;

public class SauceDemo {

    private WebDriver driver;
    private String baseUrl;
    private boolean acceptNextAlert = true;
    private StringBuffer verificationErrors = new StringBuffer();
    private JavascriptExecutor js;

    @BeforeClass
    public void setUp() throws Exception {
        WebDriverManager.chromedriver().setup();
        ChromeOptions options = new ChromeOptions();
        options.setBinary("/Applications/Google Chrome.app/Contents/MacOS");
        WebDriver driver = new ChromeDriver();

        //FirefoxOptions options = new FirefoxOptions();
        //options.setBinary("/Applications/Firefox.app/Contents/MacOS/firefox-bin");
        //WebDriver driver = new FirefoxDriver(options);

       // System.setProperty("", "");
      //  ChromeOptions options = new ChromeOptions();
      //  WebDriver driver = new ChromeDriver(options);

        baseUrl = "https://www.saucedemo.com/";
        driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(30));
        js = (JavascriptExecutor) driver;
    }

    @Test
    public void testSaucedemo() throws Exception {
        // Step 1: Navigate to the Sauce Labs Sample Application in Incognito mode
        driver.get(baseUrl);

        // Step 2: Enter valid credentials to log in
        driver.findElement(By.id("user-name")).sendKeys("standard_user");
        driver.findElement(By.id("password")).sendKeys("secret_sauce");
        driver.findElement(By.id("login-button")).click();

        // Step 3: Verify that the login is successful and the user is redirected to the products page
        assertEquals(driver.getCurrentUrl(), "https://www.saucedemo.com/inventory.html");

        // Step 4: Select a T-shirt by clicking on its image or name
        driver.findElement(By.xpath("//img[@alt='Sauce Labs Bolt T-Shirt']")).click();

        // Step 5: Verify that the T-shirt details page is displayed
        assertEquals(driver.getCurrentUrl(), "https://www.saucedemo.com/inventory-item.html?id=1");

        // Step 6: Click the "Add to Cart" button
        driver.findElement(By.id("add-to-cart")).click();

        // Step 7: Verify that the T-shirt is added to the cart successfully
        assertEquals(driver.findElement(By.className("shopping_cart_badge")).getText(), "1");

        // Step 8: Navigate to the cart by clicking the cart icon or accessing the cart page directly
       // driver.findElement(By.className("shopping_cart_link")).click();
        driver.findElement(By.linkText("1")).click();

        // Step 9: Verify that the cart page is displayed
        assertEquals(driver.getCurrentUrl(), "https://www.saucedemo.com/cart.html");

        // Step 10: Review the items in the cart and ensure that the T-shirt is listed with the correct details
        WebElement cartItem = driver.findElement(By.className("cart_item"));
        assertTrue(cartItem.getText().contains("Sauce Labs Bolt T-Shirt"));
        assertTrue(cartItem.getText().contains("$15.99"));

        // Step 11: Click the "Checkout" button
        driver.findElement(By.id("checkout")).click();

        // Step 12: Verify that the checkout information page is displayed
        assertEquals(driver.getCurrentUrl(), "https://www.saucedemo.com/checkout-step-one.html");

        // Step 13: Enter the required checkout information
        driver.findElement(By.id("first-name")).sendKeys("Samuel");
        driver.findElement(By.id("last-name")).sendKeys("Ezekiel");
        driver.findElement(By.id("postal-code")).sendKeys("100619");

        // Step 14: Click the "Continue" button
        driver.findElement(By.id("continue")).click();

        // Step 15: Verify that the order summary page is displayed
        assertEquals(driver.getCurrentUrl(), "https://www.saucedemo.com/checkout-step-two.html");

        // Step 16: Click the "Finish" button
        driver.findElement(By.id("finish")).click();

        // Step 17: Verify that the order confirmation page is displayed
        assertEquals(driver.getCurrentUrl(), "https://www.saucedemo.com/checkout-complete.html");
        assertTrue(driver.findElement(By.className("complete-header")).getText().contains("THANK YOU FOR YOUR ORDER"));

        // Step 18: Logout from the application
        driver.findElement(By.id("react-burger-menu-btn")).click();
        driver.findElement(By.id("logout_sidebar_link")).click();

        // Step 19: Verify that the user is successfully logged out and redirected to the login page
        assertEquals(driver.getCurrentUrl(), "https://www.saucedemo.com/");
    }

    @AfterClass(alwaysRun = true)
    public void tearDown() throws Exception {
        if (driver != null) {
            driver.quit();
        }
        String verificationErrorString = verificationErrors.toString();
        if (!"".equals(verificationErrorString)) fail(verificationErrorString);
    }

    private boolean isElementPresent(By by) {
        try {
            driver.findElement(by);
            return true;
        } catch (NoSuchElementException e) {
            return false;
        }
    }

    private boolean isAlertPresent() {
        try {
            driver.switchTo().alert();
            return true;
        } catch (NoAlertPresentException e) {
            return false;
        }
    }

    private String closeAlertAndGetItsText() {
        try {
            Alert alert = driver.switchTo().alert();
            String alertText = alert.getText();
            if (acceptNextAlert) {
                alert.accept();
            } else {
                alert.dismiss();
            }
            return alertText;
        } finally {
            acceptNextAlert = true;
        }
    }
}
