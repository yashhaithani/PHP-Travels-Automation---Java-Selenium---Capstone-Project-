package stepDefinition;

import java.time.Duration;
import java.util.Scanner;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;

import base.DriverFactory;
import io.cucumber.java.en.*;
import pages.RegistrationPage;

import utils.AppLogger;
import utils.ConfigReader;
import utils.TestContext;

public class RegistrationPageStepsPositive {
	private WebDriver driver;
    RegistrationPage Page;
       ConfigReader readprop=new ConfigReader();
	@Given("user is on the {string} Page")
	public void user_is_on_the_page(String string) {
	   driver= DriverFactory.getDriver();
	    driver.get(readprop.getProperty("browserurl"));
	       AppLogger.info("Launching browser");
	       Page=new RegistrationPage(driver);
	   
	}

	@When("user enters First Name {string} under Sign up")
	public void user_enters_first_name_under_sign_up(String value) {
	   Page.enterfirstName(value);
	}

	@When("user enters Last Name {string} under Sign up")
	public void user_enters_last_name_under_sign_up(String string) {
	    Page.enterlastName(string);
	}

	@When("user selects Select Country {string} under Sign up")
	public void user_selects_select_country_under_sign_up(String string) {
	   Page.selectCountry(string);
	}

	@When("user enters Phone {string} under Sign up")
	public void user_enters_phone_under_sign_up(String string) {
	   Page.enterPhone(string);
	}

	@When("user enters EmailAddress {string} under Sign up")
	public void user_enters_email_address_under_sign_up(String string) {
		String uniqueEmail = "test" + System.currentTimeMillis() + "@gmail.com";
Page.enterEmailAddress(uniqueEmail);
	}

	@When("user enters Password {string} under Sign up")
	public void user_enters_password_under_sign_up(String string) {
	   Page.enterPassword(string);
	}
	@When("user solves captcha manually")
	public void user_solves_captcha_manually() {
		Page.waitForCaptchaSolve();
	}


	@When("user clicks Sign up button under Sign up")
	public void user_clicks_sign_up_button_under_sign_up() {
		if (Page.isSubmitButtonEnabled()) {
			Page.Signup();
		} else {
			AppLogger.info("Submit button disabled – skipping click in negative flow");
		}
	}
//Positive Flow
	@Then("the user should be registered Successfully under Sign up")
	public void the_user_should_be_registered_successfully_under_sign_up() {
		String msg = Page.getSuccessMessage();
       
        
        TestContext.registeredEmail = Page.getRegisteredEmail(); // implement getter in Page
        TestContext.registeredPassword = "abc123"; // replace with real password
        TestContext.isRegistered = true;
	}
//Negative Flow
	@Then("user should not be registered")
	public void user_should_not_be_registered() {
	    String currentUrl = driver.getCurrentUrl();

	    // 1. Ensure user is still on signup page
	    Assert.assertTrue(currentUrl.contains("/signup"),
	        "❌ Expected to stay on signup page but got redirected to: " + currentUrl);

	    // 2. Ensure dashboard/confirmation not shown
	    boolean isDashboardVisible = driver.findElements(By.xpath("//h3[contains(text(),'Dashboard')]")).size() > 0;
	    Assert.assertFalse(isDashboardVisible, "❌ Unexpectedly redirected to Dashboard!");
	}
	


}
