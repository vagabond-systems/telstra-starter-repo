package au.com.telstra.simcardactivator.models;


import org.springframework.stereotype.Component;

public class ActuationResult {
    private boolean success;

    public ActuationResult(boolean success) {
        this.success = success;
    }

    public ActuationResult() {
    }


    public boolean getSuccess() {
        return success;
    }

    public void setSuccess(boolean success) {
        this.success = success;
    }

    @Override
    public String toString() {
        return "ActuationResult{" +
                "success=" + success +
                '}';
    }
}
