package heyong.handong.framework.dialog;

import android.os.Bundle;
import android.view.View;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.databinding.DataBindingUtil;
import com.handongkeji.framework.R;
import com.handongkeji.framework.databinding.DialogOptionsLayoutBinding;
import com.handongkeji.framework.databinding.ItemOptionsLayoutBinding;

import java.util.ArrayList;
import java.util.List;

public class OptionsDialog<T> extends BottomDialog<DialogOptionsLayoutBinding> {

    private DialogOptionsLayoutBinding binding;
    private List<Options<T>> list;
    private boolean showCancel;

    @Override
    protected int getLayoutRes() {
        return R.layout.dialog_options_layout;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        binding.setShowCancel(showCancel);
        if (showCancel) {
            binding.btnCancel.setOnClickListener(v -> dismissAllowingStateLoss());
        }
        if (list == null || list.isEmpty()) {
            return;
        }
        for (Options<T> tOptions : list) {
            ItemOptionsLayoutBinding itemBinding = DataBindingUtil.inflate(getLayoutInflater(), R.layout.item_options_layout, binding.container, false);
            binding.container.addView(itemBinding.getRoot());
            itemBinding.textView.setText(tOptions.t.toString());
            itemBinding.textView.setOnClickListener(v -> {
                if (listener != null) {
                    listener.onOptionSelected(v, tOptions.t, tOptions.index);
                }
                dismissAllowingStateLoss();
            });
        }

    }

    public static class Builder<T> {
        private List<Options<T>> list;
        private OnOptionSelectedListener<T> listener;
        private boolean showCancel = true;  //  是否显示取消按钮，默认显示

        public Builder<T> setData(List<Options<T>> list) {
            this.list = list;
            return this;
        }

        public Builder<T> setData(T... array) {
            this.list = new ArrayList<>();
            for (int i = 0; i < array.length; i++) {
                list.add(new Options<>(array[i], i));
            }
            return this;
        }

        public Builder<T> setListener(OnOptionSelectedListener<T> listener) {
            this.listener = listener;
            return this;
        }

        public Builder<T> showCancel(boolean showCancel) {
            this.showCancel = showCancel;
            return this;
        }

        public OptionsDialog<T> build() {
            OptionsDialog dialog = new OptionsDialog();
            dialog.list = list;
            dialog.listener = listener;
            dialog.showCancel = showCancel;
            return dialog;
        }

    }

    private OnOptionSelectedListener<T> listener;

    public void setListener(OnOptionSelectedListener<T> listener) {
        this.listener = listener;
    }

    public interface OnOptionSelectedListener<T> {
        void onOptionSelected(View view, T t, int index);
    }

    public static class Options<T> {
        private T t;
        private int index;

        public T getT() {
            return t;
        }

        public void setT(T t) {
            this.t = t;
        }

        public Options(T t, int index) {
            this.t = t;
            this.index = index;
        }
    }
}
