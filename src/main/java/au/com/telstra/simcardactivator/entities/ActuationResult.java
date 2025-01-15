package au.com.telstra.simcardactivator.entities;

public class ActuationResult {
    private boolean success;

    public ActuationResult() {
    }

    public ActuationResult(boolean success) {
        this.success = success;
    }

    public boolean getSuccess() {
        return success;
    }

    public void setSuccess(boolean success) {
        this.success = success;
    }

    @Override
    public String toString() {
        return "ActuationResult {success=" + success + "}";
    }
}
