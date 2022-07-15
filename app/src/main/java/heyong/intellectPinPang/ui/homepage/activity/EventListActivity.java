package heyong.intellectPinPang.ui.homepage.activity;

import androidx.annotation.Nullable;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;

import java.util.Arrays;

import heyong.intellectPinPang.ui.BaseActivity;
import heyong.handong.framework.base.BaseViewModel;
import heyong.intellectPinPang.R;
import heyong.intellectPinPang.adapter.game.EventListAdapter;
import heyong.intellectPinPang.databinding.ActivityEventListBinding;

/**
 * 获取赛事列表
 */
public class EventListActivity extends BaseActivity<ActivityEventListBinding, BaseViewModel> {
    EventListAdapter eventListAdapter;
    public static final String PROJECT_TYPE = "projectType";
    public static final String RANKING = "ranking";
    public static final String CLUB_ID = "club_id";
    private String strRanking = "";
    private String strProjectType = "";
    private String strClubId = "";

    public static void goActivity(Context context, String clubId, String projectType, String ranking) {
        Intent intent = new Intent(context, EventListActivity.class);
        intent.putExtra(CLUB_ID, clubId);
        intent.putExtra(RANKING, ranking);
        intent.putExtra(PROJECT_TYPE, projectType);
        context.startActivity(intent);
    }

    @Override
    public int getLayoutRes() {
        return R.layout.activity_event_list;
    }

    @Override
    public void initView(@Nullable Bundle savedInstanceState) {
        strClubId = getIntent().getStringExtra(CLUB_ID);
        strRanking = getIntent().getStringExtra(RANKING);
        strProjectType = getIntent().getStringExtra(PROJECT_TYPE);
        eventListAdapter = new EventListAdapter();
        mBinding.rvEventsList.setLayoutManager(new LinearLayoutManager(this, RecyclerView.VERTICAL, false));
        mBinding.rvEventsList.setAdapter(eventListAdapter);
        eventListAdapter.setNewData(Arrays.asList("1", "1", "1", "1"));

    }
}