package heyong.intellectPinPang.bean.competition;

import java.util.ArrayList;
import java.util.List;

public class ReckonInfoDataBean {


    /**
     * arrange : 柯南:茅十八
     * result : null
     * chess : ["11:0","22:0","0:0","33:0"]
     */

    private String arrange;
    private String result;
    private List<String> chess;

    public String getArrange() {
        return arrange == null ? "" : arrange;
    }

    public void setArrange(String arrange) {
        this.arrange = arrange;
    }

    public String getResult() {
        return result == null ? "" : result;
    }

    public void setResult(String result) {
        this.result = result;
    }

    public List<String> getChess() {
        if (chess == null) {
            return new ArrayList<>();
        }
        return chess;
    }

    public void setChess(List<String> chess) {
        this.chess = chess;
    }
}
