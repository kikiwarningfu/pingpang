package heyong.intellectPinPang.adapter.mine;

import android.graphics.Color;
import android.graphics.drawable.GradientDrawable;
import android.view.View;

import androidx.databinding.DataBindingUtil;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.chad.library.adapter.base.util.MultiTypeDelegate;

import heyong.handong.framework.utils.ImageLoader;
import heyong.intellectPinPang.R;
import heyong.intellectPinPang.bean.competition.EventMessageBean;
import heyong.intellectPinPang.bean.competition.TitleListBean;
import heyong.intellectPinPang.databinding.ItemMessageApplyClubBinding;
import heyong.intellectPinPang.databinding.ItemMessageHaveButtonBinding;
import heyong.intellectPinPang.databinding.ItemMessageNoButtonBinding;
import heyong.intellectPinPang.databinding.ItemMessageSeeResultBinding;

public class EventMessageAdapter extends BaseQuickAdapter<TitleListBean.ListBean, BaseViewHolder> {
    public EventMessageAdapter() {

        super(0);
        MultiTypeDelegate<TitleListBean.ListBean> multiTypeDelegate = new MultiTypeDelegate<TitleListBean.ListBean>() {
            @Override
            protected int getItemType(TitleListBean.ListBean o) {
//                Log.e(TAG, "getItemType: type==="+o.getType());
                return Integer.parseInt(o.getCategory());
            }
        };
//        0   没有按钮
//        1   查看比赛结果  跳转到确认比赛成绩  要区分  运动员     教练员 身份
//        2   填写对阵名单          跳转到主客队抽签界面  （什么时候是灰色  超时  后台加一个状态）
//        3   申请加入俱乐部
        multiTypeDelegate.registerItemType(0, R.layout.item_message_no_button);//没有按钮
        multiTypeDelegate.registerItemType(1, R.layout.item_message_apply_club);//申请加入俱乐部
        multiTypeDelegate.registerItemType(2, R.layout.item_message_see_result);//查看比赛结果   运动员评分
        multiTypeDelegate.registerItemType(3, R.layout.item_message_have_button);//填写对阵名单
        multiTypeDelegate.registerItemType(4, R.layout.item_message_see_result);//查看比赛结果   教练员评分
        setMultiTypeDelegate(multiTypeDelegate);
    }

    @Override
    protected void convert(BaseViewHolder helper, TitleListBean.ListBean item) {
        //0是未读   1 是已读
        int status = Integer.parseInt(item.getStatus());
        switch (helper.getItemViewType()) {
            case 0://没有按钮
                ItemMessageNoButtonBinding bindingNoButton = DataBindingUtil.bind(helper.getConvertView());
                bindingNoButton.tvContent.setText("" + item.getMessage());
                bindingNoButton.tvTime.setText("" + item.getUpdateTime());
                bindingNoButton.tvTitle.setText("" + item.getTitle());
                GradientDrawable gd = (GradientDrawable) bindingNoButton.tvTitle.getBackground();
                if(gd!=null) {
                    gd.setColor(Color.parseColor("#333333"));
                }
                break;
            case 1://申请加入俱乐部
                ItemMessageApplyClubBinding bindingApply = DataBindingUtil.bind(helper.getConvertView());
                bindingApply.tvTime.setText("" + item.getUpdateTime());
                bindingApply.tvTitle.setText("" + item.getTitle());
                bindingApply.tvNickName.setText("" + item.getNickName());
                bindingApply.tvName.setText("" + item.getName());

                ImageLoader.loadImage(bindingApply.ivLogo, item.getHeadImg(), R.drawable.img_default_avatar, R.drawable.img_default_avatar);
                if (item.getSex().equals("2")) {
                    ImageLoader.loadImage(bindingApply.ivSex, R.drawable.img_blue_woman);
                } else {
                    ImageLoader.loadImage(bindingApply.ivSex, R.drawable.img_sex_little_blue);
                }
                helper.addOnClickListener(R.id.ll_argee_with);
                helper.addOnClickListener(R.id.ll_refuse);
                bindingApply.tvContent.setText("申请加入俱乐部");
                if (item.getMessage().equals("申请加入俱乐部")) {
                    bindingApply.tvStatus.setText("");
                } else {
                    if(item.getMessage().contains("已通过"))
                    {
                        bindingApply.tvStatus.setText("已通过");
                    }else {
                        bindingApply.tvStatus.setText("" + item.getMessage());
                    }

                }
                //申请加入俱乐部
                if (status == 1)//已读 隐藏按钮
                {
                    bindingApply.rlContainer.setVisibility(View.GONE);
                } else   //显示按钮
                {
                    bindingApply.rlContainer.setVisibility(View.VISIBLE);
                }
                break;

            case 2://查看比赛结果  运动员评分
            case 4://查看比赛结果   教练员评分
                ItemMessageSeeResultBinding bindingResult = DataBindingUtil.bind(helper.getConvertView());
                bindingResult.tvContent.setText("" + item.getMessage());
                bindingResult.tvTime.setText("" + item.getUpdateTime());
                bindingResult.tvTitle.setText("" + item.getTitle());
//                if (status == 0) {
                helper.addOnClickListener(R.id.tv_see_result);//查看比赛结果
//                bindingResult.tvSeeResult.setBackgroundResource(R.drawable.shape_club_blue);
//                bindingResult.tvSeeResult.setTextColor(Color.parseColor("#FFFFFF"));
//                } else {
//                    bindingResult.tvSeeResult.setBackgroundResource(R.drawable.shape_club_gray);
//                    bindingResult.tvSeeResult.setTextColor(Color.parseColor("#FFFFFF"));
//                }
                break;
            case 3://填写对阵名单
                ItemMessageHaveButtonBinding bindingButton = DataBindingUtil.bind(helper.getConvertView());
                bindingButton.tvContent.setText("" + item.getMessage());
                bindingButton.tvTime.setText("" + item.getUpdateTime());
                bindingButton.tvTitle.setText("" + item.getTitle());
                //填写对阵名单  需要加蓝色按钮的判断
                if (status == 0) {
                    helper.addOnClickListener(R.id.tv_fill_information);//填写对阵单
                    bindingButton.tvFillInformation.setBackgroundResource(R.drawable.shape_club_blue);
                    bindingButton.tvFillInformation.setTextColor(Color.parseColor("#FFFFFF"));
                } else {
                    bindingButton.tvFillInformation.setBackgroundResource(R.drawable.shape_club_gray);
                    bindingButton.tvFillInformation.setTextColor(Color.parseColor("#FFFFFF"));
                }
                break;


        }
    }


}
