package gov.usgs.wma.waterdata;

public class ResultObject {

	private Long[] tsDescriptionIds;
	private Long[] tsCorrectedDataIds;
	private Long[] otherIds;
	public Long[] getTsDescriptionIds() {
		return tsDescriptionIds;
	}
	public void setTsDescriptionIds(Long[] tsDescriptionIds) {
		this.tsDescriptionIds = tsDescriptionIds;
	}
	public Long[] getTsCorrectedDataIds() {
		return tsCorrectedDataIds;
	}
	public void setTsCorrectedDataIds(Long[] tsCorrectedDataIds) {
		this.tsCorrectedDataIds = tsCorrectedDataIds;
	}
	public Long[] getOtherIds() {
		return otherIds;
	}
	public void setOtherIds(Long[] otherIds) {
		this.otherIds = otherIds;
	}
}
