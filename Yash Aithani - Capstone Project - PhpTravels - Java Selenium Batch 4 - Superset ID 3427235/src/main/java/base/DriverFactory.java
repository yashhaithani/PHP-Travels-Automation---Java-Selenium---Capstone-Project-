package base;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.edge.EdgeDriver;
import io.github.bonigarcia.wdm.WebDriverManager;
import org.openqa.selenium.NoSuchSessionException;

import java.time.Duration;

public class DriverFactory {

    private static WebDriver driver;   // Singleton

    public static WebDriver initDriver(String browser) {
        if (driver == null) {
            System.out.println("Launching browser: " + browser);

            if (browser.equalsIgnoreCase("chrome")) {
                WebDriverManager.chromedriver().setup();
                ChromeOptions options = new ChromeOptions();
                options.addArguments("--start-maximized");
                options.addArguments("--incognito");
                options.addArguments("--remote-allow-origins=*");
                driver = new ChromeDriver(options);

            } else if (browser.equalsIgnoreCase("firefox")) {
                WebDriverManager.firefoxdriver().setup();
                driver = new FirefoxDriver();

            } else if (browser.equalsIgnoreCase("edge")) {
                WebDriverManager.edgedriver().setup();
                driver = new EdgeDriver();

            } else {
                throw new RuntimeException("Invalid browser: " + browser);
            }

            driver.manage().deleteAllCookies();
            driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(10));
        } else {
            // Check if driver session is still valid (browser not closed)
            try {
                driver.getTitle(); // simple command to verify session validity
            } catch (NoSuchSessionException | IllegalStateException ex) {
                // Session invalid or browser closed, reinitialize
                driver = null;
                return initDriver(browser);
            }
        }
        return driver;
    }

    public static WebDriver getDriver() {
        return driver;
    }

    // Modified quitDriver: only quit if driver is initialized and browser is open
    public static void quitDriver() {
        if (driver != null) {
            try {
                driver.quit();
            } catch (Exception e) {
                System.out.println("Driver quit exception: " + e.getMessage());
            }
            driver = null;
        }
    }
}
