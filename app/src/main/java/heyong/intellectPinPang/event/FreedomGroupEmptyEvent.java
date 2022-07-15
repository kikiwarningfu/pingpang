package heyong.intellectPinPang.event;

public class FreedomGroupEmptyEvent {
    private String etClubName;
    public FreedomGroupEmptyEvent(String etClubName) {
        this.etClubName=etClubName;
    }
    public String getEtClubName() {
        return etClubName == null ? "" : etClubName;
    }

    public void setEtClubName(String etClubName) {
        this.etClubName = etClubName;
    }
}
