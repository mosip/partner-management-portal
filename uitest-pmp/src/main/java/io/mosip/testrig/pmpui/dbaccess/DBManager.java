package io.mosip.testrig.pmpui.dbaccess;

import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Statement;



import org.apache.log4j.Logger;
import org.hibernate.HibernateException;
//import org.hibernate.HibernateException;
//import org.hibernate.Session;
//import org.hibernate.SessionFactory;
//import org.hibernate.cfg.Configuration;
//import org.hibernate.cfg.Environment;
//import org.hibernate.jdbc.Work;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;
import org.hibernate.cfg.Environment;
import org.hibernate.jdbc.Work;

import io.mosip.testrig.pmpui.kernel.util.ConfigManager;
import io.mosip.testrig.pmpui.utility.BaseClass;

public class DBManager extends BaseClass {

	private static Logger logger = Logger.getLogger(DBManager.class);

	public static void clearPMSDbData() {
		Session session = null;
		try {

			logger.info("DB URL:: " + ConfigManager.getPMSDbUrl());
			logger.info("DbUser:: " + ConfigManager.getPMSDbUser());
			logger.info("DbPass:: " + ConfigManager.getPMSDbPass());
			logger.info("DbSchema:: " + ConfigManager.getPMSDbSchema());
			session = getDataBaseConnection(ConfigManager.getPMSDbUrl(), ConfigManager.getPMSDbUser(),
					ConfigManager.getPMSDbPass(), ConfigManager.getPMSDbSchema());
			if (session != null) {
				session.doWork((Work) new Work() {

					@Override
					public void execute(Connection connection) throws SQLException {
						Statement statement = connection.createStatement();
						// To Do --- Read the delete queries from a file and iterate
						try {
							;
							statement.addBatch("delete from partner_policy_request where part_id ='auth_v4"+data+"'");
							statement.addBatch("delete from partner_policy  where part_id ='auth_v4"+data+"'");
							statement.addBatch("delete from partner  where name ='AUTH_V4'");
							statement.addBatch("delete from partner_policy_request where part_id ='credential_v1"+data+"'");
							statement.addBatch("delete from partner_policy  where part_id ='credential_v1"+data+"'");
							statement.addBatch("delete from partner  where name ='CREDENTIAL_V1'");
							int[] result = statement.executeBatch();
							logger.info("Success:: Executed PMS DB quiries successfully.");
							for (int i : result) {
								logger.info("PMS db deleted records: " + i);
							}
						} finally {
							statement.close();
						}

					}

				});
			}
		} catch (Exception e) {
			logger.error("Error:: While executing MASTER DB Quiries." + e.getMessage());
		} finally {
			if (session != null) {
				session.close();
			}
		}

	}

	private static Session getDataBaseConnection(String dburl, String userName, String password, String schema) {
		SessionFactory factory = null;
		Session session = null;
		
		logger.info("dburl is" + dburl);
		logger.info("userName is" + userName);
		logger.info("password is" + password);
		

		try {
			Configuration config = new Configuration();
			config.setProperty(Environment.DRIVER, ConfigManager.getDbDriverClass());
			config.setProperty(Environment.URL, dburl);
			logger.info("dburl is" + dburl);
			config.setProperty(Environment.USER, userName);
			logger.info("userName is" + userName);
			config.setProperty(Environment.PASS, password);
			logger.info("password is" + password);
			config.setProperty(Environment.DEFAULT_SCHEMA, schema);
			config.setProperty(Environment.POOL_SIZE, ConfigManager.getDbConnectionPoolSize());
			config.setProperty(Environment.DIALECT, ConfigManager.getDbDialect());
			config.setProperty(Environment.SHOW_SQL, ConfigManager.getShowSql());
			config.setProperty(Environment.CURRENT_SESSION_CONTEXT_CLASS, ConfigManager.getDbSessionContext());

			factory = config.buildSessionFactory();
			session = factory.getCurrentSession();
			session.beginTransaction();
		} catch (HibernateException | NullPointerException e) {
			logger.error("Error while getting the db connection for ::" + dburl);
		}
		return session;
	}

}

