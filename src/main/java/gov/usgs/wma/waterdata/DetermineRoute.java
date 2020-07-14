package gov.usgs.wma.waterdata;

import java.util.function.Function;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class DetermineRoute implements Function<RequestObject, ResultObject> {
	private static final Logger LOG = LoggerFactory.getLogger(DetermineRoute.class);

	private JsonDataDao jsonDataDao;
	// Script Names
	public static final String GET_TS_DATA = "getTSData";
	public static final String GET_TS_DESCRIPTIONS = "getTSDescriptions";
	public static final String GET_TS_SITE_VISIT = "getTSSiteVisit";
	// Web Service Endpoints
	public static final String GET_TS_DESCRIPTION_LIST = "GetTimeSeriesDescriptionListByUniqueId";
	public static final String GET_TS_CORRECTED_DATA = "GetTimeSeriesCorrectedData";
	public static final String GET_FIELD_VISIT_DATA_BY_LOCATION = "GetFieldVisitDataByLocation";
	// Router Types
	public static final String OTHER = "other";
	public static final String TS_DESCRIPTION_LIST = "tsDescriptionList";
	public static final String TS_CORRECTED_DATA = "tsCorrectedData";
	public static final String FIELD_VISIT_DATA = "fieldVisitData";

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
		result.setType(determineType(request));
		return result;
	}

	protected String determineType(RequestObject request) {
		String type = OTHER;
		JsonData jsonData = jsonDataDao.getJsonData(request);
		LOG.debug("jsonData: {}", jsonData);
		if (null != jsonData
				&& 200 == jsonData.getResponseCode()
				&& (GET_TS_DATA.equalsIgnoreCase(jsonData.getScriptName())
						|| GET_TS_DESCRIPTIONS.equalsIgnoreCase(jsonData.getScriptName())
						|| GET_TS_SITE_VISIT.equalsIgnoreCase(jsonData.getScriptName()))) {
			switch (jsonData.getServiceName()) {
			case GET_TS_DESCRIPTION_LIST:
				type = TS_DESCRIPTION_LIST;
				break;
			case GET_TS_CORRECTED_DATA:
				type = TS_CORRECTED_DATA;
				break;
			case GET_FIELD_VISIT_DATA_BY_LOCATION:
				type = FIELD_VISIT_DATA;
				break;
			default:
				type = OTHER;
				break;
			}
		}
		return type;
	}
}
