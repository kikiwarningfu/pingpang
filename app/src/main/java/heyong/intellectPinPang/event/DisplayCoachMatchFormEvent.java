package heyong.intellectPinPang.event;

public class DisplayCoachMatchFormEvent {
    private int inputType;
    private String jsonStr;

    public DisplayCoachMatchFormEvent() {
    }

    public DisplayCoachMatchFormEvent(int inputType, String jsonStr) {
        this.inputType = inputType;
        this.jsonStr = jsonStr;
    }

    public int getInputType() {
        return inputType;
    }

    public void setInputType(int inputType) {
        this.inputType = inputType;
    }

    public String getJsonStr() {
        return jsonStr == null ? "" : jsonStr;
    }

    public void setJsonStr(String jsonStr) {
        this.jsonStr = jsonStr;
    }
}
