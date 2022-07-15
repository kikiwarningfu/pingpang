package heyong.intellectPinPang.bean.competition;

import java.util.List;

public class PersonalClubBean {

    /**
     * success : true
     * errorCode : 200
     * message : 正确
     * data : [{"id":0,"coverLogo":null,"teamTitle":"狂鸟电竞俱乐部235","city":null,"detailAddress":null,"status":null,"charge":false},{"id":0,"coverLogo":null,"teamTitle":"自由团体","city":null,"detailAddress":null,"status":null,"charge":false}]
     */

    private boolean success;
    private String errorCode;
    private String message;
    private List<DataBean> data;

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

    public List<DataBean> getData() {
        return data;
    }

    public void setData(List<DataBean> data) {
        this.data = data;
    }

    public static class DataBean {
        /**
         * id : 0
         * coverLogo : null
         * teamTitle : 狂鸟电竞俱乐部235
         * city : null
         * detailAddress : null
         * status : null
         * charge : false
         */

        private long id;
        private String coverLogo;
        private String teamTitle;
        private String city;
        private String detailAddress;
        private String status;
        private boolean charge;

        public long getId() {
            return id;
        }

        public void setId(long id) {
            this.id = id;
        }

        public String getCoverLogo() {
            return coverLogo;
        }

        public void setCoverLogo(String coverLogo) {
            this.coverLogo = coverLogo;
        }

        public String getTeamTitle() {
            return teamTitle;
        }

        public void setTeamTitle(String teamTitle) {
            this.teamTitle = teamTitle;
        }

        public String getCity() {
            return city;
        }

        public void setCity(String city) {
            this.city = city;
        }

        public String getDetailAddress() {
            return detailAddress;
        }

        public void setDetailAddress(String detailAddress) {
            this.detailAddress = detailAddress;
        }

        public String getStatus() {
            return status;
        }

        public void setStatus(String status) {
            this.status = status;
        }

        public boolean isCharge() {
            return charge;
        }

        public void setCharge(boolean charge) {
            this.charge = charge;
        }
    }

}
