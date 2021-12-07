package com.example.demokotlin.SingupMVP

import android.app.Activity
import com.example.demokotlin.SingupActivity
import com.google.firebase.FirebaseException
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.PhoneAuthCredential
import com.google.firebase.auth.PhoneAuthOptions
import com.google.firebase.auth.PhoneAuthProvider
import java.util.concurrent.TimeUnit

class LoginPresenter : LoginView.Presenter {

    fun LoginPresenter(singupActivity: SingupActivity) {this.loginView= loginView}


    lateinit var auth: FirebaseAuth

    lateinit var loginView: LoginView

    private lateinit var callbacks: PhoneAuthProvider.OnVerificationStateChangedCallbacks


    override fun doLogin(number: String?) {

        if (number != null) {
            if(!number.isEmpty()){
                sendVerificationcode (number)
            }else{
                loginView.onError("Invalid Mobile Number")
            }
        }
    }

    private fun sendVerificationcode(number: String) {
        val options = PhoneAuthOptions.newBuilder(auth)
            .setPhoneNumber(number) // Phone number to verify
            .setTimeout(60L, TimeUnit.SECONDS) // Timeout and unit
            .setActivity(loginView as Activity) // Activity (for callback binding)
            .setCallbacks(callbacks) // OnVerificationStateChangedCallbacks
            .build()
        PhoneAuthProvider.verifyPhoneNumber(options)

        // Callback function for Phone Auth
        callbacks = object : PhoneAuthProvider.OnVerificationStateChangedCallbacks() {

            override fun onVerificationCompleted(credential: PhoneAuthCredential) {
                loginView.onSuccess("Success")
            }

            override fun onVerificationFailed(e: FirebaseException) {
                loginView.onError("Failed")
            }

            // Perform the Code sent Function
            override fun onCodeSent(verificationId: String, token: PhoneAuthProvider.ForceResendingToken) {

                loginView.onOTPSentSuccess(toString(),token)

            }
        }
    }


}