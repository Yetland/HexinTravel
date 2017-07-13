package com.yetland.crazy.core.utils

import android.content.Context
import android.text.TextUtils
import android.util.Log
import com.google.gson.Gson
import com.yetland.crazy.core.constant.FILE_KEY_USER
import com.yetland.crazy.core.entity._User
import java.io.*


/**
 * Created by yeliang on 2017/7/10.
 */

class FileUtil {


    fun cleanData(context: Context, fileName: String) {
        val editor = context.getSharedPreferences(fileName, 0).edit()
        editor.clear()
        editor.apply()
    }

    fun clearUserInfo(context: Context){
        val clientPreferences = context.getSharedPreferences(FILE_KEY_USER, 0)
        val prefEditor = clientPreferences.edit()
        prefEditor.clear()
        prefEditor.apply()
    }

    fun saveUserInfo(context: Context, user: _User) {
        val clientPreferences = context.getSharedPreferences(FILE_KEY_USER, 0)
        val prefEditor = clientPreferences.edit()
        prefEditor.putString(FILE_KEY_USER, Gson().toJson(user))
        prefEditor.apply()
    }

    fun getUserInfo(context: Context): _User {
        val clientPreferences = context.getSharedPreferences(FILE_KEY_USER, 0)
        val userString = clientPreferences.getString(FILE_KEY_USER, "")
        if (userString.isEmpty()) {
            return _User()
        }
        return Gson().fromJson(userString, _User::class.java)
    }

    /**
     * desc:保存对象

     * @param context   Context
     * *
     * @param key       key
     * *
     * @param obj     要保存的对象，只能保存实现了serializable的对象
     * *                modified:
     */
    fun saveObject(context: Context, key: String, obj: Any, fileName: String) {
        try {
            // 保存对象
            val sharedData = context.getSharedPreferences(fileName, 0).edit()
            //先将序列化结果写到byte缓存中，其实就分配一个内存空间
            val bos = ByteArrayOutputStream()
            val os = ObjectOutputStream(bos)
            //将对象序列化写入byte缓存
            os.writeObject(obj)
            //将序列化的数据转为16进制保存
            val bytesToHexString = bos.toString("UTF-8")
            //保存该16进制数组
            sharedData.putString(key, bytesToHexString)
            sharedData.apply()
        } catch (e: IOException) {
            e.printStackTrace()
            Log.e("sTemp", "保存obj失败")
        }

    }

    /**
     * desc:将数组转为16进制

     * @param bArray        byte
     * *
     * @return modified:
     */
    fun bytesToHexString(bArray: ByteArray?): String? {
        if (bArray == null) {
            return null
        }
        if (bArray.size == 0) {
            return ""
        }
        val sb = StringBuilder(bArray.size)
        var sTemp: String
        for (aBArray in bArray) {
            sTemp = aBArray.toString(0xFF)
            if (sTemp.length < 2)
                sb.append(0)
            sb.append(sTemp.toUpperCase())
        }
        return sb.toString()
    }

    /**
     * desc:获取保存的Object对象

     * @param context       context
     * *
     * @param key           key
     * *
     * @return modified:
     */
    fun readObject(context: Context, key: String, fileName: String): Any? {
        try {
            val sp = context.getSharedPreferences(fileName, 0)
            if (sp.contains(key)) {
                val string = sp.getString(key, "")
                if (TextUtils.isEmpty(string)) {
                    return null
                } else {
                    //将16进制的数据转为数组，准备反序列化
                    val stringToBytes = StringToBytes(string)
                    val bis = ByteArrayInputStream(stringToBytes)
                    val ois = ObjectInputStream(bis)
                    //返回反序列化得到的对象
                    return ois.readObject()
                }
            }
        } catch (e: ClassNotFoundException) {
            // TODO Auto-generated catch block
            e.printStackTrace()
        } catch (e: IOException) {
            e.printStackTrace()
        }

        //所有异常返回null
        return null

    }

    /**
     * desc:将16进制的数据转为数组
     *
     * 创建人：聂旭阳 , 2014-5-25 上午11:08:33

     * @param data      data
     * *
     * @return modified:
     */
    fun StringToBytes(data: String): ByteArray? {
        val hexString = data.toUpperCase().trim { it <= ' ' }
        if (hexString.length % 2 != 0) {
            return null
        }
        val retData = ByteArray(hexString.length / 2)
        var i = 0
        while (i < hexString.length) {
            val int_ch: Int  // 两位16进制数转化后的10进制数
            val hex_char1 = hexString[i] ////两位16进制数中的第一位(高位*16)
            val int_ch1: Int
            if (hex_char1 in '0'..'9')
                int_ch1 = (hex_char1.toInt() - 48) * 16   //// 0 的Ascll - 48
            else if (hex_char1 in 'A'..'F')
                int_ch1 = (hex_char1.toInt() - 55) * 16 //// A 的Ascll - 65
            else
                return null
            i++
            val hex_char2 = hexString[i] ///两位16进制数中的第二位(低位)
            val int_ch2: Int
            if (hex_char2 in '0'..'9')
                int_ch2 = hex_char2.toInt() - 48 //// 0 的Ascll - 48
            else if (hex_char2 in 'A'..'F')
                int_ch2 = hex_char2.toInt() - 55 //// A 的Ascll - 65
            else
                return null
            int_ch = int_ch1 + int_ch2
            retData[i / 2] = int_ch.toByte()//将转化后的数放入Byte里
            i++
        }
        return retData
    }
}

