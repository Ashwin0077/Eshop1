package pages;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.testng.Assert;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

public class FunctionalTests {
    WebDriver driver;
    CataloguePage cataloguePage;
    BasketPage basketPage;
    CheckoutPage checkoutPage;

    @BeforeMethod
    public void setup() {
        System.setProperty("webdriver.gecko.driver", "/usr/local/bin/geckodriver");
        driver = new FirefoxDriver();
        driver.manage().window().maximize();
        driver.get("http://localhost:5106/Identity/Account/Login");

        // Login
        LoginPage loginPage = new LoginPage(driver);
        loginPage.login("admin@microsoft.com", "Pass@word1");

        // Initialize page objects
        cataloguePage = new CataloguePage(driver);
        basketPage = new BasketPage(driver);
        checkoutPage = new CheckoutPage(driver);
    }

    @Test
    public void testCompleteCheckoutProcess() {
        // Add an item to the basket
        cataloguePage.addItemToBasket();

        // Go to the basket page
        cataloguePage.goToBasket();

        // Click "Checkout" on the basket page
        basketPage.clickCheckout();

        // On the checkout page, click "Pay Now"
        checkoutPage.clickPayNow();

        // Verify the confirmation message
        String confirmationMessage = checkoutPage.getConfirmationMessage();
        Assert.assertEquals(confirmationMessage, "Thanks for your Order!", "Order confirmation message is incorrect.");
    }

    @AfterMethod
    public void tearDown() {
        if (driver != null) {
            driver.quit();
        }
    }
}