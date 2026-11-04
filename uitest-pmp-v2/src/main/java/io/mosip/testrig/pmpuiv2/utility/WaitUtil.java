package io.mosip.testrig.pmpuiv2.utility;

import org.openqa.selenium.By;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.StaleElementReferenceException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebDriverException;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import io.mosip.testrig.pmpuiv2.kernel.util.ConfigManager;

import java.time.Duration;

public class WaitUtil {
	private static final int TIMEOUT = ConfigManager.getTimeout();

	public static void waitForVisibility(WebDriver driver, WebElement element) {
	    WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(TIMEOUT));
	    wait.ignoring(StaleElementReferenceException.class)
	        .until(d -> {
	            try {
	                return element.isDisplayed() ? element : null;
	            } catch (WebDriverException e) {
	                // Chrome reports a node swapped out by a React re-render as "does not belong to the document"
	                // rather than as a stale element, so keep waiting for the re-rendered one
	                if (e.getMessage() != null && e.getMessage().contains("does not belong to the document")) {
	                    return null;
	                }
	                throw e;
	            }
	        });
	}

	public static void waitForClickability(WebDriver driver, WebElement element) {
		WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(TIMEOUT));
		wait.until(ExpectedConditions.refreshed(ExpectedConditions.elementToBeClickable(element)));
	}

	public static boolean waitForInvisibility(WebDriver driver, WebElement element) {
		WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(TIMEOUT));
		return wait.until(ExpectedConditions.invisibilityOf(element));
	}
	
	public static WebElement waitForClickable(WebDriver driver, By locator) {
	    WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(TIMEOUT));
	    return wait.until(
	        ExpectedConditions.refreshed(
	            ExpectedConditions.elementToBeClickable(locator)
	        )
	    );
	}
}
