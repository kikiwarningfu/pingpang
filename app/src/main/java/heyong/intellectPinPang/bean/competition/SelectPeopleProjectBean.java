package heyong.intellectPinPang.bean.competition;

import com.chad.library.adapter.base.entity.MultiItemEntity;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class SelectPeopleProjectBean implements MultiItemEntity, Serializable {
    private int cardType;
//  multiTypeDelegate.registerItemType(0, R.layout.item_select_lead_group);//0 领队
//  multiTypeDelegate.registerItemType(1, R.layout.item_select_lead_coach);//1 教练员
//  multiTypeDelegate.registerItemType(2, R.layout.item_select_people_team);//2 团体
//  multiTypeDelegate.registerItemType(3, R.layout.item_select_people_single);//3  混合单打
//  multiTypeDelegate.registerItemType(4, R.layout.item_select_people_double_profressional);//双打专业
//  multiTypeDelegate.registerItemType(5, R.layout.item_select_people_double_no_profressional);//双打非专业

    private long id;
    private String sexType;
    private String itemProjectType = "0";
    private String itemTitle;

    private String id1;
    private String itemProjectId;
    private String matchLevel;
    private String projectName;
    private String duiwuPosition;

    public int getCardType() {
        return cardType;
    }

    int projectItems;

    public void setCardType(int cardType) {
        this.cardType = cardType;
    }

    @Override
    public int getItemType() {
        return cardType;
    }

    public SelectPeopleProjectBean(int cardType) {
        this.cardType = cardType;
    }

    List<SelectPeopleProjectItemBean> newList;
    List<ChooseJoinMatchUserBean.PlayersBean> tuantiPlayers = new ArrayList<>();
    List<ChooseJoinMatchUserBean.PlayersBean> tuantiOtherPlayers = new ArrayList<>();

    public List<ChooseJoinMatchUserBean.PlayersBean> getTuantiOtherPlayers() {
        return tuantiOtherPlayers;
    }

    public void setTuantiOtherPlayers(List<ChooseJoinMatchUserBean.PlayersBean> tuantiOtherPlayers) {
        this.tuantiOtherPlayers = tuantiOtherPlayers;
    }

    public List<ChooseJoinMatchUserBean.PlayersBean> getTuantiPlayers() {
        return tuantiPlayers;
    }

    public void setTuantiPlayers(List<ChooseJoinMatchUserBean.PlayersBean> tuantiPlayers) {
        this.tuantiPlayers = tuantiPlayers;
    }
    //
//    public SelectPeopleProjectBean(int type, List<SelectPeopleProjectItemBean> newList, long projectId) {
//        this.newList = newList;
//        this.cardType = type;
//        this.projectId = projectId;
//    }

    public SelectPeopleProjectBean(int type, List<SelectPeopleProjectItemBean> newList, long id, String sexType,
                                   String itemProjectType, String itemTitle
            , String id1, String itemProjectId, String matchLevel, String projectName, int projectItems) {
        this.cardType = type;
        this.id = id;
        this.sexType = sexType;
        this.itemProjectType = itemProjectType;
        this.newList = newList;
        this.itemTitle = itemTitle;
        this.id1 = id1;
        this.itemProjectId = itemProjectId;
        this.matchLevel = matchLevel;
        this.projectName = projectName;
        this.projectItems = projectItems;
    }

    public String getDuiwuPosition() {
        return duiwuPosition == null ? "" : duiwuPosition;
    }

    public void setDuiwuPosition(String duiwuPosition) {
        this.duiwuPosition = duiwuPosition;
    }

    public int getProjectItems() {
        return projectItems;
    }

    public void setProjectItems(int projectItems) {
        this.projectItems = projectItems;
    }

    public String getProjectName() {
        return projectName == null ? "" : projectName;
    }

    public void setProjectName(String projectName) {
        this.projectName = projectName;
    }

    public String getMatchLevel() {
        return matchLevel == null ? "" : matchLevel;
    }

    public void setMatchLevel(String matchLevel) {
        this.matchLevel = matchLevel;
    }

    public String getItemId() {
        return id1 == null ? "" : id1;
    }

    public void setItemId(String itemId) {
        this.id1 = itemId;
    }

    public String getItemProjectId() {
        return itemProjectId == null ? "" : itemProjectId;
    }

    public void setItemProjectId(String itemProjectId) {
        this.itemProjectId = itemProjectId;
    }

    public String getSexType() {
        return sexType;
    }

    public void setSexType(String sexType) {
        this.sexType = sexType;
    }

    public String getItemProjectType() {
        return itemProjectType;
    }

    public void setItemProjectType(String itemProjectType) {
        this.itemProjectType = itemProjectType;
    }

    public String getItemTitle() {
        return itemTitle == null ? "" : itemTitle;
    }

    public void setItemTitle(String itemTitle) {
        this.itemTitle = itemTitle;
    }

    public long getProjectId() {
        return id;
    }

    public void setProjectId(long projectId) {
        this.id = projectId;
    }

    public List<SelectPeopleProjectItemBean> getNewList() {
        return newList;
    }

    public void setNewList(List<SelectPeopleProjectItemBean> newList) {
        this.newList = newList;
    }
}
