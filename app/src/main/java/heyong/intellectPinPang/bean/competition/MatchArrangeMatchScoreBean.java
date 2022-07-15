package heyong.intellectPinPang.bean.competition;

import java.util.List;

/*成绩单*/
public class MatchArrangeMatchScoreBean {

    /**
     * itemType : 2
     * formTitle : 男子单打
     * format : 4
     * resultList : [{"rankName":"冠军","rank":"1","resultInfo":[{"id":511486160083128336,"clubName":"12312","clubTitle":"1231","player1Name":"王洪福","player2Name":null}]}]
     */

    private String itemType;
    private String formTitle;
    private String format;
    private List<ResultListBean> resultList;

    public String getItemType() {
        return itemType == null ? "" : itemType;
    }

    public void setItemType(String itemType) {
        this.itemType = itemType;
    }

    public String getFormTitle() {
        return formTitle == null ? "" : formTitle;
    }

    public void setFormTitle(String formTitle) {
        this.formTitle = formTitle;
    }

    public String getFormat() {
        return format == null ? "" : format;
    }

    public void setFormat(String format) {
        this.format = format;
    }

    public List<ResultListBean> getResultList() {
        return resultList;
    }

    public void setResultList(List<ResultListBean> resultList) {
        this.resultList = resultList;
    }

    public static class ResultListBean {
        /**
         * rankName : 冠军
         * rank : 1
         * resultInfo : [{"id":511486160083128336,"clubName":"12312","clubTitle":"1231","player1Name":"王洪福","player2Name":null}]
         */

        private String rankName;
        private String rank;
        private List<ResultInfoBean> resultInfo;
        public String getRankName() {
            return rankName;
        }

        public void setRankName(String rankName) {
            this.rankName = rankName;
        }

        public String getRank() {
            return rank == null ? "1" : rank;
        }

        public void setRank(String rank) {
            this.rank = rank;
        }

        public List<ResultInfoBean> getResultInfo() {
            return resultInfo;
        }

        public void setResultInfo(List<ResultInfoBean> resultInfo) {
            this.resultInfo = resultInfo;
        }

        public static class ResultInfoBean {
            /**
             * id : 511486160083128336
             * clubName : 12312
             * clubTitle : 1231
             * player1Name : 王洪福
             * player2Name : null
             */

            private long id;
            private String matchId;
            private String clubId;
            private String clubName;
            private String clubTitle;
            private String player1Name;
            private String player2Name;

            public String getMatchId() {
                return matchId == null ? "" : matchId;
            }

            public void setMatchId(String matchId) {
                this.matchId = matchId;
            }

            public String getClubId() {
                return clubId == null ? "" : clubId;
            }

            public void setClubId(String clubId) {
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

            public String getClubTitle() {
                return clubTitle == null ? "" : clubTitle;
            }

            public void setClubTitle(String clubTitle) {
                this.clubTitle = clubTitle;
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
