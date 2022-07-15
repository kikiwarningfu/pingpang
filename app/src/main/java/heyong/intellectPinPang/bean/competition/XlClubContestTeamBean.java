package heyong.intellectPinPang.bean.competition;

import java.util.List;

public class XlClubContestTeamBean {


        /**
         * id : 513707520385650704
         * contestTitle : 2020冬季训练赛
         * teams : [{"pageNo":1,"pageSize":10,"orderBy":"id desc","angle":0,"contestTeamMembers":null,"id":513707520704417808,"teamNum":"红队","clubContestInfoId":null,"createUser":null,"createTime":null,"updateUser":null,"updateTime":null,"isDelete":null,"teamType":null},{"pageNo":1,"pageSize":10,"orderBy":"id desc","angle":0,"contestTeamMembers":null,"id":513707520821858320,"teamNum":"蓝队","clubContestInfoId":null,"createUser":null,"createTime":null,"updateUser":null,"updateTime":null,"isDelete":null,"teamType":null}]
         */

        private long id;
        private String contestTitle;
        private List<TeamsBean> teams;

        public long getId() {
            return id;
        }

        public void setId(long id) {
            this.id = id;
        }

        public String getContestTitle() {
            return contestTitle;
        }

        public void setContestTitle(String contestTitle) {
            this.contestTitle = contestTitle;
        }

        public List<TeamsBean> getTeams() {
            return teams;
        }

        public void setTeams(List<TeamsBean> teams) {
            this.teams = teams;
        }

        public static class TeamsBean {
            /**
             * pageNo : 1
             * pageSize : 10
             * orderBy : id desc
             * angle : 0
             * contestTeamMembers : null
             * id : 513707520704417808
             * teamNum : 红队
             * clubContestInfoId : null
             * createUser : null
             * createTime : null
             * updateUser : null
             * updateTime : null
             * isDelete : null
             * teamType : null
             */

            private int pageNo;
            private int pageSize;
            private String orderBy;
            private int angle;
            private String contestTeamMembers;
            private long id;
            private String teamNum;
            private String clubContestInfoId;
            private String createUser;
            private String createTime;
            private String updateUser;
            private String updateTime;
            private String isDelete;
            private String teamType;

            public int getPageNo() {
                return pageNo;
            }

            public void setPageNo(int pageNo) {
                this.pageNo = pageNo;
            }

            public int getPageSize() {
                return pageSize;
            }

            public void setPageSize(int pageSize) {
                this.pageSize = pageSize;
            }

            public String getOrderBy() {
                return orderBy;
            }

            public void setOrderBy(String orderBy) {
                this.orderBy = orderBy;
            }

            public int getAngle() {
                return angle;
            }

            public void setAngle(int angle) {
                this.angle = angle;
            }

            public String getContestTeamMembers() {
                return contestTeamMembers;
            }

            public void setContestTeamMembers(String contestTeamMembers) {
                this.contestTeamMembers = contestTeamMembers;
            }

            public long getId() {
                return id;
            }

            public void setId(long id) {
                this.id = id;
            }

            public String getTeamNum() {
                return teamNum;
            }

            public void setTeamNum(String teamNum) {
                this.teamNum = teamNum;
            }

            public String getClubContestInfoId() {
                return clubContestInfoId;
            }

            public void setClubContestInfoId(String clubContestInfoId) {
                this.clubContestInfoId = clubContestInfoId;
            }

            public String getCreateUser() {
                return createUser;
            }

            public void setCreateUser(String createUser) {
                this.createUser = createUser;
            }

            public String getCreateTime() {
                return createTime;
            }

            public void setCreateTime(String createTime) {
                this.createTime = createTime;
            }

            public String getUpdateUser() {
                return updateUser;
            }

            public void setUpdateUser(String updateUser) {
                this.updateUser = updateUser;
            }

            public String getUpdateTime() {
                return updateTime;
            }

            public void setUpdateTime(String updateTime) {
                this.updateTime = updateTime;
            }

            public String getIsDelete() {
                return isDelete;
            }

            public void setIsDelete(String isDelete) {
                this.isDelete = isDelete;
            }

            public String getTeamType() {
                return teamType;
            }

            public void setTeamType(String teamType) {
                this.teamType = teamType;
            }
        }

}
