package com.ynchinamobile.hexinlvxing.core.utils

import android.content.Context
import android.widget.Toast



/**
 * @Name:           BaseToast
 * @Author:         yeliang
 * @Date:           2017/7/5
 */
fun makeShortToast(context: Context?, text: String?) {
    if (context != null && text != null && "" != text) {
        Toast.makeText(context, text, Toast.LENGTH_SHORT).show()
    }
};

fun makeLongToast(context: Context?, text: String?) {
    if (context != null && text != null && "" != text) {
        Toast.makeText(context, text, Toast.LENGTH_LONG).show()
    }
};