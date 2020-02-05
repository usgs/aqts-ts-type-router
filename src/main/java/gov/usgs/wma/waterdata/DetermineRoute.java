package gov.usgs.wma.waterdata;

import java.util.function.Function;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class DetermineRoute implements Function<RequestObject, Object> {

	@Autowired
	JsonDataDao jsonDataDao;

	@Override
	public ResultObject apply(RequestObject request) {
		return processRequest(request);
	}

	protected ResultObject processRequest(RequestObject request) {
		ResultObject result = new ResultObject();
//		for (Long id : request.getIds()) {
//			
//		}
		result.setOtherIds(request.getIds());
		return result;
	}
}
