package io.mosip.testrig.pmpui.utility;

import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.security.CodeSource;
import java.util.zip.ZipEntry;
import java.util.zip.ZipInputStream;

import org.apache.commons.io.FileUtils;
import org.apache.log4j.Logger;

public class ExtractResource {
	
	private static final Logger LOGGER = Logger.getLogger(ExtractResource.class);
	public static void extractResourceFromJar() {
		getListOfFilesFromJarAndCopyToExternalResource("auth_cert/");
		getListOfFilesFromJarAndCopyToExternalResource("ca_cert/");
		getListOfFilesFromJarAndCopyToExternalResource("config/");
		getListOfFilesFromJarAndCopyToExternalResource("device_sbi_cert/");
		getListOfFilesFromJarAndCopyToExternalResource("docs/");
		getListOfFilesFromJarAndCopyToExternalResource("extent_reports/");
		getListOfFilesFromJarAndCopyToExternalResource("ftm_cert/");
		getListOfFilesFromJarAndCopyToExternalResource("logs/");
    	getListOfFilesFromJarAndCopyToExternalResource("screenshots/");
    	getListOfFilesFromJarAndCopyToExternalResource("logback.xml");
		getListOfFilesFromJarAndCopyToExternalResource("TestData.json");
		
//		getListOfFilesFromJarAndCopyToExternalResource("customize-emailable-report-template.html");
//		getListOfFilesFromJarAndCopyToExternalResource("testngapi.xml");
//		getListOfFilesFromJarAndCopyToExternalResource("metadata.xml");
//		getListOfFilesFromJarAndCopyToExternalResource("log4j.properties");
//		getListOfFilesFromJarAndCopyToExternalResource("healthCheck/");
//		getListOfFilesFromJarAndCopyToExternalResource("spring.properties");
//		getListOfFilesFromJarAndCopyToExternalResource("validations.properties");
//		getListOfFilesFromJarAndCopyToExternalResource("dbFiles/");
//		getListOfFilesFromJarAndCopyToExternalResource("mobileId/");
//		getListOfFilesFromJarAndCopyToExternalResource("esignet/");
	}
	
	public static void getListOfFilesFromJarAndCopyToExternalResource(String key) {
		ZipInputStream zipInputStream = null;
		try {
			CodeSource src = TestRunner.class.getProtectionDomain().getCodeSource();
			if (src != null) {
				URL jar = src.getLocation();
				zipInputStream = new ZipInputStream(jar.openStream());
				while (true) {
					ZipEntry e = zipInputStream.getNextEntry();
					if (e == null)
						break;
					String name = e.getName();
					if (name.startsWith(key) && name.contains(".")) {
						if (copyFilesFromJarToOutsideResource(name))
							LOGGER.info("Copied the file: " + name + " to external resource successfully..!");
						else
							LOGGER.error("Fail to copy file: " + name + " to external resource");
					}
				}
			} else {
				LOGGER.error("Something went wrong with jar location");
			}
		} catch (Exception e) {
			LOGGER.error("Exception occured in extracting resource: " + e.getMessage());
		} finally {
			closeZipInputStream(zipInputStream);
		}
	}
	
	public static void closeZipInputStream(ZipInputStream zipInputStream) {
		if (zipInputStream != null) {
	        try {
	        	zipInputStream.close();
	        } catch (IOException e) {
	        }
	    }
	}
	
	/**
	 * The method to copy resource from jar to outside jar
	 * 
	 * @param path
	 * @return
	 */
	private static boolean copyFilesFromJarToOutsideResource(String path) {
		try {
			File resourceFile = new File(TestRunner.jarUrl).getParentFile();
			File destinationFile = new File(resourceFile.getAbsolutePath() + "/resources/" + path);
			LOGGER.info("resourceFile " + TestRunner.jarUrl);
			LOGGER.info("destinationFile " + resourceFile.getAbsolutePath() + "/resources/" + path);
			org.apache.commons.io.FileUtils.copyInputStreamToFile(TestRunner.class.getResourceAsStream("/" + path),
					destinationFile);
			return true;
		} catch (Exception e) {
			LOGGER.error(
					"Exception Occured in copying the resource from jar. Kindly build new jar to perform smooth test execution: "
							+ e.getMessage());
			return false;
		}
	}	
	
	/**
	 * The method to remove old generated mosip test resource
	 */
	public static void removeOldMosipTestTestResource() {
		File mosipTestFile = new File(TestRunner.getGlobalResourcePath());
		if (mosipTestFile.exists()) {
			if (deleteDirectory(mosipTestFile))
				LOGGER.info("Old MosipTestResource folder successfully deleted!!");
			else
				LOGGER.error("Old MosipTestResource folder not deleted.");

		} else {
			LOGGER.error("Old MosipTestResource folder not exist.");
		}
	}
	
	/**
	 * The method to delete directory and its all file inside the directory
	 * 
	 * @param dir
	 * @return boolean 
	 */
	public static boolean deleteDirectory(File dir) {
        if (dir.isDirectory()) {
            File[] children = dir.listFiles();
            for (int i = 0; i < children.length; i++) {
                boolean success = deleteDirectory(children[i]);
                if (!success) {
                    return false;
                }
            }
        }
        return dir.delete();
	}

}
