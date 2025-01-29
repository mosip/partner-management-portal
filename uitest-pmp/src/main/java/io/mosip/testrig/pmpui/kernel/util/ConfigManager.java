package io.mosip.testrig.pmpui.kernel.util;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.Arrays;
import java.util.List;
import java.util.Properties;

import org.apache.log4j.Logger;

import io.mosip.testrig.pmpui.utility.Commons;
import io.mosip.testrig.pmpui.utility.TestRunner;
public class ConfigManager {

	private static final org.slf4j.Logger logger= org.slf4j.LoggerFactory.getLogger(ConfigManager.class);

	private static String IAM_ADMINPORTAL_PATH = "PmpPortalPath";

	private static String IAM_APIENVUSER = "apiEnvUser";
	private static String IAM_APIINTERNALENDPOINT = "apiInternalEndPoint";
	private static String MOSIP_IDREPO_CLIENT_SECRET = "mosip_idrepo_client_secret";
	private static String MOSIP_IDREPO_CLIENT_ID = "mosip_idrepo_client_id";
	private static String MOSIP_IDREPO_APP_ID = "mosip_idrepo_app_id";

	private static String MOSIP_ADMIN_CLIENT_SECRET = "mosip_admin_client_secret";
	private static String MOSIP_ADMIN_CLIENT_ID = "mosip_admin_client_id";
	private static String MOSIP_ADMIN_APP_ID = "mosip_admin_app_id";

	private static String MOSIP_AUTOMATION_CLIENT_SECRET = "mosip_testrig_client_secret";
	private static String MOSIP_AUTOMATION_CLIENT_ID = "mosip_testrig_client_id";
	
	private static String S3_HOST = "s3-host";
	private static String S3_REGION = "s3-region";
	private static String S3_USER_KEY = "s3-user-key";
	private static String S3_SECRET_KEY = "s3-user-secret";
	private static String S3_ACCOUNT = "s3-account";
	private static String PUSH_TO_S3 = "push-reports-to-s3";
	
	private static String DB_PORT = "db-port";
	private static String DB_DOMAIN = "db-server";
	private static String HIBERNATE_CONNECTION_DRIVER_CLASS = "hibernate.connection.driver_class";
	private static String HIBERNATE_CONNECTION_POOL_SIZE = "hibernate.connection.pool_size";
	private static String HIBERNATE_DIALECT = "hibernate.dialect";
	private static String HIBERNATE_SHOW_SQL = "hibernate.show_sql";
	private static String HIBERNATE_CONTEXT_CLASS = "hibernate.current_session_context_class";
	
	private static String PMS_DB_USER = "db-su-user";
	private static String PMS_DB_PASS = "postgres-password";
	private static String PMS_DB_SCHEMA = "pms_db_schema";
	
	private static String IAM_REALM_ID = "keycloak-realm-id";
	private static String IAM_USERS_TO_CREATE = "iam-users-to-create";
	private static String IAM_USERS_PASSWORD = "iam-users-password";
	
	private static String REPORT_EXPIRATION_IN_DAYS = "reportExpirationInDays";
	private static String SERVICES_NOT_DEPLOYED = "servicesNotDeployed";

	private static String HolidayDateCenter = "holidayDateCenter";
	private static String HolidayDate = "holidayDate";
	private static String Preappend = "preappend";
	private static String Splitdigit = "splitdigit";
	private static String JsonObjName = "jsonObjName";
	private static String Bulkwait = "bulkwait";
	private static String SbivalidDate = "sbivalidDate";
	private static String PublicKey = "publicKey";
	private static String SignPublicKey = "signPublicKey";
	private static String Headless = "headless";
	private static String Docker = "docker";
	private static String Langcode = "langcode";
	private static String SbiexpiryDate = "sbiexpiryDate";//loginlang
	private static String Loginlang = "loginlang";
	private static String PolicyData = "policyData";//loginlang
	private static String DataSharepolicyData = "dataSharepolicyData";
	private static String Testcases = "pmpscenariosToExecute";

	private static String testcases;
	private static String loginlang;
	private static String langcode;
	private static String docker;
	private static String headless;
	private static String signPublicKey;
	private static String publicKey;
	private static String sbivalidDate;
	private static String bulkwait;
	private static String sbiexpiryDate;
	private static String jsonObjName;
	private static String splitdigit;
	private static String preappend;
	private static String holidayDate;
	private static String holidayDateCenter;
	private static String policyData;
	private static String dataSharepolicyData;

	private static String idrepo_client_secret;
	private static String idrepo_client_id;
	private static String idrepo_app_id;
	//

	private static String IAM_EXTERNAL_URL = "keycloak-external-url";
	private static String admin_client_secret;
	private static String admin_client_id;
	private static String admin_app_id;

	private static String automation_client_secret;
	private static String automation_client_id;

	private static String s3_region;
	private static String s3_host;
	private static String s3_user_key;
	private static String s3_account;
	private static String s3_secret_key;
	private static String push_reports_to_s3;

	private static String serviceNotDeployedList;
	private static String enableDebug;
	private static String db_port;
	private static String db_domain;
	private static String hibernate_connection_driver_class;
	private static String hibernate_connection_pool_size;
	private static String hibernate_dialect;
	private static String hibernate_show_sql;
	private static String hibernate_current_session_context_class;

	private static String pms_db_user;
	private static String pms_db_pass;
	private static String pms_db_schema;

	private static String iamExternalURL;	
	private static String iam_realm_id;
	private static String iam_users_to_create;
	private static String iam_users_password;
	private static String iam_adminportal_path;
	private static String iam_apienvuser;
	private static String iam_apiinternalendpoint;

	public static Properties propsKernel;
	private static String reportExpirationInDays;

	public static void setProperty(String key, String value) {
		// Overwrite the value with only if the key exists
		if (propsKernel.containsKey(key)) {
			propsKernel.setProperty(key, value);
		}
	}
	
	public static String getValueForKey(String key) {
		String value = System.getProperty(key) == null ? propsKernel.getProperty(key) : System.getProperty(key);
		setProperty(key, value);

		return value;
	}

	public static void init() {
		// Loading Kernel property

		logger.info("file location for kernal"+TestRunner.getResourcePath() + "/config/"+TestRunner.GetKernalFilename());

		propsKernel = getproperty(TestRunner.getResourcePath() + "/config/"+TestRunner.GetKernalFilename());



		iamExternalURL =System.getenv(IAM_EXTERNAL_URL) == null
				? propsKernel.getProperty(IAM_EXTERNAL_URL)
						: System.getenv(IAM_EXTERNAL_URL);


		logger.info("System.getenv(IAM_EXTERNAL_URL)=" + System.getenv(IAM_EXTERNAL_URL));
		logger.info("iamExternalURL from getIAMUrl() =" + getIAMUrl());

		iam_adminportal_path =System.getenv(IAM_ADMINPORTAL_PATH) == null
				? propsKernel.getProperty(IAM_ADMINPORTAL_PATH)
						: System.getenv(IAM_ADMINPORTAL_PATH);
		logger.info("adminportal_path from config manager::" + iam_adminportal_path);
		iam_apienvuser = System.getenv(IAM_APIENVUSER) == null
				? propsKernel.getProperty(IAM_APIENVUSER)
						: System.getenv(IAM_APIENVUSER);
		logger.info("apienvuser from config manager::" + iam_apienvuser);
		iam_apiinternalendpoint = System.getenv(IAM_APIINTERNALENDPOINT) == null
				? propsKernel.getProperty(IAM_APIINTERNALENDPOINT)
						: System.getenv(IAM_APIINTERNALENDPOINT);
		logger.info("apiinternalendpoint from config manager::" + iam_apiinternalendpoint);
		idrepo_client_secret = getValueForKey(MOSIP_IDREPO_CLIENT_SECRET);
		idrepo_client_id = getValueForKey(MOSIP_IDREPO_CLIENT_ID);
		idrepo_app_id = getValueForKey(MOSIP_IDREPO_APP_ID);
		admin_client_secret = getValueForKey(MOSIP_ADMIN_CLIENT_SECRET);
		admin_client_id = getValueForKey(MOSIP_ADMIN_CLIENT_ID);
		admin_app_id = getValueForKey(MOSIP_ADMIN_APP_ID);

		automation_client_secret = getValueForKey(MOSIP_AUTOMATION_CLIENT_SECRET);
		automation_client_id = getValueForKey(MOSIP_AUTOMATION_CLIENT_ID);

		s3_host = getValueForKey(S3_HOST);
		s3_region = getValueForKey(S3_REGION);
		s3_user_key = getValueForKey(S3_USER_KEY);
		s3_secret_key = getValueForKey(S3_SECRET_KEY);
		s3_account = getValueForKey(S3_ACCOUNT);
		push_reports_to_s3 = getValueForKey(PUSH_TO_S3);
		db_port = getValueForKey(DB_PORT);
		db_domain = getValueForKey(DB_DOMAIN);
		hibernate_connection_driver_class = getValueForKey(HIBERNATE_CONNECTION_DRIVER_CLASS);
		hibernate_connection_pool_size = getValueForKey(HIBERNATE_CONNECTION_POOL_SIZE);
		hibernate_dialect = getValueForKey(HIBERNATE_DIALECT);
		hibernate_show_sql = getValueForKey(HIBERNATE_SHOW_SQL);
		hibernate_current_session_context_class = getValueForKey(HIBERNATE_CONTEXT_CLASS);

				pms_db_user = getValueForKey(PMS_DB_USER);
				pms_db_pass = getValueForKey(PMS_DB_PASS);
				pms_db_schema = getValueForKey(PMS_DB_SCHEMA);

		serviceNotDeployedList = System.getenv(SERVICES_NOT_DEPLOYED) == null
				? propsKernel.getProperty(SERVICES_NOT_DEPLOYED)
						: System.getenv(SERVICES_NOT_DEPLOYED);
		propsKernel.setProperty(SERVICES_NOT_DEPLOYED, serviceNotDeployedList);

		iam_adminportal_path =System.getenv(IAM_ADMINPORTAL_PATH) == null
				? propsKernel.getProperty(IAM_ADMINPORTAL_PATH)
						: System.getenv(IAM_ADMINPORTAL_PATH);

		logger.info("adminportal_path from config manager::" + iam_adminportal_path);
		iam_apienvuser = System.getenv(IAM_APIENVUSER) == null
				? propsKernel.getProperty(IAM_APIENVUSER)
						: System.getenv(IAM_APIENVUSER);
		logger.info("apienvuser from config manager::" + iam_apienvuser);

		iam_apiinternalendpoint = System.getenv(IAM_APIINTERNALENDPOINT) == null
				? propsKernel.getProperty(IAM_APIINTERNALENDPOINT)
						: System.getenv(IAM_APIINTERNALENDPOINT);
		logger.info("apiinternalendpoint from config manager::" + iam_apiinternalendpoint);
		//
		iam_realm_id = getValueForKey(IAM_REALM_ID);
		iam_users_to_create = getValueForKey(IAM_USERS_TO_CREATE);
		iam_users_password = getValueForKey(IAM_USERS_PASSWORD);
		//
		admin_client_secret = System.getenv(MOSIP_ADMIN_CLIENT_SECRET) == null
				? propsKernel.getProperty(MOSIP_ADMIN_CLIENT_SECRET)
						: System.getenv(MOSIP_ADMIN_CLIENT_SECRET);
		
		logger.info("admin_client_secret="+admin_client_secret);
		propsKernel.setProperty(MOSIP_ADMIN_CLIENT_SECRET, admin_client_secret);

		reportExpirationInDays = System.getenv(REPORT_EXPIRATION_IN_DAYS) == null
				? propsKernel.getProperty(REPORT_EXPIRATION_IN_DAYS)
						: System.getenv(REPORT_EXPIRATION_IN_DAYS);
		propsKernel.setProperty(REPORT_EXPIRATION_IN_DAYS, reportExpirationInDays);		

			
		push_reports_to_s3 =System.getenv(PUSH_TO_S3) == null ? propsKernel.getProperty(PUSH_TO_S3) : System.getenv(PUSH_TO_S3);
		propsKernel.setProperty(PUSH_TO_S3, push_reports_to_s3);

		holidayDateCenter =System.getenv(HolidayDateCenter) == null ? propsKernel.getProperty(HolidayDateCenter) : System.getenv(HolidayDateCenter);
		propsKernel.setProperty(HolidayDateCenter, holidayDateCenter);

		holidayDate =System.getenv(HolidayDate) == null ? propsKernel.getProperty(HolidayDate) : System.getenv(HolidayDate);
		propsKernel.setProperty(HolidayDate, holidayDate);

		preappend =System.getenv(Preappend) == null ? propsKernel.getProperty(Preappend) : System.getenv(Preappend);
		propsKernel.setProperty(Preappend, preappend);

		splitdigit =System.getenv(Splitdigit) == null ? propsKernel.getProperty(Splitdigit) : System.getenv(Splitdigit);
		propsKernel.setProperty(Splitdigit, splitdigit);

		jsonObjName =System.getenv(JsonObjName) == null ? propsKernel.getProperty(JsonObjName) : System.getenv(JsonObjName);
		propsKernel.setProperty(JsonObjName, jsonObjName);

		bulkwait =System.getenv(Bulkwait) == null ? propsKernel.getProperty(Bulkwait) : System.getenv(Bulkwait);
		propsKernel.setProperty(Bulkwait, bulkwait);



		sbivalidDate =System.getenv(SbivalidDate) == null ? propsKernel.getProperty(SbivalidDate) : System.getenv(SbivalidDate);
		propsKernel.setProperty(SbivalidDate, sbivalidDate);

		sbiexpiryDate =System.getenv(SbiexpiryDate) == null ? propsKernel.getProperty(SbiexpiryDate) : System.getenv(SbiexpiryDate);
		propsKernel.setProperty(SbiexpiryDate, sbiexpiryDate);

		publicKey =System.getenv(PublicKey) == null ? propsKernel.getProperty(PublicKey) : System.getenv(PublicKey);
		propsKernel.setProperty(PublicKey, publicKey);

		signPublicKey =System.getenv(SignPublicKey) == null ? propsKernel.getProperty(SignPublicKey) : System.getenv(SignPublicKey);
		propsKernel.setProperty(SignPublicKey, signPublicKey);

		headless =System.getenv(Headless) == null ? propsKernel.getProperty(Headless) : System.getenv(Headless);
		propsKernel.setProperty(Headless, headless);

		docker =System.getenv(Docker) == null ? propsKernel.getProperty(Docker) : System.getenv(Docker);
		propsKernel.setProperty(Docker, docker);

		langcode =System.getenv(Langcode) == null ? propsKernel.getProperty(Langcode) : System.getenv(Langcode);
		propsKernel.setProperty(Langcode, langcode);

		loginlang =System.getenv(Loginlang) == null ? propsKernel.getProperty(Loginlang) : System.getenv(Loginlang);
		propsKernel.setProperty(Loginlang, loginlang);

		policyData =System.getenv(PolicyData) == null ? propsKernel.getProperty(PolicyData) : System.getenv(PolicyData);
		propsKernel.setProperty(PolicyData, policyData);

		dataSharepolicyData =System.getenv(DataSharepolicyData) == null ? propsKernel.getProperty(DataSharepolicyData) : System.getenv(DataSharepolicyData);
		propsKernel.setProperty(DataSharepolicyData, dataSharepolicyData);

		testcases =System.getenv(Testcases) == null ? propsKernel.getProperty(Testcases) : System.getenv(Testcases);
		propsKernel.setProperty(Testcases, testcases);

	}
	public static String gettestcases() {
		return testcases;
	}
	public static String getpolicyData() {
		return policyData;
	}
	public static String getdataSharepolicyData() {
		return dataSharepolicyData;
	}
	public static String getloginlang() {
		return loginlang;
	}
	public static String getlangcode() {
		return langcode;
	}
	public static String getdocker() {
		return docker;
	}
	public static String getheadless() {
		return headless;
	}
	public static String getsignPublicKey() {
		return signPublicKey;
	}
	public static String getpublicKey() {
		return publicKey;
	}
	public static String getsbivalidDate() {
		return sbivalidDate;
	}
	public static String getsbiexpiryDate() {
		return sbiexpiryDate;
	}

	public static String getbulkwait() {
		return bulkwait;
	}
	public static String getjsonObjName() {
		return jsonObjName;
	}
	public static String getsplitdigit() {
		return splitdigit;
	}
	public static String getpreappend() {
		return preappend;
	}
	public static String getholidayDate() {
		return holidayDate;
	}
	public static String getholidayDateCenter() {
		return holidayDateCenter;
	}

	public static String getAdminClientSecret() {
		return admin_client_secret;
	}
	
	public static String getAdminClientId() {
		return admin_client_id;
	}
	
	public static String getAdminAppId() {
		return admin_app_id;
	}
	
	public static String getIdRepoClientSecret() {
		return idrepo_client_secret;
	}
	
	public static String getidRepoClientId() {
		return idrepo_client_id;
	}
	
	public static String getidRepoAppId() {
		return idrepo_app_id;
	}
	public static String getiam_adminportal_path() {
		return iam_adminportal_path;
	}
	public static String getiam_apienvuser() {
		return iam_apienvuser;
	}
	public static String getiam_apiinternalendpoint() {
		return iam_apiinternalendpoint;
	}

	public static String getIAMUrl() {
		return iamExternalURL+"/auth";

	}
	public static String getAutomationClientSecret() {
		return automation_client_secret;
	}

	public static String getAutomationClientId() {
		return automation_client_id;
	}
	
	public static Boolean IsDebugEnabled() {
		return enableDebug.equalsIgnoreCase("yes");
	}
	
	public static String getS3Host() {
		return s3_host;
	}
	public static String getReportExpirationInDays() {
		return reportExpirationInDays;
	}
	
	public static String getS3Region() {
		return s3_region;
	}
	
	public static String getS3UserKey() {
		return s3_user_key;
	}
	
	public static String getS3SecretKey() {
		return s3_secret_key;
	}
	
	public static String getS3Account() {
		return s3_account;
	}
	
	public static String getPushReportsToS3() {
		return push_reports_to_s3;
	}
	
	public static String getIdaDbUrl() {
		return "jdbc:postgresql://" + db_domain + ":" + db_port + "/mosip_ida";
	}

	public static String getDbDriverClass() {
		return hibernate_connection_driver_class;
	}
	
	public static String getDbConnectionPoolSize() {
		return hibernate_connection_pool_size;
	}
	
	public static String getDbDialect() {
		return hibernate_dialect;
	}
	
	public static String getShowSql() {
		return hibernate_show_sql;
	}
	
	public static String getDbSessionContext() {
		return hibernate_current_session_context_class;
	}

		public static String getPMSDbUrl() {
			return "jdbc:postgresql://" + db_domain + ":" + db_port + "/mosip_pms";
		}

		public static String getPMSDbUser() {
			return pms_db_user;
		}
	
		public static String getPMSDbPass() {
			return pms_db_pass;
		}
	
		public static String getPMSDbSchema() {
			return pms_db_schema;
		}

	public static String getIAMRealmId() {
		return iam_realm_id;
	}


	public static String getIAMUsersToCreate() {
		return iam_users_to_create;
	}
	//
	public static String getIAMUsersPassword() {
		return iam_users_password;
	}

	public static String getRolesForUser(String userId) {
		propsKernel = getproperty(TestRunner.getResourcePath() + "/" + "config/"+TestRunner.GetKernalFilename());
		return propsKernel.getProperty("roles");
	}

	public static boolean isInServiceNotDeployedList(String stringToFind) {
		synchronized (serviceNotDeployedList) {
			if (serviceNotDeployedList.isBlank())
				return false;
			List<String> serviceNotDeployed = Arrays.asList(serviceNotDeployedList.split(","));
			if (ConfigManager.IsDebugEnabled())
				logger.info("serviceNotDeployedList:  " + serviceNotDeployedList + ", serviceNotDeployed : " + serviceNotDeployed
						+ ", stringToFind : " + stringToFind);
			for (String string : serviceNotDeployed) {
				if (string.equalsIgnoreCase(stringToFind))
					return true;
				else if(stringToFind.toLowerCase().contains(string.toLowerCase())) {
					return true;
				}
			}
		}
		return false;
	}

	private static Properties getproperty(String path) {
		Properties prop = new Properties();
		try {
			File file = new File(path);
			prop.load(new FileInputStream(file));
		} catch (IOException e) {
			logger.error("Exception " + e.getMessage());
		}
		return prop;
	}

}