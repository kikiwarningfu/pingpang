package heyong.intellectPinPang.bean.friend;

public class FriendListPostBean {
    private String page;
    private String pageNumber;
    private String type;
    private String userIds;
    private String isHot;
    private String province;
    private String city;
    private String area;
    private String currentUserId;
    public String getPage() {
        return page == null ? "" : page;
    }

    public void setPage(String page) {
        this.page = page;
    }

    public String getPageNumber() {
        return pageNumber == null ? "" : pageNumber;
    }

    public void setPageNumber(String pageNumber) {
        this.pageNumber = pageNumber;
    }

    public String getType() {
        return type == null ? "" : type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getUserIds() {
        return userIds == null ? "" : userIds;
    }

    public void setUserIds(String userIds) {
        this.userIds = userIds;
    }

    public String getIsHot() {
        return isHot == null ? "" : isHot;
    }

    public void setIsHot(String isHot) {
        this.isHot = isHot;
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

    public String getCurrentUserId() {
        return currentUserId == null ? "" : currentUserId;
    }

    public void setCurrentUserId(String currentUserId) {
        this.currentUserId = currentUserId;
    }
}
