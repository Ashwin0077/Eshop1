package pages;

import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;

public class BasketPage {
    WebDriver driver;

    public BasketPage(WebDriver driver) {
        this.driver = driver;
    }
    public void clickCheckout() {
        WebElement checkoutButton = driver.findElement(By.xpath("/html/body/div/div/form/div/div[3]/section[2]/a"));

        // Scroll the element into view
        ((JavascriptExecutor) driver).executeScript("arguments[0].scrollIntoView(true);", checkoutButton);

        // Wait until the element is clickable
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));
        wait.until(ExpectedConditions.elementToBeClickable(checkoutButton));

        // Click the element using JavaScript
        ((JavascriptExecutor) driver).executeScript("arguments[0].click();", checkoutButton);
    }


}
