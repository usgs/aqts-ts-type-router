package gov.usgs.wma.waterdata;

public class RequestObject {
	private Long id;
	private Integer partitionNumber;
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	public Integer getPartitionNumber() {
		return partitionNumber;
	}
	public void setPartitionNumber(Integer partitionNumber) {
		this.partitionNumber = partitionNumber;
	}
}
