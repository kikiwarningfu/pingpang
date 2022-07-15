package com.weigan.loopview.dialog;

import android.app.Dialog;
import android.content.Context;
import android.graphics.drawable.ColorDrawable;
import android.os.Handler;
import android.text.TextUtils;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.TextView;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.weigan.loopview.LoopView;
import com.weigan.loopview.OnItemSelectedListener;
import com.weigan.loopview.ProListBean;
import com.weigan.loopview.R;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by Administrator on 2017/10/26 0026.
 */

public class AreaSelectNewDialog extends Dialog {

    private TextView tvCancel;
    private TextView tvComplete;

    private LoopView loopProvince;
    private LoopView loopCity;
    private LoopView loopArea;
    private ImageView ivClose;
    private List<ProListBean> dataList;
//    private TextView tvTitle;

    private boolean isInit = true;
    private String mProvince;
    private String mCity;
    private String mArea;
    private int type = 0;

    public AreaSelectNewDialog(Context context, int types) {
        super(context);

        type = types;
        dataList = new ArrayList<>();

        init();
    }


    public void setCancelText(String text) {
        tvCancel.setText(text);
    }

    public void setEnsureText(String text) {
        tvComplete.setText(text);
    }

//    public void setTitle(String title) {
//        tvTitle.setText(title);
//    }

    private void init() {

        isInit = true;
        Window window = getWindow();
        window.requestFeature(Window.FEATURE_NO_TITLE);
        window.setBackgroundDrawable(new ColorDrawable(0x004caf65));
        WindowManager.LayoutParams attributes = window.getAttributes();

        window.setLayout(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);

        window.setGravity(Gravity.BOTTOM);
        window.getDecorView().setPadding(5, 5, 5, 5);


        View rootView = LayoutInflater.from(getContext()).inflate(R.layout.dialog_date_new, null);
        setContentView(rootView);
        tvCancel = (TextView) findViewById(R.id.tv_cancel);
        ivClose = (ImageView) findViewById(R.id.iv_close);
//        tvTitle = (TextView) findViewById(R.id.tv_title);
        tvCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dismiss();
            }
        });
        ivClose.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dismiss();
            }
        });
        tvComplete = (TextView) findViewById(R.id.tv_sure);

        loopProvince = rootView.findViewById(R.id.loop_year);
        loopCity = rootView.findViewById(R.id.loop_month);
        loopArea = rootView.findViewById(R.id.loop_day);
        loopProvince.setNotLoop();
        loopCity.setNotLoop();
        loopArea.setNotLoop();

        tvComplete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (listener != null) {
                    try {
                        int provinceIndex = loopProvince.getSelectedItem();
                        int cityIndex = loopCity.getSelectedItem();
                        int areaIndex = loopArea.getSelectedItem();
                        ProListBean areaBean = dataList.get(provinceIndex);
                        String provinceName = areaBean.getName();
                        String provinceId = String.valueOf(areaBean.getCode());

                        ProListBean.CityListBean city = areaBean.getCityList().get(cityIndex);
                        String cityName = city.getName();
                        String cityId = String.valueOf(city.getCode());
                        //Log.e(TAG, "onClick:1 tvComplete"+isInit );
                        //                        if (isInit) {
                        //                            if (areaIndex > 0) {
                        //                                areaIndex = areaIndex - 1;
                        //                            }
                        //                        }
                        ProListBean.CityListBean.AreaListBean area = city.getAreaList().get(areaIndex);
                        String areaName = area.getName();
                        //Log.e(TAG, "onClick:1 "+areaName );
                        String areaId = String.valueOf(area.getCode());
                        Log.i(TAG, "onClick:1 " + provinceName + "" + cityName + "" + areaName);
                        listener.onAreaCallback(provinceName, cityName, areaName
                        );
                    } catch (Exception e) {
                        //Log.e(TAG, "onClick1: " + e.getMessage());
                    }
                }
                dismiss();


            }
        });

        loopProvince.setListener(new OnItemSelectedListener() {
            @Override
            public void onItemSelected(int index) {
                isInit = false;
                int selectedItem = loopProvince.getSelectedItem();
                if (selectedItem > dataList.size() - 1) {
                    selectedItem = dataList.size() - 1;
                }
                ProListBean province = dataList.get(selectedItem);
                List<String> list = new ArrayList<>();
                for (ProListBean.CityListBean a : province.getCityList()) {
                    list.add(a.getName());
                }
                if(province.getCityList().size()==0)
                {
                    loopCity.setItems(new ArrayList<String>());
                    loopArea.setItems(new ArrayList<String>());
                }else
                {
                    loopCity.setItems(list);
                    loopCity.setInitPosition(0);
                    setArea();
                }


            }
        });

        loopCity.setListener(new OnItemSelectedListener() {
            @Override
            public void onItemSelected(int index) {
                isInit = false;
                setArea();
            }
        });

        initData();
    }


    public interface OnAreaSelectedListener {
        void onAreaCallback(String province, String city, String area);
    }

    private OnAreaSelectedListener listener;

    public void setListener(OnAreaSelectedListener listener) {
        this.listener = listener;
    }

    public void setDataList(List<ProListBean> dataList) {
        this.dataList = dataList;
        //Log.e(TAG, "setDataList: " + dataList.size());


    }

    Handler handler = new Handler();

    private void initData() {
        //Log.e(TAG, "initData: type====" + type);
        //Log.e(TAG, "initData: " + isInit);
        new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    try {
                        int i = Integer.parseInt(mProvince);
                        type = 0;
                    } catch (Exception e) {
                        type = 1;
                    }
                    if (type == 0) {
                        if (mProvince != null && mCity != null & mArea != null && !TextUtils.isEmpty(mProvince) && !TextUtils.isEmpty(mCity) && !TextUtils.isEmpty(mArea)) {
                            //Log.e(TAG, "run: 11111111111111111");
                            InputStream inputStream = getContext().getAssets().open("cities.json");
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
                                    List<String> provinces = new ArrayList<>();
                                    for (int i = 0; i < dataList.size(); i++) {
                                        provinces.add(dataList.get(i).getName());
                                    }
                                    loopProvince.setItems(provinces);
                                    for (int i = 0; i < dataList.size(); i++) {
                                        if (dataList.get(i).getCode().equals(mProvince)) {
                                            //Log.e(TAG, "run: Provice===" + i);
                                            loopProvince.setInitPosition(i);
                                            ProListBean province = dataList.get(i);
                                            List<String> list1 = new ArrayList<>();
                                            List<String> areaid = new ArrayList<>();
                                            for (ProListBean.CityListBean a : province.getCityList()) {
                                                list1.add(a.getName());
                                                areaid.add(String.valueOf(a.getCode()));
                                            }
                                            loopCity.setItems(list1);
                                            for (int j = 0; j < areaid.size(); j++) {
                                                if (areaid.get(j).equals(mCity)) {
                                                    //Log.e(TAG, "run: city===" + j);
                                                    loopCity.setInitPosition(j);
                                                    ProListBean.CityListBean city = province.getCityList().get(j);
                                                    List<String> list2 = new ArrayList<>();
                                                    List<String> aredsList = new ArrayList<>();
                                                    for (ProListBean.CityListBean.AreaListBean a : city.getAreaList()) {
                                                        list2.add(a.getName());
                                                        aredsList.add(String.valueOf(a.getCode()));
                                                    }
                                                    loopArea.setItems(list2);
                                                    for (int k = 0; k < aredsList.size(); k++) {
                                                        if (aredsList.get(k).equals(mArea)) {
                                                            //Log.e(TAG, "run: mArea===" + k);
                                                            loopArea.setInitPosition(k);
                                                        }
                                                    }

                                                }
                                            }
                                        }
                                    }
                                }
                            });

                        } else {
                            try {
                                //Log.e(TAG, "run: 22222222222222222222222");
                                InputStream inputStream = getContext().getAssets().open("cities.json");
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
                                        List<String> provinces = new ArrayList<>();
                                        for (int i = 0; i < dataList.size(); i++) {
                                            provinces.add(dataList.get(i).getName());
                                        }
                                        loopProvince.setItems(provinces);
                                        loopProvince.setInitPosition(4);
                                        //                                int selectedItem = loopArea.getSelectedItem();
                                        ProListBean province = dataList.get(4);
                                        List<String> list = new ArrayList<>();
                                        for (ProListBean.CityListBean a : province.getCityList()) {
                                            list.add(a.getName());
                                        }

                                        loopCity.setItems(list);
                                        ProListBean.CityListBean city = province.getCityList().get(0);
                                        for (ProListBean.CityListBean.AreaListBean a : city.getAreaList()) {
                                            list.add(a.getName());
                                        }
                                        loopArea.setItems(list);
                                        //                                    loopArea.set
                                        loopArea.setInitPosition(1);
                                    }
                                });
                            } catch (Exception e) {
                                //Log.e(TAG, "run: " + e.getMessage());
                            }


                        }
                    } else {
                        if (mProvince != null && mCity != null & mArea != null && !TextUtils.isEmpty(mProvince) && !TextUtils.isEmpty(mCity) && !TextUtils.isEmpty(mArea)) {
                            //Log.e(TAG, "run: 11111111111111111");
                            InputStream inputStream = getContext().getAssets().open("cities.json");
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
                                    List<String> provinces = new ArrayList<>();
                                    for (int i = 0; i < dataList.size(); i++) {
                                        provinces.add(dataList.get(i).getName());
                                    }
                                    loopProvince.setItems(provinces);
                                    if (dataList.size() > 0) {
                                        for (int i = 0; i < dataList.size(); i++) {
                                            if (dataList.get(i).getName().equals(mProvince)) {
                                                //Log.e(TAG, "run: Provice===" + i);
                                                loopProvince.setInitPosition(i);
                                                ProListBean province = dataList.get(i);
                                                List<String> list1 = new ArrayList<>();
                                                for (ProListBean.CityListBean a : province.getCityList()) {
                                                    list1.add(a.getName());
                                                }
                                                loopCity.setItems(list1);
                                                for (int j = 0; j < list1.size(); j++) {
                                                    if (list1.get(j).equals(mCity)) {
                                                        //Log.e(TAG, "run: city===" + j);
                                                        loopCity.setInitPosition(j);
                                                        ProListBean.CityListBean city = province.getCityList().get(j);
                                                        List<String> list2 = new ArrayList<>();
                                                        for (ProListBean.CityListBean.AreaListBean a : city.getAreaList()) {
                                                            list2.add(a.getName());
                                                        }
                                                        loopArea.setItems(list2);
                                                        for (int k = 0; k < list2.size(); k++) {
                                                            if (list2.get(k).equals(mArea)) {
                                                                //Log.e(TAG, "run: mArea===" + k);
                                                                loopArea.setInitPosition(k);
                                                            }
                                                        }

                                                    }
                                                }
                                            }
                                        }
                                    } else {
                                        List<String> provinces1 = new ArrayList<>();
                                        for (int i = 0; i < dataList.size(); i++) {
                                            provinces1.add(dataList.get(i).getName());
                                        }
                                        loopProvince.setItems(provinces1);
                                        loopProvince.setInitPosition(4);
                                        //                                int selectedItem = loopArea.getSelectedItem();
                                        ProListBean province = dataList.get(4);
                                        List<String> list = new ArrayList<>();
                                        for (ProListBean.CityListBean a : province.getCityList()) {
                                            list.add(a.getName());
                                        }

                                        loopCity.setItems(list);
                                        ProListBean.CityListBean city = province.getCityList().get(0);
                                        for (ProListBean.CityListBean.AreaListBean a : city.getAreaList()) {
                                            list.add(a.getName());
                                        }
                                        loopArea.setItems(list);
                                        //                                    loopArea.set
                                        loopArea.setInitPosition(1);
                                    }


                                }
                            });

                        } else {
                            try {
                                //Log.e(TAG, "run: 22222222222222222222222");
                                InputStream inputStream = getContext().getAssets().open("cities.json");
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
                                        List<String> provinces = new ArrayList<>();
                                        for (int i = 0; i < dataList.size(); i++) {
                                            provinces.add(dataList.get(i).getName());
                                        }
                                        loopProvince.setItems(provinces);
                                        loopProvince.setInitPosition(4);
                                        //                                int selectedItem = loopArea.getSelectedItem();
                                        ProListBean province = dataList.get(4);
                                        List<String> list = new ArrayList<>();
                                        for (ProListBean.CityListBean a : province.getCityList()) {
                                            list.add(a.getName());
                                        }

                                        loopCity.setItems(list);
                                        ProListBean.CityListBean city = province.getCityList().get(0);
                                        for (ProListBean.CityListBean.AreaListBean a : city.getAreaList()) {
                                            list.add(a.getName());
                                        }
                                        list.remove(0);
                                        loopArea.setItems(list);

                                        //                                    loopArea.set
                                        loopArea.setInitPosition(0);
                                    }
                                });
                            } catch (Exception e) {
                                //Log.e(TAG, "run: " + e.getMessage());
                            }


                        }
                    }
                } catch (IOException e) {
                    e.printStackTrace();
                }

            }
        }).start();


        //        if (dataList != null && dataList.size() > 0) {
        //            new Thread(new Runnable() {
        //                @Override
        //                public void run() {
        //                    handler.post(new Runnable() {
        //                        @Override
        //                        public void run() {

        //                            if (mProvince != null && mCity != null & mArea != null&&!TextUtils.isEmpty(mProvince)&&!TextUtils.isEmpty(mCity)&&!TextUtils.isEmpty(mArea)) {
        //
        //                                List<String> provinces = new ArrayList<>();
        //                                for (int i = 0; i < dataList.size(); i++) {
        //                                    provinces.add(dataList.get(i).getAreaName());
        //                                }
        //                                loopProvince.setItems(provinces);
        //                                for (int i = 0; i < dataList.size(); i++) {
        //                                    if (dataList.get(i).getAreaName().equals(mProvince)) {
        //                                        //Log.e(TAG, "run: Provice==="+i );
        //                                        loopProvince.setInitPosition(i);
        //                                        AreaBean province = dataList.get(i);
        //                                        List<String> list1 = new ArrayList<>();
        //                                        for (AreaBean a : province.getChildren()) {
        //                                            list1.add(a.getAreaName());
        //                                        }
        //                                        loopCity.setItems(list1);
        //                                        for (int j = 0; j < list1.size(); j++) {
        //                                            if (list1.get(j).equals(mCity)) {
        //                                                //Log.e(TAG, "run: city==="+j );
        //                                                loopCity.setInitPosition(j);
        //                                                AreaBean city = province.getChildren().get(j);
        //                                                List<String> list2 = new ArrayList<>();
        //                                                for (AreaBean a : city.getChildren()) {
        //                                                    list2.add(a.getAreaName());
        //                                                }
        //                                                loopArea.setItems(list2);
        //                                                for (int k = 0; k < list2.size(); k++) {
        //                                                    if (list2.get(k).equals(mArea)) {
        //                                                        //Log.e(TAG, "run: mArea==="+k );
        //                                                        loopArea.setInitPosition(k);
        //                                                    }
        //                                                }
        //
        //                                            }
        //                                        }
        //                                    }
        //                                }
        //                            } else {
        //                                List<String> provinces = new ArrayList<>();
        //                                for (int i = 0; i < dataList.size(); i++) {
        //                                    provinces.add(dataList.get(i).getAreaName());
        //                                }
        //                                loopProvince.setItems(provinces);
        //                                loopProvince.setInitPosition(4);
        ////                                int selectedItem = loopArea.getSelectedItem();
        //                                AreaBean province = dataList.get(4);
        //                                List<String> list = new ArrayList<>();
        //                                for (AreaBean a : province.getChildren()) {
        //                                    list.add(a.getAreaName());
        //                                }
        //
        //                                loopCity.setItems(list);
        //                                AreaBean city = province.getChildren().get(0);
        //                                for (AreaBean a : city.getChildren()) {
        //                                    list.add(a.getAreaName());
        //                                }
        //                                loopArea.setItems(list);
        //                                loopArea.setInitPosition(1);
        //                            }
        //                        }
        //                    });
        //                }
        //            }).start();
        //        } else {
        //
        //        }

    }

    /**
     * 设置区
     */
    private void setArea() {
        try {
            ProListBean province = dataList.get(loopProvince.getSelectedItem() > dataList.size() - 1 ? dataList.size() - 1 : loopProvince.getSelectedItem());
            List<ProListBean.CityListBean> children = province.getCityList();
            int selectedItem = loopCity.getSelectedItem();
            if (selectedItem > children.size() - 1) {
                selectedItem = children.size() - 1;
            }
            ProListBean.CityListBean city = province.getCityList().get(selectedItem);
            List<String> list = new ArrayList<>();
            for (ProListBean.CityListBean.AreaListBean a : city.getAreaList()) {
                list.add(a.getName());
            }
            loopArea.setItems(list);
            //            loopArea.setInitPosition(1);

        } catch (Exception e) {

        }


    }

    /**
     * 设置城市
     */
    private void setCity() {
        int selectedItem = loopProvince.getSelectedItem();
        if (selectedItem > dataList.size() - 1) {
            selectedItem = dataList.size() - 1;
        }
        ProListBean province = dataList.get(selectedItem);
        List<String> list = new ArrayList<>();
        for (ProListBean.CityListBean a : province.getCityList()) {
            list.add(a.getName());
        }

        loopCity.setItems(list);

        //        loopCity.setCurrentPosition(0);
        //        loopArea.setCurrentPosition(4);
    }

    /**
     * 设置省
     */
    private void setProvince() {
        List<String> provinces = new ArrayList<>();
        for (ProListBean a : dataList) {
            provinces.add(a.getName());
        }
        loopProvince.setItems(provinces);
//        loopCity.setCurrentPosition(4);
    }

    private static final String TAG = "AreaSelectDialog";


    private List<ProListBean> parseJson(String json) {

        List<ProListBean> provinces = new ArrayList<>();
        try {
            provinces = new Gson().fromJson(json, new TypeToken<List<ProListBean>>() {
            }.getType());

        } catch (Exception e) {
            provinces = new ArrayList<>();
        }

        return provinces;

    }

}
