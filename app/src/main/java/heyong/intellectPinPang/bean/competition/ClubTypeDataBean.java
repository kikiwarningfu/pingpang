package heyong.intellectPinPang.bean.competition;

import java.io.Serializable;
import java.util.List;

public class ClubTypeDataBean implements Serializable {


        /**
         * code : 1
         * text : 自由团体
         */

        private String code;
        private String text;

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
}
