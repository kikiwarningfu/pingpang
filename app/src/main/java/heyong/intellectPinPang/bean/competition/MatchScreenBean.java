package heyong.intellectPinPang.bean.competition;

import java.util.List;

public class MatchScreenBean {

    /**
     * matchRule : null
     * matchType : null
     * project : [{"id":512317538160906256,"projectTitle":"成人甲组","sex":[{"sex":"1","sexName":"男","item":[{"id":512317538177683472,"projectItem":"1","projectItemName":"团体"},{"id":514038703090864144,"projectItem":"2","projectItemName":"单打"}]}]}]
     * matchTypeSingle : null
     */

    private String matchRule;
    private String matchType;
    private String matchTypeSingle;
    private List<ProjectBean> project;

    public String getMatchRule() {
        return matchRule == null ? "" : matchRule;
    }

    public void setMatchRule(String matchRule) {
        this.matchRule = matchRule;
    }

    public String getMatchType() {
        return matchType == null ? "" : matchType;
    }

    public void setMatchType(String matchType) {
        this.matchType = matchType;
    }

    public String getMatchTypeSingle() {
        return matchTypeSingle == null ? "" : matchTypeSingle;
    }

    public void setMatchTypeSingle(String matchTypeSingle) {
        this.matchTypeSingle = matchTypeSingle;
    }

    public List<ProjectBean> getProject() {
        return project;
    }

    public void setProject(List<ProjectBean> project) {
        this.project = project;
    }

    public static class ProjectBean {
        /**
         * id : 512317538160906256
         * projectTitle : 成人甲组
         * sex : [{"sex":"1","sexName":"男","item":[{"id":512317538177683472,"projectItem":"1","projectItemName":"团体"},{"id":514038703090864144,"projectItem":"2","projectItemName":"单打"}]}]
         */

        private long id;
        private String projectTitle;
        private List<SexBean> sex;
        private boolean isSelect;

        public boolean isSelect() {
            return isSelect;
        }

        public void setSelect(boolean select) {
            isSelect = select;
        }

        public long getId() {
            return id;
        }

        public void setId(long id) {
            this.id = id;
        }

        public String getProjectTitle() {
            return projectTitle == null ? "" : projectTitle;
        }

        public void setProjectTitle(String projectTitle) {
            this.projectTitle = projectTitle;
        }

        public List<SexBean> getSex() {
            return sex;
        }

        public void setSex(List<SexBean> sex) {
            this.sex = sex;
        }

        public static class SexBean {
            /**
             * sex : 1
             * sexName : 男
             * item : [{"id":512317538177683472,"projectItem":"1","projectItemName":"团体"},{"id":514038703090864144,"projectItem":"2","projectItemName":"单打"}]
             */

            private String sex;
            private String sexName;
            private List<ItemBean> item;
            private boolean isSelect;

            public boolean isSelect() {
                return isSelect;
            }

            public void setSelect(boolean select) {
                isSelect = select;
            }

            public String getSex() {
                return sex == null ? "" : sex;
            }

            public void setSex(String sex) {
                this.sex = sex;
            }

            public String getSexName() {
                return sexName == null ? "" : sexName;
            }

            public void setSexName(String sexName) {
                this.sexName = sexName;
            }

            public List<ItemBean> getItem() {
                return item;
            }

            public void setItem(List<ItemBean> item) {
                this.item = item;
            }

            public static class ItemBean {
                /**
                 * id : 512317538177683472
                 * projectItem : 1
                 * projectItemName : 团体
                 */

                private long id;
                private String projectItem;
                private String projectItemName;
                private boolean isSelect;

                public boolean isSelect() {
                    return isSelect;
                }

                public void setSelect(boolean select) {
                    isSelect = select;
                }

                public long getId() {
                    return id;
                }

                public void setId(long id) {
                    this.id = id;
                }

                public String getProjectItem() {
                    return projectItem == null ? "" : projectItem;
                }

                public void setProjectItem(String projectItem) {
                    this.projectItem = projectItem;
                }

                public String getProjectItemName() {
                    return projectItemName == null ? "" : projectItemName;
                }

                public void setProjectItemName(String projectItemName) {
                    this.projectItemName = projectItemName;
                }
            }
        }
    }

}
