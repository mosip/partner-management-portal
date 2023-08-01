package io.mosip.testrig.pmpui.utility;

import com.aventstack.extentreports.ExtentReports;
import com.aventstack.extentreports.ExtentTest;
import com.aventstack.extentreports.reporter.ExtentSparkReporter;
import com.aventstack.extentreports.ExtentReports;
import com.aventstack.extentreports.ExtentTest;

public class ExtentReportManager {
	public static ExtentSparkReporter html;
	public static String Filepath;
	public static	ExtentReports extent;
	//public static WebDriver driver;
	public static	ExtentTest test;
	public static ExtentReports getReports() {
		if(extent==null) {
			extent=new ExtentReports();
			extent=new ExtentReports();
			Filepath=System.getProperty("user.dir")+"/Reports/"+"pmp-ui-report-"+Commons.appendDate+".html";
			html=new ExtentSparkReporter(Filepath);
			  extent.attachReporter(html);
		}
		
		return extent;
		
	}
}
