package com.yetland.crazy.bundle.main.fragment

import android.content.Intent
import android.os.Bundle
import android.support.v4.widget.SwipeRefreshLayout
import android.text.TextUtils
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.TextView
import com.squareup.picasso.Picasso
import com.yetland.crazy.bundle.user.CreateActivity
import com.yetland.crazy.bundle.user.FollowActivity
import com.yetland.crazy.bundle.user.contract.UserDataContract
import com.yetland.crazy.bundle.user.contract.UserDataModel
import com.yetland.crazy.bundle.user.contract.UserDataPresenter
import com.yetland.crazy.bundle.user.detail.UserDetailActivity
import com.yetland.crazy.bundle.user.login.LoginActivity
import com.yetland.crazy.bundle.user.mine.MineActivity
import com.yetland.crazy.bundle.user.mine.MineCommentActivity
import com.yetland.crazy.bundle.user.register.RegisterActivity
import com.yetland.crazy.core.base.BaseFragment
import com.yetland.crazy.core.constant.IntentRequestCode
import com.yetland.crazy.core.constant.SharedPreferencesConstant
import com.yetland.crazy.core.entity._User
import com.yetland.crazy.core.utils.SharedPreferencesUtils
import com.yetland.crazy.core.utils.ToastUtils
import com.ynchinamobile.hexinlvxing.R
import kotlinx.android.synthetic.main.fragment_me.view.*
import kotlinx.android.synthetic.main.include_user_not_login.view.*

class MeFragment : BaseFragment(), View.OnClickListener, UserDataContract.View {

    override fun updateUser(user: _User, map: HashMap<String, Any>) {}

    override fun updateUserSuccess() {}

    override fun updateUserFailed(msg: String) {}

    val presenter = UserDataPresenter(UserDataModel(), this)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    lateinit var srlUserData: SwipeRefreshLayout
    lateinit var llUserHasLogin: LinearLayout
    lateinit var llUser: LinearLayout
    lateinit var llLogOut: LinearLayout
    lateinit var llMyFollowee: LinearLayout
    lateinit var llMyFollower: LinearLayout
    lateinit var llMyComment: LinearLayout
    lateinit var llMyActivity: LinearLayout

    lateinit var ivAdd: ImageView
    lateinit var ivAvatar: ImageView
    lateinit var tvUsername: TextView
    lateinit var tvEmail: TextView

    lateinit var inUserNotLogin: View
    lateinit var btLogin: Button
    lateinit var btRegister: Button

    override fun onCreateView(inflater: LayoutInflater?, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        if (currentView == null) {
            currentView = inflater!!.inflate(R.layout.fragment_me, container, false)
            initView()

            checkLogin(true)

        }
        return currentView
    }

    private fun checkLogin(needRefresh: Boolean) {
        if (isLogin) {
            ivAdd.visibility = View.VISIBLE
            inUserNotLogin.visibility = View.GONE
            llUserHasLogin.visibility = View.VISIBLE

            setUserData(currentLoginUser)
            if (needRefresh) {
                srlUserData.isRefreshing = true
                srlUserData.setOnRefreshListener {
                    getUser(currentLoginUser.objectId)
                }
                getUser(currentLoginUser.objectId)
                ivAdd.setOnClickListener({
                    startActivity(Intent(activity, CreateActivity::class.java))
                })
            }
        } else {
            ivAdd.visibility = View.GONE
            inUserNotLogin.visibility = View.VISIBLE
            llUserHasLogin.visibility = View.GONE
        }
    }

    override fun onResume() {
        super.onResume()
        checkLogin(false)
    }

    private fun initView() {
        inUserNotLogin = currentView!!.in_user_not_login
        btLogin = currentView!!.btLogin
        btRegister = currentView!!.btRegister

        srlUserData = currentView!!.srlUserData
        llUserHasLogin = currentView!!.ll_user_has_login

        llUser = currentView!!.llUser
        llLogOut = currentView!!.llLogOut
        llMyComment = currentView!!.llMyComment
        llMyFollowee = currentView!!.llMyFollowee
        llMyFollower = currentView!!.llMyFollower
        llMyActivity = currentView!!.llMyActivity
        ivAdd = currentView!!.ivAdd
        ivAvatar = currentView!!.ivAvatar
        tvUsername = currentView!!.tvUsername
        tvEmail = currentView!!.tvEmail

        llUser.setOnClickListener(this)
        llLogOut.setOnClickListener(this)
        llMyFollowee.setOnClickListener(this)
        llMyFollower.setOnClickListener(this)
        llMyComment.setOnClickListener(this)
        llMyActivity.setOnClickListener(this)

        btLogin.setOnClickListener(this)
        btRegister.setOnClickListener(this)
    }


    override fun onClick(view: View) {
        when (view.id) {
            R.id.btRegister -> {
                startActivityForResult(Intent(activity, RegisterActivity::class.java), IntentRequestCode.MAIN_TO_LOGIN)
            }
            R.id.btLogin -> {
                startActivityForResult(Intent(activity, LoginActivity::class.java), IntentRequestCode.REGISTER)
            }
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
                getCurrentUser()
                checkLogin(false)
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
