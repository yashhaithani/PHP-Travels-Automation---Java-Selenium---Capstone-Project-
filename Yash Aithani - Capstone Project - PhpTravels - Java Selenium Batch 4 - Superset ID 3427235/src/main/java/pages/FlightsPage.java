package pages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;

public class FlightsPage {
	WebDriver driver;
    private By flightsTab = By.xpath("//a[normalize-space()='Flights']");

    public  FlightsPage(WebDriver driver) {
        this.driver = driver;
    }

    public void goToFlights() {
        driver.findElement(flightsTab).click();
    }

}
