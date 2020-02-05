package gov.usgs.wma.waterdata;

import java.sql.SQLException;

import javax.sql.DataSource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.context.annotation.Bean;
import org.springframework.jdbc.core.JdbcTemplate;

import com.github.springtestdbunit.bean.DatabaseConfigBean;
import com.github.springtestdbunit.bean.DatabaseDataSourceConnectionFactoryBean;

@TestConfiguration
public class DBTestConfig {

	@Autowired
	public DataSource dataSource;

	@Value("${AQTS_SCHEMA_NAME}")
	private String schemaName;

	@Bean
	public JdbcTemplate jdbcTemplate() {
		return new JdbcTemplate(dataSource);
	}

	@Bean
	public DatabaseConfigBean dbUnitDatabaseConfig() {
		DatabaseConfigBean dbUnitDbConfig = new DatabaseConfigBean();
		dbUnitDbConfig.setDatatypeFactory(new TSDataTypeFactory());
		return dbUnitDbConfig;
	}

	@Bean
	public DatabaseDataSourceConnectionFactoryBean dbUnitDatabaseConnection() throws SQLException {
		DatabaseDataSourceConnectionFactoryBean dbUnitDatabaseConnection = new DatabaseDataSourceConnectionFactoryBean();
		dbUnitDatabaseConnection.setDatabaseConfig(dbUnitDatabaseConfig());
		dbUnitDatabaseConnection.setDataSource(dataSource);
		dbUnitDatabaseConnection.setSchema(schemaName);
		return dbUnitDatabaseConnection;
	}
}
