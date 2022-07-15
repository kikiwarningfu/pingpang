package heyong.intellectPinPang.adapter.game;

import android.content.Context;

import androidx.databinding.DataBindingUtil;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;

import java.util.ArrayList;
import java.util.List;

import heyong.intellectPinPang.R;
import heyong.intellectPinPang.bean.competition.CalculationInfoBean;
import heyong.intellectPinPang.bean.competition.SimpleCalculationInfoBean;
import heyong.intellectPinPang.databinding.ItemIntegralCalculateBinding;

/**
 * 积分计算的适配器
 */
public class IntegralCalculateAdapter extends BaseQuickAdapter<String, BaseViewHolder> {
    private Context context;
    List<CalculationInfoBean.FirstBean> first;
    List<CalculationInfoBean.SecondBean> second;

    public IntegralCalculateAdapter(Context context) {
        super(R.layout.item_integral_calculate);
        this.context = context;
    }

    public void setDatas(List<CalculationInfoBean.FirstBean> first, List<CalculationInfoBean.SecondBean> second) {
        this.first = first;
        this.second = second;
        notifyDataSetChanged();
    }

    @Override
    protected void convert(BaseViewHolder helper, String item) {
        ItemIntegralCalculateBinding binding = DataBindingUtil.bind(helper.getConvertView());
        RecyclerView rvCalculateView = helper.getView(R.id.rv_list_middle);
        rvCalculateView.setLayoutManager(new LinearLayoutManager(context, RecyclerView.VERTICAL, false));
        IntegrakCalculateMiddleAdapter integralCalculateItemAdapter = new IntegrakCalculateMiddleAdapter(context);
        rvCalculateView.setAdapter(integralCalculateItemAdapter);
        if (helper.getAdapterPosition() == 0) {
            binding.tvJisuanLevel.setText("一级计算");
            if (first != null && first.size() > 0) {
                List<SimpleCalculationInfoBean> dataList1 = new ArrayList<>();
                for (int j = 0; j < first.size(); j++) {
                    SimpleCalculationInfoBean simpleCalculationInfoBean = new SimpleCalculationInfoBean();
                    List<CalculationInfoBean.FirstBean.ListInfoBean> listInfo = first.get(j).getListInfo();
                    List<SimpleCalculationInfoBean.ListInfoBean> listInfo1 = simpleCalculationInfoBean.getListInfo();
                    listInfo1.clear();
                    for (int i = 0; i < listInfo.size(); i++) {
                        if (i == listInfo.size() - 1) {
                            if(listInfo.get(i).getLoseCount()!=0) {
                                listInfo1.add(new SimpleCalculationInfoBean.ListInfoBean("" + listInfo.get(i).getName(),
                                        listInfo.get(i).getWinCount(), listInfo.get(i).getLoseCount()
                                        , "" + String.format("%.2f", (Double.parseDouble(String.valueOf(listInfo.get(i).getWinCount())))
                                        / (Double.parseDouble(String.valueOf(listInfo.get(i).getLoseCount()))))));
                            }else
                            {
                                listInfo1.add(new SimpleCalculationInfoBean.ListInfoBean("" + listInfo.get(i).getName(),
                                        listInfo.get(i).getWinCount(), listInfo.get(i).getLoseCount()
                                        , "0"));
                            }

                        } else {
                            listInfo1.add(new SimpleCalculationInfoBean.ListInfoBean("" + listInfo.get(i).getName(),
                                    listInfo.get(i).getWinCount(), listInfo.get(i).getLoseCount()
                                    , "" + ""));
                        }
                    }
                    simpleCalculationInfoBean.setListInfo(listInfo1);
                    simpleCalculationInfoBean.setName(first.get(j).getName());
                    simpleCalculationInfoBean.setRanking(first.get(j).getRanking());
                    simpleCalculationInfoBean.setResult(first.get(j).getResult());
                    simpleCalculationInfoBean.setUserId(first.get(j).getUserId());
                    dataList1.add(simpleCalculationInfoBean);
                }
                integralCalculateItemAdapter.setNewData(dataList1);
            }
        } else if (helper.getAdapterPosition() == 1) {
            binding.tvJisuanLevel.setText("二级计算");
            if (second != null && second.size() > 0) {
                List<SimpleCalculationInfoBean> dataList1 = new ArrayList<>();
                for (int j = 0; j < second.size(); j++) {
                    SimpleCalculationInfoBean simpleCalculationInfoBean = new SimpleCalculationInfoBean();
                    List<CalculationInfoBean.SecondBean.ListInfoBeanX> listInfo = second.get(j).getListInfo();
                    List<SimpleCalculationInfoBean.ListInfoBean> listInfo1 = simpleCalculationInfoBean.getListInfo();
                    listInfo1.clear();
                    for (int i = 0; i < listInfo.size(); i++) {
                        if (i == listInfo.size() - 1) {
                            listInfo1.add(new SimpleCalculationInfoBean.ListInfoBean("" + listInfo.get(i).getName(),
                                    listInfo.get(i).getWinCount(), listInfo.get(i).getLoseCount(),
                                    "" + second.get(j).getResult())
                            );
                        } else {
                            listInfo1.add(new SimpleCalculationInfoBean.ListInfoBean("" + listInfo.get(i).getName(),
                                    listInfo.get(i).getWinCount(), listInfo.get(i).getLoseCount(),
                                    "")
                            );
                        }
                    }
                    simpleCalculationInfoBean.setListInfo(listInfo1);
                    simpleCalculationInfoBean.setName(second.get(j).getName());
                    simpleCalculationInfoBean.setRanking(second.get(j).getRanking());
                    simpleCalculationInfoBean.setResult(second.get(j).getResult());
                    simpleCalculationInfoBean.setUserId(second.get(j).getUserId());
                    dataList1.add(simpleCalculationInfoBean);
                }
                integralCalculateItemAdapter.setNewData(dataList1);
            }

        }


    }
}
