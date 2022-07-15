package heyong.intellectPinPang.bean.competition;

import java.util.List;

public class MyAchievementBean {


        /**
         * counts : 1
         * projectItem : 1
         * ranks : [{"ranking":1,"counts":1},{"ranking":2,"counts":0},{"ranking":3,"counts":0},{"ranking":4,"counts":0},{"ranking":5,"counts":0},{"ranking":6,"counts":0},{"ranking":7,"counts":0},{"ranking":8,"counts":0}]
         */

        private int counts;
        private String projectItem;
        private List<RanksBean> ranks;

        public int getCounts() {
            return counts;
        }

        public void setCounts(int counts) {
            this.counts = counts;
        }

        public String getProjectItem() {
            return projectItem;
        }

        public void setProjectItem(String projectItem) {
            this.projectItem = projectItem;
        }

        public List<RanksBean> getRanks() {
            return ranks;
        }

        public void setRanks(List<RanksBean> ranks) {
            this.ranks = ranks;
        }

        public static class RanksBean {
            /**
             * ranking : 1
             * counts : 1
             */

            private int ranking;
            private int counts;

            public int getRanking() {
                return ranking;
            }

            public void setRanking(int ranking) {
                this.ranking = ranking;
            }

            public int getCounts() {
                return counts;
            }

            public void setCounts(int counts) {
                this.counts = counts;
            }
        }

}
