package io.mosip.testrig.pmpv2ui.utility;

import org.apache.log4j.Logger;
import org.openqa.selenium.WebElement;
import org.testng.Reporter;

public class LogUtil {

    private static final Logger logger = Logger.getLogger(LogUtil.class);

    // Helper method to extract meaningful information from WebElement
    private static String describeElement(WebElement element) {
        try {
            String desc = element.toString();
            if (desc.contains("->")) {
                return desc.substring(desc.indexOf("->") + 2, desc.length() - 1).trim();
            }
            return desc;
        } catch (Exception e) {
            return "Unknown Element";
        }
    }
    
    private static void logToReporter(String message, String color) {
        Reporter.log("<br><span style='color:" + color + ";'>" + message + "</span>", false);
    }

    // Method to log section headers
    public static void section(String title) {
        logger.info("\n\n🟩==============================================");
        logger.info("🟩 SECTION: " + title.toUpperCase());
        logger.info("🟩==============================================\n");
    }

    // Method to log steps
    public static void step(String message) {
        logger.info("➡️  " + message);
    }

    // Method to log actions with WebElement description
    public static void action(String message, WebElement element) {
        String fullMessage = "🔸 ACTION: " + message + ": " + describeElement(element);
        logger.info(fullMessage);
        Reporter.log("<br><span style='color:blue;'>" + fullMessage + "</span>", false);
    }
    
    public static void action(String message) {
    	String formatted = "🔸 ACTION: " + message;
        logger.info(formatted);
        logToReporter(formatted, "blue");
    }


    // Method to log verification with WebElement description
    public static void verify(String message, WebElement element) {
        String fullMessage = "🔍 VERIFY: " + message + ": " + describeElement(element);
        logger.info(fullMessage);
        Reporter.log("<br><span style='color:darkorange;'>" + fullMessage + "</span>", false);
    }

    // Method to log input
    public static void input(String message) {
        logger.info("✏️  INPUT: " + message);
    }

    // Method to log success
    public static void success(String message) {
        logger.info("✅ SUCCESS: " + message);
    }

    // Method to log error
    public static void error(String message) {
        logger.error("❌ ERROR: " + message);
    }

    // Method to log the end of a section
    public static void endSection() {
        logger.info("🟩============== END OF SECTION ==============\n");
    }
}