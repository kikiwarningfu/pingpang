package heyong.intellectPinPang.bean.competition;

public class RefereeInfoBean {

    /**
     * name : 王洪福
     * headImg : https://img.tupianzj.com/uploads/allimg/202011/9999/249fcab011.jpg
     * role : 国家一级裁判员
     * address : 河北 石家庄 长安
     * sex : 1
     * age : 25
     * status : 2
     * refereeCount : 0
     * introduce : http://images.xlttsports.com/android_img_20201114092629056
     * roleId : 10000
     */

    private String name;
    private String headImg;
    private String role;
    private String address;
    private String sex;
    private int age;
    private String status;
    private int refereeCount;
    private String introduce;
    private long roleId;

    public String getName() {
        return name == null ? "" : name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getHeadImg() {
        return headImg == null ? "" : headImg;
    }

    public void setHeadImg(String headImg) {
        this.headImg = headImg;
    }

    public String getRole() {
        return role == null ? "" : role;
    }

    public void setRole(String role) {
        this.role = role;
    }

    public String getAddress() {
        return address == null ? "" : address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getSex() {
        return sex == null ? "" : sex;
    }

    public void setSex(String sex) {
        this.sex = sex;
    }

    public int getAge() {
        return age;
    }

    public void setAge(int age) {
        this.age = age;
    }

    public String getStatus() {
        return status == null ? "" : status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public int getRefereeCount() {
        return refereeCount;
    }

    public void setRefereeCount(int refereeCount) {
        this.refereeCount = refereeCount;
    }

    public String getIntroduce() {
        return introduce == null ? "" : introduce;
    }

    public void setIntroduce(String introduce) {
        this.introduce = introduce;
    }

    public long getRoleId() {
        return roleId;
    }

    public void setRoleId(long roleId) {
        this.roleId = roleId;
    }
}
