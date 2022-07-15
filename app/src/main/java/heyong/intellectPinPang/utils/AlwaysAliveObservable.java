package heyong.intellectPinPang.utils;


import androidx.databinding.ObservableBoolean;

public class AlwaysAliveObservable extends ObservableBoolean {


    public void update(){
        notifyChange();
    }

    @Override
    public boolean get() {
        return true;
    }

    @Override
    public void set(boolean value) {
//        super.set(value);
    }
}
