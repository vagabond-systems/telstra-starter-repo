package au.com.telstra.simcardactivator;

import com.fasterxml.jackson.annotation.JsonProperty;

public class SimCardResponse {
    
    @JsonProperty("iccid")
    private String iccid;
    
    @JsonProperty("customerEmail")
    private String customerEmail;
    
    @JsonProperty("active")
    private boolean active;
    
    // Default constructor
    public SimCardResponse() {}
    
    // Constructor with parameters
    public SimCardResponse(String iccid, String customerEmail, boolean active) {
        this.iccid = iccid;
        this.customerEmail = customerEmail;
        this.active = active;
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
    
    public boolean isActive() {
        return active;
    }
    
    public void setActive(boolean active) {
        this.active = active;
    }
    
    @Override
    public String toString() {
        return "SimCardResponse{" +
                "iccid='" + iccid + '\'' +
                ", customerEmail='" + customerEmail + '\'' +
                ", active=" + active +
                '}';
    }
}
