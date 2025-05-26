package io.mosip.testrig.pmpv2ui.utility;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import io.mosip.testrig.pmpv2ui.kernel.util.ConfigManager;

import java.time.Duration;

public class WaitUtil {
    private static final int TIMEOUT = ConfigManager.getTimeout();

    public static void waitForVisibility(WebDriver driver, WebElement element) {
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(TIMEOUT));
        wait.until(ExpectedConditions.visibilityOf(element));
    }

    public static void waitForClickability(WebDriver driver, WebElement element) {
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(TIMEOUT));
        wait.until(ExpectedConditions.elementToBeClickable(element));
    }

    public static boolean waitForInvisibility(WebDriver driver, WebElement element) {
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(TIMEOUT));
        return wait.until(ExpectedConditions.invisibilityOf(element));
    }
}
