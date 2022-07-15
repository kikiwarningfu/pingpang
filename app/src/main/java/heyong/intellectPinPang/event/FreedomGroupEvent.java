package heyong.intellectPinPang.event;

public class FreedomGroupEvent {
    private String etClubName;
    public FreedomGroupEvent(String etClubName) {
        this.etClubName=etClubName;
    }
    public String getEtClubName() {
        return etClubName == null ? "" : etClubName;
    }

    public void setEtClubName(String etClubName) {
        this.etClubName = etClubName;
    }
}
