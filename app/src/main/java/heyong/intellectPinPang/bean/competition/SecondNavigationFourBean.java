package heyong.intellectPinPang.bean.competition;

import java.util.List;

public class SecondNavigationFourBean {


    /**
     * name : 名次
     * navigation : ["1-8"]
     */
    private int winCount;
    private String name;
    private List<String> navigation;

    public int getWinCount() {
        return winCount;
    }

    public void setWinCount(int winCount) {
        this.winCount = winCount;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public List<String> getNavigation() {
        return navigation;
    }

    public void setNavigation(List<String> navigation) {
        this.navigation = navigation;
    }

}
