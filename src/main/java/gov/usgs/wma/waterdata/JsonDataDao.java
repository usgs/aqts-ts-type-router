package gov.usgs.wma.waterdata;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;

@Component
public class JsonDataDao {
	@Autowired
	protected JdbcTemplate jdbcTemplate;

	public JsonData getJsonData(Long jsonDataId) {
		String sql = "select json_data_id, response_code, (regexp_match(url, 'V2/(.*)\\?'))[1] service_name, script_name"
				+ "  from json_data"
				+ " where json_data_id = ?";
		return jdbcTemplate.queryForObject(sql,
				new Object[] {jsonDataId},
				new JsonDataRowMapper()
			);
	}
}
