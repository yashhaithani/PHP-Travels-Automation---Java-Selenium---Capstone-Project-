package pages;

import java.time.Duration;
import java.util.List;

import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.Keys;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.StaleElementReferenceException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

/**
 * Robust FlightSearchPage with resilient suggestion selection and
 * a helper to pick the first flight on results.
 */
public class FlightSearchPage {
    WebDriver driver;
    WebDriverWait wait;

    private By fromInput = By.xpath("//input[@name='from']");
    private By toInput = By.xpath("//input[@name='to']");
    private By departureDate = By.id("departure");
    private By searchBtn = By.id("flights-search");

    public FlightSearchPage(WebDriver driver) {
        this.driver = driver;
        this.wait = new WebDriverWait(driver, Duration.ofSeconds(25));
    }

    public void enterFromCity(String cityOrCode) {
        WebElement from = wait.until(ExpectedConditions.elementToBeClickable(fromInput));
        typeSlowly(from, cityOrCode);
        attemptSelectSuggestion(cityOrCode, from);
    }

    public void enterToCity(String cityOrCode) {
        WebElement to = wait.until(ExpectedConditions.elementToBeClickable(toInput));
        typeSlowly(to, cityOrCode);
        attemptSelectSuggestion(cityOrCode, to);
    }

    public void setDepartureDate(String date) {
        WebElement dep = wait.until(ExpectedConditions.visibilityOfElementLocated(departureDate));
        dep.clear();
        dep.sendKeys(date);
    }

    public void clickSearch() {
    	WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));
        wait.until(ExpectedConditions.elementToBeClickable(searchBtn)).click();
    }

    /**
     * Attempts various strategies to select a suggestion. Retries on stale references.
     */
    private void attemptSelectSuggestion(String keyword, WebElement inputElement) {
        String kw = keyword == null ? "" : keyword.trim();
        if (kw.isEmpty()) throw new IllegalArgumentException("keyword cannot be empty");

        boolean selected = false;
        int attempts = 0;

        while (!selected && attempts < 4) {
            attempts++;
            try {
                // Wait briefly for suggestion container to appear
                By suggestionsContainer = By.xpath(
                    "//div[contains(@class,'autocomplete') or contains(@class,'autocomplete-result') or contains(@class,'suggestions') or contains(@class,'result-option')]");
                wait.until(ExpectedConditions.presenceOfElementLocated(suggestionsContainer));
                Thread.sleep(200); // small breathing room

                // strategy 1: match data-code exactly (airport code e.g. DEL, BLR)
                String code = kw.toUpperCase();
                By byDataCode = By.xpath("//div[contains(@class,'result-option') and translate(@data-code,'abcdefghijklmnopqrstuvwxyz','ABCDEFGHIJKLMNOPQRSTUVWXYZ')='"
                        + code + "']");
                List<WebElement> els = driver.findElements(byDataCode);
                if (!els.isEmpty()) { clickElement(els.get(0)); selected = true; break; }

                // strategy 2: match <strong> city name (case-insensitive)
                String kwLower = kw.toLowerCase();
                By byStrong = By.xpath("//div[contains(@class,'result-option')]//strong[contains(translate(text(),'ABCDEFGHIJKLMNOPQRSTUVWXYZ','abcdefghijklmnopqrstuvwxyz'), '"
                        + kwLower + "')]/ancestor::div[contains(@class,'result-option')]");
                els = driver.findElements(byStrong);
                if (!els.isEmpty()) { clickElement(els.get(0)); selected = true; break; }

                // strategy 3: match whole result-option text (case-insensitive)
                By byDivContains = By.xpath("//div[contains(@class,'result-option') and contains(translate(.,'ABCDEFGHIJKLMNOPQRSTUVWXYZ','abcdefghijklmnopqrstuvwxyz'), '"
                        + kwLower + "')]");
                els = driver.findElements(byDivContains);
                if (!els.isEmpty()) { clickElement(els.get(0)); selected = true; break; }

                // strategy 4: match button text inside option (airport code shown in button)
                By byButton = By.xpath("//div[contains(@class,'result-option')]//button[contains(translate(text(),'ABCDEFGHIJKLMNOPQRSTUVWXYZ','abcdefghijklmnopqrstuvwxyz'), '"
                        + kwLower + "')]/ancestor::div[contains(@class,'result-option')]");
                els = driver.findElements(byButton);
                if (!els.isEmpty()) { clickElement(els.get(0)); selected = true; break; }

                // strategy 5: fallback — use keyboard to pick first suggestion
                inputElement.sendKeys(Keys.ARROW_DOWN);
                Thread.sleep(150);
                inputElement.sendKeys(Keys.ENTER);
                selected = true;
                break;

            } catch (StaleElementReferenceException se) {
                // try again
                System.out.println("⚠️ StaleElementReferenceException, retrying attempt " + attempts + " for: " + kw);
                safeSleep(200);
            } catch (InterruptedException ie) {
                Thread.currentThread().interrupt();
                throw new RuntimeException(ie);
            } catch (Exception e) {
                // debug: print available suggestions for analysis
                System.out.println("⚠️ attempt " + attempts + " failed to select '" + kw + "': " + e.getMessage());
                printCurrentSuggestions();
                safeSleep(300);
            }
        }

        if (!selected) {
            // final debug print before failing
            printCurrentSuggestions();
            throw new RuntimeException("❌ Could not select suggestion for: " + kw);
        } else {
            System.out.println("✅ Selected suggestion for: " + kw);
        }
    }

    // Click with wait + JS fallback
    private void clickElement(WebElement el) {
        try {
            wait.until(ExpectedConditions.elementToBeClickable(el)).click();
        } catch (Exception e) {
            ((JavascriptExecutor) driver).executeScript("arguments[0].click();", el);
        }
    }

    // Slow typing to give site time to populate suggestions
    private void typeSlowly(WebElement input, String text) {
        try {
            input.clear();
            for (char c : text.toCharArray()) {
                input.sendKeys(String.valueOf(c));
                Thread.sleep(50); // tweak if needed
            }
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }
    }

    // Debug helper: print current suggestion texts to console
    private void printCurrentSuggestions() {
        try {
            List<WebElement> all = driver.findElements(By.xpath("//div[contains(@class,'result-option')]"));
            System.out.println("---- suggestion dump (" + all.size() + ") ----");
            for (WebElement e : all) {
                try {
                    String t = e.getText().replaceAll("\\s+", " ").trim();
                    String code = e.getAttribute("data-code");
                    System.out.println("data-code=" + code + " | text=" + t);
                } catch (Exception ex) {
                    // ignore individual element failures
                }
            }
            System.out.println("---- end dump ----");
        } catch (Exception e) {
            System.out.println("No suggestion elements found to dump.");
        }
    }

    private void safeSleep(long ms) {
        try { Thread.sleep(ms); } catch (InterruptedException e) { Thread.currentThread().interrupt(); }
    }

    /**
     * After clicking Search this waits for results URL and clicks first available flight Book/Select button.
     */
    //public void selectFirstAvailableFlightOnResults() {
        // wait for results page to load (URL contains '/flights/')
       // wait.until(ExpectedConditions.urlContains("/flights/"));
        // try to find common "book/select" buttons (text-based heuristics)
        //By firstBookBtn = By.xpath("(//button[contains(translate(text(),'ABCDEFGHIJKLMNOPQRSTUVWXYZ','abcdefghijklmnopqrstuvwxyz'),'book') or contains(translate(text(),'ABCDEFGHIJKLMNOPQRSTUVWXYZ','abcdefghijklmnopqrstuvwxyz'),'select') or contains(@class,'book')])[1]");
        //WebElement btn = wait.until(ExpectedConditions.elementToBeClickable(firstBookBtn));
        //clickElement(btn);
   // }
}
