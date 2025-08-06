package au.com.telstra.simcardactivator.model;

import com.fasterxml.jackson.annotation.JsonProperty;

public class ActivationRequest {
    
    @JsonProperty("iccid")
    private String iccid;
    
    @JsonProperty("customerEmail")
    private String customerEmail;

    public ActivationRequest() {}

    public ActivationRequest(String iccid, String customerEmail) {
        this.iccid = iccid;
        this.customerEmail = customerEmail;
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
}