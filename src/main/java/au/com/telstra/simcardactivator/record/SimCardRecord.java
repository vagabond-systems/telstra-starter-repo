package au.com.telstra.simcardactivator.record;

import au.com.telstra.simcardactivator.foundation.ActuationResult;
import au.com.telstra.simcardactivator.foundation.SimCard;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

@Entity
public class SimCardRecord {

    @Id
    @GeneratedValue()
    private long id;

    @Column(nullable = false)
    private String iccid;

    @Column(nullable = false)
    private String customerEmail;

    @Column(nullable = false)
    private boolean active;

    protected SimCardRecord() {
    }

    public SimCardRecord(SimCard simCard, ActuationResult actuationResult) {
        this.iccid = simCard.getIccid();
        this.customerEmail = simCard.getCustomerEmail();
        this.active = actuationResult.getSuccess();
    }

    public long getId() {
        return id;
    }

    public String getIccid() {
        return iccid;
    }

    public String getCustomerEmail() {
        return customerEmail;
    }

    public boolean getActive() {
        return active;
    }

    @Override
    public String toString() {
        return "SimCardRecord {id=" + id + ", iccid=" + iccid + ", customerEmail=" + customerEmail + ", active=" + active + "}";
    }
}
