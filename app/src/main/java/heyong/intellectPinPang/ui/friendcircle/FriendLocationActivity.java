package heyong.intellectPinPang.ui.friendcircle;

import androidx.annotation.Nullable;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

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
import com.kongzue.dialog.v3.WaitDialog;
import com.scwang.smartrefresh.layout.api.RefreshLayout;
import com.scwang.smartrefresh.layout.listener.OnLoadmoreListener;
import com.scwang.smartrefresh.layout.listener.OnRefreshListener;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import heyong.intellectPinPang.ui.BaseActivity;
import heyong.handong.framework.base.BaseViewModel;
import heyong.intellectPinPang.R;
import heyong.intellectPinPang.adapter.friendcircle.FriendLocationAdapter;
import heyong.intellectPinPang.bean.friend.MyPoiItems;
import heyong.intellectPinPang.databinding.ActivityFriendLocationBinding;
import heyong.intellectPinPang.widget.refersh.MyRefreshAnimHeader;

public class FriendLocationActivity extends BaseActivity<ActivityFriendLocationBinding, BaseViewModel> implements AMapLocationListener, PoiSearch.OnPoiSearchListener, OnRefreshListener, View.OnClickListener {
    private AMapLocationClient mlocationClient;//定位监听
    private AMapLocationClientOption mLocationOption = null;//定位监听

    FriendLocationAdapter friendLocationAdapter;
    private int pageNum = 1;
    private int pageSize = 10;
    private boolean haveMore = true;
    MyRefreshAnimHeader mRefreshAnimHeader;
    private String citys = "";
    private String keyWord = "";
    List<MyPoiItems> list = new ArrayList<>();
    public static FriendLocationActivity instance = null;
    private String locationCode="";
    @Override
    public int getLayoutRes() {
        return R.layout.activity_friend_location;
    }

    @Override
    public void initView(@Nullable Bundle savedInstanceState) {
        instance = this;
        mBinding.setListener(this);
        mBinding.tvSubmit.setEnabled(false);
        mBinding.tvSubmit.setBackgroundResource(R.drawable.shape_circle_user_detail_gray);
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
        mRefreshAnimHeader = new MyRefreshAnimHeader(FriendLocationActivity.this);
        if (mBinding.swFresh.isRefreshing()) {
            mBinding.swFresh.finishRefresh();
        }
        friendLocationAdapter = new FriendLocationAdapter();
        mBinding.rvUserList.setLayoutManager(new LinearLayoutManager(this, RecyclerView.VERTICAL, false));
        mBinding.rvUserList.setAdapter(friendLocationAdapter);
        mBinding.swFresh.setOnRefreshListener(this);

        friendLocationAdapter.setOnItemClickListener(new BaseQuickAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(BaseQuickAdapter adapter, View view, int position) {
                List<MyPoiItems> data = friendLocationAdapter.getData();
                for (int m = 0; m < data.size(); m++) {
                    data.get(m).setSelect(false);
                }
                data.get(position).setSelect(true);
                friendLocationAdapter.setNewData(data);

                mBinding.tvSubmit.setEnabled(true);
                mBinding.tvSubmit.setBackgroundResource(R.drawable.shape_circle_user_detail_blue);

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


    }

    private void getData() {
        mBinding.swFresh.autoRefresh(100);
        Log.e(TAG, "getData: pageNum===" + pageNum);
        PoiSearch.Query mPoiSearchQuery = new PoiSearch.Query("" + keyWord, "", citys);
//        mPoiSearchQuery.requireSubPois(true);   //true 搜索结果包含POI父子关系; false
        mPoiSearchQuery.setPageSize(15);
        mPoiSearchQuery.setPageNum(pageNum);
        PoiSearch poiSearch = new PoiSearch(FriendLocationActivity.this, mPoiSearchQuery);
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
                keyWord = amapLocation.getPoiName();
//                locationCode=amapLocation.getAdCode();
                Log.e(TAG, "onLocationChanged: adCode==="+amapLocation.getAdCode() );
                Log.e(TAG, "onLocationChanged: cityCode==="+amapLocation.getCityCode() );
                Log.e(TAG, "getdatda: " + citys + keyWord);
                getData();
            } else {
                //显示错误信息ErrCode是错误码，errInfo是错误信息，详见错误码表。
                Log.e("AmapError", "location Error, ErrCode:"
                        + amapLocation.getErrorCode() + ", errInfo:"
                        + amapLocation.getErrorInfo());
            }
        }
    }

    @Override
    public void onPoiSearched(PoiResult poiResult, int rCode) {

        if (rCode == AMapException.CODE_AMAP_SUCCESS) {
            if (poiResult != null) {
                Log.e(TAG, "onPoiSearched: " + new Gson().toJson(poiResult.getPois()));
                if (pageNum == 1) {
                    list.clear();
                    MyPoiItems e = new MyPoiItems();
                    e.setSelect(false);
                    list.add(e);
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
                    Log.e(TAG, "onPoiSearched: "+new Gson().toJson(pois.get(0)) );
                    Log.e(TAG, "onPoiSearched1111:getCityCode "+pois.get(0).getCityCode() );
                    Log.e(TAG, "onPoiSearched1111: getAdCode"+pois.get(0).getAdCode() );
                    Log.e(TAG, "onPoiSearched1111: getProvinceCode"+pois.get(0).getProvinceCode() );
                    Log.e(TAG, "onPoiSearched1111: getTypeCode"+pois.get(0).getTypeCode() );
                    Log.e(TAG, "onPoiSearched1111: "+new Gson().toJson(pois.get(0)) );
                    for (int m = 0; m < pois.size(); m++) {
                        MyPoiItems myPoiItems1 = new MyPoiItems();
                        myPoiItems1.setSelect(false);
                        myPoiItems1.setProviceName("" + pois.get(m).getProvinceName());
                        myPoiItems1.setCityName("" + pois.get(m).getCityName());
                        myPoiItems1.setAreName("" + pois.get(m).getAdName());
                        myPoiItems1.setDetailAddress("" + pois.get(m).getSnippet());
                        myPoiItems1.setTitle("" + pois.get(m).getTitle());
                        myPoiItems1.setCityCode("" + pois.get(m).getTypeCode());
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
    public void onPoiItemSearched(PoiItem item, int rCode) {
        if (rCode == AMapException.CODE_AMAP_SUCCESS) {
            List<PoiItem> poiItems = new ArrayList<PoiItem>();
            poiItems.add(item);
            Log.e(TAG, "onPoiItemSearched: " + new Gson().toJson(poiItems));
        } else {
            showerror(this, rCode);
        }
    }


    public static void showerror(Context context, int rCode) {

        try {
            switch (rCode) {
                //服务错误码
                case 1001:
                    throw new AMapException(AMapException.AMAP_SIGNATURE_ERROR);
                case 1002:
                    throw new AMapException(AMapException.AMAP_INVALID_USER_KEY);
                case 1003:
                    throw new AMapException(AMapException.AMAP_SERVICE_NOT_AVAILBALE);
                case 1004:
                    throw new AMapException(AMapException.AMAP_DAILY_QUERY_OVER_LIMIT);
                case 1005:
                    throw new AMapException(AMapException.AMAP_ACCESS_TOO_FREQUENT);
                case 1006:
                    throw new AMapException(AMapException.AMAP_INVALID_USER_IP);
                case 1007:
                    throw new AMapException(AMapException.AMAP_INVALID_USER_DOMAIN);
                case 1008:
                    throw new AMapException(AMapException.AMAP_INVALID_USER_SCODE);
                case 1009:
                    throw new AMapException(AMapException.AMAP_USERKEY_PLAT_NOMATCH);
                case 1010:
                    throw new AMapException(AMapException.AMAP_IP_QUERY_OVER_LIMIT);
                case 1011:
                    throw new AMapException(AMapException.AMAP_NOT_SUPPORT_HTTPS);
                case 1012:
                    throw new AMapException(AMapException.AMAP_INSUFFICIENT_PRIVILEGES);
                case 1013:
                    throw new AMapException(AMapException.AMAP_USER_KEY_RECYCLED);
                case 1100:
                    throw new AMapException(AMapException.AMAP_ENGINE_RESPONSE_ERROR);
                case 1101:
                    throw new AMapException(AMapException.AMAP_ENGINE_RESPONSE_DATA_ERROR);
                case 1102:
                    throw new AMapException(AMapException.AMAP_ENGINE_CONNECT_TIMEOUT);
                case 1103:
                    throw new AMapException(AMapException.AMAP_ENGINE_RETURN_TIMEOUT);
                case 1200:
                    throw new AMapException(AMapException.AMAP_SERVICE_INVALID_PARAMS);
                case 1201:
                    throw new AMapException(AMapException.AMAP_SERVICE_MISSING_REQUIRED_PARAMS);
                case 1202:
                    throw new AMapException(AMapException.AMAP_SERVICE_ILLEGAL_REQUEST);
                case 1203:
                    throw new AMapException(AMapException.AMAP_SERVICE_UNKNOWN_ERROR);
                    //sdk返回错误
                case 1800:
                    throw new AMapException(AMapException.AMAP_CLIENT_ERRORCODE_MISSSING);
                case 1801:
                    throw new AMapException(AMapException.AMAP_CLIENT_ERROR_PROTOCOL);
                case 1802:
                    throw new AMapException(AMapException.AMAP_CLIENT_SOCKET_TIMEOUT_EXCEPTION);
                case 1803:
                    throw new AMapException(AMapException.AMAP_CLIENT_URL_EXCEPTION);
                case 1804:
                    throw new AMapException(AMapException.AMAP_CLIENT_UNKNOWHOST_EXCEPTION);
                case 1806:
                    throw new AMapException(AMapException.AMAP_CLIENT_NETWORK_EXCEPTION);
                case 1900:
                    throw new AMapException(AMapException.AMAP_CLIENT_UNKNOWN_ERROR);
                case 1901:
                    throw new AMapException(AMapException.AMAP_CLIENT_INVALID_PARAMETER);
                case 1902:
                    throw new AMapException(AMapException.AMAP_CLIENT_IO_EXCEPTION);
                case 1903:
                    throw new AMapException(AMapException.AMAP_CLIENT_NULLPOINT_EXCEPTION);
                    //云图和附近错误码
                case 2000:
                    throw new AMapException(AMapException.AMAP_SERVICE_TABLEID_NOT_EXIST);
                case 2001:
                    throw new AMapException(AMapException.AMAP_ID_NOT_EXIST);
                case 2002:
                    throw new AMapException(AMapException.AMAP_SERVICE_MAINTENANCE);
                case 2003:
                    throw new AMapException(AMapException.AMAP_ENGINE_TABLEID_NOT_EXIST);
                case 2100:
                    throw new AMapException(AMapException.AMAP_NEARBY_INVALID_USERID);
                case 2101:
                    throw new AMapException(AMapException.AMAP_NEARBY_KEY_NOT_BIND);
                case 2200:
                    throw new AMapException(AMapException.AMAP_CLIENT_UPLOADAUTO_STARTED_ERROR);
                case 2201:
                    throw new AMapException(AMapException.AMAP_CLIENT_USERID_ILLEGAL);
                case 2202:
                    throw new AMapException(AMapException.AMAP_CLIENT_NEARBY_NULL_RESULT);
                case 2203:
                    throw new AMapException(AMapException.AMAP_CLIENT_UPLOAD_TOO_FREQUENT);
                case 2204:
                    throw new AMapException(AMapException.AMAP_CLIENT_UPLOAD_LOCATION_ERROR);
                    //路径规划
                case 3000:
                    throw new AMapException(AMapException.AMAP_ROUTE_OUT_OF_SERVICE);
                case 3001:
                    throw new AMapException(AMapException.AMAP_ROUTE_NO_ROADS_NEARBY);
                case 3002:
                    throw new AMapException(AMapException.AMAP_ROUTE_FAIL);
                case 3003:
                    throw new AMapException(AMapException.AMAP_OVER_DIRECTION_RANGE);
                    //短传分享
                case 4000:
                    throw new AMapException(AMapException.AMAP_SHARE_LICENSE_IS_EXPIRED);
                case 4001:
                    throw new AMapException(AMapException.AMAP_SHARE_FAILURE);
                default:
                    Toast.makeText(context, "查询失败：" + rCode, Toast.LENGTH_LONG).show();
                    Log.e(TAG, "showerror: 查询失败" + rCode);
                    break;
            }
        } catch (Exception e) {
            Toast.makeText(context.getApplicationContext(), e.getMessage(), Toast.LENGTH_LONG).show();
            Log.e(TAG, "showerror: excetopi 查询失败" + rCode);
        }
    }

    @Override
    public void onRefresh(RefreshLayout refreshlayout) {
        mBinding.swFresh.setEnableLoadmore(true);
        haveMore = true;
        pageNum = 1;
        getData();
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.rl_search:
                WaitDialog.dismiss();
                startActivity(new Intent(FriendLocationActivity.this, FriendLocationSearchActivity.class));

                break;
            case R.id.tv_submit:
                List<MyPoiItems> data = friendLocationAdapter.getData();
                for (int m = 0; m < data.size(); m++) {
                    if (data.get(m).isSelect()) {
                        MyPoiItems myPoiItems = data.get(m);
                        Intent intent = new Intent();
                        intent.putExtra("datas", (Serializable) myPoiItems);
                        setResult(RESULT_OK, intent);
                        finish();
                    }
                }

                break;
            case R.id.tv_cancel:
                finish();


                break;
        }
    }
}
