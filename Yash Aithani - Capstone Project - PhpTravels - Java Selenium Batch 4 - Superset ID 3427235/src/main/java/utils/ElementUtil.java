package utils;

import java.text.SimpleDateFormat;
import java.time.Duration;
import java.util.Date;

import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.StaleElementReferenceException;
import org.openqa.selenium.TimeoutException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import base.DriverFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class ElementUtil {

	private WebDriver driver;
	private final WebDriverWait wait;
	private WaitUtils waitUtil = new WaitUtils(driver, 10);
	private static final Logger logger = LoggerFactory.getLogger(ElementUtil.class);

	// default constructor uses 10s wait
	public ElementUtil(WebDriver driver) {
		this(driver, Duration.ofSeconds(10));
	}

	public ElementUtil(WebDriver driver, Duration timeout) {
		if (driver == null) throw new IllegalArgumentException("WebDriver cannot be null");
		this.driver = driver;
		this.wait = new WebDriverWait(driver, timeout);
	}

	private WebElement waitUntilVisible(By locator) throws Exception {
		try {
			return wait.until(ExpectedConditions.visibilityOfElementLocated(locator));
		} catch (TimeoutException e) {
			throw new Exception("Element not visible within timeout: " + locator, e);
		} catch (NoSuchElementException e) {
			throw new Exception("Element not found: " + locator, e);
		}
	}

	private WebElement waitUntilClickable(By locator) throws Exception {
		try {
			return wait.until(ExpectedConditions.elementToBeClickable(locator));
		} catch (TimeoutException e) {
			throw new Exception("Element not clickable within timeout: " + locator, e);
		}
	}

	public boolean isTextBoxVisible(WebElement element) {
		try {
			wait.until(ExpectedConditions.visibilityOf(element));
			return true;
		} catch (Exception e) {
			logger.warn("Textbox not visible: {}", e.getMessage());
			return false;
		}
	}

	public boolean isTextBoxEnabled(WebElement element) {
		try {
			wait.until(ExpectedConditions.elementToBeClickable(element));
			return element.isEnabled();
		} catch (Exception e) {
			logger.warn("Textbox not enabled: {}", e.getMessage());
			return false;
		}
	}

	public boolean hasText(WebElement element) {
		try {
			String value = element.getAttribute("value");
			return value != null && !value.trim().isEmpty();
		} catch (Exception e) {
			logger.error("Textbox text check failed: {}", e.getMessage());
			return false;
		}
	}

	public void enterText(WebElement element, String text) {
		if (isTextBoxVisible(element) && isTextBoxEnabled(element)) {
			element.clear();
			element.sendKeys(text);
			logger.info("Entered text: {}", text);
		} else {
			throw new RuntimeException("Textbox is not ready for input");
		}
	}

	public String getPlaceholder(WebElement element) {
		return element.getAttribute("placeholder");
	}

	public boolean isEnabled(By locator) {
		try {
			WebElement ele = waitUntilVisible(locator);
			return ele.isEnabled();
		} catch (Exception e) {
			return false;
		}
	}

	public boolean clickWebElement(WebElement element, String methodName) {
		boolean flag = false;
		int retries = 2;

		try {
			waitUtil.waitForClickability(element);

			for (int i = 0; i < retries; i++) {
				try {
					element.click();
					flag = true;
					break;
				} catch (StaleElementReferenceException sere) {
					if (i == retries - 1) {
						logger.error("StaleElementReferenceException in method: {}", methodName);
						throw sere;
					}
				} catch (Exception e) {
					logger.warn("Fallback to JS click in method: {}", methodName);
					((JavascriptExecutor) driver).executeScript("arguments[0].click();", element);
				}
			}

		} catch (Exception e) {
			logger.error("Failed to click element in method: {}", methodName, e);
			flag = false;
		}
		return flag;
	}

	public void clickButton(WebElement element) {
		try {
			wait.until(ExpectedConditions.elementToBeClickable(element));
			element.click();
			logger.info("Clicked on button: {}", element.getText());
		} catch (Exception e) {
			throw new RuntimeException("Failed to click button: " + e.getMessage());
		}
	}

	public void highlightAndClick(WebElement element) {
		try {
			JavascriptExecutor js = (JavascriptExecutor) DriverFactory.getDriver();
			js.executeScript("arguments[0].setAttribute('style', 'border: 2px solid red; background: yellow;');", element);
			Thread.sleep(500);
			clickButton(element);
		} catch (Exception e) {
			throw new RuntimeException("Failed to highlight and click button: " + e.getMessage());
		}
	}

	public void highlightElement(WebElement element) {
		JavascriptExecutor js = (JavascriptExecutor) driver;
		try {
			String originalStyle = element.getAttribute("style");

			js.executeScript("arguments[0].setAttribute('style', arguments[1]);",
					element, "border: 2px solid red; background: yellow;");

			Thread.sleep(500);

			js.executeScript("arguments[0].setAttribute('style', arguments[1]);",
					element, originalStyle);
			logger.info("Element highlighted with original style restored");
		} catch (InterruptedException e) {
			Thread.currentThread().interrupt();
		}
	}

	public boolean verifyText(WebElement element, String textValue) {
		boolean flag = false;
		try {
			waitUtil.waitForPresence(element);
			String text = element.getText().trim();
			if (text.equals(textValue)) {
				flag = true;
				logger.info("Text verified successfully: {}", text);
				return flag;
			}
		} catch (Exception e) {
			logger.error("Text verification failed: {}", e.getMessage());
		}
		return flag;
	}

	public static String getCurrentDate(String format) {
		SimpleDateFormat sdf = new SimpleDateFormat(format);
		return sdf.format(new Date());
	}
}
