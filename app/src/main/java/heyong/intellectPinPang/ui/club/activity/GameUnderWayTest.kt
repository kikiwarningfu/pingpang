package heyong.intellectPinPang.ui.club.activity


import android.app.Activity
import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.text.TextUtils
import android.util.Log
import android.view.View
import android.widget.TextView
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView

import com.chad.library.adapter.base.BaseQuickAdapter
import com.dnwalter.formlayoutmanager.entity.Monster
import com.dnwalter.formlayoutmanager.helper.FormScrollHelper
import com.dnwalter.formlayoutmanager.layoutmanager.FormLayoutManager
import com.google.gson.Gson

import java.util.ArrayList

import heyong.intellectPinPang.ui.BaseActivity
import heyong.handong.framework.utils.ImageLoader
import heyong.intellectPinPang.R
import heyong.intellectPinPang.adapter.game.CyclicGroupAdapter
import heyong.intellectPinPang.adapter.game.GameUnderWayListAdapter
import heyong.intellectPinPang.adapter.game.MonsterHAdapter
import heyong.intellectPinPang.adapter.game.TitleGameUnderWayAdapter
import heyong.intellectPinPang.adapter.game.TitleGameUnderWayTopAdapter
import heyong.intellectPinPang.bean.competition.SingleHitDataBean
import heyong.intellectPinPang.bean.competition.TitleBean
import heyong.intellectPinPang.databinding.ActivityGameUnderWayBinding
import heyong.intellectPinPang.ui.club.viewmodel.ClubViewModel
import heyong.intellectPinPang.ui.competition.activity.SinglesDetailActivity
import heyong.intellectPinPang.ui.competition.activity.SinglesDetailScoreActivity
import heyong.intellectPinPang.utils.AntiShakeUtils
import heyong.intellectPinPang.utils.MessageDialogBuilder
import heyong.intellectPinPang.widget.MyDividerItemDecoration

/**
 * 比赛进行中   单打 对内比赛
 */
class GameUnderWayTest : BaseActivity<ActivityGameUnderWayBinding, ClubViewModel>(),
    View.OnClickListener {
    private var strContestId = ""
    private var rvTopTitle: RecyclerView? = null
    private var rvLeftTitle: RecyclerView? = null
    private var mRecyclerView: RecyclerView? = null
    lateinit var tvNum:TextView
    private val toptitle = ArrayList<TitleBean>()
    private val leftTitles = ArrayList<TitleBean>()
    private var type = -1//type为1 是横向 type为2 的是竖向
    internal var monsterList: MutableList<Monster>? = ArrayList()
    lateinit var mSixDiD:MyDividerItemDecoration
    private var strTeamLeftId = ""

    private var GroupTitle = ""
    lateinit var leftAdapter:TitleGameUnderWayAdapter
    lateinit var topAdapter:TitleGameUnderWayTopAdapter
    lateinit var adapter:MonsterHAdapter
    lateinit var formScrollHelper:FormScrollHelper
    lateinit var cyclicGroupAdapter:CyclicGroupAdapter
    private var circlePosition = 0
    private var strMatchTitle = ""
    var MATCH_TYITLE = "match_title"
    var CONTEST_ID: String="contest_id"

    companion object {

        val TAG = GameUnderWayActivity::class.java.simpleName

    }
    fun goActivity( context:Context,constartId:String,matchTilte:String)
    {
        val intent= Intent(context,GameUnderWayTest::class.java)
        intent.putExtra("match_title",matchTilte)
        intent.putExtra("contest_id",constartId)
        context.startActivity(intent)
    }


    override fun getLayoutRes(): Int {
        return R.layout.activity_game_under_way
    }

    override fun initView(savedInstanceState: Bundle?) {
        mBinding.listener = this
        type = 1
        strContestId = intent.getStringExtra(CONTEST_ID)
        strMatchTitle = intent.getStringExtra(MATCH_TYITLE)
        mBinding.tvCenter.text = "" + strMatchTitle
        if (monsterList != null && monsterList!!.size > 0) {
            monsterList!!.clear()
        }
        mViewModel.getClubContestTeam(strContestId)

        //mBinding.tvRight.visibility = View.GONE
        val gridLayoutManager = GridLayoutManager(this, 5)
        mBinding.rvCycliGroup.removeItemDecoration(mSixDiD)
        mSixDiD = MyDividerItemDecoration(
            this, 5,

            resources.getColor(R.color.white)
        )
        mBinding.rvCycliGroup.addItemDecoration(mSixDiD)
        mBinding.rvCycliGroup.layoutManager = gridLayoutManager
        cyclicGroupAdapter = CyclicGroupAdapter()
        mBinding.rvCycliGroup.adapter = cyclicGroupAdapter

        mRecyclerView = findViewById(R.id.rv_form_list)
        rvTopTitle = findViewById(R.id.rv_top_list)
        rvLeftTitle = findViewById(R.id.rv_left_list)
        tvNum = findViewById(R.id.tv_num)
        leftAdapter = TitleGameUnderWayAdapter(leftTitles)
        topAdapter = TitleGameUnderWayTopAdapter(toptitle)
        topAdapter.setType(1)
        adapter = MonsterHAdapter(this, monsterList)
        adapter.setType(1)
        mBinding.tvCelOrition.text = "竖向表格"


        formScrollHelper = FormScrollHelper()
        formScrollHelper.connectRecyclerView(mRecyclerView!!)
        formScrollHelper.connectRecyclerView(rvTopTitle!!)
        formScrollHelper.connectRecyclerView(rvLeftTitle!!)
        /*总的结束比赛*/
        mViewModel.updateXlClubContestInfoLiveData.observe(this,
            Observer { responseBean ->
                /*结束比赛*/
                if (responseBean.errorCode == "200") {
                    toast("提交成功")
                    finish()
                } else {
                    toast("" + responseBean.message)
                }
            })
        cyclicGroupAdapter.onItemClickListener =
            BaseQuickAdapter.OnItemClickListener { adapter, view, position ->
                val fastClick = BaseActivity.isFastClick()
                if (!fastClick) {
                    val data = cyclicGroupAdapter.data
                    for (i in data.indices) {
                        data[i].isSelect = false
                    }
                    circlePosition = position
                    data[position].isSelect = true
                    cyclicGroupAdapter.setNewData(data)
                    val id = cyclicGroupAdapter.data[position].id
                    strTeamLeftId = "" + id
                    GroupTitle = cyclicGroupAdapter.data[position].teamNum
                    monsterList!!.clear()
                    mViewModel.getSingleHitData("" + strContestId, "" + strTeamLeftId)
                }
            }
        mViewModel.getClubContestTeamLiveData.observe(this,
            Observer { listResponseBean ->
                if (listResponseBean.errorCode == "200") {

                    val data = listResponseBean.data
                    if (data != null) {
                        try {
                            for (i in data.indices) {
                                if (i == 0) {
                                    data[i].isSelect = true
                                } else {
                                    data[i].isSelect = false
                                }
                            }
                            cyclicGroupAdapter.setNewData(data)
                            circlePosition = 0
                            GroupTitle = data[0].teamNum
                            strTeamLeftId = "" + data[0].id
                            mViewModel.getSingleHitData("" + strContestId, "" + strTeamLeftId)
                        } catch (e: Exception) {

                        }

                    } else {

                    }

                } else {
                    toast("" + listResponseBean.message)
                }
            })
        mViewModel.getClubContestTeamDataLiveData.observe(this,
            Observer { clubContestTeamDataBeanResponseBean ->
                if (clubContestTeamDataBeanResponseBean.errorCode == "200") {

                    val data = clubContestTeamDataBeanResponseBean.data
                    if (data != null) {
                        val contestArrangeList = data.contestArrangeList

                        val resultListData = data.resultListData
                        toptitle?.clear()
                        leftTitles?.clear()
                        for (i in resultListData.indices) {
                            val titleBean = TitleBean("" + resultListData[i].userName, 1)
                            toptitle.add(titleBean)
                            leftTitles.add(titleBean)
                            if (type == 1) {
                                if (i == resultListData.size - 1) {
                                    val titleBeanTop = TitleBean("积分", -1)
                                    toptitle.add(titleBeanTop)
                                    val titleBeanTop2 = TitleBean("计算", -2)
                                    toptitle.add(titleBeanTop2)
                                    val titleBeanTop3 = TitleBean("名次", -3)
                                    toptitle.add(titleBeanTop3)
                                }
                            } else if (type == 2) {
                                if (i == resultListData.size - 1) {
                                    val titleBeanLeft = TitleBean("积分", -1)
                                    leftTitles.add(titleBeanLeft)
                                    val titleBeanLeft2 = TitleBean("计算", -2)
                                    leftTitles.add(titleBeanLeft2)
                                    val titleBeanLeft3 = TitleBean("名次", -3)
                                    leftTitles.add(titleBeanLeft3)
                                }
                            }
                            val resultListDataBean = resultListData[i]
                            val arrangeList1 = resultListDataBean.arrangeList1
                            for (j in arrangeList1.indices) {
                                val arrangeList1Bean = arrangeList1[j]
                                val monster = Monster()
                                if (TextUtils.isEmpty(arrangeList1Bean.left1Name.toString())) {
                                    monster.type = 0
                                } else {
                                    if (TextUtils.isEmpty(arrangeList1Bean.status)) {
                                        //                                   /*未开始*/
                                        monster.type = 1
                                        monster.tableNum = "" + arrangeList1Bean.tableNum//球台数

                                    } else if (arrangeList1Bean.status == "1" || arrangeList1Bean.status == "3"
                                        || arrangeList1Bean.status == "4" || arrangeList1Bean.status == "5"
                                        || arrangeList1Bean.status == "6" || arrangeList1Bean.status == "2"
                                    ) {
                                        /*已结束的四种状态*/
                                        val leftWavier: Int
                                        val rightWavier: Int
                                        val leftWincount: Int
                                        val rightWinCount: Int
                                        if (type == 1) {
                                            leftWavier = arrangeList1Bean.leftWavier
                                            rightWavier = arrangeList1Bean.rightWavier
                                            leftWincount = arrangeList1Bean.leftWinCount
                                            rightWinCount = arrangeList1Bean.rightWinCount
                                        } else {

                                            leftWavier = arrangeList1Bean.rightWavier
                                            rightWavier = arrangeList1Bean.leftWavier
                                            leftWincount = arrangeList1Bean.rightWinCount
                                            rightWinCount = arrangeList1Bean.leftWinCount
                                        }
                                        /*两边都弃权*/
                                        if (leftWavier == 1 && rightWavier == 1) {
                                            val leftWinCount11 = arrangeList1Bean.leftWinCount
                                            val rightWinCount11 = arrangeList1Bean.rightWinCount
                                            monster.showScore = 0
                                            monster.type = 6
                                            monster.score = "W-$leftWinCount11 : W-$rightWinCount11"
                                        } else {

                                            if (leftWavier == 1 && rightWavier == 0) {
                                                monster.score = "W-$leftWincount : $rightWinCount"
                                                if (leftWincount == rightWinCount) {
                                                    monster.showScore = 0
                                                } else if (leftWincount > rightWinCount) {
                                                    monster.showScore = 2
                                                } else {
                                                    monster.showScore = 0
                                                }
                                                monster.type = 5
                                            } else if (leftWavier == 0 && rightWavier == 1) {
                                                if (leftWincount == rightWinCount) {
                                                    monster.showScore = 0
                                                } else if (leftWincount > rightWinCount) {
                                                    monster.showScore = 2
                                                } else {
                                                    monster.showScore = 0
                                                }
                                                monster.type = 4
                                                monster.score = "$leftWincount : W-$rightWinCount"
                                            } else {
                                                /*type设置为7*/
                                                if (leftWincount == rightWinCount) {
                                                    /*判断计算 是一个人赢 还是一个人输*/
                                                } else if (leftWincount > rightWinCount) {
                                                    /*左侧比分 大于右侧比分*/
                                                    monster.type = 2//左边赢是2
                                                    monster.score = "$leftWincount : $rightWinCount"
                                                    monster.showScore = 2
                                                } else {
                                                    monster.type = 3//右边赢是3
                                                    monster.score = "$leftWincount : $rightWinCount"
                                                    monster.showScore = 1
                                                }
                                            }
                                        }
                                    } else {
                                        monster.type = 1
                                        monster.tableNum = "" + arrangeList1Bean.tableNum//球台数
                                    }
                                }
                                monster.contestId = "" + arrangeList1Bean.id
                                monster.itemType = "" + arrangeList1Bean.contestType
                                monsterList!!.add(monster)


                                if (type == 1) {
                                    if (j == arrangeList1.size - 1) {
                                        val monster1 = Monster()
                                        monster1.type = -1
                                        monster1.teamId = "" + resultListDataBean.teamId
                                        monster1.intergal = "" + resultListDataBean.integral
                                        monsterList!!.add(monster1)
                                        val monster2 = Monster()
                                        if (TextUtils.isEmpty(resultListDataBean.result2)) {
                                            if (TextUtils.isEmpty(resultListDataBean.result1)) {
                                                monster2.result = ""
                                            } else {
                                                monster2.result = "1-" + resultListDataBean.result1
                                            }
                                        } else {
                                            monster2.result = "2-" + resultListDataBean.result2
                                        }
                                        monster2.type = -2

                                        monsterList!!.add(monster2)
                                        val monster3 = Monster()
                                        monster3.ranking = "" + resultListDataBean.ranking
                                        monster3.type = -3

                                        monsterList!!.add(monster3)

                                    }

                                }

                            }
                        }

                        val resultListData2 = data.resultListData

                        if (type == 2) {
                            for (i in 0..2) {
                                if (i == 0) {
                                    for (j in resultListData2.indices) {
                                        val monster = Monster()
                                        monster.type = -1
                                        monster.teamId = "" + resultListData2[j].teamId
                                        monster.intergal = "" + resultListData2[j].integral
                                        monsterList!!.add(monster)
                                    }
                                } else if (i == 1) {
                                    for (j in resultListData2.indices) {
                                        val monster = Monster()
                                        if (TextUtils.isEmpty(resultListData2[j].result2)) {
                                            if (TextUtils.isEmpty(resultListData2[j].result1)) {
                                                monster.result = ""
                                            } else {
                                                monster.result = "1-" + resultListData2[j].result1
                                            }
                                        } else {
                                            monster.result = "2-" + resultListData2[j].result2
                                        }
                                        monster.type = -2
                                        monsterList!!.add(monster)
                                    }

                                } else if (i == 2) {
                                    for (j in resultListData2.indices) {
                                        val monster = Monster()
                                        monster.ranking = "" + resultListData2[j].ranking
                                        monster.type = -3
                                        monsterList!!.add(monster)
                                    }

                                }
                            }
                        }
                        val gson = Gson()
                        val s = gson.toJson(monsterList)
                        dealData(contestArrangeList)
                    }


                } else {
                    toast("" + clubContestTeamDataBeanResponseBean.message)
                }
            })

    }

    private fun dealData(contestArrangeList: List<SingleHitDataBean.ContestArrangeListBeanX>) {

        if (!TextUtils.isEmpty(GroupTitle)) {
            tvNum.text = "" + GroupTitle
        }

        //        if (leftTitles.size() >= 10) {
        //            ViewGroup.LayoutParams layoutParams = rvLeftTitle.getLayoutParams();
        //            layoutParams.height = leftTitles.size() * 88;
        //            rvLeftTitle.setLayoutParams(layoutParams);
        //            ViewGroup.LayoutParams layoutParams1 = mRecyclerView.getLayoutParams();
        //            layoutParams1.height = leftTitles.size() * 88;
        //            mRecyclerView.setLayoutParams(layoutParams1);
        //        }
        rvLeftTitle!!.layoutManager =
            LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false)

        rvLeftTitle!!.adapter = leftAdapter

        rvTopTitle!!.layoutManager =
            LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false)

        rvTopTitle!!.adapter = topAdapter

        val layoutManager = FormLayoutManager(toptitle.size, mRecyclerView)
        mRecyclerView!!.layoutManager = layoutManager
        mRecyclerView!!.adapter = adapter

        val layoutParams = rvLeftTitle!!.layoutParams
        layoutParams.height = 150 * leftTitles.size
        rvLeftTitle!!.layoutParams = layoutParams

        val layoutParams1 = mRecyclerView!!.layoutParams
        layoutParams1.height = 150 * leftTitles.size
        mRecyclerView!!.layoutParams = layoutParams1


        //        rvLeftTitle.getViewTreeObserver().addOnGlobalLayoutListener(
        //                new ViewTreeObserver.OnGlobalLayoutListener() {
        //
        //                    @Override
        //                    public void onGlobalLayout() {
        //                        //只需要获取一次高度，获取后移除监听器
        //                        rvLeftTitle.getViewTreeObserver().removeGlobalOnLayoutListener(this);
        //                        //这里高度应该定义为成员变量，定义为局部为展示代码方便
        //                        int height = rvLeftTitle.getHeight();
        //                        ViewGroup.LayoutParams layoutParams1 = mRecyclerView.getLayoutParams();
        //                        Log.e(TAG, "dealData: mRecyclerVIew height" + layoutParams1.height);
        //                        layoutParams1.height = height;
        //                        mRecyclerView.setLayoutParams(layoutParams1);
        //                    }
        //
        //                });


        adapter.setListener { monster, position, type ->
            val teamId = monster.teamId
            Log.e(TAG, "OnListener: monster==" + Gson().toJson(monster))
            val arrangeId = monster.contestId
            val intergal = monster.intergal
            when (type) {
                -2//计算
                -> {
                    showLoading()
                    //                        请求接口 弹窗
                    mViewModel.calculationInfo(teamId, intergal)
                }
                2//左边赢
                -> {
                    //                        toast("左边赢");

                    //单打
                    val intent =
                        Intent(this, SinglesDetailScoreActivity::class.java)
                    intent.putExtra(SinglesDetailScoreActivity.CONTEST_ARRANGE_ID, "" + arrangeId)
                    intent.putExtra(SinglesDetailScoreActivity.CLICK_TYPE, "" + 2)
                    startActivity(intent)
                }
                5//左边赢  右边弃权  //判断这个人的id 是否是自己 然后显示黑色
                -> {

                    //                        toast("右边弃权");
                    val intent1 =
                        Intent(this, SinglesDetailScoreActivity::class.java)
                    intent1.putExtra(SinglesDetailScoreActivity.CONTEST_ARRANGE_ID, "" + arrangeId)
                    intent1.putExtra(SinglesDetailScoreActivity.CLICK_TYPE, "" + 2)
                    startActivity(intent1)
                }
                3//右边赢
                -> {
                    //                        toast("右边赢");
                    val intent2 =
                        Intent(this, SinglesDetailScoreActivity::class.java)
                    intent2.putExtra(SinglesDetailScoreActivity.CONTEST_ARRANGE_ID, "" + arrangeId)
                    intent2.putExtra(SinglesDetailScoreActivity.CLICK_TYPE, "" + 2)
                    startActivity(intent2)
                }
                4////右边赢  左边弃权
                -> {
                    //                        toast("左边弃权");
                    val intent3 =
                        Intent(this, SinglesDetailScoreActivity::class.java)
                    intent3.putExtra(SinglesDetailScoreActivity.CONTEST_ARRANGE_ID, "" + arrangeId)
                    intent3.putExtra(SinglesDetailScoreActivity.CLICK_TYPE, "" + 2)
                    startActivity(intent3)
                }
                6//两边都弃权
                -> {
                    //                        toast("两边都弃权");
                    val intent4 =
                        Intent(this, SinglesDetailScoreActivity::class.java)
                    intent4.putExtra(SinglesDetailScoreActivity.CONTEST_ARRANGE_ID, "" + arrangeId)
                    intent4.putExtra(SinglesDetailScoreActivity.CLICK_TYPE, "" + 2)
                    startActivity(intent4)
                }
            }
        }
        /*val gameUnderWayListAdapter = GameUnderWayListAdapter(this)
        mBinding.rvVsList.layoutManager =
            LinearLayoutManager(this, RecyclerView.VERTICAL, false)
        mBinding.rvVsList.adapter = gameUnderWayListAdapter
        gameUnderWayListAdapter.setNewData(contestArrangeList)
        gameUnderWayListAdapter.setListener { id, bean, position ->
            //        clickType;//按钮类型 1是开始，创建初始数据，2是修改，查询数据

            val intent = Intent(this, SinglesDetailActivity::class.java)
            intent.putExtra(SinglesDetailActivity.CONTEST_ARRANGE_ID, "" + id)
            if (TextUtils.isEmpty(bean.status)) {
                intent.putExtra(SinglesDetailActivity.CLICK_TYPE, "" + 1)
            } else {
                intent.putExtra(SinglesDetailActivity.CLICK_TYPE, "" + 2)
            }

            startActivityForResult(intent, 1)
        }*/

    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (requestCode == 1) {

            if (resultCode == Activity.RESULT_OK) {
                val datas = cyclicGroupAdapter.data
                for (i in datas.indices) {
                    datas[i].isSelect = false
                }
                datas[circlePosition].isSelect = true
                cyclicGroupAdapter.setNewData(datas)
                val id = cyclicGroupAdapter.data[circlePosition].id
                strTeamLeftId = "" + id
                GroupTitle = cyclicGroupAdapter.data[circlePosition].teamNum
                monsterList!!.clear()
                mViewModel.getSingleHitData("" + strContestId, "" + strTeamLeftId)

            }
        }
    }

    override fun onClick(v: View) {
        when (v.id) {
            R.id.ll_orition -> {
                if (AntiShakeUtils.isInvalidClick(v))
                    return
                if (type == 1) {
                    type = 2
                    mBinding.tvCelOrition.text = "横向表格"
                    adapter.setType(2)

                    ImageLoader.loadImage(mBinding.ivUpAndDown, R.drawable.img_yellow_arrow_up)
                } else {
                    type = 1
                    mBinding.tvCelOrition.text = "竖向表格"
                    adapter.setType(1)
                    ImageLoader.loadImage(mBinding.ivUpAndDown, R.drawable.img_yellow_arrow_down)

                }
                monsterList!!.clear()

                mViewModel.getSingleHitData("" + strContestId, "" + strTeamLeftId)
            }
            R.id.iv_finish ->

                finish()
            R.id.tv_end_of_game//结束比赛
            -> {
                if (AntiShakeUtils.isInvalidClick(v))
                    return
                MessageDialogBuilder(this)
                    .setMessage("")
                    .setTvTitle("是否要结束比赛？")
                    .setTvCancel("取消")
                    .setTvSure("确定")
                    .setBtnCancleHint(false)
                    .setSureListener { v1 -> getEndData() }
                    .show()
            }
        }
    }

    private fun getEndData() {
        mViewModel.updateXlClubContestInfo("" + strContestId, false)

    }


}