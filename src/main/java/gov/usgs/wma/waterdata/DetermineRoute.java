package gov.usgs.wma.waterdata;

import java.util.function.Function;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Component
public class DetermineRoute implements Function<RequestObject, ResultObject> {
	private static final Logger LOG = LoggerFactory.getLogger(DetermineRoute.class);

	@Value("${maxTimeSeriesCorrectedData}")
	private Long maxTimeSeriesCorrectedData;

	private JsonDataDao jsonDataDao;
	// Script Names
	public static final String GET_TS_DATA = "getTSData";
	// Web Service Endpoints
	public static final String GET_TS_DESCRIPTION_LIST = "GetTimeSeriesDescriptionListByUniqueId";
	public static final String GET_TS_CORRECTED_DATA = "GetTimeSeriesCorrectedData";
	// Router Types
	public static final String ERROR = "error";
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
		String type = ERROR;
		JsonData jsonData = null;
		try {
			jsonData = jsonDataDao.getJsonData(id);
			LOG.debug("jsonData: {}", jsonData);
		} catch (Exception e) {
			LOG.error("Issue getting json_data record.", e);
		}
		LOG.debug("here");
		if (null != jsonData
				&& 200 == jsonData.getResponseCode()
				&& GET_TS_DATA.equalsIgnoreCase(jsonData.getScriptName())) {
			switch (jsonData.getServiceName()) {
			case GET_TS_DESCRIPTION_LIST:
				type = TS_DESCRIPTION_LIST;
				break;
			case GET_TS_CORRECTED_DATA:
				if (maxTimeSeriesCorrectedData > jsonData.getContentLength()) {
					type = TS_CORRECTED_DATA;
				} else {
					type = ERROR;
				}
				break;
			default:
				type = OTHER;
				break;
			}
		} else {
			type = OTHER;
		}
		return type;
	}
}
