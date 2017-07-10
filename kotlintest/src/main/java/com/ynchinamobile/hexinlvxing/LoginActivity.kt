package com.ynchinamobile.hexinlvxing

import android.annotation.SuppressLint
import android.content.Intent
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import android.os.Message
import android.text.TextUtils
import android.view.View
import android.widget.Button
import android.widget.EditText
import com.cmcc.hysso.sdk.auth.AuthnHelper
import com.cmcc.hysso.sdk.util.SsoSdkConstants
import com.ynchinamobile.hexinlvxing.core.constant.APP_ID
import com.ynchinamobile.hexinlvxing.core.constant.APP_KEY
import com.ynchinamobile.hexinlvxing.core.constant.LOGON_SUCCESS
import com.ynchinamobile.hexinlvxing.core.utils.isNetworkAvailable
import com.ynchinamobile.hexinlvxing.core.utils.makeShortToast
import org.json.JSONObject


class LoginActivity : AppCompatActivity() {

    var etPassword: EditText? = null
    var etPhone: EditText? = null
    private var helper: AuthnHelper = AuthnHelper(baseContext)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)
        val btLogin = findViewById<Button>(R.id.bt_login)
        val btRegister = findViewById<Button>(R.id.bt_register)
        val btQuickLogin = findViewById<Button>(R.id.bt_quick_login)
        val btForgetPassword = findViewById<Button>(R.id.bt_forget_password)
        etPhone = findViewById<EditText>(R.id.et_phone)
        etPassword = findViewById<EditText>(R.id.et_password)

        btLogin!!.setOnClickListener(click)
        btRegister!!.setOnClickListener(click)
        btQuickLogin!!.setOnClickListener(click)
        btForgetPassword!!.setOnClickListener(click)

        helper.setDefaultUI(false)

    }

    val click = View.OnClickListener {
        view ->
        when (view.id) {

            R.id.bt_quick_login -> {
                quickLogin()
            }

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
                    makeShortToast(baseContext, "Phone can't be empty")
                } else if (TextUtils.isEmpty(etPassword?.text)) {
                    makeShortToast(baseContext, "Password can't be empty")
                } else {
                    if (isNetworkAvailable(baseContext)) {
                        val phone = etPhone!!.text.toString()
                        val password = etPassword!!.text.toString()
                        login(phone, password)
                    } else {
                        makeShortToast(baseContext, getString(R.string.network_unavailable))
                    }
                }
            }
        }
    }

    private fun quickLogin() {
        helper.getAccessToken(APP_ID, APP_KEY, "", SsoSdkConstants.LOGIN_TYPE_DEFAULT, {
            json ->
            run {
                if (json != null) {
                    val message: Message = Message()
                    message.obj = json
                    loginHandler.handleMessage(message)
                } else {
                    makeShortToast(baseContext, getString(R.string.login_error))
                }
            }
        })
    }

    private fun login(phone: String, password: String) {

        helper.getAccessTokenByCondition(APP_ID, APP_KEY,
                SsoSdkConstants.AUTHN_ACCOUNT_PASSWORD.toInt(),
                phone, password,
                {
                    json ->
                    if (json != null) {
                        val message: Message = Message()
                        message.obj = json
                        loginHandler.handleMessage(message)
                    } else {
                        makeShortToast(baseContext, getString(R.string.login_error))
                    }
                }
        )
    }

    @SuppressLint("HandlerLeak")
    private var loginHandler: Handler = object : Handler() {
        override fun handleMessage(msg: Message?) {
            super.handleMessage(msg)
            val json: JSONObject = msg?.obj as JSONObject
            val resultCode = json.optInt(SsoSdkConstants.VALUES_KEY_RESULT_CODE, -1)
            val token = json.optString(SsoSdkConstants.VALUES_KEY_TOKEN, "")
            if (resultCode == LOGON_SUCCESS) {
                verifyToken(token)
            }
        }
    }

    private fun verifyToken(token: String?) {

    }
}
