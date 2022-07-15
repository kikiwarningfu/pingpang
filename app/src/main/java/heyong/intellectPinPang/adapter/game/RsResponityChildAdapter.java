package heyong.intellectPinPang.adapter.game;

import android.content.Context;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.view.ViewGroup;

import androidx.databinding.DataBindingUtil;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;

import heyong.intellectPinPang.R;
import heyong.intellectPinPang.bean.competition.BsAttrListBean;
import heyong.intellectPinPang.bean.competition.CreateXlClubContestInfoBean;
import heyong.intellectPinPang.databinding.ItemBsResponityChildBinding;

/**
 * Create On 2019/8/21 17:43
 * Copyrights 2019/8/21 handongkeji All rights reserved
 * ClassName:RsResponityChildAdapter
 * PackageName:com.hejiumao.app.business.mine.adapter
 * Site:http://www.handongkeji.com
 * Author: wanghongfu
 * Date: 2019/8/21 17:43
 * Description:
 */
public class RsResponityChildAdapter extends BaseQuickAdapter<CreateXlClubContestInfoBean.XlClubContestTeamsBean.ContestTeamMembersBean, BaseViewHolder> {
    private int size = 0;
    //    public HashMap<Integer, String> contents = new HashMap<>();
    private int width;

    public void setWidth(int width) {
        this.width = width;

    }

    public RsResponityChildAdapter(int sizes) {
        super(R.layout.item_bs_responity_child);
        this.size = sizes;
    }


    @Override
    protected void convert(BaseViewHolder helper, CreateXlClubContestInfoBean.XlClubContestTeamsBean.ContestTeamMembersBean item) {
        helper.setText(R.id.tv_name, item.getUserName());
        ItemBsResponityChildBinding binding = DataBindingUtil.bind(helper.getConvertView());
        boolean close = item.isClose();
        if (close) {
            helper.addOnClickListener(R.id.iv_close_blue);
            binding.ivCloseBlue.setVisibility(View.VISIBLE);
        } else {
            binding.ivCloseBlue.setVisibility(View.GONE);
        }
//        ViewGroup.LayoutParams layoutParams = binding.tvName.getLayoutParams();
//        layoutParams.height = width / 4;
//        binding.tvName.setLayoutParams(layoutParams);
    }

    public class MyTextChangedListener implements TextWatcher {

        public BaseViewHolder holder;
        public BsAttrListBean contents;

        public MyTextChangedListener(BaseViewHolder holder, BsAttrListBean contents) {
            this.holder = holder;
            this.contents = contents;
        }

        @Override
        public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

        }

        @Override
        public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

        }

        @Override
        public void afterTextChanged(Editable editable) {
            if (holder != null && contents != null) {
                int adapterPosition = holder.getAdapterPosition();
//                    LogUtil.d(TAG,"adapterPosition==================="+adapterPosition);
                contents.setEtContgent(editable.toString());
            }
        }
    }
}
