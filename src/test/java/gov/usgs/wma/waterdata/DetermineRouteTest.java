package gov.usgs.wma.waterdata;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.SpringBootTest.WebEnvironment;

@SpringBootTest(webEnvironment=WebEnvironment.NONE)//,
//	classes={DBTestConfig.class, DetermineRoute.class, JsonDataDao.class})
public class DetermineRouteTest {

//	@Autowired
	DetermineRoute determineRoute = new DetermineRoute();

	@Test
	public void foundTest() {
		RequestObject request = new RequestObject();
		request.setId(Long.valueOf(1));
		ResultObject result = determineRoute.apply(request);
		assertNotNull(result);
		assertEquals(Long.valueOf(1), result.getId());
		assertEquals("other", result.getType());
	}

}
