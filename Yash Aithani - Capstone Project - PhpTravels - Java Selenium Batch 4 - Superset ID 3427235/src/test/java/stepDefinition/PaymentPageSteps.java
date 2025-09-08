package stepDefinition;

import java.time.Duration;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;

import base.DriverFactory;
import io.cucumber.java.en.*;
import utils.AppLogger;
import utils.ConfigReader;

public class PaymentPageSteps {

    private WebDriver driver;
    ConfigReader readprop = new ConfigReader();

    By proceedPaymentBtn = By.id("form"); // proceed button on invoice page
    By paypalButtonContainer = By.xpath("//div[@class='paypal-button-label-container']"); // PayPal payment button container on PayPal page
    By failureMessageLocator = By.xpath("//*[contains(text(),'card details not valid') or contains(text(),'invalid')]");

    @Given("user is on the Invoice page")
    public void onInvoicePage() {
        driver = DriverFactory.getDriver();
        driver.get("https://www.phptravels.net/flights/invoice/20250908052650");
        AppLogger.info("Opened invoice page");
    }

    @When("user proceeds to PayPal payment")
    public void proceedPayment() {
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(15));
        driver.findElement(proceedPaymentBtn).click();
        AppLogger.info("Clicked proceed payment button");

        // Wait until URL changes to PayPal payment page
        wait.until(ExpectedConditions.urlContains("/payment/paypal"));
        String currentUrl = driver.getCurrentUrl();
        Assert.assertTrue(currentUrl.contains("/payment/paypal"),
                "User was not redirected to PayPal payment page, current URL: " + currentUrl);
        AppLogger.info("Redirected to PayPal payment page");
    }

    @When("user chooses PayPal payment button container")
    public void clickPaypalButtonContainer() {
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(15));
        wait.until(ExpectedConditions.elementToBeClickable(paypalButtonContainer)).click();
        AppLogger.info("Clicked PayPal payment button container");
    }

    @Then("payment should fail and test should end with invalid card details message")
    public void verifyPaymentFailure() {
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));
        // Wait for failure message or simulate wait for failure
        boolean failureMessageDisplayed = false;
        try {
            failureMessageDisplayed = wait.until(ExpectedConditions.visibilityOfElementLocated(failureMessageLocator)).isDisplayed();
        } catch (Exception e) {
            failureMessageDisplayed = false;
        }

        // Fail the test forcibly with message about invalid card details
        Assert.assertTrue(failureMessageDisplayed, "❌ Card details not valid message was not displayed, test failed.");
    }
}
