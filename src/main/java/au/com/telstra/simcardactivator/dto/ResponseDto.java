package au.com.telstra.simcardactivator.dto;

public class ResponseDto {
	public String iccid;
	public String customerEmail;
	public boolean active;
	public ResponseDto() {
		super();
		// TODO Auto-generated constructor stub
	}
	public ResponseDto(String iccid, String customerEmail, boolean active) {
		super();
		this.iccid = iccid;
		this.customerEmail = customerEmail;
		this.active = active;
	}
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
	public boolean isActive() {
		return active;
	}
	public void setActive(boolean active) {
		this.active = active;
	}	
	
}
