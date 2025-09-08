package hooks;

import com.aventstack.extentreports.ExtentReports;
import com.aventstack.extentreports.ExtentTest;
import com.aventstack.extentreports.Status;

import base.DriverFactory;
import io.cucumber.java.*;

import report.ExtentReportManager;

import java.io.File;
import java.text.SimpleDateFormat;
import java.util.Date;

import org.apache.commons.io.FileUtils;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;

public class Hooks {

    private static ExtentReports extent = ExtentReportManager.getInstance();
    private static ThreadLocal<ExtentTest> scenarioTest = new ThreadLocal<>();

    /**
     * Launch browser ONCE before all scenarios
     */
    @Before(order = 0)
    public void setUp(Scenario scenario) {
        if (DriverFactory.getDriver() == null) {
            DriverFactory.initDriver("chrome");
            System.out.println("✅ Browser launched successfully");
        }
        scenarioTest.set(extent.createTest(scenario.getName()));
    }

    /**
     * Capture screenshot after every step
     */
    @AfterStep
    public void afterStep(Scenario scenario) {
        try {
            if (DriverFactory.getDriver() != null) {
                String folderPath = System.getProperty("user.dir") + "/test-output/screenshots/";
                new File(folderPath).mkdirs();

                String timestamp = new SimpleDateFormat("yyyyMMdd_HHmmss").format(new Date());
                String screenshotPath = folderPath + scenario.getName().replaceAll(" ", "_") + "_" + timestamp + ".png";

                File srcFile = ((TakesScreenshot) DriverFactory.getDriver()).getScreenshotAs(OutputType.FILE);
                FileUtils.copyFile(srcFile, new File(screenshotPath));

                byte[] screenshotBytes = ((TakesScreenshot) DriverFactory.getDriver()).getScreenshotAs(OutputType.BYTES);
                scenario.attach(screenshotBytes, "image/png", "Step Screenshot");

                if (scenario.isFailed()) {
                    scenarioTest.get().log(Status.FAIL, "Step failed")
                                 .addScreenCaptureFromPath(screenshotPath);
                } else {
                    scenarioTest.get().log(Status.PASS, "Step passed")
                                 .addScreenCaptureFromPath(screenshotPath);
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * Log scenario status (keep browser alive between scenarios)
     */
    @After
    public void afterScenario(Scenario scenario) {
        if (scenario.isFailed()) {
            scenarioTest.get().log(Status.FAIL, "Scenario failed: " + scenario.getName());
        } else {
            scenarioTest.get().log(Status.PASS, "Scenario passed: " + scenario.getName());
        }
    }

    /**
     * Quit driver ONLY once after all scenarios
     */
    @AfterAll
    public static void tearDown() {
        DriverFactory.quitDriver();
        extent.flush();
        System.out.println("❌ Browser closed after all scenarios");
    }
}
