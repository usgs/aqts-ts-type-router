package gov.usgs.wma.waterdata;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase.Replace;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.SpringBootTest.WebEnvironment;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.TestExecutionListeners;
import org.springframework.test.context.support.DependencyInjectionTestExecutionListener;
import org.springframework.test.context.support.DirtiesContextTestExecutionListener;
import org.springframework.test.context.transaction.TransactionalTestExecutionListener;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.github.springtestdbunit.TransactionDbUnitTestExecutionListener;
import com.github.springtestdbunit.annotation.DatabaseSetup;
import com.github.springtestdbunit.annotation.DbUnitConfiguration;

@SpringBootTest(webEnvironment=WebEnvironment.NONE,
	classes={DBTestConfig.class, JsonDataDao.class})
@ActiveProfiles("it")
@TestExecutionListeners({ DependencyInjectionTestExecutionListener.class,
	DirtiesContextTestExecutionListener.class,
	TransactionalTestExecutionListener.class,
	TransactionDbUnitTestExecutionListener.class })
@DbUnitConfiguration(dataSetLoader=FileSensingDataSetLoader.class)
@AutoConfigureTestDatabase(replace=Replace.NONE)
@Transactional(propagation=Propagation.NOT_SUPPORTED)
@DirtiesContext
@DatabaseSetup("classpath:/testData/")
public class JsonDataDaoIT {

	@Autowired
	JsonDataDao jsonDataDao;

	@Test
	public void foundTest() {
		JsonData jsonData = jsonDataDao.getJsonData(Long.valueOf(1));
		assertNotNull(jsonData);
		assertEquals(1, jsonData.getId());
		assertEquals(200, jsonData.getResponseCode());
		assertEquals(DetermineRoute.GET_TS_DESCRIPTION_LIST, jsonData.getServiceName());
		assertEquals(DetermineRoute.GET_TS_DATA, jsonData.getScriptName());
		assertEquals(Long.valueOf(8), jsonData.getContentLength());
	}

	@Test
	public void notFoundTest() {
		JsonData jsonData = jsonDataDao.getJsonData(Long.valueOf(0));
		assertNull(jsonData);
	}
}
