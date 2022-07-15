package heyong.intellectPinPang.ui.club.activity;

import androidx.annotation.Nullable;
import androidx.lifecycle.Observer;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.SimpleItemAnimator;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;

import com.google.gson.Gson;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import heyong.intellectPinPang.ui.BaseActivity;
import heyong.handong.framework.base.ResponseBean;
import heyong.intellectPinPang.R;
import heyong.intellectPinPang.adapter.game.SelectTeamNumbersAdapter;
import heyong.intellectPinPang.bean.competition.ChooseMemberDataBean;
import heyong.intellectPinPang.bean.competition.Contact;
import heyong.intellectPinPang.bean.competition.CreateXlClubContestInfoBean;
import heyong.intellectPinPang.cn.CNPinyin;
import heyong.intellectPinPang.cn.CNPinyinFactory;
import heyong.intellectPinPang.cn.stickyheader.StickyHeaderDecoration;
import heyong.intellectPinPang.databinding.ActivitySelectTeamNumbersBinding;
import heyong.intellectPinPang.ui.club.viewmodel.ClubViewModel;
import heyong.intellectPinPang.utils.AntiShakeUtils;
import heyong.intellectPinPang.widget.CharIndexView;

/**
 * 选择参赛队员
 */
public class SelectTeamNumbersActivity extends BaseActivity<ActivitySelectTeamNumbersBinding, ClubViewModel> implements View.OnClickListener {
    public static final String SOLO_TYPE = "solo_type";
    public static final String CLUB_ID = "club_id";
    public static final String COLOR_TYPE = "color_type";

    public static final String TAG = SelectTeamNumbersActivity.class.getSimpleName();
    SelectTeamNumbersAdapter adapter;
    List<CreateXlClubContestInfoBean.XlClubContestTeamsBean.ContestTeamMembersBean> dataList = new ArrayList<>();
    List<CreateXlClubContestInfoBean.XlClubContestTeamsBean.ContestTeamMembersBean> myTeamList = new ArrayList<>();
    private int soloType = -1;//0是单打 1是团体
    private int colorType = -1;
    private String clubId = "";
    List<CNPinyin<Contact>> contactList = new ArrayList<>();
    List<CreateXlClubContestInfoBean.XlClubContestTeamsBean.ContestTeamMembersBean> currentDataList = new ArrayList<>();
    List<Contact> contacts = new ArrayList<>();

    @Override
    public int getLayoutRes() {
        return R.layout.activity_select_team_numbers;
    }

    @Override
    public void initView(@Nullable Bundle savedInstanceState) {
        mBinding.setListener(this);
        soloType = getIntent().getIntExtra(SOLO_TYPE, 0);
        clubId = getIntent().getStringExtra(CLUB_ID);
        colorType = getIntent().getIntExtra(COLOR_TYPE, 0);
        mViewModel.chooseMemberData(clubId);

        mViewModel.chooseMemberDataLiveData.observe(this, new Observer<ResponseBean<List<ChooseMemberDataBean>>>() {
            @Override
            public void onChanged(ResponseBean<List<ChooseMemberDataBean>> responseBean) {
                dataList = (List<CreateXlClubContestInfoBean.XlClubContestTeamsBean.ContestTeamMembersBean>) getIntent().getSerializableExtra("data");
                myTeamList = (List<CreateXlClubContestInfoBean.XlClubContestTeamsBean.ContestTeamMembersBean>) getIntent().getSerializableExtra("myTeamList");
                Log.e(TAG, "onChanged: dataList==="+new Gson().toJson(dataList));
                Log.e(TAG, "onChanged: myTeamList==="+new Gson().toJson(myTeamList));

                List<ChooseMemberDataBean> data = responseBean.getData();

                for (int i = 0; i < data.size(); i++) {
                    List<ChooseMemberDataBean.MembersBean> members = data.get(i).getMembers();
                    for (int j = 0; j < members.size(); j++) {
                        CreateXlClubContestInfoBean.XlClubContestTeamsBean.ContestTeamMembersBean bean = new CreateXlClubContestInfoBean.XlClubContestTeamsBean.ContestTeamMembersBean();
                        bean.setUserId("" + members.get(j).getId());
                        bean.setUserName("" + members.get(j).getName());
                        currentDataList.add(bean);
                    }
                }
                for (int m = 0; m < currentDataList.size(); m++) {
                    String userId = currentDataList.get(m).getUserId();
                    Contact contact = new Contact();
                    contact.setSelectType(1);
                    contact.setName(currentDataList.get(m).getUserName());
                    contact.setId(currentDataList.get(m).getUserId());
                    for (int j = 0; j < dataList.size(); j++) {
                        if (userId.equals(dataList.get(j).getUserId())) {
                            Log.e(TAG, "onChanged: userId相等  name===="+dataList.get(j).getUserId() );
                            contact.setSelectType(0);
                           break;
                        }
                    }
                    for (int j1 = 0; j1 < myTeamList.size(); j1++) {
                        if (userId.equals(myTeamList.get(j1).getUserId())) {
                            Log.e(TAG, "onChanged: userId相等  name===="+myTeamList.get(j1).getUserId() );
                            contact.setSelectType(2);
                            break;
                        }
                    }

                    contacts.add(contact);
                }
                contactList = CNPinyinFactory.createCNPinyinList(contacts);
                Collections.sort(contactList);
                adapter = new SelectTeamNumbersAdapter(contactList);
                final LinearLayoutManager manager = new LinearLayoutManager(SelectTeamNumbersActivity.this);
                mBinding.rvClubMembers.setLayoutManager(manager);
                mBinding.rvClubMembers.setAdapter(adapter);
                ((SimpleItemAnimator)mBinding.rvClubMembers.getItemAnimator()).setSupportsChangeAnimations(false);
                mBinding.rvClubMembers.addItemDecoration(new StickyHeaderDecoration(adapter));
                mBinding.ivMain.setOnCharIndexChangedListener(new CharIndexView.OnCharIndexChangedListener() {
                    @Override
                    public void onCharIndexChanged(char currentIndex) {
                        for (int i = 0; i < contactList.size(); i++) {
                            if (contactList.get(i).getFirstChar() == currentIndex) {
                                manager.scrollToPositionWithOffset(i, 0);
                                return;
                            }
                        }
                    }

                    @Override
                    public void onCharIndexSelected(String currentIndex) {
                        Log.e(TAG, "onCharIndexSelected: currentIndex===" + currentIndex);
                    }
                });
                /*判断类型*/
                adapter.setListener(new SelectTeamNumbersAdapter.onMyListener() {
                    @Override
                    public void OnListener(int position, Contact contact) {
                        if (soloType == 0) {
                            /*单打*/
                            List<CNPinyin<Contact>> datas = adapter.getDatas();
                            for (int i = 0; i < datas.size(); i++) {
                                CNPinyin<Contact> contactCNPinyin = datas.get(i);
                                Contact data = contactCNPinyin.data;
                                data.setSelectType(1);
                            }
                            CNPinyin<Contact> contactCNPinyin = datas.get(position);
                            Contact data = contactCNPinyin.data;
                            data.setSelectType(2);
                            adapter.notifyItemChanged(position);
                        } else {
                            List<CNPinyin<Contact>> datas = adapter.getDatas();
                            CNPinyin<Contact> contactCNPinyin = datas.get(position);
                            Contact data = contactCNPinyin.data;
                            if (data.getSelectType() == 1) {
                                data.setSelectType(2);
                            } else {
                                data.setSelectType(1);
                            }
                            adapter.notifyItemChanged(position);
                        }
                        int num = adapter.getNum();
                        mBinding.tvSubmit.setText("确定" + "(" + num + ")");
                    }
                });
            }
        });

    }


    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.tv_submit:
                if (AntiShakeUtils.isInvalidClick(v))
                    return;
                if (adapter != null) {
                    List<CNPinyin<Contact>> datas = adapter.getDatas();
                    dataList.clear();
                    for (int i = 0; i < datas.size(); i++) {
                        Contact data1 = datas.get(i).data;
                        if (data1.getSelectType() == 2) {
                            CreateXlClubContestInfoBean.XlClubContestTeamsBean.ContestTeamMembersBean data
                                    = new CreateXlClubContestInfoBean.XlClubContestTeamsBean.ContestTeamMembersBean();
                            data.setUserName(data1.getName());
                            data.setUserId(data1.getId());
                            dataList.add(data);
                        }
                    }
                    Intent intent = new Intent();
                    intent.putExtra("listbean", (Serializable) dataList);
                    intent.putExtra("colorTypes", colorType);
                    setResult(RESULT_OK, intent);
                    finish();
                }
                break;
            case R.id.iv_left_back:

                finish();
                break;
        }
    }

}