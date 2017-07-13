package com.yetland.crazy.bundle.user.login

import android.app.ProgressDialog
import android.content.Intent
import android.os.Bundle
import android.text.TextUtils
import android.view.View
import android.widget.Button
import android.widget.EditText
import com.yetland.crazy.bundle.user.register.RegisterActivity
import com.yetland.crazy.core.base.BaseActivity
import com.yetland.crazy.core.constant.IntentRequestCode
import com.yetland.crazy.core.constant.IntentResultCode
import com.yetland.crazy.core.entity._User
import com.yetland.crazy.core.utils.FileUtil
import com.yetland.crazy.core.utils.isNetworkAvailable
import com.yetland.crazy.core.utils.makeShortToast
import com.ynchinamobile.hexinlvxing.R


class LoginActivity : BaseActivity(), LoginContract.View {


    val loginModel = LoginModel()
    val loginPresenter = LoginPresenter(loginModel, this)

    var etPassword: EditText? = null
    var etUsername: EditText? = null

    lateinit var dialog: ProgressDialog
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)
        val btForgetPassword = findViewById<Button>(R.id.bt_forget_password)
        etUsername = findViewById<EditText>(R.id.et_username)
        etPassword = findViewById<EditText>(R.id.et_password)

        supportActionBar?.title = "Login"
        supportActionBar?.setDisplayHomeAsUpEnabled(true)

        dialog = ProgressDialog(activity)
        dialog.setCancelable(false)
        dialog.setMessage("Login...")
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
                    makeShortToast(activity, "Username can't be empty")
                } else if (TextUtils.isEmpty(etPassword?.text)) {
                    makeShortToast(activity, "Password can't be empty")
                } else {
                    if (isNetworkAvailable(activity)) {
                        val phone = etUsername!!.text.toString()
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
        dialog.show()
        loginPresenter.login(username, password)
    }

    override fun loginSuccess(user: _User) {
        dialog.dismiss()
        FileUtil().saveUserInfo(activity, user)
        setResult(IntentResultCode.LOG_IN)
        finish()
    }

    override fun loginFailed(msg: String) {
        dialog.dismiss()
        makeShortToast(activity, msg)
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
}
