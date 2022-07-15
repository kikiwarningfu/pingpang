package heyong.intellectPinPang.bean.competition;

import java.io.Serializable;

public class RefereeApplyMatchBean implements Serializable {

        /**
         * roleName : 国家一级裁判员
         * name : 王洪福
         * sex : 1
         * matchTitle : 北京二级运动员晋级赛
         */

        private String roleName;
        private String name;
        private String sex;
        private String matchTitle;

    public String getRoleName() {
        return roleName == null ? "" : roleName;
    }

    public void setRoleName(String roleName) {
        this.roleName = roleName;
    }

    public String getName() {
        return name == null ? "" : name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getSex() {
        return sex == null ? "" : sex;
    }

    public void setSex(String sex) {
        this.sex = sex;
    }

    public String getMatchTitle() {
        return matchTitle == null ? "" : matchTitle;
    }

    public void setMatchTitle(String matchTitle) {
        this.matchTitle = matchTitle;
    }
}
