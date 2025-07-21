package pages;


import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.safari.SafariDriver;
import org.openqa.selenium.support.ui.Select;
import org.testng.Assert;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import java.util.List;

public class CataloguePageTest {
    WebDriver driver;
    CataloguePage catalogPage;

    @BeforeMethod
    public void setup() {
        System.setProperty("webdriver.gecko.driver", "/usr/local/bin/geckodriver");
        WebDriver driver = new FirefoxDriver();
        driver.manage().window().maximize();
        driver.get("localhost:5106/Identity/Account/Login");
        LoginPage loginPage = new LoginPage(driver);
        loginPage.login("demouser@microsoft.com", "Pass@word1");
        catalogPage = new CataloguePage(driver);
    }

    @Test
    public void testAddToBasket() {
        String initialBasketCount = catalogPage.getBasketItemCount();
        catalogPage.clickAddToBasket();
        String updatedBasketCount = catalogPage.getBasketItemCount();
        Assert.assertNotEquals(initialBasketCount, updatedBasketCount, "Basket count did not increase after adding an item");
    }

    @Test
    public void testNavigateToNextPage() {
        String firstPageItem = catalogPage.getFirstItemName();
        catalogPage.clickNextPage();
        String nextPageItem = catalogPage.getFirstItemName();
        Assert.assertNotEquals(firstPageItem, nextPageItem, "Failed to navigate to the next page");
    }

    @Test
    public void testLogOut() {
        String loginText= catalogPage.logOut();
        Assert.assertEquals(loginText, "LOGIN", "User was not logged out");    }

    @Test
    public void testBasketPageNavigation() {
        WebElement cartIcon = driver.findElement(By.className("esh-basketstatus"));
        cartIcon.click();
        String expectedTitle = "Basket - Microsoft.eShopOnWeb";
        String actualTitle = driver.getTitle();
        Assert.assertEquals(actualTitle, expectedTitle, "Failed to navigate to basket page.");
    }
    @Test
    public void verifyProductFiltering() {
        WebElement brandDropdown = driver.findElement(By.id("CatalogModel_BrandFilterApplied"));
        WebElement typeDropdown = driver.findElement(By.id("CatalogModel_TypesFilterApplied"));

        // Select 'Azure' as brand
        new Select(brandDropdown).selectByVisibleText("Azure");

        // Select 'Mug' as type
        new Select(typeDropdown).selectByVisibleText("Mug");

        // Submit the form
        WebElement submitButton = driver.findElement(By.className("esh-catalog-send"));
        submitButton.click();

        // Validate the filtered results (you need to adjust based on expected items after filtering)
        List<WebElement> catalogItems = driver.findElements(By.className("esh-catalog-item"));
        Assert.assertTrue(catalogItems.size() > 0, "No products found after filtering");
    }
    @Test
    public void verifyProductFilteringg() {
        // Use the CatalogPage object to interact with the page
        catalogPage.filterProducts(".NET", "Mug");

        // Validate the filtered results
        int productCount = catalogPage.getCatalogItemsCount();
        Assert.assertTrue(productCount > 0, "No products found after filtering");
    }

    @AfterMethod
    public void tearDown() {
        if (driver != null) {
            driver.quit();
        }
    }
}
