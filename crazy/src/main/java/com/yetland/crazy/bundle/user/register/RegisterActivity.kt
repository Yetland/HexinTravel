package com.yetland.crazy.bundle.user.register

import android.annotation.SuppressLint
import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.EditText
import com.yetland.crazy.bundle.main.MainActivity
import com.yetland.crazy.bundle.user.login.LoginActivity
import com.yetland.crazy.core.base.BaseActivity
import com.yetland.crazy.core.entity._User
import com.yetland.crazy.core.utils.ActivityManager
import com.yetland.crazy.core.utils.FileUtil
import com.yetland.crazy.core.utils.makeShortToast
import com.ynchinamobile.hexinlvxing.R

class RegisterActivity : BaseActivity(), RegisterContract.View, View.OnClickListener {
    override fun onClick(view: View) {
        when (view.id) {
            android.R.id.home -> onBackPressed()
            R.id.bt_register -> {
                val username = etUserName.text.toString().trim()
                val email = etEmail.text.toString().trim()
                val password = etPassword.text.toString().trim()
                if (username.isEmpty()) {
                    makeShortToast(activity, "Username cannot be empty")
                } else if (password.isEmpty()) {
                    makeShortToast(activity, "Password cannot be empty")
                } else if (password.isNotEmpty()) {
                    if (password.contains(" ")) {
                        makeShortToast(activity, "Password cannot contains space")
                    } else if (password.length < 6) {
                        makeShortToast(activity, "Password is too short to register")
                    } else {
                        user.username = username
                        user.password = password
                        if (email.isNotEmpty()) user.email = email
                        register(user)
                    }
                }
            }
        }
    }

    override fun register(user: _User) {
        presenter.register(user)
    }

    override fun success() {
        FileUtil().saveUserInfo(activity, user)
        ActivityManager().removeActivity(LoginActivity())
        val intent = Intent(activity, MainActivity::class.java)
        startActivity(intent)
        finish()
    }

    override fun failed(msg: String) {
        makeShortToast(activity, msg)
    }

    lateinit var etUserName: EditText
    lateinit var etPassword: EditText
    lateinit var etEmail: EditText
    val user = _User()

    val model = RegisterModel()
    val presenter = RegisterPresenter(model, this)

    @SuppressLint("RestrictedApi")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_register)
        val actionBar = supportActionBar
        actionBar?.title = "Register"
        actionBar?.setDisplayHomeAsUpEnabled(true)
        findViewById<Button>(R.id.bt_register).setOnClickListener(this)
        etUserName = findViewById(R.id.et_username)
        etEmail = findViewById(R.id.et_mail)
        etPassword = findViewById(R.id.et_password)
    }
}
