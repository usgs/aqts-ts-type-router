package gov.usgs.wma.waterdata;

import java.sql.ResultSet;
import java.sql.SQLException;

import org.springframework.jdbc.core.RowMapper;

public class JsonDataRowMapper implements RowMapper<JsonData> {

	@Override
	public JsonData mapRow(ResultSet rs, int rowNum) throws SQLException {
		JsonData jsonData = new JsonData();
		jsonData.setId(rs.getLong("json_data_id"));
		jsonData.setResponseCode(rs.getInt("response_code"));
		jsonData.setServiceName(rs.getString("service_name"));
		jsonData.setScriptName(rs.getString("script_name"));
		jsonData.setContentLength(rs.getLong("content_length"));
		return jsonData;
	}

}
