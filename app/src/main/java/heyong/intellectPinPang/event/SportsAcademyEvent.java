package heyong.intellectPinPang.event;

public class SportsAcademyEvent {
    private String etClubName;
    public SportsAcademyEvent(String etClubName) {
        this.etClubName=etClubName;
    }
    public String getEtClubName() {
        return etClubName == null ? "" : etClubName;
    }

    public void setEtClubName(String etClubName) {
        this.etClubName = etClubName;
    }
}
