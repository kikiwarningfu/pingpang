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
import heyong.intellectPinPang.bean.competition.QueryComputeInfoBean;
import heyong.intellectPinPang.bean.competition.SimpleCalculationInfoBean;
import heyong.intellectPinPang.databinding.ItemIntegralCalculateBinding;

/**
 * 积分计算的适配器
 */
public class IntegralCalculateOfficialAdapter extends BaseQuickAdapter<String, BaseViewHolder> {
    private Context context;
    List<QueryComputeInfoBean.FirstBean> first;
    List<QueryComputeInfoBean.SecondBean> second;
    private String itemType = "";  //1 团体 2 单打  3 双打   4 混双（和业余双打一样）
    int mItemType;

    public IntegralCalculateOfficialAdapter(Context context, String itemType) {
        super(R.layout.item_integral_calculate);
        this.context = context;
        this.itemType = itemType;
    }

    public void setDatas(List<QueryComputeInfoBean.FirstBean> first, List<QueryComputeInfoBean.SecondBean> second) {
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
        try {
            mItemType = Integer.parseInt(itemType);
        } catch (Exception e) {
            mItemType = 1;
        }


        if (helper.getAdapterPosition() == 0) {
            binding.tvJisuanLevel.setText("一级计算");
            if (first != null && first.size() > 0) {
                List<SimpleCalculationInfoBean> dataList1 = new ArrayList<>();
                for (int j = 0; j < first.size(); j++) {
                    SimpleCalculationInfoBean simpleCalculationInfoBean = new SimpleCalculationInfoBean();
                    List<QueryComputeInfoBean.FirstBean.ListInfoBean> listInfo = first.get(j).getListInfo();
                    List<SimpleCalculationInfoBean.ListInfoBean> listInfo1 = simpleCalculationInfoBean.getListInfo();
                    listInfo1.clear();
                    for (int i = 0; i < listInfo.size(); i++) {
                        if (mItemType == 1 || mItemType == 2) {
                            if (i == listInfo.size() - 1) {
                                if (listInfo.get(i).getLoseCount() != 0) {
                                    listInfo1.add(new SimpleCalculationInfoBean.ListInfoBean("" + listInfo.get(i).getPlayer1(),
                                            listInfo.get(i).getWinCount(), listInfo.get(i).getLoseCount()
                                            , "" + String.format("%.2f", (Double.parseDouble(String.valueOf(listInfo.get(i).getWinCount()))) / (Double.parseDouble(String.valueOf(listInfo.get(i).getLoseCount()))))));
                                } else {
                                    listInfo1.add(new SimpleCalculationInfoBean.ListInfoBean("" + listInfo.get(i).getPlayer1(),
                                            listInfo.get(i).getWinCount(), listInfo.get(i).getLoseCount()
                                            , ""+String.format("%.2f", (Double.parseDouble(String.valueOf(listInfo.get(i).getWinCount()))) / (Double.parseDouble(String.valueOf(1))))));
                                }
                            } else {
                                listInfo1.add(new SimpleCalculationInfoBean.ListInfoBean("" + listInfo.get(i).getPlayer1(),
                                        listInfo.get(i).getWinCount(), listInfo.get(i).getLoseCount()
                                        , "" + ""));
                            }

                        } else {
                            if (i == listInfo.size() - 1) {
                                listInfo1.add(new SimpleCalculationInfoBean.ListInfoBean("" + listInfo.get(i).getPlayer1() + "\n" + listInfo.get(i).getPlayer2(),
                                        listInfo.get(i).getWinCount(), listInfo.get(i).getLoseCount()
                                        , "" + String.format("%.2f", (Double.parseDouble(String.valueOf(listInfo.get(i).getWinCount()))) / (Double.parseDouble(String.valueOf(listInfo.get(i).getLoseCount()))))));
                            } else {
                                listInfo1.add(new SimpleCalculationInfoBean.ListInfoBean("" + listInfo.get(i).getPlayer1() + "\n" + listInfo.get(i).getPlayer2(),
                                        listInfo.get(i).getWinCount(), listInfo.get(i).getLoseCount()
                                        , ""));
                            }
                        }
                    }
                    simpleCalculationInfoBean.setListInfo(listInfo1);
                    if (mItemType == 1 || mItemType == 2) {
                        simpleCalculationInfoBean.setName(first.get(j).getPlayer1());
                    } else {
                        simpleCalculationInfoBean.setName(first.get(j).getPlayer1() + "\n" + first.get(j).getPlayer2());
                    }
                    simpleCalculationInfoBean.setRanking(first.get(j).getRanking());
                    simpleCalculationInfoBean.setResult(first.get(j).getResult());
                    simpleCalculationInfoBean.setUserId(first.get(j).getJoinId());
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
                    List<QueryComputeInfoBean.SecondBean.ListInfoBeanX> listInfo = second.get(j).getListInfo();
                    List<SimpleCalculationInfoBean.ListInfoBean> listInfo1 = simpleCalculationInfoBean.getListInfo();
                    listInfo1.clear();
                    for (int i = 0; i < listInfo.size(); i++) {
                        if (mItemType == 1 || mItemType == 2) {
                            if (i == listInfo.size() - 1) {
                                listInfo1.add(new SimpleCalculationInfoBean.ListInfoBean("" + listInfo.get(i).getPlayer1(),
                                        listInfo.get(i).getWinCount(), listInfo.get(i).getLoseCount(),
                                        "" + second.get(j).getResult())
                                );
                            } else {
                                listInfo1.add(new SimpleCalculationInfoBean.ListInfoBean("" + listInfo.get(i).getPlayer1(),
                                        listInfo.get(i).getWinCount(), listInfo.get(i).getLoseCount(),
                                        "")
                                );
                            }

                        } else {
                            if (i == listInfo.size() - 1) {
                                listInfo1.add(new SimpleCalculationInfoBean.ListInfoBean("" + listInfo.get(i).getPlayer1() + "\n" + listInfo.get(i).getPlayer2(),
                                        listInfo.get(i).getWinCount(), listInfo.get(i).getLoseCount(),
                                        "" + second.get(j).getResult())
                                );
                            } else {
                                listInfo1.add(new SimpleCalculationInfoBean.ListInfoBean("" + listInfo.get(i).getPlayer1() + "\n" + listInfo.get(i).getPlayer2(),
                                        listInfo.get(i).getWinCount(), listInfo.get(i).getLoseCount(),
                                        "" + second.get(j).getResult())
                                );
                            }
                        }

                    }
                    simpleCalculationInfoBean.setListInfo(listInfo1);
                    if (mItemType == 1 || mItemType == 2) {
                        simpleCalculationInfoBean.setName(second.get(j).getPlayer1());
                    } else {
                        simpleCalculationInfoBean.setName(second.get(j).getPlayer1() + "\n" + second.get(j).getPlayer2());
                    }
                    simpleCalculationInfoBean.setRanking(second.get(j).getRanking());
                    simpleCalculationInfoBean.setResult(second.get(j).getResult());
                    simpleCalculationInfoBean.setUserId(second.get(j).getJoinId());
                    dataList1.add(simpleCalculationInfoBean);
                }
                integralCalculateItemAdapter.setNewData(dataList1);
            }

        }


    }
}
