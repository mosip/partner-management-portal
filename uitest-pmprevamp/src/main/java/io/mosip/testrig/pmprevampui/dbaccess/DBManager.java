package io.mosip.testrig.pmprevampui.dbaccess;

import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Statement;



import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;
import org.hibernate.cfg.Environment;
import org.hibernate.jdbc.Work;

import io.mosip.testrig.pmprevampui.kernel.util.ConfigManager;
import io.mosip.testrig.pmprevampui.utility.BaseClass;

public class DBManager extends BaseClass {

	private static final org.slf4j.Logger DBManager_LOGGER= org.slf4j.LoggerFactory.getLogger(DBManager.class);

	public static void clearPMSDbData() {
		Session session = null;
		try {

			DBManager_LOGGER.info("DB URL:: " + ConfigManager.getPMSDbUrl());
			DBManager_LOGGER.info("DbUser:: " + ConfigManager.getPMSDbUser());
			DBManager_LOGGER.info("DbPass:: " + ConfigManager.getPMSDbPass());
			DBManager_LOGGER.info("DbSchema:: " + ConfigManager.getPMSDbSchema());
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
							DBManager_LOGGER.info("Success:: Executed PMS DB quiries successfully.");
							for (int i : result) {
								DBManager_LOGGER.info("PMS db deleted records: " + i);
							}
						} finally {
							statement.close();
						}

					}

				});
			}
		} catch (Exception e) {
			DBManager_LOGGER.error("Error:: While executing MASTER DB Quiries." + e.getMessage());
		} finally {
			if (session != null) {
				session.close();
			}
		}

	}

	private static Session getDataBaseConnection(String dburl, String userName, String password, String schema) {
		SessionFactory factory = null;
		Session session = null;
		
		DBManager_LOGGER.info("dburl is" + dburl);
		DBManager_LOGGER.info("userName is" + userName);
		DBManager_LOGGER.info("password is" + password);
		

		try {
			Configuration config = new Configuration();
			config.setProperty(Environment.DRIVER, ConfigManager.getDbDriverClass());
			config.setProperty(Environment.URL, dburl);
			DBManager_LOGGER.info("dburl is" + dburl);
			config.setProperty(Environment.USER, userName);
			DBManager_LOGGER.info("userName is" + userName);
			config.setProperty(Environment.PASS, password);
			DBManager_LOGGER.info("password is" + password);
			config.setProperty(Environment.DEFAULT_SCHEMA, schema);
			config.setProperty(Environment.POOL_SIZE, ConfigManager.getDbConnectionPoolSize());
			config.setProperty(Environment.DIALECT, ConfigManager.getDbDialect());
			config.setProperty(Environment.SHOW_SQL, ConfigManager.getShowSql());
			config.setProperty(Environment.CURRENT_SESSION_CONTEXT_CLASS, ConfigManager.getDbSessionContext());

			factory = config.buildSessionFactory();
			session = factory.getCurrentSession();
			session.beginTransaction();
		} catch (HibernateException | NullPointerException e) {
			DBManager_LOGGER.error("Error while getting the db connection for ::" + dburl);
		}
		return session;
	}

}

