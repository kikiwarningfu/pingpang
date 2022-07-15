package heyong.intellectPinPang.bean.competition;

import java.util.ArrayList;
import java.util.List;

public class TeamMemberByMatchBean {


    /**
     * clubName : 女子特战队
     * logo : http://images.xlttsports.com/android_img_20210204183608367
     * coaches : [{"userId":541937243616533120,"name":"巩俐","headImg":"http://images.xlttsports.com/android_img_20210204111057467"},{"userId":552446941952301696,"name":"邓亚萍","headImg":"http://images.xlttsports.com/android_img_20210305110726027"}]
     * players : [{"userId":552167071556917888,"name":"王大壮","headImg":"http://images.xlttsports.com/android_img_20210304163712345"},{"userId":552168420638022272,"name":"李二牛","headImg":"http://images.xlttsports.com/android_img_20210304164054567"},{"userId":552169711128894080,"name":"乔峰","headImg":"http://images.xlttsports.com/android_img_20210304164537123"},{"userId":552170223001754240,"name":"慕容复","headImg":"http://images.xlttsports.com/android_img_20210304164740138"},{"userId":552170860926032512,"name":"段誉","headImg":"http://images.xlttsports.com/android_img_20210304165015035"}]
     */

    private String clubName;
    private String logo;
    private List<CoachesBean> coaches;
    private List<PlayersBean> players;

    public String getClubName() {
        return clubName == null ? "" : clubName;
    }

    public void setClubName(String clubName) {
        this.clubName = clubName;
    }

    public String getLogo() {
        return logo == null ? "" : logo;
    }

    public void setLogo(String logo) {
        this.logo = logo;
    }

    public List<CoachesBean> getCoaches() {
        if (coaches == null) {
            return new ArrayList<>();
        }
        return coaches;
    }

    public void setCoaches(List<CoachesBean> coaches) {
        this.coaches = coaches;
    }

    public List<PlayersBean> getPlayers() {
        if (players == null) {
            return new ArrayList<>();
        }
        return players;
    }

    public void setPlayers(List<PlayersBean> players) {
        this.players = players;
    }

    public static class CoachesBean {
        /**
         * userId : 541937243616533120
         * name : 巩俐
         * headImg : http://images.xlttsports.com/android_img_20210204111057467
         */

        private long userId;
        private String name;
        private String headImg;

        public long getUserId() {
            return userId;
        }

        public void setUserId(long userId) {
            this.userId = userId;
        }

        public String getName() {
            return name == null ? "" : name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public String getHeadImg() {
            return headImg == null ? "" : headImg;
        }

        public void setHeadImg(String headImg) {
            this.headImg = headImg;
        }
    }

    public static class PlayersBean {
        /**
         * userId : 552167071556917888
         * name : 王大壮
         * headImg : http://images.xlttsports.com/android_img_20210304163712345
         */

        private long userId;
        private String name;
        private String headImg;

        public long getUserId() {
            return userId;
        }

        public void setUserId(long userId) {
            this.userId = userId;
        }

        public String getName() {
            return name == null ? "" : name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public String getHeadImg() {
            return headImg == null ? "" : headImg;
        }

        public void setHeadImg(String headImg) {
            this.headImg = headImg;
        }
    }

}
