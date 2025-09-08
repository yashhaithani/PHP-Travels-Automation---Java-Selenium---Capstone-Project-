package stepDefinition;
import pages.*;
import utils.AppLogger;
import utils.ConfigReader;
import org.testng.Assert;

import java.time.Duration;
//import java.util.concurrent.TimeoutException;

import org.openqa.selenium.By;
import org.openqa.selenium.TimeoutException;
//import org.apache.logging.log4j.core.util.Assert;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import base.DriverFactory;
//import dev.failsafe.internal.util.Assert;
import io.cucumber.java.en.*;

public class FlightBookingSteps {
	private WebDriver driver ;
    FlightsPage dashboard;
    FlightSearchPage flights ;
    FlightBookingPage results;
    ConfigReader readprop=new ConfigReader();
	@Given("user is registered and redirected to {string}")
	public void user_is_logged_in_and_redirected_to(String string) {
		driver= DriverFactory.getDriver();
		 driver.get(readprop.getProperty("base.url"));
	       AppLogger.info("Launching browser");
	       dashboard=new FlightsPage(driver);
	       flights=new FlightSearchPage(driver);
	       results=new FlightBookingPage(driver);
	   
	}

	@When("user navigates to {string} section")
	public void user_navigates_to_section(String string) {
	    dashboard.goToFlights();
	}

	@When("user searches flight from {string} to {string} with departure {string}")
	public void user_searches_flight_from_to_with_departure(String from, String to, String date) throws InterruptedException {
	   flights.enterFromCity(from);
	   flights.enterToCity(to);
	   flights.setDepartureDate(date);
	   flights.clickSearch();
	}

	@When("user selects the first flight available")
	public void user_selects_the_first_flight_available() {
	   results.selectFirstAvailableFlightOnResults();
	}
	@When("user fill the passenger and traveler details")
    public void user_fill_the_passenger_and_traveler_details() {
        results.fillPersonal("Neha", "Mehta", "neha.mehta@test.com", "8754911423", "St.45 Kormangla,Bangalore");
        results.fillTraveller("Neha", "Mehta", "A9876543", "neha.mehta@test.com", "8754911423");
        results.agreeTerms();
        results.confirmBooking();
    }

	@Then("user should see booking confirmation")
	public void user_should_see_booking_confirmation() {
		WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(30));

	    try {
	        // Wait until invoice page is loaded
	        wait.until(ExpectedConditions.urlContains("invoice"));

	        // Optionally wait for booking reference or invoice number text
	        WebElement confirmation = wait.until(ExpectedConditions.presenceOfElementLocated(
	                By.xpath("//*[contains(text(),'Booking') or contains(text(),'Invoice') or contains(text(),'Confirmation')]")
	        ));

	        String currentUrl = driver.getCurrentUrl();
	        System.out.println("✅ Booking confirmation page reached: " + currentUrl);
	        System.out.println("✅ Confirmation message: " + confirmation.getText());

	        Assert.assertTrue(true, "Booking confirmation found");
	    } catch (TimeoutException e) {
	        Assert.fail("❌ Booking confirmation not found. Current URL: " + driver.getCurrentUrl());
	    }
	}

}
