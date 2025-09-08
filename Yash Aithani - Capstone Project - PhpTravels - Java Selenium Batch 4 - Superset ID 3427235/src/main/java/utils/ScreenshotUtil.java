package utils;

import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;

import java.io.File;
import java.nio.file.Files;


public class ScreenshotUtil {

	public static String captureScreenshot(WebDriver driver, String screenshotName) {
	    try {
	        File src = ((TakesScreenshot) driver).getScreenshotAs(OutputType.FILE);

	        // Create folder if not exists
	        String filePath = "eCommerceProject\\Reports";
	        File directory = new File(filePath);
	        if (!directory.exists()) {
	            directory.mkdirs();
	        }

	        // Save with timestamp
	        String path = filePath + File.separator + screenshotName + "_" + System.currentTimeMillis() + ".png";
	        Files.copy(src.toPath(), new File(path).toPath());

	        return path;
	    } catch (Exception e) {
	        e.printStackTrace();
	        return null;
	    }
	}

}


