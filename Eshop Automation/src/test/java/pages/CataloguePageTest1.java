package pages;

import io.github.bonigarcia.wdm.WebDriverManager;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.Select;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import java.time.Duration;
import java.util.List;

public class CataloguePageTest1 {

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
    public void verifyPageLoad() {
        String expectedTitle = "Catalog - Microsoft.eShopOnWeb";
        Assert.assertEquals(driver.getTitle(), expectedTitle, "Page title mismatch");
    }

    @Test
    public void verifyHeaderLogo() {
        WebElement logo = driver.findElement(By.cssSelector("a[href='/'] img"));
        Assert.assertTrue(logo.isDisplayed(), "Logo is not displayed");
        Assert.assertEquals(logo.getAttribute("src"), "/images/brand.png", "Incorrect logo image source");

        logo.click();
        Assert.assertEquals(driver.getCurrentUrl(), "https://localhost:5106/", "Logo did not navigate to the homepage");
    }

    @Test
    public void verifyIdentitySection() {
        WebElement identity = driver.findElement(By.className("esh-identity-name"));
        Assert.assertEquals(identity.getText(), "demouser@microsoft.com", "User email is not displayed correctly");
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
    public void addItemToBasket() throws InterruptedException {
        WebElement addToBasketButton = driver.findElement(By.xpath("//input[@value='[ ADD TO BASKET ]']"));
        addToBasketButton.click();

        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));
        WebElement basketBadge = wait.until(ExpectedConditions.visibilityOfElementLocated(By.className("esh-basketstatus-badge")));

        Assert.assertNotEquals(basketBadge.getText(), "0", "Basket count did not update after adding item");
    }

    @Test
    public void verifyPagination() {
        WebElement nextButton = driver.findElement(By.id("Next"));
        Assert.assertTrue(nextButton.isDisplayed(), "Next button is not displayed");

        nextButton.click();
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));
        wait.until(ExpectedConditions.urlContains("pageId=1"));

        Assert.assertTrue(driver.getCurrentUrl().contains("pageId=1"), "Did not navigate to the next page");
    }

    @Test
    public void verifyLogout() {
        WebElement logoutLink = driver.findElement(By.xpath("//a[contains(text(), 'Log Out')]"));
        logoutLink.click();

        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));
        wait.until(ExpectedConditions.urlContains("Identity/Account/Login"));

        Assert.assertTrue(driver.getCurrentUrl().contains("Identity/Account/Login"), "Logout did not redirect to login page");
    }

    @Test
    public void verifyBasketNavigation() {
        WebElement basketLink = driver.findElement(By.className("esh-basketstatus"));
        basketLink.click();

        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));
        wait.until(ExpectedConditions.urlContains("/Basket"));

        Assert.assertTrue(driver.getCurrentUrl().contains("/Basket"), "Clicking basket did not navigate to basket page");
    }

    @AfterMethod
    public void tearDown() {
        if (driver != null) {
            driver.quit();
        }
    }
}
