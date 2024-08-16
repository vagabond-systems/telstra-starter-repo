package au.com.telstra.simcardactivator.dto;

public class RequestDto {
	public String iccid;
	public String customerEmail;
	public String getIccid() {
		return iccid;
	}
	public void setIccid(String iccid) {
		this.iccid = iccid;
	}
	public String getCustomerEmail() {
		return customerEmail;
	}
	public void setCustomerEmail(String customerEmail) {
		this.customerEmail = customerEmail;
	}
	public RequestDto(String iccid, String customerEmail) {
		super();
		this.iccid = iccid;
		this.customerEmail = customerEmail;
	}
	public RequestDto() {
		super();
		// TODO Auto-generated constructor stub
	}
	
	
}
