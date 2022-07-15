package heyong.intellectPinPang.bean.competition;

public class QuerySubmitBean {


        /**
         * code : 7
         * leftName : 1
         * rightName : null
         */

        private String code;
        private String leftName;
        private String rightName;

    public String getCode() {
        return code == null ? "0" : code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getLeftName() {
        return leftName == null ? "" : leftName;
    }

    public void setLeftName(String leftName) {
        this.leftName = leftName;
    }

    public String getRightName() {
        return rightName == null ? "" : rightName;
    }

    public void setRightName(String rightName) {
        this.rightName = rightName;
    }
}
