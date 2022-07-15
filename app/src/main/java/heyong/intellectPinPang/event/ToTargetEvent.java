package heyong.intellectPinPang.event;

public class ToTargetEvent {
    private long id;
    private int adapterPosition;
    public ToTargetEvent(long id, int adapterPosition) {
        this.id=id;
        this.adapterPosition=adapterPosition;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public int getAdapterPosition() {
        return adapterPosition;
    }

    public void setAdapterPosition(int adapterPosition) {
        this.adapterPosition = adapterPosition;
    }
}
