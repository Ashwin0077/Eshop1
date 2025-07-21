package pages;


import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.Select;

import java.util.List;

public class CataloguePage {
    WebDriver driver;

    // Constructor
    public CataloguePage(WebDriver driver) {
        this.driver = driver;
    }

    // Locators
    By logoutButton = By.xpath("//a[contains(text(),'Log Out')]");
    By basketStatus = By.className("esh-basketstatus-badge");
    By addToBasketButton = By.xpath("//input[@value='[ ADD TO BASKET ]']");
    By catalogItemName = By.xpath("//div[@class='esh-catalog-name']/span");
    By nextPageButton = By.id("Next");
    By logOut= By.xpath("//*[@id='logoutForm']/section[2]/a[3]/div");
    By loginButton= By.className("esh-identity-name");




    // Methods to interact with elements
    public void clickAddToBasket() {
        driver.findElement(addToBasketButton).click();
    }

    public String getBasketItemCount() {
        return driver.findElement(basketStatus).getText();
    }

    public String getFirstItemName() {
        return driver.findElement(catalogItemName).getText();
    }

    public void clickNextPage() {
        driver.findElement(nextPageButton).click();
    }

    public String logOut()  {
        WebElement logOutElement = driver.findElement(logOut);
        ((JavascriptExecutor) driver).executeScript("arguments[0].scrollIntoView(true);", logOutElement);
        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
        logOutElement.click();

       return driver.findElement(loginButton).getText();
    }

    public void addItemToBasket() {
        driver.findElement(By.xpath("//input[@value='[ ADD TO BASKET ]']")).click(); // Finds and clicks "Add to Basket"
    }

    public void goToBasket() {
        driver.findElement(By.className("esh-basketstatus")).click(); // Clicks on the basket icon
    }
    public void filterProducts(String brand, String type) {
        WebElement brandDropdown = driver.findElement(By.id("CatalogModel_BrandFilterApplied"));
        WebElement typeDropdown = driver.findElement(By.id("CatalogModel_TypesFilterApplied"));

        // Select brand and type
        new Select(brandDropdown).selectByVisibleText(brand);
        new Select(typeDropdown).selectByVisibleText(type);

        // Click on the submit button (assuming it’s an image-based input)
        WebElement submitButton = driver.findElement(By.className("esh-catalog-send"));
        submitButton.click();
    }
    public int getCatalogItemsCount() {
        List<WebElement> catalogItems = driver.findElements(By.className("esh-catalog-item"));
        return catalogItems.size();
    }



}
