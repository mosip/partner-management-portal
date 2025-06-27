package io.mosip.testrig.pmpuiv2.dbaccess;

import java.io.BufferedReader;
import java.io.FileReader;
import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;
import org.hibernate.cfg.Environment;
import org.hibernate.jdbc.Work;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import io.mosip.testrig.pmpuiv2.kernel.util.ConfigManager;

public class DBManager {

	private static final Logger LOGGER = LoggerFactory.getLogger(DBManager.class);

	/**
	 * Execute SQL queries from a given file on the specified DB
	 */
	public static void executeDBQueries(String dbUrl, String dbUser, String dbPass, String dbSchema, String queryFilePath) {
		SessionFactory sessionFactory = null;
		Session session = null;

		try {
			// Log DB config
			LOGGER.info("🔧 Hibernate Config: hibernate.connection.driver_class = org.postgresql.Driver");
			LOGGER.info("🔧 Hibernate Config: hibernate.connection.url = {}", dbUrl);
			LOGGER.info("🔧 Hibernate Config: hibernate.dialect = org.hibernate.dialect.PostgreSQLDialect");
			LOGGER.info("🔧 Hibernate Config: hibernate.connection.pool_size = 1");
			LOGGER.info("🔧 Hibernate Config: hibernate.show_sql = true");
			LOGGER.info("🔧 Hibernate Config: hibernate.connection.username = {}", dbUser);
			LOGGER.info("🔧 Hibernate Config: hibernate.connection.password = {}", dbPass);
			LOGGER.info("🔧 Hibernate Config: hibernate.default_schema = {}", dbSchema);

			// Hibernate configuration using Environment constants
			Configuration config = new Configuration();
			config.setProperty(Environment.DRIVER, ConfigManager.getDbDriverClass());
			config.setProperty(Environment.URL, dbUrl);
			config.setProperty(Environment.USER, dbUser);
			config.setProperty(Environment.PASS, dbPass);
			config.setProperty(Environment.DIALECT, ConfigManager.getDbDialect());
			config.setProperty(Environment.CURRENT_SESSION_CONTEXT_CLASS, ConfigManager.getDbSessionContext());
			config.setProperty(Environment.SHOW_SQL, "true");
			config.setProperty(Environment.POOL_SIZE, ConfigManager.getDbConnectionPoolSize());
			config.setProperty(Environment.DEFAULT_SCHEMA, dbSchema);

			// Build session factory
			sessionFactory = config.buildSessionFactory();
			session = sessionFactory.openSession();
			LOGGER.info("✅ DB connection established.");

			// Read and filter SQL queries
			List<String> queries = new ArrayList<>();
			try (BufferedReader br = new BufferedReader(new FileReader(queryFilePath))) {
				String line;
				while ((line = br.readLine()) != null) {
					String trimmedLine = line.trim();
					if (trimmedLine.isEmpty() || trimmedLine.startsWith("#")) {
						LOGGER.debug("⏭️ Skipping line: {}", trimmedLine);
						continue;
					}
					LOGGER.debug("✅ Adding SQL query: {}", trimmedLine);
					queries.add(trimmedLine);
				}
			}

			LOGGER.info("📋 Total queries to execute: {}", queries.size());

			// Execute queries
			List<Integer> rowCounts = new ArrayList<>();
			session.doWork(new Work() {
				@Override
				public void execute(Connection connection) throws SQLException {
					try (Statement stmt = connection.createStatement()) {
						LOGGER.info("✅ Connected to DB: {}", connection.getMetaData().getURL());
						LOGGER.info("✅ Connected as user: {}", connection.getMetaData().getUserName());
						LOGGER.info("🧾 Schema: {}", dbSchema);
						LOGGER.info("🧾 DB: {}", connection.getCatalog());
						LOGGER.info("🧾 User: {}", dbUser);

						for (String query : queries) {
							LOGGER.info("🟡 Executing SQL: {}", query);
							stmt.addBatch(query);
						}
						int[] results = stmt.executeBatch();
						for (int result : results) {
							rowCounts.add(result);
						}
					}
				}
			});

			LOGGER.info("✅ DB queries executed successfully.");
			for (int count : rowCounts) {
				LOGGER.info("➡️ Rows affected: {}", count);
			}
		} catch (Exception e) {
			LOGGER.error("❌ Error:: While executing DB Queries. {}", e.getMessage(), e);
		} finally {
			if (session != null) session.close();
			if (sessionFactory != null) sessionFactory.close();
		}
	}
}
