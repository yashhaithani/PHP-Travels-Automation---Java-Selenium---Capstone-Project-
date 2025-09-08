package runner;

import org.testng.annotations.DataProvider;

import io.cucumber.testng.AbstractTestNGCucumberTests;
import io.cucumber.testng.CucumberOptions;

@CucumberOptions(
		features = {
				"src/test/resources/features/01_RegistrationPage.feature",
				"src/test/resources/features/02_FlightBooking.feature",
				"src/test/resources/features/03_PaymentGatewayfailure.feature"
		},
		glue = {"stepDefinition", "hooks", "base"},
		plugin = {
				"pretty",
				"html:target/cucumber-reports/Cucumber.html",
				"json:target/cucumber-reports/Cucumber.json",
				"junit:target/cucumber-reports/Cucumber.xml",
				"com.aventstack.extentreports.cucumber.adapter.ExtentCucumberAdapter:target/extent-reports/"
		},
		tags = "@positive or @negative or @search or @booking or @negative",
		dryRun = false,
		monochrome = true
)
public class TestRunner extends AbstractTestNGCucumberTests {
	@Override
	@DataProvider(parallel = false)
	public Object[][] scenarios() {
		return super.scenarios();
	}
}
