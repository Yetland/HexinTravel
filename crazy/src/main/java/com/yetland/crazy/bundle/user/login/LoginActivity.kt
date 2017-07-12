package com.yetland.crazy.bundle.user.login

import android.content.Intent
import android.os.Bundle
import android.text.TextUtils
import android.view.View
import android.widget.Button
import android.widget.EditText
import com.yetland.crazy.bundle.user.register.RegisterActivity
import com.yetland.crazy.bundle.main.MainActivity
import com.yetland.crazy.core.base.BaseActivity
import com.yetland.crazy.core.entity._User
import com.yetland.crazy.core.utils.FileUtil
import com.yetland.crazy.core.utils.isNetworkAvailable
import com.yetland.crazy.core.utils.makeShortToast
import com.ynchinamobile.hexinlvxing.R


class LoginActivity : BaseActivity(), LoginContract.View {


    val loginModel = LoginModel()
    val loginPresenter = LoginPresenter(loginModel, this)

    var etPassword: EditText? = null
    var etPhone: EditText? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)
        val btForgetPassword = findViewById<Button>(R.id.bt_forget_password)
        etPhone = findViewById<EditText>(R.id.et_phone)
        etPassword = findViewById<EditText>(R.id.et_password)

        supportActionBar?.title = "Login"
        findViewById<Button>(R.id.bt_login).setOnClickListener(click)
        findViewById<Button>(R.id.bt_register).setOnClickListener(click)
        btForgetPassword!!.setOnClickListener(click)

    }

    val click = View.OnClickListener {
        view ->
        when (view.id) {

            R.id.bt_forget_password -> {
                val intent = Intent(applicationContext, RegisterActivity::class.java)
                startActivity(intent)
            }
            R.id.bt_register -> {
                val intent = Intent(applicationContext, RegisterActivity::class.java)
                startActivity(intent)
            }

            R.id.bt_login -> {
                if (TextUtils.isEmpty(etPhone?.text)) {
                    makeShortToast(activity, "Phone can't be empty")
                } else if (TextUtils.isEmpty(etPassword?.text)) {
                    makeShortToast(activity, "Password can't be empty")
                } else {
                    if (isNetworkAvailable(activity)) {
                        val phone = etPhone!!.text.toString()
                        val password = etPassword!!.text.toString()
                        login(phone, password)
                    } else {
                        makeShortToast(activity, getString(R.string.network_unavailable))
                    }
                }
            }
        }
    }


    override fun login(username: String, password: String) {
        loginPresenter.login(username, password)
    }

    override fun loginSuccess(user: _User) {
        FileUtil().saveUserInfo(activity, user)
        val intent = Intent(applicationContext, MainActivity::class.java)
        startActivity(intent)
        finish()
    }

    override fun loginFailed(msg: String) {
        makeShortToast(activity, msg)
    }

    override fun goToRegister() {
        val intent = Intent(applicationContext, RegisterActivity::class.java)
        startActivity(intent)
    }

}
