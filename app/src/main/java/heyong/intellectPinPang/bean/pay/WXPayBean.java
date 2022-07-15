package heyong.intellectPinPang.bean.pay;


import java.io.Serializable;

/**
 * Created by Administrator on 2017/7/22.
 */

public class WXPayBean implements Serializable {


        /**
         * appid : wx2f0dce047c73e6a1
         * partnerid : 1503481981
         * prepayid : wx24134225318336ab9d456c7dab92760000
         * packagestr : Sign=WXPay
         * noncestr : emMGFbPTkSvVj2aW
         * timestamp : 1608788544
         * sign : 8A67636A0A3D33D97FF52F2EBFB92E25
         * clickType : 1
         */

        private String appid;
        private String partnerid;
        private String prepayid;
        private String packagestr;
        private String noncestr;
        private String timestamp;
        private String sign;
        private String clickType;

        public String getAppid() {
            return appid;
        }

        public void setAppid(String appid) {
            this.appid = appid;
        }

        public String getPartnerid() {
            return partnerid;
        }

        public void setPartnerid(String partnerid) {
            this.partnerid = partnerid;
        }

        public String getPrepayid() {
            return prepayid;
        }

        public void setPrepayid(String prepayid) {
            this.prepayid = prepayid;
        }

        public String getPackagestr() {
            return packagestr;
        }

        public void setPackagestr(String packagestr) {
            this.packagestr = packagestr;
        }

        public String getNoncestr() {
            return noncestr;
        }

        public void setNoncestr(String noncestr) {
            this.noncestr = noncestr;
        }

        public String getTimestamp() {
            return timestamp;
        }

        public void setTimestamp(String timestamp) {
            this.timestamp = timestamp;
        }

        public String getSign() {
            return sign;
        }

        public void setSign(String sign) {
            this.sign = sign;
        }

        public String getClickType() {
            return clickType;
        }

        public void setClickType(String clickType) {
            this.clickType = clickType;
        }

}
