package heyong.intellectPinPang.ui.club.activity;

import android.Manifest;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.graphics.Matrix;
import android.media.ExifInterface;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.lifecycle.Observer;

import com.bigkoo.pickerview.OptionsPickerView;
import com.google.gson.Gson;
import com.nanchen.compresshelper.CompressHelper;
import com.qiniu.android.http.ResponseInfo;
import com.qiniu.android.storage.UpCompletionHandler;
import com.qiniu.android.storage.UploadManager;
import com.tbruyelle.rxpermissions2.RxPermissions;
import com.weigan.loopview.dialog.AreaSelectDialog;

import org.json.JSONObject;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.Random;
import java.util.Set;

import heyong.intellectPinPang.ui.BaseActivity;
import heyong.handong.framework.base.ResponseBean;
import heyong.handong.framework.utils.ImageLoader;
import heyong.intellectPinPang.R;
import heyong.intellectPinPang.bean.competition.ClubTypeDataBean;
import heyong.intellectPinPang.bean.competition.XlClubInfoBean;
import heyong.intellectPinPang.databinding.ActivityCreateClubBinding;
import heyong.intellectPinPang.ui.club.viewmodel.ClubViewModel;
import heyong.intellectPinPang.utils.AntiShakeUtils;
import heyong.intellectPinPang.utils.CommonUtilis;
import heyong.intellectPinPang.utils.ImagePickerUtil;
import heyong.intellectPinPang.utils.MessageDialogBuilder;
import heyong.intellectPinPang.utils.RxBusSubscriber;
import heyong.intellectPinPang.widget.PhotoSelDialog;
import heyong.lzy.imagepicker.ImagePicker;
import heyong.lzy.imagepicker.bean.ImageItem;
import heyong.lzy.imagepicker.ui.ImageGridActivity;

/**
 * ???????????????
 * ??????????????? ?????????  ????????????   ????????????
 */
public class CreateClubActivity extends BaseActivity<ActivityCreateClubBinding, ClubViewModel> implements View.OnClickListener, PhotoSelDialog.OnSelectListener, ImagePickerUtil.OnImageCallback, AreaSelectDialog.OnAreaSelectedListener {
    private static final List<String> options1Items = new ArrayList<>();
    public static final String IDS = "ids";
    private String ids = "";
    private ImagePickerUtil imagePickerUtil;
    public static final String TAG = CreateClubActivity.class.getSimpleName();
    public static final int REQUEST_CODE_SELECT_PHOTO = 1; //??????-??????
    private static final int IMAGE_PICKER = 10001;
    private static final int REQUEST_CODE_SELECT = 10002;
    private static final int REQUEST_SIGN_IN = 10003;
    public static final int REQUEST_CODE_TAKE_PHOTO = 2;   //??????-??????
    private int type = 0;//0 ?????????logo  1??????????????????  2 ????????????????????????
    private int mClubType = 0;
    public static final String CLUB_TYPE = "create_type";

    private String strTeamType = "";
    private String strTeamCode = "";
    private String strCity = "";
    private String mCoverLogo = "";
    private String clubImgUrl = "";
    private String qualificationImgUrl = "";
    List<ClubTypeDataBean> clubTypeDataBeanList;
    String key = "";
    String picPath = "";
    XlClubInfoBean data;

    private String newCode;
    private String newText;

    @Override
    protected void onResume() {
        super.onResume();

    }

    @Override
    public int[] hideSoftByEditViewIds() {
        int[] ids = {R.id.et_team_introduce, R.id.et_team_mingcheng, R.id.et_jiancheng, R.id.et_in_charge,
                R.id.et_tel_number, R.id.et_tables_num, R.id.et_detail_address};
        return ids;
    }

    @Override
    public int getLayoutRes() {
        return R.layout.activity_create_club;
    }

    @Override
    public void initView(@Nullable Bundle savedInstanceState) {
        mClubType = getIntent().getIntExtra(CLUB_TYPE, 0);
        if (mClubType == 0) {
            mBinding.topBar.setCenterText("???????????????");
        } else {
            /*??????????????????????????????  ??????????????? ?????????  ????????????   ?????????????????????????????????????????????*/
            mBinding.topBar.setCenterText("???????????????");
            mBinding.rlTeamType.setEnabled(false);//???????????????????????????
            ids = getIntent().getStringExtra(IDS);
            data = (XlClubInfoBean) getIntent().getSerializableExtra("data");
            clubTypeDataBeanList = (List<ClubTypeDataBean>) getIntent().getSerializableExtra("mydata");

            ImageLoader.loadImage(mBinding.ivUploadCoverLogo, data.getCoverLogo());
            if (!TextUtils.isEmpty(data.getClubImgUrl())) {
                mBinding.ivClubImage.setVisibility(View.VISIBLE);
            }
            ImageLoader.loadImage(mBinding.ivClubImage, data.getClubImgUrl());
//et_team_mingcheng  et_jiancheng
            mBinding.etTeamMingcheng.setText("" + data.getTeamTitle());
            mBinding.etJiancheng.setText("" + data.getAbbreviation());
            mBinding.etInCharge.setText("" + data.getInCharge());
            mBinding.etTelNumber.setText("" + data.getPhoneNum());
            mBinding.tvSelectCity.setText("" + data.getCity());
            mBinding.tvSelectCity.setTextColor(Color.BLACK);
            mCoverLogo = data.getCoverLogo();
            clubImgUrl = data.getClubImgUrl();
            qualificationImgUrl = data.getQualificationImgUrl();
            if (TextUtils.isEmpty(qualificationImgUrl)) {
                mBinding.rlClubImageCertificate.setVisibility(View.VISIBLE);
            } else {
                mBinding.rlClubImageCertificate.setVisibility(View.GONE);
                mBinding.ivClubImageCertificate.setVisibility(View.VISIBLE);
                ImageLoader.loadImage(mBinding.ivClubImageCertificate, qualificationImgUrl);
            }
            /*????????? ???????????? ????????????*/
            mBinding.etTablesNum.setText("" + data.getTablesNum());
            mBinding.etDetailAddress.setText("" + data.getDetailAddress());
            mBinding.etTeamIntroduce.setText("" + data.getTeamIntroduce());
            strCity = "" + data.getCity();
            strTeamType = data.getTeamType();
            newCode = data.getTeamType();
            for (int i = 0; i < clubTypeDataBeanList.size(); i++) {
                if (clubTypeDataBeanList.get(i).getCode().equals(newCode)) {
                    strTeamCode = clubTypeDataBeanList.get(i).getCode();
                    mBinding.tvTeamType.setText("" + clubTypeDataBeanList.get(i).getText());
                    newText = clubTypeDataBeanList.get(i).getText();
                    mBinding.tvTeamType.setTextColor(Color.BLACK);
                }
            }

            if (newText.equals("????????????")) {
                mBinding.llClubZhengshu.setVisibility(View.GONE);
                mBinding.rlClubImageCertificate.setEnabled(false);

            } else {
                mBinding.llClubZhengshu.setVisibility(View.VISIBLE);
                mBinding.rlClubImageCertificate.setEnabled(true);
            }


            //???????????????   ?????????????????????
//            mBinding.etInCharge.setFocusableInTouchMode(false);//????????????
//            mBinding.etInCharge.setKeyListener(null);//??????????????????????????????????????????
//            mBinding.etInCharge.setClickable(false);//??????????????????????????????????????????????????????????????????????????????
//            mBinding.etInCharge.setFocusable(false);//????????????
//rl_club_image_certificate
            //????????????
            mBinding.etTelNumber.setFocusableInTouchMode(false);//????????????
            mBinding.etTelNumber.setKeyListener(null);//??????????????????????????????????????????
            mBinding.etTelNumber.setClickable(false);//??????????????????????????????????????????????????????????????????????????????
            mBinding.etTelNumber.setFocusable(false);//????????????


        }
        mBinding.setListener(this);
        imagePickerUtil = new ImagePickerUtil();
        mViewModel.getClubTypeDataLiveData.observe(this, new Observer<ResponseBean<List<ClubTypeDataBean>>>() {
            @Override
            public void onChanged(ResponseBean<List<ClubTypeDataBean>> loginBeanResponseBean) {
                dismissLoading();
                if (loginBeanResponseBean.getErrorCode().equals("200")) {

                    clubTypeDataBeanList = loginBeanResponseBean.getData();
                    if (clubTypeDataBeanList != null && clubTypeDataBeanList.size() > 0) {
                        options1Items.clear();
                        for (int i = 0; i < clubTypeDataBeanList.size(); i++) {
                            options1Items.add("" + clubTypeDataBeanList.get(i).getText());
                        }
                        showType();
                    }

                } else {
                    toast("" + loginBeanResponseBean.getMessage());
                }

            }
        });


        mViewModel.createXlClubInfoLiveData.observe(this, new Observer<ResponseBean>() {
            @Override
            public void onChanged(ResponseBean loginBeanResponseBean) {
                if (loginBeanResponseBean.getErrorCode().equals("200")) {
                    toast("????????????");
                    finish();
                } else {
                    toast("" + loginBeanResponseBean.getMessage());
                }

            }
        });
        mViewModel.editXlClubInfoLiveData.observe(this, new Observer<ResponseBean>() {
            @Override
            public void onChanged(ResponseBean responseBean) {
                if (responseBean.getErrorCode().equals("200")) {
                    toast("????????????");
                    finish();
                } else {
                    toast("" + responseBean.getMessage());
                }

            }
        });


        mViewModel.getUploadTokenLiveData.observe(this, new Observer<ResponseBean>() {
            @Override
            public void onChanged(ResponseBean responseBean) {
                if (responseBean.getErrorCode().equals("200")) {
                    UploadManager uploadManager = new UploadManager();
                    // ??????????????????
                    uploadImage(picPath, responseBean.getData().toString(), uploadManager, key);
                } else {
                    toast("????????????");
                }

            }
        });

    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.rl_select_city://????????????
                if (AntiShakeUtils.isInvalidClick(v))
                    return;
                AreaSelectDialog mAreaSelectDialogss = new AreaSelectDialog(CreateClubActivity.this, 0);
                mAreaSelectDialogss.setListener(CreateClubActivity.this);
                mAreaSelectDialogss.show();

                break;
            case R.id.iv_upload_cover_logo://????????????

                if (AntiShakeUtils.isInvalidClick(v))
                    return;
                type = 0;
                PhotoSelDialog dialog = new PhotoSelDialog(this);
                dialog.setClickListener(this);
                dialog.show();
                break;

            case R.id.rl_team_type://????????????
//                showLoading("?????????");
                if (AntiShakeUtils.isInvalidClick(v))
                    return;
                if (mClubType == 0) {
                    showLoading();
                    mViewModel.getClubTypeData();
                }


                break;
            case R.id.tv_submit:
                if (AntiShakeUtils.isInvalidClick(v))
                    return;
                if (mClubType == 0) {
                    String etTeamTitle = mBinding.etTeamMingcheng.getText().toString().trim();
                    String rtAbbreviation = mBinding.etJiancheng.getText().toString().trim();
                    String etInCharge = mBinding.etInCharge.getText().toString().trim();
                    String phoneNumber = mBinding.etTelNumber.getText().toString().trim();
                    String tablesNumber = mBinding.etTablesNum.getText().toString().trim();
                    String detailAddress = mBinding.etDetailAddress.getText().toString().trim();
                    String teamIntroduce = mBinding.etTeamIntroduce.getText().toString().trim();

                    if (TextUtils.isEmpty(mCoverLogo)) {
                        toast("???????????????LOGO");
                        return;
                    }
                    if (TextUtils.isEmpty(strTeamType)) {
                        toast("?????????????????????");
                        return;
                    }
                    if (TextUtils.isEmpty(etTeamTitle)) {
                        toast("?????????????????????");
                        return;
                    }
                    if (TextUtils.isEmpty(rtAbbreviation)) {
                        toast("?????????????????????");
                        return;
                    }
                    if (TextUtils.isEmpty(etInCharge)) {
                        toast("??????????????????");
                        return;
                    }
                    if (TextUtils.isEmpty(phoneNumber)) {
                        toast("?????????????????????");
                        return;
                    }


                    if (!CommonUtilis.isMobileNO(phoneNumber)) {
                        toast("??????????????????????????????");
                        return;
                    }
                    if (TextUtils.isEmpty(strCity)) {
                        toast("???????????????");
                        return;
                    }
                    if (TextUtils.isEmpty(clubImgUrl)) {
                        toast("????????????????????????");
                        return;
                    }
                    if (!strTeamType.equals("????????????")) {
                        if (TextUtils.isEmpty(qualificationImgUrl)) {
                            toast("??????????????????????????????");
                            return;
                        }
                    }
                    if (TextUtils.isEmpty(tablesNumber)) {
                        toast("??????????????????");
                        return;
                    }
                    if (TextUtils.isEmpty(detailAddress)) {
                        toast("?????????????????????");
                        return;
                    }
                    if (TextUtils.isEmpty(teamIntroduce)) {
                        toast("?????????????????????");
                        return;
                    }
                    new MessageDialogBuilder(CreateClubActivity.this)
                            .setMessage("??????????????????????????????????????????")
                            .setTvTitle("?????????????????????")
                            .setTvCancel("??????")
                            .setBtnCancleHint(false)
                            .setTvSure("??????")
                            .setSureListener(v1 ->
                                    commitCreate1(etTeamTitle, rtAbbreviation, etInCharge, phoneNumber, tablesNumber, detailAddress, teamIntroduce)
                            )
                            .show();


                } else {
//                    editXlClubInfo
                    String etTeamTitle = mBinding.etTeamMingcheng.getText().toString().trim();
                    String rtAbbreviation = mBinding.etJiancheng.getText().toString().trim();
                    String etInCharge = mBinding.etInCharge.getText().toString().trim();
                    String phoneNumber = mBinding.etTelNumber.getText().toString().trim();
                    String tablesNumber = mBinding.etTablesNum.getText().toString().trim();
                    String detailAddress = mBinding.etDetailAddress.getText().toString().trim();
                    String teamIntroduce = mBinding.etTeamIntroduce.getText().toString().trim();

                    if (TextUtils.isEmpty(mCoverLogo)) {
                        toast("???????????????LOGO");
                        return;
                    }
                    if (TextUtils.isEmpty(strTeamType)) {
                        toast("?????????????????????");
                        return;
                    }
                    if (TextUtils.isEmpty(etTeamTitle)) {
                        toast("?????????????????????");
                        return;
                    }
                    if (TextUtils.isEmpty(rtAbbreviation)) {
                        toast("?????????????????????");
                        return;
                    }
                    if (TextUtils.isEmpty(etInCharge)) {
                        toast("??????????????????");
                        return;
                    }
                    if (TextUtils.isEmpty(phoneNumber)) {
                        toast("?????????????????????");
                        return;
                    }
                    if (TextUtils.isEmpty(strCity)) {
                        toast("???????????????");
                        return;
                    }
                    if (TextUtils.isEmpty(clubImgUrl)) {
                        toast("????????????????????????");
                        return;
                    }
                    if (!newText.equals("????????????")) {
                        if (TextUtils.isEmpty(qualificationImgUrl)) {
                            toast("??????????????????????????????");
                            return;
                        }
                    }
                    if (TextUtils.isEmpty(tablesNumber)) {
                        toast("??????????????????");
                        return;
                    }
                    if (TextUtils.isEmpty(detailAddress)) {
                        toast("?????????????????????");
                        return;
                    }
                    if (TextUtils.isEmpty(teamIntroduce)) {
                        toast("?????????????????????");
                        return;
                    }
                    if (mClubType == 0) {
                        //????????????????????????
                        new MessageDialogBuilder(CreateClubActivity.this)
                                .setMessage("??????????????????????????????????????????")
                                .setTvTitle("?????????????????????")
                                .setTvCancel("??????")
                                .setBtnCancleHint(false)
                                .setTvSure("??????")
                                .setSureListener(v1 ->
                                        commitCreate2(etTeamTitle, rtAbbreviation, etInCharge, phoneNumber, tablesNumber, detailAddress, teamIntroduce)
                                )
                                .show();
                    } else {
                        //????????????????????????
                        new MessageDialogBuilder(CreateClubActivity.this)
                                .setMessage("")
                                .setTvTitle("???????????????????????????")
                                .setTvCancel("??????")
                                .setBtnCancleHint(false)
                                .setTvSure("??????")
                                .setSureListener(v1 ->
                                        commitCreate2(etTeamTitle, rtAbbreviation, etInCharge, phoneNumber, tablesNumber, detailAddress, teamIntroduce)
                                )
                                .show();
                    }


                }
                break;
            case R.id.rl_club_image://?????????????????????    ??????????????????  ??????????????????????????????????????????
                if (AntiShakeUtils.isInvalidClick(v))
                    return;
                type = 1;
                PhotoSelDialog dialog2 = new PhotoSelDialog(this);
                dialog2.setClickListener(this);
                dialog2.show();

                break;
            case R.id.rl_club_image_certificate://???????????????????????????
                if (AntiShakeUtils.isInvalidClick(v))
                    return;
//                if (mClubType == 0) {
                    type = 2;
                    PhotoSelDialog dialog3 = new PhotoSelDialog(this);
                    dialog3.setClickListener(this);
                    dialog3.show();
//                }


                break;
        }
    }

    private void commitCreate2(String etTeamTitle, String rtAbbreviation, String etInCharge, String phoneNumber, String tablesNumber, String detailAddress, String teamIntroduce) {
        mViewModel.editXlClubInfo("" + ids, etTeamTitle, "" + mCoverLogo,
                "" + strTeamCode, "" + rtAbbreviation,
                "" + etInCharge, "" + phoneNumber, "" + strCity,
                "" + detailAddress, "" + clubImgUrl, "" + qualificationImgUrl,
                "" + tablesNumber, "" + teamIntroduce);
    }

    private void commitCreate1(String etTeamTitle, String rtAbbreviation, String etInCharge, String phoneNumber, String tablesNumber, String detailAddress, String teamIntroduce) {
        mViewModel.createXlClubInfo("" + etTeamTitle, "" + mCoverLogo,
                "" + strTeamCode, "" + rtAbbreviation,
                "" + etInCharge, "" + phoneNumber, "" + strCity,
                "" + detailAddress, "" + clubImgUrl, "" + qualificationImgUrl,
                "" + tablesNumber, "" + teamIntroduce);
    }

    private void showType() {
        OptionsPickerView pvOptions = new OptionsPickerView.Builder(CreateClubActivity.this, new OptionsPickerView.OnOptionsSelectListener() {
            @Override
            public void onOptionsSelect(int options1, int option2, int options3, View v) {
                //?????????????????????????????????????????????
                String s = options1Items.get(options1);
//                        button4.setText(s);
                strTeamType = s;
                for (int i = 0; i < clubTypeDataBeanList.size(); i++) {
                    if (clubTypeDataBeanList.get(i).getText().equals(strTeamType)) {
                        strTeamCode = clubTypeDataBeanList.get(i).getCode();
                    }
                }
                mBinding.tvTeamType.setText("" + s);
                mBinding.tvTeamType.setTextColor(Color.BLACK);
                if (strTeamType.equals("????????????")) {
                    mBinding.tvText.setVisibility(View.VISIBLE);
                    mBinding.llClubZhengshu.setVisibility(View.GONE);
                } else {
                    mBinding.tvText.setVisibility(View.GONE);

                    mBinding.llClubZhengshu.setVisibility(View.VISIBLE);
                }
//                        mBinding.tvTeamType.setTextColor(Color.BLACK);
            }
        })
//                        .setSubmitText("??????")//??????????????????
//                        .setCancelText("??????")//??????????????????
//                        .setTitleText("????????????")//??????
                .setSubCalSize(20)//???????????????????????????
//                        .setTitleSize(20)//??????????????????
//                        .setTitleColor(Color.BLACK)//??????????????????
//                        .setSubmitColor(Color.BLUE)//????????????????????????
//                        .setCancelColor(Color.BLUE)//????????????????????????
//                        .setTitleBgColor(0xFF333333)//?????????????????? Night mode
//                        .setBgColor(0xFF000000)//?????????????????? Night mode
//                        .setContentTextSize(18)//??????????????????
//                        .setTextColorCenter(Color.BLUE)//????????????????????????
                .setTextColorCenter(Color.parseColor("#333333"))//????????????????????????
//setTextColorOut
                .setTextColorOut(Color.parseColor("#666666"))
//                        .setLineSpacingMultiplier(1.6f)//????????????????????????????????????
//                        .setLinkage(false)//???????????????????????????true
//                        .setLabels("???", "???", "???")//???????????????????????????
//                        .isCenterLabel(false) //?????????????????????????????????label?????????false?????????item???????????????label???
//                        .setCyclic(false, false, false)//????????????
//                        .setSelectOptions(1, 1, 1)  //?????????????????????
//                        .setOutSideCancelable(false)//????????????dismiss default true
//                        .isDialog(true)//??????????????????????????????
                .build();
        pvOptions.setPicker(options1Items);
        pvOptions.show();
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
//        imagePickerUtil.handResult(requestCode, resultCode, data);
        if (resultCode == ImagePicker.RESULT_CODE_ITEMS) {
            if (data != null && requestCode == IMAGE_PICKER) {
                //????????????
                ArrayList<ImageItem> images = (ArrayList<ImageItem>) data.getSerializableExtra(ImagePicker.EXTRA_RESULT_ITEMS);
                Log.e(TAG, "onActivityResult:1111===== " + images.size());
//                ImageLoader.loadImage(mBinding.ivAvatar, images.get(0).path);

                String path = images.get(0).path;
//                File file2 = BitmapUtils.compressImageToSD(path, 30);
                File file2 = CompressHelper.getDefault(this).compressToFile(new File(path));
                Log.e(TAG, "onActivityResult: " + file2.length());


                uploadImg2QiNiu(file2.getPath());

            } else if (data != null && requestCode == REQUEST_CODE_SELECT) {
                ArrayList<ImageItem> images = (ArrayList<ImageItem>) data.getSerializableExtra(ImagePicker.EXTRA_RESULT_ITEMS);
                Log.e(TAG, "onActivityResult:2222===== " + images.size());
//                ImageLoader.loadImage(mBinding.ivAvatar, images.get(0).path);

                String path = images.get(0).path;
                File file2 = CompressHelper.getDefault(this).compressToFile(new File(path));
                Log.e(TAG, "onActivityResult: " + file2.length());

//                File file2 = BitmapUtils.compressImageToSD(path, 30);
                uploadImg2QiNiu(file2.getPath());
            } else {
                Toast.makeText(this, "????????????", Toast.LENGTH_SHORT).show();
            }
        } else {
//            Log.e(TAG, "onActivityResult: signIn??????");
//            if(data!=null) {
//                signPath = data.getStringExtra("path");
//                Log.e(TAG, "onActivityResult: ===" + signPath + "newPath===" + data.getStringExtra("path"));
//                mBinding.rlContent.setVisibility(View.GONE);
//                mBinding.ivHardSignature.setVisibility(View.VISIBLE);
//                mBinding.ivHardSignature.setBackground(null);
//                mBinding.ivHardSignature.setBackgroundResource(0);
//                ImageLoader.loadImage(mBinding.ivHardSignature, signPath);
//                updateXlUserInfoBean.setAutographImg(signPath);
//            }
        }
    }

    @Override
    public void takePhoto() {
        RxPermissions rxPermissions = new RxPermissions(this);
        rxPermissions.request(Manifest.permission.WRITE_EXTERNAL_STORAGE, Manifest.permission.READ_EXTERNAL_STORAGE)
                .subscribe(new RxBusSubscriber<Boolean>() {
                    @Override
                    protected void onEvent(Boolean aBoolean) {
                        if (aBoolean) {
                            //???????????????????????????
                            //??????- ??????
                            Intent intent = new Intent(CreateClubActivity.this, ImageGridActivity.class);
                            intent.putExtra(ImageGridActivity.EXTRAS_TAKE_PICKERS, true); // ???????????????????????????
                            startActivityForResult(intent, REQUEST_CODE_SELECT);
//                            ImagePicker imagePicker = ImagePicker.getInstance();
//                            imagePicker.setMultiMode(false);
//                            imagePicker.setShowCamera(false);
//                            imagePicker.setCrop(false);
//                            imagePicker.setFocusHeight(SystemHelper.getScreenInfo(CreateClubActivity.this).heightPixels);
//                            imagePicker.setFocusWidth(SystemHelper.getScreenInfo(CreateClubActivity.this).widthPixels);
//                            imagePickerUtil.takePhoto(CreateClubActivity.this, REQUEST_CODE_TAKE_PHOTO, CreateClubActivity.this);
                        } else {
                            //?????????????????????????????????????????????
                            toast("??????????????????????????????????????????");
                        }
                    }
                });
    }

    @Override
    public void selectPhoto() {
        //?????? - ????????????
//        if ( binding.gridImageView.getImages().size() >= 9) {
//            ToastUtils.showShort("??????9????????????");
//        }
//        int needPicNum = 9 -  binding.gridImageView.getImages().size();
//        ImagePicker imagePicker = ImagePicker.getInstance();
//        imagePicker.setMultiMode(true);
//        imagePicker.setShowCamera(false);
////        if (needPicNum < 0) {
////            needPicNum = 1;
////        }
//        imagePicker.setSelectLimit(1);
//        imagePicker.setCrop(false);
//        imagePickerUtil.selectPic(CreateClubActivity.this, REQUEST_CODE_SELECT_PHOTO, CreateClubActivity.this);
        Intent intent = new Intent(this, ImageGridActivity.class);
        startActivityForResult(intent, IMAGE_PICKER);
    }

    public static String getTime(Date date) {//???????????????????????????????????????
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
        return format.format(date);
    }

    public static String getTimeMinute(Date date) {//???????????????????????????????????????
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm");
        return format.format(date);
    }

    public static String getTimeSecond(Date date) {//???????????????????????????????????????
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");
        return format.format(date);
    }

    public static String getYearDay(Date date) {//???????????????????????????????????????
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
        return format.format(date);
    }

    private void uploadImg2QiNiu(String localpicPath) {
        // ??????????????????
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
    UpCompletionHandler handler=new UpCompletionHandler() {
        @Override
        public void complete(String s, ResponseInfo responseInfo, JSONObject jsonObject) {

        }
    };
    private void uploadImage(String picPath, String token, UploadManager uploadManager, String key) {
        Log.e(TAG, "uploadImage: picPath===" + picPath);
        Log.e(TAG, "uploadImage: token===" + token);
        Log.e(TAG, "uploadImage:key ===" + key);
//        uploadManager.put(picPath, key, Auth.create(Constants.AccessKey, Constants.SecretKey).uploadToken("xlttsports"), new UpCompletionHandler() {
//        uploadManager.put(picPath, key, token,handler,
//                new UploadOptions(null, null, false,
//                        new UpProgressHandler(){
//                            public void progress(String key, double percent){
//                                Log.i("qiniu", key + ": " + percent);
//                            }
//                        }, null));
        uploadManager.put(picPath, key, token, new UpCompletionHandler() {
            @Override
            public void complete(String key, ResponseInfo info, JSONObject res) {
                // info.error??????????????????????????????????????????
                // ??????????????????key??????????????????????????????
                if (info.isOK()) {
                    Log.e(TAG, "token===" + token);
                    String headpicPath = "http://images.xlttsports.com/" + key;
                    Log.e(TAG, "complete: " + headpicPath);
                    if (type == 0) {
                        ImageLoader.loadImage(mBinding.ivUploadCoverLogo, headpicPath);
                        mCoverLogo = "" + headpicPath;
                    } else if (type == 1) {
                        ImageLoader.loadImage(mBinding.ivClubImage, headpicPath);
                        mBinding.ivClubImage.setVisibility(View.VISIBLE);
                        mBinding.rlClubImage.setVisibility(View.GONE);
                        clubImgUrl = "" + headpicPath;
                    } else if (type == 2) {
                        ImageLoader.loadImage(mBinding.ivClubImageCertificate, headpicPath);
                        mBinding.ivClubImageCertificate.setVisibility(View.VISIBLE);
                        mBinding.rlClubImageCertificate.setVisibility(View.GONE);
                        qualificationImgUrl = "" + headpicPath;
                    }
                } else {
                    toast("????????????");
                    Log.e(TAG, "complete: error===" + new Gson().toJson(info));
                }
                Log.e(TAG, "complete: error===" + new Gson().toJson(info));

            }
        }, null);
    }

    @Override
    public void callBack(List<ImageItem> list) {
        List<String> images = new ArrayList<>();
        String path = list.get(0).path;
//        File file2 = BitmapUtils.compressImageToSD(path, 30);
        File file2 = CompressHelper.getDefault(this).compressToFile(new File(path));
        Log.e(TAG, "callBack: length==" + file2.length());
        if (type == 0) {
//
//            uploadImg2QiNiu(file2.getPath());
            uploadImg2QiNiu(file2.getPath());
        } else if (type == 1) {
            uploadImg2QiNiu(file2.getPath());


        } else if (type == 2) {
            uploadImg2QiNiu(file2.getPath());

        }
        for (ImageItem item : list
        ) {
//            int degree = readPictureDegree(item.path);
//            Bitmap bitmap = rotateBitmap(BitmapFactory.decodeFile(item.path), degree);
//            saveBitmap(item.path, bitmap);
            images.add(item.path);
        }

//        ImageLoader.loadImage(mBinding.ivUploadCoverLogo, list.get(0).path);
//        viewModel.imgItems.addAll(images);
//        binding.gridImageView.setImages(images);
    }

    public static Bitmap rotateBitmap(Bitmap bitmap, int degress) {
        if (bitmap != null) {
            Matrix m = new Matrix();
            m.postRotate(degress);
            bitmap = Bitmap.createBitmap(bitmap, 0, 0, bitmap.getWidth(), bitmap.getHeight(), m, true);
            return bitmap;
        }
        return bitmap;
    }

    /**
     * ????????????
     */
    public static void saveBitmap(String path, Bitmap bm) {
        File f = new File(path);
//        if (f.exists()) {
//            f.delete();
//        }
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

    /**
     * ????????????????????????????????????
     *
     * @param path ??????????????????
     * @return degree???????????????
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


    @Override
    public void onAreaCallback(String province, String city, String area) {
        Log.e(TAG, "onAreaCallback: " + province + "  " + city + "  " + area);
        mBinding.tvSelectCity.setText("" + province + "" + city + "" + area);
        strCity = "" + province + " " + city + " " + area;
    }
}