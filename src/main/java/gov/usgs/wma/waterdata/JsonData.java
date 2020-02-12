package gov.usgs.wma.waterdata;

public class JsonData {
	private Long id;
	private int responseCode;
	private String serviceName;
	private String scriptName;
	private Long contentLength;
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	public int getResponseCode() {
		return responseCode;
	}
	public void setResponseCode(int responseCode) {
		this.responseCode = responseCode;
	}
	public String getServiceName() {
		return serviceName;
	}
	public void setServiceName(String serviceName) {
		this.serviceName = serviceName;
	}
	public String getScriptName() {
		return scriptName;
	}
	public void setScriptName(String scriptName) {
		this.scriptName = scriptName;
	}
	public Long getContentLength() {
		return contentLength;
	}
	public void setContentLength(Long contentLength) {
		this.contentLength = contentLength;
	}
	@Override
	public String toString() {
		return String.join(";", id.toString(), String.valueOf(responseCode), serviceName, scriptName, contentLength.toString());
	}
}
