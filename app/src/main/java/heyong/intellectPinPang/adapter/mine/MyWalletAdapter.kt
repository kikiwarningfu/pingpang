package heyong.intellectPinPang.adapter.mine

import android.graphics.Color
import android.view.View
import androidx.databinding.DataBindingUtil
import com.chad.library.adapter.base.BaseQuickAdapter
import com.chad.library.adapter.base.BaseViewHolder
import heyong.intellectPinPang.R
import heyong.intellectPinPang.bean.live.MoneyDetailListBean
import heyong.intellectPinPang.databinding.ItemMyWalletBinding

//我的钱包
class MyWalletAdapter :
    BaseQuickAdapter<MoneyDetailListBean.ListBean, BaseViewHolder>(R.layout.item_my_wallet) {

    override fun convert(helper: BaseViewHolder, item: MoneyDetailListBean.ListBean) {

        val binding = DataBindingUtil.bind<ItemMyWalletBinding>(helper.itemView)

//        tv_wallet_status
        //1直播收入2微信提现3支付宝提现  detailType

        //0失败1成功2待审核    status

        //	1收入2支出    moneyType

        //提现审核中，预计1-3个工作日到账

        var text = ""
        if (item.detailType.equals("1")) {
            text = "直播收益"
        } else if (item.detailType.equals("2")) {
            text = "提现到微信"
        } else if (item.detailType.equals("3")) {
            text = "提现到支付宝"
        }
        binding!!.tvTime.text=""+item.endTime.substring(0,11)
        when(item.moneyType)
        {
            "1"->{
                //收入
                binding!!.tvMoney.text="+"+item.money
                binding.tvMoney.setTextColor(Color.parseColor("#F33E3E"))
            }
            "2"->{
                //支出
                binding!!.tvMoney.text="+"+item.money
                binding.tvMoney.setTextColor(Color.parseColor("#333333"))

            }
        }
        when (item.status) {
            "0" -> {
                //失败
                binding!!.tvWalletStatus.setTextColor(Color.parseColor("#F33E3E"))
                binding.tvWalletStatus.text = "" + text + "- 失败"
                binding!!.ivFailLogo.visibility = View.VISIBLE
                helper.addOnClickListener(R.id.iv_fail_logo)
                when(item.moneyType)
                {
                    "1"->{
                        //收入
                        binding!!.tvMoney.text=""+item.money
                        binding.tvMoney.setTextColor(Color.parseColor("#F33E3E"))
                    }
                    "2"->{
                        //支出
                        binding!!.tvMoney.text=""+item.money
                        binding.tvMoney.setTextColor(Color.parseColor("#333333"))

                    }
                }
            }
            "1" -> {
                //成功
                binding!!.tvWalletStatus.setTextColor(Color.parseColor("#333333"))
                binding.tvWalletStatus.text = "" + text
                binding!!.ivFailLogo.visibility = View.GONE
            }
            "2" -> {
                //待审核
                binding!!.tvWalletStatus.setTextColor(Color.parseColor("#ffa400"))
                binding.tvWalletStatus.text = "" + text + "- 审核中"
                binding!!.ivFailLogo.visibility = View.VISIBLE
                helper.addOnClickListener(R.id.iv_fail_logo)
            }
        }


    }


}