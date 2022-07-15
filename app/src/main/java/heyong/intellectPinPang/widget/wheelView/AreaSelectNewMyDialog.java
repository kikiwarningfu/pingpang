package heyong.intellectPinPang.widget.wheelView;

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

import com.weigan.loopview.LoopView;
import com.weigan.loopview.OnItemSelectedListener;
import com.weigan.loopview.R;
import com.weigan.loopview.dialog.AreaBean;

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

public class AreaSelectNewMyDialog extends Dialog {

    private TextView tvCancel;
    private TextView tvComplete;

    private LoopView loopProvince;
    private LoopView loopCity;
    private LoopView loopArea;
    private ImageView ivClose;
    private List<AreaBean> dataList;
//    private TextView tvTitle;

    private boolean isInit = true;
    private String mProvince;
    private String mCity;
    private String mArea;
    private int type = 0;

    public AreaSelectNewMyDialog(Context context, int types) {
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
                        AreaBean areaBean = dataList.get(provinceIndex);
                        String provinceName = areaBean.getAreaName();
                        String provinceId = String.valueOf(areaBean.getAreaId());

                        AreaBean city = areaBean.getChildren().get(cityIndex);
                        String cityName = city.getAreaName();
                        String cityId = String.valueOf(city.getAreaId());
                        //Log.e(TAG, "onClick:1 tvComplete"+isInit );
                        //                        if (isInit) {
                        //                            if (areaIndex > 0) {
                        //                                areaIndex = areaIndex - 1;
                        //                            }
                        //                        }
                        AreaBean area = city.getChildren().get(areaIndex);
                        String areaName = area.getAreaName();
                        //Log.e(TAG, "onClick:1 "+areaName );
                        String areaId = String.valueOf(area.getAreaId());
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
                AreaBean province = dataList.get(selectedItem);
                List<String> list = new ArrayList<>();
                for (AreaBean a : province.getChildren()) {
                    list.add(a.getAreaName());
                }

                loopCity.setItems(list);
                loopCity.setInitPosition(0);
                setArea();
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

    public void setDataList(List<AreaBean> dataList) {
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
                                        provinces.add(dataList.get(i).getAreaName());
                                    }
                                    loopProvince.setItems(provinces);
                                    for (int i = 0; i < dataList.size(); i++) {
                                        if (dataList.get(i).getAreaId() == Integer.parseInt(mProvince)) {
                                            //Log.e(TAG, "run: Provice===" + i);
                                            loopProvince.setInitPosition(i);
                                            AreaBean province = dataList.get(i);
                                            List<String> list1 = new ArrayList<>();
                                            List<String> areaid = new ArrayList<>();
                                            for (AreaBean a : province.getChildren()) {
                                                list1.add(a.getAreaName());
                                                areaid.add(String.valueOf(a.getAreaId()));
                                            }
                                            loopCity.setItems(list1);
                                            for (int j = 0; j < areaid.size(); j++) {
                                                if (areaid.get(j).equals(mCity)) {
                                                    //Log.e(TAG, "run: city===" + j);
                                                    loopCity.setInitPosition(j);
                                                    AreaBean city = province.getChildren().get(j);
                                                    List<String> list2 = new ArrayList<>();
                                                    List<String> aredsList = new ArrayList<>();
                                                    for (AreaBean a : city.getChildren()) {
                                                        list2.add(a.getAreaName());
                                                        aredsList.add(String.valueOf(a.getAreaId()));
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
                                            provinces.add(dataList.get(i).getAreaName());
                                        }
                                        loopProvince.setItems(provinces);
                                        loopProvince.setInitPosition(4);
                                        //                                int selectedItem = loopArea.getSelectedItem();
                                        AreaBean province = dataList.get(4);
                                        List<String> list = new ArrayList<>();
                                        for (AreaBean a : province.getChildren()) {
                                            list.add(a.getAreaName());
                                        }

                                        loopCity.setItems(list);
                                        AreaBean city = province.getChildren().get(0);
                                        for (AreaBean a : city.getChildren()) {
                                            list.add(a.getAreaName());
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
                                        provinces.add(dataList.get(i).getAreaName());
                                    }
                                    loopProvince.setItems(provinces);
                                    if (dataList.size() > 0) {
                                        for (int i = 0; i < dataList.size(); i++) {
                                            if (dataList.get(i).getAreaName().equals(mProvince)) {
                                                //Log.e(TAG, "run: Provice===" + i);
                                                loopProvince.setInitPosition(i);
                                                AreaBean province = dataList.get(i);
                                                List<String> list1 = new ArrayList<>();
                                                for (AreaBean a : province.getChildren()) {
                                                    list1.add(a.getAreaName());
                                                }
                                                loopCity.setItems(list1);
                                                for (int j = 0; j < list1.size(); j++) {
                                                    if (list1.get(j).equals(mCity)) {
                                                        //Log.e(TAG, "run: city===" + j);
                                                        loopCity.setInitPosition(j);
                                                        AreaBean city = province.getChildren().get(j);
                                                        List<String> list2 = new ArrayList<>();
                                                        for (AreaBean a : city.getChildren()) {
                                                            list2.add(a.getAreaName());
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
                                            provinces1.add(dataList.get(i).getAreaName());
                                        }
                                        loopProvince.setItems(provinces1);
                                        loopProvince.setInitPosition(4);
                                        //                                int selectedItem = loopArea.getSelectedItem();
                                        AreaBean province = dataList.get(4);
                                        List<String> list = new ArrayList<>();
                                        for (AreaBean a : province.getChildren()) {
                                            list.add(a.getAreaName());
                                        }

                                        loopCity.setItems(list);
                                        AreaBean city = province.getChildren().get(0);
                                        for (AreaBean a : city.getChildren()) {
                                            list.add(a.getAreaName());
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
                                            provinces.add(dataList.get(i).getAreaName());
                                        }
                                        loopProvince.setItems(provinces);
                                        loopProvince.setInitPosition(4);
                                        //                                int selectedItem = loopArea.getSelectedItem();
                                        AreaBean province = dataList.get(4);
                                        List<String> list = new ArrayList<>();
                                        for (AreaBean a : province.getChildren()) {
                                            list.add(a.getAreaName());
                                        }

                                        loopCity.setItems(list);
                                        AreaBean city = province.getChildren().get(0);
                                        for (AreaBean a : city.getChildren()) {
                                            list.add(a.getAreaName());
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
            AreaBean province = dataList.get(loopProvince.getSelectedItem() > dataList.size() - 1 ? dataList.size() - 1 : loopProvince.getSelectedItem());
            List<AreaBean> children = province.getChildren();
            int selectedItem = loopCity.getSelectedItem();
            if (selectedItem > children.size() - 1) {
                selectedItem = children.size() - 1;
            }
            AreaBean city = province.getChildren().get(selectedItem);
            List<String> list = new ArrayList<>();
            for (AreaBean a : city.getChildren()) {
                list.add(a.getAreaName());
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
        AreaBean province = dataList.get(selectedItem);
        List<String> list = new ArrayList<>();
        for (AreaBean a : province.getChildren()) {
            list.add(a.getAreaName());
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
        for (AreaBean a : dataList) {
            provinces.add(a.getAreaName());
        }
        loopProvince.setItems(provinces);
        loopCity.setCurrentPosition(4);
    }

    private static final String TAG = "AreaSelectDialog";


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
