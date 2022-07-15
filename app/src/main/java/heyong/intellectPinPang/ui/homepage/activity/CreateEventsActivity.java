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
 * 创建比赛界面 1  收费设置 APP代收费 是1  的话     如果允许个人报名 运动员报名费 是必须判断的 如果 不允许个人报名 需要判断团体报名费和运动员
 * 报名费不能为空
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
    //-----------------------------------赛事信息参数
    private String strStartAndEndKaishi = "";
    private String strStartAndEnd = "";
    private String strStartAndEndJieshu = "";
    private String strEndTime = "";
    private String strLocalCity = "";
    //------------------------------------上传图片参数
    private String strMatchImgUrl = "";
    private String strStampImgUrl = "";
    private int strImageShowType = 1;//1 是比赛图片  2 是印章图片
    private int matchImgType = 0;
    //--------------------------------------收费设置  设置类别
    private int allowPersonalType = 0;//0 是不允许  1 是允许个人报名参赛
    private int realType = 0;//0是不用实名认证 1 是需要实名认证
    private int needMoneyType = 0;//0是不用APP代收费 1 是需要APP交钱
    private int collectionRoomType = 0;//0是不代收食宿费  1是代收食宿费
    //存在的情况   就是needMoneyType== 0 不需要代收费 两个都隐藏
    //当需要代收费的时候 needMoneyType==1 判断 是否允许个人报名参赛 如果是0  隐藏  是1  显示
    String strGroupName = "";//组别名称

    public static final int REQUEST_CODE_SELECT_PHOTO = 1; //图片-相册
    public static final int REQUEST_CODE_TAKE_PHOTO = 2;   //拍照-相机
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
                        .setMessage("现在退出不会保存填写过的信息，下次进入\n" +
                                "将重新填写")
                        .setTvTitle("是否退出创建比赛页面？")
                        .setTvCancel("取消")
                        .setTvSure("确定")
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
        options1Items.add("托儿索");
        options1Items.add("儿童劫");
        options1Items.add("小学生之手");
        options1Items.add("德玛西亚大保健");
        options1Items.add("面对疾风吧");
        options1Items.add("天王盖地虎");
        options1Items.add("我发一米五");
        options1Items.add("爆刘继芬");
        competitionLevels.clear();
        competitionLevels.add("全国级");
        competitionLevels.add("省级");
        competitionLevels.add("市级");
        levelCompetitions.clear();
        levelCompetitions.add("专业级");
        levelCompetitions.add("业余级");


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
                                        if (projectItemList.get(j).getItemTitle().contains("男团体")) {
                                            isShuangDaClickNan = true;
                                        }
                                    }

                                    for (int j1 = 0; j1 < projectItemList.size(); j1++) {
                                        if (projectItemList.get(j1).getItemTitle().contains("女团体")) {
                                            isShuangDaClickWoman = true;
                                        }
                                    }

                                }
                            }
                            boolean isHunShuangClick = false;
                            if (matchshuiping.equals("专业级")) {
                                /*还需要再次判断 是否符合条件*/
                                boolean isSelectNan = false;
                                boolean isSelectNv = false;
                                for (int i = 0; i < data.size(); i++) {
                                    List<CreateEventsBean.ProjectListBean.ProjectItemListBean> projectItemList = data.get(i).getProjectItemList();
                                    for (int j = 0; j < projectItemList.size(); j++) {
                                        if (projectItemList.get(j).getItemTitle().contains("男团体")) {
                                            isSelectNan = true;
                                        }
                                    }

                                    for (int j1 = 0; j1 < projectItemList.size(); j1++) {
                                        if (projectItemList.get(j1).getItemTitle().contains("女团体")) {
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
                            if (matchshuiping.equals("专业级")) {
                                intent.putExtra(NewCompetitionActivity.COMPETITION_LEVEL, 1);
                            } else {
                                intent.putExtra(NewCompetitionActivity.COMPETITION_LEVEL, 0);
                            }
                            if (isHunShuangClick) {
                                intent.putExtra(NewCompetitionActivity.HUNSHUANG_CLICK, "true");
                            } else {
                                intent.putExtra(NewCompetitionActivity.HUNSHUANG_CLICK, "false");
                            }
                            if (matchshuiping.equals("专业级")) {
                                if (isShuangDaClickNan) {
                                    intent.putExtra(NewCompetitionActivity.SHUANGDA_CANSELECT_MAN, "true");
                                } else {
                                    intent.putExtra(NewCompetitionActivity.SHUANGDA_CANSELECT_MAN, "false");
                                }
                            } else {
                                intent.putExtra(NewCompetitionActivity.SHUANGDA_CANSELECT_MAN, "true");
                            }
                            if (matchshuiping.equals("专业级")) {
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
                                    .setTvTitle("是否删除" + projectTitle + "?")
                                    .setTvCancel("取消")
                                    .setBtnCancleHint(false)
                                    .setTvSure("确定")
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
                            .setSubCalSize(20)//确定和取消文字大小
                            .setSubmitColor(Color.BLUE)//确定按钮文字颜色
                            .setCancelColor(Color.BLUE)//取消按钮文字颜色
                            .setTextColorCenter(Color.parseColor("#000000"))//设置选中项的颜色
                            .setTextColorOut(Color.parseColor("#666666"))
                            .build();
                    pvOptions.setPicker(options1Items);
                    pvOptions.show();
                } else {
                    toast("暂无可用俱乐部");
                }
            }
        });
        mViewModel.getUploadTokenLiveData.observe(this, new Observer<ResponseBean>() {
            @Override
            public void onChanged(ResponseBean responseBean) {
                //                if (responseBean.isSuccess()) {
                if (responseBean.getErrorCode().equals("200")) {

                    UploadManager uploadManager = new UploadManager();
                    //                    // 设置图片名字
                    uploadImage(picPath, responseBean.getData().toString(), uploadManager, key);
                } else {
                    toast("暂无权限");
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
                            .setMessage("请等待系统审核")
                            .setTvTitle("提交成功")
                            .setTvCancel("取消")
                            .setBtnCancleHint(true)
                            .setTvSure("返回")
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
                //功能实现部分
                if (s.length() >= 1 && Integer.parseInt(s.toString()) != 0) {
                    try {
                        int money = Integer.parseInt(s.toString());
                        if (money == 0) {
                            toast("金额不能为0");
                        } else {
                            mBinding.cbTuanti.setChecked(true);
                        }
                    } catch (Exception e) {
                        mBinding.cbTuanti.setChecked(false);
                        toast("输入有误");
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
                /*判断类别是否显示*/


                //功能实现部分
                if (s.length() >= 1 && Integer.parseInt(s.toString()) != 0) {
                    mBinding.cbPersonalCheck.setChecked(true);
                    try {
                        int money = Integer.parseInt(s.toString());
                        if (money == 0) {
                            toast("金额不能为0");
                        } else {
                            mBinding.cbPersonalCheck.setChecked(true);
                        }
                    } catch (Exception e) {
                        mBinding.cbPersonalCheck.setChecked(false);
                        toast("输入有误");
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
                    toast("不可添加重复项目,请创建新的组别");
                } else {
                    dataList.add(bean);
                }
                projectListBean.setProjectItemList(dataList);
                createCompetitionCategoriesAdapter.notifyDataSetChanged();
            }
        }else  if (data != null && requestCode == IMAGE_PICKER) {
            //选择相册
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


    //--------------------------------------收费设置  设置类别
    //    private int allowPersonalType = 0;//0 是不允许  1 是允许个人报名参赛
    //    private int realType = 0;//0是不用实名认证 1 是需要实名认证
    //    private int needMoneyType = 0;//0是不用APP代收费 1 是需要APP交钱
    //存在的情况   就是needMoneyType== 0 不需要代收费 两个都隐藏
    //当需要代收费的时候 needMoneyType==1 判断 是否允许个人报名参赛 如果是0  隐藏  是1  显示
    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.rl_level_competition://（选择专业级比赛，没有混合比赛，且新建团体赛后才可
                if (AntiShakeUtils.isInvalidClick(v))
                    return;
                OptionsPickerView pvOptionLevelCompetition =
                        new OptionsPickerView.Builder(CreateEventsActivity.this, new OptionsPickerView.OnOptionsSelectListener() {
                            @Override
                            public void onOptionsSelect(int options1, int option2, int options3, View v) {
                                //返回的分别是三个级别的选中位置
                                String s = levelCompetitions.get(options1);
                                strGroupName = "";
                                //                        button4.setText(s);
                                if (TextUtils.isEmpty(matchshuiping)) {
                                    matchshuiping = s;
                                } else if (matchshuiping.equals(s)) {
                                    /*不做操作*/
                                    toast("不做操作");
                                } else {
                                    matchshuiping = s;
                                    /*如果不相等  清空data*/
                                    data.clear();
                                    createCompetitionCategoriesAdapter.notifyDataSetChanged();
                                }

                                mBinding.tvLevelText.setText("" + s);
                                mBinding.tvLevelText.setTextColor(Color.BLACK);
                            }
                        })
                                //                        .setSubmitText("确定")//确定按钮文字
                                //                        .setCancelText("取消")//取消按钮文字
                                //                        .setTitleText("城市选择")//标题
                                .setSubCalSize(20)//确定和取消文字大小
                                //                        .setTitleSize(20)//标题文字大小
                                //                        .setTitleColor(Color.BLACK)//标题文字颜色
                                .setSubmitColor(Color.BLUE)//确定按钮文字颜色
                                .setCancelColor(Color.BLUE)//取消按钮文字颜色
                                //                        .setTitleBgColor(0xFF333333)//标题背景颜色 Night mode
                                //                        .setBgColor(0xFF000000)//滚轮背景颜色 Night mode
                                //                        .setContentTextSize(18)//滚轮文字大小
                                //                        .setTextColorCenter(Color.BLUE)//设置选中项的颜色
                                .setTextColorCenter(Color.parseColor("#000000"))//设置选中项的颜色
                                //setTextColorOut
                                .setTextColorOut(Color.parseColor("#666666"))
                                //                        .setLineSpacingMultiplier(1.6f)//设置两横线之间的间隔倍数
                                //                        .setLinkage(false)//设置是否联动，默认true
                                //                        .setLabels("省", "市", "区")//设置选择的三级单位
                                //                        .isCenterLabel(false) //是否只显示中间选中项的label文字，false则每项item全部都带有label。
                                //                        .setCyclic(false, false, false)//循环与否
                                //                        .setSelectOptions(1, 1, 1)  //设置默认选中项
                                //                        .setOutSideCancelable(false)//点击外部dismiss default true
                                //                        .isDialog(true)//是否显示为对话框样式
                                .build();
                pvOptionLevelCompetition.setPicker(levelCompetitions);
                pvOptionLevelCompetition.show();

                break;
            case R.id.cb_tuanti://团体报名费
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
            case R.id.cb_personal_check://个人报名费
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
            case R.id.iv_daisoufei_yes://代收食宿费  yes
                if (AntiShakeUtils.isInvalidClick(v))
                    return;
                collectionRoomType = 1;
                ImageLoader.loadImage(mBinding.ivDaisoufeiYes, R.drawable.icon_login_selected);
                ImageLoader.loadImage(mBinding.ivDaishoufeiNo, R.drawable.icon_login_unselected);
                mBinding.etEatMoney.setEnabled(true);
                mBinding.etDay.setEnabled(true);
                mBinding.viewShifufeiMasking.setVisibility(View.GONE);

                break;

            case R.id.iv_daishoufei_no://代收食宿费  no
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


            case R.id.iv_personal_sign_up_yes://允许个人报名参赛
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
            case R.id.iv_personal_sign_up_no://不允许个人报名参赛
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
            case R.id.iv_real_type_yes://必须实名认证
                if (AntiShakeUtils.isInvalidClick(v))
                    return;
                realType = 1;
                ImageLoader.loadImage(mBinding.ivRealTypeYes, R.drawable.icon_login_selected);
                ImageLoader.loadImage(mBinding.ivRealTypeNo, R.drawable.icon_login_unselected);
                break;
            case R.id.iv_real_type_no://不是必须实名认证
                if (AntiShakeUtils.isInvalidClick(v))
                    return;
                realType = 0;
                ImageLoader.loadImage(mBinding.ivRealTypeYes, R.drawable.icon_login_unselected);
                ImageLoader.loadImage(mBinding.ivRealTypeNo, R.drawable.icon_login_selected);
                break;

            case R.id.iv_baoming_need_yes://是否用APP代收报名费  yes
                if (AntiShakeUtils.isInvalidClick(v))
                    return;
                needMoneyType = 1;

                ImageLoader.loadImage(mBinding.ivBaomingNeedNo, R.drawable.icon_login_unselected);
                ImageLoader.loadImage(mBinding.ivBaomingNeedYes, R.drawable.icon_login_selected);
                mBinding.viewMasking.setVisibility(View.GONE);
                mBinding.etTuantiBaomingMoney.setEnabled(true);
                mBinding.etPersonalBaomingMoney.setEnabled(true);
                break;
            case R.id.iv_baoming_need_no:////是否用APP代收报名费  no
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
            case R.id.iv_saishi_image://赛事宣传图片
                if (AntiShakeUtils.isInvalidClick(v))
                    return;
                strImageShowType = 1;

                PhotoSelDialog dialog2 = new PhotoSelDialog(this);
                dialog2.setClickListener(this);
                dialog2.show();
                break;
            case R.id.iv_logo_tuzhang://上传图章
                if (AntiShakeUtils.isInvalidClick(v))
                    return;
                if (matchImgType == 1) {
                    strImageShowType = 2;
                    PhotoSelDialog dialog3 = new PhotoSelDialog(this);
                    dialog3.setClickListener(this);
                    dialog3.show();
                }


                break;
            /*申请俱乐部*/
            case R.id.rl_apply_for_club://申请俱乐部
                if (AntiShakeUtils.isInvalidClick(v))
                    return;
                mViewModel.myClubList();


                break;
            case R.id.rl_saishi_level://赛事等级
                if (AntiShakeUtils.isInvalidClick(v))
                    return;
                OptionsPickerView pvOptionCompetitionLevel =
                        new OptionsPickerView.Builder(CreateEventsActivity.this, new OptionsPickerView.OnOptionsSelectListener() {
                            @Override
                            public void onOptionsSelect(int options1, int option2, int options3, View v) {
                                //返回的分别是三个级别的选中位置
                                String s = competitionLevels.get(options1);
                                matchdengji = s;
                                mBinding.tvSaishiLevel.setText("" + s);
                                mBinding.tvSaishiLevel.setTextColor(Color.BLACK);

                            }
                        })
                                .setSubCalSize(20)//确定和取消文字大小
                                .setSubmitColor(Color.BLUE)//确定按钮文字颜色
                                .setCancelColor(Color.BLUE)//取消按钮文字颜色
                                .setTextColorCenter(Color.parseColor("#000000"))//设置选中项的颜色
                                .setTextColorOut(Color.parseColor("#666666"))
                                .build();
                pvOptionCompetitionLevel.setPicker(competitionLevels);
                pvOptionCompetitionLevel.show();

                break;

            case R.id.tv_commit_bidding_information://   点击申办信息的下一步  判断条件 然后设置值 下一步
                if (AntiShakeUtils.isInvalidClick(v))
                    return;
                String strEtCompetitionName = mBinding.etSaishiName.getText().toString().trim();
                String strEtZhubanfang = mBinding.etZhubanfang.getText().toString().trim();
                String strEtChengbanfang = mBinding.etChengbanfa.getText().toString().trim();
                if (TextUtils.isEmpty(strEtCompetitionName)) {
                    toast("请输入赛事名称");
                    return;
                }
                if (TextUtils.isEmpty(strClubName)) {
                    toast("请选择俱乐部");
                    return;
                }
                if (TextUtils.isEmpty(strEtZhubanfang)) {
                    toast("请输入主办方");
                    return;
                }
                if (TextUtils.isEmpty(strEtChengbanfang)) {
                    toast("请输入承办方");
                    return;
                }
                if (TextUtils.isEmpty(matchdengji)) {
                    toast("请选择赛事等级");
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
            case R.id.tv_event_information_up://赛事信息 点击上一步
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
            case R.id.rl_baoming_time://报名截止时间
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
                        .setType(TimePickerView.Type.YEAR_MONTH_DAY_HOUR_MIN)//默认全部显示
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
            case R.id.ll_start_end_time://开始和结束时间
                if (AntiShakeUtils.isInvalidClick(v))
                    return;
                TimeGameSelectView pvTimeSelectEnd = new TimeGameSelectView.Builder(CreateEventsActivity.this, new TimeGameSelectView.OnTimeSelectListener() {
                    @Override
                    public void onTimeSelect(Date dateMax, Date dateMin, View v) {
                        String time = getTime(dateMax);
                        if (dateEndTime == null) {
                            toast("请选择报名截止时间");
                            return;
                        }
                        if (dateEndTime.after(dateMin)) {
                            toast("比赛开始时间不能早于报名截止时间");
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
                                    toast("开始时间不能大于结束时间");
                                }
                            }

                        }
                    }
                })
                        .setType(TimePickerView.Type.YEAR_MONTH_DAY)//默认全部显示
                        .setCancelText("取消")//取消按钮文字
                        .setSubmitText("确定")//确认按钮文字
                        .setContentSize(20)//滚轮文字大小
                        .setTitleSize(20)//标题文字大小
                        //                        .setTitleText("请选择时间")//标题文字
                        .setOutSideCancelable(true)//点击屏幕，点在控件外部范围时，是否取消显示
                        .isCyclic(false)//是否循环滚动
                        .setTextColorCenter(Color.parseColor("#333333"))//设置选中项的颜色
                        .setTextColorOut(Color.parseColor("#333333"))
                        .setTitleColor(Color.BLACK)//标题文字颜色
                        .setSubmitColor(Color.BLUE)//确定按钮文字颜色
                        .setCancelColor(Color.BLUE)//取消按钮文字颜色
                        .isCenterLabel(false) //是否只显示中间选中项的label文字，false则每项item全部都带有label。
                        .build();
                pvTimeSelectEnd.setDate(Calendar.getInstance());//注：根据需求来决定是否使用该方法（一般是精确到秒的情况），此项可以在弹出选择器的时候重新设置当前时间，避免在初始化之后由于时间已经设定，导致选中时间与当前时间不匹配的问题。
                pvTimeSelectEnd.show();
                break;
            case R.id.tv_event_information_next://赛事信息 点击下一步
                if (AntiShakeUtils.isInvalidClick(v))
                    return;
                /*判断比赛城市*/
                if (TextUtils.isEmpty(strLocalCity)) {
                    toast("请选择举办城市");
                    return;
                }
                /*判断比赛地点*/
                String etDetailAddress = mBinding.etDetailAddress.getText().toString().trim();
                String etConnectName = mBinding.etConnectName.getText().toString().trim();
                String etTelPhone = mBinding.etTelPhone.getText().toString().trim();
                if (TextUtils.isEmpty(etDetailAddress)) {
                    toast("请输入比赛地点");
                    return;
                }
                /*判断联系人*/
                if (TextUtils.isEmpty(etConnectName)) {
                    toast("请输入联系人姓名");
                    return;
                }

                /*判断联系电话*/
                if (TextUtils.isEmpty(etTelPhone)) {
                    toast("请输入联系电话");
                    return;
                }

                if (!CommonUtilis.isMobileNO(etTelPhone)) {
                    toast("请输入正确的手机号");
                    return;
                }
                /*判断报名截止时间*/
                if (TextUtils.isEmpty(strEndTime)) {
                    toast("请选择报名截止时间");
                    return;
                }
                /*判断比赛时间*/
                if (TextUtils.isEmpty(strStartAndEnd)) {
                    toast("请选择比赛时间");
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

            case R.id.tv_upload_picture_up://上传图片的上一步
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
            case R.id.iv_upload_zhang_yes://上传主办方
                if (AntiShakeUtils.isInvalidClick(v))
                    return;
                strStampImgUrl = "";
                ImageLoader.loadImage(mBinding.ivUploadZhangYes, R.drawable.icon_login_selected);
                ImageLoader.loadImage(mBinding.ivUploadZhangNo, R.drawable.icon_login_unselected);
                ImageLoader.loadImage(mBinding.ivLogoTuzhang, R.drawable.img_upload_cachet);
                matchImgType = 1;
                break;
            case R.id.iv_upload_zhang_no://不上传主办方
                if (AntiShakeUtils.isInvalidClick(v))
                    return;
                matchImgType = 0;
                ImageLoader.loadImage(mBinding.ivLogoTuzhang, R.drawable.img_not_upload);
                ImageLoader.loadImage(mBinding.ivUploadZhangYes, R.drawable.icon_login_unselected);
                ImageLoader.loadImage(mBinding.ivUploadZhangNo, R.drawable.icon_login_selected);
                break;
            case R.id.tv_upload_picture_next://上传图片的下一步
                if (AntiShakeUtils.isInvalidClick(v))
                    return;

                if (TextUtils.isEmpty(strMatchImgUrl)) {
                    toast("请上传赛事宣传图片");
                    return;
                }
                if (matchImgType == 1) {
                    if (TextUtils.isEmpty(strStampImgUrl)) {
                        toast("请上传主办方图章");
                        return;
                    }
                }
                /*判断是否上传主办方章*/
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
            case R.id.tv_charging_set_up://收费设置 点击上一步
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
            case R.id.tv_charging_set_next://收费设置  点击下一步
                if (AntiShakeUtils.isInvalidClick(v))
                    return;
                /*需要判断 选中了那些  判断是否允许个人报名参赛   还有是否选中团队报名费用*/
                //--------------------------------------收费设置  设置类别
                //    private int allowPersonalType = 0;//0 是不允许  1 是允许个人报名参赛
                //    private int realType = 0;//0是不用实名认证 1 是需要实名认证
                //    private int needMoneyType = 0;//0是不用APP代收费 1 是需要APP交钱
                //存在的情况   就是needMoneyType== 0 不需要代收费 两个都隐藏
                //当需要代收费的时候 needMoneyType==1 判断 是否允许个人报名参赛 如果是0  隐藏  是1  显示
                //
                //                创建比赛界面 1  收费设置 APP代收费 是1  的话     如果允许个人报名 运动员报名费 是必须判断的
                //                如果 不允许个人报名 需要判断团体报名费和运动员

                String etTuantiBaomingMoney = mBinding.etTuantiBaomingMoney.getText().toString();
                String etPersonalMoney = mBinding.etPersonalBaomingMoney.getText().toString();
                String etSimpleMoney = mBinding.etEatMoney.getText().toString();
                String etDay = mBinding.etDay.getText().toString();

                createEventsBean.setAuthentication("" + realType);//实名认证
                createEventsBean.setOwnRegistration("" + allowPersonalType);//允许个人报名
                if (needMoneyType == 1) {
                    /*APP代收费*/
                    createEventsBean.setReplaceCharge("1");//APP代收报名费
                    /*判断是否允许个人报名  allowPersonalType 为1 不能为空*/
                    if (allowPersonalType == 1) {
                        /*必须有个人报名*/
                        if (TextUtils.isEmpty(etPersonalMoney)) {
                            toast("请输入个人报名费");
                            return;
                        }
                        createEventsBean.setOwnFree("" + etPersonalMoney);//个人报名
                    } else {
                        /*不是必须个人报名*/
                        if (!TextUtils.isEmpty(etPersonalMoney) || !TextUtils.isEmpty(etTuantiBaomingMoney)) {
                            if (!TextUtils.isEmpty(etPersonalMoney)) {
                                createEventsBean.setOwnFree("" + etPersonalMoney);//个人报名
                            }
                            if (!TextUtils.isEmpty(etTuantiBaomingMoney)) {
                                createEventsBean.setRegistrationCount("" + etTuantiBaomingMoney);//团队费用
                            }

                        } else {
                            toast("请输入报名费");
                            return;
                        }

                    }
                } else {

                    /*APP不代收费*/
                    createEventsBean.setReplaceCharge("0");
                }

                createEventsBean.setReplaceChargeEat("" + collectionRoomType);

                if (collectionRoomType == 1) {
                    if (TextUtils.isEmpty(etSimpleMoney)) {
                        toast("请输入每天食宿费");
                        return;
                    }
                    if (TextUtils.isEmpty(etDay)) {
                        toast("请输入天数");
                        return;
                    }
                    double money = Double.parseDouble(etSimpleMoney) * Double.parseDouble(etDay);
                    createEventsBean.setEatFree("" + CommonUtilis.doubleMoney(money));//食宿费
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

            case R.id.tv_competition_item_up://上一步
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
            case R.id.tv_competition_item_next://提交申请
                if (AntiShakeUtils.isInvalidClick(v))
                    return;
                if (TextUtils.isEmpty(matchshuiping)) {
                    toast("请选择赛事水平");
                    return;
                }
                createEventsBean.setMatchLevel("" + matchshuiping);
                List<CreateEventsBean.ProjectListBean> dataCategory = createCompetitionCategoriesAdapter.getData();
                if (dataCategory == null || dataCategory.size() <= 0) {
                    toast("选择项目不能为空");
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
                        toast("" + titleName + "组别不能为空");
                        return;
                    }

                    new MessageDialogBuilder(CreateEventsActivity.this)
                            .setMessage("待系统审核后，赛事即可开始报名")
                            .setTvTitle("是否提交申请？")
                            .setTvCancel("取消")
                            .setBtnCancleHint(false)
                            .setTvSure("确定")
                            .setSureListener(new View.OnClickListener() {
                                @Override
                                public void onClick(View v) {

                                    Calendar c = Calendar.getInstance();//
                                    int mYear = c.get(Calendar.YEAR); // 获取当前年份
                                    int mMonth = c.get(Calendar.MONTH) + 1;// 获取当前月份
                                    int mDay = c.get(Calendar.DAY_OF_MONTH);// 获取当日期
                                    String maxYear = mYear + "-" + mMonth + "-" + mDay + " " + "00:00:00";
                                    showLoading();
                                    String minYear = 1900 + "-" + mMonth + "-" + mDay + " " + "00:00:00";
                                    //2021-12-16 00:00:00
                                    for (int i = 0; i < dataCategory.size(); i++) {
                                        CreateEventsBean.ProjectListBean projectListBean = dataCategory.get(i);
                                        List<CreateEventsBean.ProjectListBean.ProjectItemListBean> projectItemList = projectListBean.getProjectItemList();
                                        for (int n = 0; n < projectItemList.size(); n++) {
                                            CreateEventsBean.ProjectListBean.ProjectItemListBean projectItemListBean = projectItemList.get(n);
                                            if (projectItemListBean.getMaxAge().equals("不限年龄")) {
                                                projectItemListBean.setMaxAge("" + maxYear);
                                            }
                                            if (projectItemListBean.getMinAge().equals("不限年龄")) {
                                                projectItemListBean.setMinAge("" + minYear);
                                            }
                                            if (projectItemListBean.getManMaxAge().equals("不限年龄")) {
                                                projectItemListBean.setManMaxAge("" + maxYear);
                                            }
                                            if (projectItemListBean.getManMinAge().equals("不限年龄")) {
                                                projectItemListBean.setManMinAge("" + minYear);
                                            }
                                            if (projectItemListBean.getWomanMaxAge().equals("不限年龄")) {
                                                projectItemListBean.setWomanMaxAge("" + maxYear);
                                            }
                                            if (projectItemListBean.getWomanMinAge().equals("不限年龄")) {
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
                    toast("请先选择赛事水平");
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
                            //申请的权限全部允许
                            //图片- 拍照
//                            ImagePicker imagePicker = ImagePicker.getInstance();
//                            imagePicker.setMultiMode(false);
//                            imagePicker.setShowCamera(false);
//                            imagePicker.setCrop(false);
//                            imagePicker.setFocusHeight(SystemHelper.getScreenInfo(CreateEventsActivity.this).heightPixels);
//                            imagePicker.setFocusWidth(SystemHelper.getScreenInfo(CreateEventsActivity.this).widthPixels);
//                            imagePickerUtil.takePhoto(CreateEventsActivity.this, REQUEST_CODE_TAKE_PHOTO, CreateEventsActivity.this);
                            Intent intent = new Intent(CreateEventsActivity.this, ImageGridActivity.class);
                            intent.putExtra(ImageGridActivity.EXTRAS_TAKE_PICKERS, true); // 是否是直接打开相机
                            startActivityForResult(intent, REQUEST_CODE_SELECT);
                        } else {
                            //只要有一个权限被拒绝，就会执行
                            toast("未授权权限，部分功能不能使用");
                        }
                    }
                });
    }

    @Override
    public void selectPhoto() {
        //图片 - 选择相册
        //        if ( binding.gridImageView.getImages().size() >= 9) {
        //            ToastUtils.showShort("最多9张图片！");
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
        if (strImageShowType == 1) {//比赛图片
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
                // info.error中包含了错误信息，可打印调试
                // 上传成功后将key值上传到自己的服务器
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
                    Log.e(TAG, "complete: 上传后的图片==="+headpicPath );
                } else {
                }
                //上传至阡陌链接
                //uploadpictoQianMo(headpicPath, picPath);

            }
        }, null);

    }

    String key = "";
    String picPath = "";

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
        mViewModel.getUploadToken(key);

    }


    @Override
    public boolean dispatchTouchEvent(MotionEvent ev) {
        if (ev.getAction() == MotionEvent.ACTION_DOWN) {
            View v = getCurrentFocus();
            //当isShouldHideInput(v, ev)为true时，表示的是点击输入框区域，则需要显示键盘，同时显示光标，反之，需要隐藏键盘、光标
            if (isShouldHideInput(v, ev)) {

                InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
                if (imm != null) {
                    imm.hideSoftInputFromWindow(v.getWindowToken(), 0);
                    //处理Editext的光标隐藏、显示逻辑
                    v.clearFocus();
                }
            }
            return super.dispatchTouchEvent(ev);
        }
        // 必不可少，否则所有的组件都不会有TouchEvent了
        if (getWindow().superDispatchTouchEvent(ev)) {
            return true;
        }
        return onTouchEvent(ev);
    }

    public boolean isShouldHideInput(View v, MotionEvent event) {
        if (v != null && (v instanceof EditText)) {
            int[] leftTop = {0, 0};
            //获取输入框当前的location位置
            v.getLocationInWindow(leftTop);
            int left = leftTop[0];
            int top = leftTop[1];
            int bottom = top + v.getHeight();
            int right = left + v.getWidth();
            if (event.getX() > left && event.getX() < right
                    && event.getY() > top && event.getY() < bottom) {
                // 点击的是输入框区域，保留点击EditText的事件
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
                    toast("请输入新建的组别名称");
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
                            toast("组别名称重复");
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