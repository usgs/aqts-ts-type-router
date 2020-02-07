package gov.usgs.wma.waterdata;

import java.io.IOException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.Resource;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;
import org.springframework.util.FileCopyUtils;

@Component
public class JsonDataDao {

	@Autowired
	protected JdbcTemplate jdbcTemplate;

	@Value("classpath:sql/activity/readArsActivity.sql")
	private Resource sqlFile;

	public JsonData getJsonData(Long jsonDataId) throws IOException {
		String sql = new String(FileCopyUtils.copyToByteArray(sqlFile.getInputStream()));
		return jdbcTemplate.queryForObject(sql,
				new Object[] {jsonDataId},
				new JsonDataRowMapper()
			);
	}
}
