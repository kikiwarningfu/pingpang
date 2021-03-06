package heyong.intellectPinPang.ui.homepage.activity;

import android.Manifest;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.util.Log;
import android.view.KeyEvent;
import android.view.MotionEvent;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.lifecycle.Observer;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.bigkoo.pickerview.OptionsPickerView;
import com.bigkoo.pickerview.TimeGameSelectView;
import com.bigkoo.pickerview.TimePickerView;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.nanchen.compresshelper.CompressHelper;
import com.qiniu.android.http.ResponseInfo;
import com.qiniu.android.storage.UpCompletionHandler;
import com.qiniu.android.storage.UploadManager;
import com.tbruyelle.rxpermissions2.RxPermissions;
import com.weigan.loopview.dialog.AreaSelectDialog;
import com.xq.fasterdialog.dialog.CustomDialog;

import org.json.JSONObject;

import java.io.File;
import java.io.Serializable;
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
import heyong.intellectPinPang.adapter.game.CreateCompetitionCategoriesAdapter;
import heyong.intellectPinPang.bean.competition.SelectClubListBean;
import heyong.intellectPinPang.bean.gsonbean.CreateEventsBean;
import heyong.intellectPinPang.databinding.ActivityCreateEventsBinding;
import heyong.intellectPinPang.ui.homepage.viewmodel.CreateEventsViewModel;
import heyong.intellectPinPang.utils.AntiShakeUtils;
import heyong.intellectPinPang.utils.CommonUtilis;
import heyong.intellectPinPang.utils.ImagePickerUtil;
import heyong.intellectPinPang.utils.MessageDialogBuilder;
import heyong.intellectPinPang.utils.MyDateUtils;
import heyong.intellectPinPang.utils.RxBusSubscriber;
import heyong.intellectPinPang.widget.PhotoSelDialog;
import heyong.lzy.imagepicker.ImagePicker;
import heyong.lzy.imagepicker.bean.ImageItem;
import heyong.lzy.imagepicker.ui.ImageGridActivity;

import static heyong.intellectPinPang.ui.club.activity.CreateClubActivity.getTime;
import static heyong.intellectPinPang.ui.club.activity.CreateClubActivity.getTimeMinute;


/**
 * ?????????????????? 1  ???????????? APP????????? ???1  ??????     ???????????????????????? ?????????????????? ?????????????????? ?????? ????????????????????? ???????????????????????????????????????
 * ?????????????????????
 */
public class CreateEventsActivity extends BaseActivity<ActivityCreateEventsBinding, CreateEventsViewModel> implements View.OnClickListener, AreaSelectDialog.OnAreaSelectedListener, PhotoSelDialog.OnSelectListener, ImagePickerUtil.OnImageCallback {
    private static final List<String> options1Items = new ArrayList<>();
    private static final List<String> competitionLevels = new ArrayList<>();
    private static final List<String> levelCompetitions = new ArrayList<>();
    public static final String TAG = CreateEventsActivity.class.getSimpleName();
    CreateEventsBean createEventsBean;
    CreateCompetitionCategoriesAdapter createCompetitionCategoriesAdapter;
    private List<CreateEventsBean.ProjectListBean> data = new ArrayList<>();
    private String matchshuiping = "";
    private String matchdengji = "";
    private String strClubName = "";
    private String strApplyClubId = "";
    //    setApplyClubId
    private ImagePickerUtil imagePickerUtil;
    //-----------------------------------??????????????????
    private String strStartAndEndKaishi = "";
    private String strStartAndEnd = "";
    private String strStartAndEndJieshu = "";
    private String strEndTime = "";
    private String strLocalCity = "";
    //------------------------------------??????????????????
    private String strMatchImgUrl = "";
    private String strStampImgUrl = "";
    private int strImageShowType = 1;//1 ???????????????  2 ???????????????
    private int matchImgType = 0;
    //--------------------------------------????????????  ????????????
    private int allowPersonalType = 0;//0 ????????????  1 ???????????????????????????
    private int realType = 0;//0????????????????????? 1 ?????????????????????
    private int needMoneyType = 0;//0?????????APP????????? 1 ?????????APP??????
    private int collectionRoomType = 0;//0?????????????????????  1??????????????????
    //???????????????   ??????needMoneyType== 0 ?????????????????? ???????????????
    //??????????????????????????? needMoneyType==1 ?????? ?????????????????????????????? ?????????0  ??????  ???1  ??????
    String strGroupName = "";//????????????

    public static final int REQUEST_CODE_SELECT_PHOTO = 1; //??????-??????
    public static final int REQUEST_CODE_TAKE_PHOTO = 2;   //??????-??????
    private boolean baomingTuantiFlag = false;
    private boolean baomingTuantiTwoFlag = false;

    CustomDialog customDialogAddGroup;

    List<SelectClubListBean> selectClubList = new ArrayList<>();
    private Date dateEndTime;

    //    private
    @Override
    public int[] hideSoftByEditViewIds() {
        int[] ids = {R.id.et_saishi_name, R.id.et_zhubanfang, R.id.et_chengbanfa, R.id.et_detail_address, R.id.et_connect_name
                , R.id.et_tel_phone, R.id.et_tuanti_baoming_money, R.id.et_personal_baoming_money, R.id.et_eat_money, R.id.et_day};
        return ids;
    }

    public int getLayoutRes() {
        return R.layout.activity_create_events;
    }

    @Override
    public void initView(@Nullable Bundle savedInstanceState) {
        mBinding.setListener(this);
        createEventsBean = new CreateEventsBean();
        createCompetitionCategoriesAdapter = new CreateCompetitionCategoriesAdapter(CreateEventsActivity.this);
        imagePickerUtil = new ImagePickerUtil();
        mBinding.llBiddingForInformation.setVisibility(View.VISIBLE);
        mBinding.llEventInformation.setVisibility(View.GONE);
        mBinding.llUploadPictures.setVisibility(View.GONE);
        mBinding.llChargingSet.setVisibility(View.GONE);
        mBinding.llCompetitionItem.setVisibility(View.GONE);
        mBinding.topBar.getIvFinish().setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                new MessageDialogBuilder(CreateEventsActivity.this)
                        .setMessage("?????????????????????????????????????????????????????????\n" +
                                "???????????????")
                        .setTvTitle("?????????????????????????????????")
                        .setTvCancel("??????")
                        .setTvSure("??????")
                        .setBtnCancleHint(false)
                        .setSureListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                finish();
                            }
                        })
                        .show();
            }
        });

        options1Items.clear();
        options1Items.add("?????????");
        options1Items.add("?????????");
        options1Items.add("???????????????");
        options1Items.add("?????????????????????");
        options1Items.add("???????????????");
        options1Items.add("???????????????");
        options1Items.add("???????????????");
        options1Items.add("????????????");
        competitionLevels.clear();
        competitionLevels.add("?????????");
        competitionLevels.add("??????");
        competitionLevels.add("??????");
        levelCompetitions.clear();
        levelCompetitions.add("?????????");
        levelCompetitions.add("?????????");


        mBinding.rvCreateGroupCompetition.setLayoutManager(new LinearLayoutManager(this, RecyclerView.VERTICAL, false));
        mBinding.rvCreateGroupCompetition.setAdapter(createCompetitionCategoriesAdapter);
        createCompetitionCategoriesAdapter.setNewData(data);
        createCompetitionCategoriesAdapter.setOnItemChildClickListener(new BaseQuickAdapter.OnItemChildClickListener() {
            @Override
            public void onItemChildClick(BaseQuickAdapter adapter, View view, int position) {
                boolean fastClick = isFastClick();
                if (!fastClick) {
                    switch (view.getId()) {
                        case R.id.ll_add_project:
                            List<CreateEventsBean.ProjectListBean> data = createCompetitionCategoriesAdapter.getData();

                            boolean isShuangDaClickNan = false;
                            boolean isShuangDaClickWoman = false;
                            if (data.size() == 0) {
                                isShuangDaClickNan = false;
                                isShuangDaClickWoman = false;
                            } else {
                                for (int i = 0; i < data.size(); i++) {
                                    List<CreateEventsBean.ProjectListBean.ProjectItemListBean> projectItemList = data.get(i).getProjectItemList();
                                    for (int j = 0; j < projectItemList.size(); j++) {
                                        if (projectItemList.get(j).getItemTitle().contains("?????????")) {
                                            isShuangDaClickNan = true;
                                        }
                                    }

                                    for (int j1 = 0; j1 < projectItemList.size(); j1++) {
                                        if (projectItemList.get(j1).getItemTitle().contains("?????????")) {
                                            isShuangDaClickWoman = true;
                                        }
                                    }

                                }
                            }
                            boolean isHunShuangClick = false;
                            if (matchshuiping.equals("?????????")) {
                                /*????????????????????? ??????????????????*/
                                boolean isSelectNan = false;
                                boolean isSelectNv = false;
                                for (int i = 0; i < data.size(); i++) {
                                    List<CreateEventsBean.ProjectListBean.ProjectItemListBean> projectItemList = data.get(i).getProjectItemList();
                                    for (int j = 0; j < projectItemList.size(); j++) {
                                        if (projectItemList.get(j).getItemTitle().contains("?????????")) {
                                            isSelectNan = true;
                                        }
                                    }

                                    for (int j1 = 0; j1 < projectItemList.size(); j1++) {
                                        if (projectItemList.get(j1).getItemTitle().contains("?????????")) {
                                            isSelectNv = true;
                                        }
                                    }

                                }
                                if (isSelectNan && isSelectNv) {
                                    isHunShuangClick = true;
                                } else {
                                    isHunShuangClick = false;
                                }
                            } else {
                                isHunShuangClick = true;
                            }
                            String groupName = data.get(position).getProjectTitle();
                            Intent intent = new Intent(CreateEventsActivity.this, NewCompetitionActivity.class);
                            if (matchshuiping.equals("?????????")) {
                                intent.putExtra(NewCompetitionActivity.COMPETITION_LEVEL, 1);
                            } else {
                                intent.putExtra(NewCompetitionActivity.COMPETITION_LEVEL, 0);
                            }
                            if (isHunShuangClick) {
                                intent.putExtra(NewCompetitionActivity.HUNSHUANG_CLICK, "true");
                            } else {
                                intent.putExtra(NewCompetitionActivity.HUNSHUANG_CLICK, "false");
                            }
                            if (matchshuiping.equals("?????????")) {
                                if (isShuangDaClickNan) {
                                    intent.putExtra(NewCompetitionActivity.SHUANGDA_CANSELECT_MAN, "true");
                                } else {
                                    intent.putExtra(NewCompetitionActivity.SHUANGDA_CANSELECT_MAN, "false");
                                }
                            } else {
                                intent.putExtra(NewCompetitionActivity.SHUANGDA_CANSELECT_MAN, "true");
                            }
                            if (matchshuiping.equals("?????????")) {
                                if (isShuangDaClickWoman) {
                                    intent.putExtra(NewCompetitionActivity.SHUANGDA_CANSELECT_WOMAN, "true");
                                } else {
                                    intent.putExtra(NewCompetitionActivity.SHUANGDA_CANSELECT_WOMAN, "false");
                                }
                            } else {
                                intent.putExtra(NewCompetitionActivity.SHUANGDA_CANSELECT_WOMAN, "true");
                            }
                            intent.putExtra(NewCompetitionActivity.POSITION, position);
                            intent.putExtra(NewCompetitionActivity.GROUP_NAME, groupName);
                            intent.putExtra("data", (Serializable) data);
                            startActivityForResult(intent, 1);
                            break;
                        case R.id.iv_del:
                            String projectTitle = createCompetitionCategoriesAdapter.getData().get(position).getProjectTitle();
                            new MessageDialogBuilder(CreateEventsActivity.this)
                                    .setMessage("")
                                    .setTvTitle("????????????" + projectTitle + "?")
                                    .setTvCancel("??????")
                                    .setBtnCancleHint(false)
                                    .setTvSure("??????")
                                    .setSureListener(new View.OnClickListener() {
                                        @Override
                                        public void onClick(View v) {

                                            List<CreateEventsBean.ProjectListBean> data1 = createCompetitionCategoriesAdapter.getData();
                                            data1.remove(position);
                                            createCompetitionCategoriesAdapter.notifyDataSetChanged();
                                        }
                                    })
                                    .show();

                            break;
                    }
                }
            }
        });

        createCompetitionCategoriesAdapter.setItemMyOnClickInterface(new CreateCompetitionCategoriesAdapter.ItemOnClickInterface() {
            @Override
            public void onItemClick(int parentposition, CreateEventsBean.ProjectListBean item, int childposition) {
                List<CreateEventsBean.ProjectListBean.ProjectItemListBean> projectItemList = createCompetitionCategoriesAdapter.getData().get(parentposition).getProjectItemList();
                projectItemList.remove(childposition);
                createCompetitionCategoriesAdapter.getData().get(parentposition).setProjectItemList(projectItemList);
                createCompetitionCategoriesAdapter.notifyDataSetChanged();
            }

            @Override
            public void onDel(int parentposition, CreateEventsBean.ProjectListBean item, int childposition, String projectTitle) {


            }
        });
        mViewModel.myClubListLiveData.observe(this, new Observer<ResponseBean<List<SelectClubListBean>>>() {
            @Override
            public void onChanged(ResponseBean<List<SelectClubListBean>> listResponseBean) {
                if (listResponseBean.getData() != null && listResponseBean.getData().size() > 0) {
                    selectClubList = listResponseBean.getData();
                    dismissLoading();
                    options1Items.clear();
                    for (int i = 0; i < selectClubList.size(); i++) {
                        options1Items.add("" + selectClubList.get(i).getTeamTitle());
                    }
                    OptionsPickerView pvOptions = new OptionsPickerView.Builder(CreateEventsActivity.this, new OptionsPickerView.OnOptionsSelectListener() {
                        @Override
                        public void onOptionsSelect(int options1, int option2, int options3, View v) {
                            String s = options1Items.get(options1);
                            for (int i = 0; i < selectClubList.size(); i++) {
                                if (selectClubList.get(i).getTeamTitle().equals(s)) {
                                    strClubName = s;
                                    mBinding.tvApplyForClub.setText("" + s);
                                    mBinding.tvApplyForClub.setTextColor(Color.BLACK);
                                    strApplyClubId = "" + selectClubList.get(i).getId();
                                }
                            }

                        }
                    })
                            .setSubCalSize(20)//???????????????????????????
                            .setSubmitColor(Color.BLUE)//????????????????????????
                            .setCancelColor(Color.BLUE)//????????????????????????
                            .setTextColorCenter(Color.parseColor("#000000"))//????????????????????????
                            .setTextColorOut(Color.parseColor("#666666"))
                            .build();
                    pvOptions.setPicker(options1Items);
                    pvOptions.show();
                } else {
                    toast("?????????????????????");
                }
            }
        });
        mViewModel.getUploadTokenLiveData.observe(this, new Observer<ResponseBean>() {
            @Override
            public void onChanged(ResponseBean responseBean) {
                //                if (responseBean.isSuccess()) {
                if (responseBean.getErrorCode().equals("200")) {

                    UploadManager uploadManager = new UploadManager();
                    //                    // ??????????????????
                    uploadImage(picPath, responseBean.getData().toString(), uploadManager, key);
                } else {
                    toast("????????????");
                }
            }
        });

        mViewModel.createXlMatchInfoLiveData.observe(this, new Observer<ResponseBean>() {
            @Override
            public void onChanged(ResponseBean responseBean) {
                dismissLoading();
                //                if (responseBean.isSuccess()) {
                if (responseBean.getErrorCode().equals("200")) {


                    new MessageDialogBuilder(CreateEventsActivity.this)
                            .setMessage("?????????????????????")
                            .setTvTitle("????????????")
                            .setTvCancel("??????")
                            .setBtnCancleHint(true)
                            .setTvSure("??????")
                            .setSureListener(v1 ->
                                    finish()
                            )
                            .show();
                } else {
                    toast("" + responseBean.getMessage());
                }
            }
        });
        initClearView();
        mBinding.etTuantiBaomingMoney.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                if (baomingTuantiTwoFlag) {
                    return;
                }
                baomingTuantiTwoFlag = true;
                mBinding.etTuantiBaomingMoney.setText("" + s.toString());
                //??????????????????
                if (s.length() >= 1 && Integer.parseInt(s.toString()) != 0) {
                    try {
                        int money = Integer.parseInt(s.toString());
                        if (money == 0) {
                            toast("???????????????0");
                        } else {
                            mBinding.cbTuanti.setChecked(true);
                        }
                    } catch (Exception e) {
                        mBinding.cbTuanti.setChecked(false);
                        toast("????????????");
                    }

                } else if (s.length() == 0 || Integer.parseInt(s.toString()) == 0) {
                    mBinding.cbTuanti.setChecked(false);
                    mBinding.etTuantiBaomingMoney.setText("");
                    mBinding.etTuantiBaomingMoney.clearFocus();
                    CommonUtilis.hideSoftKeyboard(CreateEventsActivity.this);
                }
                baomingTuantiTwoFlag = false;
            }
        });
        mBinding.etPersonalBaomingMoney.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                if (baomingTuantiFlag) {
                    return;
                }
                baomingTuantiFlag = true;
                /*????????????????????????*/


                //??????????????????
                if (s.length() >= 1 && Integer.parseInt(s.toString()) != 0) {
                    mBinding.cbPersonalCheck.setChecked(true);
                    try {
                        int money = Integer.parseInt(s.toString());
                        if (money == 0) {
                            toast("???????????????0");
                        } else {
                            mBinding.cbPersonalCheck.setChecked(true);
                        }
                    } catch (Exception e) {
                        mBinding.cbPersonalCheck.setChecked(false);
                        toast("????????????");
                    }
                } else if (s.length() == 0 || Integer.parseInt(s.toString()) == 0) {
                    mBinding.etPersonalBaomingMoney.setText("");
                    mBinding.cbPersonalCheck.setChecked(false);
                    mBinding.etPersonalBaomingMoney.clearFocus();
                    CommonUtilis.hideSoftKeyboard(CreateEventsActivity.this);
                }
                baomingTuantiFlag = false;
            }
        });

    }

    private void initClearView() {
        mBinding.etPersonalBaomingMoney.setEnabled(false);
        mBinding.etTuantiBaomingMoney.setEnabled(false);
        mBinding.etEatMoney.setEnabled(false);
        mBinding.etDay.setEnabled(false);
        setEditListener(mBinding.etPersonalBaomingMoney);
        setEditListener(mBinding.etTuantiBaomingMoney);
        setEditListener(mBinding.etEatMoney);
        setEditListener(mBinding.etDay);


    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == 1) {

            if (resultCode == RESULT_OK) {
                String textData = data.getStringExtra("textData");
                CreateEventsBean.ProjectListBean.ProjectItemListBean bean = (CreateEventsBean.ProjectListBean.ProjectItemListBean) data.getSerializableExtra("bean");
                CreateEventsBean.ProjectListBean projectListBean = createCompetitionCategoriesAdapter.getData().get(Integer.parseInt(textData));
                List<CreateEventsBean.ProjectListBean.ProjectItemListBean> dataList = projectListBean.getProjectItemList();
                boolean isContains = false;
                for (int i = 0; i < dataList.size(); i++) {
                    if (dataList.get(i).getItemTitle().equals(bean.getItemTitle())) {
                        isContains = true;
                    }
                }
                if (isContains) {
                    toast("????????????????????????,?????????????????????");
                } else {
                    dataList.add(bean);
                }
                projectListBean.setProjectItemList(dataList);
                createCompetitionCategoriesAdapter.notifyDataSetChanged();
            }
        }else  if (data != null && requestCode == IMAGE_PICKER) {
            //????????????
            ArrayList<ImageItem> images = (ArrayList<ImageItem>) data.getSerializableExtra(ImagePicker.EXTRA_RESULT_ITEMS);
            String path = images.get(0).path;
            File file2 = CompressHelper.getDefault(this).compressToFile(new File(path));
            showLoading();
            uploadImg2QiNiu(file2.getPath());

        } else if (data != null && requestCode == REQUEST_CODE_SELECT) {
            ArrayList<ImageItem> images = (ArrayList<ImageItem>) data.getSerializableExtra(ImagePicker.EXTRA_RESULT_ITEMS);//
            String path = images.get(0).path;
            File file2 = CompressHelper.getDefault(this).compressToFile(new File(path));
            showLoading();
            uploadImg2QiNiu(file2.getPath());


        }
//        imagePickerUtil.handResult(requestCode, resultCode, data);
    }

    public void clearSelect() {
        mBinding.tvTitleChargingSet.setTextColor(Color.parseColor("#666666"));
        mBinding.tvBiddingTitleInformation.setTextColor(Color.parseColor("#666666"));
        mBinding.tvTitleCompetitionItem.setTextColor(Color.parseColor("#666666"));
        mBinding.tvTitleEventInformation.setTextColor(Color.parseColor("#666666"));
        mBinding.tvTitleUploadPictures.setTextColor(Color.parseColor("#666666"));

        mBinding.tvChargingSet.setVisibility(View.INVISIBLE);
        mBinding.viewChargingSet.setBackgroundResource(R.drawable.shape_competition_gray_circle);
        mBinding.tvBiddingOfInformation.setVisibility(View.INVISIBLE);
        mBinding.viewBiddingOfInformation.setBackgroundResource(R.drawable.shape_competition_gray_circle);
        mBinding.tvCompetitionItem.setVisibility(View.INVISIBLE);
        mBinding.viewCompetitionItem.setBackgroundResource(R.drawable.shape_competition_gray_circle);
        mBinding.tvEventInformation.setVisibility(View.INVISIBLE);
        mBinding.viewEventInformation.setBackgroundResource(R.drawable.shape_competition_gray_circle);
        mBinding.tvUploadPictures.setVisibility(View.INVISIBLE);
        mBinding.viewUploadPictures.setBackgroundResource(R.drawable.shape_competition_gray_circle);

    }


    //--------------------------------------????????????  ????????????
    //    private int allowPersonalType = 0;//0 ????????????  1 ???????????????????????????
    //    private int realType = 0;//0????????????????????? 1 ?????????????????????
    //    private int needMoneyType = 0;//0?????????APP????????? 1 ?????????APP??????
    //???????????????   ??????needMoneyType== 0 ?????????????????? ???????????????
    //??????????????????????????? needMoneyType==1 ?????? ?????????????????????????????? ?????????0  ??????  ???1  ??????
    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.rl_level_competition://???????????????????????????????????????????????????????????????????????????
                if (AntiShakeUtils.isInvalidClick(v))
                    return;
                OptionsPickerView pvOptionLevelCompetition =
                        new OptionsPickerView.Builder(CreateEventsActivity.this, new OptionsPickerView.OnOptionsSelectListener() {
                            @Override
                            public void onOptionsSelect(int options1, int option2, int options3, View v) {
                                //?????????????????????????????????????????????
                                String s = levelCompetitions.get(options1);
                                strGroupName = "";
                                //                        button4.setText(s);
                                if (TextUtils.isEmpty(matchshuiping)) {
                                    matchshuiping = s;
                                } else if (matchshuiping.equals(s)) {
                                    /*????????????*/
                                    toast("????????????");
                                } else {
                                    matchshuiping = s;
                                    /*???????????????  ??????data*/
                                    data.clear();
                                    createCompetitionCategoriesAdapter.notifyDataSetChanged();
                                }

                                mBinding.tvLevelText.setText("" + s);
                                mBinding.tvLevelText.setTextColor(Color.BLACK);
                            }
                        })
                                //                        .setSubmitText("??????")//??????????????????
                                //                        .setCancelText("??????")//??????????????????
                                //                        .setTitleText("????????????")//??????
                                .setSubCalSize(20)//???????????????????????????
                                //                        .setTitleSize(20)//??????????????????
                                //                        .setTitleColor(Color.BLACK)//??????????????????
                                .setSubmitColor(Color.BLUE)//????????????????????????
                                .setCancelColor(Color.BLUE)//????????????????????????
                                //                        .setTitleBgColor(0xFF333333)//?????????????????? Night mode
                                //                        .setBgColor(0xFF000000)//?????????????????? Night mode
                                //                        .setContentTextSize(18)//??????????????????
                                //                        .setTextColorCenter(Color.BLUE)//????????????????????????
                                .setTextColorCenter(Color.parseColor("#000000"))//????????????????????????
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
                pvOptionLevelCompetition.setPicker(levelCompetitions);
                pvOptionLevelCompetition.show();

                break;
            case R.id.cb_tuanti://???????????????
                if (AntiShakeUtils.isInvalidClick(v))
                    return;
                boolean checked = mBinding.cbTuanti.isChecked();
                int visibility = mBinding.viewMasking.getVisibility();
                if (visibility != 0) {
                    if (!checked) {
                        mBinding.etTuantiBaomingMoney.setText("");
                        mBinding.etTuantiBaomingMoney.clearFocus();
                        mBinding.cbTuanti.setChecked(false);
                    } else {
                        mBinding.cbTuanti.setChecked(true);
                    }

                } else {
                    mBinding.etTuantiBaomingMoney.setText("");
                    mBinding.etTuantiBaomingMoney.clearFocus();
                    mBinding.cbTuanti.setChecked(false);
                }

                break;
            case R.id.cb_personal_check://???????????????
                if (AntiShakeUtils.isInvalidClick(v))
                    return;
                boolean checkedPersonal = mBinding.cbPersonalCheck.isChecked();
                int visibility1 = mBinding.viewMasking.getVisibility();
                if (visibility1 != 0) {
                    if (!checkedPersonal) {
                        mBinding.etPersonalBaomingMoney.setText("");
                        mBinding.etPersonalBaomingMoney.clearFocus();
                        mBinding.cbPersonalCheck.setChecked(false);
                    } else {
                        mBinding.cbPersonalCheck.setChecked(true);
                    }
                } else {
                    mBinding.etPersonalBaomingMoney.setText("");
                    mBinding.etPersonalBaomingMoney.clearFocus();
                    mBinding.cbPersonalCheck.setChecked(false);
                }
                break;
            case R.id.iv_daisoufei_yes://???????????????  yes
                if (AntiShakeUtils.isInvalidClick(v))
                    return;
                collectionRoomType = 1;
                ImageLoader.loadImage(mBinding.ivDaisoufeiYes, R.drawable.icon_login_selected);
                ImageLoader.loadImage(mBinding.ivDaishoufeiNo, R.drawable.icon_login_unselected);
                mBinding.etEatMoney.setEnabled(true);
                mBinding.etDay.setEnabled(true);
                mBinding.viewShifufeiMasking.setVisibility(View.GONE);

                break;

            case R.id.iv_daishoufei_no://???????????????  no
                if (AntiShakeUtils.isInvalidClick(v))
                    return;
                collectionRoomType = 0;
                ImageLoader.loadImage(mBinding.ivDaisoufeiYes, R.drawable.icon_login_unselected);
                ImageLoader.loadImage(mBinding.ivDaishoufeiNo, R.drawable.icon_login_selected);
                mBinding.etEatMoney.setText("");
                mBinding.etDay.setText("");
                mBinding.etEatMoney.setEnabled(false);
                mBinding.etDay.setEnabled(false);
                mBinding.viewShifufeiMasking.setVisibility(View.VISIBLE);

                break;


            case R.id.iv_personal_sign_up_yes://????????????????????????
                if (AntiShakeUtils.isInvalidClick(v))
                    return;
                allowPersonalType = 1;
                ImageLoader.loadImage(mBinding.ivPersonalSignUpYes, R.drawable.icon_login_selected);
                ImageLoader.loadImage(mBinding.ivPersonalSignUpNo, R.drawable.icon_login_unselected);
                //                if (needMoneyType == 1) {
                //                    mBinding.etPersonalBaomingMoney.setEnabled(false);
                //                }
                mBinding.etPersonalBaomingMoney.setEnabled(true);
                break;
            case R.id.iv_personal_sign_up_no://???????????????????????????
                if (AntiShakeUtils.isInvalidClick(v))
                    return;
                allowPersonalType = 0;
                ImageLoader.loadImage(mBinding.ivPersonalSignUpYes, R.drawable.icon_login_unselected);
                ImageLoader.loadImage(mBinding.ivPersonalSignUpNo, R.drawable.icon_login_selected);
                mBinding.etPersonalBaomingMoney.setEnabled(true);

                if (needMoneyType == 1) {
                    mBinding.etPersonalBaomingMoney.setText("");
                    mBinding.etPersonalBaomingMoney.clearFocus();
                    //mBinding.etPersonalBaomingMoney.setEnabled(false);
                }
                break;
            case R.id.iv_real_type_yes://??????????????????
                if (AntiShakeUtils.isInvalidClick(v))
                    return;
                realType = 1;
                ImageLoader.loadImage(mBinding.ivRealTypeYes, R.drawable.icon_login_selected);
                ImageLoader.loadImage(mBinding.ivRealTypeNo, R.drawable.icon_login_unselected);
                break;
            case R.id.iv_real_type_no://????????????????????????
                if (AntiShakeUtils.isInvalidClick(v))
                    return;
                realType = 0;
                ImageLoader.loadImage(mBinding.ivRealTypeYes, R.drawable.icon_login_unselected);
                ImageLoader.loadImage(mBinding.ivRealTypeNo, R.drawable.icon_login_selected);
                break;

            case R.id.iv_baoming_need_yes://?????????APP???????????????  yes
                if (AntiShakeUtils.isInvalidClick(v))
                    return;
                needMoneyType = 1;

                ImageLoader.loadImage(mBinding.ivBaomingNeedNo, R.drawable.icon_login_unselected);
                ImageLoader.loadImage(mBinding.ivBaomingNeedYes, R.drawable.icon_login_selected);
                mBinding.viewMasking.setVisibility(View.GONE);
                mBinding.etTuantiBaomingMoney.setEnabled(true);
                mBinding.etPersonalBaomingMoney.setEnabled(true);
                break;
            case R.id.iv_baoming_need_no:////?????????APP???????????????  no
                if (AntiShakeUtils.isInvalidClick(v))
                    return;
                needMoneyType = 0;
                ImageLoader.loadImage(mBinding.ivBaomingNeedNo, R.drawable.icon_login_selected);
                ImageLoader.loadImage(mBinding.ivBaomingNeedYes, R.drawable.icon_login_unselected);
                mBinding.viewMasking.setVisibility(View.VISIBLE);
                mBinding.etTuantiBaomingMoney.setEnabled(false);
                mBinding.etPersonalBaomingMoney.setEnabled(false);
                mBinding.etTuantiBaomingMoney.setText("");
                mBinding.etPersonalBaomingMoney.setText("");
                mBinding.cbPersonalCheck.setChecked(false);
                mBinding.cbTuanti.setChecked(false);
                break;
            case R.id.iv_saishi_image://??????????????????
                if (AntiShakeUtils.isInvalidClick(v))
                    return;
                strImageShowType = 1;

                PhotoSelDialog dialog2 = new PhotoSelDialog(this);
                dialog2.setClickListener(this);
                dialog2.show();
                break;
            case R.id.iv_logo_tuzhang://????????????
                if (AntiShakeUtils.isInvalidClick(v))
                    return;
                if (matchImgType == 1) {
                    strImageShowType = 2;
                    PhotoSelDialog dialog3 = new PhotoSelDialog(this);
                    dialog3.setClickListener(this);
                    dialog3.show();
                }


                break;
            /*???????????????*/
            case R.id.rl_apply_for_club://???????????????
                if (AntiShakeUtils.isInvalidClick(v))
                    return;
                mViewModel.myClubList();


                break;
            case R.id.rl_saishi_level://????????????
                if (AntiShakeUtils.isInvalidClick(v))
                    return;
                OptionsPickerView pvOptionCompetitionLevel =
                        new OptionsPickerView.Builder(CreateEventsActivity.this, new OptionsPickerView.OnOptionsSelectListener() {
                            @Override
                            public void onOptionsSelect(int options1, int option2, int options3, View v) {
                                //?????????????????????????????????????????????
                                String s = competitionLevels.get(options1);
                                matchdengji = s;
                                mBinding.tvSaishiLevel.setText("" + s);
                                mBinding.tvSaishiLevel.setTextColor(Color.BLACK);

                            }
                        })
                                .setSubCalSize(20)//???????????????????????????
                                .setSubmitColor(Color.BLUE)//????????????????????????
                                .setCancelColor(Color.BLUE)//????????????????????????
                                .setTextColorCenter(Color.parseColor("#000000"))//????????????????????????
                                .setTextColorOut(Color.parseColor("#666666"))
                                .build();
                pvOptionCompetitionLevel.setPicker(competitionLevels);
                pvOptionCompetitionLevel.show();

                break;

            case R.id.tv_commit_bidding_information://   ??????????????????????????????  ???????????? ??????????????? ?????????
                if (AntiShakeUtils.isInvalidClick(v))
                    return;
                String strEtCompetitionName = mBinding.etSaishiName.getText().toString().trim();
                String strEtZhubanfang = mBinding.etZhubanfang.getText().toString().trim();
                String strEtChengbanfang = mBinding.etChengbanfa.getText().toString().trim();
                if (TextUtils.isEmpty(strEtCompetitionName)) {
                    toast("?????????????????????");
                    return;
                }
                if (TextUtils.isEmpty(strClubName)) {
                    toast("??????????????????");
                    return;
                }
                if (TextUtils.isEmpty(strEtZhubanfang)) {
                    toast("??????????????????");
                    return;
                }
                if (TextUtils.isEmpty(strEtChengbanfang)) {
                    toast("??????????????????");
                    return;
                }
                if (TextUtils.isEmpty(matchdengji)) {
                    toast("?????????????????????");
                    return;
                }

                createEventsBean.setMatchTitle("" + strEtCompetitionName);
                createEventsBean.setApplyClubId("" + strApplyClubId);
                createEventsBean.setSponsor("" + strEtZhubanfang);
                createEventsBean.setOrganizer("" + strEtChengbanfang);
                createEventsBean.setMatchGrade("" + matchdengji);

                mBinding.llBiddingForInformation.setVisibility(View.GONE);
                mBinding.llEventInformation.setVisibility(View.VISIBLE);
                mBinding.llUploadPictures.setVisibility(View.GONE);
                mBinding.llChargingSet.setVisibility(View.GONE);
                mBinding.llCompetitionItem.setVisibility(View.GONE);
                clearSelect();
                mBinding.tvTitleEventInformation.setTextColor(Color.parseColor("#4795ED"));
                mBinding.tvEventInformation.setVisibility(View.VISIBLE);
                mBinding.viewEventInformation.setBackgroundResource(R.drawable.shape_competition_blue_circle);

                break;

            case R.id.rl_select_city:
                if (AntiShakeUtils.isInvalidClick(v))
                    return;
                AreaSelectDialog mAreaSelectDialogss = new AreaSelectDialog(CreateEventsActivity.this, 0);
                mAreaSelectDialogss.setListener(CreateEventsActivity.this);
                mAreaSelectDialogss.show();
                break;
            case R.id.tv_event_information_up://???????????? ???????????????
                if (AntiShakeUtils.isInvalidClick(v))
                    return;
                mBinding.llBiddingForInformation.setVisibility(View.VISIBLE);
                mBinding.llEventInformation.setVisibility(View.GONE);
                mBinding.llUploadPictures.setVisibility(View.GONE);
                mBinding.llChargingSet.setVisibility(View.GONE);
                mBinding.llCompetitionItem.setVisibility(View.GONE);
                clearSelect();
                mBinding.tvBiddingTitleInformation.setTextColor(Color.parseColor("#4795ED"));
                mBinding.tvBiddingOfInformation.setVisibility(View.VISIBLE);
                mBinding.viewBiddingOfInformation.setBackgroundResource(R.drawable.shape_competition_blue_circle);

                break;
            case R.id.rl_baoming_time://??????????????????
                if (AntiShakeUtils.isInvalidClick(v))
                    return;
                TimePickerView pvTime = new TimePickerView.Builder(CreateEventsActivity.this, new TimePickerView.OnTimeSelectListener() {
                    @Override
                    public void onTimeSelect(Date dateMax, View v) {
                        dateEndTime = dateMax;
                        String time = getTimeMinute(dateMax);
                        strEndTime = time;
                        mBinding.tvBaomingTime.setText("" + time);
                        mBinding.tvBaomingTime.setTextColor(Color.BLACK);

                        //                        mBinding.tvDeadlineForRegistrationTime.setText("" + time);
                    }
                })
                        .setType(TimePickerView.Type.YEAR_MONTH_DAY_HOUR_MIN)//??????????????????
                        .setCancelText("??????")//??????????????????
                        .setSubmitText("??????")//??????????????????
                        .setContentSize(20)//??????????????????
                        .setTitleSize(20)//??????????????????
                        //                        .setTitleText("???????????????")//????????????
                        .setOutSideCancelable(true)//???????????????????????????????????????????????????????????????
                        .isCyclic(true)//??????????????????
                        .setTextColorCenter(Color.parseColor("#000000"))//????????????????????????
                        .setTextColorOut(Color.parseColor("#666666"))
                        .setTitleColor(Color.BLACK)//??????????????????
                        .setSubmitColor(Color.BLUE)//????????????????????????
                        .setCancelColor(Color.BLUE)//????????????????????????
                        .setLoop(false)
                        .isCenterLabel(false) //?????????????????????????????????label?????????false?????????item???????????????label???
                        //                        .isDialog(true)//??????????????????????????????
                        .build();
                pvTime.setDate(Calendar.getInstance());//??????????????????????????????????????????????????????????????????????????????????????????????????????????????????????????????????????????????????????????????????????????????????????????????????????????????????????????????????????????????????????????????????
                pvTime.show();

                break;
            case R.id.ll_start_end_time://?????????????????????
                if (AntiShakeUtils.isInvalidClick(v))
                    return;
                TimeGameSelectView pvTimeSelectEnd = new TimeGameSelectView.Builder(CreateEventsActivity.this, new TimeGameSelectView.OnTimeSelectListener() {
                    @Override
                    public void onTimeSelect(Date dateMax, Date dateMin, View v) {
                        String time = getTime(dateMax);
                        if (dateEndTime == null) {
                            toast("???????????????????????????");
                            return;
                        }
                        if (dateEndTime.after(dateMin)) {
                            toast("????????????????????????????????????????????????");
                            return;
                        } else {
                            Log.e(TAG, "onTimeSelect: dateMax===" + dateMax);
                            Log.e(TAG, "onTimeSelect: dateMin===" + dateMin);
                            Log.e(TAG, "onTimeSelect: first===" + (dateMax == dateMin));
                            Log.e(TAG, "onTimeSelect: second===" + dateMax.equals(dateMin));
                            if (MyDateUtils.sameDate(dateMax, dateMin)) {
                                Log.e(TAG, "onTimeSelect: same");
                                strStartAndEndKaishi = getTime(dateMin);
                                strStartAndEndJieshu = getTime(dateMax);
                                mBinding.tvStartTime.setText("" + strStartAndEndKaishi);
                                mBinding.tvEndTime.setText("" + strStartAndEndJieshu);
                                mBinding.tvStartTime.setTextColor(Color.BLACK);
                                mBinding.tvEndTime.setTextColor(Color.BLACK);
                                strStartAndEnd = "onTimeSelect: dateMax===" + time + "====" + getTime(dateMin);
                            } else {
                                boolean after = dateMax.after(dateMin);
                                if (after) {
                                    strStartAndEndKaishi = getTime(dateMin);
                                    strStartAndEndJieshu = getTime(dateMax);
                                    mBinding.tvStartTime.setText("" + strStartAndEndKaishi);
                                    mBinding.tvEndTime.setText("" + strStartAndEndJieshu);
                                    mBinding.tvStartTime.setTextColor(Color.BLACK);
                                    mBinding.tvEndTime.setTextColor(Color.BLACK);
                                    strStartAndEnd = "onTimeSelect: dateMax===" + time + "====" + getTime(dateMin);
                                    Log.e(TAG, "onTimeSelect: ====strStartAndEndKaishi===" + strStartAndEndKaishi);
                                    Log.e(TAG, "onTimeSelect: ====strStartAndEndJieshu===" + strStartAndEndJieshu);
                                } else {
                                    toast("????????????????????????????????????");
                                }
                            }

                        }
                    }
                })
                        .setType(TimePickerView.Type.YEAR_MONTH_DAY)//??????????????????
                        .setCancelText("??????")//??????????????????
                        .setSubmitText("??????")//??????????????????
                        .setContentSize(20)//??????????????????
                        .setTitleSize(20)//??????????????????
                        //                        .setTitleText("???????????????")//????????????
                        .setOutSideCancelable(true)//???????????????????????????????????????????????????????????????
                        .isCyclic(false)//??????????????????
                        .setTextColorCenter(Color.parseColor("#333333"))//????????????????????????
                        .setTextColorOut(Color.parseColor("#333333"))
                        .setTitleColor(Color.BLACK)//??????????????????
                        .setSubmitColor(Color.BLUE)//????????????????????????
                        .setCancelColor(Color.BLUE)//????????????????????????
                        .isCenterLabel(false) //?????????????????????????????????label?????????false?????????item???????????????label???
                        .build();
                pvTimeSelectEnd.setDate(Calendar.getInstance());//??????????????????????????????????????????????????????????????????????????????????????????????????????????????????????????????????????????????????????????????????????????????????????????????????????????????????????????????????????????????????????????????????
                pvTimeSelectEnd.show();
                break;
            case R.id.tv_event_information_next://???????????? ???????????????
                if (AntiShakeUtils.isInvalidClick(v))
                    return;
                /*??????????????????*/
                if (TextUtils.isEmpty(strLocalCity)) {
                    toast("?????????????????????");
                    return;
                }
                /*??????????????????*/
                String etDetailAddress = mBinding.etDetailAddress.getText().toString().trim();
                String etConnectName = mBinding.etConnectName.getText().toString().trim();
                String etTelPhone = mBinding.etTelPhone.getText().toString().trim();
                if (TextUtils.isEmpty(etDetailAddress)) {
                    toast("?????????????????????");
                    return;
                }
                /*???????????????*/
                if (TextUtils.isEmpty(etConnectName)) {
                    toast("????????????????????????");
                    return;
                }

                /*??????????????????*/
                if (TextUtils.isEmpty(etTelPhone)) {
                    toast("?????????????????????");
                    return;
                }

                if (!CommonUtilis.isMobileNO(etTelPhone)) {
                    toast("???????????????????????????");
                    return;
                }
                /*????????????????????????*/
                if (TextUtils.isEmpty(strEndTime)) {
                    toast("???????????????????????????");
                    return;
                }
                /*??????????????????*/
                if (TextUtils.isEmpty(strStartAndEnd)) {
                    toast("?????????????????????");
                    return;
                }


                createEventsBean.setHoldCity("" + strLocalCity);
                createEventsBean.setVenue("" + etDetailAddress);
                createEventsBean.setLinkPerson("" + etConnectName);
                createEventsBean.setLinkNum("" + etTelPhone);
                createEventsBean.setRegistrationDeadline("" + strEndTime);
                createEventsBean.setBeginTime("" + strStartAndEndKaishi);
                createEventsBean.setEndTime("" + strStartAndEndJieshu);


                mBinding.llBiddingForInformation.setVisibility(View.GONE);
                mBinding.llEventInformation.setVisibility(View.GONE);
                mBinding.llUploadPictures.setVisibility(View.VISIBLE);
                mBinding.llChargingSet.setVisibility(View.GONE);
                mBinding.llCompetitionItem.setVisibility(View.GONE);
                clearSelect();
                mBinding.tvTitleUploadPictures.setTextColor(Color.parseColor("#4795ED"));
                mBinding.tvUploadPictures.setVisibility(View.VISIBLE);
                mBinding.viewUploadPictures.setBackgroundResource(R.drawable.shape_competition_blue_circle);

                break;

            case R.id.tv_upload_picture_up://????????????????????????
                if (AntiShakeUtils.isInvalidClick(v))
                    return;
                mBinding.llBiddingForInformation.setVisibility(View.GONE);
                mBinding.llEventInformation.setVisibility(View.VISIBLE);
                mBinding.llUploadPictures.setVisibility(View.GONE);
                mBinding.llChargingSet.setVisibility(View.GONE);
                mBinding.llCompetitionItem.setVisibility(View.GONE);
                clearSelect();


                mBinding.tvTitleEventInformation.setTextColor(Color.parseColor("#4795ED"));
                mBinding.tvEventInformation.setVisibility(View.VISIBLE);
                mBinding.viewEventInformation.setBackgroundResource(R.drawable.shape_competition_blue_circle);
                break;
            case R.id.iv_upload_zhang_yes://???????????????
                if (AntiShakeUtils.isInvalidClick(v))
                    return;
                strStampImgUrl = "";
                ImageLoader.loadImage(mBinding.ivUploadZhangYes, R.drawable.icon_login_selected);
                ImageLoader.loadImage(mBinding.ivUploadZhangNo, R.drawable.icon_login_unselected);
                ImageLoader.loadImage(mBinding.ivLogoTuzhang, R.drawable.img_upload_cachet);
                matchImgType = 1;
                break;
            case R.id.iv_upload_zhang_no://??????????????????
                if (AntiShakeUtils.isInvalidClick(v))
                    return;
                matchImgType = 0;
                ImageLoader.loadImage(mBinding.ivLogoTuzhang, R.drawable.img_not_upload);
                ImageLoader.loadImage(mBinding.ivUploadZhangYes, R.drawable.icon_login_unselected);
                ImageLoader.loadImage(mBinding.ivUploadZhangNo, R.drawable.icon_login_selected);
                break;
            case R.id.tv_upload_picture_next://????????????????????????
                if (AntiShakeUtils.isInvalidClick(v))
                    return;

                if (TextUtils.isEmpty(strMatchImgUrl)) {
                    toast("???????????????????????????");
                    return;
                }
                if (matchImgType == 1) {
                    if (TextUtils.isEmpty(strStampImgUrl)) {
                        toast("????????????????????????");
                        return;
                    }
                }
                /*??????????????????????????????*/
                if (matchImgType == 1) {
                    createEventsBean.setStampImgUrl("" + strStampImgUrl);
                }

                createEventsBean.setMatchImgUrl("" + strMatchImgUrl);
                mBinding.llBiddingForInformation.setVisibility(View.GONE);
                mBinding.llEventInformation.setVisibility(View.GONE);
                mBinding.llUploadPictures.setVisibility(View.GONE);
                mBinding.llChargingSet.setVisibility(View.VISIBLE);
                mBinding.llCompetitionItem.setVisibility(View.GONE);
                clearSelect();

                mBinding.tvTitleChargingSet.setTextColor(Color.parseColor("#4795ED"));
                mBinding.tvChargingSet.setVisibility(View.VISIBLE);
                mBinding.viewChargingSet.setBackgroundResource(R.drawable.shape_competition_blue_circle);

                break;
            case R.id.tv_charging_set_up://???????????? ???????????????
                if (AntiShakeUtils.isInvalidClick(v))
                    return;
                mBinding.llBiddingForInformation.setVisibility(View.GONE);
                mBinding.llEventInformation.setVisibility(View.GONE);
                mBinding.llUploadPictures.setVisibility(View.VISIBLE);
                mBinding.llChargingSet.setVisibility(View.GONE);
                mBinding.llCompetitionItem.setVisibility(View.GONE);
                clearSelect();

                mBinding.tvTitleUploadPictures.setTextColor(Color.parseColor("#4795ED"));
                mBinding.tvUploadPictures.setVisibility(View.VISIBLE);
                mBinding.viewUploadPictures.setBackgroundResource(R.drawable.shape_competition_blue_circle);


                break;
            case R.id.tv_charging_set_next://????????????  ???????????????
                if (AntiShakeUtils.isInvalidClick(v))
                    return;
                /*???????????? ???????????????  ????????????????????????????????????   ????????????????????????????????????*/
                //--------------------------------------????????????  ????????????
                //    private int allowPersonalType = 0;//0 ????????????  1 ???????????????????????????
                //    private int realType = 0;//0????????????????????? 1 ?????????????????????
                //    private int needMoneyType = 0;//0?????????APP????????? 1 ?????????APP??????
                //???????????????   ??????needMoneyType== 0 ?????????????????? ???????????????
                //??????????????????????????? needMoneyType==1 ?????? ?????????????????????????????? ?????????0  ??????  ???1  ??????
                //
                //                ?????????????????? 1  ???????????? APP????????? ???1  ??????     ???????????????????????? ?????????????????? ??????????????????
                //                ?????? ????????????????????? ???????????????????????????????????????

                String etTuantiBaomingMoney = mBinding.etTuantiBaomingMoney.getText().toString();
                String etPersonalMoney = mBinding.etPersonalBaomingMoney.getText().toString();
                String etSimpleMoney = mBinding.etEatMoney.getText().toString();
                String etDay = mBinding.etDay.getText().toString();

                createEventsBean.setAuthentication("" + realType);//????????????
                createEventsBean.setOwnRegistration("" + allowPersonalType);//??????????????????
                if (needMoneyType == 1) {
                    /*APP?????????*/
                    createEventsBean.setReplaceCharge("1");//APP???????????????
                    /*??????????????????????????????  allowPersonalType ???1 ????????????*/
                    if (allowPersonalType == 1) {
                        /*?????????????????????*/
                        if (TextUtils.isEmpty(etPersonalMoney)) {
                            toast("????????????????????????");
                            return;
                        }
                        createEventsBean.setOwnFree("" + etPersonalMoney);//????????????
                    } else {
                        /*????????????????????????*/
                        if (!TextUtils.isEmpty(etPersonalMoney) || !TextUtils.isEmpty(etTuantiBaomingMoney)) {
                            if (!TextUtils.isEmpty(etPersonalMoney)) {
                                createEventsBean.setOwnFree("" + etPersonalMoney);//????????????
                            }
                            if (!TextUtils.isEmpty(etTuantiBaomingMoney)) {
                                createEventsBean.setRegistrationCount("" + etTuantiBaomingMoney);//????????????
                            }

                        } else {
                            toast("??????????????????");
                            return;
                        }

                    }
                } else {

                    /*APP????????????*/
                    createEventsBean.setReplaceCharge("0");
                }

                createEventsBean.setReplaceChargeEat("" + collectionRoomType);

                if (collectionRoomType == 1) {
                    if (TextUtils.isEmpty(etSimpleMoney)) {
                        toast("????????????????????????");
                        return;
                    }
                    if (TextUtils.isEmpty(etDay)) {
                        toast("???????????????");
                        return;
                    }
                    double money = Double.parseDouble(etSimpleMoney) * Double.parseDouble(etDay);
                    createEventsBean.setEatFree("" + CommonUtilis.doubleMoney(money));//?????????
                }
                mBinding.llBiddingForInformation.setVisibility(View.GONE);
                mBinding.llEventInformation.setVisibility(View.GONE);
                mBinding.llUploadPictures.setVisibility(View.GONE);
                mBinding.llChargingSet.setVisibility(View.GONE);
                mBinding.llCompetitionItem.setVisibility(View.VISIBLE);
                clearSelect();
                mBinding.tvTitleCompetitionItem.setTextColor(Color.parseColor("#4795ED"));
                mBinding.tvCompetitionItem.setVisibility(View.VISIBLE);
                mBinding.viewCompetitionItem.setBackgroundResource(R.drawable.shape_competition_blue_circle);
                break;

            case R.id.tv_competition_item_up://?????????
                if (AntiShakeUtils.isInvalidClick(v))
                    return;
                mBinding.llBiddingForInformation.setVisibility(View.GONE);
                mBinding.llEventInformation.setVisibility(View.GONE);
                mBinding.llUploadPictures.setVisibility(View.GONE);
                mBinding.llChargingSet.setVisibility(View.VISIBLE);
                mBinding.llCompetitionItem.setVisibility(View.GONE);
                clearSelect();
                mBinding.tvTitleChargingSet.setTextColor(Color.parseColor("#4795ED"));
                mBinding.tvChargingSet.setVisibility(View.VISIBLE);
                mBinding.viewChargingSet.setBackgroundResource(R.drawable.shape_competition_blue_circle);
                break;
            case R.id.tv_competition_item_next://????????????
                if (AntiShakeUtils.isInvalidClick(v))
                    return;
                if (TextUtils.isEmpty(matchshuiping)) {
                    toast("?????????????????????");
                    return;
                }
                createEventsBean.setMatchLevel("" + matchshuiping);
                List<CreateEventsBean.ProjectListBean> dataCategory = createCompetitionCategoriesAdapter.getData();
                if (dataCategory == null || dataCategory.size() <= 0) {
                    toast("????????????????????????");
                    return;
                } else {
                    boolean isSuccess = false;
                    String titleName = "";
                    for (int i = 0; i < dataCategory.size(); i++) {
                        List<CreateEventsBean.ProjectListBean.ProjectItemListBean> projectItemList = dataCategory.get(i).getProjectItemList();
                        if (projectItemList.size() == 0) {
                            isSuccess = true;
                            titleName = dataCategory.get(i).getProjectTitle();
                        }
                    }
                    if (isSuccess) {
                        toast("" + titleName + "??????????????????");
                        return;
                    }

                    new MessageDialogBuilder(CreateEventsActivity.this)
                            .setMessage("?????????????????????????????????????????????")
                            .setTvTitle("?????????????????????")
                            .setTvCancel("??????")
                            .setBtnCancleHint(false)
                            .setTvSure("??????")
                            .setSureListener(new View.OnClickListener() {
                                @Override
                                public void onClick(View v) {

                                    Calendar c = Calendar.getInstance();//
                                    int mYear = c.get(Calendar.YEAR); // ??????????????????
                                    int mMonth = c.get(Calendar.MONTH) + 1;// ??????????????????
                                    int mDay = c.get(Calendar.DAY_OF_MONTH);// ???????????????
                                    String maxYear = mYear + "-" + mMonth + "-" + mDay + " " + "00:00:00";
                                    showLoading();
                                    String minYear = 1900 + "-" + mMonth + "-" + mDay + " " + "00:00:00";
                                    //2021-12-16 00:00:00
                                    for (int i = 0; i < dataCategory.size(); i++) {
                                        CreateEventsBean.ProjectListBean projectListBean = dataCategory.get(i);
                                        List<CreateEventsBean.ProjectListBean.ProjectItemListBean> projectItemList = projectListBean.getProjectItemList();
                                        for (int n = 0; n < projectItemList.size(); n++) {
                                            CreateEventsBean.ProjectListBean.ProjectItemListBean projectItemListBean = projectItemList.get(n);
                                            if (projectItemListBean.getMaxAge().equals("????????????")) {
                                                projectItemListBean.setMaxAge("" + maxYear);
                                            }
                                            if (projectItemListBean.getMinAge().equals("????????????")) {
                                                projectItemListBean.setMinAge("" + minYear);
                                            }
                                            if (projectItemListBean.getManMaxAge().equals("????????????")) {
                                                projectItemListBean.setManMaxAge("" + maxYear);
                                            }
                                            if (projectItemListBean.getManMinAge().equals("????????????")) {
                                                projectItemListBean.setManMinAge("" + minYear);
                                            }
                                            if (projectItemListBean.getWomanMaxAge().equals("????????????")) {
                                                projectItemListBean.setWomanMaxAge("" + maxYear);
                                            }
                                            if (projectItemListBean.getWomanMinAge().equals("????????????")) {
                                                projectItemListBean.setWomanMinAge("" + minYear);

                                            }
                                        }
                                    }
                                    //                                    Log.e(TAG, "onClick======: "+new Gson().toJson(dataCategory));
                                    createEventsBean.setProjectList(dataCategory);
                                    mViewModel.createXlMatchInfo(createEventsBean);
                                }
                            })
                            .show();
                }


                break;

            case R.id.ll_add_group:
                if (AntiShakeUtils.isInvalidClick(v))
                    return;
                if (TextUtils.isEmpty(matchshuiping)) {
                    toast("????????????????????????");
                    return;
                }
                addGroupName();

                break;

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
//                            ImagePicker imagePicker = ImagePicker.getInstance();
//                            imagePicker.setMultiMode(false);
//                            imagePicker.setShowCamera(false);
//                            imagePicker.setCrop(false);
//                            imagePicker.setFocusHeight(SystemHelper.getScreenInfo(CreateEventsActivity.this).heightPixels);
//                            imagePicker.setFocusWidth(SystemHelper.getScreenInfo(CreateEventsActivity.this).widthPixels);
//                            imagePickerUtil.takePhoto(CreateEventsActivity.this, REQUEST_CODE_TAKE_PHOTO, CreateEventsActivity.this);
                            Intent intent = new Intent(CreateEventsActivity.this, ImageGridActivity.class);
                            intent.putExtra(ImageGridActivity.EXTRAS_TAKE_PICKERS, true); // ???????????????????????????
                            startActivityForResult(intent, REQUEST_CODE_SELECT);
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
//        imagePicker.setCrop(true);
//        imagePicker.setShowCamera(true);
//        //        if (needPicNum < 0) {
//        //            needPicNum = 1;
//        //        }
//        imagePicker.setSelectLimit(1);
//        imagePicker.setCrop(false);
//        imagePicker.setMultiMode(true);
//        imagePickerUtil.selectPic(CreateEventsActivity.this, REQUEST_CODE_SELECT_PHOTO, CreateEventsActivity.this);
        Intent intent = new Intent(this, ImageGridActivity.class);
        startActivityForResult(intent, IMAGE_PICKER);

    }
    private static final int IMAGE_PICKER = 10001;
    private static final int REQUEST_CODE_SELECT = 10002;
    @Override
    public void callBack(List<ImageItem> list) {
        List<String> images = new ArrayList<>();
        showLoading();
        File file2 = CompressHelper.getDefault(this).compressToFile(new File(list.get(0).path));

        //        File file2 = BitmapUtils.compressImageToSD(list.get(0).path, 30);
        if (strImageShowType == 1) {//????????????
            uploadImg2QiNiu(file2.getPath());
        } else if (strImageShowType == 2) {
            uploadImg2QiNiu(file2.getPath());
        }
        for (ImageItem item : list
        ) {
//            int degree = readPictureDegree(item.path);
//            Bitmap bitmap = rotateBitmap(BitmapFactory.decodeFile(item.path), degree);
//            saveBitmap(item.path, bitmap);
            images.add(item.path);
        }
    }


    private void uploadImage(String picPath, String token, UploadManager uploadManager, String key) {
        //xlttsports
                uploadManager.put(picPath, "" + key, token, new UpCompletionHandler() {
//        uploadManager.put(picPath, key, Auth.create(Constants.AccessKey, Constants.SecretKey).uploadToken("xlttsports"), new UpCompletionHandler() {

            @Override
            public void complete(String key, ResponseInfo info, JSONObject res) {
                // info.error??????????????????????????????????????????
                // ??????????????????key??????????????????????????????
                dismissLoading();
                //                oX5vp7Hub8m1zO7QdGYIxZQQBeK9BmBsLxPgURNA:dVgNxp_0wpFkdXJsSkrtECd9alY=:eyJkZWFkbGluZSI6MTYwNzA3NDI1OCwicmV0dXJuQm9keVZvIjp7ImgiOiIkKGltYWdlSW5mby5o
                //                        ZWlnaHQpIiwiaGFzaCI6IiQoZXRhZykiLCJuYW1lIjoiJChmbmFtZSkiLCJzaXplIjoiJChmc2l6
                //                ZSkiLCJ3IjoiJChpbWFnZUluZm8ud2lkdGgpIn0sInNjb3BlIjoieGx0dHNwb3J0czphbmRyb2lk
                //                        X2ltZ18yMDIwMTIwNDE2MzA1ODE3OSJ9

                //                oX5vp7Hub8m1zO7QdGYIxZQQBeK9BmBsLxPgURNA:RE7olg9Gft4O6ce10-3fgepqOyA=:eyJzY29wZSI6InhsdHRzcG9ydHMiLCJkZWFkbGluZSI6MTYwNzA3NDI4NX0=
                if (info.isOK()) {
                    String headpicPath = "http://images.xlttsports.com/" + key;
                    if (strImageShowType == 1) {
                        ImageLoader.loadImage(mBinding.ivSaishiImage, picPath);
                        strMatchImgUrl = "" + headpicPath;
                    } else if (strImageShowType == 2) {
                        ImageLoader.loadImage(mBinding.ivLogoTuzhang, picPath);
                        strStampImgUrl = "" + headpicPath;
                    }
                    Log.e(TAG, "complete: ??????????????????==="+headpicPath );
                } else {
                }
                //?????????????????????
                //uploadpictoQianMo(headpicPath, picPath);

            }
        }, null);

    }

    String key = "";
    String picPath = "";

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
        mViewModel.getUploadToken(key);

    }


    @Override
    public boolean dispatchTouchEvent(MotionEvent ev) {
        if (ev.getAction() == MotionEvent.ACTION_DOWN) {
            View v = getCurrentFocus();
            //???isShouldHideInput(v, ev)???true???????????????????????????????????????????????????????????????????????????????????????????????????????????????????????????
            if (isShouldHideInput(v, ev)) {

                InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
                if (imm != null) {
                    imm.hideSoftInputFromWindow(v.getWindowToken(), 0);
                    //??????Editext??????????????????????????????
                    v.clearFocus();
                }
            }
            return super.dispatchTouchEvent(ev);
        }
        // ????????????????????????????????????????????????TouchEvent???
        if (getWindow().superDispatchTouchEvent(ev)) {
            return true;
        }
        return onTouchEvent(ev);
    }

    public boolean isShouldHideInput(View v, MotionEvent event) {
        if (v != null && (v instanceof EditText)) {
            int[] leftTop = {0, 0};
            //????????????????????????location??????
            v.getLocationInWindow(leftTop);
            int left = leftTop[0];
            int top = leftTop[1];
            int bottom = top + v.getHeight();
            int right = left + v.getWidth();
            if (event.getX() > left && event.getX() < right
                    && event.getY() > top && event.getY() < bottom) {
                // ??????????????????????????????????????????EditText?????????
                return false;
            } else {
                return true;
            }
        }
        return false;
    }

    public void setEditListener(EditText etclick) {
        etclick.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
                if (actionId == EditorInfo.IME_ACTION_DONE) {
                    etclick.clearFocus();
                    CommonUtilis.hideSoftKeyboard(CreateEventsActivity.this);
                }
                return false;
            }
        });
    }

    private void addGroupName() {
        customDialogAddGroup = new CustomDialog(CreateEventsActivity.this);
        customDialogAddGroup.setCustomView(R.layout.pop_group_name);
        customDialogAddGroup.show();
        View customView = customDialogAddGroup.getCustomView();
        showAskConfirmView(customView, customDialogAddGroup);
    }

    private void showAskConfirmView(View customView, CustomDialog customDialog) {
        EditText etGroupName = customView.findViewById(R.id.et_group_name);
        TextView tvSubmit = customView.findViewById(R.id.btnSubmit);
        TextView tvCancel = customView.findViewById(R.id.btnCancel);
        tvCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (AntiShakeUtils.isInvalidClick(v))
                    return;
                if (customDialog != null) {
                    customDialog.dismiss();
                }
            }
        });
        tvSubmit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (AntiShakeUtils.isInvalidClick(v))
                    return;
                strGroupName = etGroupName.getText().toString();
                if (TextUtils.isEmpty(strGroupName)) {
                    toast("??????????????????????????????");
                    return;
                }
                if (customDialog != null) {
                    if (data.size() > 0) {
                        boolean isNameTrue = false;
                        for (int i = 0; i < data.size(); i++) {
                            if (data.get(i).getProjectTitle().equals("" + strGroupName)) {
                                isNameTrue = true;
                            }
                        }
                        if (isNameTrue) {
                            toast("??????????????????");
                            return;
                        } else {
                            data.add(new CreateEventsBean.ProjectListBean(strGroupName, new ArrayList<>()));
                            createCompetitionCategoriesAdapter.notifyDataSetChanged();
                            customDialog.dismiss();
                        }
                    } else {
                        data.add(new CreateEventsBean.ProjectListBean(strGroupName, new ArrayList<>()));
                        createCompetitionCategoriesAdapter.notifyDataSetChanged();
                        customDialog.dismiss();
                    }
                }

            }
        });


    }

    @Override
    public void onAreaCallback(String province, String city, String area) {
        mBinding.tvHostCity.setText("" + province + " " + city + " " + area);
        mBinding.tvHostCity.setTextColor(Color.BLACK);
        strLocalCity = "" + province + " " + city + " " + area;
    }
}