package heyong.intellectPinPang.ui.friendcircle;

import androidx.annotation.Nullable;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Context;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.view.inputmethod.InputMethodManager;

import com.amap.api.location.AMapLocation;
import com.amap.api.location.AMapLocationClient;
import com.amap.api.location.AMapLocationClientOption;
import com.amap.api.location.AMapLocationListener;
import com.amap.api.services.core.AMapException;
import com.amap.api.services.core.PoiItem;
import com.amap.api.services.poisearch.PoiResult;
import com.amap.api.services.poisearch.PoiSearch;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.google.gson.Gson;
import com.scwang.smartrefresh.layout.api.RefreshLayout;
import com.scwang.smartrefresh.layout.listener.OnLoadmoreListener;
import com.scwang.smartrefresh.layout.listener.OnRefreshListener;

import org.greenrobot.eventbus.EventBus;

import java.util.ArrayList;
import java.util.List;

import heyong.intellectPinPang.ui.BaseActivity;
import heyong.handong.framework.base.BaseViewModel;
import heyong.intellectPinPang.R;
import heyong.intellectPinPang.adapter.friendcircle.FriendLocationSearchAdapter;
import heyong.intellectPinPang.bean.friend.MyPoiItems;
import heyong.intellectPinPang.databinding.ActivityFriendLocationSearchBinding;
import heyong.intellectPinPang.event.FriendCircleEvent;
import heyong.intellectPinPang.widget.refersh.MyRefreshAnimHeader;
import heyong.lzy.imagepicker.util.StatusBarUtil;

import static heyong.intellectPinPang.ui.friendcircle.FriendLocationActivity.showerror;

/**
 * 搜索定位
 */
public class FriendLocationSearchActivity extends BaseActivity<ActivityFriendLocationSearchBinding, BaseViewModel> implements View.OnClickListener, AMapLocationListener, OnRefreshListener, PoiSearch.OnPoiSearchListener {

    private AMapLocationClient mlocationClient;//定位监听
    private AMapLocationClientOption mLocationOption = null;//定位监听

    FriendLocationSearchAdapter friendLocationAdapter;
    private int pageNum = 1;
    private int pageSize = 10;
    private boolean haveMore = true;
    MyRefreshAnimHeader mRefreshAnimHeader;
    private String citys = "";
    private String keyWord = "";
    List<MyPoiItems> list = new ArrayList<>();

    @Override
    public int getLayoutRes() {
        return R.layout.activity_friend_location_search;
    }

    @Override
    public void initView(@Nullable Bundle savedInstanceState) {
        mBinding.setListener(this);
        StatusBarUtil.setStatusBarColor(this, R.color.color_f5);
        mlocationClient = new AMapLocationClient(this);
//初始化定位参数
        mLocationOption = new AMapLocationClientOption();
//设置定位监听
        mlocationClient.setLocationListener(this);
//设置定位模式为高精度模式，Battery_Saving为低功耗模式，Device_Sensors是仅设备模式
        mLocationOption.setLocationMode(AMapLocationClientOption.AMapLocationMode.Hight_Accuracy);
//设置定位间隔,单位毫秒,默认为2000ms
        mLocationOption.setInterval(2000);
        mLocationOption.setOnceLocation(true);
//设置定位参数
        mlocationClient.setLocationOption(mLocationOption);
// 此方法为每隔固定时间会发起一次定位请求，为了减少电量消耗或网络流量消耗，
// 注意设置合适的定位时间的间隔（最小间隔支持为1000ms），并且在合适时间调用stopLocation()方法来取消定位请求
// 在定位结束后，在合适的生命周期调用onDestroy()方法
// 在单次定位情况下，定位无论成功与否，都无需调用stopLocation()方法移除请求，定位sdk内部会移除
//启动定位
        mlocationClient.startLocation();
        mRefreshAnimHeader = new MyRefreshAnimHeader(FriendLocationSearchActivity.this);
        if (mBinding.swFresh.isRefreshing()) {
            mBinding.swFresh.finishRefresh();
        }
        friendLocationAdapter = new FriendLocationSearchAdapter();
        mBinding.rvUserList.setLayoutManager(new LinearLayoutManager(this, RecyclerView.VERTICAL, false));
        mBinding.rvUserList.setAdapter(friendLocationAdapter);
        mBinding.swFresh.setOnRefreshListener(this);

        friendLocationAdapter.setOnItemClickListener(new BaseQuickAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(BaseQuickAdapter adapter, View view, int position) {
//                List<MyPoiItems> data = friendLocationAdapter.getData();
//                for (int m = 0; m < data.size(); m++) {
//                    data.get(m).setSelect(false);
//                }
//                data.get(position).setSelect(true);
//                friendLocationAdapter.setNewData(data);
//                销毁界面
                EventBus.getDefault().post(new FriendCircleEvent(friendLocationAdapter.getData().get(position)));
                FriendLocationActivity.instance.finish();
                finish();
//                mBinding.tvSubmit.setEnabled(true);
//                mBinding.tvSubmit.setBackgroundResource(R.drawable.shape_circle_user_detail_blue);

            }
        });
        mBinding.swFresh.setOnLoadmoreListener(new OnLoadmoreListener() {
            @Override
            public void onLoadmore(RefreshLayout refreshlayout) {
                if (haveMore) {
                    pageNum++;
                    getData();
                } else {
                    mBinding.swFresh.finishLoadmore();
                }
            }
        });
        mBinding.etSearchKeyword.setOnEditorActionListener((v, actionId, event) -> {
            if (actionId == EditorInfo.IME_ACTION_SEARCH) {
                InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
                imm.toggleSoftInput(0, InputMethodManager.HIDE_NOT_ALWAYS);//关闭软键盘
                String searchText = mBinding.etSearchKeyword.getText().toString();

                if (!TextUtils.isEmpty(searchText)) {
                    keyWord = searchText;
                    pageNum = 1;
                    getData();
                }
//                mViewModel.title.set("" + mBinding.query.getText().toString());
//                helper.start();
                return true;
            }
            return false;
        });
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.tv_cancel:

                finish();

                break;
        }
    }

    private void getData() {
        showLoading();
//        mBinding.swFresh.autoRefresh(100);
        Log.e(TAG, "getData: pageNum===" + pageNum);
        PoiSearch.Query mPoiSearchQuery = new PoiSearch.Query("" + keyWord, "", citys);
//        mPoiSearchQuery.requireSubPois(true);   //true 搜索结果包含POI父子关系; false
        mPoiSearchQuery.setPageSize(20);
        mPoiSearchQuery.setPageNum(pageNum);
        PoiSearch poiSearch = new PoiSearch(FriendLocationSearchActivity.this, mPoiSearchQuery);
        poiSearch.setOnPoiSearchListener(this);
        poiSearch.searchPOIAsyn();
    }

    @Override
    public void onLocationChanged(AMapLocation amapLocation) {
        if (amapLocation != null) {
            if (amapLocation.getErrorCode() == 0) {
                //定位成功回调信息，设置相关消息
                amapLocation.getLocationType();//获取当前定位结果来源，如网络定位结果，详见定位类型表
                amapLocation.getLatitude();//获取纬度
                amapLocation.getLongitude();//获取经度
                amapLocation.getAccuracy();//获取精度信息
                String city = amapLocation.getCity();
                Log.e(TAG, "onLocationChanged: city===" + city);
                Log.e(TAG, "onLocationChanged: " + new Gson().toJson(amapLocation));
//                SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
//                Date date = new Date(amapLocation.getTime());
//                df.format(date);//定位时间
//                String citys = city.replace("市", "");
                Log.e(TAG, "onLocationChanged: getDescription=" + amapLocation.getDescription());
                Log.e(TAG, "onLocationChanged: getCity=" + amapLocation.getCity());
                Log.e(TAG, "onLocationChanged: getAoiName=" + amapLocation.getAoiName());
                Log.e(TAG, "onLocationChanged: getAddress=" + amapLocation.getAddress());
                Log.e(TAG, "onLocationChanged: getDistrict=" + amapLocation.getDistrict());
                Log.e(TAG, "onLocationChanged: getPoiName=" + amapLocation.getPoiName());
                Log.e(TAG, "onLocationChanged: getLocationDetail=" + amapLocation.getLocationDetail());
                citys = city;
//                keyWord = mBinding.etSearchKeyword.getText().toString();
//                Log.e(TAG, "getdatda: " + citys + keyWord);
//                getData();
            } else {
                //显示错误信息ErrCode是错误码，errInfo是错误信息，详见错误码表。
                Log.e("AmapError", "location Error, ErrCode:"
                        + amapLocation.getErrorCode() + ", errInfo:"
                        + amapLocation.getErrorInfo());
            }
        }
    }

    @Override
    public void onRefresh(RefreshLayout refreshlayout) {
//        pageNum = 1;
//        haveMore = true;
//        showLoading();
//        mViewModel.tableMyNumArrange("" + strTableNum, "" + strMatchId, "" + mIds, "" + times, ""+status, "" + code,
//                "" + pageNum, "" + pageSize);
    }

    @Override
    public void onPoiSearched(PoiResult poiResult, int rCode) {
        dismissLoading();
        if (mBinding.swFresh.isRefreshing()) {
            mBinding.swFresh.finishRefresh();
        }
        mBinding.swFresh.finishLoadmore();
        if (rCode == AMapException.CODE_AMAP_SUCCESS) {
            if (poiResult != null) {
                Log.e(TAG, "onPoiSearched: " + new Gson().toJson(poiResult.getPois()));
                if (pageNum == 1) {
                    list.clear();
                    if (mBinding.swFresh != null) {
                        mBinding.swFresh.finishRefresh();
                    }
                } else {
                    mBinding.swFresh.finishLoadmore();
                }
                if (list != null) {
                    //设置消息数目
                    ArrayList<PoiItem> pois = poiResult.getPois();
                    List<MyPoiItems> myPoiItems = new ArrayList<>();
                    for (int m = 0; m < pois.size(); m++) {
                        MyPoiItems myPoiItems1 = new MyPoiItems();
                        myPoiItems1.setProviceName("" + pois.get(m).getProvinceName());
                        myPoiItems1.setCityName("" + pois.get(m).getCityName());
                        myPoiItems1.setAreName("" + pois.get(m).getAdName());
                        myPoiItems1.setDetailAddress("" + pois.get(m).getSnippet());
                        myPoiItems1.setTitle("" + pois.get(m).getTitle());
                        myPoiItems1.setCityCode("" + pois.get(m).getAdCode());
                        myPoiItems.add(myPoiItems1);
                    }
                    list.addAll(myPoiItems);
                    if (list != null && list.size() < pageSize) {
                        haveMore = false;
                        mBinding.swFresh.setEnableLoadmore(false);
                    }
                    friendLocationAdapter.setNewData(list);
                } else {
                    friendLocationAdapter.setNewData(new ArrayList<>());
                }

            }
        } else {
            showerror(this, rCode);
        }
    }

    @Override
    public void onPoiItemSearched(PoiItem poiItem, int i) {

    }
}
