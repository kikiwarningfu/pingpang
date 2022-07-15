package heyong.intellectPinPang.event;

public class SportsAcademyEmptyEvent {
    private String etClubName;

    public SportsAcademyEmptyEvent(String etClubName) {
        this.etClubName = etClubName;
    }

    public String getEtClubName() {
        return etClubName == null ? "" : etClubName;
    }

    public void setEtClubName(String etClubName) {
        this.etClubName = etClubName;
    }
}
