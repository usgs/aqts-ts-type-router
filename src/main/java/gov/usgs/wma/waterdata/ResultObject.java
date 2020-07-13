package gov.usgs.wma.waterdata;

public class ResultObject {
	private Long id;
	private String type;
	private Integer partitionNumber;
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	public String getType() {
		return type;
	}
	public void setType(String type) {
		this.type = type;
	}
	public Integer getPartitionNumber() {
		return partitionNumber;
	}
	public void setPartitionNumber(Integer partitionNumber) {
		this.partitionNumber = partitionNumber;
	}
}
