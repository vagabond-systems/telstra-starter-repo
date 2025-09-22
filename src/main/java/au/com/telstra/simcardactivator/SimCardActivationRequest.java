package au.com.telstra.simcardactivator;

import com.fasterxml.jackson.annotation.JsonProperty;

public class SimCardActivationRequest {
    
    @JsonProperty("iccid")
    private String iccid;
    
    @JsonProperty("customerEmail")
    private String customerEmail;
    
    // Default constructor
    public SimCardActivationRequest() {}
    
    // Constructor with parameters
    public SimCardActivationRequest(String iccid, String customerEmail) {
        this.iccid = iccid;
        this.customerEmail = customerEmail;
    }
    
    // Getters and setters
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
    
    @Override
    public String toString() {
        return "SimCardActivationRequest{" +
                "iccid='" + iccid + '\'' +
                ", customerEmail='" + customerEmail + '\'' +
                '}';
    }
}
