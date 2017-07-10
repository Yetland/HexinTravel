package com.ynchinamobile.hexinlvxing.core.utils

import android.content.Context
import com.cmcc.hysso.sdk.auth.AuthnHelper
import com.ynchinamobile.hexinlvxing.R


/**
 * @Name:           CheckError
 * @Author:         yeliang
 * @Date:           2017/7/5
 */

fun errorCodeToast(errorCode: Int, context: Context) {
    when (errorCode) {
        103101 -> makeShortToast(context, context.resources.getString(R.string.login_wrong_request))
        103103 -> makeShortToast(context, context.resources.getString(R.string.login_userNotExist))
        103104 -> makeShortToast(context,
                context.resources.getString(R.string.login_userNotSupportWayTologin))
        103105 -> makeShortToast(context,
                context.resources.getString(R.string.login_passwordError))
        103106 -> makeShortToast(context,
                context.resources.getString(R.string.login_userError))
        103107 -> makeShortToast(context,
                context.resources.getString(R.string.login_existSameRandomNumber))
        103108 -> makeShortToast(context,
                context.resources.getString(R.string.login_MessageCodeWrong))
        103109 -> makeShortToast(context,
                context.resources.getString(R.string.login_MessageCodeTimeOut))
        103110 -> makeShortToast(context,
                context.resources.getString(R.string.login_messageRamdonaNotSameHttpRequest))
        103111 -> makeShortToast(context,
                context.resources.getString(R.string.login_WAPdwanguanwrong))
        103112 -> makeShortToast(context,
                context.resources.getString(R.string.login_WrongRequest))
        103113 -> makeShortToast(context,
                context.resources.getString(R.string.login_tokenError))
        103114 -> {
            makeShortToast(context,
                    context.resources.getString(R.string.login_tokenCheckKSOverdue))
            AuthnHelper(context).cleanSSO {
                // 处理响应码,102000为成功响应码，其他为非正常响应码
            }
        }
        103115 -> {
            makeShortToast(context,
                    context.resources.getString(R.string.login_tokenCheckKSNoneExist))
            AuthnHelper(context).cleanSSO {
                // 处理响应码,102000为成功响应码，其他为非正常响应码
            }
        }
        103116 -> {
            makeShortToast(context,
                    context.resources.getString(R.string.login_tokenCheckSqnErr))
            AuthnHelper(context).cleanSSO {
                // 处理响应码,102000为成功响应码，其他为非正常响应码
            }
        }
        103117 -> {
            makeShortToast(context,
                    context.resources.getString(R.string.login_macError))
            AuthnHelper(context).cleanSSO {
                // 处理响应码,102000为成功响应码，其他为非正常响应码
            }
        }
        103118 -> makeShortToast(context,
                context.resources.getString(R.string.login_sourceidNotExist))
        103119 -> makeShortToast(context,
                context.resources.getString(R.string.login_appidNotExist))
        103120 -> makeShortToast(context,
                context.resources.getString(R.string.login_clientauthNotExist))
        103121 -> makeShortToast(context,
                context.resources.getString(R.string.login_passidNotExist))
        103122 -> makeShortToast(context,
                context.resources.getString(R.string.login_btidNotExist))
        103123 -> makeShortToast(context,
                context.resources.getString(R.string.login_redisinfoNotExist))
        103124 -> makeShortToast(context,
                context.resources.getString(R.string.login_ksnafNotSame))
        103125 -> makeShortToast(context,
                context.resources.getString(R.string.login_phoneFromWrong))
        103126 -> makeShortToast(context,
                context.resources.getString(R.string.login_phoneNumberNotExist))
        103127 -> makeShortToast(context,
                context.resources.getString(R.string.login_certificateValidation_versionExpired))
        103128 -> makeShortToast(context,
                context.resources.getString(R.string.login_gba_webservice_userFail))
        103129 -> makeShortToast(context,
                context.resources.getString(R.string.login_getMessageCode_msgtypeWrong))
        103130 -> makeShortToast(context,
                context.resources.getString(R.string.login_newpasswordNotSameAsNowPassword))
        103131 -> makeShortToast(context,
                context.resources.getString(R.string.login_passwordSoEasy))
        103132 -> makeShortToast(context,
                context.resources.getString(R.string.login_userRegisteFail))
        103133 -> makeShortToast(context,
                context.resources.getString(R.string.login_sourceidIllegal))
        103134 -> makeShortToast(context,
                context.resources.getString(R.string.login_wapWapPhoneNumberEmpty))
        103135 -> makeShortToast(context,
                context.resources.getString(R.string.login_nameIllegally))
        103136 -> makeShortToast(context,
                context.resources.getString(R.string.login_emailIllegally))
        103137 -> makeShortToast(context,
                context.resources.getString(R.string.login_requestOtherToken))
        103203 -> makeShortToast(context,
                context.resources.getString(R.string.login_saveUserNotExist))
        103204 -> makeShortToast(context,
                context.resources.getString(R.string.login_saveRandomNmuberNotExist))
        103205 -> makeShortToast(context,
                context.resources.getString(R.string.login_ServiceWrong))
        103206 -> makeShortToast(context,
                context.resources.getString(R.string.login_encryptWrong))
        103207 -> makeShortToast(context,
                context.resources.getString(R.string.login_SendMessageFail))
        103208 -> makeShortToast(context,
                context.resources.getString(R.string.login_UseThirdInterfaceFail))
        103209 -> makeShortToast(context,
                context.resources.getString(R.string.login_synchroPasswrodFaIL))
        103210 -> makeShortToast(context,
                context.resources.getString(R.string.login_changePasswrodFail))
        103211 -> makeShortToast(context,
                context.resources.getString(R.string.login_otherWrong))
        103212 -> makeShortToast(context,
                context.resources.getString(R.string.login_checkPasswordFail))
        103213 -> makeShortToast(context,
                context.resources.getString(R.string.login_oldPasswordWrong))
        103265 -> makeShortToast(context,
                context.resources.getString(R.string.login_userAlreadyExist))
        103266 -> makeShortToast(context,
                context.resources.getString(R.string.login_passwordFromIllegally))
        103300 -> makeShortToast(context,
                context.resources.getString(R.string.login_SIPsuccess))
        103301 -> makeShortToast(context,
                context.resources.getString(R.string.login_SIPchecktokenerror))
        103302 -> makeShortToast(context,
                context.resources.getString(R.string.login_SIPuserexist))
        103303 -> makeShortToast(context,
                context.resources.getString(R.string.login_SIPuserNoaccount))
        103304 -> makeShortToast(context,
                context.resources.getString(R.string.login_SIPuserNoaccountCancel))
        103305 -> makeShortToast(context,
                context.resources.getString(R.string.login_SIPuserNameError))
        103306 -> makeShortToast(context,
                context.resources.getString(R.string.login_SIPuserNameNotEmpty))
        103307 -> makeShortToast(context,
                context.resources.getString(R.string.login_SIPuserNameNotEmptyCancel))
        103308 -> makeShortToast(context,
                context.resources.getString(R.string.login_SIPPhoneillegal))
        103309 -> makeShortToast(context,
                context.resources.getString(R.string.login_SIPopertypeIsEmpty))
        103310 -> makeShortToast(context,
                context.resources.getString(R.string.login_SIPsourceidNotExist))
        103311 -> makeShortToast(context,
                context.resources.getString(R.string.login_SIPsourceidIllegal))
        103312 -> makeShortToast(context,
                context.resources.getString(R.string.login_SIPbtidNotExist))
        103313 -> makeShortToast(context,
                context.resources.getString(R.string.login_SIPksNotExist))
        103399 -> makeShortToast(context,
                context.resources.getString(R.string.login_SIPSystemError))

        103400 -> makeShortToast(context,
                context.resources.getString(R.string.login_authorizationEmity))
        103401 -> makeShortToast(context,
                context.resources.getString(R.string.login_signMessageEmpty))
        103402 -> makeShortToast(context,
                context.resources.getString(R.string.login_noUseAuthWay))
        103403 -> makeShortToast(context,
                context.resources.getString(R.string.login_defaultRegisterFail))
        103404 -> makeShortToast(context,
                context.resources.getString(R.string.login_encryptFail))
        103405 -> makeShortToast(context,
                context.resources.getString(R.string.login_savephoneIsEmpty))
        103406 -> makeShortToast(context,
                context.resources.getString(R.string.login_saveMessageIsEmpty))

        103407 -> makeShortToast(context,
                context.resources.getString(R.string.login_this_sourceId_appPackage_si))
        103408 -> makeShortToast(context,
                context.resources.getString(R.string.login_thisSourceIdRegister99Times))
        103409 -> makeShortToast(context,
                context.resources.getString(R.string.login_queryEmpty))
        103410 -> makeShortToast(context,
                context.resources.getString(R.string.login_noUsemac))
        103411 -> makeShortToast(context,
                context.resources.getString(R.string.login_queryWrong))
        103412 -> makeShortToast(context,
                context.resources.getString(R.string.login_noUserRequest))
        103413 -> makeShortToast(context,
                context.resources.getString(R.string.login_RuntimeException))
        103414 -> makeShortToast(context,
                context.resources.getString(R.string.login_parameterCheckWrong))
        103500 -> makeShortToast(context,
                context.resources.getString(R.string.login_registerUserNoOperateinBlackList))
        103501 -> makeShortToast(context,
                context.resources.getString(R.string.login_changePasswordUserNoOperateinBlackList))
        103502 -> makeShortToast(context,
                context.resources.getString(R.string.login_restartPasswordUserNoOperateinBlackList))
        103503 -> makeShortToast(context,
                context.resources.getString(R.string.login_loginUserNoOperateinBlackList))
        103801 -> makeShortToast(context,
                context.resources.getString(R.string.login_aoiTokenPhoneIllegal))
        103802 -> makeShortToast(context,
                context.resources.getString(R.string.login_aoiTokenPhoneNotRegister))
        103803 -> makeShortToast(context,
                context.resources.getString(R.string.login_aoiTokenRequestTimeout))
        103804 -> makeShortToast(context,
                context.resources.getString(R.string.login_aoiTokenUserConfirmLogin))
        103805 -> makeShortToast(context,
                context.resources.getString(R.string.login_aoiTokenUserCancelLogin))
        103806 -> makeShortToast(context,
                context.resources.getString(R.string.login_aoiTokensourceidIllegal))
        103807 -> makeShortToast(context,
                context.resources.getString(R.string.login_aoiUserOffline))
        103808 -> makeShortToast(context,
                context.resources.getString(R.string.login_aoiUserNotConfirm))
        103809 -> makeShortToast(context,
                context.resources.getString(R.string.login_aoiMessageSendFailed))
        103899 -> makeShortToast(context,
                context.resources.getString(R.string.login_aoiTokenOtherError))
        103901 -> makeShortToast(context,
                context.resources.getString(R.string.login_sendVerTimesOut))
        102102 -> makeShortToast(context, "网络连接错误")
        102303 ->
            //Get local sso url failed
            makeShortToast(context, "连接服务器失败")
        else -> makeShortToast(context, "连接服务器失败")
    }
}