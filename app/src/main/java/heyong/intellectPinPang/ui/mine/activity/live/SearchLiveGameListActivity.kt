package heyong.intellectPinPang.ui.mine.activity.live

import android.app.Activity
import android.content.Context
import android.content.Intent
import android.graphics.Rect
import android.os.Bundle
import android.text.Editable
import android.text.TextUtils
import android.text.TextWatcher
import android.view.View
import android.view.inputmethod.EditorInfo
import android.view.inputmethod.InputMethodManager
import com.chad.library.adapter.base.BaseQuickAdapter
import com.zhy.view.flowlayout.FlowLayout
import com.zhy.view.flowlayout.TagFlowLayout
import heyong.intellectPinPang.R
import heyong.intellectPinPang.bean.live.TagBean
import heyong.intellectPinPang.databinding.ActivitySearchLiveListBinding
import heyong.intellectPinPang.databinding.ActivityShopMallBinding
import heyong.intellectPinPang.live.adapter.MyTagHistoryAdapter
import heyong.intellectPinPang.live.model.LiveViewModel
import heyong.intellectPinPang.ui.BaseActivity
import heyong.intellectPinPang.ui.homepage.activity.SearchActivity
import heyong.intellectPinPang.utils.MessageDialogBuilder
import heyong.intellectPinPang.utils.SearchHelper
import java.util.ArrayList

public class SearchLiveGameListActivity:BaseActivity<ActivitySearchLiveListBinding, LiveViewModel>(),
    View.OnClickListener {


    internal lateinit var myTagHistoryAdapter: MyTagHistoryAdapter
    internal var isHistorySearch = false
    val TAG = SearchLiveListActivity::class.java.simpleName
    var instance: SearchActivity? = null


    override fun getLayoutRes(): Int {
        return R.layout.activity_search_live_list
    }

    private fun getHistoryList() {
        val historyList = ArrayList<TagBean>()
        val searchHistory = SearchHelper.getSearchHistory()
        if (searchHistory.size > 0) {
            //            if (searchHistory.size() > 10) {
            //                for (int i = 0; i < 10; i++) {
            //                    historyList.add(new TagBean(i, searchHistory.get(i), false));
            //                }
            //            } else {
            for (i in searchHistory.indices) {
                historyList.add(TagBean(i, searchHistory[i], false))
            }
            //            }
            myTagHistoryAdapter = MyTagHistoryAdapter(this, historyList)
        } else {
            myTagHistoryAdapter = MyTagHistoryAdapter(this, ArrayList())
        }
        mBinding.flowHistorySearchs.setAdapter(myTagHistoryAdapter)
        mBinding.flowHistorySearchs.setOnTagClickListener(TagFlowLayout.OnTagClickListener { view, position, parent ->
            isHistorySearch = true
            if (isSoftShowing()) {
                val imm = getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
                imm.toggleSoftInput(0, InputMethodManager.HIDE_NOT_ALWAYS)//关闭软键盘
            }
            SearchHelper.saveSearchHistory("" + mBinding.query.getText().toString())
            //                mViewModel.title.set("" + searchHistory.get(position));
            mBinding.query.setText("" + searchHistory[position])
            //                mBinding.llSearch.setVisibility(View.GONE);
            //                Intent intent = new Intent(SearchActivity.this, SearchResultActivity.class);
            //                intent.putExtra(SearchResultActivity.SEARCH_CONTENT, mBinding.query.getText().toString());
            //                startActivity(intent);

            val intent = Intent()
            intent.putExtra("textData", "" + searchHistory[position])
            setResult(Activity.RESULT_OK, intent)
            finish()
            false
        })

    }


    private fun isSoftShowing(): Boolean {
        // 获取当前屏幕内容的高度
        val screenHeight = getWindow().getDecorView().getHeight()
        // 获取View可见区域的bottom
        val rect = Rect()
        // DecorView即为activity的顶级view
        getWindow().getDecorView().getWindowVisibleDisplayFrame(rect)
        // 考虑到虚拟导航栏的情况（虚拟导航栏情况下：screenHeight = rect.bottom + 虚拟导航栏高度）
        // 选取screenHeight*2/3进行判断
        return screenHeight * 2 / 3 > rect.bottom
    }

    override fun initView(savedInstanceState: Bundle?) {
        mBinding.setListener(this)

        mBinding.llSearch.setVisibility(View.VISIBLE)
        mBinding.query.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(s: CharSequence, start: Int, count: Int, after: Int) {

            }

            override fun onTextChanged(s: CharSequence, start: Int, before: Int, count: Int) {

            }

            override fun afterTextChanged(s: Editable) {
                if (s.length == 0) {
                    getHistoryList()
                    mBinding.ivClose.setVisibility(View.GONE)
                    mBinding.tvSearch.setText("取消")
                    mBinding.llSearch.setVisibility(View.VISIBLE)
                } else {
                    mBinding.tvSearch.setText("搜索")
                }
                if (s.length > 0) {
                    mBinding.ivClose.setVisibility(View.VISIBLE)
                }
            }
        })
        mBinding.tvSearch.setOnClickListener(View.OnClickListener {
            if (!TextUtils.isEmpty(mBinding.query.getText().toString())) {
                val imm = getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
                imm.toggleSoftInput(0, InputMethodManager.HIDE_NOT_ALWAYS)//关闭软键盘
                //                mViewModel.title.set("" + mBinding.query.getText().toString());
                SearchHelper.saveSearchHistory("" + mBinding.query.getText().toString())
//                LiveSearchNewListActivity.goActivity(
//                    this,
//                    "" + mBinding.query.getText().toString()
//                )
                val intent = Intent()
                intent.putExtra("textData", "" + mBinding.query.getText().toString())
                setResult(Activity.RESULT_OK, intent)
                finish()
            } else {
                finish()
            }
        })
        mBinding.query.setOnFocusChangeListener(View.OnFocusChangeListener { v, hasFocus ->
            if (!isHistorySearch) {
                if (hasFocus) {
                    //                    /*有焦点*/
                    getHistoryList()
                    //                        mBinding.llSearch.setVisibility(View.VISIBLE);
                } else {
                    /*没焦点*/
                    //                        mBinding.llSearch.setVisibility(View.GONE);
                }
            }
        })

        mBinding.query.setOnEditorActionListener({ v, actionId, event ->
            if (actionId == EditorInfo.IME_ACTION_SEARCH) {
                val imm = getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
                imm.toggleSoftInput(0, InputMethodManager.HIDE_NOT_ALWAYS)//关闭软键盘
                SearchHelper.saveSearchHistory("" + mBinding.query.getText().toString())
                //                mViewModel.title.set("" + mBinding.query.getText().toString());
                //                helper.start();
                val intent = Intent()
                intent.putExtra("textData", "" + mBinding.query.getText().toString())
                setResult(Activity.RESULT_OK, intent)
                finish()
                true
            }
            false
        })
    }

    override fun onClick(v: View) {


        if (v.id == R.id.iv_close) {
            mBinding.query.setText("")
            val imm = getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
            imm.toggleSoftInput(0, InputMethodManager.HIDE_NOT_ALWAYS)//关闭软键盘
            mBinding.ivClose.setVisibility(View.GONE)
            getHistoryList()
        }

        if (v.id == R.id.iv_clear_search) {
            MessageDialogBuilder(this)
                .setTvTitle("是否要删除历史记录?")
                .setMessage("")
                .setTvSure("确定")
                .setSureListener { v1 -> clearSearch() }
                .show()
        }
    }

    fun clearSearch() {
        SearchHelper.clearSearchHistory()
        getHistoryList()

    }





}