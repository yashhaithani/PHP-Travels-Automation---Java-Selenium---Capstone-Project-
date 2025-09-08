package pages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;

public class PaymentFailurePage {

    private WebDriver driver;
    private WebDriverWait wait;

    // Locator for the payment failure message shown on PayPal page
    private By failureMessageLocator = By.xpath("//*[contains(text(),'card details not valid') or contains(text(),'invalid')]");

    public PaymentFailurePage(WebDriver driver) {
        this.driver = driver;
        this.wait = new WebDriverWait(driver, Duration.ofSeconds(10));
    }

    /**
     * Waits for failure message to be visible and returns true if displayed
     */
    public boolean isFailureMessageDisplayed() {
        try {
            wait.until(ExpectedConditions.visibilityOfElementLocated(failureMessageLocator));
            return driver.findElement(failureMessageLocator).isDisplayed();
        } catch (Exception e) {
            return false;
        }
    }
}
