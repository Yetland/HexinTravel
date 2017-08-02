package com.yetland.crazy.bundle.user

import android.content.Intent
import android.os.Bundle
import android.text.TextUtils
import android.view.Menu
import android.view.MenuInflater
import android.view.View
import com.squareup.picasso.Picasso
import com.yetland.crazy.bundle.user.contract.UserDataContract
import com.yetland.crazy.bundle.user.contract.UserDataModel
import com.yetland.crazy.bundle.user.contract.UserDataPresenter
import com.yetland.crazy.bundle.user.mine.MineActivity
import com.yetland.crazy.bundle.user.mine.MineCommentActivity
import com.yetland.crazy.core.base.BaseActivity
import com.yetland.crazy.core.constant.IntentResultCode
import com.yetland.crazy.core.constant.SharedPreferencesConstant
import com.yetland.crazy.core.entity._User
import com.yetland.crazy.core.utils.SharedPreferencesUtils
import com.yetland.crazy.core.utils.ToastUtils
import com.ynchinamobile.hexinlvxing.R
import kotlinx.android.synthetic.main.activity_user_data.*

class UserDataActivity : BaseActivity(), View.OnClickListener, UserDataContract.View {
    override fun updateUser(user: _User, map: HashMap<String, Any>) {}

    override fun updateUserSuccess() {}

    override fun updateUserFailed(msg: String) {}

    val presenter = UserDataPresenter(UserDataModel(), this)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_user_data)

        setSupportActionBar(toolbar2)
        supportActionBar?.title = "Me"
        supportActionBar?.setDisplayHomeAsUpEnabled(true)

        llUser.setOnClickListener(this)
        llLogOut.setOnClickListener(this)
        llMyFollowee.setOnClickListener(this)
        llMyFollower.setOnClickListener(this)
        llMyComment.setOnClickListener(this)
        llMyActivity.setOnClickListener(this)

        setUserData(currentLoginUser)

        srlUserData.isRefreshing = true
        srlUserData.setOnRefreshListener {
            getUser(currentLoginUser.objectId)
        }
        getUser(currentLoginUser.objectId)

        ivAdd.setOnClickListener({
            startActivity(Intent(this, CreateActivity::class.java))
        })
    }

    override fun onClick(view: View) {
        when (view.id) {
            R.id.llUser -> {
                val intent = Intent(activity, UserDetailActivity::class.java)
                val bundle = Bundle()
                bundle.putSerializable("showUser", currentLoginUser)
                intent.putExtras(bundle)
                startActivity(intent)
            }
            R.id.llMyComment -> {
                startActivity(Intent(activity, MineCommentActivity::class.java))
            }
            R.id.llMyActivity -> {
                startActivity(Intent(activity, MineActivity::class.java))
            }
            R.id.llMyFollower -> {
                val intent = Intent(activity, FollowActivity::class.java)
                intent.putExtra("isFollower", true)
                startActivity(intent)
            }
            R.id.llMyFollowee -> {
                val intent = Intent(activity, FollowActivity::class.java)
                intent.putExtra("isFollower", false)
                startActivity(intent)
            }
            R.id.llLogOut -> {
                SharedPreferencesUtils.cleanData(SharedPreferencesConstant.PREF_NAME)
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
                    .placeholder(R.mipmap.image_default)
                    .into(ivAvatar)
        }
    }

    override fun getUser(objectId: String) {
        presenter.getUser(objectId)
    }

    override fun getUserFailed(msg: String) {
        srlUserData.isRefreshing = false
        ToastUtils.showShortSafe(msg)
    }

    override fun getUserSuccess(user: _User) {
        srlUserData.isRefreshing = false
        SharedPreferencesUtils.saveUserInfo(user)
        setUserData(user)
    }

    override fun onDataChanged() {
        super.onDataChanged()
        setUserData(currentLoginUser)
    }
}
