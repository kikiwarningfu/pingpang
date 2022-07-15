package heyong.intellectPinPang.bean.competition;

public class OrderIdBean {


    /**
     * success : true
     * errorCode : 200
     * message : 正确
     * data : 564811634162685104
     */

    private boolean success;
    private String errorCode;
    private String message;
    private long data;

    public boolean isSuccess() {
        return success;
    }

    public void setSuccess(boolean success) {
        this.success = success;
    }

    public String getErrorCode() {
        return errorCode;
    }

    public void setErrorCode(String errorCode) {
        this.errorCode = errorCode;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public long getData() {
        return data;
    }

    public void setData(long data) {
        this.data = data;
    }
}
