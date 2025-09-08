package report;

import com.aventstack.extentreports.ExtentReports;
import com.aventstack.extentreports.reporter.ExtentSparkReporter;
import com.aventstack.extentreports.reporter.configuration.Theme;

public class ExtentReportManager {

    private static ExtentReports extent;

    private ExtentReportManager() {
        // Private constructor to prevent instantiation
    }

    public static synchronized ExtentReports getInstance() {
        if (extent == null) {
            createInstance();
        }
        return extent;
    }

    private static void createInstance() {
        String reportPath = System.getProperty("user.dir") + "/target/ExtentReports/extent-report.html";

        ExtentSparkReporter spark = new ExtentSparkReporter(reportPath);
        spark.config().setTheme(Theme.STANDARD);
        spark.config().setDocumentTitle("Automation Test Report");
        spark.config().setReportName("Cucumber Test Execution Report");

        extent = new ExtentReports();
        extent.attachReporter(spark);

        // Add system/environment details
        extent.setSystemInfo("Tester", "QA Team");
        extent.setSystemInfo("Environment", "QA");
        extent.setSystemInfo("Browser", System.getProperty("browser", "chrome"));
    }
}
