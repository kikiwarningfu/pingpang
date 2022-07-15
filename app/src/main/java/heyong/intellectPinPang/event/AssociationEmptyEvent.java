package heyong.intellectPinPang.event;

public class AssociationEmptyEvent {
    private String etClubName;
    public AssociationEmptyEvent(String etClubName) {
        this.etClubName=etClubName;
    }
    public String getEtClubName() {
        return etClubName == null ? "" : etClubName;
    }

    public void setEtClubName(String etClubName) {
        this.etClubName = etClubName;
    }
}
