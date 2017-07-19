package com.yetland.crazy.bundle.user

import android.content.Intent
import android.os.Bundle
import android.support.v4.widget.SwipeRefreshLayout
import android.text.TextUtils
import android.view.Menu
import android.view.MenuInflater
import android.view.View
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.TextView
import com.squareup.picasso.Picasso
import com.yetland.crazy.bundle.user.contract.UserDataContract
import com.yetland.crazy.bundle.user.contract.UserDataModel
import com.yetland.crazy.bundle.user.contract.UserDataPresenter
import com.yetland.crazy.bundle.user.mine.MineActivity
import com.yetland.crazy.bundle.user.mine.MineCommentActivity
import com.yetland.crazy.core.base.BaseActivity
import com.yetland.crazy.core.constant.IntentResultCode
import com.yetland.crazy.core.entity._User
import com.yetland.crazy.core.utils.SharedPreferencesUtils
import com.yetland.crazy.core.utils.ToastUtils
import com.ynchinamobile.hexinlvxing.R

class UserDataActivity : BaseActivity(), View.OnClickListener, UserDataContract.View {


    lateinit var llUser: LinearLayout
    lateinit var ivAvatar: ImageView
    lateinit var tvUsername: TextView
    lateinit var tvEmail: TextView
    lateinit var llMyActivity: LinearLayout
    lateinit var llMyComment: LinearLayout
    lateinit var llMyFollower: LinearLayout
    lateinit var llMyFollowee: LinearLayout
    lateinit var llLogOut: LinearLayout

    lateinit var refreshLayout: SwipeRefreshLayout

    val model = UserDataModel()
    val presenter = UserDataPresenter(model, this)

    lateinit var user: _User
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_user_data)
        user = SharedPreferencesUtils.getUserInfo(activity)
        supportActionBar?.title = "Me"
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        llUser = findViewById(R.id.ll_user)
        ivAvatar = findViewById(R.id.iv_avatar)
        tvUsername = findViewById(R.id.tv_username)
        tvEmail = findViewById(R.id.tv_email)
        llMyActivity = findViewById(R.id.ll_my_activity)
        llMyComment = findViewById(R.id.ll_my_comment)

        llMyFollower = findViewById(R.id.ll_my_follower)
        llMyFollowee = findViewById(R.id.ll_my_followee)
        llLogOut = findViewById(R.id.ll_log_out)

        llUser.setOnClickListener(this)
        llLogOut.setOnClickListener(this)
        llMyFollowee.setOnClickListener(this)
        llMyFollower.setOnClickListener(this)
        llMyComment.setOnClickListener(this)
        llMyActivity.setOnClickListener(this)

        refreshLayout = findViewById(R.id.srl_user_data)
        setUserData(user)

        refreshLayout.isRefreshing = true
        refreshLayout.setOnRefreshListener {
            getUser(user.objectId)
        }
        getUser(user.objectId)
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        MenuInflater(activity).inflate(R.menu.menu_user, menu)
        return super.onCreateOptionsMenu(menu)
    }

    override fun onClick(view: View) {
        when (view.id) {
            R.id.ll_user -> {
                startActivity(Intent(activity, UserDetailActivity::class.java))
            }
            R.id.ll_my_comment -> {
                startActivity(Intent(activity, MineCommentActivity::class.java))
            }
            R.id.ll_my_activity -> {
                startActivity(Intent(activity, MineActivity::class.java))
            }
            R.id.ll_my_follower -> {
                val intent = Intent(activity, FollowActivity::class.java)
                intent.putExtra("isFollower", true)
                startActivity(intent)
            }
            R.id.ll_my_followee -> {
                val intent = Intent(activity, FollowActivity::class.java)
                intent.putExtra("isFollower", false)
                startActivity(intent)
            }
            R.id.ll_log_out -> {
                SharedPreferencesUtils.clearUserInfo(activity)
                setResult(IntentResultCode.LOG_OUT)
                finish()
            }
        }
    }

    fun setUserData(user: _User) {

        if (!TextUtils.isEmpty(user.username)) {
            tvUsername.text = user.username
        }
        if (TextUtils.isEmpty(user.email)) {
            tvEmail.visibility = View.GONE
        } else {
            tvEmail.visibility = View.VISIBLE
            tvEmail.text = user.email
        }
        if (!TextUtils.isEmpty(user.avatarUrl)) {
            Picasso.with(activity)
                    .load(user.avatarUrl)
                    .into(ivAvatar)
        }
    }

    override fun getUser(objectId: String) {
        presenter.getUser(objectId)
    }

    override fun getUserFailed(msg: String) {
        refreshLayout.isRefreshing = false
        ToastUtils.showShortSafe(msg)
    }

    override fun getUserSuccess(user: _User) {
        refreshLayout.isRefreshing = false
        SharedPreferencesUtils.saveUserInfo(activity, user)
        this.user = user
        setUserData(user)
    }
}
