package com.yetland.crazy.bundle.user.login

import android.content.Intent
import android.os.Bundle
import android.text.TextUtils
import android.view.View
import android.widget.Button
import android.widget.EditText
import com.google.gson.Gson
import com.yetland.crazy.bundle.user.contract.FollowContract
import com.yetland.crazy.bundle.user.contract.FollowModel
import com.yetland.crazy.bundle.user.contract.FollowPresenter
import com.yetland.crazy.bundle.user.register.RegisterActivity
import com.yetland.crazy.core.base.BaseActivity
import com.yetland.crazy.core.constant.IntentRequestCode
import com.yetland.crazy.core.constant.IntentResultCode
import com.yetland.crazy.core.constant.SharedPreferencesConstant
import com.yetland.crazy.core.entity.*
import com.yetland.crazy.core.utils.*
import com.ynchinamobile.hexinlvxing.R


class LoginActivity : BaseActivity(), LoginContract.View, FollowContract.View {


    val loginPresenter = LoginPresenter(LoginModel(), this)
    val followPresenter = FollowPresenter(FollowModel(), this)

    var etPassword: EditText? = null
    var etUsername: EditText? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)
        val btForgetPassword = findViewById<Button>(R.id.bt_forget_password)
        etUsername = findViewById<EditText>(R.id.et_username)
        etPassword = findViewById<EditText>(R.id.et_password)

        supportActionBar?.title = "Login"
        supportActionBar?.setDisplayHomeAsUpEnabled(true)

        findViewById<Button>(R.id.bt_login).setOnClickListener(click)
        findViewById<Button>(R.id.bt_register).setOnClickListener(click)
        btForgetPassword!!.setOnClickListener(click)

    }

    val click = View.OnClickListener {
        view ->
        when (view.id) {

            R.id.bt_forget_password -> {
                val intent = Intent(applicationContext, RegisterActivity::class.java)
                startActivityForResult(intent, IntentRequestCode.REGISTER)
            }
            R.id.bt_register -> {
                val intent = Intent(applicationContext, RegisterActivity::class.java)
                startActivityForResult(intent, IntentRequestCode.REGISTER)
            }

            R.id.bt_login -> {
                if (TextUtils.isEmpty(etUsername?.text)) {
                    ToastUtils.showShortSafe("Username can't be empty")
                } else if (TextUtils.isEmpty(etPassword?.text)) {
                    ToastUtils.showShortSafe("Password can't be empty")
                } else {
                    val phone = etUsername!!.text.toString()
                    val password = etPassword!!.text.toString()
                    login(phone, password)
                }
            }
        }
    }


    override fun login(username: String, password: String) {
        progressDialog.setContent("Login...")
        progressDialog.show()
        loginPresenter.login(username, password)
    }

    override fun loginSuccess(user: _User) {

        SharedPreferencesUtils.saveUserInfo(user)
        val map = HashMap<String, Any>()
        map.put("follower", Point("_User", user.objectId))
        getFollower(map, -1)

    }

    override fun loginFailed(msg: String) {
        progressDialog.dismiss()
        ToastUtils.showShortSafe(msg)
    }

    override fun goToRegister() {
        val intent = Intent(applicationContext, RegisterActivity::class.java)
        startActivityForResult(intent, IntentRequestCode.REGISTER)
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {

        when (resultCode) {
            IntentResultCode.REGISTER_SUCCESS -> {
                setResult(IntentResultCode.LOG_IN)
                finish()
            }

        }
        super.onActivityResult(requestCode, resultCode, data)
    }

    override fun getFollower(map: HashMap<String, Any>, page: Int) {
        followPresenter.getFollower(Gson().toJson(map), page)
    }

    override fun follow(follow: CommitFollow) {
    }

    override fun unFollow(objectId: String) {
    }

    override fun getFollowerSuccess(data: Data<Follow>) {

        progressDialog.dismiss()
        SharedPreferencesUtils.saveString(SharedPreferencesConstant.KEY_FOLLOWER_LIST,
                Gson().toJson(data.results))
        setResult(IntentResultCode.LOG_IN)
        finish()
    }

    override fun followSuccess(follow: Follow) {
    }

    override fun unFollowSuccess() {
    }

    override fun getFollowerFailed(msg: String) {
        progressDialog.dismiss()
        ToastUtils.showShortSafe(msg)
    }

    override fun followFailed(msg: String) {
    }

    override fun unFollowFailed(msg: String) {
    }
}
