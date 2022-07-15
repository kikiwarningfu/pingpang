package heyong.intellectPinPang.bean.competition;

public class TitleBean {
    private String title;
    private int type;
    private String itemType;//1团体 2单打 3 双打  4 混双
    private String clubName;
    private String topTitle;
    private String leftTilte;
    private String id;
    private String clubId;
    private String matchId;
    private int showType;
    private String leftPosition;

    public String getLeftPosition() {
        return leftPosition == null ? "" : leftPosition;
    }

    public void setLeftPosition(String leftPosition) {
        this.leftPosition = leftPosition;
    }

    public String getTopTitle() {
        return topTitle == null ? "" : topTitle;
    }

    public void setTopTitle(String topTitle) {
        this.topTitle = topTitle;
    }

    public String getLeftTilte() {
        return leftTilte == null ? "" : leftTilte;
    }

    public void setLeftTilte(String leftTilte) {
        this.leftTilte = leftTilte;
    }

    public String getItemType() {
        return itemType == null ? "1" : itemType;
    }

    public void setItemType(String itemType) {
        this.itemType = itemType;
    }

    public TitleBean() {
    }

    public TitleBean(String title, int type) {
        this.title = title;
        this.type = type;
    }

    public TitleBean(String title, int type, String itemType) {
        this.title = title;
        this.type = type;
        this.itemType = itemType;
    }

    public int getShowType() {
        return showType;
    }

    public void setShowType(int showType) {
        this.showType = showType;
    }

    public String getId() {
        return id == null ? "" : id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getClubId() {
        return clubId == null ? "" : clubId;
    }

    public void setClubId(String clubId) {
        this.clubId = clubId;
    }

    public String getMatchId() {
        return matchId == null ? "" : matchId;
    }

    public void setMatchId(String matchId) {
        this.matchId = matchId;
    }

    public String getClubName() {
        return clubName == null ? "" : clubName;
    }

    public void setClubName(String clubName) {
        this.clubName = clubName;
    }

    public String getTitle() {
        return title == null ? "" : title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }
}
