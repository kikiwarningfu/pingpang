package heyong.intellectPinPang.bean.competition;

import java.io.Serializable;
import java.util.List;
import java.util.Objects;

public class ChooseJoinMatchUserBean implements Serializable {


    /**
     * role : 1
     * players : [{"userId":511486160083128336,"userName":"王洪福","sex":"1"},{"userId":511486160083128336,"userName":"王洪福","sex":"1"}]
     */
    private long itemId;

    public long getItemId() {
        return itemId;
    }

    public void setItemId(long itemId) {
        this.itemId = itemId;
    }

    private String role;
    private List<PlayersBean> players;

    public String getRole() {
        return role;
    }

    public void setRole(String role) {
        this.role = role;
    }

    public List<PlayersBean> getPlayers() {
        return players;
    }

    public void setPlayers(List<PlayersBean> players) {
        this.players = players;
    }

    public static class PlayersBean implements Serializable {
        /**
         * userId : 511486160083128336
         * userName : 王洪福
         * sex : 1
         */

        private long userId;
        private String userName;
        private String sex;
        private boolean isSelect;
        private String userLeftName;
        private String userRightName;
        private long userLeftId;
        private long userRightId;
        private String leftSex;
        private String rightSex;

        public PlayersBean() {
        }

        public PlayersBean(long userId, String userName, String sex) {
            this.userId = userId;
            this.userName = userName;
            this.sex = sex;
        }

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

        @Override
        public boolean equals(Object o) {
            if (this == o) return true;
            if (o == null || getClass() != o.getClass()) return false;
            PlayersBean that = (PlayersBean) o;
            return userId == that.userId &&
                    isSelect == that.isSelect &&
                    Objects.equals(userName, that.userName) &&
                    Objects.equals(sex, that.sex) &&
                    Objects.equals(userLeftName, that.userLeftName) &&
                    Objects.equals(userRightName, that.userRightName) &&
                    Objects.equals(userLeftId, that.userLeftId) &&
                    Objects.equals(userRightId, that.userRightId);
        }

        @Override
        public int hashCode() {
            return Objects.hash(userId, userName, sex, isSelect, userLeftName, userRightName, userLeftId, userRightId);
        }

        public long getUserLeftId() {
            return userLeftId;
        }

        public void setUserLeftId(long userLeftId) {
            this.userLeftId = userLeftId;
        }

        public long getUserRightId() {
            return userRightId;
        }

        public void setUserRightId(long userRightId) {
            this.userRightId = userRightId;
        }

        public String getUserLeftName() {
            return userLeftName;
        }

        public void setUserLeftName(String userLeftName) {
            this.userLeftName = userLeftName;
        }

        public String getUserRightName() {
            return userRightName;
        }

        public void setUserRightName(String userRightName) {
            this.userRightName = userRightName;
        }

        public boolean isSelect() {
            return isSelect;
        }

        public void setSelect(boolean select) {
            isSelect = select;
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
    }

}
