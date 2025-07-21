package pages;


import io.github.bonigarcia.wdm.WebDriverManager;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.safari.SafariDriver;
import org.testng.Assert;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

public class LoginTest {
    WebDriver driver;

    @BeforeMethod
    public void setup() {

        System.setProperty("webdriver.gecko.driver", "/usr/local/bin/geckodriver");
        WebDriver driver = new FirefoxDriver();
        driver.manage().window().maximize();
        driver.get("localhost:5106");

    }

    @Test
    public void testValidLogin() {
        LoginPage loginPage = new LoginPage(driver);
        loginPage.login("demouser@microsoft.com", "Pass@word1");
        String expectedUrl = "https://localhost:5106/Identity/Account/Login";
        System.out.println(driver.getCurrentUrl());
        Assert.assertEquals(driver.getCurrentUrl(), expectedUrl, "Login failed");
    }

    @Test
    public void testAddItemToBasket() throws InterruptedException {
        // Login to the application
        LoginPage loginPage = new LoginPage(driver);
        loginPage.login("demouser@microsoft.com", "Pass@word1");

        // Navigate to the catalog page after login
        driver.get("https://localhost:5106/catalog");

        // Locate and click on the first "Add to Basket" button
        WebElement addToBasketButton = driver.findElement(By.xpath("//input[@value='[ ADD TO BASKET ]']"));
        addToBasketButton.click();

        // Optional: Wait for the item to be added to the basket (can be replaced with explicit waits)
        Thread.sleep(2000);

        // Check the basket status to ensure the item count has increased
        WebElement basketStatus = driver.findElement(By.className("esh-basketstatus-badge"));
        String itemCount = basketStatus.getText();

        // Validate if the item count in the basket has increased (assuming it starts with 0)
        Assert.assertNotEquals(itemCount, "0", "Item was not added to the basket");
    }

    // test invalid login

    //


    @AfterMethod
    public void tearDown() {
        driver.quit();
    }
}
