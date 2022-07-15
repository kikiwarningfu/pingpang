package heyong.intellectPinPang.bean.competition;

import java.io.Serializable;
import java.util.List;

public class CreateCompetitionCategoriesBean implements Serializable{

    List<CreateCompetitionCategoriesChildBean> dataList;
    private String groupName;

    public String getGroupName() {
        return groupName == null ? "" : groupName;
    }

    public void setGroupName(String groupName) {
        groupName = groupName;
    }

    public CreateCompetitionCategoriesBean() {
    }

    public CreateCompetitionCategoriesBean(List<CreateCompetitionCategoriesChildBean> dataList, String groupName) {
        this.dataList = dataList;
        this.groupName = groupName;
    }

    public List<CreateCompetitionCategoriesChildBean> getDataList() {
        return dataList;
    }

    public void setDataList(List<CreateCompetitionCategoriesChildBean> dataList) {
        this.dataList = dataList;
    }

    public static class CreateCompetitionCategoriesChildBean implements Serializable {
        private String sexType;
        private String manMinAge;
        private String manMaxAge;
        private String womanMinAge;
        private String womanMaxAge;
        private String projectItem;
        private String minLimit;
        private String maxLimit;
        private String itemTitle;

        public CreateCompetitionCategoriesChildBean() {
        }

        public CreateCompetitionCategoriesChildBean(String sexType, String manMinAge, String manMaxAge, String womanMinAge, String womanMaxAge, String projectItem, String minLimit, String maxLimit, String itemTitle) {
            this.sexType = sexType;
            this.manMinAge = manMinAge;
            this.manMaxAge = manMaxAge;
            this.womanMinAge = womanMinAge;
            this.womanMaxAge = womanMaxAge;
            this.projectItem = projectItem;
            this.minLimit = minLimit;
            this.maxLimit = maxLimit;
            this.itemTitle = itemTitle;
        }

        public String getSexType() {
            return sexType == null ? "" : sexType;
        }

        public void setSexType(String sexType) {
            this.sexType = sexType;
        }

        public String getManMinAge() {
            return manMinAge == null ? "" : manMinAge;
        }

        public void setManMinAge(String manMinAge) {
            this.manMinAge = manMinAge;
        }

        public String getManMaxAge() {
            return manMaxAge == null ? "" : manMaxAge;
        }

        public void setManMaxAge(String manMaxAge) {
            this.manMaxAge = manMaxAge;
        }

        public String getWomanMinAge() {
            return womanMinAge == null ? "" : womanMinAge;
        }

        public void setWomanMinAge(String womanMinAge) {
            this.womanMinAge = womanMinAge;
        }

        public String getWomanMaxAge() {
            return womanMaxAge == null ? "" : womanMaxAge;
        }

        public void setWomanMaxAge(String womanMaxAge) {
            this.womanMaxAge = womanMaxAge;
        }

        public String getProjectItem() {
            return projectItem == null ? "" : projectItem;
        }

        public void setProjectItem(String projectItem) {
            this.projectItem = projectItem;
        }

        public String getMinLimit() {
            return minLimit == null ? "" : minLimit;
        }

        public void setMinLimit(String minLimit) {
            this.minLimit = minLimit;
        }

        public String getMaxLimit() {
            return maxLimit == null ? "" : maxLimit;
        }

        public void setMaxLimit(String maxLimit) {
            this.maxLimit = maxLimit;
        }

        public String getItemTitle() {
            return itemTitle == null ? "" : itemTitle;
        }

        public void setItemTitle(String itemTitle) {
            this.itemTitle = itemTitle;
        }


    }
}
