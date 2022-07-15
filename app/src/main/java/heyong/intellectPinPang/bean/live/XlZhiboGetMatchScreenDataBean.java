package heyong.intellectPinPang.bean.live;

import java.util.List;

public class XlZhiboGetMatchScreenDataBean {


    private String matchRule;
    private String matchType;
    private List<ProjectBean> project;
    private String matchTypeSingle;

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

    public List<ProjectBean> getProject() {
        return project;
    }

    public void setProject(List<ProjectBean> project) {
        this.project = project;
    }

    public String getMatchTypeSingle() {
        return matchTypeSingle == null ? "" : matchTypeSingle;
    }

    public void setMatchTypeSingle(String matchTypeSingle) {
        this.matchTypeSingle = matchTypeSingle;
    }

    public static class ProjectBean {
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
