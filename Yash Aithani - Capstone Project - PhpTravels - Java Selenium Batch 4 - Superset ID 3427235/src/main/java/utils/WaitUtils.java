package utils;

import java.time.Duration;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

public class WaitUtils {
	private WebDriverWait wait;

	 // Constructor to initialize WebDriver and WebDriverWait
    public WaitUtils(WebDriver driver, int duration) {
        this.wait = new WebDriverWait(driver, Duration.ofSeconds(duration));
    }

    /**
     * Wait for element to be visible
     */
    public WebElement waitForVisibility(By locator) {
        return wait.until(ExpectedConditions.visibilityOfElementLocated(locator));
    }

    /**
     * Wait for element to be clickable
     */
    public WebElement waitForClickability(By locator) {
        return wait.until(ExpectedConditions.elementToBeClickable(locator));
    }

    
    public WebElement waitForClickability(WebElement ele) {
        return wait.until(ExpectedConditions.elementToBeClickable(ele));
    }

    
    /**
     * Wait for presence of element in DOM (not necessarily visible)
     */
    public WebElement waitForPresence(WebElement element) {
        return wait.until(ExpectedConditions.presenceOfElementLocated((By) element));
    }

    /**
     * Wait for alert to be present
     */
    public void waitForAlert() {
        wait.until(ExpectedConditions.alertIsPresent());
    }

    /**
     * Wait for a specific title
     */
    public void waitForTitle(String title) {
        wait.until(ExpectedConditions.titleIs(title));
    }


}

