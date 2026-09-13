package ru.social.app.data.network.firebase

import android.net.Uri
import android.util.Log
import com.google.firebase.Firebase
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.auth.auth
import com.google.firebase.auth.userProfileChangeRequest


class AuthClient(
    private val auth: FirebaseAuth
) {

    companion object {

        @Volatile
        private var instance: AuthClient? = null

        fun getInstance() = instance ?: synchronized(this) {
            AuthClient(Firebase.auth).also {
                instance = it
            }
        }

    }

    fun signUp(
        email: String,
        password: String,
        onSuccess: (FirebaseUser?) -> Unit = {},
        onError: (String) -> Unit = {}
    ) {
        auth.createUserWithEmailAndPassword(email, password)
            .addOnSuccessListener { onSuccess(auth.currentUser) }
            .addOnFailureListener { onError(it.message ?: "Sign Up Error") }
    }

    fun signIn(
        email: String,
        password: String,
        onSuccess: (FirebaseUser?) -> Unit = {},
        onError: (String) -> Unit = {}
    ) {
        auth.signInWithEmailAndPassword(email, password)
            .addOnSuccessListener { onSuccess(auth.currentUser) }
            .addOnFailureListener { onError(it.message ?: "Sign Up Error") }
    }

    fun updateProfile(
        user: FirebaseUser? = null,
        name: String,
        imageUrl: String,
        onSuccess: () -> Unit = {},
        onError: (String) -> Unit = {}
    ) {
        val updates = userProfileChangeRequest {
            displayName = name
            photoUri = Uri.parse(imageUrl)
        }

        try {
            (user ?: auth.currentUser)!!.updateProfile(updates)
                .addOnSuccessListener { onSuccess() }
                .addOnFailureListener { onError(it.message ?: "Update profile error") }
        } catch (e: Exception) {
            Log.d("TEST", "Firebase, update profile. Current user is null!")
            onError("Current user is null!")
        }

    }

}