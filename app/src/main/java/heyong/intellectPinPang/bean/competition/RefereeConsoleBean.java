package heyong.intellectPinPang.bean.competition;

import java.io.Serializable;
import java.util.List;

public class RefereeConsoleBean {


    /**
     * tableNum : 1
     * allCount : 3
     * doneCount : 2
     * inHandCount : 1
     * noStartCount : 1
     * refereeId : 541940214542719616
     * refereeName : 吴京
     * refereeImg : http://images.xlttsports.com/android_img_20210204111722103
     * inHandDto : {"id":577191101040317616,"fraction":"3:0","itemType":"1"}
     */

    private String tableNum;
    private int allCount;
    private int doneCount;
    private int inHandCount;
    private int noStartCount;
    private String refereeId;
    private String refereeName;
    private String refereeImg;
    private InHandDtoBean inHandDto;

    public String getTableNum() {
        return tableNum == null ? "" : tableNum;
    }

    public void setTableNum(String tableNum) {
        this.tableNum = tableNum;
    }

    public int getAllCount() {
        return allCount;
    }

    public void setAllCount(int allCount) {
        this.allCount = allCount;
    }

    public int getDoneCount() {
        return doneCount;
    }

    public void setDoneCount(int doneCount) {
        this.doneCount = doneCount;
    }

    public int getInHandCount() {
        return inHandCount;
    }

    public void setInHandCount(int inHandCount) {
        this.inHandCount = inHandCount;
    }

    public int getNoStartCount() {
        return noStartCount;
    }

    public void setNoStartCount(int noStartCount) {
        this.noStartCount = noStartCount;
    }

    public String getRefereeId() {
        return refereeId == null ? "" : refereeId;
    }

    public void setRefereeId(String refereeId) {
        this.refereeId = refereeId;
    }

    public String getRefereeName() {
        return refereeName == null ? "" : refereeName;
    }

    public void setRefereeName(String refereeName) {
        this.refereeName = refereeName;
    }

    public String getRefereeImg() {
        return refereeImg == null ? "" : refereeImg;
    }

    public void setRefereeImg(String refereeImg) {
        this.refereeImg = refereeImg;
    }

    public InHandDtoBean getInHandDto() {
        return inHandDto;
    }

    public void setInHandDto(InHandDtoBean inHandDto) {
        this.inHandDto = inHandDto;
    }

    public static class InHandDtoBean implements Serializable {
        /**
         * id : 577191101040317616
         * fraction : 3:0
         * itemType : 1
         */

        private long id;
        private String fraction;
        private String itemType;

        public long getId() {
            return id;
        }

        public void setId(long id) {
            this.id = id;
        }

        public String getFraction() {
            return fraction == null ? "" : fraction;
        }

        public void setFraction(String fraction) {
            this.fraction = fraction;
        }

        public String getItemType() {
            return itemType == null ? "" : itemType;
        }

        public void setItemType(String itemType) {
            this.itemType = itemType;
        }
    }

}
