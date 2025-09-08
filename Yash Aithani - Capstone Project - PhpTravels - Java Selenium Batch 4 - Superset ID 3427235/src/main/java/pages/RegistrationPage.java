package pages;

import java.time.Duration;

import org.openqa.selenium.By;
import org.openqa.selenium.ElementClickInterceptedException;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.TimeoutException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.Select;
import org.openqa.selenium.support.ui.Wait;
import org.openqa.selenium.support.ui.WebDriverWait;

import base.DriverFactory;
import utils.ElementUtil;

public class RegistrationPage {
   private WebDriver driver;
   WebDriverWait wait;
   ElementUtil webelements = new ElementUtil(DriverFactory.getDriver());
   private By firstName=By.id("firstname");
   private By lastName=By.id("last_name");
   private By countryDropdown = By.xpath("//button[@title='Select Country']");
 // clickable dropdown box
   private By countrySearchBox = By.xpath("//input[@aria-label='Search']");
   private By phone=By.id("phone");
   private By emailAddress=By.id("user_email");
   private By password=By.id("password");
   private By signUpButton = By.id("submitBTN");
   private By successMsg=By.xpath("//h2[contains(text(),'Hi')]");
   private String registeredEmail;
   private By errorAlert = By.cssSelector(".alert-danger"); 
   
   

   public RegistrationPage(WebDriver driver) {
	  this.driver=driver;
	 
	  wait = new WebDriverWait(driver, Duration.ofSeconds(20));
	   
   
   }
   public void enterfirstName(String value) {
	   webelements.enterText(driver.findElement(firstName),value);
   }
   public void enterlastName(String value) {
	   webelements.enterText(driver.findElement(lastName), value);
   }
   public void selectCountry(String countryName) {
	    WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));

	    // Step 1: Click the dropdown
	    WebElement dropdown = wait.until(
	            ExpectedConditions.elementToBeClickable(
	            		 By.xpath("//button[@title='Select Country']")));
	    dropdown.click();

	    // Step 2: Type in the search box
	    WebElement searchBox = wait.until(
	            ExpectedConditions.visibilityOfElementLocated(
	            		By.xpath("//input[@aria-label='Search']")));
	    searchBox.sendKeys(countryName);

	    // Step 3: Select from the result
	    WebElement option = wait.until(ExpectedConditions
	            .elementToBeClickable(By.xpath("//span[@class='text' and normalize-space()='" + countryName + "']")));
	    option.click();
	}


   
   public void  enterPhone(String value) {
	   webelements.enterText(driver.findElement(phone), value);
   }
   
   public void enterEmailAddress(String value) {
	   webelements.enterText(driver.findElement(emailAddress), value);
   }
   
   public void enterPassword(String value) {
	   webelements.enterText(driver.findElement(password), value);
   }
	public void waitForCaptchaSolve() {
		System.out.println("Waiting for CAPTCHA to be solved manually in the browser...");

		// Wait for captcha to disappear or some element indicating the captcha is solved
		try {
			wait.until(ExpectedConditions.invisibilityOfElementLocated(By.id("captchaElementId")));
			// replace "captchaElementId" with actual captcha element ID or locator
		} catch (TimeoutException e) {
			System.out.println("Captcha was not solved in time.");
		}
	}


	public void Signup() {
	   
	   WebElement btn = wait.until(ExpectedConditions.elementToBeClickable(signUpButton));

	    try {
	        btn.click();
	    } catch (ElementClickInterceptedException e) {
	        ((JavascriptExecutor) driver).executeScript("arguments[0].click();", btn);
	    }

	    // ✅ Wait for redirect or success message
	    try {
	        wait.until(ExpectedConditions.or(
	                ExpectedConditions.urlContains("signup_success"),
	                ExpectedConditions.visibilityOfElementLocated(successMsg)
	        ));
	        System.out.println("✅ Signup successful!");
	    } catch (TimeoutException e) {
	        System.out.println("⚠ Signup did not redirect or show success message in time.");
	        System.out.println("Page URL: " + driver.getCurrentUrl());
	    }
   }
   public String getSuccessMessage() {
	   
		    WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(15));
		    try {
		        WebElement alert = wait.until(ExpectedConditions.visibilityOfElementLocated(
		                By.cssSelector(".alert-success")));
		        return alert.getText();
		    } catch (Exception e1) {
		        try {
		            WebElement loginMsg = driver.findElement(By.xpath("//*[contains(text(),'Account created')]"));
		            return loginMsg.getText();
		        } catch (Exception e2) {
		            return "No success message found";
		        }
		    }
		}
   public void setRegisteredEmail(String email) {
       this.registeredEmail = email;
   }

   public String getRegisteredEmail() {
       // ✅ Option 1: Return the stored value
       return registeredEmail;

       // ✅ Option 2: Or read directly from UI if that's the intention
       // return driver.findElement(emailField).getAttribute("value");
   }
   public String getAppErrorMessage() {
       try {
           return driver.findElement(errorAlert).getText();
       } catch (Exception e) {
           return null; // no app error
       }
   }
   public String getBrowserValidationMessage() {
       WebElement email = driver.findElement(emailAddress);
       return email.getAttribute("validationMessage");
   }
   public boolean isUserRegistered() {
	// TODO Auto-generated method stub
	return false;
   }
   public boolean isSubmitButtonEnabled() {
	    return driver.findElement(By.id("submitBTN")).isEnabled();
	}

   }
  
  



