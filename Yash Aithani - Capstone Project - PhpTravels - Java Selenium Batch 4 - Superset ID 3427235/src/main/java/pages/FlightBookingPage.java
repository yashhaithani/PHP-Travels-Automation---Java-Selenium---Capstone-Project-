package pages;

import java.time.Duration;

import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import base.DriverFactory;
import utils.ElementUtil;

public class FlightBookingPage {
	WebDriver driver;
	WebDriverWait wait;
	 //private ElementUtil el = new ElementUtil();
    private By firstFlightBookBtn = By.xpath("(//button[@type='submit'][normalize-space()='Select Flight'])[1]");
    private By fromInput = By.xpath("//input[@name='from']");
    private By toInput = By.xpath("//input[@name='to']");
    private By departureDate = By.id("departure");
    // Personal info
    private By pFirstName = By.id("p-first-name");
    private By pLastName = By.id("p-last-name");
    private By pEmail = By.id("p-email");
    private By pPhone = By.id("p-phone");
    private By pAddress = By.id("p-address");
    // Traveller info
    private By tFirstName = By.id("t-first-name-1");
    private By tLastName = By.id("t-last-name-1");
    private By tPassport = By.id("t-passport-1");
    private By tEmail =  By.id("t-email-1");
    private By tPhone = By.id("t-phone-1");

    private By agree = By.xpath("//input[@id='agreechb']");
    private By booking = By.xpath("//button[@id='booking']");



    public FlightBookingPage(WebDriver driver) {
        this.driver = driver;
        this.wait = new WebDriverWait(driver, Duration.ofSeconds(20));
    }

    public void selectFirstAvailableFlightOnResults() {
        // wait for results page to load (URL contains '/flights/')
        //wait.until(ExpectedConditions.urlContains("/flights/"));
        // try to find common "book/select" buttons (text-based heuristics)
    	driver.get("https://www.phptravels.net/flights/DEL/BLR/oneway/economy/20-09-2025/1/0/0");
        
    	WebElement firstFlightBtn = wait.until(
    		    ExpectedConditions.elementToBeClickable(
    		        By.xpath("(//button[contains(@class,'btn') and (contains(text(),'Select') or contains(text(),'Book'))])[1]")
    		    )
    		);

    		try {
    		    firstFlightBtn.click();
    		} catch (Exception e) {
    		    ((JavascriptExecutor) driver).executeScript("arguments[0].click();", firstFlightBtn);
    		}
    		wait.until(ExpectedConditions.urlContains("booking"));
    }
   

	public void fillPersonal(String fn, String ln, String em, String ph, String addr) {
		//driver.manage().timeouts().pageLoadTimeout(Duration.ofSeconds(60));
		//driver.get("https://www.phptravels.net/flight/booking");
		WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(20));
		//System.out.println(driver.getCurrentUrl());
		//System.out.println(driver.getPageSource());
		// wait.until(ExpectedConditions.visibilityOfElementLocated(By.cssSelector("form#booking-form")));
	    // wait until the first name field is visible
	    WebElement firstName = wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("p-first-name")));
	    firstName.sendKeys(fn);
		
		//driver.get("https://www.phptravels.net/flight/booking");
    	//driver.findElement(pFirstName).sendKeys(fn);
        driver.findElement(pLastName).sendKeys(ln);
        driver.findElement(pEmail).sendKeys(em);
        driver.findElement(pPhone).sendKeys(ph);
        driver.findElement(pAddress).sendKeys(addr);
    }

    public void fillTraveller(String fn, String ln, String passport, String em, String ph) {
    	driver.findElement(tFirstName).sendKeys(fn);
        driver.findElement(tLastName).sendKeys(ln);
        driver.findElement(tPassport).sendKeys(passport);
        driver.findElement(tEmail).sendKeys(em);
        driver.findElement(tPhone).sendKeys(ph);
        
    }
    

    public void agreeTerms() {
    	
    	    WebElement checkbox = wait.until(ExpectedConditions.presenceOfElementLocated(agree));

    	    // Scroll if hidden
    	    ((JavascriptExecutor) driver).executeScript("arguments[0].scrollIntoView(true);", checkbox);

    	    // Try normal click, fallback to JS click
    	    try {
    	        wait.until(ExpectedConditions.elementToBeClickable(checkbox)).click();
    	    } catch (Exception e) {
    	        ((JavascriptExecutor) driver).executeScript("arguments[0].click();", checkbox);
    	    }
    	}
 
    public void confirmBooking() {
    	WebElement bookBtn = wait.until(ExpectedConditions.presenceOfElementLocated(booking));

        // Scroll into view
        ((JavascriptExecutor) driver).executeScript("arguments[0].scrollIntoView(true);", bookBtn);

        try {
            wait.until(ExpectedConditions.elementToBeClickable(bookBtn)).click();
        } catch (Exception e) {
            ((JavascriptExecutor) driver).executeScript("arguments[0].click();", bookBtn);
        }
    };
    }


