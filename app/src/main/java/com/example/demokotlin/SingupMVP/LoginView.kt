package com.example.demokotlin.SingupMVP

import android.content.Context
import com.google.firebase.auth.PhoneAuthProvider

interface LoginView {

    fun onOTPSentSuccess(verificationId: String, token: PhoneAuthProvider.ForceResendingToken)

    fun onSuccess(message: String)

    fun onError(error : String)

    interface Presenter {
        fun doLogin(number: String?)
    }
}