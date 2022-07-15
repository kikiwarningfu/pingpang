package heyong.intellectPinPang.bean.competition;

import java.util.List;

public class RefreerConsoleSortBean {


    /**
     * matchId : 512317538144129040
     * status : 2
     * timeSort : [{"time":1610380800000,"timeName":"今日"},{"time":1610467200000,"timeName":"1月13日"}]
     * timeSlot : [{"code":"1","text":"全天"},{"code":"2","text":"上午"},{"code":"3","text":"下午"}]
     * item : [{"itemType":"1","itemTypeName":"团体"}]
     */
    private String year;

    private long matchId;
    private String status;
    private List<TimeSortBean> timeSort;
    private List<TimeSlotBean> timeSlot;
    private List<ItemBean> item;

    public String getYear() {
        return year == null ? "" : year;
    }

    public void setYear(String year) {
        this.year = year;
    }

    public long getMatchId() {
        return matchId;
    }

    public void setMatchId(long matchId) {
        this.matchId = matchId;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public List<TimeSortBean> getTimeSort() {
        return timeSort;
    }

    public void setTimeSort(List<TimeSortBean> timeSort) {
        this.timeSort = timeSort;
    }

    public List<TimeSlotBean> getTimeSlot() {
        return timeSlot;
    }

    public void setTimeSlot(List<TimeSlotBean> timeSlot) {
        this.timeSlot = timeSlot;
    }

    public List<ItemBean> getItem() {
        return item;
    }

    public void setItem(List<ItemBean> item) {
        this.item = item;
    }

    public static class TimeSortBean {
        /**
         * time : 1610380800000
         * timeName : 今日
         */
        private boolean isSelect;
        private String time;
        private String timeName;

        public String getTime() {
            return time == null ? "" : time;
        }

        public void setTime(String time) {
            this.time = time;
        }

        public String getTimeName() {
            return timeName;
        }

        public void setTimeName(String timeName) {
            this.timeName = timeName;
        }

        public boolean isSelect() {
            return isSelect;
        }

        public void setSelect(boolean select) {
            isSelect = select;
        }
    }

    public static class TimeSlotBean {
        /**
         * code : 1
         * text : 全天
         */

        private String code;
        private String text;
        private boolean isSelect;

        public String getCode() {
            return code == null ? "" : code;
        }

        public void setCode(String code) {
            this.code = code;
        }

        public String getText() {
            return text == null ? "" : text;
        }

        public void setText(String text) {
            this.text = text;
        }

        public boolean isSelect() {
            return isSelect;
        }

        public void setSelect(boolean select) {
            isSelect = select;
        }
    }

    public static class ItemBean {
        /**
         * itemType : 1
         * itemTypeName : 团体
         */
        private boolean isSelect;
        private String itemType;
        private String itemTypeName;

        public boolean isSelect() {
            return isSelect;
        }

        public void setSelect(boolean select) {
            isSelect = select;
        }

        public String getItemType() {
            return itemType == null ? "" : itemType;
        }

        public void setItemType(String itemType) {
            this.itemType = itemType;
        }

        public String getItemTypeName() {
            return itemTypeName == null ? "" : itemTypeName;
        }

        public void setItemTypeName(String itemTypeName) {
            this.itemTypeName = itemTypeName;
        }
    }

}
