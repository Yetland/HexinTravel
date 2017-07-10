package com.ynchinamobile.hexinlvxing

import android.content.Context
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.EditText
import kotlinx.android.synthetic.main.activity_login.*
import org.jetbrains.anko.AnkoComponent

class LoginActivity : AppCompatActivity() {

    lateinit var mBtLogin: Button
    lateinit var mBtRegister: Button
    lateinit var mEdtPhoneNumber: EditText
    lateinit var mEdtPassword: EditText
    lateinit var mContext: Context
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)
        init()
        mBtLogin.setOnClickListener(onClick)
        mBtRegister.setOnClickListener(onClick)
        bt_login.text = "登录"
        bt_register.text = "zhuCe"

    }

    fun init() {
        mBtLogin = findViewById(R.id.bt_login)
        mBtRegister = findViewById(R.id.bt_register)
        mEdtPassword = findViewById(R.id.et_password)
        mEdtPhoneNumber = findViewById(R.id.et_phone_number)
    }

    var onClick: View.OnClickListener = View.OnClickListener {
        view ->
        run {
            when (view.id) {
                R.id.bt_login -> print("fff")
                R.id.bt_register -> print("fff")
            }
        }
    }
}
