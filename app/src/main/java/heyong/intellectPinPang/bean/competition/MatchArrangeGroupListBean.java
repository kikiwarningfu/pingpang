package heyong.intellectPinPang.bean.competition;

import java.util.List;

public class MatchArrangeGroupListBean {


    /**
     * joinCount : 1
     * fromTitle : 男子单打出组名单
     * format : 2
     * groupDto : [{"groupNum":1,"groupNumName":"A","titleWay":"2","winCount":1,"groupData":[{"id":511486160083128336,"clubName":"12312","player1Name":"王洪福","player2Name":null}]}]
     */

    private int joinCount;
    private String itemType;
    private String fromTitle;
    private String format;




    private List<GroupDtoBean> groupDto;

    public String getItemType() {
        return itemType == null ? "" : itemType;
    }

    public void setItemType(String itemType) {
        this.itemType = itemType;
    }

    public int getJoinCount() {
        return joinCount;
    }

    public void setJoinCount(int joinCount) {
        this.joinCount = joinCount;
    }

    public String getFromTitle() {
        return fromTitle == null ? "" : fromTitle;
    }

    public void setFromTitle(String fromTitle) {
        this.fromTitle = fromTitle;
    }

    public String getFormat() {
        return format == null ? "" : format;
    }

    public void setFormat(String format) {
        this.format = format;
    }

    public List<GroupDtoBean> getGroupDto() {
        return groupDto;
    }

    public void setGroupDto(List<GroupDtoBean> groupDto) {
        this.groupDto = groupDto;
    }

    public static class GroupDtoBean {
        /**
         * groupNum : 1
         * groupNumName : A
         * titleWay : 2
         * winCount : 1
         * groupData : [{"id":511486160083128336,"clubName":"12312","player1Name":"王洪福","player2Name":null}]
         */

        private int groupNum;
        private String groupNumName;
        private String titleWay;
        private int winCount;
        private List<GroupDataBean> groupData;

        public int getGroupNum() {
            return groupNum;
        }

        public void setGroupNum(int groupNum) {
            this.groupNum = groupNum;
        }

        public String getGroupNumName() {
            return groupNumName == null ? "" : groupNumName;
        }

        public void setGroupNumName(String groupNumName) {
            this.groupNumName = groupNumName;
        }

        public String getTitleWay() {
            return titleWay == null ? "" : titleWay;
        }

        public void setTitleWay(String titleWay) {
            this.titleWay = titleWay;
        }

        public int getWinCount() {
            return winCount;
        }

        public void setWinCount(int winCount) {
            this.winCount = winCount;
        }

        public List<GroupDataBean> getGroupData() {
            return groupData;
        }

        public void setGroupData(List<GroupDataBean> groupData) {
            this.groupData = groupData;
        }

        public static class GroupDataBean {
            /**
             * id : 511486160083128336
             * clubName : 12312
             * player1Name : 王洪福
             * player2Name : null
             */

            private long id;
            private long matchId;
            private long clubId;
            private String clubName;
            private String player1Name;
            private String player2Name;

            public long getMatchId() {
                return matchId;
            }

            public void setMatchId(long matchId) {
                this.matchId = matchId;
            }

            public long getClubId() {
                return clubId;
            }

            public void setClubId(long clubId) {
                this.clubId = clubId;
            }

            public long getId() {
                return id;
            }

            public void setId(long id) {
                this.id = id;
            }

            public String getClubName() {
                return clubName == null ? "" : clubName;
            }

            public void setClubName(String clubName) {
                this.clubName = clubName;
            }

            public String getPlayer1Name() {
                return player1Name == null ? "" : player1Name;
            }

            public void setPlayer1Name(String player1Name) {
                this.player1Name = player1Name;
            }

            public String getPlayer2Name() {
                return player2Name == null ? "" : player2Name;
            }

            public void setPlayer2Name(String player2Name) {
                this.player2Name = player2Name;
            }
        }
    }

}
