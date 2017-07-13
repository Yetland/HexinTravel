package com.yetland.crazy.bundle.user

import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.text.TextUtils
import android.view.View
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.TextView
import com.squareup.picasso.Picasso
import com.yetland.crazy.core.base.BaseActivity
import com.yetland.crazy.core.entity._User
import com.yetland.crazy.core.utils.FileUtil
import com.ynchinamobile.hexinlvxing.R
import kotlinx.android.synthetic.main.activity_user_data.*

class UserDataActivity : BaseActivity(), View.OnClickListener {


    lateinit var llUser: LinearLayout
    lateinit var ivAvatar: ImageView
    lateinit var tvUsername: TextView
    lateinit var tvEmail: TextView
    lateinit var llMyActivity: LinearLayout
    lateinit var llMyComment: LinearLayout
    lateinit var llLogOut: LinearLayout
    lateinit var user: _User
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_user_data)
        user = FileUtil().getUserInfo(activity)
        supportActionBar?.title = "Me"
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        llUser = findViewById(R.id.ll_user)
        ivAvatar = findViewById(R.id.iv_avatar)
        tvUsername = findViewById(R.id.tv_username)
        tvEmail = findViewById(R.id.tv_email)
        llMyActivity = findViewById(R.id.ll_my_activity)
        llMyComment = findViewById(R.id.ll_my_comment)
        llLogOut = findViewById(R.id.ll_log_out)

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

    override fun onClick(view: View) {
        when (view.id) {
            R.id.ll_my_comment -> {

            }
            R.id.ll_my_activity -> {

            }
            R.id.ll_log_out -> {

            }
        }
    }
}
