package heyong.intellectPinPang.ui.homepage.activity;

import androidx.annotation.Nullable;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.graphics.Color;
import android.os.Bundle;
import android.os.Handler;
import android.text.TextUtils;
import android.view.View;
import android.view.ViewGroup;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.weigan.loopview.dialog.AreaBean;
import com.weigan.loopview.dialog.RankingLsitCityDialog;


import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import heyong.intellectPinPang.ui.BaseActivity;
import heyong.handong.framework.base.BaseViewModel;
import heyong.intellectPinPang.R;
import heyong.intellectPinPang.adapter.utli.CityAdapter;
import heyong.intellectPinPang.adapter.game.ProvinceAdapter;
import heyong.intellectPinPang.adapter.game.RankingListAdapter;
import heyong.intellectPinPang.bean.competition.CityListBean;
import heyong.intellectPinPang.bean.competition.ProvinceListBean;
import heyong.intellectPinPang.databinding.ActivityRankingListBinding;
import heyong.intellectPinPang.utils.AntiShakeUtils;
import heyong.intellectPinPang.widget.popupwindow.CommonPopupWindow;

/**
 * 排行榜
 */
public class RankingListActivity extends BaseActivity<ActivityRankingListBinding, BaseViewModel> implements View.OnClickListener, RankingLsitCityDialog.OnAreaSelectedListener {
    private RankingListAdapter rankingListAdapter;
    private int showType = 1;//1  全国  2 显示市级 3  显示省级
    private List<AreaBean> dataList;
    List<String> provincesList;
    List<ProvinceListBean> provinceListBeans;
    List<String> cityList;
    List<CityListBean> cityListBeans;
    public static final String TAG = RankingListActivity.class.getSimpleName();
    private CommonPopupWindow mMovieTicketWindow;

    private String province, city;
    private String localCity;

    @Override
    public int getLayoutRes() {
        return R.layout.activity_ranking_list;
    }

    @Override
    public void initView(@Nullable Bundle savedInstanceState) {
        mBinding.setListener(this);
        mBinding.rvRankingList.setLayoutManager(new LinearLayoutManager(this, RecyclerView.VERTICAL, false));
        rankingListAdapter = new RankingListAdapter();
        mBinding.rvRankingList.setAdapter(rankingListAdapter);
        rankingListAdapter.setNewData(Arrays.asList("1", "1", "1", "1"));
        initData();
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.tv_sports_man://运动员
                if (AntiShakeUtils.isInvalidClick(v))
                    return;
                mBinding.tvSportsMan.setBackgroundResource(R.drawable.shape_left_corners_blue);
                mBinding.tvJudge.setBackgroundResource(R.drawable.shape_right_corners_gray);
                mBinding.tvSportsMan.setTextColor(Color.parseColor("#ffffff"));
                mBinding.tvJudge.setTextColor(Color.parseColor("#666666"));
                break;
            case R.id.tv_judge://裁判员
                if (AntiShakeUtils.isInvalidClick(v))
                    return;
                mBinding.tvSportsMan.setBackgroundResource(R.drawable.shape_right_corners_gray);
                mBinding.tvJudge.setBackgroundResource(R.drawable.shape_left_corners_blue);
                mBinding.tvSportsMan.setTextColor(Color.parseColor("#666666"));
                mBinding.tvJudge.setTextColor(Color.parseColor("#ffffff"));
                break;
            case R.id.tv_national_level://国家级
                if (AntiShakeUtils.isInvalidClick(v))
                    return;
                showType = 1;
                mBinding.tvNationalLevel.setBackgroundResource(R.color.white);
                mBinding.tvNationalLevel.setTextColor(Color.parseColor("#4795ED"));
                mBinding.tvProvincialLevel.setBackground(null);
                mBinding.tvProvincialLevel.setTextColor(Color.parseColor("#666666"));
                mBinding.tvCityLevel.setBackground(null);
                mBinding.tvCityLevel.setTextColor(Color.parseColor("#666666"));
                setViewStatus();
                break;
            case R.id.tv_provincial_level://省级
                if (AntiShakeUtils.isInvalidClick(v))
                    return;
                showType = 2;
                mBinding.tvNationalLevel.setBackground(null);
                mBinding.tvNationalLevel.setTextColor(Color.parseColor("#666666"));
                mBinding.tvProvincialLevel.setBackgroundResource(R.color.white);
                mBinding.tvProvincialLevel.setTextColor(Color.parseColor("#4795ED"));
                mBinding.tvCityLevel.setBackground(null);
                mBinding.tvCityLevel.setTextColor(Color.parseColor("#666666"));
                setViewStatus();
                break;
            case R.id.tv_city_level://市级
                if (AntiShakeUtils.isInvalidClick(v))
                    return;
                showType = 3;
                mBinding.tvNationalLevel.setBackground(null);
                mBinding.tvNationalLevel.setTextColor(Color.parseColor("#666666"));
                mBinding.tvProvincialLevel.setBackground(null);
                mBinding.tvProvincialLevel.setTextColor(Color.parseColor("#666666"));
                mBinding.tvCityLevel.setBackgroundResource(R.color.white);
                mBinding.tvCityLevel.setTextColor(Color.parseColor("#4795ED"));
                setViewStatus();
                break;
            case R.id.ll_city_select_view:
                if (AntiShakeUtils.isInvalidClick(v))
                    return;
                switch (showType) {
                    case 1:
                        mBinding.tvCitySelect.setText("全国级");
                        break;
                    case 2:
                        if (mMovieTicketWindow != null && mMovieTicketWindow.isShowing()) {

                        } else {
                            localCity = "";
                            province = "";
                            city = "";

//                            mBinding.ivAll.setBackgroundResource(R.drawable.icon_choose_xia);
                            showPopCity(dataList);
                        }
                        break;
                    case 3:
                        if (mMovieTicketWindow != null && mMovieTicketWindow.isShowing()) {

                        } else {
                            localCity = "";
                            province = "";
                            city = "";

//                            mBinding.ivAll.setBackgroundResource(R.drawable.icon_choose_xia);
                            showPopProvinceCity(dataList);
                        }
                        break;
                }
                break;
        }
    }

    public void showPopCity(List<AreaBean> dataList) {
        mMovieTicketWindow = new CommonPopupWindow.Builder(RankingListActivity.this)
                .setView(R.layout.pop_select_city)
                .setBackGroundLevel(1f)
                .setViewOnclickListener(new CommonPopupWindow.ViewInterface() {
                    @Override
                    public void getChildView(View view, int layoutResId) {
                        if (AntiShakeUtils.isInvalidClick(view))
                            return;
                        RecyclerView rvProvince = view.findViewById(R.id.rv_province);

                        ProvinceAdapter provinceAdapter = new ProvinceAdapter();
                        rvProvince.setAdapter(provinceAdapter);
                        rvProvince.setLayoutManager(new LinearLayoutManager(RankingListActivity.this, RecyclerView.VERTICAL, false));
                        provinceAdapter.setNewData(provinceListBeans);
                        provinceAdapter.setOnItemClickListener(new BaseQuickAdapter.OnItemClickListener() {
                            @Override
                            public void onItemClick(BaseQuickAdapter adapter, View view, int position) {
                                boolean fastClick = isFastClick();
                                if (!fastClick) {
                                    List<ProvinceListBean> data = provinceAdapter.getData();
                                    for (int i = 0; i < data.size(); i++) {
                                        data.get(i).setSelect(false);
                                    }
                                    data.get(position).setSelect(true);

                                    AreaBean areaBean = dataList.get(position);
                                    localCity = areaBean.getAreaName();
                                    cityListBeans = new ArrayList<>();
                                    for (AreaBean a : areaBean.getChildren()) {
                                        cityList.add(a.getAreaName());
                                        cityListBeans.add(new CityListBean(a.getAreaName(), false));
                                    }

                                    provinceAdapter.notifyDataSetChanged();


                                    mBinding.tvCitySelect.setText("" + localCity);

                                    mMovieTicketWindow.dismiss();
                                }
                            }
                        });


                    }
                })
                .setOutsideTouchable(true)//设置外层不可点击
                .setWidthAndHeight(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT)
                .create();
//        View view1 = View.inflate(MovieTicketActivity.this, R.layout.activity_movie_ticket, null);
        if (mMovieTicketWindow == null || !mMovieTicketWindow.isShowing()) {
            mMovieTicketWindow.setFocusable(true);// 这个很重要
            mMovieTicketWindow.showAsDropDown(mBinding.llCitySelectView);
        }
    }

    public void showPopProvinceCity(List<AreaBean> dataList) {
        mMovieTicketWindow = new CommonPopupWindow.Builder(RankingListActivity.this)
                .setView(R.layout.pop_select_province_city)
                .setBackGroundLevel(1f)
                .setViewOnclickListener(new CommonPopupWindow.ViewInterface() {
                    @Override
                    public void getChildView(View view, int layoutResId) {
                        if (AntiShakeUtils.isInvalidClick(view))
                            return;
                        RecyclerView rvProvince = view.findViewById(R.id.rv_province);
                        RecyclerView rvCity = view.findViewById(R.id.rv_city);

                        ProvinceAdapter provinceAdapter = new ProvinceAdapter();
                        CityAdapter cityAdapter = new CityAdapter();
                        rvProvince.setAdapter(provinceAdapter);
                        rvProvince.setLayoutManager(new LinearLayoutManager(RankingListActivity.this, RecyclerView.VERTICAL, false));
                        provinceAdapter.setNewData(provinceListBeans);
                        provinceAdapter.setOnItemClickListener(new BaseQuickAdapter.OnItemClickListener() {
                            @Override
                            public void onItemClick(BaseQuickAdapter adapter, View view, int position) {
                                boolean fastClick = isFastClick();
                                if (!fastClick) {
                                    List<ProvinceListBean> data = provinceAdapter.getData();
                                    for (int i = 0; i < data.size(); i++) {
                                        data.get(i).setSelect(false);
                                    }
                                    data.get(position).setSelect(true);

                                    AreaBean areaBean = dataList.get(position);
                                    province = areaBean.getAreaName();
                                    cityListBeans = new ArrayList<>();
                                    for (AreaBean a : areaBean.getChildren()) {
                                        cityList.add(a.getAreaName());
                                        cityListBeans.add(new CityListBean(a.getAreaName(), false));
                                    }
                                    cityAdapter.setNewData(cityListBeans);
                                    cityAdapter.notifyDataSetChanged();
                                    provinceAdapter.notifyDataSetChanged();
                                }

                            }
                        });
                        rvCity.setAdapter(cityAdapter);
                        rvCity.setLayoutManager(new LinearLayoutManager(RankingListActivity.this,
                                RecyclerView.VERTICAL, false));
                        cityAdapter.setNewData(cityListBeans);
                        cityAdapter.setOnItemClickListener(new BaseQuickAdapter.OnItemClickListener() {
                            @Override
                            public void onItemClick(BaseQuickAdapter adapter, View view, int position) {
                                boolean fastClick = isFastClick();
                                if (!fastClick) {
                                    List<CityListBean> data = cityAdapter.getData();
                                    for (int i = 0; i < data.size(); i++) {
                                        data.get(i).setSelect(false);
                                    }
                                    data.get(position).setSelect(true);
                                    city = data.get(position).getName();
                                    cityAdapter.notifyDataSetChanged();
                                    mMovieTicketWindow.dismiss();
                                    if (TextUtils.isEmpty(province)) {
                                        for (int i = 0; i < provinceAdapter.getData().size(); i++) {
                                            if (provinceAdapter.getData().get(i).isSelect()) {

                                                String name = provinceAdapter.getData().get(i).getName();
                                                if (name.equals(city)) {
                                                    mBinding.tvCitySelect.setText("" + name);
                                                } else {
                                                    mBinding.tvCitySelect.setText("" + name + city);
                                                }
                                            }
                                        }
                                    } else {
                                        if (province.equals(city)) {
                                            mBinding.tvCitySelect.setText("" + province);
                                        } else {
                                            mBinding.tvCitySelect.setText("" + province + city);
                                        }
                                    }
                                }
                            }
                        });

                    }
                })
                .setOutsideTouchable(true)//设置外层不可点击
                .setWidthAndHeight(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT)
                .create();
//        View view1 = View.inflate(MovieTicketActivity.this, R.layout.activity_movie_ticket, null);
        if (mMovieTicketWindow == null || !mMovieTicketWindow.isShowing()) {
            mMovieTicketWindow.setFocusable(true);// 这个很重要
            mMovieTicketWindow.showAsDropDown(mBinding.llCitySelectView);
        }
    }

    private void setViewStatus() {
        switch (showType) {
            case 1:
                mBinding.viewUpDown.setVisibility(View.GONE);
                mBinding.tvCitySelect.setText("全国级");
                break;
            case 2:
                mBinding.viewUpDown.setVisibility(View.VISIBLE);
                mBinding.tvCitySelect.setText("省级");
                break;
            case 3:
                mBinding.viewUpDown.setVisibility(View.VISIBLE);
                mBinding.tvCitySelect.setText("市级");
                break;
        }
    }

    @Override
    public void onAreaCallback(String province, String city) {
    }

    Handler handler = new Handler();

    private void initData() {

        new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    InputStream inputStream = getAssets().open("cities.json");
                    InputStreamReader inputStreamReader = new InputStreamReader(inputStream, "UTF-8");
                    BufferedReader bufferedReader = new BufferedReader(inputStreamReader);
                    StringBuilder sb = new StringBuilder();
                    String info = "";
                    while ((info = bufferedReader.readLine()) != null) {
                        sb.append(info);
                    }
                    String json = sb.toString().trim();
                    dataList = parseJson(json);
                    handler.post(new Runnable() {
                        @Override
                        public void run() {
                            provincesList = new ArrayList<>();
                            provinceListBeans = new ArrayList<>();
                            for (int i = 0; i < dataList.size(); i++) {
                                provincesList.add(dataList.get(i).getAreaName());
                                provinceListBeans.add(new ProvinceListBean(dataList.get(i).getAreaName(), false));
                            }

                            AreaBean province = dataList.get(0);
                            cityList = new ArrayList<>();
                            cityListBeans = new ArrayList<>();
                            for (AreaBean a : province.getChildren()) {
                                cityList.add(a.getAreaName());
                                cityListBeans.add(new CityListBean(a.getAreaName(), false));
                            }


                        }
                    });
                } catch (Exception e) {
                }


            }
        }).start();


    }

    private List<AreaBean> parseJson(String json) {

        List<AreaBean> provinces = new ArrayList<>();

        try {
            JSONObject object = new JSONObject(json);

            JSONArray data = object.getJSONArray("data");

            if (data != null) {

                for (int i = 0; i < data.length(); i++) {
                    JSONObject jsonObject = data.getJSONObject(i);

                    AreaBean province = new AreaBean();

                    String areaName = jsonObject.getString("name");
                    province.setAreaName(areaName);
                    province.setAreaId(jsonObject.getInt("id"));
                    JSONArray children = jsonObject.getJSONArray("children");

                    List<AreaBean> citys = new ArrayList<>();
                    if (children != null) {

                        for (int j = 0; j < children.length(); j++) {
                            JSONObject jsonObject1 = children.getJSONObject(j);

                            AreaBean city = new AreaBean();

                            city.setAreaName(jsonObject1.getString("name"));
                            city.setAreaId(jsonObject1.getInt("id"));
                            JSONArray children1 = jsonObject1.getJSONArray("children");
                            List<AreaBean> areas = new ArrayList<>();

                            if (children1 != null) {
                                for (int k = 0; k < children1.length(); k++) {

                                    JSONObject jsonObject2 = children1.getJSONObject(k);
                                    AreaBean area = new AreaBean();

                                    area.setAreaName(jsonObject2.getString("name"));
                                    area.setAreaId(jsonObject2.getInt("id"));
                                    areas.add(area);

                                }
                            }

                            city.setChildren(areas);
                            citys.add(city);
                        }

                    }
                    province.setChildren(citys);
                    provinces.add(province);
                }
            }


        } catch (JSONException e) {
            e.printStackTrace();
        }

        return provinces;

    }
}