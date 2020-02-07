package gov.usgs.wma.waterdata;

import java.io.IOException;
import java.util.function.Function;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class DetermineRoute implements Function<RequestObject, ResultObject> {

	private JsonDataDao jsonDataDao;
	// Script Names
	public static final String GET_TS_DATA = "getTSData";
	// Web Service Endpoints
	public static final String GET_TS_DESCRIPTION_LIST = "GetTimeSeriesDescriptionListByUniqueId";
	public static final String GET_TS_CORRECTED_DATA = "GetTimeSeriesCorrectedData";
	// Router Types
	public static final String OTHER = "other";
	public static final String TS_DESCRIPTION_LIST = "tsDescriptionList";
	public static final String TS_CORRECTED_DATA = "tsCorrectedData";

	@Autowired
	public DetermineRoute(JsonDataDao jsonDataDao) {
		this.jsonDataDao = jsonDataDao;
	}

	@Override
	public ResultObject apply(RequestObject request) {
		return processRequest(request);
	}

	protected ResultObject processRequest(RequestObject request) {
		ResultObject result = new ResultObject();
		result.setId(request.getId());
		result.setType(determineType(request.getId()));
		return result;
	}

	protected String determineType(Long id) {
		String type = OTHER;
		JsonData jsonData = null;
		try {
			jsonData = jsonDataDao.getJsonData(id);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		if (null != jsonData
				&& 200 == jsonData.getResponseCode()
				&& GET_TS_DATA.equalsIgnoreCase(jsonData.getScriptName())) {
			switch (jsonData.getServiceName()) {
			case GET_TS_DESCRIPTION_LIST:
				type = TS_DESCRIPTION_LIST;
				break;
			case GET_TS_CORRECTED_DATA:
				type = TS_CORRECTED_DATA;
				break;
			default:
				type = OTHER;
				break;
			}
		}
		return type;
	}
}
