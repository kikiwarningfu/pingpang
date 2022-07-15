package heyong.intellectPinPang.bean.competition;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class SelectPlayerForTeamBean implements Serializable{
    private String teamName;
    private String duiwuName;

    public SelectPlayerForTeamBean(String teamName, String duiwuName) {
        this.teamName = teamName;
        this.duiwuName = duiwuName;
    }

    public String getTeamName() {
        return teamName == null ? "" : teamName;
    }

    public void setTeamName(String teamName) {
        this.teamName = teamName;
    }

    public String getDuiwuName() {
        return duiwuName == null ? "" : duiwuName;
    }

    public void setDuiwuName(String duiwuName) {
        this.duiwuName = duiwuName;
    }

    private List<SelectBean> dataList=new ArrayList<>();

    public List<SelectBean> getDataList() {
        return dataList;
    }

    public void setDataList(List<SelectBean> dataList) {
        this.dataList = dataList;
    }

    public static class SelectBean implements Serializable {
        private String name;
        boolean isSelect;
        private String sex;
        private long userId;
        private String userName;

        public SelectBean(String name, boolean isSelect, String sex, long userId, String userName) {
            this.name = name;
            this.isSelect = isSelect;
            this.sex = sex;
            this.userId = userId;
            this.userName = userName;
        }

        public long getUserId() {
            return userId;
        }

        public void setUserId(long userId) {
            this.userId = userId;
        }

        public String getUserName() {
            return userName == null ? "" : userName;
        }

        public void setUserName(String userName) {
            this.userName = userName;
        }



        public String getSex() {
            return sex == null ? "" : sex;
        }

        public void setSex(String sex) {
            this.sex = sex;
        }

        public SelectBean(String name, boolean isSelect) {
            this.name = name;
            this.isSelect = isSelect;
        }

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public boolean isSelect() {
            return isSelect;
        }

        public void setSelect(boolean select) {
            isSelect = select;
        }
    }
}
