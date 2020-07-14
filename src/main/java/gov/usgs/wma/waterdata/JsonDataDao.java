package gov.usgs.wma.waterdata;

import java.io.IOException;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.Resource;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;
import org.springframework.util.FileCopyUtils;

@Component
public class JsonDataDao {
	private static final Logger LOG = LoggerFactory.getLogger(JsonDataDao.class);

	@Autowired
	private JdbcTemplate jdbcTemplate;

	@Value("classpath:sql/jsonData.sql")
	private Resource sqlFile;

	public JsonData getJsonData(RequestObject request) {
		JsonData jsonData = null;
		try {
			String sql = new String(FileCopyUtils.copyToByteArray(sqlFile.getInputStream()));
			jsonData = jdbcTemplate.queryForObject(sql,
					new JsonDataRowMapper(),
					request.getId(),
					request.getPartitionNumber()
				);
		} catch (EmptyResultDataAccessException e) {
			LOG.info("Couldn't find {} - {}", request.getId(), e.getLocalizedMessage());
		} catch (IOException e) {
			LOG.error("Unable to get SQL statement", e);
			throw new RuntimeException(e);
		}
		return jsonData;
	}
}
