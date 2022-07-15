package heyong.intellectPinPang.adapter.game;

import android.content.Context;
import android.util.Log;
import android.view.View;

import androidx.databinding.DataBindingUtil;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;

import java.util.List;

import heyong.intellectPinPang.R;
import heyong.intellectPinPang.bean.gsonbean.CreateEventsBean;
import heyong.intellectPinPang.databinding.ItemCreateCompetitionCategoryBinding;

import static heyong.intellectPinPang.ui.BaseActivity.isFastClick;

/**
 * 创建比赛组别
 */
public class CreateCompetitionCategoriesAdapter extends BaseQuickAdapter<CreateEventsBean.ProjectListBean, BaseViewHolder> {
    private Context context;
    private ItemOnClickInterface itemOnClickInterface;

    public CreateCompetitionCategoriesAdapter(Context context) {
        super(R.layout.item_create_competition_category);
        this.context = context;
    }

    //回调接口
    public interface ItemOnClickInterface {
        void onItemClick(int parentposition, CreateEventsBean.ProjectListBean item, int childposition);

        void onDel(int parentposition, CreateEventsBean.ProjectListBean item, int childposition,String projectTitle);
    }

    //定义回调方法
    public void setItemMyOnClickInterface(ItemOnClickInterface itemOnClickInterface) {
        this.itemOnClickInterface = itemOnClickInterface;
    }

    @Override
    protected void convert(BaseViewHolder helper, CreateEventsBean.ProjectListBean item) {
        helper.addOnClickListener(R.id.ll_add_project);
        helper.addOnClickListener(R.id.iv_del);
        ItemCreateCompetitionCategoryBinding binding = DataBindingUtil.bind(helper.getConvertView());
        binding.tvName.setText("" + item.getProjectTitle());
        List<CreateEventsBean.ProjectListBean.ProjectItemListBean> dataList = item.getProjectItemList();
        RecyclerView rvList = helper.getView(R.id.rv_list);
        rvList.setLayoutManager(new LinearLayoutManager(context, RecyclerView.VERTICAL, false));
        CreateCompetitionCategoriesChildAdapter createCompetitionCategoriesChildAdapter = new CreateCompetitionCategoriesChildAdapter(context);
        rvList.setAdapter(createCompetitionCategoriesChildAdapter);
        createCompetitionCategoriesChildAdapter.setNewData(dataList);
        createCompetitionCategoriesChildAdapter.setOnItemChildClickListener(new OnItemChildClickListener() {
            @Override
            public void onItemChildClick(BaseQuickAdapter adapter, View view, int currentposition) {
                boolean fastClick = isFastClick();
                if (!fastClick) {
                    switch (view.getId()) {
                        case R.id.ll_del:
                            if (itemOnClickInterface != null) {
                                itemOnClickInterface.onItemClick(helper.getAdapterPosition(), item, currentposition);
                            }
                            break;
                        case R.id.iv_del://点击了删除
                            Log.e(TAG, "onItemChildClick: 点击了删除");
                            if (itemOnClickInterface != null) {
                                itemOnClickInterface.onDel(helper.getAdapterPosition(), item, currentposition, item.getProjectTitle());
                            }
                            break;
                    }
                }
            }
        });
    }
}
