package com.yetland.crazy.bundle.main

import android.content.Intent
import android.os.Bundle
import android.support.design.widget.BottomNavigationView
import android.support.v4.app.Fragment
import android.view.KeyEvent
import com.yetland.crazy.bundle.main.adapter.MainAdapter
import com.yetland.crazy.bundle.main.fragment.MainFragment
import com.yetland.crazy.bundle.main.fragment.MeFragment
import com.yetland.crazy.bundle.main.fragment.SearchFragment
import com.yetland.crazy.core.base.BaseActivity
import com.yetland.crazy.core.constant.IntentResultCode
import com.yetland.crazy.core.entity.ActivityInfo
import com.yetland.crazy.core.utils.LogUtils
import com.yetland.crazy.core.utils.ToastUtils
import com.ynchinamobile.hexinlvxing.R
import kotlinx.android.synthetic.main.activity_main.*


class MainActivity : BaseActivity() {

    lateinit var adapter: MainAdapter
    val mainFragment = MainFragment()
    val searchFragment = SearchFragment()
    val meFragment = MeFragment()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        navigation.setOnNavigationItemSelectedListener(mOnNavigationItemSelectedListener)
        vpMain.setScroll(false)
        val list = ArrayList<Fragment>()

        list.add(0, mainFragment)
        list.add(1, searchFragment)
        list.add(2, meFragment)

        adapter = MainAdapter(supportFragmentManager, list)
        vpMain.adapter = adapter

    }

    private val mOnNavigationItemSelectedListener = BottomNavigationView.OnNavigationItemSelectedListener { item ->
        when (item.itemId) {
            R.id.navigation_home -> {
                vpMain.setCurrentItem(0, false)
                return@OnNavigationItemSelectedListener true
            }
            R.id.navigation_dashboard -> {
                vpMain.setCurrentItem(1, false)
                return@OnNavigationItemSelectedListener true
            }
            R.id.navigation_notifications -> {
                vpMain.setCurrentItem(2, false)
                return@OnNavigationItemSelectedListener true
            }
        }
        false
    }

    var exitTime: Long = 0
    override fun onKeyDown(keyCode: Int, event: KeyEvent): Boolean {
        if (keyCode == KeyEvent.KEYCODE_BACK && event.repeatCount == 0) {
            if (System.currentTimeMillis() - exitTime > 2000) {
                ToastUtils.showShortSafe("再按一次退出")
                exitTime = System.currentTimeMillis()
            } else {
                finish()
            }
            return true
        }
        return super.onKeyDown(keyCode, event)
    }


    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        LogUtils.e("resultCode = $resultCode")
        when (resultCode) {
            IntentResultCode.LOG_OUT -> {
                mainFragment.onDataChanged()
            }
            IntentResultCode.LOG_IN -> {
                mainFragment.onDataChanged()
            }
            IntentResultCode.MAIN_TO_DETAIL_RESULT -> {
                val bundle = data?.extras
                val activityInfo: ActivityInfo = bundle?.getSerializable("activityInfo") as ActivityInfo
                val position = bundle.getInt("position")
                mainFragment.onItemChanged(position, activityInfo)
            }
            IntentResultCode.ACTIVITY_DELETE -> {
                val position = data?.getIntExtra("position", 0)
                if (position != null)
                    mainFragment.onItemDelete(position)
            }
        }
        super.onActivityResult(requestCode, resultCode, data)
    }

}
