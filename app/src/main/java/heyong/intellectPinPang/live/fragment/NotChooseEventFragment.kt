package heyong.intellectPinPang.live.fragment

import android.graphics.Color
import android.os.Bundle
import android.text.TextUtils
import android.util.Log
import android.view.View
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.chad.library.adapter.base.BaseQuickAdapter
import com.chad.library.adapter.base.BaseViewHolder
import com.elvishew.xlog.XLog
import com.google.android.flexbox.FlexDirection
import com.google.android.flexbox.FlexWrap
import com.google.android.flexbox.FlexboxLayoutManager
import com.google.android.flexbox.JustifyContent
import com.google.gson.Gson
import com.kongzue.dialog.util.TextInfo
import com.scwang.smartrefresh.layout.api.RefreshLayout
import com.scwang.smartrefresh.layout.listener.OnRefreshListener
import heyong.intellectPinPang.R
import heyong.intellectPinPang.adapter.game.RoundSecondAdapter
import heyong.intellectPinPang.adapter.game.XLGameProjectAdapter
import heyong.intellectPinPang.adapter.game.XLGameSexAdapter
import heyong.intellectPinPang.bean.competition.MatchScreenFormatBean
import heyong.intellectPinPang.bean.live.CanonDemandBean
import heyong.intellectPinPang.bean.live.XlZhiboGetMatchScreenDataBean
import heyong.intellectPinPang.databinding.FragmentNotChooseEventBinding
import heyong.intellectPinPang.databinding.ItemNoChooseGameBinding
import heyong.intellectPinPang.live.activity.LiveOrderActivity
import heyong.intellectPinPang.live.activity.YourOrderListActivity
import heyong.intellectPinPang.live.adapter.MyTagGameGroupZhiboAdapter
import heyong.intellectPinPang.live.adapter.MyTagLittleGroupAdapter
import heyong.intellectPinPang.live.model.LiveViewModel
import heyong.intellectPinPang.ui.BaseActivity
import heyong.intellectPinPang.ui.BaseFragment
import java.util.ArrayList

class NotChooseEventFragment(private val matchIds: String) :
    BaseFragment<FragmentNotChooseEventBinding, LiveViewModel>(), View.OnClickListener,
    OnRefreshListener {

    private var matchId: String = ""
    var project: MutableList<XlZhiboGetMatchScreenDataBean.ProjectBean> = mutableListOf()
    private var TAG = YourOrderListActivity::class.java.simpleName
    private var projectItemId = "";
    private var strMatchId = "" //赛事id
    var matchScreenFormatBean: MatchScreenFormatBean? = null

    var myTagGameGroupAdapter: MyTagGameGroupZhiboAdapter? = null//组别
    var gameSexAdapter: XLGameSexAdapter? = null//性别
    var gameProjectAdapter: XLGameProjectAdapter? = null//项目

    val myTagLittleGroupAdapter: MyTagLittleGroupAdapter by lazy {
        MyTagLittleGroupAdapter()
    }
    val roundSecondAdapter: RoundSecondAdapter by lazy {
        RoundSecondAdapter()
    }//轮次
    val noChooseAdapter: NoChooseGameAdapter by lazy {
        NoChooseGameAdapter()
    }
    var selectCode = ""
    var groupNumId = ""
    var itemTiles = ""
    var page = 1
    var pageSize = 10
    private var haveMore = true
    var list: MutableList<CanonDemandBean.ListBean> = ArrayList()

    var newItemTitle = "";
    var groupTitle = "";
    var sexTitle = "";
    var projectTitle = ""

    var stageTite = ""
    var littleGroupTitle = ""
    var lunciTitle = ""
    var gamerule = ""
    var leftComPosition = ""
    var rightComPosition = ""
    override fun onResume() {
        super.onResume()
        redrawTitle()
        if (!TextUtils.isEmpty(newItemTitle)) {
            page = 1;
            haveMore = true;
            viewModel.getCanOnDemand(
                "" + strMatchId, "" + projectItemId,
                "" + groupNumId, "" + page, "" + pageSize
            )
        }
    }

    override fun getLayoutRes(): Int {
        return R.layout.fragment_not_choose_event
    }

    override fun initView(savedInstanceState: Bundle?) {
        matchId = matchIds
        viewModel.xlZhibogetMatchScreen(matchId)

        strMatchId = matchId;
        binding.listener = this
        if (binding.swFresh.isRefreshing) {
            binding.swFresh.finishRefresh()
        }

        binding.swFresh.setOnRefreshListener(this)
        binding.swFresh.setOnLoadmoreListener {
            if (haveMore) {
                page++
                viewModel.getCanOnDemand(
                    "" + strMatchId, "" + projectItemId,
                    "" + groupNumId, "" + page, "" + pageSize
                )
            } else {
                binding.swFresh.finishLoadmore()
            }
        }
        //xlZhiboArrange/getMatchScreen
        viewModel.xlZhibogetMatchScreenData.observe(this, androidx.lifecycle.Observer {
            project.clear()
            binding.llZubie.visibility = View.VISIBLE
            binding.llSex.visibility = View.VISIBLE
            binding.llProject.visibility = View.VISIBLE
            if (it.data != null) {
                project = it.data.project as MutableList<XlZhiboGetMatchScreenDataBean.ProjectBean>
                newItemTitle = "";
                for (i in project.indices) {
                    val projectBean = project[i]
                    if (i == 0) {
                        projectBean.isSelect = true
                        groupTitle = "" + projectBean.projectTitle
                    } else {
                        projectBean.isSelect = false
                    }
                    val sex = projectBean.sex
                    for (m in sex.indices) {
                        if (m == 0) {
                            sex[m].isSelect = true
                            sexTitle = sex[m].sexName

                        } else {
                            sex[m].isSelect = false
                        }
                        val item = sex[m].item
                        for (n in item.indices) {
                            if (n == 0) {
                                item[n].isSelect = true
                                //项目
                                projectTitle = "" + item[n].projectItemName
                            } else {
                                item[n].isSelect = false
                            }
                        }
                    }
                }
                myTagGameGroupAdapter = MyTagGameGroupZhiboAdapter(activity, project)
                binding.rvGroup.adapter = myTagGameGroupAdapter
                //                    gameGroupAdapter.setNewData(project);
                binding.rvGroup.setOnTagClickListener { _, position, _ ->
                    val fastClick = BaseActivity.isFastClick()
                    if (!fastClick) {
                        for (i in project.indices) {
                            project[i].isSelect = false
                            val sex = project[i].sex
                            for (i1 in sex.indices) {
                                sex[i1].isSelect = false
                                val item = sex[i1].item
                                for (i2 in item.indices) {
                                    item[i2].isSelect = false
                                }
                            }
                        }
                        project[position].isSelect = true
                        groupTitle = project[position].projectTitle
                        val projectBean = project[position]
                        val sex = projectBean.sex
                        Log.e(BaseActivity.TAG, "onItemClick:json=== " + Gson().toJson(sex))
                        for (m in sex.indices) {
                            //                                if (m == 0) {
                            Log.e(BaseActivity.TAG, "onItemClick: m===0")
                            if (m == 0) {
                                sex[m].isSelect = true
                                sexTitle = sex[m].sexName


                            } else {
                                sex[m].isSelect = false
                            }
                            val item = sex[m].item
                            for (n in item.indices) {
                                if (n == 0) {
                                    Log.e(BaseActivity.TAG, "onItemClick: n===0")
                                    item[n].isSelect = true
                                    projectItemId = "" + item[n].id
                                    projectTitle = "" + item[n].projectItemName
                                    Log.e(
                                        BaseActivity.TAG,
                                        "onItemClick: projectItemId===$projectItemId"
                                    )
                                } else {
                                    Log.e(BaseActivity.TAG, "onItemClick: n====$n")
                                    item[n].isSelect = false
                                }
                            }
                            sex[m].item = item
                            projectBean.sex = sex
                        }
                        //                            Log.e(TAG, "onItemClick: project====" + new Gson().toJson(project));
                        gameSexAdapter!!.setNewData(project[position].sex)
                        //                            Log.e(TAG, "onItemClick: sex设置数据===" + new Gson().toJson(project.get(position).getSex()));
                        //                                gameGroupAdapter.notifyDataSetChanged();
                        myTagGameGroupAdapter!!.setData(project)
                        gameSexAdapter!!.notifyDataSetChanged()
                        val item = project[position].sex[0].item
                        Log.e(BaseActivity.TAG, "onItemClick: item====" + Gson().toJson(item))
                        for (i in item.indices) {
                            if (i == 0) {
                                item[i].isSelect = true
                                projectItemId = "" + item[0].id
                            } else {
                                item[i].isSelect = false
                            }
                        }
                        gameProjectAdapter!!.setNewData(item)
                        Log.e(BaseActivity.TAG, "onChanged: okhttp===22222")
                        viewModel.xlZhibogetMatchScreenFormat("" + strMatchId, "" + projectItemId)
                    }
                    false
                }
                if (project.size > 0 && project[0].sex.size > 0 && project[0].sex[0].item.size > 0) {
                    val id = project[0].sex[0].item[0].id
                    Log.e(BaseActivity.TAG, "onChanged: okhttp===11111")
                    viewModel.xlZhibogetMatchScreenFormat("" + strMatchId, "" + id)
                }

                gameSexAdapter = XLGameSexAdapter()
                binding.rvSex.layoutManager =
                    LinearLayoutManager(activity, RecyclerView.HORIZONTAL, false)
                binding.rvSex.adapter = gameSexAdapter
                if (project.size > 0) {
                    gameSexAdapter!!.setNewData(project[0].sex)
                    /*刷新性别*/
                    gameSexAdapter!!.setOnItemClickListener { _, _, position ->
                        val fastClick = BaseActivity.isFastClick()
                        if (!fastClick) {
                            val data = gameSexAdapter!!.data
                            for (i in data.indices) {
                                data[i].isSelect = false
                            }
                            data[position].isSelect = true
                            sexTitle = data[position].sexName

                            val item = data[position].item
                            for (m in item.indices) {
                                if (m == 0) {
                                    item[m].isSelect = true
                                    projectItemId = "" + item[m].id
                                    projectTitle = "" + item[m].projectItemName
                                } else {
                                    item[m].isSelect = false
                                }
                            }
                            gameSexAdapter!!.notifyDataSetChanged()
                            gameProjectAdapter!!.setNewData(item)
                            Log.e(BaseActivity.TAG, "onChanged: okhttp===33333")
                            viewModel.xlZhibogetMatchScreenFormat(
                                "" + strMatchId,
                                "" + projectItemId
                            )
                        }
                    }

                    gameProjectAdapter = XLGameProjectAdapter()
                    binding.rvProject.layoutManager =
                        LinearLayoutManager(activity, RecyclerView.HORIZONTAL, false)
                    binding.rvProject.adapter = gameProjectAdapter
                    val item = project[0].sex[0].item
                    for (i in item.indices) {
                        if (i == 0) {
                            item[i].isSelect = true
                            projectItemId = "" + item[0].id
                            projectTitle = "" + item[0].projectItemName
                        } else {
                            item[i].isSelect = false
                        }
                    }
                    gameProjectAdapter!!.setNewData(item)

                    gameProjectAdapter!!.setOnItemClickListener { _, _, position ->
                        val fastClick = BaseActivity.isFastClick()
                        if (!fastClick) {
                            val data = gameProjectAdapter!!.data
                            for (i in data.indices) {
                                data[i].isSelect = false
                            }
                            data[position].isSelect = true
                            projectTitle = "" + data[position].projectItemName
                            val id = data[position].id
                            projectItemId = "" + id
                            gameProjectAdapter!!.notifyDataSetChanged()
                            Log.e(BaseActivity.TAG, "onChanged: okhttp===4444444")
                            viewModel.xlZhibogetMatchScreenFormat(
                                "" + strMatchId,
                                "" + projectItemId
                            )
                        }
                    }
                }
            }
        })
        //xlZhiboArrange/getMatchScreenFormat
        viewModel.xlZhibogetMatchScreenFormatData.observe(this, Observer {
            it.let {
                if (it.errorCode.equals("200")) {
                    stageTite = "";
                    matchScreenFormatBean = it.data
                    if (it.data != null) {
                        gamerule = it.data.matchRule
                        if (gamerule == "3") {
                            //循环和淘汰
                            binding.rlSingle.visibility = View.GONE
                            binding.rlRecyclerAndElimination.visibility = View.VISIBLE
                            selectCode = "1"
                            binding.tvStageOne.setBackgroundResource(R.drawable.shape_club_blue)
                            binding.tvStageTwo.setBackgroundResource(R.drawable.shape_club_item_gray)
                            binding.tvStageOne.setTextColor(Color.parseColor("#FFFFFF"))
                            binding.tvStageTwo.setTextColor(Color.parseColor("#ff666666"))
                            //显示小组 隐藏轮次
                            binding.llRound.visibility = View.GONE
                            binding.llLittleGroup.visibility = View.VISIBLE
                            var get =
                                it.data.matchType.get(0) as MatchScreenFormatBean.MatchTypeBean

                            if (it.data.matchType != null && get.info.size > 0) {
                                showGroup(get)
                                stageTite = "第一阶段"
                                viewModel.getCanOnDemand(
                                    "" + strMatchId, "" + projectItemId,
                                    "" + groupNumId, "" + page, "" + pageSize
                                )
                            } else {
                                binding.llRound.visibility = View.GONE
                                binding.llLittleGroup.visibility = View.GONE
                                toast("暂无编排")
                                list.clear()
                                noChooseAdapter.setNewData(list)
                            }


                        } else if (gamerule == "1") {
                            //循环  用matchsingle
                            binding.rlSingle.visibility = View.VISIBLE
                            binding.rlRecyclerAndElimination.visibility = View.GONE
                            binding.tvRecycler.text = "循环赛"
                            //小组隐藏 轮次 隐藏
                            binding.llRound.visibility = View.GONE
                            binding.llLittleGroup.visibility = View.GONE

                            stageTite = "循环赛"
                            viewModel.getCanOnDemand(
                                "" + strMatchId, "" + projectItemId,
                                "" + groupNumId, "" + page, "" + pageSize
                            )

//                            使用matchSingle
                        } else if (gamerule == "2") {
                            lunciTitle = ""
                            //淘汰
                            binding.rlSingle.visibility = View.VISIBLE
                            binding.rlRecyclerAndElimination.visibility = View.GONE
                            binding.tvRecycler.text = "淘汰赛"
                            //小组隐藏 轮次 显示
                            binding.llRound.visibility = View.VISIBLE
                            binding.llLittleGroup.visibility = View.GONE

                            binding.rvLunci.adapter = roundSecondAdapter
                            binding.rvLunci.layoutManager = LinearLayoutManager(
                                activity,
                                RecyclerView.HORIZONTAL, false
                            )
                            stageTite = "淘汰赛"

                            val matchType =
                                it.data.matchType.get(0) as MatchScreenFormatBean.MatchTypeBean
                            if (matchType != null && matchType.info.size > 0) {
                                binding.llRound.visibility = View.VISIBLE
                                val info: List<MatchScreenFormatBean.MatchTypeBean.InfoBean> =
                                    matchType.getInfo()
                                for (i in info.indices) {
                                    if (i == 0) {
                                        info[i].isSelect = true
                                        groupNumId = "" + info[0].id
                                        itemTiles = "" + info[0].titleName
                                        lunciTitle = "" + info[0].titleName
                                    } else {
                                        info[i].isSelect = false
                                    }
                                }
                                roundSecondAdapter.setNewData(info)
                            } else {
                                if (roundSecondAdapter != null) {
                                    roundSecondAdapter.setNewData(ArrayList<MatchScreenFormatBean.MatchTypeBean.InfoBean>())
                                    binding.llRound.visibility = View.GONE
                                }
                            }
                            viewModel.getCanOnDemand(
                                "" + strMatchId, "" + projectItemId,
                                "" + groupNumId, "" + page, "" + pageSize
                            )
                        }
                    } else {
                        toast(it.message)
                    }
                    redrawTitle()
                } else if (it.errorCode.equals("100000-100061")) {


                } else {
                    toast(it.message)
                }
            }

        })


        viewModel.getCanOnDemandData.observe(this, Observer {

            if (it.errorCode.equals("200")) {
                if (it.data.list != null && it.data.list.size > 0) {
                    if (page == 1) {
                        list.clear()
                    }
                    list.addAll(it.data.list)

                    if (it.data.list != null && it.data.list.size < pageSize) {
                        haveMore = false
                        binding.swFresh.isEnableLoadmore = false
                    } else {
                        binding.swFresh.isEnableLoadmore = true
                    }
                    binding.rvOrderList.layoutManager = LinearLayoutManager(
                        activity,
                        RecyclerView.VERTICAL, false
                    )
                    binding.rvOrderList.adapter = noChooseAdapter
                    noChooseAdapter.setNewData(list)
                    if (binding.swFresh != null) {
                        binding.swFresh.finishRefresh()
                        binding.swFresh.finishLoadmore()
                    }
                    noChooseAdapter.setOnItemChildClickListener(BaseQuickAdapter.OnItemChildClickListener { baseQuickAdapter, view, i ->

                        var data = noChooseAdapter.data.get(i) as CanonDemandBean.ListBean
                        leftComPosition = ""
                        if (TextUtils.isEmpty(data.player1)) {
                            leftComPosition = "" + ((i + 1) * 2 - 1) + "号位"
                        }
                        rightComPosition = ""
                        if (TextUtils.isEmpty(data.player3)) {
                            rightComPosition = "" + ((i + 1) * 2) + "号位"
                        }
//                        if (!TextUtils.isEmpty(bean.player1)) {
//                            binding!!.tvLeftUserNameOne.text = bean.player1
//                            binding!!.tvLeftClubName.text = bean.club1Name
//                        } else {
//                            binding!!.tvLeftUserNameOne.text = "虚位待赛"
//                            binding!!.tvLeftClubName.text =
//                                "" + ((helper.adapterPosition + 1) * 2 - 1) + "号位"
//                        }
//                        if (!TextUtils.isEmpty(bean.player3)) {
//                            binding!!.tvRightUserNameOne.text = bean.player3
//                            binding!!.tvRightClubName.text = bean.club2Name
//                        } else {
//                            binding!!.tvRightUserNameOne.text = "虚位待赛"
//                            binding!!.tvRightClubName.text =
//                                "" + ((helper.adapterPosition + 1) * 2) + "号位"
//                        }

                        activity.let {
                            LiveOrderActivity.startActivity(
                                it!!,
                                "" + newItemTitle,
                                "" + data.arrangeId, leftComPosition, rightComPosition
                            )

                        }
                    })
                } else {
                    if (page == 1) {
                        list.clear()
                    }
                    binding.swFresh.finishRefresh()
                    binding.swFresh.finishLoadmore()
                    noChooseAdapter.setNewData(list)
                }
                redrawTitle()
            } else {
                if (!it.errorCode.equals("100000-100061")) {
                    toast(it.message)
                }
                if (page == 1) {
                    list.clear()
                }
                noChooseAdapter.setNewData(list)
            }
            if (binding.swFresh != null) {
                binding.swFresh.finishRefresh()
            }


        })


    }

    private fun redrawTitle() {
        //1 循环 2 淘汰 3 循环+淘汰
        if (gamerule.equals("1")) {
            newItemTitle =
                "" + groupTitle + "-" + sexTitle + "-" + projectTitle
        } else if (gamerule.equals("2")) {
            //淘汰赛
            if (TextUtils.isEmpty(lunciTitle)) {
                newItemTitle =
                    "" + groupTitle + "-" + sexTitle + "-" + projectTitle + "-" + stageTite
            } else {
                newItemTitle =
                    "" + groupTitle + "-" + sexTitle + "-" + projectTitle + "-" + stageTite + "-" + lunciTitle
            }

        } else if (gamerule.equals("3")) {
            if (stageTite.equals("第一阶段")) {
                if (TextUtils.isEmpty(littleGroupTitle)) {
                    newItemTitle =
                        "" + groupTitle + "-" + sexTitle + "-" + projectTitle + "-" + stageTite
                } else {
                    newItemTitle =
                        "" + groupTitle + "-" + sexTitle + "-" + projectTitle + "-" + stageTite + "-" + littleGroupTitle
                }
            } else {
                if (TextUtils.isEmpty(lunciTitle)) {
                    newItemTitle =
                        "" + groupTitle + "-" + sexTitle + "-" + projectTitle + "-" + stageTite
                } else {
                    newItemTitle =
                        "" + groupTitle + "-" + sexTitle + "-" + projectTitle + "-" + stageTite + "-" + lunciTitle
                }
            }

        }
    }

    private fun showGroup(matchScreenFormatBean: MatchScreenFormatBean.MatchTypeBean) {
        val flexboxLayoutManager = FlexboxLayoutManager(activity)
        flexboxLayoutManager.flexDirection = FlexDirection.ROW //主轴为水平方向，起点在左端。
        flexboxLayoutManager.flexWrap = FlexWrap.WRAP //按正常方向换行
        //justifyContent 属性定义了项目在主轴上的对齐方式。
        //justifyContent 属性定义了项目在主轴上的对齐方式。
        flexboxLayoutManager.justifyContent =
            JustifyContent.FLEX_START //交叉轴的起点对齐。
        binding.rvXiaozu.setLayoutManager(flexboxLayoutManager)
        binding.rvXiaozu.setAdapter(myTagLittleGroupAdapter)
        val info: MutableList<MatchScreenFormatBean.MatchTypeBean.InfoBean> = ArrayList()
        var infos = matchScreenFormatBean.info
        for (i in infos.indices) {
            val infoBean = MatchScreenFormatBean.MatchTypeBean.InfoBean()
            if (i == 0) {
                infoBean.isSelect = true
            } else {
                infoBean.isSelect = false
            }
            infoBean.id = infos.get(i).id
            infoBean.titleName = "" + infos.get(i).getTitle()
            info.add(infoBean)
        }

        if (info.size > 0) {
            myTagLittleGroupAdapter.setNewData(info)
            binding.llLittleGroup.visibility = View.VISIBLE
            groupNumId = "" + info[0].id
            littleGroupTitle = "" + info[0].titleName
            if (!littleGroupTitle.endsWith("组")) {
                littleGroupTitle = littleGroupTitle + "组"
            }
            viewModel.getCanOnDemand(
                "" + strMatchId, "" + projectItemId,
                "" + groupNumId, "" + page, "" + pageSize
            )
        } else {
            littleGroupTitle = ""
            binding.llLittleGroup.visibility = View.GONE
            myTagLittleGroupAdapter.setNewData(ArrayList<MatchScreenFormatBean.MatchTypeBean.InfoBean>())
        }
        myTagLittleGroupAdapter.setOnItemClickListener(BaseQuickAdapter.OnItemClickListener { adapter, view, position ->
            //                                clearData()
            val data1: List<MatchScreenFormatBean.MatchTypeBean.InfoBean> =
                myTagLittleGroupAdapter.data
            for (i in data1.indices) {
                data1[i].isSelect = false
            }
            data1[position].isSelect = true
            myTagLittleGroupAdapter.setNewData(data1)
            groupNumId = "" + data1[position].id
            littleGroupTitle = "" + data1[position].titleName
            if (!littleGroupTitle.endsWith("组")) {
                littleGroupTitle = littleGroupTitle + "组"
            }
            viewModel.getCanOnDemand(
                "" + strMatchId, "" + projectItemId,
                "" + groupNumId, "" + page, "" + pageSize
            )
        })
    }


    inner class NoChooseGameAdapter :
        BaseQuickAdapter<CanonDemandBean.ListBean, BaseViewHolder>(R.layout.item_no_choose_game) {
        override fun convert(helper: BaseViewHolder, bean: CanonDemandBean.ListBean) {

            val binding = DataBindingUtil.bind<ItemNoChooseGameBinding>(helper.getConvertView())
            var projectItem = bean.projectType
            binding!!.tvDate.text = bean.dayTime
            binding!!.tvTime.text = bean.hourTime
            helper.addOnClickListener(R.id.tv_see_live)
            if (projectItem == "1" || projectItem == "2") {
                if (!TextUtils.isEmpty(bean.player1)) {
                    binding!!.tvLeftUserNameOne.text = bean.player1
                    binding!!.tvLeftClubName.text = bean.club1Name
                } else {
                    binding!!.tvLeftUserNameOne.text = "虚位待赛"
                    binding!!.tvLeftClubName.text =
                        "" + ((helper.adapterPosition + 1) * 2 - 1) + "号位"
                }
                if (!TextUtils.isEmpty(bean.player3)) {
                    binding!!.tvRightUserNameOne.text = bean.player3
                    binding!!.tvRightClubName.text = bean.club2Name
                } else {
                    binding!!.tvRightUserNameOne.text = "虚位待赛"
                    binding!!.tvRightClubName.text =
                        "" + ((helper.adapterPosition + 1) * 2) + "号位"
                }


            } else {
                //双打
                if (!TextUtils.isEmpty(bean.player1)) {
                    binding!!.tvLeftUserNameOne.text = bean.player1
                    binding!!.tvLeftUserNameTwo.text = bean.player2
                    binding!!.tvLeftClubName.text = bean.club1Name
                } else {
                    binding!!.tvLeftUserNameOne.text = "虚位待赛"
                    binding!!.tvLeftUserNameTwo.text = "虚位待赛"
                    binding!!.tvLeftClubName.text =
                        "" + ((helper.adapterPosition + 1) * 2 - 1) + "号位"
                }

                if (!TextUtils.isEmpty(bean.player3)) {
                    binding!!.tvRightUserNameOne.text = bean.player3
                    binding!!.tvRightUserNameTwo.text = bean.player4
                    binding!!.tvRightClubName.text = bean.club2Name
                } else {
                    binding!!.tvRightUserNameOne.text = "虚位待赛"
                    binding!!.tvRightClubName.text =
                        "" + ((helper.adapterPosition + 1) * 2 - 1) + "号位"
                }


            }

        }

    }

    override fun onRefresh(p0: RefreshLayout?) {
        page = 1;
        haveMore = true;
        if (selectCode.equals("2")) {
            //第二阶段
//            matchScreenFormatBean

                if (matchScreenFormatBean != null) {
                    if(matchScreenFormatBean!!.matchType!=null) {
                        var bean = matchScreenFormatBean!!.matchType!!.get(1)
                        if (bean != null) {
                            if (bean.info != null && bean.info.size > 0) {
                                viewModel.getCanOnDemand(
                                    "" + strMatchId, "" + projectItemId,
                                    "" + groupNumId, "" + page, "" + pageSize
                                )
                            } else {
                                XLog.e("没有数据2 ")
                                binding.swFresh.finishRefresh()
                                binding.swFresh.finishLoadmore()
                            }
                        } else {
                            XLog.e("没有数据4 ")
                            binding.swFresh.finishRefresh()
                            binding.swFresh.finishLoadmore()
                        }
                    }else{
                        XLog.e("没有数据5 ")
                        binding.swFresh.finishRefresh()
                        binding.swFresh.finishLoadmore()
                    }
                } else {
                    XLog.e("没有数据1 ")
                    binding.swFresh.finishRefresh()
                    binding.swFresh.finishLoadmore()
                }


        } else {
            viewModel.getCanOnDemand(
                "" + strMatchId, "" + projectItemId,
                "" + groupNumId, "" + page, "" + pageSize
            )
        }

    }

    override fun onClick(v: View?) {
        when (v?.id) {
            R.id.tv_stage_one -> {
                if (matchScreenFormatBean?.matchRule.equals("3")) {
                    selectCode = "1"
                    stageTite = "第一阶段"
                    binding.tvStageOne.setBackgroundResource(R.drawable.shape_club_blue)
                    binding.tvStageTwo.setBackgroundResource(R.drawable.shape_club_item_gray)
                    binding.tvStageOne.setTextColor(Color.parseColor("#FFFFFF"))
                    binding.tvStageTwo.setTextColor(Color.parseColor("#ff666666"))
                    binding.llRound.visibility = View.GONE
                    binding.llLittleGroup.visibility = View.VISIBLE
                    matchScreenFormatBean.let {
                        var get = matchScreenFormatBean!!.matchType.get(0)
                        //显示小组 隐藏轮次
                        showGroup(get)
                    }
                }

            }
            R.id.tv_stage_two -> {
                selectCode = "2"
                binding.tvStageOne.setBackgroundResource(R.drawable.shape_club_item_gray)
                binding.tvStageTwo.setBackgroundResource(R.drawable.shape_club_blue)
                binding.tvStageOne.setTextColor(Color.parseColor("#ff666666"))
                binding.tvStageTwo.setTextColor(Color.parseColor("#FFFFFF"))
                //隐藏小组  显示轮次
                stageTite = "第二阶段"
                matchScreenFormatBean.let {
                    var get = matchScreenFormatBean!!.matchType.get(1)
                    //显示轮次 隐藏小组
                    binding.llRound.visibility = View.VISIBLE
                    binding.llLittleGroup.visibility = View.GONE

                    val info: MutableList<MatchScreenFormatBean.MatchTypeBean.InfoBean> =
                        ArrayList()
                    var infos = get.info
                    for (i in infos.indices) {
                        val infoBean = MatchScreenFormatBean.MatchTypeBean.InfoBean()
                        if (i == 0) {
                            infoBean.isSelect = true
                        } else {
                            infoBean.isSelect = false
                        }
                        infoBean.id = infos.get(i).id
                        infoBean.titleName = infos.get(i).titleName
                        info.add(infoBean)
                    }

                    if (info.size > 0) {
                        myTagLittleGroupAdapter.setNewData(info)
                        binding.llRound.visibility = View.VISIBLE
                        groupNumId = "" + info[0].id
                        roundSecondAdapter.setNewData(info)
                        lunciTitle = "" + info[0].titleName
                        binding.rvLunci.layoutManager = LinearLayoutManager(
                            activity,
                            RecyclerView.HORIZONTAL, false
                        )
                        binding.rvLunci.adapter = roundSecondAdapter
                        viewModel.getCanOnDemand(
                            "" + strMatchId, "" + projectItemId,
                            "" + groupNumId, "" + page, "" + pageSize
                        )
                        roundSecondAdapter!!.setOnItemClickListener { adapter, view, position ->
                            val fastClick = BaseActivity.isFastClick()
                            if (!fastClick) {
                                val data = roundSecondAdapter!!.data
                                for (i in data.indices) {
                                    data[i].isSelect = false
                                }
                                data[position].isSelect = true
                                lunciTitle = data[position].titleName
                                val id = data[position].id
                                groupNumId = "" + id
                                roundSecondAdapter!!.notifyDataSetChanged()
                                Log.e(BaseActivity.TAG, "onChanged: okhttp===4444444")
                                viewModel.getCanOnDemand(
                                    "" + strMatchId, "" + projectItemId,
                                    "" + groupNumId, "" + page, "" + pageSize
                                )
                            }
                        }
                    } else {
                        lunciTitle = ""
                        binding.llRound.visibility = View.GONE
                        roundSecondAdapter.setNewData(ArrayList<MatchScreenFormatBean.MatchTypeBean.InfoBean>())
                        noChooseAdapter.setNewData(ArrayList<CanonDemandBean.ListBean>())
                    }

                }
            }
        }
    }
}