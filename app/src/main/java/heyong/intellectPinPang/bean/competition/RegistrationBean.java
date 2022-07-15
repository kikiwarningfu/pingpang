package heyong.intellectPinPang.bean.competition;

import java.util.ArrayList;
import java.util.List;

public class RegistrationBean {

    /**
     * leader : [{"id":572936037387468816,"clubName":"大荔县国球训练馆","userName":"王靖","role":"1"},{"id":572936037798510608,"clubName":"大荔县国球训练馆","userName":"王巍然","role":"2"},{"id":572936038071140368,"clubName":"大荔县国球训练馆","userName":"赵冠宇","role":"2"},{"id":572936038347964432,"clubName":"大荔县国球训练馆","userName":"夏涛","role":"2"}]
     * players : [{"id":572936038901612560,"clubName":"大荔县国球训练馆","userName":"马一涵","role":"3"},{"id":572936039732084752,"clubName":"大荔县国球训练馆","userName":"李奕江","role":"3"},{"id":572936040558362640,"clubName":"大荔县国球训练馆","userName":"马雨辰","role":"3"},{"id":572936041661464592,"clubName":"大荔县国球训练馆","userName":"马一涵","role":"3"},{"id":572936042487742480,"clubName":"大荔县国球训练馆","userName":"李奕江","role":"3"},{"id":572936043314020368,"clubName":"大荔县国球训练馆","userName":"马雨辰","role":"3"},{"id":572936044412928016,"clubName":"大荔县国球训练馆","userName":"毛星悦","role":"3"},{"id":572936045243400208,"clubName":"大荔县国球训练馆","userName":"何宇樂","role":"3"},{"id":572936046078066704,"clubName":"大荔县国球训练馆","userName":"邵新颖","role":"3"},{"id":572936046908538896,"clubName":"大荔县国球训练馆","userName":"王子倾","role":"3"},{"id":572936048015835152,"clubName":"大荔县国球训练馆","userName":"毛星悦","role":"3"},{"id":572936048842113040,"clubName":"大荔县国球训练馆","userName":"何宇樂","role":"3"},{"id":572936049668390928,"clubName":"大荔县国球训练馆","userName":"邵新颖","role":"3"},{"id":572936050628886544,"clubName":"大荔县国球训练馆","userName":"王子倾","role":"3"},{"id":572936051731988496,"clubName":"大荔县国球训练馆","userName":"杨博然","role":"3"},{"id":572936052562460688,"clubName":"大荔县国球训练馆","userName":"秦涵烁","role":"3"},{"id":572936053397127184,"clubName":"大荔县国球训练馆","userName":"高郁琛","role":"3"},{"id":572936054500229136,"clubName":"大荔县国球训练馆","userName":"杨博然","role":"3"},{"id":572936055326507024,"clubName":"大荔县国球训练馆","userName":"秦涵烁","role":"3"},{"id":572936056156979216,"clubName":"大荔县国球训练馆","userName":"高郁琛","role":"3"},{"id":572936057255886864,"clubName":"大荔县国球训练馆","userName":"刘馨怡雯","role":"3"},{"id":572936058086359056,"clubName":"大荔县国球训练馆","userName":"党睿琳","role":"3"},{"id":572936058916831248,"clubName":"大荔县国球训练馆","userName":"王奕阳","role":"3"},{"id":572936059747303440,"clubName":"大荔县国球训练馆","userName":"侯润茜","role":"3"},{"id":572936060854599696,"clubName":"大荔县国球训练馆","userName":"刘馨怡雯","role":"3"},{"id":572936061676683280,"clubName":"大荔县国球训练馆","userName":"党睿琳","role":"3"},{"id":572936062519738384,"clubName":"大荔县国球训练馆","userName":"王奕阳","role":"3"},{"id":572936063480234000,"clubName":"大荔县国球训练馆","userName":"侯润茜","role":"3"},{"id":572936064587530256,"clubName":"大荔县国球训练馆","userName":"刘恒宇","role":"3"},{"id":572936065418002448,"clubName":"大荔县国球训练馆","userName":"雷可聿","role":"3"},{"id":572936066248474640,"clubName":"大荔县国球训练馆","userName":"孙霁杨","role":"3"},{"id":572936067351576592,"clubName":"大荔县国球训练馆","userName":"马煜涵","role":"3"},{"id":572936068244963344,"clubName":"大荔县国球训练馆","userName":"夏文轩","role":"3"},{"id":572936069071241232,"clubName":"大荔县国球训练馆","userName":"何熙同","role":"3"},{"id":572936070174343184,"clubName":"大荔县国球训练馆","userName":"刘恒宇","role":"3"},{"id":572936071000621072,"clubName":"大荔县国球训练馆","userName":"雷可聿","role":"3"},{"id":572936071822704656,"clubName":"大荔县国球训练馆","userName":"马煜涵","role":"3"},{"id":572936072644788240,"clubName":"大荔县国球训练馆","userName":"孙霁杨","role":"3"},{"id":572936073466871824,"clubName":"大荔县国球训练馆","userName":"夏文轩","role":"3"},{"id":572936074293149712,"clubName":"大荔县国球训练馆","userName":"何熙同","role":"3"},{"id":572936075392057360,"clubName":"大荔县国球训练馆","userName":"苏科梁","role":"3"},{"id":572936076356747280,"clubName":"大荔县国球训练馆","userName":"王鑫泽","role":"3"},{"id":572936077187219472,"clubName":"大荔县国球训练馆","userName":"王麒涵","role":"3"},{"id":572936078017691664,"clubName":"大荔县国球训练馆","userName":"雷钊源","role":"3"},{"id":572936078848163856,"clubName":"大荔县国球训练馆","userName":"孙明泽","role":"3"},{"id":572936079955460112,"clubName":"大荔县国球训练馆","userName":"苏科梁","role":"3"},{"id":572936080777543696,"clubName":"大荔县国球训练馆","userName":"王鑫泽","role":"3"},{"id":572936081603821584,"clubName":"大荔县国球训练馆","userName":"王麒涵","role":"3"},{"id":572936082430099472,"clubName":"大荔县国球训练馆","userName":"雷钊源","role":"3"},{"id":572936083252183056,"clubName":"大荔县国球训练馆","userName":"孙明泽","role":"3"},{"id":572936084355285008,"clubName":"大荔县国球训练馆","userName":"车明轩","role":"3"},{"id":572936085181562896,"clubName":"大荔县国球训练馆","userName":"李陈酷","role":"3"},{"id":572936086020423696,"clubName":"大荔县国球训练馆","userName":"孙凡森","role":"3"},{"id":572936087127719952,"clubName":"大荔县国球训练馆","userName":"车明轩","role":"3"},{"id":572936087949803536,"clubName":"大荔县国球训练馆","userName":"李陈酷","role":"3"},{"id":572936088910299152,"clubName":"大荔县国球训练馆","userName":"孙凡森","role":"3"}]
     * clubName : 大荔县国球训练馆
     * personCount : 56
     */

    private String clubName;
    private int personCount;
    private List<LeaderBean> leader;
    private List<PlayersBean> players;

    public String getClubName() {
        return clubName == null ? "" : clubName;
    }

    public void setClubName(String clubName) {
        this.clubName = clubName;
    }

    public int getPersonCount() {
        return personCount;
    }

    public void setPersonCount(int personCount) {
        this.personCount = personCount;
    }

    public List<LeaderBean> getLeader() {
        if (leader == null) {
            return new ArrayList<>();
        }
        return leader;
    }

    public void setLeader(List<LeaderBean> leader) {
        this.leader = leader;
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

    public static class LeaderBean {
        /**
         * id : 572936037387468816
         * clubName : 大荔县国球训练馆
         * userName : 王靖
         * role : 1
         */

        private long id;
        private String clubName;
        private String userName;
        private String role;

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

        public String getUserName() {
            return userName == null ? "" : userName;
        }

        public void setUserName(String userName) {
            this.userName = userName;
        }

        public String getRole() {
            return role == null ? "" : role;
        }

        public void setRole(String role) {
            this.role = role;
        }
    }

    public static class PlayersBean {
        /**
         * id : 572936038901612560
         * clubName : 大荔县国球训练馆
         * userName : 马一涵
         * role : 3
         */

        private long id;
        private String clubName;
        private String userName;
        private String role;

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

        public String getUserName() {
            return userName == null ? "" : userName;
        }

        public void setUserName(String userName) {
            this.userName = userName;
        }

        public String getRole() {
            return role == null ? "" : role;
        }

        public void setRole(String role) {
            this.role = role;
        }
    }

}
