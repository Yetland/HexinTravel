package com.yetland.crazy.bundle.user.register

import android.os.Bundle
import android.view.View
import com.yetland.crazy.core.base.BaseActivity
import com.yetland.crazy.core.constant.IntentResultCode
import com.yetland.crazy.core.entity._User
import com.yetland.crazy.core.utils.RegexUtil
import com.yetland.crazy.core.utils.SharedPreferencesUtils
import com.yetland.crazy.core.utils.ToastUtils
import com.ynchinamobile.hexinlvxing.R
import kotlinx.android.synthetic.main.activity_register.*

class RegisterActivity : BaseActivity(), RegisterContract.View, View.OnClickListener {
    override fun onClick(view: View) {
        when (view.id) {
            android.R.id.home -> onBackPressed()
            R.id.btRegister -> {
                val username = etUsername.text.toString().trim()
                val email = etMail.text.toString().trim()
                val password = etPassword.text.toString().trim()
                if (username.isEmpty()) {
                    ToastUtils.showShortSafe("Username cannot be empty")
                } else if (password.isEmpty()) {
                    ToastUtils.showShortSafe("Password cannot be empty")
                } else if (email.isEmpty()) {
                    ToastUtils.showShortSafe("Email cannot be empty")
                } else if (!RegexUtil.isEmail(email)) {
                    ToastUtils.showShortSafe("Email is not correct")
                } else if (password.isNotEmpty()) {
                    if (password.contains(" ")) {
                        ToastUtils.showShortSafe("Password cannot contains space")
                    } else if (password.length < 6) {
                        ToastUtils.showShortSafe("Password is too short to register")
                    } else {
                        val user = _User()

                        user.username = username
                        user.password = password
                        user.email = email
                        register(user)
                    }
                }
            }
        }
    }

    override fun register(user: _User) {
        progressDialog.show()
        presenter.register(user)
    }

    override fun success(resultUser: _User) {
        progressDialog.dismiss()
        SharedPreferencesUtils.saveUserInfo(resultUser)
        setResult(IntentResultCode.REGISTER_SUCCESS)
        finish()
    }

    override fun failed(msg: String) {
        progressDialog.dismiss()
        ToastUtils.showShortSafe(msg)
    }

    val presenter = RegisterPresenter(RegisterModel(), this)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_register)
        supportActionBar?.title = "Register"
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        btRegister.setOnClickListener(this)

        progressDialog.setContent("Registering...")
    }
}
