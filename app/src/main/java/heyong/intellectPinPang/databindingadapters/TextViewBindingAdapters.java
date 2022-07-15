//package heyong.intellectPinPang.databindingadapters;
//
//import android.graphics.Paint;
//import android.widget.TextView;
//import androidx.databinding.BindingAdapter;
//
//public class TextViewBindingAdapters {
//
//    @BindingAdapter(value = "app:strike")
//    public static void setStrike(TextView textView,boolean strike){
//        if (strike) {
//            textView.setPaintFlags(textView.getPaintFlags() | Paint.STRIKE_THRU_TEXT_FLAG);
//        }else{
//            textView.setPaintFlags(textView.getPaintFlags() | ~Paint.STRIKE_THRU_TEXT_FLAG);
//        }
//    }
//
//}
