package heyong.intellectPinPang.ui.friendcircle.fragment;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import heyong.intellectPinPang.R;


/**
 * Created by zejian
 * Time  16/1/7 上午10:26
 * Email shinezejian@163.com
 * Description:
 */
public class FragmentNormal extends EmtionBaseFragment {
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View rootView=LayoutInflater.from(getActivity()).inflate(R.layout.fragment_normal,null);
        TextView tv= (TextView) rootView.findViewById(R.id.tv);

        tv.setText(args.getString("Interge"));
        return rootView ;
    }
}
