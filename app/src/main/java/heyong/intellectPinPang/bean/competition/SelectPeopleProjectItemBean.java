package heyong.intellectPinPang.bean.competition;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class SelectPeopleProjectItemBean implements Serializable {
    private String name;
    private boolean isSelect;
    private int type;
    private List<Players> playersList;
    private List<Players> noPlayersList;
    private String groupName;
    private String duiwuPosition;
    public List<Players> getNoPlayersList() {
        return noPlayersList;
    }

    public void setNoPlayersList(List<Players> noPlayersList) {
        this.noPlayersList = noPlayersList;
    }

    public SelectPeopleProjectItemBean(String name, boolean isSelect, int type, List<Players> playersList) {
        this.name = name;
        this.isSelect = isSelect;
        this.type = type;
        this.playersList = playersList;
    }

    public String getDuiwuPosition() {
        return duiwuPosition == null ? "" : duiwuPosition;
    }

    public void setDuiwuPosition(String duiwuPosition) {
        this.duiwuPosition = duiwuPosition;
    }

    public List<Players> getPlayersList() {
        if (playersList == null) {
            return new ArrayList<>();
        }
        return playersList;
    }

    public void setPlayersList(List<Players> playersList) {
        this.playersList = playersList;
    }

    public SelectPeopleProjectItemBean() {
    }

    public SelectPeopleProjectItemBean(String name, boolean isSelect, int type) {
        this.name = name;
        this.isSelect = isSelect;
        this.type = type;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getGroupName() {
        return groupName == null ? "" : groupName;
    }

    public void setGroupName(String groupName) {
        this.groupName = groupName;
    }

    public boolean isSelect() {
        return isSelect;
    }

    public void setSelect(boolean select) {
        isSelect = select;
    }

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }

    public static class Players implements Serializable{
        private long userId;
        private String userName;
        private String sex;
        private boolean isSelect;
        private String leftOneName;
        private String rightOneName;
        private long leftUserId;
        private long rightUserId;
        private String leftSex;
        private String rightSex;

        public String getLeftSex() {
            return leftSex == null ? "" : leftSex;
        }

        public void setLeftSex(String leftSex) {
            this.leftSex = leftSex;
        }

        public String getRightSex() {
            return rightSex == null ? "" : rightSex;
        }

        public void setRightSex(String rightSex) {
            this.rightSex = rightSex;
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

        public boolean isSelect() {
            return isSelect;
        }

        public void setSelect(boolean select) {
            isSelect = select;
        }

        public String getLeftOneName() {
            return leftOneName == null ? "" : leftOneName;
        }

        public void setLeftOneName(String leftOneName) {
            this.leftOneName = leftOneName;
        }

        public String getRightOneName() {
            return rightOneName == null ? "" : rightOneName;
        }

        public void setRightOneName(String rightOneName) {
            this.rightOneName = rightOneName;
        }

        public long getLeftUserId() {
            return leftUserId;
        }

        public void setLeftUserId(long leftUserId) {
            this.leftUserId = leftUserId;
        }

        public long getRightUserId() {
            return rightUserId;
        }

        public void setRightUserId(long rightUserId) {
            this.rightUserId = rightUserId;
        }
    }
}
