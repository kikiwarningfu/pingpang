package heyong.intellectPinPang.ui.competition.activity;

import android.Manifest;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.media.ExifInterface;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.lifecycle.Observer;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.bigkoo.pickerview.TimePickerView;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.google.gson.Gson;
import com.nanchen.compresshelper.CompressHelper;
import com.qiniu.android.http.ResponseInfo;
import com.qiniu.android.storage.UpCompletionHandler;
import com.qiniu.android.storage.UploadManager;
import com.tbruyelle.rxpermissions2.RxPermissions;
import com.weigan.loopview.dialog.AreaSelectDialog;
import com.weigan.loopview.dialog.AreaSelectNewDialog;

import org.json.JSONObject;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.Random;
import java.util.Set;

import heyong.intellectPinPang.ui.BaseActivity;
import heyong.handong.framework.base.ResponseBean;
import heyong.handong.framework.utils.ImageLoader;
import heyong.intellectPinPang.R;
import heyong.intellectPinPang.adapter.game.HouduAdapter;
import heyong.intellectPinPang.adapter.game.HouduHardAdapter;
import heyong.intellectPinPang.adapter.game.KeyBoradAdapter;
import heyong.intellectPinPang.adapter.game.KeyBoradXinghaoAdapter;
import heyong.intellectPinPang.bean.competition.AllMyInfoBean;
import heyong.intellectPinPang.bean.competition.ModelDataBrandIdBean;
import heyong.intellectPinPang.bean.competition.UpdateXlUserInfoBean;
import heyong.intellectPinPang.bean.competition.XlEquipmentBrandListBean;
import heyong.intellectPinPang.databinding.ActivityPersonalInformationBinding;
import heyong.intellectPinPang.ui.SignInterfaceActivity;
import heyong.intellectPinPang.ui.mine.MineViewModel;
import heyong.intellectPinPang.utils.AntiShakeUtils;
import heyong.intellectPinPang.utils.CommonUtilis;
import heyong.intellectPinPang.utils.ImagePickerUtil;
import heyong.intellectPinPang.utils.MessageDialogBuilder;
import heyong.intellectPinPang.utils.RxBusSubscriber;
import heyong.intellectPinPang.widget.PhotoSelDialog;
import heyong.intellectPinPang.widget.popupwindow.CommonPopupWindow;
import heyong.lzy.imagepicker.ImagePicker;
import heyong.lzy.imagepicker.bean.ImageItem;
import heyong.lzy.imagepicker.ui.ImageGridActivity;

/**
 * 完善个人信息界面UI
 */
public class PersonalInformationActivity extends BaseActivity<ActivityPersonalInformationBinding, MineViewModel> implements View.OnClickListener, PhotoSelDialog.OnSelectListener, ImagePickerUtil.OnImageCallback, AreaSelectDialog.OnAreaSelectedListener, AreaSelectNewDialog.OnAreaSelectedListener {
    public static final int REQUEST_CODE_SELECT_PHOTO = 1; //图片-相册

    public static final int REQUEST_CODE_TAKE_PHOTO = 2;   //拍照-相机
    private static final int IMAGE_PICKER = 10001;
    private static final int REQUEST_CODE_SELECT = 10002;
    private static final int REQUEST_SIGN_IN = 10003;
    private ImagePickerUtil imagePickerUtil;
    String key = "";
    String picPath = "";
    private String mCoverLogo = "";//个人头像
    public static final String TAG = PersonalInformationActivity.class.getSimpleName();
    private int sexType = -1;
    boolean islMaxCount = false;
    private String strCity = "";
    private String signPath = "";
    private String strEndTime = "";
    UpdateXlUserInfoBean updateXlUserInfoBean;

    private int chipaiType = -1;
    private int wopaiType = -1;
    private int zhengshouType = -1;
    private int fanshouType = -1;
    CommonPopupWindow mShowEquipmentWindow;
    private int bardType = -1;//点击品牌的type
    private int taoJiaoType = -1;// 1是正手  2是反手
    private int oneTypes = -1;//显示在哪个型号下面
    List<XlEquipmentBrandListBean.ListBean> list;//
    List<ModelDataBrandIdBean.XlModelThicksBean> xlModelThicksZheng = new ArrayList<>();
    List<ModelDataBrandIdBean.XlModelThicksBean> xlModelThicksFan = new ArrayList<>();
    List<ModelDataBrandIdBean.XlModelHardnessBean> xlModelHardnessZheng = new ArrayList<>();//硬度的集合
    List<ModelDataBrandIdBean.XlModelHardnessBean> xlModelHardnessFan = new ArrayList<>();//硬度的集合
    private String selectOneId = "";
    private String zhengjiaoId = "";
    private String fanjiaoId = "";

    private String oneXinghaoId = "";
    private String twoXinghaoId = "";
    private String threeXinghaoId = "";

    private String houduZhengshouId = "";
    private String houduFanshouId = "";

    private String hardZhengshouId = "";
    private String hardFanshouId = "";
    private int oneShowType = -1;  //1 展示第一个  2  展示第二个
    private int oneHardType = -1;//  1 展示厚度   2展示硬度
    private int selectHardHouduType = -1;// 1正手套胶  2 是反手套胶
    private int selectYingduType = -1;// 1正手套胶  2 是反手套胶

    private String zhengshouColorId = "";//正手的颜色id
    private String fanshouColorId = "";//反手的颜色id

    private String strCloth = "";//衣服尺码
    private String shoesCloth = "";//鞋子尺码
    private int showType = 1;//1是没有数据

    @Override
    public int[] hideSoftByEditViewIds() {
        int[] ids = {R.id.et_nick_name, R.id.et_personal_sign, R.id.et_right_name, R.id.et_tel};
        return ids;
    }

    @Override
    public int getLayoutRes() {
        return R.layout.activity_personal_information;
    }

    @Override
    public void initView(@Nullable Bundle savedInstanceState) {
        mBinding.setListener(this);
        imagePickerUtil = new ImagePickerUtil();
        mViewModel.getAllMyInfo();
        mBinding.etTel.setTextColor(Color.BLACK);
        updateXlUserInfoBean = new UpdateXlUserInfoBean();
        zhengshouColorId = "1";
        fanshouColorId = "2";
        mBinding.llZhengshou.setBackgroundResource(R.drawable.shape_limit_people_blue_solid_blue);
        mBinding.llFanshou.setBackground(null);
        mBinding.llBasicInformation.setVisibility(View.VISIBLE);
        mBinding.llGameInformation.setVisibility(View.GONE);
        mBinding.llEquipmentInformation.setVisibility(View.GONE);


        mViewModel.getAllMyInfoLiveData.observe(this, new Observer<ResponseBean<AllMyInfoBean>>() {
            @Override
            public void onChanged(ResponseBean<AllMyInfoBean> responseBean) {

                AllMyInfoBean data = responseBean.getData();
                if (data != null) {
                    showType = 2;
                    mCoverLogo = data.getHeadImg();
                    updateXlUserInfoBean.setHeadImg(mCoverLogo);
                    mBinding.etNickName.setText("" + data.getNickName());
                    updateXlUserInfoBean.setNickName("" + data.getNickName());
                    mBinding.etPersonalSign.setText("" + data.getOwnSign());
                    updateXlUserInfoBean.setOwnSign("" + data.getOwnSign());
                    mBinding.etRightName.setText("" + data.getName());
                    updateXlUserInfoBean.setName("" + data.getName());
                    strEndTime = "" + data.getBornDate();
                    mBinding.tvRightBirthday.setText("" + strEndTime);
                    mBinding.tvRightBirthday.setTextColor(Color.BLACK);
                    updateXlUserInfoBean.setBornDate(strEndTime);
                    int sex = Integer.parseInt(data.getSex());
                    if (sex == 1) {
                        //男
                        mBinding.cbTheMan.setChecked(true);
                        mBinding.cbTheWoman.setChecked(false);
                        sexType = 1;
                    } else if (sex == 2) {
                        mBinding.cbTheMan.setChecked(false);
                        mBinding.cbTheWoman.setChecked(true);
                        sexType = 2;
                    }
                    updateXlUserInfoBean.setSex("" + sexType);
                    strCity = data.getAddress();
                    mBinding.tvDetailAddress.setText("" + strCity);
                    updateXlUserInfoBean.setAddress(strCity);
                    mBinding.etTel.setText("" + data.getAccount());
                    updateXlUserInfoBean.setPhoneNumber("" + data.getAccount());
                    signPath = data.getAutographImg();
                    mBinding.rlContent.setVisibility(View.GONE);
                    mBinding.ivHardSignature.setVisibility(View.VISIBLE);
                    ImageLoader.loadImage(mBinding.ivHardSignature, signPath);
                    ImageLoader.loadImage(mBinding.ivAvatar, data.getHeadImg(), R.drawable.img_default_avatar, R.drawable.img_default_avatar);
                    updateXlUserInfoBean.setAutographImg(signPath);

                    switch (data.getClapHandName()) {
                        case "右手":
                            chipaiType = 2;
                            mBinding.tvChipaishouRight.setBackgroundResource(R.drawable.shape_club_blue);
                            mBinding.tvChipaishouRight.setTextColor(Color.parseColor("#FFFFFFFF"));
                            mBinding.tvChipaishouLeft.setBackgroundResource(R.drawable.shape_club_gray_little);
                            mBinding.tvChipaishouLeft.setTextColor(Color.parseColor("#FF666666"));
                            break;
                        case "左手":
                            chipaiType = 1;
                            mBinding.tvChipaishouLeft.setBackgroundResource(R.drawable.shape_club_blue);
                            mBinding.tvChipaishouLeft.setTextColor(Color.parseColor("#FFFFFFFF"));
                            mBinding.tvChipaishouRight.setBackgroundResource(R.drawable.shape_club_gray_little);
                            mBinding.tvChipaishouRight.setTextColor(Color.parseColor("#FF666666"));
                            break;
                    }
                    switch (data.getGripMethonName()) {
                        case "横板":
                            wopaiType = 2;
                            mBinding.tvIvHengbanRight.setBackgroundResource(R.drawable.shape_club_blue);
                            mBinding.tvIvHengbanRight.setTextColor(Color.parseColor("#FFFFFFFF"));
                            mBinding.tvStraightPanelLeft.setBackgroundResource(R.drawable.shape_club_gray_little);
                            mBinding.tvStraightPanelLeft.setTextColor(Color.parseColor("#FF666666"));
                            break;
                        case "直板":
                            wopaiType = 1;
                            /*tv_straight_panel_left*/
                            mBinding.tvStraightPanelLeft.setBackgroundResource(R.drawable.shape_club_blue);
                            mBinding.tvStraightPanelLeft.setTextColor(Color.parseColor("#FFFFFFFF"));
                            mBinding.tvIvHengbanRight.setBackgroundResource(R.drawable.shape_club_gray_little);
                            mBinding.tvIvHengbanRight.setTextColor(Color.parseColor("#FF666666"));
                            break;
                    }
                    updateXlUserInfoBean.setClapHand("" + chipaiType);
                    updateXlUserInfoBean.setGripMethon("" + wopaiType);
                    //正手
                    switch (data.getRightPlayMethonName()) {
                        case "正胶"://4
                            zhengshouType = 4;
                            mBinding.tvZhengshouZhengjiao.setBackgroundResource(R.drawable.shape_club_blue);
                            mBinding.tvZhengshouZhengjiao.setTextColor(Color.parseColor("#FFFFFFFF"));
                            mBinding.tvZhengshouFanjiao.setBackgroundResource(R.drawable.shape_club_gray_little);
                            mBinding.tvZhengshouFanjiao.setTextColor(Color.parseColor("#FF666666"));
                            mBinding.tvZhengshouChangjiao.setBackgroundResource(R.drawable.shape_club_gray_little);
                            mBinding.tvZhengshouChangjiao.setTextColor(Color.parseColor("#FF666666"));
                            mBinding.tvZhengshouShengjiao.setBackgroundResource(R.drawable.shape_club_gray_little);
                            mBinding.tvZhengshouShengjiao.setTextColor(Color.parseColor("#FF666666"));
                            break;
                        case "反胶"://1
                            zhengshouType = 1;
                            mBinding.tvZhengshouFanjiao.setBackgroundResource(R.drawable.shape_club_blue);
                            mBinding.tvZhengshouFanjiao.setTextColor(Color.parseColor("#FFFFFFFF"));
                            mBinding.tvZhengshouChangjiao.setBackgroundResource(R.drawable.shape_club_gray_little);
                            mBinding.tvZhengshouChangjiao.setTextColor(Color.parseColor("#FF666666"));
                            mBinding.tvZhengshouShengjiao.setBackgroundResource(R.drawable.shape_club_gray_little);
                            mBinding.tvZhengshouShengjiao.setTextColor(Color.parseColor("#FF666666"));
                            mBinding.tvZhengshouZhengjiao.setBackgroundResource(R.drawable.shape_club_gray_little);
                            mBinding.tvZhengshouZhengjiao.setTextColor(Color.parseColor("#FF666666"));
                            break;
                        case "长胶"://2
                            zhengshouType = 2;
                            mBinding.tvZhengshouChangjiao.setBackgroundResource(R.drawable.shape_club_blue);
                            mBinding.tvZhengshouChangjiao.setTextColor(Color.parseColor("#FFFFFFFF"));
                            mBinding.tvZhengshouFanjiao.setBackgroundResource(R.drawable.shape_club_gray_little);
                            mBinding.tvZhengshouFanjiao.setTextColor(Color.parseColor("#FF666666"));
                            mBinding.tvZhengshouShengjiao.setBackgroundResource(R.drawable.shape_club_gray_little);
                            mBinding.tvZhengshouShengjiao.setTextColor(Color.parseColor("#FF666666"));
                            mBinding.tvZhengshouZhengjiao.setBackgroundResource(R.drawable.shape_club_gray_little);
                            mBinding.tvZhengshouZhengjiao.setTextColor(Color.parseColor("#FF666666"));
                            break;
                        case "生胶"://3
                            zhengshouType = 3;
                            mBinding.tvZhengshouShengjiao.setBackgroundResource(R.drawable.shape_club_blue);
                            mBinding.tvZhengshouShengjiao.setTextColor(Color.parseColor("#FFFFFFFF"));
                            mBinding.tvZhengshouFanjiao.setBackgroundResource(R.drawable.shape_club_gray_little);
                            mBinding.tvZhengshouFanjiao.setTextColor(Color.parseColor("#FF666666"));
                            mBinding.tvZhengshouChangjiao.setBackgroundResource(R.drawable.shape_club_gray_little);
                            mBinding.tvZhengshouChangjiao.setTextColor(Color.parseColor("#FF666666"));
                            mBinding.tvZhengshouZhengjiao.setBackgroundResource(R.drawable.shape_club_gray_little);
                            mBinding.tvZhengshouZhengjiao.setTextColor(Color.parseColor("#FF666666"));
                            break;
                    }

                    updateXlUserInfoBean.setRightPlayMethon("" + zhengshouType);
                    //leftPlayMethonName  反手
                    switch (data.getLeftPlayMethonName()) {
                        case "反胶"://1
                            fanshouType = 1;
                            mBinding.tvFanshouFanjiao.setBackgroundResource(R.drawable.shape_club_blue);
                            mBinding.tvFanshouFanjiao.setTextColor(Color.parseColor("#FFFFFFFF"));
                            mBinding.tvFanshouChangjiao.setBackgroundResource(R.drawable.shape_club_gray_little);
                            mBinding.tvFanshouChangjiao.setTextColor(Color.parseColor("#FF666666"));
                            mBinding.tvFanshouShengjiao.setBackgroundResource(R.drawable.shape_club_gray_little);
                            mBinding.tvFanshouShengjiao.setTextColor(Color.parseColor("#FF666666"));
                            mBinding.tvFanshouZhengjiao.setBackgroundResource(R.drawable.shape_club_gray_little);
                            mBinding.tvFanshouZhengjiao.setTextColor(Color.parseColor("#FF666666"));
                            break;
                        case "长胶"://2
                            fanshouType = 2;
                            mBinding.tvFanshouChangjiao.setBackgroundResource(R.drawable.shape_club_blue);
                            mBinding.tvFanshouChangjiao.setTextColor(Color.parseColor("#FFFFFFFF"));
                            mBinding.tvFanshouFanjiao.setBackgroundResource(R.drawable.shape_club_gray_little);
                            mBinding.tvFanshouFanjiao.setTextColor(Color.parseColor("#FF666666"));
                            mBinding.tvFanshouShengjiao.setBackgroundResource(R.drawable.shape_club_gray_little);
                            mBinding.tvFanshouShengjiao.setTextColor(Color.parseColor("#FF666666"));
                            mBinding.tvFanshouZhengjiao.setBackgroundResource(R.drawable.shape_club_gray_little);
                            mBinding.tvFanshouZhengjiao.setTextColor(Color.parseColor("#FF666666"));
                            break;
                        case "生胶"://3
                            fanshouType = 3;
                            mBinding.tvFanshouShengjiao.setBackgroundResource(R.drawable.shape_club_blue);
                            mBinding.tvFanshouShengjiao.setTextColor(Color.parseColor("#FFFFFFFF"));
                            mBinding.tvFanshouFanjiao.setBackgroundResource(R.drawable.shape_club_gray_little);
                            mBinding.tvFanshouFanjiao.setTextColor(Color.parseColor("#FF666666"));
                            mBinding.tvFanshouChangjiao.setBackgroundResource(R.drawable.shape_club_gray_little);
                            mBinding.tvFanshouChangjiao.setTextColor(Color.parseColor("#FF666666"));
                            mBinding.tvFanshouZhengjiao.setBackgroundResource(R.drawable.shape_club_gray_little);
                            mBinding.tvFanshouZhengjiao.setTextColor(Color.parseColor("#FF666666"));
                            break;
                        case "正胶"://4
                            fanshouType = 4;
                            mBinding.tvFanshouZhengjiao.setBackgroundResource(R.drawable.shape_club_blue);
                            mBinding.tvFanshouZhengjiao.setTextColor(Color.parseColor("#FFFFFFFF"));
                            mBinding.tvFanshouFanjiao.setBackgroundResource(R.drawable.shape_club_gray_little);
                            mBinding.tvFanshouFanjiao.setTextColor(Color.parseColor("#FF666666"));
                            mBinding.tvFanshouChangjiao.setBackgroundResource(R.drawable.shape_club_gray_little);
                            mBinding.tvFanshouChangjiao.setTextColor(Color.parseColor("#FF666666"));
                            mBinding.tvFanshouShengjiao.setBackgroundResource(R.drawable.shape_club_gray_little);
                            mBinding.tvFanshouShengjiao.setTextColor(Color.parseColor("#FF666666"));
                            break;
                    }

                    updateXlUserInfoBean.setLeftPlayMethon("" + fanshouType);
                    updateXlUserInfoBean.setDressSize("" + data.getDressSize());
                    updateXlUserInfoBean.setShoesSize("" + data.getShoesSize());
                    int dressSize = Integer.parseInt(data.getShoesSize());
                    switch (dressSize) {
                        case 41:
                            shoesCloth = "41";
                            showNoSelectSHose();
                            mBinding.tvShoose41.setTextColor(Color.WHITE);
                            mBinding.tvShoose41.setBackgroundResource(R.drawable.shape_club_blue);
                            break;
                        case 42:
                            shoesCloth = "42";
                            showNoSelectSHose();
                            mBinding.tvShoose42.setTextColor(Color.WHITE);
                            mBinding.tvShoose42.setBackgroundResource(R.drawable.shape_club_blue);
                            break;
                        case 43:
                            shoesCloth = "43";
                            showNoSelectSHose();
                            mBinding.tvShoose43.setTextColor(Color.WHITE);
                            mBinding.tvShoose43.setBackgroundResource(R.drawable.shape_club_blue);
                            break;
                        case 44:
                            shoesCloth = "44";
                            showNoSelectSHose();
                            mBinding.tvShoose44.setTextColor(Color.WHITE);
                            mBinding.tvShoose44.setBackgroundResource(R.drawable.shape_club_blue);
                            break;
                        case 45:
                            shoesCloth = "45";
                            showNoSelectSHose();
                            mBinding.tvShoose45.setTextColor(Color.WHITE);
                            mBinding.tvShoose45.setBackgroundResource(R.drawable.shape_club_blue);
                            break;
                        case 36:
                            shoesCloth = "36";
                            showNoSelectSHose();
                            mBinding.tvShose36.setTextColor(Color.WHITE);
                            mBinding.tvShose36.setBackgroundResource(R.drawable.shape_club_blue);
                            break;
                        case 37:
                            shoesCloth = "37";
                            showNoSelectSHose();
                            mBinding.tvShose37.setTextColor(Color.WHITE);
                            mBinding.tvShose37.setBackgroundResource(R.drawable.shape_club_blue);
                            break;
                        case 38:
                            shoesCloth = "38";
                            showNoSelectSHose();
                            mBinding.tvShose38.setTextColor(Color.WHITE);
                            mBinding.tvShose38.setBackgroundResource(R.drawable.shape_club_blue);
                            break;
                        case 39:
                            shoesCloth = "39";
                            showNoSelectSHose();
                            mBinding.tvShose39.setTextColor(Color.WHITE);
                            mBinding.tvShose39.setBackgroundResource(R.drawable.shape_club_blue);
                            break;
                        case 40:
                            shoesCloth = "40";
                            showNoSelectSHose();
                            mBinding.tvShose40.setTextColor(Color.WHITE);
                            mBinding.tvShose40.setBackgroundResource(R.drawable.shape_club_blue);
                            break;
                        case 31:
                            shoesCloth = "31";
                            showNoSelectSHose();
                            mBinding.tvShose31.setTextColor(Color.WHITE);
                            mBinding.tvShose31.setBackgroundResource(R.drawable.shape_club_blue);
                            break;
                        case 32:
                            shoesCloth = "32";
                            showNoSelectSHose();
                            mBinding.tvShose32.setTextColor(Color.WHITE);
                            mBinding.tvShose32.setBackgroundResource(R.drawable.shape_club_blue);
                            break;
                        case 33:
                            shoesCloth = "33";
                            showNoSelectSHose();
                            mBinding.tvShose33.setTextColor(Color.WHITE);
                            mBinding.tvShose33.setBackgroundResource(R.drawable.shape_club_blue);
                            break;
                        case 34:
                            shoesCloth = "34";
                            showNoSelectSHose();
                            mBinding.tvShose34.setTextColor(Color.WHITE);
                            mBinding.tvShose34.setBackgroundResource(R.drawable.shape_club_blue);
                            break;
                        case 35:
                            shoesCloth = "35";
                            showNoSelectSHose();
                            mBinding.tvShose35.setTextColor(Color.WHITE);
                            mBinding.tvShose35.setBackgroundResource(R.drawable.shape_club_blue);
                            break;
                    }
                    switch (data.getDressSize()) {
                        case "XL":
                            strCloth = "XL";
                            showNoSelect();
                            mBinding.tvXl.setBackgroundResource(R.drawable.shape_club_blue);
                            mBinding.tvXl.setTextColor(Color.WHITE);
                            break;
                        case "2XL":
                            strCloth = "2XL";
                            showNoSelect();
                            mBinding.tv2xl.setBackgroundResource(R.drawable.shape_club_blue);
                            mBinding.tv2xl.setTextColor(Color.WHITE);
                            break;
                        case "3XL":
                            strCloth = "3XL";
                            showNoSelect();
                            mBinding.tv3xl.setBackgroundResource(R.drawable.shape_club_blue);
                            mBinding.tv3xl.setTextColor(Color.WHITE);
                            break;
                        case "4XL":
                            strCloth = "4XL";
                            showNoSelect();
                            mBinding.tv4xl.setBackgroundResource(R.drawable.shape_club_blue);
                            mBinding.tv4xl.setTextColor(Color.WHITE);
                            break;
                        case "5XL":
                            strCloth = "5XL";
                            showNoSelect();
                            mBinding.tv5xl.setBackgroundResource(R.drawable.shape_club_blue);
                            mBinding.tv5xl.setTextColor(Color.WHITE);
                            break;
                        case "S":
                            strCloth = "S";
                            showNoSelect();
                            mBinding.tvS.setBackgroundResource(R.drawable.shape_club_blue);
                            mBinding.tvS.setTextColor(Color.WHITE);
                            break;
                        case "M":
                            strCloth = "M";
                            showNoSelect();
                            mBinding.tvM.setBackgroundResource(R.drawable.shape_club_blue);
                            mBinding.tvM.setTextColor(Color.WHITE);
                            break;
                        case "L":
                            strCloth = "L";
                            showNoSelect();
                            mBinding.tvL.setBackgroundResource(R.drawable.shape_club_blue);
                            mBinding.tvL.setTextColor(Color.WHITE);
                            break;

                    }
                    selectOneId = "" + data.getFloorBrandId();
                    oneXinghaoId = "" + data.getFloorModelId();
                    zhengjiaoId = "" + data.getRightBrandId();
                    twoXinghaoId = "" + data.getRightModelId();
                    houduZhengshouId = "" + data.getRightLandId();
                    hardZhengshouId = "" + data.getRightHardId();
                    zhengshouColorId = "" + data.getRightColorId();
                    updateXlUserInfoBean.setFloorBrandId(selectOneId);
                    updateXlUserInfoBean.setFloorModelId(oneXinghaoId);
                    updateXlUserInfoBean.setRightBrandId(zhengjiaoId);
                    updateXlUserInfoBean.setRightModelId(twoXinghaoId);
                    updateXlUserInfoBean.setRightLandId(houduZhengshouId);
                    updateXlUserInfoBean.setRightHardId(hardZhengshouId);
                    updateXlUserInfoBean.setRightColorId(zhengshouColorId);
                    fanjiaoId = "" + data.getLeftBrandId();
                    threeXinghaoId = "" + data.getLeftModelId();
                    houduFanshouId = "" + data.getLeftLandId();
                    hardFanshouId = "" + data.getLeftHardId();
                    fanshouColorId = "" + data.getLeftColorId();
                    updateXlUserInfoBean.setLeftBrandId(fanjiaoId);
                    updateXlUserInfoBean.setLeftModelId(threeXinghaoId);
                    updateXlUserInfoBean.setLeftLandId(houduFanshouId);
                    updateXlUserInfoBean.setLeftHardId(hardFanshouId);
                    updateXlUserInfoBean.setLeftColorId(fanshouColorId);
                    mBinding.tvBrandLeftOne.setText("" + data.getFloorBrandName());//底板 横版品牌
                    mBinding.tvLeftBrandTwo.setText("" + data.getFloorModelName());//底板 横版 型号

                    mBinding.tvTaojiaoPinpaiZheng.setText("" + data.getRightBrandName());//正手桃胶  品牌
                    mBinding.tvXinghaoZheng.setText("" + data.getRightModelName());//正手套胶  型号正
                    mBinding.tvHouduZhengshou.setText("" + data.getRightLandName());//正手套胶  厚度
                    mBinding.tvYingduZhengshou.setText("" + data.getRightHardName());

                    mBinding.tvTaojiaoFan.setText("" + data.getLeftBrandName());
                    mBinding.tvXinghaoFan.setText("" + data.getLeftModelName());
                    mBinding.tvFanshouHoudu.setText("" + data.getLeftLandName());
                    mBinding.tvYingduFanshou.setText("" + data.getLeftHardName());

                    if (data.getRightColorId() == 1) {
                        //正手红色  正红反黑
                        zhengshouColorId = "1";
                        fanshouColorId = "2";
                        mBinding.llZhengshou.setBackgroundResource(R.drawable.shape_limit_people_blue_solid_blue);
                        mBinding.llFanshou.setBackground(null);
                    } else {
                        //正手黑色   正黑反红
                        zhengshouColorId = "2";
                        fanshouColorId = "1";
                        mBinding.llFanshou.setBackgroundResource(R.drawable.shape_limit_people_blue_solid_blue);
                        mBinding.llZhengshou.setBackground(null);
                    }

                } else {
                    showType = 1;
                }

            }
        });
        initContentView();
    }

    private void initContentView() {
        mBinding.llBasicInformation.setVisibility(View.VISIBLE);
        mBinding.llGameInformation.setVisibility(View.GONE);
        mBinding.llEquipmentInformation.setVisibility(View.GONE);

        mBinding.viewBasicInformationTitle.setBackgroundResource(R.drawable.shape_competition_blue_circle);
        mBinding.viewEquipmentInformationTitle.setBackgroundResource(R.drawable.shape_competition_gray_circle);
        mBinding.viewGameInformationTitle.setBackgroundResource(R.drawable.shape_competition_gray_circle);
        mBinding.tvBasicInformationTitle.setTextColor(Color.parseColor("#ff4795ed"));
        mBinding.tvEquipmentInformationTitle.setTextColor(Color.parseColor("#333333"));
        mBinding.tvGameInformationTitle.setTextColor(Color.parseColor("#333333"));
        mBinding.etPersonalSign.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                int detailLength = s.length();
                mBinding.tvPersonalSignNum.setText(detailLength + "/20");
                if (detailLength == 19) {
                    islMaxCount = true;
                }
                // 不知道为什么执行俩次，所以增加一个标识符去标识
                if (detailLength == 19 && islMaxCount) {
                    Log.e(TAG, "afterTextChanged: 到达最大数目");
//                    UIHelper.getShortToast(self, (String) StringUtils.getResourceContent(self, Convention.RESOURCE_TYPE_STRING, R.string.string_editor_detail_input_limit));
                    islMaxCount = false;
                }

            }
        });
        mViewModel.getUploadTokenLiveData.observe(this, new Observer<ResponseBean>() {
            @Override
            public void onChanged(ResponseBean responseBean) {
//                if (responseBean.isSuccess()) {
                if (responseBean.getErrorCode().equals("200")) {

                    UploadManager uploadManager = new UploadManager();
                    // 设置图片名字
                    uploadImage(picPath, responseBean.getData().toString(), uploadManager, key);
                } else {
                    toast("暂无权限");
                }
            }
        });
        //点击品牌
        mViewModel.getXlEquipmentBrandListLiveData.observe(this, new Observer<ResponseBean<XlEquipmentBrandListBean>>() {
            @Override
            public void onChanged(ResponseBean<XlEquipmentBrandListBean> xlEquipmentBrandListBeanResponseBean) {
                dismissLoading();
                if (xlEquipmentBrandListBeanResponseBean.getData() != null) {
                    list = xlEquipmentBrandListBeanResponseBean.getData().getList();
                    if (list != null && list.size() > 0) {
                        if (bardType == 1) {
                            mBinding.tvBrandLeftOne.post(new Runnable() {
                                @Override
                                public void run() {
                                    oneShowType = 1;//地板 品牌
                                    int height = mBinding.tvBrandLeftOne.getHeight();
                                    showPopBaseBorad(mBinding.tvBrandLeftOne, height, list);

                                }
                            });
                        } else {
                            if (taoJiaoType == 1) {

                                mBinding.tvTaojiaoPinpaiZheng.post(new Runnable() {
                                    @Override
                                    public void run() {
                                        oneShowType = 1;//地板 品牌
                                        int height = mBinding.tvTaojiaoPinpaiZheng.getHeight();
                                        showPopBaseBorad(mBinding.tvTaojiaoPinpaiZheng, height, list);

                                    }
                                });
                            } else {
                                mBinding.tvTaojiaoFan.post(new Runnable() {
                                    @Override
                                    public void run() {
                                        oneShowType = 1;//地板 品牌
                                        int height = mBinding.tvTaojiaoFan.getHeight();
                                        showPopBaseBorad(mBinding.tvTaojiaoFan, height, list);

                                    }
                                });
                            }
                        }
                    }
                }
            }
        });
        //型号的windiw
        mViewModel.getModelDataByBrandIdLiveData.observe(this, new Observer<ResponseBean<List<ModelDataBrandIdBean>>>() {
            @Override
            public void onChanged(ResponseBean<List<ModelDataBrandIdBean>> responseBean) {
                List<ModelDataBrandIdBean> data = responseBean.getData();
                if (data != null) {
                    if (oneTypes == 1) {//底板的型号
                        mBinding.tvLeftBrandTwo.post(new Runnable() {
                            @Override
                            public void run() {
                                oneShowType = 2;//地板  型号
                                int height = mBinding.tvLeftBrandTwo.getHeight();
                                showPopXinghaoWindow(mBinding.tvLeftBrandTwo, height, data);
                            }
                        });

                    } else if (oneTypes == 2) {//正手套胶的型号

                        mBinding.tvXinghaoZheng.post(new Runnable() {
                            @Override
                            public void run() {
                                oneShowType = 2;//地板  型号
                                int height = mBinding.tvXinghaoZheng.getHeight();
                                showPopXinghaoWindow(mBinding.tvXinghaoZheng, height, data);
                            }
                        });

                    } else if (oneTypes == 3) {//

                        mBinding.tvXinghaoFan.post(new Runnable() {
                            @Override
                            public void run() {
                                oneShowType = 2;//地板  型号
                                int height = mBinding.tvXinghaoFan.getHeight();
                                showPopXinghaoWindow(mBinding.tvXinghaoFan, height, data);
                            }
                        });

                    }
                }
                xlModelThicksFan.clear();
                xlModelThicksZheng.clear();
//                xlModelHardnessZheng.clear();
//                xlModelHardnessFan.clear();
            }
        });
        //完善个人信息
        mViewModel.updateXlUserInfoLiveData.observe(this, new Observer<ResponseBean>() {
            @Override
            public void onChanged(ResponseBean responseBean) {
//                if (responseBean.isSuccess()) {
                if (responseBean.getErrorCode().equals("200")) {

                    dismissLoading();
                    new MessageDialogBuilder(PersonalInformationActivity.this)
                            .setTvTitle("保存成功")
                            .setMessage("")
                            .setTvSure("确定")
                            .setBtnCancleHint(true)
                            .setSureListener(v1 ->
                                    finish()
                            )
                            .show();
                } else {

                    toast("" + responseBean.getMessage());
                }
            }
        });


    }

    @Override
            public void onClick(View v) {
                switch (v.getId()) {
            case R.id.ll_zhengshou://  //正手红色  正红反黑
                if (AntiShakeUtils.isInvalidClick(v))
                    return;
                zhengshouColorId = "1";
                fanshouColorId = "2";
                mBinding.llZhengshou.setBackgroundResource(R.drawable.shape_limit_people_blue_solid_blue);
                mBinding.llFanshou.setBackground(null);
                break;
            case R.id.ll_fanshou://    //正手黑色   正黑反红
                if (AntiShakeUtils.isInvalidClick(v))
                    return;
                zhengshouColorId = "2";
                fanshouColorId = "1";
                mBinding.llFanshou.setBackgroundResource(R.drawable.shape_limit_people_blue_solid_blue);
                mBinding.llZhengshou.setBackground(null);
                break;

            case R.id.tv_chipaishou_left://持拍手左点击
                if (AntiShakeUtils.isInvalidClick(v))
                    return;
                chipaiType = 1;
                mBinding.tvChipaishouLeft.setBackgroundResource(R.drawable.shape_club_blue);
                mBinding.tvChipaishouLeft.setTextColor(Color.parseColor("#FFFFFFFF"));
                mBinding.tvChipaishouRight.setBackgroundResource(R.drawable.shape_club_gray_little);
                mBinding.tvChipaishouRight.setTextColor(Color.parseColor("#FF666666"));
                break;
            case R.id.tv_chipaishou_right://持拍手右点击
                if (AntiShakeUtils.isInvalidClick(v))
                    return;
                chipaiType = 2;
                mBinding.tvChipaishouRight.setBackgroundResource(R.drawable.shape_club_blue);
                mBinding.tvChipaishouRight.setTextColor(Color.parseColor("#FFFFFFFF"));
                mBinding.tvChipaishouLeft.setBackgroundResource(R.drawable.shape_club_gray_little);
                mBinding.tvChipaishouLeft.setTextColor(Color.parseColor("#FF666666"));

                break;
            case R.id.iv_straight_panel://直板
            case R.id.tv_straight_panel_left://直板
                if (AntiShakeUtils.isInvalidClick(v))
                    return;
                wopaiType = 1;
                /*tv_straight_panel_left*/
                mBinding.tvStraightPanelLeft.setBackgroundResource(R.drawable.shape_club_blue);
                mBinding.tvStraightPanelLeft.setTextColor(Color.parseColor("#FFFFFFFF"));
                mBinding.tvIvHengbanRight.setBackgroundResource(R.drawable.shape_club_gray_little);
                mBinding.tvIvHengbanRight.setTextColor(Color.parseColor("#FF666666"));
                break;
            case R.id.iv_hengban:////横板
            case R.id.tv_iv_hengban_right:////横板
                if (AntiShakeUtils.isInvalidClick(v))
                    return;
                wopaiType = 2;
                mBinding.tvIvHengbanRight.setBackgroundResource(R.drawable.shape_club_blue);
                mBinding.tvIvHengbanRight.setTextColor(Color.parseColor("#FFFFFFFF"));
                mBinding.tvStraightPanelLeft.setBackgroundResource(R.drawable.shape_club_gray_little);
                mBinding.tvStraightPanelLeft.setTextColor(Color.parseColor("#FF666666"));

                break;
            case R.id.tv_zhengshou_fanjiao://反焦
                if (AntiShakeUtils.isInvalidClick(v))
                    return;
                zhengshouType = 1;
                mBinding.tvZhengshouFanjiao.setBackgroundResource(R.drawable.shape_club_blue);
                mBinding.tvZhengshouFanjiao.setTextColor(Color.parseColor("#FFFFFFFF"));
                mBinding.tvZhengshouChangjiao.setBackgroundResource(R.drawable.shape_club_gray_little);
                mBinding.tvZhengshouChangjiao.setTextColor(Color.parseColor("#FF666666"));
                mBinding.tvZhengshouShengjiao.setBackgroundResource(R.drawable.shape_club_gray_little);
                mBinding.tvZhengshouShengjiao.setTextColor(Color.parseColor("#FF666666"));
                mBinding.tvZhengshouZhengjiao.setBackgroundResource(R.drawable.shape_club_gray_little);
                mBinding.tvZhengshouZhengjiao.setTextColor(Color.parseColor("#FF666666"));
                break;
            case R.id.tv_zhengshou_changjiao://长焦
                if (AntiShakeUtils.isInvalidClick(v))
                    return;
                zhengshouType = 2;
                mBinding.tvZhengshouChangjiao.setBackgroundResource(R.drawable.shape_club_blue);
                mBinding.tvZhengshouChangjiao.setTextColor(Color.parseColor("#FFFFFFFF"));
                mBinding.tvZhengshouFanjiao.setBackgroundResource(R.drawable.shape_club_gray_little);
                mBinding.tvZhengshouFanjiao.setTextColor(Color.parseColor("#FF666666"));
                mBinding.tvZhengshouShengjiao.setBackgroundResource(R.drawable.shape_club_gray_little);
                mBinding.tvZhengshouShengjiao.setTextColor(Color.parseColor("#FF666666"));
                mBinding.tvZhengshouZhengjiao.setBackgroundResource(R.drawable.shape_club_gray_little);
                mBinding.tvZhengshouZhengjiao.setTextColor(Color.parseColor("#FF666666"));

                break;
            case R.id.tv_zhengshou_shengjiao://shengjiao
                if (AntiShakeUtils.isInvalidClick(v))
                    return;
                zhengshouType = 3;

                mBinding.tvZhengshouShengjiao.setBackgroundResource(R.drawable.shape_club_blue);
                mBinding.tvZhengshouShengjiao.setTextColor(Color.parseColor("#FFFFFFFF"));
                mBinding.tvZhengshouFanjiao.setBackgroundResource(R.drawable.shape_club_gray_little);
                mBinding.tvZhengshouFanjiao.setTextColor(Color.parseColor("#FF666666"));
                mBinding.tvZhengshouChangjiao.setBackgroundResource(R.drawable.shape_club_gray_little);
                mBinding.tvZhengshouChangjiao.setTextColor(Color.parseColor("#FF666666"));
                mBinding.tvZhengshouZhengjiao.setBackgroundResource(R.drawable.shape_club_gray_little);
                mBinding.tvZhengshouZhengjiao.setTextColor(Color.parseColor("#FF666666"));

                break;
            case R.id.tv_zhengshou_zhengjiao://正饺
                if (AntiShakeUtils.isInvalidClick(v))
                    return;
                zhengshouType = 4;
                mBinding.tvZhengshouZhengjiao.setBackgroundResource(R.drawable.shape_club_blue);
                mBinding.tvZhengshouZhengjiao.setTextColor(Color.parseColor("#FFFFFFFF"));
                mBinding.tvZhengshouFanjiao.setBackgroundResource(R.drawable.shape_club_gray_little);
                mBinding.tvZhengshouFanjiao.setTextColor(Color.parseColor("#FF666666"));
                mBinding.tvZhengshouChangjiao.setBackgroundResource(R.drawable.shape_club_gray_little);
                mBinding.tvZhengshouChangjiao.setTextColor(Color.parseColor("#FF666666"));
                mBinding.tvZhengshouShengjiao.setBackgroundResource(R.drawable.shape_club_gray_little);
                mBinding.tvZhengshouShengjiao.setTextColor(Color.parseColor("#FF666666"));
                break;
            case R.id.tv_fanshou_fanjiao://反手反胶
                if (AntiShakeUtils.isInvalidClick(v))
                    return;
                fanshouType = 1;
                mBinding.tvFanshouFanjiao.setBackgroundResource(R.drawable.shape_club_blue);
                mBinding.tvFanshouFanjiao.setTextColor(Color.parseColor("#FFFFFFFF"));
                mBinding.tvFanshouChangjiao.setBackgroundResource(R.drawable.shape_club_gray_little);
                mBinding.tvFanshouChangjiao.setTextColor(Color.parseColor("#FF666666"));
                mBinding.tvFanshouShengjiao.setBackgroundResource(R.drawable.shape_club_gray_little);
                mBinding.tvFanshouShengjiao.setTextColor(Color.parseColor("#FF666666"));
                mBinding.tvFanshouZhengjiao.setBackgroundResource(R.drawable.shape_club_gray_little);
                mBinding.tvFanshouZhengjiao.setTextColor(Color.parseColor("#FF666666"));
                break;
            case R.id.tv_fanshou_changjiao://反手长焦
                if (AntiShakeUtils.isInvalidClick(v))
                    return;
                fanshouType = 2;
                mBinding.tvFanshouChangjiao.setBackgroundResource(R.drawable.shape_club_blue);
                mBinding.tvFanshouChangjiao.setTextColor(Color.parseColor("#FFFFFFFF"));
                mBinding.tvFanshouFanjiao.setBackgroundResource(R.drawable.shape_club_gray_little);
                mBinding.tvFanshouFanjiao.setTextColor(Color.parseColor("#FF666666"));
                mBinding.tvFanshouShengjiao.setBackgroundResource(R.drawable.shape_club_gray_little);
                mBinding.tvFanshouShengjiao.setTextColor(Color.parseColor("#FF666666"));
                mBinding.tvFanshouZhengjiao.setBackgroundResource(R.drawable.shape_club_gray_little);
                mBinding.tvFanshouZhengjiao.setTextColor(Color.parseColor("#FF666666"));

                break;
            case R.id.tv_fanshou_shengjiao://反手生胶
                if (AntiShakeUtils.isInvalidClick(v))
                    return;
                fanshouType = 3;
                mBinding.tvFanshouShengjiao.setBackgroundResource(R.drawable.shape_club_blue);
                mBinding.tvFanshouShengjiao.setTextColor(Color.parseColor("#FFFFFFFF"));
                mBinding.tvFanshouFanjiao.setBackgroundResource(R.drawable.shape_club_gray_little);
                mBinding.tvFanshouFanjiao.setTextColor(Color.parseColor("#FF666666"));
                mBinding.tvFanshouChangjiao.setBackgroundResource(R.drawable.shape_club_gray_little);
                mBinding.tvFanshouChangjiao.setTextColor(Color.parseColor("#FF666666"));
                mBinding.tvFanshouZhengjiao.setBackgroundResource(R.drawable.shape_club_gray_little);
                mBinding.tvFanshouZhengjiao.setTextColor(Color.parseColor("#FF666666"));
                break;
            case R.id.tv_fanshou_zhengjiao://反手正焦
                if (AntiShakeUtils.isInvalidClick(v))
                    return;
                fanshouType = 4;
                mBinding.tvFanshouZhengjiao.setBackgroundResource(R.drawable.shape_club_blue);
                mBinding.tvFanshouZhengjiao.setTextColor(Color.parseColor("#FFFFFFFF"));
                mBinding.tvFanshouFanjiao.setBackgroundResource(R.drawable.shape_club_gray_little);
                mBinding.tvFanshouFanjiao.setTextColor(Color.parseColor("#FF666666"));
                mBinding.tvFanshouChangjiao.setBackgroundResource(R.drawable.shape_club_gray_little);
                mBinding.tvFanshouChangjiao.setTextColor(Color.parseColor("#FF666666"));
                mBinding.tvFanshouShengjiao.setBackgroundResource(R.drawable.shape_club_gray_little);
                mBinding.tvFanshouShengjiao.setTextColor(Color.parseColor("#FF666666"));
                break;
            case R.id.rl_birthday:
                if (AntiShakeUtils.isInvalidClick(v))
                    return;
                TimePickerView pvTime = new TimePickerView.Builder(PersonalInformationActivity.this, new TimePickerView.OnTimeSelectListener() {
                    @Override
                    public void onTimeSelect(Date dateMax, View v) {
                        String time = getTimeMinute(dateMax);
                        strEndTime = time;
                        mBinding.tvRightBirthday.setText("" + time);
                        mBinding.tvRightBirthday.setTextColor(Color.BLACK);
                    }
                })
                        .setType(TimePickerView.Type.YEAR_MONTH_DAY)//默认全部显示
                        .setCancelText("取消")//取消按钮文字
                        .setSubmitText("确定")//确认按钮文字
                        .setContentSize(20)//滚轮文字大小
                        .setTitleSize(20)//标题文字大小
//                        .setTitleText("请选择时间")//标题文字
                        .setOutSideCancelable(true)//点击屏幕，点在控件外部范围时，是否取消显示
                        .isCyclic(true)//是否循环滚动
                        .setTextColorCenter(Color.parseColor("#000000"))//设置选中项的颜色
                        .setTextColorOut(Color.parseColor("#666666"))
                        .setTitleColor(Color.BLACK)//标题文字颜色
                        .setSubmitColor(Color.BLUE)//确定按钮文字颜色
                        .setCancelColor(Color.BLUE)//取消按钮文字颜色
                        .setLoop(false)
                        .isCenterLabel(false) //是否只显示中间选中项的label文字，false则每项item全部都带有label。
//                        .isDialog(true)//是否显示为对话框样式
                        .build();
                pvTime.setDate(Calendar.getInstance());//注：根据需求来决定是否使用该方法（一般是精确到秒的情况），此项可以在弹出选择器的时候重新设置当前时间，避免在初始化之后由于时间已经设定，导致选中时间与当前时间不匹配的问题。
                pvTime.show();

                break;
            case R.id.rl_address://选择地址
                if (AntiShakeUtils.isInvalidClick(v))
                    return;
                AreaSelectNewDialog mAreaSelectDialogss = new AreaSelectNewDialog(PersonalInformationActivity.this,  0);
                mAreaSelectDialogss.setListener(PersonalInformationActivity.this);
                mAreaSelectDialogss.show();

                break;
            case R.id.ll_sex_nan://男
            case R.id.cb_the_man:
                if (AntiShakeUtils.isInvalidClick(v))
                    return;
                mBinding.cbTheMan.setChecked(true);
                mBinding.cbTheWoman.setChecked(false);
                sexType = 1;
                break;
            case R.id.ll_sex_woman://女
            case R.id.cb_the_woman:
                if (AntiShakeUtils.isInvalidClick(v))
                    return;
                mBinding.cbTheMan.setChecked(false);
                mBinding.cbTheWoman.setChecked(true);
                sexType = 2;

                break;
            case R.id.iv_avatar://加载图片
                if (AntiShakeUtils.isInvalidClick(v))
                    return;
                PhotoSelDialog dialog = new PhotoSelDialog(this);
                dialog.setClickListener(this);
                dialog.show();


                break;
            case R.id.tv_basic_information://下一步
                if (AntiShakeUtils.isInvalidClick(v))
                    return;
                String etNickName = mBinding.etNickName.getText().toString().trim();
                String etPersonalSign = mBinding.etPersonalSign.getText().toString().trim();
                String etRealName = mBinding.etRightName.getText().toString().trim();
                String tvBirthday = mBinding.tvRightBirthday.getText().toString();
                String etPhoneNumber = mBinding.etTel.getText().toString();
//                if (TextUtils.isEmpty(mCoverLogo)) {
//                    toast("请上传个人头像");
//                    return;
//                }
//                if (TextUtils.isEmpty(etNickName)) {
//                    toast("请输入昵称");
//                    return;
//                }
//                if (TextUtils.isEmpty(etPersonalSign)) {
//                    toast("请输入个人签名");
//                    return;
//                }
                if (TextUtils.isEmpty(etRealName)) {
                    toast("请输入姓名");
                    return;
                }
                if (tvBirthday.equals("请选择生日")) {
                    toast("请选择生日");
                    return;
                }
                if (sexType == -1) {
                    toast("请选择性别");
                    return;
                }
                if (TextUtils.isEmpty(strCity)) {
                    toast("请输入地址");
                    return;
                }
                if (TextUtils.isEmpty(etPhoneNumber)) {
                    toast("请输入手机号");
                    return;
                }

                if (CommonUtilis.isMobileNO(etPhoneNumber.trim())) {

                    if (showType == 1) {
                        if (TextUtils.isEmpty(signPath)) {
                            toast("请录入手写签名");
                            return;
                        }
                    }

                    updateXlUserInfoBean.setHeadImg(mCoverLogo);
                    updateXlUserInfoBean.setNickName(etNickName);
                    if (!TextUtils.isEmpty(etPersonalSign)) {
                        updateXlUserInfoBean.setOwnSign(etPersonalSign);
                    } else {
                        updateXlUserInfoBean.setOwnSign("");
                    }
                    updateXlUserInfoBean.setName(etRealName);
                    updateXlUserInfoBean.setBornDate(strEndTime);
                    updateXlUserInfoBean.setSex("" + sexType);
                    updateXlUserInfoBean.setAddress(strCity);
                    updateXlUserInfoBean.setPhoneNumber(etPhoneNumber);
                    if (TextUtils.isEmpty(signPath)) {
//                            toast("请录入手写签名");
//                            return;
//                        }
                        updateXlUserInfoBean.setAutographImg("");
                    } else {
                        updateXlUserInfoBean.setAutographImg(signPath);
                    }

                    //请求接口
                    //空判断
//                    mBinding.llBasicInformation.setVisibility(View.GONE);
//                    mBinding.llGameInformation.setVisibility(View.VISIBLE);
//                    mBinding.llEquipmentInformation.setVisibility(View.GONE);
//
//                    mBinding.viewBasicInformationTitle.setBackgroundResource(R.drawable.shape_competition_gray_circle);
//                    mBinding.viewEquipmentInformationTitle.setBackgroundResource(R.drawable.shape_competition_gray_circle);
//                    mBinding.viewGameInformationTitle.setBackgroundResource(R.drawable.shape_competition_blue_circle);
//                    mBinding.tvBasicInformationTitle.setTextColor(Color.parseColor("#333333"));
//                    mBinding.tvEquipmentInformationTitle.setTextColor(Color.parseColor("#333333"));
//                    mBinding.tvGameInformationTitle.setTextColor(Color.parseColor("#ff4795ed"));

//                    updateXlUserInfoBean.setFloorBrandId(selectOneId);
//                    updateXlUserInfoBean.setFloorModelId(oneXinghaoId);
//                    updateXlUserInfoBean.setRightBrandId(zhengjiaoId);
//                    updateXlUserInfoBean.setRightModelId(twoXinghaoId);
//                    updateXlUserInfoBean.setRightLandId(houduZhengshouId);
//                    updateXlUserInfoBean.setRightHardId(hardZhengshouId);
//                    updateXlUserInfoBean.setRightColorId(zhengshouColorId);
//
//                    updateXlUserInfoBean.setLeftBrandId(fanjiaoId);
//                    updateXlUserInfoBean.setLeftModelId(threeXinghaoId);
//                    updateXlUserInfoBean.setLeftLandId(houduFanshouId);
//                    updateXlUserInfoBean.setLeftHardId(hardFanshouId);
//                    updateXlUserInfoBean.setLeftColorId(fanshouColorId);
//
//                    updateXlUserInfoBean.setDressSize(strCloth);
//                    updateXlUserInfoBean.setShoesSize(shoesCloth);
                    showLoading();
                    mViewModel.updateXlUserInfo(updateXlUserInfoBean);
                } else {
                    toast("请输入正确的手机号");
                }

                break;
            case R.id.ll_game_information_last://打法信息上一步
                if (AntiShakeUtils.isInvalidClick(v))
                    return;
                mBinding.llBasicInformation.setVisibility(View.VISIBLE);
                mBinding.llGameInformation.setVisibility(View.GONE);
                mBinding.llEquipmentInformation.setVisibility(View.GONE);

                mBinding.viewBasicInformationTitle.setBackgroundResource(R.drawable.shape_competition_blue_circle);
                mBinding.viewEquipmentInformationTitle.setBackgroundResource(R.drawable.shape_competition_gray_circle);
                mBinding.viewGameInformationTitle.setBackgroundResource(R.drawable.shape_competition_gray_circle);
                mBinding.tvBasicInformationTitle.setTextColor(Color.parseColor("#ff4795ed"));
                mBinding.tvEquipmentInformationTitle.setTextColor(Color.parseColor("#333333"));
                mBinding.tvGameInformationTitle.setTextColor(Color.parseColor("#333333"));
                break;
            case R.id.ll_game_information_next://打法信息下一步
                if (AntiShakeUtils.isInvalidClick(v))
                    return;
                /*判断type 是否选中*/
                if (chipaiType == -1) {
                    toast("请选择持拍手");
                    return;
                }
                if (wopaiType == -1) {
                    toast("请选择握拍法");
                    return;
                }
                if (zhengshouType == -1) {
                    toast("请选择正手");
                    return;
                }
                if (fanshouType == -1) {
                    toast("请选择反手");
                    return;
                }
                updateXlUserInfoBean.setClapHand("" + chipaiType);
                updateXlUserInfoBean.setGripMethon("" + wopaiType);
                updateXlUserInfoBean.setRightPlayMethon("" + zhengshouType);
                updateXlUserInfoBean.setLeftPlayMethon("" + fanshouType);
                mBinding.llBasicInformation.setVisibility(View.GONE);
                mBinding.llGameInformation.setVisibility(View.GONE);
                mBinding.llEquipmentInformation.setVisibility(View.VISIBLE);
                mBinding.viewBasicInformationTitle.setBackgroundResource(R.drawable.shape_competition_gray_circle);
                mBinding.viewEquipmentInformationTitle.setBackgroundResource(R.drawable.shape_competition_blue_circle);
                mBinding.viewGameInformationTitle.setBackgroundResource(R.drawable.shape_competition_gray_circle);
                mBinding.tvBasicInformationTitle.setTextColor(Color.parseColor("#333333"));
                mBinding.tvEquipmentInformationTitle.setTextColor(Color.parseColor("#ff4795ed"));
                mBinding.tvGameInformationTitle.setTextColor(Color.parseColor("#333333"));

                break;
            case R.id.ll_equipment_information_last://器材信息上一步
                if (AntiShakeUtils.isInvalidClick(v))
                    return;
                mBinding.llBasicInformation.setVisibility(View.GONE);
                mBinding.llGameInformation.setVisibility(View.VISIBLE);
                mBinding.llEquipmentInformation.setVisibility(View.GONE);
                mBinding.viewBasicInformationTitle.setBackgroundResource(R.drawable.shape_competition_gray_circle);
                mBinding.viewEquipmentInformationTitle.setBackgroundResource(R.drawable.shape_competition_gray_circle);
                mBinding.viewGameInformationTitle.setBackgroundResource(R.drawable.shape_competition_blue_circle);
                mBinding.tvBasicInformationTitle.setTextColor(Color.parseColor("#333333"));
                mBinding.tvEquipmentInformationTitle.setTextColor(Color.parseColor("#333333"));
                mBinding.tvGameInformationTitle.setTextColor(Color.parseColor("#ff4795ed"));
                break;
            case R.id.ll_equipment_information_next://器材信息下一步
                if (AntiShakeUtils.isInvalidClick(v))
                    return;
                if (TextUtils.isEmpty(selectOneId) || selectOneId.equals("0")) {
                    toast("请选择底板品牌");
                    return;
                }
                if (TextUtils.isEmpty(oneXinghaoId) || oneXinghaoId.equals("0")) {
                    toast("请选择底板型号");
                    return;
                }
                //正手套胶
                if (TextUtils.isEmpty(zhengjiaoId) || zhengjiaoId.equals("0")) {
                    toast("请选择正手套胶品牌");
                    return;
                }
                if (TextUtils.isEmpty(twoXinghaoId) || twoXinghaoId.equals("0")) {
                    toast("请选择正手套胶型号");
                    return;
                }
                if (TextUtils.isEmpty(houduZhengshouId) || houduZhengshouId.equals("0")) {
                    toast("请选择正手套胶厚度");
                    return;
                }
                if (TextUtils.isEmpty(hardZhengshouId) || hardZhengshouId.equals("0")) {
                    toast("请选择正手套胶硬度");
                    return;
                }
                if (TextUtils.isEmpty(zhengshouColorId) || zhengshouColorId.equals("0")) {
                    toast("请选择正手套胶颜色");
                    return;
                }

                //反手套胶
                if (TextUtils.isEmpty(fanjiaoId) || fanjiaoId.equals("0")) {
                    toast("请选择反手套胶品牌");
                    return;
                }
                if (TextUtils.isEmpty(threeXinghaoId) || threeXinghaoId.equals("0")) {
                    toast("请选择反手套胶型号");
                    return;
                }
                if (TextUtils.isEmpty(houduFanshouId) || houduFanshouId.equals("0")) {
                    toast("请选择反手套胶厚度");
                    return;
                }
                if (TextUtils.isEmpty(hardFanshouId) || hardFanshouId.equals("0")) {
                    toast("请选择反手套胶硬度");
                    return;
                }

                if (TextUtils.isEmpty(fanshouColorId) || fanshouColorId.equals("0")) {
                    toast("请选择反手套胶颜色");
                    return;
                }
                if (TextUtils.isEmpty(strCloth)) {
                    toast("请选择衣服尺码");
                    return;
                }
                if (TextUtils.isEmpty(shoesCloth)) {
                    toast("请选择鞋子尺码");
                    return;
                }


                new MessageDialogBuilder(PersonalInformationActivity.this)
                        .setMessage("")
                        .setTvTitle("是否保存个人信息?")
                        .setTvSure("确定")
                        .setTvCancel("取消")
                        .setBtnCancleHint(false)
                        .setSureListener(v1 ->
                                showConfirm()
                        )
                        .show();

                break;
            case R.id.fl_sign://个人签名
                if (AntiShakeUtils.isInvalidClick(v))
                    return;
                startActivityForResult(new Intent(PersonalInformationActivity.this, SignInterfaceActivity.class), REQUEST_SIGN_IN);

                break;
            case R.id.rl_left_brand_one://地板    品牌
                if (AntiShakeUtils.isInvalidClick(v))
                    return;
                bardType = 1;//地板
                showLoading();
                //品牌信息获取  调用品牌信息获取接口
                mViewModel.getXlEquipmentBrandList("" + bardType);

                break;
            case R.id.rl_left_brand_two:
                if (AntiShakeUtils.isInvalidClick(v))
                    return;
                oneTypes = 1;
                if (list != null && list.size() > 0) {
                    if (TextUtils.isEmpty(selectOneId)) {
                        toast("请选择地板品牌");
                        return;
                    } else {
                        /*调用接口*/
                        mViewModel.getModelDataByBrandId(selectOneId);

                    }
                } else {
                    toast("请选择地板品牌");
                }

                break;
            case R.id.rl_taojiao_pinpai_zheng:
                if (AntiShakeUtils.isInvalidClick(v))
                    return;
                bardType = 2;//正手套胶
                taoJiaoType = 1;
                /*调接口*/
                showLoading();
                //品牌信息获取  调用品牌信息获取接口
                mViewModel.getXlEquipmentBrandList("" + bardType);

                break;
            case R.id.rl_taojiao_fanshou://套胶反手
                if (AntiShakeUtils.isInvalidClick(v))
                    return;
                bardType = 2;//反手套胶
                taoJiaoType = 2;
                /*调接口*/
                showLoading();
                //品牌信息获取  调用品牌信息获取接口
                mViewModel.getXlEquipmentBrandList("" + bardType);
                break;


            case R.id.rl_xinghao_zheng://正手型号
                if (AntiShakeUtils.isInvalidClick(v))
                    return;
                oneTypes = 2;
                if (list != null && list.size() > 0) {
                    if (TextUtils.isEmpty(zhengjiaoId)) {
                        toast("请选择正手套胶品牌");
                        return;
                    } else {
                        /*调用接口*/
                        selectYingduType = 1;

                        mViewModel.getModelDataByBrandId(zhengjiaoId);

                    }
                } else {
                    toast("请选择正手套胶品牌");
                }

                break;
            case R.id.rl_xinghao_fan://反手型号
                if (AntiShakeUtils.isInvalidClick(v))
                    return;
                oneTypes = 3;
                if (list != null && list.size() > 0) {
                    if (TextUtils.isEmpty(fanjiaoId)) {
                        toast("请选择反手套胶品牌");
                        return;
                    } else {
                        /*调用接口*/
                        selectYingduType = 2;

                        mViewModel.getModelDataByBrandId(fanjiaoId);

                    }
                } else {
                    toast("请选择反手套胶品牌");
                }
                break;

            case R.id.rl_houdu_zhengshou://厚度 正手套胶
                if (AntiShakeUtils.isInvalidClick(v))
                    return;
                //判断集合是否大于0
                Log.e(TAG, "onClick: xlModelThicksZheng===" + xlModelThicksZheng.size());
                Log.e(TAG, "onClick: xlModelThicksFan===" + xlModelThicksFan.size());
                if (xlModelThicksZheng != null && xlModelThicksZheng.size() > 0) {
                    oneHardType = 1;
                    selectHardHouduType = 1;
                    mBinding.tvHouduZhengshou.post(new Runnable() {
                        @Override
                        public void run() {
                            int height = mBinding.tvHouduZhengshou.getHeight();
                            showPopHouduHard(mBinding.tvHouduZhengshou, height, xlModelThicksZheng);

                        }
                    });
                } else {
                    toast("请选择品牌或者型号");
                }
                break;


            case R.id.rl_fanshou_taojiao://厚度  反手套胶
                if (AntiShakeUtils.isInvalidClick(v))
                    return;
                //判断集合是否大于0
                if (xlModelThicksFan != null && xlModelThicksFan.size() > 0) {
                    oneHardType = 1;
                    selectHardHouduType = 2;
                    selectYingduType = 2;
                    mBinding.tvFanshouHoudu.post(new Runnable() {
                        @Override
                        public void run() {
                            int height = mBinding.tvFanshouHoudu.getHeight();
                            showPopHouduHard(mBinding.tvFanshouHoudu, height, xlModelThicksFan);

                        }
                    });
                } else {
                    toast("请选择品牌或者型号");
                }
                break;

            case R.id.rl_yingdu_zhengshou://硬度   正手
                if (AntiShakeUtils.isInvalidClick(v))
                    return;
                if (xlModelHardnessZheng != null && xlModelHardnessZheng.size() > 0) {
                    oneHardType = 2;
                    selectYingduType = 1;
                    mBinding.tvYingduZhengshou.post(new Runnable() {
                        @Override
                        public void run() {
                            int height = mBinding.tvYingduZhengshou.getHeight();
                            showPopHard(mBinding.tvYingduZhengshou, height, xlModelHardnessZheng);
                        }
                    });

                } else {
                    toast("请选择品牌或者型号");
                }


                break;
            case R.id.rl_yingdu_fanshou://硬度  反手
                if (AntiShakeUtils.isInvalidClick(v))
                    return;
                if (xlModelHardnessFan != null && xlModelHardnessFan.size() > 0) {
                    oneHardType = 2;
                    selectYingduType = 2;
                    mBinding.tvYingduFanshou.post(new Runnable() {
                        @Override
                        public void run() {
                            int height = mBinding.tvYingduFanshou.getHeight();
                            showPopHard(mBinding.tvYingduFanshou, height, xlModelHardnessFan);
                        }
                    });
                } else {
                    toast("请选择品牌或者型号");
                }

                break;
            case R.id.tv_xl:
                if (AntiShakeUtils.isInvalidClick(v))
                    return;
                strCloth = "XL";
                showNoSelect();
                mBinding.tvXl.setBackgroundResource(R.drawable.shape_club_blue);
                mBinding.tvXl.setTextColor(Color.WHITE);
                break;
            case R.id.tv_2xl:
                if (AntiShakeUtils.isInvalidClick(v))
                    return;
                strCloth = "2XL";
                showNoSelect();
                mBinding.tv2xl.setBackgroundResource(R.drawable.shape_club_blue);
                mBinding.tv2xl.setTextColor(Color.WHITE);
                break;
            case R.id.tv_3xl:
                if (AntiShakeUtils.isInvalidClick(v))
                    return;
                strCloth = "3XL";
                showNoSelect();
                mBinding.tv3xl.setBackgroundResource(R.drawable.shape_club_blue);
                mBinding.tv3xl.setTextColor(Color.WHITE);
                break;
            case R.id.tv_4xl:
                if (AntiShakeUtils.isInvalidClick(v))
                    return;
                strCloth = "4XL";
                showNoSelect();
                mBinding.tv4xl.setBackgroundResource(R.drawable.shape_club_blue);
                mBinding.tv4xl.setTextColor(Color.WHITE);
                break;
            case R.id.tv_5xl:
                if (AntiShakeUtils.isInvalidClick(v))
                    return;
                strCloth = "5XL";
                showNoSelect();
                mBinding.tv5xl.setBackgroundResource(R.drawable.shape_club_blue);
                mBinding.tv5xl.setTextColor(Color.WHITE);
                break;
            case R.id.tv_s:
                if (AntiShakeUtils.isInvalidClick(v))
                    return;
                strCloth = "S";
                showNoSelect();
                mBinding.tvS.setBackgroundResource(R.drawable.shape_club_blue);
                mBinding.tvS.setTextColor(Color.WHITE);
                break;
            case R.id.tv_m:
                if (AntiShakeUtils.isInvalidClick(v))
                    return;
                strCloth = "M";
                showNoSelect();
                mBinding.tvM.setBackgroundResource(R.drawable.shape_club_blue);
                mBinding.tvM.setTextColor(Color.WHITE);
                break;
            case R.id.tv_l:
                if (AntiShakeUtils.isInvalidClick(v))
                    return;
                strCloth = "L";
                showNoSelect();
                mBinding.tvL.setBackgroundResource(R.drawable.shape_club_blue);
                mBinding.tvL.setTextColor(Color.WHITE);
                break;

            case R.id.tv_shoose_41://41号鞋子
                if (AntiShakeUtils.isInvalidClick(v))
                    return;
                shoesCloth = "41";
                showNoSelectSHose();
                mBinding.tvShoose41.setTextColor(Color.WHITE);
                mBinding.tvShoose41.setBackgroundResource(R.drawable.shape_club_blue);
                break;
            case R.id.tv_shoose_42:
                if (AntiShakeUtils.isInvalidClick(v))
                    return;
                shoesCloth = "42";
                showNoSelectSHose();
                mBinding.tvShoose42.setTextColor(Color.WHITE);
                mBinding.tvShoose42.setBackgroundResource(R.drawable.shape_club_blue);

                break;
            case R.id.tv_shoose_43:
                if (AntiShakeUtils.isInvalidClick(v))
                    return;
                shoesCloth = "43";
                showNoSelectSHose();
                mBinding.tvShoose43.setTextColor(Color.WHITE);
                mBinding.tvShoose43.setBackgroundResource(R.drawable.shape_club_blue);
                break;
            case R.id.tv_shoose_44:
                if (AntiShakeUtils.isInvalidClick(v))
                    return;
                shoesCloth = "44";
                showNoSelectSHose();
                mBinding.tvShoose44.setTextColor(Color.WHITE);
                mBinding.tvShoose44.setBackgroundResource(R.drawable.shape_club_blue);
                break;

            case R.id.tv_shoose_45:
                if (AntiShakeUtils.isInvalidClick(v))
                    return;
                shoesCloth = "45";
                showNoSelectSHose();
                mBinding.tvShoose45.setTextColor(Color.WHITE);
                mBinding.tvShoose45.setBackgroundResource(R.drawable.shape_club_blue);
                break;
            case R.id.tv_shose_36:
                if (AntiShakeUtils.isInvalidClick(v))
                    return;
                shoesCloth = "36";
                showNoSelectSHose();
                mBinding.tvShose36.setTextColor(Color.WHITE);
                mBinding.tvShose36.setBackgroundResource(R.drawable.shape_club_blue);
                break;
            case R.id.tv_shose_37:
                shoesCloth = "37";
                if (AntiShakeUtils.isInvalidClick(v))
                    return;
                showNoSelectSHose();
                mBinding.tvShose37.setTextColor(Color.WHITE);
                mBinding.tvShose37.setBackgroundResource(R.drawable.shape_club_blue);
                break;
            case R.id.tv_shose_38:
                shoesCloth = "38";
                if (AntiShakeUtils.isInvalidClick(v))
                    return;
                showNoSelectSHose();
                mBinding.tvShose38.setTextColor(Color.WHITE);
                mBinding.tvShose38.setBackgroundResource(R.drawable.shape_club_blue);
                break;
            case R.id.tv_shose_39:
                shoesCloth = "39";
                if (AntiShakeUtils.isInvalidClick(v))
                    return;
                showNoSelectSHose();
                mBinding.tvShose39.setTextColor(Color.WHITE);
                mBinding.tvShose39.setBackgroundResource(R.drawable.shape_club_blue);
                break;
            case R.id.tv_shose_40:
                shoesCloth = "40";
                if (AntiShakeUtils.isInvalidClick(v))
                    return;
                showNoSelectSHose();
                mBinding.tvShose40.setTextColor(Color.WHITE);
                mBinding.tvShose40.setBackgroundResource(R.drawable.shape_club_blue);
                break;
            case R.id.tv_shose_31:
                shoesCloth = "31";
                if (AntiShakeUtils.isInvalidClick(v))
                    return;
                showNoSelectSHose();
                mBinding.tvShose31.setTextColor(Color.WHITE);
                mBinding.tvShose31.setBackgroundResource(R.drawable.shape_club_blue);
                break;
            case R.id.tv_shose_32:
                shoesCloth = "32";
                if (AntiShakeUtils.isInvalidClick(v))
                    return;
                showNoSelectSHose();
                mBinding.tvShose32.setTextColor(Color.WHITE);
                mBinding.tvShose32.setBackgroundResource(R.drawable.shape_club_blue);
                break;
            case R.id.tv_shose_33:
                if (AntiShakeUtils.isInvalidClick(v))
                    return;
                shoesCloth = "33";
                showNoSelectSHose();
                mBinding.tvShose33.setTextColor(Color.WHITE);
                mBinding.tvShose33.setBackgroundResource(R.drawable.shape_club_blue);
                break;
            case R.id.tv_shose_34:
                shoesCloth = "34";
                if (AntiShakeUtils.isInvalidClick(v))
                    return;
                showNoSelectSHose();
                mBinding.tvShose34.setTextColor(Color.WHITE);
                mBinding.tvShose34.setBackgroundResource(R.drawable.shape_club_blue);
                break;
            case R.id.tv_shose_35:
                if (AntiShakeUtils.isInvalidClick(v))
                    return;
                shoesCloth = "35";
                showNoSelectSHose();
                mBinding.tvShose35.setTextColor(Color.WHITE);
                mBinding.tvShose35.setBackgroundResource(R.drawable.shape_club_blue);
                break;
        }
    }

    public static String getTimeMinute(Date date) {//可根据需要自行截取数据显示
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
        return format.format(date);
    }

    private void showNoSelectSHose() {
        mBinding.tvShose31.setBackgroundResource(R.drawable.shape_club_gray_little);
        mBinding.tvShose31.setTextColor(Color.parseColor("#666666"));
        mBinding.tvShose32.setBackgroundResource(R.drawable.shape_club_gray_little);
        mBinding.tvShose32.setTextColor(Color.parseColor("#666666"));
        mBinding.tvShose33.setBackgroundResource(R.drawable.shape_club_gray_little);
        mBinding.tvShose33.setTextColor(Color.parseColor("#666666"));
        mBinding.tvShose34.setBackgroundResource(R.drawable.shape_club_gray_little);
        mBinding.tvShose34.setTextColor(Color.parseColor("#666666"));
        mBinding.tvShose35.setBackgroundResource(R.drawable.shape_club_gray_little);
        mBinding.tvShose35.setTextColor(Color.parseColor("#666666"));


        mBinding.tvShose36.setBackgroundResource(R.drawable.shape_club_gray_little);
        mBinding.tvShose36.setTextColor(Color.parseColor("#666666"));
        mBinding.tvShose37.setBackgroundResource(R.drawable.shape_club_gray_little);
        mBinding.tvShose37.setTextColor(Color.parseColor("#666666"));
        mBinding.tvShose38.setBackgroundResource(R.drawable.shape_club_gray_little);
        mBinding.tvShose38.setTextColor(Color.parseColor("#666666"));
        mBinding.tvShose39.setBackgroundResource(R.drawable.shape_club_gray_little);
        mBinding.tvShose39.setTextColor(Color.parseColor("#666666"));
        mBinding.tvShose40.setBackgroundResource(R.drawable.shape_club_gray_little);
        mBinding.tvShose40.setTextColor(Color.parseColor("#666666"));


        mBinding.tvShoose41.setBackgroundResource(R.drawable.shape_club_gray_little);
        mBinding.tvShoose41.setTextColor(Color.parseColor("#666666"));
        mBinding.tvShoose42.setBackgroundResource(R.drawable.shape_club_gray_little);
        mBinding.tvShoose42.setTextColor(Color.parseColor("#666666"));
        mBinding.tvShoose43.setBackgroundResource(R.drawable.shape_club_gray_little);
        mBinding.tvShoose43.setTextColor(Color.parseColor("#666666"));
        mBinding.tvShoose44.setBackgroundResource(R.drawable.shape_club_gray_little);
        mBinding.tvShoose44.setTextColor(Color.parseColor("#666666"));
        mBinding.tvShoose45.setBackgroundResource(R.drawable.shape_club_gray_little);
        mBinding.tvShoose45.setTextColor(Color.parseColor("#666666"));


    }


    //没有选中
    private void showNoSelect() {
        mBinding.tvXl.setBackgroundResource(R.drawable.shape_club_gray_little);
        mBinding.tvXl.setTextColor(Color.parseColor("#666666"));
        mBinding.tv2xl.setBackgroundResource(R.drawable.shape_club_gray_little);
        mBinding.tv2xl.setTextColor(Color.parseColor("#666666"));
        mBinding.tv3xl.setBackgroundResource(R.drawable.shape_club_gray_little);
        mBinding.tv3xl.setTextColor(Color.parseColor("#666666"));
        mBinding.tv4xl.setBackgroundResource(R.drawable.shape_club_gray_little);
        mBinding.tv4xl.setTextColor(Color.parseColor("#666666"));
        mBinding.tv5xl.setBackgroundResource(R.drawable.shape_club_gray_little);
        mBinding.tv5xl.setTextColor(Color.parseColor("#666666"));
        mBinding.tvS.setBackgroundResource(R.drawable.shape_club_gray_little);
        mBinding.tvS.setTextColor(Color.parseColor("#666666"));
        mBinding.tvM.setBackgroundResource(R.drawable.shape_club_gray_little);
        mBinding.tvM.setTextColor(Color.parseColor("#666666"));
        mBinding.tvL.setBackgroundResource(R.drawable.shape_club_gray_little);
        mBinding.tvL.setTextColor(Color.parseColor("#666666"));
    }


    /**
     * 显示硬度
     */
    private void showPopHard(TextView tvCoach, int height, List<ModelDataBrandIdBean.XlModelHardnessBean> list) {
        mShowEquipmentWindow = new CommonPopupWindow.Builder(PersonalInformationActivity.this)
                .setView(R.layout.pop_fill_info_hard)
                .setBackGroundLevel(1f)
                .setViewOnclickListener(new CommonPopupWindow.ViewInterface() {
                    @Override
                    public void getChildView(View view, int layoutResId) {
                        RecyclerView rvHardLeft = view.findViewById(R.id.rv_hard_left);
                        RecyclerView rvHardRight = view.findViewById(R.id.rv_hard_right);
                        LinearLayout llRvLeft = view.findViewById(R.id.ll_rv_left);
                        LinearLayout llRvRight = view.findViewById(R.id.ll_rv_right);
                        if (oneHardType == 1) {
                            rvHardLeft.setVisibility(View.VISIBLE);
                            rvHardRight.setVisibility(View.INVISIBLE);
                            llRvLeft.setVisibility(View.VISIBLE);
                            llRvRight.setVisibility(View.INVISIBLE);
                        } else {
                            rvHardLeft.setVisibility(View.INVISIBLE);
                            rvHardRight.setVisibility(View.VISIBLE);
                            llRvLeft.setVisibility(View.INVISIBLE);
                            llRvRight.setVisibility(View.VISIBLE);
                        }
                        rvHardLeft.setLayoutManager(new LinearLayoutManager(PersonalInformationActivity.this, RecyclerView.VERTICAL, false));
                        HouduAdapter fillMatchFormAdapter = new HouduAdapter();
                        rvHardLeft.setAdapter(fillMatchFormAdapter);
                        rvHardRight.setLayoutManager(new LinearLayoutManager(PersonalInformationActivity.this, RecyclerView.VERTICAL, false));
                        HouduHardAdapter fillMatchFormAdapter2 = new HouduHardAdapter();
                        rvHardRight.setAdapter(fillMatchFormAdapter2);
                        fillMatchFormAdapter2.setNewData(list);

                        fillMatchFormAdapter2.setOnItemClickListener(new BaseQuickAdapter.OnItemClickListener() {
                            @Override
                            public void onItemClick(BaseQuickAdapter adapter, View view, int position) {
                                boolean fastClick = isFastClick();
                                if (!fastClick) {
                                    ModelDataBrandIdBean.XlModelHardnessBean listBean = list.get(position);
                                    long id = listBean.getId();
                                    if (selectYingduType == 1) {
                                        hardZhengshouId = "" + id;
                                    } else {
                                        hardFanshouId = "" + id;
                                    }
                                    mShowEquipmentWindow.dismiss();
                                    tvCoach.setText("" + listBean.getAttributeValue());
                                }
                            }
                        });


                    }
                })
                .setOutsideTouchable(true)//设置外层不可点击
                .setWidthAndHeight(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT)
                .create();
        //获取需要在其上方显示的控件的位置信息
        int[] location = new int[2];
        tvCoach.getLocationOnScreen(location);
        Log.e(TAG, "showPopWindow: " + location[0]);
        Log.e(TAG, "showPopWindow: " + location[1]);
//        View view1 = View.inflate(MovieTicketActivity.this, R.layout.activity_movie_ticket, null);
        if (mShowEquipmentWindow == null || !mShowEquipmentWindow.isShowing()) {
            mShowEquipmentWindow.setFocusable(true);// 这个很重要
            mShowEquipmentWindow.showAtLocation(tvCoach, Gravity.NO_GRAVITY, (location[0] + tvCoach.getWidth() / 2) + 60 / 2, location[1] + +height * 2 / 3 + height / 2 + 15);
        }


    }

    /**
     * 显示厚度
     */
    private void showPopHouduHard(TextView tvCoach, int height, List<ModelDataBrandIdBean.XlModelThicksBean> list) {
        mShowEquipmentWindow = new CommonPopupWindow.Builder(PersonalInformationActivity.this)
                .setView(R.layout.pop_fill_info_hard)
                .setBackGroundLevel(1f)
                .setViewOnclickListener(new CommonPopupWindow.ViewInterface() {
                    @Override
                    public void getChildView(View view, int layoutResId) {
                        RecyclerView rvHardLeft = view.findViewById(R.id.rv_hard_left);
                        RecyclerView rvHardRight = view.findViewById(R.id.rv_hard_right);
                        LinearLayout llRvLeft = view.findViewById(R.id.ll_rv_left);
                        LinearLayout llRvRight = view.findViewById(R.id.ll_rv_right);
                        if (oneHardType == 1) {
                            rvHardLeft.setVisibility(View.VISIBLE);
                            rvHardRight.setVisibility(View.INVISIBLE);
                            llRvLeft.setVisibility(View.VISIBLE);
                            llRvRight.setVisibility(View.GONE);
                        } else {
                            rvHardLeft.setVisibility(View.INVISIBLE);
                            rvHardRight.setVisibility(View.VISIBLE);
                            llRvLeft.setVisibility(View.GONE);
                            llRvRight.setVisibility(View.VISIBLE);
                        }
                        rvHardLeft.setLayoutManager(new LinearLayoutManager(PersonalInformationActivity.this, RecyclerView.VERTICAL, false));
                        HouduAdapter fillMatchFormAdapter = new HouduAdapter();
                        rvHardLeft.setAdapter(fillMatchFormAdapter);
                        fillMatchFormAdapter.setNewData(list);
                        rvHardRight.setLayoutManager(new LinearLayoutManager(PersonalInformationActivity.this, RecyclerView.VERTICAL, false));
                        KeyBoradAdapter fillMatchFormAdapter2 = new KeyBoradAdapter();
                        rvHardRight.setAdapter(fillMatchFormAdapter2);
                        fillMatchFormAdapter.setOnItemClickListener(new BaseQuickAdapter.OnItemClickListener() {
                            @Override
                            public void onItemClick(BaseQuickAdapter adapter, View view, int position) {
                                boolean fastClick = isFastClick();
                                if (!fastClick) {
                                    ModelDataBrandIdBean.XlModelThicksBean listBean = list.get(position);
                                    long id = listBean.getId();
                                    if (selectHardHouduType == 1) {
                                        //上面的厚度
                                        houduZhengshouId = "" + id;
                                    } else {
                                        houduFanshouId = "" + id;
                                    }
                                    mShowEquipmentWindow.dismiss();
                                    tvCoach.setText("" + listBean.getAttributeValue());
                                }
                            }
                        });


                    }
                })
                .setOutsideTouchable(true)//设置外层不可点击
                .setWidthAndHeight(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT)
                .create();
        //获取需要在其上方显示的控件的位置信息
        int[] location = new int[2];
        tvCoach.getLocationOnScreen(location);
        Log.e(TAG, "showPopWindow: " + location[0]);
        Log.e(TAG, "showPopWindow: " + location[1]);
//        View view1 = View.inflate(MovieTicketActivity.this, R.layout.activity_movie_ticket, null);
        if (mShowEquipmentWindow == null || !mShowEquipmentWindow.isShowing()) {
            mShowEquipmentWindow.setFocusable(true);// 这个很重要
            mShowEquipmentWindow.showAtLocation(tvCoach, Gravity.NO_GRAVITY, (location[0] + tvCoach.getWidth() / 2) + 60 / 2, location[1] + +height * 2 / 3 + height / 2 + 15);
        }


    }

    /**
     * 地板的弹窗 以及征收胶套的品牌和型号的弹窗
     *
     * @param tvCoach
     * @param list
     */
    private void showPopBaseBorad(TextView tvCoach, int height, List<XlEquipmentBrandListBean.ListBean> list) {
        mShowEquipmentWindow = new CommonPopupWindow.Builder(PersonalInformationActivity.this)
                .setView(R.layout.pop_fill_info_equipment)
                .setBackGroundLevel(1f)
                .setViewOnclickListener(new CommonPopupWindow.ViewInterface() {
                    @Override
                    public void getChildView(View view, int layoutResId) {
                        RecyclerView rvLeftBrand = view.findViewById(R.id.rv_left_brand);
                        RecyclerView rvRightBrand = view.findViewById(R.id.rv_right_brand);
                        LinearLayout llRvLeft = view.findViewById(R.id.ll_rv_left);
                        LinearLayout llRvRight = view.findViewById(R.id.ll_rv_right);
                        if (oneShowType == 1) {
                            llRvLeft.setVisibility(View.VISIBLE);
                            llRvRight.setVisibility(View.INVISIBLE);
                            rvLeftBrand.setVisibility(View.VISIBLE);
                            rvRightBrand.setVisibility(View.INVISIBLE);
                        } else {
                            rvLeftBrand.setVisibility(View.INVISIBLE);
                            rvRightBrand.setVisibility(View.VISIBLE);
                            llRvLeft.setVisibility(View.INVISIBLE);
                            llRvRight.setVisibility(View.VISIBLE);
                        }
                        rvLeftBrand.setLayoutManager(new LinearLayoutManager(PersonalInformationActivity.this, RecyclerView.VERTICAL, false));
                        KeyBoradAdapter fillMatchFormAdapter = new KeyBoradAdapter();
                        rvLeftBrand.setAdapter(fillMatchFormAdapter);
                        fillMatchFormAdapter.setNewData(list);
                        rvRightBrand.setLayoutManager(new LinearLayoutManager(PersonalInformationActivity.this, RecyclerView.VERTICAL, false));
                        KeyBoradAdapter fillMatchFormAdapter2 = new KeyBoradAdapter();
                        rvRightBrand.setAdapter(fillMatchFormAdapter2);
                        fillMatchFormAdapter.setOnItemClickListener(new BaseQuickAdapter.OnItemClickListener() {
                            @Override
                            public void onItemClick(BaseQuickAdapter adapter, View view, int position) {
                                boolean fastClick = isFastClick();
                                if (!fastClick) {
                                    XlEquipmentBrandListBean.ListBean listBean = list.get(position);

                                    long id = listBean.getId();
                                    if (bardType == 1) {
                                        selectOneId = "" + id;
                                        oneXinghaoId = "";
                                        mBinding.tvLeftBrandTwo.setText("型号");
                                    } else {
                                        if (taoJiaoType == 1) {
                                            zhengjiaoId = "" + id;
                                            twoXinghaoId = "";
                                            mBinding.tvXinghaoZheng.setText("型号");
                                            houduZhengshouId = "";
                                            mBinding.tvHouduZhengshou.setText("厚度");
                                            hardZhengshouId = "";
                                            mBinding.tvYingduZhengshou.setText("硬度");
                                        } else {
                                            fanjiaoId = "" + id;
                                            threeXinghaoId = "";
                                            mBinding.tvXinghaoFan.setText("型号");
                                            houduFanshouId = "";
                                            mBinding.tvFanshouHoudu.setText("厚度");
                                            hardFanshouId = "";
                                            mBinding.tvYingduFanshou.setText("硬度");
                                        }
                                    }
                                    mShowEquipmentWindow.dismiss();
                                    tvCoach.setText("" + listBean.getTitle());
                                }
                            }
                        });

                        fillMatchFormAdapter2.setOnItemClickListener(new BaseQuickAdapter.OnItemClickListener() {
                            @Override
                            public void onItemClick(BaseQuickAdapter adapter, View view, int position) {

                            }
                        });

                    }
                })
                .setOutsideTouchable(true)//设置外层不可点击
                .setWidthAndHeight(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT)
                .create();
        //获取需要在其上方显示的控件的位置信息
        int[] location = new int[2];
        tvCoach.getLocationOnScreen(location);
        Log.e(TAG, "showPopWindow: " + location[0]);
        Log.e(TAG, "showPopWindow: " + location[1]);
//        View view1 = View.inflate(MovieTicketActivity.this, R.layout.activity_movie_ticket, null);
        if (mShowEquipmentWindow == null || !mShowEquipmentWindow.isShowing()) {
            mShowEquipmentWindow.setFocusable(true);// 这个很重要
            mShowEquipmentWindow.showAtLocation(tvCoach, Gravity.NO_GRAVITY, (location[0] + tvCoach.getWidth() / 2) + 60 / 2, location[1] + +height * 2 / 3 + height / 2 + 15);
        }


    }

    /**
     * 地板的弹窗 以及征收胶套的品牌和型号的弹窗
     *
     * @param tvCoach
     */
    private void showPopXinghaoWindow(TextView tvCoach, int height, List<ModelDataBrandIdBean> xlModelList) {
        mShowEquipmentWindow = new CommonPopupWindow.Builder(PersonalInformationActivity.this)
                .setView(R.layout.pop_fill_info_equipment)
                .setBackGroundLevel(1f)
                .setViewOnclickListener(new CommonPopupWindow.ViewInterface() {
                    @Override
                    public void getChildView(View view, int layoutResId) {
                        RecyclerView rvLeftBrand = view.findViewById(R.id.rv_left_brand);
                        RecyclerView rvRightBrand = view.findViewById(R.id.rv_right_brand);
                        LinearLayout llRvLeft = view.findViewById(R.id.ll_rv_left);
                        LinearLayout llRvRight = view.findViewById(R.id.ll_rv_right);
                        if (oneShowType == 1) {
                            rvLeftBrand.setVisibility(View.VISIBLE);
                            rvRightBrand.setVisibility(View.INVISIBLE);
                            llRvLeft.setVisibility(View.VISIBLE);
                            llRvRight.setVisibility(View.INVISIBLE);
                        } else {
                            rvLeftBrand.setVisibility(View.INVISIBLE);
                            rvRightBrand.setVisibility(View.VISIBLE);
                            llRvLeft.setVisibility(View.INVISIBLE);
                            llRvRight.setVisibility(View.VISIBLE);
                        }
                        rvLeftBrand.setLayoutManager(new LinearLayoutManager(PersonalInformationActivity.this, RecyclerView.VERTICAL, false));
                        KeyBoradAdapter fillMatchFormAdapter = new KeyBoradAdapter();
                        rvLeftBrand.setAdapter(fillMatchFormAdapter);
                        fillMatchFormAdapter.setNewData(list);
                        rvRightBrand.setLayoutManager(new LinearLayoutManager(PersonalInformationActivity.this, RecyclerView.VERTICAL, false));
                        KeyBoradXinghaoAdapter fillMatchFormAdapter2 = new KeyBoradXinghaoAdapter();
                        rvRightBrand.setAdapter(fillMatchFormAdapter2);
                        fillMatchFormAdapter2.setNewData(xlModelList);
                        fillMatchFormAdapter2.setOnItemClickListener(new BaseQuickAdapter.OnItemClickListener() {
                            @Override
                            public void onItemClick(BaseQuickAdapter adapter, View view, int position) {
                                boolean fastClick = isFastClick();
                                if (!fastClick) {
                                    mShowEquipmentWindow.dismiss();
                                    /*型号  点击  获取   */
                                    ModelDataBrandIdBean xlModelThicksBean = xlModelList.get(position);
//                                selectYingduType
                                    Log.e(TAG, "onItemClick: selectYingduType====" + selectYingduType);
                                    if (selectYingduType == 1) {
                                        //正
                                        xlModelHardnessZheng.clear();
                                        xlModelHardnessZheng = xlModelList.get(position).getXlModelHardness();
                                    } else {
                                        xlModelHardnessFan.clear();
                                        xlModelHardnessFan = xlModelList.get(position).getXlModelHardness();
                                    }
//                                oneTypes = 2;
                                    if (oneTypes == 2) {
                                        //正
                                        xlModelThicksZheng = xlModelList.get(position).getXlModelThicks();
                                    } else {
                                        xlModelThicksFan = xlModelList.get(position).getXlModelThicks();
                                    }


                                    if (xlModelThicksBean != null) {
                                        String attributeValue = xlModelThicksBean.getTitle();
                                        if (oneTypes == 1) {
                                            oneXinghaoId = "" + xlModelThicksBean.getId();
                                            tvCoach.setText("" + attributeValue);
                                        } else if (oneTypes == 2) {
                                            twoXinghaoId = "" + xlModelThicksBean.getId();
                                            tvCoach.setText("" + attributeValue);
                                        } else if (oneTypes == 3) {
                                            threeXinghaoId = "" + xlModelThicksBean.getId();
                                            tvCoach.setText("" + attributeValue);
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
        //获取需要在其上方显示的控件的位置信息
        int[] location = new int[2];
        tvCoach.getLocationOnScreen(location);
        Log.e(TAG, "showPopWindow: " + location[0]);
        Log.e(TAG, "showPopWindow: " + location[1]);
//        View view1 = View.inflate(MovieTicketActivity.this, R.layout.activity_movie_ticket, null);
        if (mShowEquipmentWindow == null || !mShowEquipmentWindow.isShowing()) {
            mShowEquipmentWindow.setFocusable(true);// 这个很重要
            mShowEquipmentWindow.showAtLocation(tvCoach, Gravity.NO_GRAVITY, (location[0] + tvCoach.getWidth() / 2) + 60 / 2, location[1] + +height * 2 / 3 + height / 2 + 15);
        }


    }

    private void showConfirm() {


        updateXlUserInfoBean.setFloorBrandId(selectOneId);
        updateXlUserInfoBean.setFloorModelId(oneXinghaoId);
        updateXlUserInfoBean.setRightBrandId(zhengjiaoId);
        updateXlUserInfoBean.setRightModelId(twoXinghaoId);
        updateXlUserInfoBean.setRightLandId(houduZhengshouId);
        updateXlUserInfoBean.setRightHardId(hardZhengshouId);
        updateXlUserInfoBean.setRightColorId(zhengshouColorId);

        updateXlUserInfoBean.setLeftBrandId(fanjiaoId);
        updateXlUserInfoBean.setLeftModelId(threeXinghaoId);
        updateXlUserInfoBean.setLeftLandId(houduFanshouId);
        updateXlUserInfoBean.setLeftHardId(hardFanshouId);
        updateXlUserInfoBean.setLeftColorId(fanshouColorId);

        updateXlUserInfoBean.setDressSize(strCloth);
        updateXlUserInfoBean.setShoesSize(shoesCloth);
        showLoading();
        mViewModel.updateXlUserInfo(updateXlUserInfoBean);

    }

    //拍照
    @Override
    public void takePhoto() {
        RxPermissions rxPermissions = new RxPermissions(this);
        rxPermissions.request(Manifest.permission.WRITE_EXTERNAL_STORAGE, Manifest.permission.READ_EXTERNAL_STORAGE)
                .subscribe(new RxBusSubscriber<Boolean>() {
                    @Override
                    protected void onEvent(Boolean aBoolean) {
                        if (aBoolean) {
                            //申请的权限全部允许
                            //图片- 拍照
                            Intent intent = new Intent(PersonalInformationActivity.this, ImageGridActivity.class);
                            intent.putExtra(ImageGridActivity.EXTRAS_TAKE_PICKERS, true); // 是否是直接打开相机
                            startActivityForResult(intent, REQUEST_CODE_SELECT);
//                            ImagePicker imagePicker = ImagePicker.getInstance();
//                            imagePicker.setMultiMode(false);
//                            imagePicker.setShowCamera(false);
//                            imagePicker.setCrop(false);
//                            imagePicker.setFocusHeight(SystemHelper.getScreenInfo(PersonalInformationActivity.this).heightPixels);
//                            imagePicker.setFocusWidth(SystemHelper.getScreenInfo(PersonalInformationActivity.this).widthPixels);
//                            imagePickerUtil.takePhoto(PersonalInformationActivity.this, REQUEST_CODE_TAKE_PHOTO, PersonalInformationActivity.this);
                        } else {
                            //只要有一个权限被拒绝，就会执行
                            toast("未授权权限，部分功能不能使用");
                        }
                    }
                });
    }

    //相机
    @Override
    public void selectPhoto() {
//        ImagePicker imagePicker = ImagePicker.getInstance();
//        imagePicker.setMultiMode(true);
//        imagePicker.setCrop(true);
//        imagePicker.setShowCamera(true);
////        if (needPicNum < 0) {
////            needPicNum = 1;
////        }
//        imagePicker.setSelectLimit(1);
//        imagePicker.setCrop(false);
//        imagePicker.setMultiMode(true);
//        imagePickerUtil.selectPic(PersonalInformationActivity.this, REQUEST_CODE_SELECT_PHOTO, PersonalInformationActivity.this);
        Intent intent = new Intent(this, ImageGridActivity.class);
        startActivityForResult(intent, IMAGE_PICKER);
    }

    @Override
    public void callBack(List<ImageItem> list) {
        Log.e(TAG, "callBack: callBack========size===" + list.size());
//        List<String> images = new ArrayList<>();
//        showLoading();
//        File file2 = BitmapUtils.compressImageToSD(list.get(0).path, 30);
//        uploadImg2QiNiu(file2.getPath());
////        uploadImg2QiNiu(list.get(0).path);
//
//        for (ImageItem item : list
//        ) {
//            int degree = readPictureDegree(item.path);
//            Bitmap bitmap = rotateBitmap(BitmapFactory.decodeFile(item.path), degree);
//            saveBitmap(item.path, bitmap);
//            images.add(item.path);
//        }
    }

    /**
     * 读取图片属性：旋转的角度
     *
     * @param path 图片绝对路径
     * @return degree旋转的角度
     */
    public static int readPictureDegree(String path) {
        int degree = 0;
        try {
            ExifInterface exifInterface = new ExifInterface(path);
            int orientation = exifInterface.getAttributeInt(ExifInterface.TAG_ORIENTATION, ExifInterface.ORIENTATION_NORMAL);
            switch (orientation) {
                case ExifInterface.ORIENTATION_ROTATE_90:
                    degree = 90;
                    break;
                case ExifInterface.ORIENTATION_ROTATE_180:
                    degree = 180;
                    break;
                case ExifInterface.ORIENTATION_ROTATE_270:
                    degree = 270;
                    break;
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return degree;
    }

    /**
     * 保存方法
     */
    public static void saveBitmap(String path, Bitmap bm) {
        File f = new File(path);
        if (f.exists()) {
            f.delete();
        }
        try {
            FileOutputStream out = new FileOutputStream(f);
            bm.compress(Bitmap.CompressFormat.PNG, 90, out);
            out.flush();
            out.close();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void uploadImg2QiNiu(String localpicPath) {
        // 设置图片名字
        SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMddHHmmss");
        Random random = new Random();
        Set<Integer> set = new HashSet<Integer>();
        while (set.size() < 3) {
            int randomInt = random.nextInt(10);
            set.add(randomInt);
        }
        StringBuffer sb = new StringBuffer();
        for (Integer i : set) {
            sb.append("" + i);
        }
        key = "android_img_" + sdf.format(new Date()) + sb.toString();
        picPath = localpicPath;
        Log.e(TAG, "uploadImg2QiNiu: localPath===" + localpicPath);
        mViewModel.getUploadToken(key);
    }

    private void uploadImage(String picPath, String token, UploadManager uploadManager, String key) {
        Log.e(TAG, "uploadImage: picPath===" + picPath);
        Log.e(TAG, "uploadImage: token===" + token);
        Log.e(TAG, "uploadImage:key ===" + key);
//        uploadManager.put(picPath, key, Auth.create(Constants.AccessKey, Constants.SecretKey).uploadToken("xlttsports"), new UpCompletionHandler() {
                      uploadManager.put(picPath, key, token, new UpCompletionHandler() {
            @Override
            public void complete(String key, ResponseInfo info, JSONObject res) {
                // info.error中包含了错误信息，可打印调试
                dismissLoading();
                // 上传成功后将key值上传到自己的服务器
                if (info.isOK()) {
                    Log.e(TAG, "token===" + token);
                    String headpicPath = "http://images.xlttsports.com/" + key;
                    Log.e(TAG, "complete: " + headpicPath);

//                    ImageLoader.loadImage(mBinding.ivAvatar, headpicPath);
                    mCoverLogo = "" + headpicPath;

                } else {
                    toast("上传失败");
                    Log.e(TAG, "complete:上传失败 error===" + new Gson().toJson(info));
                }
                Log.e(TAG, "complete: error===" + new Gson().toJson(info));

            }
        }, null);
    }




    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        Log.e(TAG, "onActivityResult: requestCode===" + requestCode
                + "resultCode===" + resultCode);
        if (resultCode == ImagePicker.RESULT_CODE_ITEMS) {
            if (data != null && requestCode == IMAGE_PICKER) {
                //选择相册
                ArrayList<ImageItem> images = (ArrayList<ImageItem>) data.getSerializableExtra(ImagePicker.EXTRA_RESULT_ITEMS);
                Log.e(TAG, "onActivityResult:1111===== " + images.size());
                ImageLoader.loadImage(mBinding.ivAvatar, images.get(0).path);

                String path = images.get(0).path;
//                File file2 = BitmapUtils.compressImageToSD(path, 30);
                File file2 = CompressHelper.getDefault(this).compressToFile(new File(path));
                Log.e(TAG, "onActivityResult: "+file2.length() );


                uploadImg2QiNiu(file2.getPath());

            } else if (data != null && requestCode == REQUEST_CODE_SELECT) {
                ArrayList<ImageItem> images = (ArrayList<ImageItem>) data.getSerializableExtra(ImagePicker.EXTRA_RESULT_ITEMS);
                Log.e(TAG, "onActivityResult:2222===== " + images.size());
                ImageLoader.loadImage(mBinding.ivAvatar, images.get(0).path);

                String path = images.get(0).path;
                File file2 = CompressHelper.getDefault(this).compressToFile(new File(path));
                Log.e(TAG, "onActivityResult: "+file2.length() );

//                File file2 = BitmapUtils.compressImageToSD(path, 30);
                uploadImg2QiNiu(file2.getPath());
            } else {
                Toast.makeText(this, "没有数据", Toast.LENGTH_SHORT).show();
            }
        } else {
            Log.e(TAG, "onActivityResult: signIn签名");
            if(data!=null) {
                signPath = data.getStringExtra("path");
                Log.e(TAG, "onActivityResult: ===" + signPath + "newPath===" + data.getStringExtra("path"));
                mBinding.rlContent.setVisibility(View.GONE);
                mBinding.ivHardSignature.setVisibility(View.VISIBLE);
                mBinding.ivHardSignature.setBackground(null);
                mBinding.ivHardSignature.setBackgroundResource(0);
                ImageLoader.loadImage(mBinding.ivHardSignature, signPath);
                updateXlUserInfoBean.setAutographImg(signPath);
            }
        }
//        if (requestCode == 1) {
//            //.requestCode===1resultCode===1004  从相册选取
//
//            if (resultCode == RESULT_OK || resultCode == -1) {
//                signPath = data.getStringExtra("path");
//                Log.e(TAG, "onActivityResult: ===" + signPath + "newPath===" + data.getStringExtra("path"));
//                mBinding.rlContent.setVisibility(View.GONE);
//                mBinding.ivHardSignature.setVisibility(View.VISIBLE);
//                mBinding.ivHardSignature.setBackground(null);
//                mBinding.ivHardSignature.setBackgroundResource(0);
//                ImageLoader.loadImage(mBinding.ivHardSignature, signPath);
//                updateXlUserInfoBean.setAutographImg(signPath);
//            }
//        }else
//        {
//            Log.e(TAG, "onActivityResult: "+data.getExtras() );
//        }
//        else {
//            imagePickerUtil.handResult(requestCode, resultCode, data);
//        }
    }

    @Override
    public void onAreaCallback(String province, String city, String area) {
        Log.e(TAG, "onAreaCallback: " + province + "  " + city + "  " + area);
        mBinding.tvDetailAddress.setText("" + province + "" + city + "" + area);
        strCity = "" + province + " " + city + " " + area;
    }

    @Override
    public void onPointerCaptureChanged(boolean hasCapture) {

    }
}