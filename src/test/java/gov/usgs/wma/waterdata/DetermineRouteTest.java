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
		Long[] ids = new Long[] {Long.valueOf(1),Long.valueOf(2),Long.valueOf(3),Long.valueOf(4),Long.valueOf(5)};
		request.setIds(ids);
		ResultObject result = determineRoute.apply(request);
		assertNotNull(result);
		assertEquals(ids, result.getOtherIds());
	}

}
