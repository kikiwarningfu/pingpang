package heyong.intellectPinPang.bean.friend;

import java.util.ArrayList;
import java.util.List;

public class PushMomentMessagePostBean {


    /**
     * address : 北京市北京市大兴区林肯公园A区
     * area : 大兴区
     * areaCode : 120302
     * city : 北京市
     * content : 发布新公民
     * detailAddr : 林肯公园A区
     * pictureList : [{"filePixels":{"fileUrl":"http://images.xlttsports.com/android_img_20210726111056249"}}]
     * province : 北京市
     * type : 2
     * userId : 598806417188585488
     */
    private long userId;
    private String content;
    private long type;
    private String detailAddr;
    private long areaCode;
    private String province;
    private String city;
    private String area;
    private String address;
    private String pictureList;
    private String videoList;

    public long getUserId() {
        return userId;
    }

    public void setUserId(long userId) {
        this.userId = userId;
    }

    public String getContent() {
        return content == null ? "" : content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public long getType() {
        return type;
    }

    public void setType(long type) {
        this.type = type;
    }

    public String getDetailAddr() {
        return detailAddr == null ? "" : detailAddr;
    }

    public void setDetailAddr(String detailAddr) {
        this.detailAddr = detailAddr;
    }

    public long getAreaCode() {
        return areaCode;
    }

    public void setAreaCode(long areaCode) {
        this.areaCode = areaCode;
    }

    public String getProvince() {
        return province == null ? "" : province;
    }

    public void setProvince(String province) {
        this.province = province;
    }

    public String getCity() {
        return city == null ? "" : city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getArea() {
        return area == null ? "" : area;
    }

    public void setArea(String area) {
        this.area = area;
    }

    public String getAddress() {
        return address == null ? "" : address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getPictureList() {
        return pictureList == null ? "" : pictureList;
    }

    public void setPictureList(String pictureList) {
        this.pictureList = pictureList;
    }

    public String getVideoList() {
        return videoList == null ? "" : videoList;
    }

    public void setVideoList(String videoList) {
        this.videoList = videoList;
    }

    public static class PictureListBean {
        /**
         * filePixels : {"fileUrl":"http://images.xlttsports.com/android_img_20210726111056249"}
         */

        private FilePixelsBean filePixels;

        public FilePixelsBean getFilePixels() {
            return filePixels;
        }

        public void setFilePixels(FilePixelsBean filePixels) {
            this.filePixels = filePixels;
        }

        public static class FilePixelsBean {
            /**
             * fileUrl : http://images.xlttsports.com/android_img_20210726111056249
             */

            private String fileUrl;

            public String getFileUrl() {
                return fileUrl;
            }

            public void setFileUrl(String fileUrl) {
                this.fileUrl = fileUrl;
            }
        }
    }
}
