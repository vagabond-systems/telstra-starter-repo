package au.com.telstra.simcardactivator.model;

public class SimActivatorRequest {
       
	   String iccid;

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

	String customerEmail;
}
