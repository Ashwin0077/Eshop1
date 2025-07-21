package pages;

import org.openqa.selenium.*;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;

public class CheckoutPage {
    WebDriver driver;

    public CheckoutPage(WebDriver driver) {
        this.driver = driver;
    }

    public void clickPayNow() {
        JavascriptExecutor js = (JavascriptExecutor) driver;

        boolean isElementFound = false;

        // Loop for scrolling and checking for the "Pay Now" button
        while (!isElementFound) {
            try {
                // Try to find the element after scrolling
                WebElement payNowButton = driver.findElement(By.xpath("//input[@type='submit' and @value='[ Pay Now ]']"));

                // Scroll to the element
                js.executeScript("arguments[0].scrollIntoView(true);", payNowButton);

                // Click the button once it's in view
                payNowButton.click();
                isElementFound = true;  // Exit loop if element is found and clicked
                System.out.println("Element found and clicked!");

            } catch (NoSuchElementException e) {
                // Scroll down by a small amount (you can adjust the value)
                js.executeScript("window.scrollBy(0, 500);");
                System.out.println("Scrolling down...");
            }
        }







    }

    public String getConfirmationMessage() {
        return driver.findElement(By.tagName("h1")).getText(); // Gets the confirmation message
    }
}
