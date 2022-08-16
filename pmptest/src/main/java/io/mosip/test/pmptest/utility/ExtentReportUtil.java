package io.mosip.test.pmptest.utility;


import java.awt.image.BufferedImage;
import java.io.File;

import javax.imageio.ImageIO;
import java.nio.file.Path;
import java.nio.file.Paths;

import java.awt.Dimension;
import java.awt.Rectangle;
import java.awt.Robot;
import java.awt.Toolkit;


import com.aventstack.extentreports.ExtentReports;
import com.aventstack.extentreports.ExtentTest;
import com.aventstack.extentreports.reporter.ExtentHtmlReporter;

public class ExtentReportUtil {
    private static final org.slf4j.Logger logger= org.slf4j.LoggerFactory.getLogger(ExtentReportUtil.class);

    public static Path REPORTPATH = Paths.get(System.getProperty("user.dir"), "report",
            "extentReport" + DateUtil.getDateTime() + ".html");

    public static ExtentHtmlReporter htmlReporter;
    public static ExtentReports reports;
    public static ExtentTest test1, test2, test3, test4, test5, test6, test7;
    public static ExtentTest step1, step2, step3, step4, step5, step6, step7, step8, step9;

    public static void ExtentSetting() {
        htmlReporter = new ExtentHtmlReporter(REPORTPATH.toString());

        reports = new ExtentReports();
        reports.attachReporter(htmlReporter);

    }
    
    public static String capture() {
        String snapshotpath = null;
        try {
            Robot rb = new Robot();
            Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();

            Rectangle rec = new Rectangle(0, 0, screenSize.width, screenSize.height);
            BufferedImage image = rb.createScreenCapture(rec);
            // Image myImage=SwingFXUtils.toFXImage(image, null);

            Path SNAPSHOTPATH = Paths.get(System.getProperty("user.dir"), "snapshot",
                    "snapshot" + DateUtil.getDateTime() + ".jpg");
            snapshotpath = SNAPSHOTPATH.toString();
            ImageIO.write(image, "jpg", new File(snapshotpath));

        } catch (Exception e) {
            logger.error("", e);

        }
        return snapshotpath;

    }

}
