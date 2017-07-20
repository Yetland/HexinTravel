package com.yetland.crazy.bundle.user

import android.content.Context
import android.content.SharedPreferences
import android.os.Bundle
import android.support.design.widget.TabLayout
import android.support.v4.app.Fragment
import android.support.v4.view.PagerAdapter
import android.support.v4.view.ViewPager
import android.support.v7.widget.Toolbar
import android.util.DisplayMetrics
import android.view.WindowManager
import com.squareup.picasso.Picasso
import com.yetland.crazy.core.base.BaseActivity
import com.yetland.crazy.core.entity._User
import com.yetland.crazy.core.utils.SharedPreferencesUtils
import com.yetland.crazy.core.widget.CircleImageView
import com.ynchinamobile.hexinlvxing.R
import kotlinx.android.synthetic.main.activity_user_detail.*
import kotlinx.android.synthetic.main.item_follower.*

class UserDetailActivity : BaseActivity() {

    lateinit var toolBar: Toolbar
    lateinit var tabLayout: TabLayout
    lateinit var viewPager: ViewPager
    lateinit var ivAvatar: CircleImageView

    lateinit var adapter: UserDetailAdapter

    lateinit var currentUser: _User
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_user_detail)

        toolBar = findViewById(R.id.toolbar)
        setSupportActionBar(toolBar)
        supportActionBar?.title = "UserDetail"
        supportActionBar?.setDisplayHomeAsUpEnabled(true)

        val bundle = intent.extras
        if (bundle != null) {
            currentUser = bundle.getSerializable("showUser") as _User
        }

        if (currentUser.objectId.isEmpty()) {
            currentUser = SharedPreferencesUtils.getUserInfo(activity)
        }
        supportActionBar?.title = currentUser.username

        tabLayout = findViewById(R.id.tabLayout)
        viewPager = findViewById(R.id.viewPager)
        ivAvatar = findViewById(R.id.iv_user_detail_avatar)

        if (currentUser.avatarUrl != null) {
            Picasso.with(activity)
                    .load(currentUser.avatarUrl)
                    .placeholder(R.mipmap.huas)
                    .into(ivAvatar)
        }
        val titleList = arrayListOf("Follower", "Followee", "Activity")
        val list = ArrayList<Fragment>()
        val fragment = UserDetailTabFragment()
        val fragment2 = UserDetailTabFragment()
        val fragment3 = UserDetailTabFragment()

        fragment.openType = fragment.TAB_FOLLOWER
        fragment2.openType = fragment.TAB_FOLLOWEE
        fragment3.openType = fragment.TAB_ACTIVITY

        fragment.currentUser = currentUser
        fragment2.currentUser = currentUser
        fragment3.currentUser = currentUser

        list.add(fragment)
        list.add(fragment2)
        list.add(fragment3)

        adapter = UserDetailAdapter(supportFragmentManager, list, titleList)

        viewPager.adapter = adapter
        tabLayout.setupWithViewPager(viewPager)
    }
}
