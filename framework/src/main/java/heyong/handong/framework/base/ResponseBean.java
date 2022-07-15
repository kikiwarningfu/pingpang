package heyong.handong.framework.base;

import com.google.gson.annotations.SerializedName;

public class ResponseBean<T> {

    private static final int SUCCESS_CODE = 1;
    private static final int STATUS_2 = 200;
    private static final int MULTIDEVICE_CODE = 602;    // 账号在其他设备登录
    private static final int ACCOUNT_FROZEN_CODE = 609;
    private static final int TOKEN_EXPIRE_CODE = 40003;   //  token 过期

    private boolean success;
    @SerializedName(value = "errorCode", alternate = {"status"})
    private String errorCode;
    @SerializedName(value = "message", alternate = {"statusText"})
    private String message;
    @SerializedName(value = "data", alternate = {"result"})
    private T data;

    public boolean isSuccess() {
        return STATUS_2 == Long.parseLong(errorCode) || success == true;
    }

    @Override
    public String toString() {
        return "ResponseBean{" +
                "success=" + success +
                ", errorCode='" + errorCode + '\'' +
                ", message='" + message + '\'' +
                ", data=" + data +
                '}';
    }

    public static int getSuccessCode() {
        return SUCCESS_CODE;
    }

    public static int getStatus2() {
        return STATUS_2;
    }

    public static int getMultideviceCode() {
        return MULTIDEVICE_CODE;
    }

    public static int getAccountFrozenCode() {
        return ACCOUNT_FROZEN_CODE;
    }

    public static int getTokenExpireCode() {
        return TOKEN_EXPIRE_CODE;
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

    public T getData() {
        return data;
    }

    public void setData(T data) {
        this.data = data;
    }


}
