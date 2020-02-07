package gov.usgs.wma.waterdata;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.when;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.SpringBootTest.WebEnvironment;
import org.springframework.boot.test.mock.mockito.MockBean;

@SpringBootTest(webEnvironment=WebEnvironment.NONE)//,
//	classes={DBTestConfig.class, DetermineRoute.class, JsonDataDao.class})
public class DetermineRouteTest {

//	@Autowired
	@MockBean
	JsonDataDao jsonDataDao;
	DetermineRoute determineRoute;

	@Test
	public void foundTest() {
		when(jsonDataDao.getJsonData(anyLong())).thenReturn(new JsonData());
		determineRoute = new DetermineRoute(jsonDataDao);
		RequestObject request = new RequestObject();
		request.setId(Long.valueOf(1));
		ResultObject result = determineRoute.apply(request);
		assertNotNull(result);
		assertEquals(Long.valueOf(1), result.getId());
		assertEquals("other", result.getType());
	}

}
