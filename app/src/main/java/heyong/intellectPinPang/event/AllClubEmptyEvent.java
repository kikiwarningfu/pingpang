package heyong.intellectPinPang.event;

public class AllClubEmptyEvent {
    private String etClubName;

    public AllClubEmptyEvent(String etClubName) {
        this.etClubName = etClubName;
    }

    public String getEtClubName() {
        return etClubName == null ? "" : etClubName;
    }

    public void setEtClubName(String etClubName) {
        this.etClubName = etClubName;
    }
}
