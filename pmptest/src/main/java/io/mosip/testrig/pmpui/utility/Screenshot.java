package io.mosip.testrig.pmpui.utility;

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
	private static final org.slf4j.Logger logger= org.slf4j.LoggerFactory.getLogger(Screenshot.class);

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
	//	public static String Attachfile(ExtentTest test,String n) {
	//		try {
	//			String up="C:\\Users\\jayesh.kharode\\Downloads\\"+n+".csv";
	//			Workbook workbook = new Workbook(up);
	//			String path=System.getProperty("user.dir")+"/Export/"+System.currentTimeMillis()+".png";
	//			 workbook.save(path);
	//			 FileInputStream fis=new FileInputStream(path);
	//				byte[] bytes =IOUtils.toByteArray(fis);
	//				String base64img=Base64.getEncoder().encodeToString(bytes);
	//				return base64img;
	//			
	//		}catch(Exception e) {
	//			test.fail(e.getMessage());
	//		}
	//		
	//		return null;
	//		
	//	}
	//
}
