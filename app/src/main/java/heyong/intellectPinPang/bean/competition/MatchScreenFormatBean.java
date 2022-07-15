package heyong.intellectPinPang.bean.competition;

import java.util.List;

public class MatchScreenFormatBean {


    /**
     * matchRule : 3
     * matchType : [{"title":"第一阶段","info":[{"id":1,"title":"3","titleName":null}]},{"title":"出组名单","info":null},{"title":"第二阶段","info":[{"id":1,"title":"3","titleName":null}]},{"title":"成绩单","info":null}]
     * project : null
     * matchTypeSingle : ["循环赛","成绩单"]
     */

    private String matchRule;
    private String project;
    private List<MatchTypeBean> matchType;
    private List<MatchTypeSingleBean> matchTypeSingle;

    public String getMatchRule() {
        return matchRule == null ? "1" : matchRule;
    }

    public void setMatchRule(String matchRule) {
        this.matchRule = matchRule;
    }

    public String getProject() {
        return project == null ? "" : project;
    }

    public void setProject(String project) {
        this.project = project;
    }

    public List<MatchTypeBean> getMatchType() {
        return matchType;
    }

    public void setMatchType(List<MatchTypeBean> matchType) {
        this.matchType = matchType;
    }

    public List<MatchTypeSingleBean> getMatchTypeSingle() {
        return matchTypeSingle;
    }

    public void setMatchTypeSingle(List<MatchTypeSingleBean> matchTypeSingle) {
        this.matchTypeSingle = matchTypeSingle;
    }

    public static class MatchTypeBean {
        /**
         * title : 第一阶段
         * info : [{"id":1,"title":"3","titleName":null}]
         */

        private String title;
        private String code;
        private List<InfoBean> info;
        private boolean isSelect;

        public boolean isSelect() {
            return isSelect;
        }

        public void setSelect(boolean select) {
            isSelect = select;
        }

        public String getCode() {
            return code == null ? "" : code;
        }

        public void setCode(String code) {
            this.code = code;
        }

        public String getTitle() {
            return title == null ? "" : title;
        }

        public void setTitle(String title) {
            this.title = title;
        }

        public List<InfoBean> getInfo() {
            return info;
        }

        public void setInfo(List<InfoBean> info) {
            this.info = info;
        }

        public static class InfoBean {
            /**
             * id : 1
             * title : 3
             * titleName : null
             */

            private long id;
            private String title;
            private String titleName;
            private String titleWay;
            private boolean isSelect;

            public boolean isSelect() {
                return isSelect;
            }

            public void setSelect(boolean select) {
                isSelect = select;
            }

            public String getTitleWay() {
                return titleWay == null ? "" : titleWay;
            }

            public void setTitleWay(String titleWay) {
                this.titleWay = titleWay;
            }

            public long getId() {
                return id;
            }

            public void setId(long id) {
                this.id = id;
            }

            public String getTitle() {
                return title == null ? "" : title;
            }

            public void setTitle(String title) {
                this.title = title;
            }

            public String getTitleName() {
                return titleName == null ? "" : titleName;
            }

            public void setTitleName(String titleName) {
                this.titleName = titleName;
            }
        }
    }

    public static class MatchTypeSingleBean {
        private String title;
        private String code;

        public String getTitle() {
            return title == null ? "" : title;
        }

        public void setTitle(String title) {
            this.title = title;
        }

        public String getCode() {
            return code == null ? "0" : code;
        }

        public void setCode(String code) {
            this.code = code;
        }
    }
}
