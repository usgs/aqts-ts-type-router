package gov.usgs.wma.waterdata;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.SpringBootTest.WebEnvironment;
import org.springframework.boot.test.mock.mockito.MockBean;

@SpringBootTest(webEnvironment=WebEnvironment.NONE)
public class DetermineRouteTest {

	public static final String SCRIPT_NAME = "abd";

	@MockBean
	private JsonDataDao jsonDataDao;
	private DetermineRoute determineRoute;
	private RequestObject request;

	@BeforeEach
	public void beforeEach() {
		determineRoute = new DetermineRoute(jsonDataDao);
		request = new RequestObject();
		request.setId(JsonDataDaoIT.JSON_DATA_ID_1);
		request.setPartitionNumber(JsonDataDaoIT.PARTITION_NUMBER);
	}

	@Test
	public void notFoundAtAllTest() {
		when(jsonDataDao.getJsonData(any())).thenReturn(null);
		ResultObject result = determineRoute.apply(request);
		assertNotNull(result);
		assertEquals(JsonDataDaoIT.JSON_DATA_ID_1, result.getId());
		assertEquals(DetermineRoute.OTHER, result.getType());
	}

	@Test
	public void foundGenericTest() {
		when(jsonDataDao.getJsonData(any())).thenReturn(new JsonData());
		ResultObject result = determineRoute.apply(request);
		assertNotNull(result);
		assertEquals(JsonDataDaoIT.JSON_DATA_ID_1, result.getId());
		assertEquals(DetermineRoute.OTHER, result.getType());
	}

	@Test
	public void foundWrongScriptTest() {
		JsonData jsonData = new JsonData();
		jsonData.setResponseCode(JsonDataDaoIT.RESPONSE_CODE_SUCCESS);
		jsonData.setScriptName(SCRIPT_NAME);
		when(jsonDataDao.getJsonData(any())).thenReturn(jsonData);
		ResultObject result = determineRoute.apply(request);
		assertNotNull(result);
		assertEquals(JsonDataDaoIT.JSON_DATA_ID_1, result.getId());
		assertEquals(DetermineRoute.OTHER, result.getType());
	}

	@Test
	public void foundTSDescriptionTest() {
		JsonData jsonData = new JsonData();
		jsonData.setResponseCode(JsonDataDaoIT.RESPONSE_CODE_SUCCESS);
		jsonData.setScriptName(DetermineRoute.GET_TS_DATA);
		jsonData.setServiceName(DetermineRoute.GET_TS_DESCRIPTION_LIST);
		when(jsonDataDao.getJsonData(any())).thenReturn(jsonData);
		ResultObject result = determineRoute.apply(request);
		assertNotNull(result);
		assertEquals(JsonDataDaoIT.JSON_DATA_ID_1, result.getId());
		assertEquals(DetermineRoute.TS_DESCRIPTION_LIST, result.getType());
	}

	@Test
	public void foundTSCorrectedDataTest() {
		JsonData jsonData = new JsonData();
		jsonData.setResponseCode(JsonDataDaoIT.RESPONSE_CODE_SUCCESS);
		jsonData.setScriptName(DetermineRoute.GET_TS_DATA);
		jsonData.setServiceName(DetermineRoute.GET_TS_CORRECTED_DATA);
		when(jsonDataDao.getJsonData(any())).thenReturn(jsonData);
		ResultObject result = determineRoute.apply(request);
		assertNotNull(result);
		assertEquals(JsonDataDaoIT.JSON_DATA_ID_1, result.getId());
		assertEquals(DetermineRoute.TS_CORRECTED_DATA, result.getType());
	}

	@Test
	public void foundFieldVisitDataTest() {
		JsonData jsonData = new JsonData();
		jsonData.setResponseCode(JsonDataDaoIT.RESPONSE_CODE_SUCCESS);
		jsonData.setScriptName(DetermineRoute.GET_TS_SITE_VISIT);
		jsonData.setServiceName(DetermineRoute.GET_FIELD_VISIT_DATA_BY_LOCATION);
		when(jsonDataDao.getJsonData(any())).thenReturn(jsonData);
		ResultObject result = determineRoute.apply(request);
		assertNotNull(result);
		assertEquals(JsonDataDaoIT.JSON_DATA_ID_1, result.getId());
		assertEquals(DetermineRoute.FIELD_VISIT_DATA, result.getType());
	}

	@Test
	public void foundSomethingElseTest() {
		JsonData jsonData = new JsonData();
		jsonData.setResponseCode(JsonDataDaoIT.RESPONSE_CODE_SUCCESS);
		jsonData.setScriptName(DetermineRoute.GET_TS_DATA);
		jsonData.setServiceName(SCRIPT_NAME);
		when(jsonDataDao.getJsonData(any())).thenReturn(jsonData);
		ResultObject result = determineRoute.apply(request);
		assertNotNull(result);
		assertEquals(JsonDataDaoIT.JSON_DATA_ID_1, result.getId());
		assertEquals(DetermineRoute.OTHER, result.getType());
	}
}
