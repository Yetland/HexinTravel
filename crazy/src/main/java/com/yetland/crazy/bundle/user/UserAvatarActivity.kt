package com.yetland.crazy.bundle.user

import android.os.Bundle
import android.support.v7.widget.GridLayoutManager
import com.yetland.crazy.core.base.BaseActivity
import com.yetland.crazy.core.base.OnRecyclerViewItemClickListener
import com.yetland.crazy.core.base.RecyclerViewListener
import com.yetland.crazy.core.entity.Avatar
import com.yetland.crazy.core.entity.BaseEntity
import com.yetland.crazy.core.utils.LogUtils
import com.ynchinamobile.hexinlvxing.R
import kotlinx.android.synthetic.main.activity_user_avatar.*

class UserAvatarActivity : BaseActivity(), RecyclerViewListener, OnRecyclerViewItemClickListener {

    var avatarList = ArrayList<Avatar>()
    var avatarId = listOf(R.mipmap.ic_avatar_1, R.mipmap.ic_avatar_2,
            R.mipmap.ic_avatar_3, R.mipmap.ic_avatar_4,
            R.mipmap.ic_avatar_5, R.mipmap.ic_avatar_6,
            R.mipmap.ic_avatar_7, R.mipmap.ic_avatar_8,
            R.mipmap.ic_avatar_9, R.mipmap.ic_avatar_10,
            R.mipmap.ic_avatar_11, R.mipmap.ic_avatar_12,
            R.mipmap.ic_avatar_13, R.mipmap.ic_avatar_14,
            R.mipmap.ic_avatar_15, R.mipmap.ic_avatar_16,
            R.mipmap.ic_avatar_17, R.mipmap.ic_avatar_18,
            R.mipmap.ic_avatar_19, R.mipmap.ic_avatar_20,
            R.mipmap.ic_avatar_22, R.mipmap.ic_avatar_23,
            R.mipmap.ic_avatar_25, R.mipmap.ic_avatar_26,
            R.mipmap.ic_avatar_27, R.mipmap.ic_avatar_28,
            R.mipmap.ic_avatar_29, R.mipmap.ic_avatar_30,
            R.mipmap.ic_avatar_31, R.mipmap.ic_avatar_32)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_user_avatar)

        setSupportActionBar(toolbar2)
        supportActionBar?.title = "AvatarList"
        supportActionBar?.setDisplayHomeAsUpEnabled(true)

        rvUserAvatar.recyclerViewListener = this
        rvUserAvatar.canLoadMore = false
        rvUserAvatar.canRefresh = false
        rvUserAvatar.layoutManager = GridLayoutManager(activity, 3)
        rvUserAvatar.initView(activity)
        rvUserAvatar.onLoading()
        rvUserAvatar.adapter.onItemClickListener = this
        for (x in avatarId) {
            val avatar = Avatar()
            avatar.avatarUrl = x
            avatarList.add(avatar)
        }

        val list = ArrayList<BaseEntity>()
        list.addAll(avatarList)
        rvUserAvatar.onDefaultComplete(list, 0)
    }

    override fun onRecyclerViewItemClick(position: Int) {
        LogUtils.e(position)
        val size = avatarList.size
        var x = 0

        while (x < size) {
            if (x == position) {
                avatarList[position].checked = !avatarList[position].checked
            } else {
                avatarList[x].checked = false
            }
            x++
        }

        val list = ArrayList<BaseEntity>()
        list.addAll(avatarList)
        rvUserAvatar.onDefaultComplete(list, 0)
    }

    override fun onRefresh() {
    }

    override fun onLoadMore() {
    }

    override fun onErrorClick() {
    }

}
