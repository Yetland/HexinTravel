package com.yetland.crazy.bundle.main

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.Menu
import android.view.MenuItem
import com.yetland.crazy.bundle.destination.bean.Footer
import com.yetland.crazy.bundle.main.contract.MainContract
import com.yetland.crazy.bundle.main.contract.MainModel
import com.yetland.crazy.bundle.main.contract.MainPresent
import com.yetland.crazy.bundle.user.UserDataActivity
import com.yetland.crazy.bundle.user.login.LoginActivity
import com.yetland.crazy.core.base.BaseActivity
import com.yetland.crazy.core.base.BaseRecyclerView
import com.yetland.crazy.core.base.RecyclerViewListener
import com.yetland.crazy.core.constant.IntentRequestCode
import com.yetland.crazy.core.entity.ActivityInfo
import com.yetland.crazy.core.entity.BaseEntity
import com.yetland.crazy.core.entity.Data
import com.yetland.crazy.core.entity._User
import com.yetland.crazy.core.utils.FileUtil
import com.yetland.crazy.core.utils.makeShortToast
import com.ynchinamobile.hexinlvxing.R

class MainActivity : BaseActivity(), MainContract.View, RecyclerViewListener {

    var mainModel = MainModel()
    var mainPresent = MainPresent(mainModel, this)
    var list = ArrayList<BaseEntity>()
    lateinit var rvList: BaseRecyclerView
    var user = _User()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        user = FileUtil().getUserInfo(activity)
        supportActionBar?.title = "Main"
        rvList = findViewById(R.id.rv_list)
        rvList.initView(this)
        rvList.recyclerViewListener = this
        onLoading("Loading")
        getActivities(currentPage)
    }

    override fun onLoading(msg: String) {
        rvList.onLoading()
    }

    override fun onError(msg: String) {
        rvList.onLoadError(msg)
    }


    override fun onComplete(activityModel: Data<ActivityInfo>) {

        Log.e("MainActivity", "onComplete")

        val results = activityModel.results!!

        if (results.size == 0) {
            if (currentPage == 0) {
                list = ArrayList()
                val footer = Footer()
                footer.noMore = true
                list.add(footer)
                rvList.onComplete(list, true)
            } else {
                list.removeAt(list.size - 1)
                val footer = Footer()
                footer.noMore = true
                list.add(footer)
                rvList.onComplete(list, true)
            }
        } else {
            if (currentPage == 0) {
                list = ArrayList<BaseEntity>()
            } else {
                list.removeAt(list.size - 1)
            }
            list.addAll(results)
            rvList.onComplete(list)
        }
    }

    override fun onRefresh() {
        currentPage = 0
        getActivities(currentPage)
    }

    override fun onLoadMore() {
        currentPage++
        Log.e("MainActivity", "onLoadMore")
        rvList.adapter.mList.add(Footer())
        rvList.adapter.notifyDataSetChanged()
        getActivities(currentPage)
    }

    override fun onErrorClick() {
        onLoading("Loading")
        currentPage = 0
        getActivities(currentPage)
    }

    override fun getActivities(skip: Int) {
        mainPresent.getActivities(currentPage)
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
        super.onActivityResult(requestCode, resultCode, data)
    }
}
