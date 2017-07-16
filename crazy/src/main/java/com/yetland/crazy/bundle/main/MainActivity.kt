package com.yetland.crazy.bundle.main

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.Menu
import android.view.MenuItem
import com.yetland.crazy.bundle.main.contract.MainContract
import com.yetland.crazy.bundle.main.contract.MainModel
import com.yetland.crazy.bundle.main.contract.MainPresent
import com.yetland.crazy.bundle.user.UserDataActivity
import com.yetland.crazy.bundle.user.login.LoginActivity
import com.yetland.crazy.core.base.BaseActivity
import com.yetland.crazy.core.base.BaseRecyclerView
import com.yetland.crazy.core.base.RecyclerViewListener
import com.yetland.crazy.core.constant.IntentRequestCode
import com.yetland.crazy.core.constant.IntentResultCode
import com.yetland.crazy.core.entity.ActivityInfo
import com.yetland.crazy.core.entity.BaseEntity
import com.yetland.crazy.core.entity.Data
import com.yetland.crazy.core.utils.FileUtil
import com.yetland.crazy.core.utils.makeShortToast
import com.ynchinamobile.hexinlvxing.R

class MainActivity : BaseActivity(), MainContract.View, RecyclerViewListener {

    var mainModel = MainModel()
    var mainPresent = MainPresent(mainModel, this)
    lateinit var rvList: BaseRecyclerView
    val map = HashMap<String, String>()


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        supportActionBar?.title = "Main"
        rvList = findViewById(R.id.rv_list)
        rvList.initView(this)
        rvList.recyclerViewListener = this
        onLoading("Loading")
        getActivities(null, currentPage)
    }

    override fun onLoading(msg: String) {
        rvList.onLoading()
    }

    override fun onError(msg: String) {
        rvList.onLoadError(msg)
    }


    override fun onComplete(activityModel: Data<ActivityInfo>) {

        Log.e("MainActivity", "onComplete")
        val list = ArrayList<BaseEntity>()
        list.addAll(activityModel.results!!)
        rvList.onDefaultComplete(list, currentPage)
    }

    override fun onRefresh() {
        currentPage = 0
        getActivities(null, currentPage)
    }

    override fun onLoadMore() {
        currentPage++
        Log.e("MainActivity", "onLoadMore")
        getActivities(null, currentPage)
    }

    override fun onErrorClick() {
        onLoading("Loading")
        currentPage = 0
        getActivities(null, currentPage)
    }

    override fun getActivities(where: String?, skip: Int) {
        mainPresent.getActivities(where, currentPage)
    }


    override fun onPrepareOptionsMenu(menu: Menu): Boolean {
        return super.onPrepareOptionsMenu(menu)
    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        menuInflater.inflate(R.menu.main, menu)
        return super.onCreateOptionsMenu(menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            R.id.menu_user -> {
                val user = FileUtil().getUserInfo(activity)
                if (user.username!!.isNotEmpty()) {
                    val intent = Intent(activity, UserDataActivity::class.java)
                    startActivityForResult(intent, IntentRequestCode.MAIN_TO_USER_DATA)
                } else {
                    val intent = Intent(activity, LoginActivity::class.java)
                    startActivityForResult(intent, IntentRequestCode.MAIN_TO_LOGIN)
                }
            }
            R.id.menu_search -> {
                makeShortToast(activity, "Search")
            }
        }
        return super.onOptionsItemSelected(item)
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        Log.e("MainActivity", "resultCode = $resultCode")
        when (resultCode) {
            IntentResultCode.LOG_OUT -> {
                rvList.adapter.notifyDataSetChanged()
            }
            IntentResultCode.LOG_IN -> {
                rvList.adapter.notifyDataSetChanged()
            }
            IntentResultCode.MAIN_TO_DETAIL_RESULT -> {
                val bundle = data?.extras
                val activityInfo: ActivityInfo = bundle?.getSerializable("activityInfo") as ActivityInfo
                val position = bundle.getInt("position")

                if (rvList.canLoadMore) {
                    if (position + 1 <= rvList.adapter.mList.size) {
                        rvList.adapter.mList[position] = activityInfo
                        rvList.adapter.notifyDataSetChanged()
                    }
                } else {
                    if (position + 2 <= rvList.adapter.mList.size) {
                        rvList.adapter.mList[position] = activityInfo
                        rvList.adapter.notifyDataSetChanged()
                    }
                }
            }
        }
        super.onActivityResult(requestCode, resultCode, data)
    }
}
