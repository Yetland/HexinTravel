package com.yetland.crazy.bundle.user.mine

import android.content.Intent
import android.os.Bundle
import com.google.gson.Gson
import com.yetland.crazy.bundle.main.contract.MainContract
import com.yetland.crazy.bundle.main.contract.MainModel
import com.yetland.crazy.bundle.main.contract.MainPresent
import com.yetland.crazy.core.base.BaseActivity
import com.yetland.crazy.core.base.RecyclerViewListener
import com.yetland.crazy.core.constant.IntentResultCode
import com.yetland.crazy.core.entity.ActivityInfo
import com.yetland.crazy.core.entity.BaseEntity
import com.yetland.crazy.core.entity.Data
import com.yetland.crazy.core.entity.Point
import com.yetland.crazy.core.utils.LogUtils
import com.ynchinamobile.hexinlvxing.R
import kotlinx.android.synthetic.main.activity_mine.*

class MineActivity : BaseActivity(), MainContract.View, RecyclerViewListener {

    val map = HashMap<String, Any>()
    val presenter = MainPresent(MainModel(), this)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_mine)

        supportActionBar?.title = "MyActivity"
        supportActionBar?.setDisplayHomeAsUpEnabled(true)

        rvMyActivity.initView(activity)
        rvMyActivity.recyclerViewListener = this
        rvMyActivity.onLoading()

        if (!isUserExist(currentLoginUser)) {
            rvMyActivity.onLoadError("User is null , try to logout then login")
            rvMyActivity.isErrorClickable = false
        } else {
            map.put("creator", Point("_User", currentLoginUser.objectId))
            getActivities(Gson().toJson(map), currentPage)
        }
    }

    override fun onRefresh() {
        currentPage = 0
        getActivities(Gson().toJson(map), currentPage)
    }

    override fun onLoadMore() {
        currentPage++
        getActivities(Gson().toJson(map), currentPage)
    }

    override fun onErrorClick() {
        rvMyActivity.onLoading()
        currentPage = 0
        getActivities(Gson().toJson(map), currentPage)
    }

    override fun getActivities(where: String?, skip: Int) {
        presenter.getActivities(where, skip)
    }

    override fun onLoading(msg: String) {
    }

    override fun onError(msg: String) {
        rvMyActivity.onLoadError(msg)
    }

    override fun onComplete(activityModel: Data<ActivityInfo>) {

        val list = ArrayList<BaseEntity>()
        list.addAll(activityModel.results!!)
        rvMyActivity.onDefaultComplete(list, currentPage)
    }


    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        LogUtils.e("resultCode = $resultCode")
        when (resultCode) {
            IntentResultCode.MAIN_TO_DETAIL_RESULT -> {
                val bundle = data?.extras
                val activityInfo: ActivityInfo = bundle?.getSerializable("activityInfo") as ActivityInfo
                val position = bundle.getInt("position")

                if (rvMyActivity.canLoadMore) {
                    if (position + 1 <= rvMyActivity.adapter.mList.size) {
                        rvMyActivity.adapter.mList[position] = activityInfo
                        rvMyActivity.adapter.notifyDataSetChanged()
                    }
                } else {
                    if (position + 2 <= rvMyActivity.adapter.mList.size) {
                        rvMyActivity.adapter.mList[position] = activityInfo
                        rvMyActivity.adapter.notifyDataSetChanged()
                    }
                }
            }
            IntentResultCode.ACTIVITY_DELETE -> {
                val position = data?.getIntExtra("position", 0)
                if (position != null) {
                    rvMyActivity.adapter.mList.removeAt(position)
                    rvMyActivity.adapter.notifyDataSetChanged()
                }
            }
        }
        super.onActivityResult(requestCode, resultCode, data)
    }
}
