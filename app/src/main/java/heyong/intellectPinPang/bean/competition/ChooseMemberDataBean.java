package heyong.intellectPinPang.bean.competition;

import java.util.List;

public class ChooseMemberDataBean {


    /**
     * letter : K
     * members : [{"id":512936350908452880,"name":"柯南"}]
     */

    private String letter;
    private List<MembersBean> members;

    public String getLetter() {
        return letter;
    }

    public void setLetter(String letter) {
        this.letter = letter;
    }

    public List<MembersBean> getMembers() {
        return members;
    }

    public void setMembers(List<MembersBean> members) {
        this.members = members;
    }

    public static class MembersBean {
        /**
         * id : 512936350908452880
         * name : 柯南
         */

        private long id;
        private String name;

        public long getId() {
            return id;
        }

        public void setId(long id) {
            this.id = id;
        }

        public String getName() {
            return name == null ? "" : name;
        }

        public void setName(String name) {
            this.name = name;
        }


    }

}
