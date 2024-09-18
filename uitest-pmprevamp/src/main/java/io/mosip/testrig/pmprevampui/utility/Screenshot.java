package io.mosip.testrig.pmprevampui.utility;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.Base64;

import org.apache.commons.io.IOUtils;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.io.FileHandler;



public class Screenshot  {
	public static String  ClickScreenshot(WebDriver driver) throws IOException {
		TakesScreenshot ss=(TakesScreenshot)driver;
		File so=ss.getScreenshotAs(OutputType.FILE);
		String path=null;
		path = TestRunner.getResourcePath()+ "/Screenshots/"+System.currentTimeMillis()+".png";
		File des=new File(path);
		FileHandler.copy(so, des);
		FileInputStream fis=new FileInputStream(path);
		byte[] bytes =IOUtils.toByteArray(fis);
		String base64img=Base64.getEncoder().encodeToString(bytes);
		return base64img;

	}
}
