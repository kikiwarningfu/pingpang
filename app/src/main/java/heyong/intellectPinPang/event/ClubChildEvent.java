package heyong.intellectPinPang.event;

public class ClubChildEvent {
    private String etClubName;
    public ClubChildEvent(String etClubName) {
        this.etClubName=etClubName;
    }
    public String getEtClubName() {
        return etClubName == null ? "" : etClubName;
    }

    public void setEtClubName(String etClubName) {
        this.etClubName = etClubName;
    }
}
