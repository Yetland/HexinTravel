package com.yetland.crazy.bundle.user.detail

import android.content.Intent
import android.os.Bundle
import android.support.design.widget.TabLayout
import android.support.v4.app.Fragment
import android.support.v4.view.ViewPager
import android.support.v7.widget.Toolbar
import android.view.View
import android.widget.ImageView
import com.google.gson.Gson
import com.squareup.picasso.Picasso
import com.yetland.crazy.bundle.user.UserAvatarActivity
import com.yetland.crazy.bundle.user.contract.FollowContract
import com.yetland.crazy.bundle.user.contract.FollowModel
import com.yetland.crazy.bundle.user.contract.FollowPresenter
import com.yetland.crazy.core.base.BaseActivity
import com.yetland.crazy.core.constant.SharedPreferencesConstant
import com.yetland.crazy.core.entity.*
import com.yetland.crazy.core.utils.SharedPreferencesUtils
import com.yetland.crazy.core.utils.ToastUtils
import com.yetland.crazy.core.widget.CircleImageView
import com.ynchinamobile.hexinlvxing.R

class UserDetailActivity : BaseActivity(), FollowContract.View {

    lateinit var toolBar: Toolbar
    lateinit var tabLayout: TabLayout
    lateinit var viewPager: ViewPager
    lateinit var ivAvatar: CircleImageView
    lateinit var ivFollow: ImageView
    lateinit var adapter: UserDetailAdapter

    lateinit var currentShowUser: _User

    val fragment = UserDetailTabFragment()
    val fragment2 = UserDetailTabFragment()
    val fragment3 = UserDetailTabFragment()

    var presenter = FollowPresenter(FollowModel(), this)

    var isMe = false
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_user_detail)

        toolBar = findViewById(R.id.toolbar)
        tabLayout = findViewById<TabLayout>(R.id.tabLayout)
        viewPager = findViewById(R.id.viewPager)
        ivAvatar = findViewById(R.id.iv_user_detail_avatar)
        ivFollow = findViewById(R.id.iv_user_detail_follow)

        setSupportActionBar(toolBar)
        supportActionBar?.title = "UserDetail"
        supportActionBar?.setDisplayHomeAsUpEnabled(true)

        val bundle = intent.extras
        if (bundle != null) {
            currentShowUser = bundle.getSerializable("showUser") as _User
        }

        if (!isUserExist(currentShowUser)) {
            ToastUtils.showShortSafe("User not exist")
            finish()
        }

        dealFollow()
        setUserData()


        val titleList = arrayListOf("Follower", "Followee", "Activity")
        val list = ArrayList<Fragment>()

        fragment.openType = fragment.TAB_FOLLOWER
        fragment2.openType = fragment.TAB_FOLLOWEE
        fragment3.openType = fragment.TAB_ACTIVITY

        fragment.currentUser = currentShowUser
        fragment2.currentUser = currentShowUser
        fragment3.currentUser = currentShowUser

        list.add(fragment)
        list.add(fragment2)
        list.add(fragment3)

        adapter = UserDetailAdapter(supportFragmentManager, list, titleList)

        viewPager.adapter = adapter
        tabLayout.setupWithViewPager(viewPager)
    }

    private fun setUserData() {
        supportActionBar?.title = currentShowUser.username

        if (isMe)
            ivAvatar.setOnClickListener({
                startActivity(Intent(activity, UserAvatarActivity::class.java))
            })

        if (currentShowUser.avatarUrl != null) {
            Picasso.with(activity)
                    .load(currentShowUser.avatarUrl)
                    .placeholder(R.mipmap.image_load_1_1)
                    .into(ivAvatar)
        }
    }

    override fun onDataChanged() {
        if (isUserChanged && isMe) {
            currentShowUser = currentLoginUser
            setUserData()
        }
        dealFollow()
    }

    private fun dealFollow() {
        ivFollow.visibility = View.VISIBLE

        if (isLogin) {
            if (currentLoginUser.objectId == currentShowUser.objectId) {
                isMe = true
                ivFollow.visibility = View.GONE
            }
            val followId = SharedPreferencesUtils.getFollowList()
            if (followId.contains(currentShowUser.objectId)) {
                ivFollow.visibility = View.GONE
            }
        }
        ivFollow.setOnClickListener({
            if (!isLogin) {
                showLogin()
            } else {
                val commitFollow = CommitFollow()
                commitFollow.follower = Point("_User", currentLoginUser.objectId)
                commitFollow.user = Point("_User", currentShowUser.objectId)
                follow(commitFollow)
            }
        })
    }

    override fun getFollower(map: HashMap<String, Any>, page: Int) {

    }

    override fun follow(follow: CommitFollow) {
        progressDialog.setContent("Following...")
        progressDialog.show()
        presenter.follow(follow)
    }

    override fun unFollow(objectId: String) {
    }

    override fun getFollowerSuccess(data: Data<Follow>) {
    }

    override fun followSuccess(follow: Follow) {
        progressDialog.dismiss()

        val followList = SharedPreferencesUtils.getFollowLists()
        val followId = SharedPreferencesUtils.getFollowList()
        if (!followId.contains(follow.user.objectId)) {
            followList.add(follow)
            SharedPreferencesUtils.saveString(SharedPreferencesConstant.KEY_FOLLOWER_LIST, Gson().toJson(followList))
        }
        dealFollow()
    }

    override fun unFollowSuccess() {
    }

    override fun getFollowerFailed(msg: String) {
    }

    override fun followFailed(msg: String) {
        progressDialog.dismiss()
        ToastUtils.showShortSafe(msg)
    }

    override fun unFollowFailed(msg: String) {
    }

}
