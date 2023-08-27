package uz.gita.fooddeliveryumidjon.domain.repository.impl

import com.google.firebase.auth.ktx.auth
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import kotlinx.coroutines.channels.awaitClose
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.callbackFlow
import uz.gita.fooddeliveryumidjon.data.local.MySharedPreferences
import uz.gita.fooddeliveryumidjon.domain.repository.AuthRepository

class AuthRepositoryImpl : AuthRepository {
    private val myPrefs = MySharedPreferences.getInstance()
    private val auth = Firebase.auth
    private val db = Firebase.firestore

    companion object {
        private var instance: AuthRepositoryImpl? = null

        fun getInstance(): AuthRepository {
            if (instance == null) {
                instance = AuthRepositoryImpl()
            }
            return instance!!
        }
    }

    override fun signIn(email: String, password: String): Flow<Result<Unit>> = callbackFlow {
        auth.signInWithEmailAndPassword(email, password)
            .addOnSuccessListener {
                myPrefs.isSignedIn = true
                trySend(Result.success(Unit))
            }
            .addOnFailureListener {
                trySend(Result.failure(it))
            }
        awaitClose()
    }

    override fun signUp(userName: String, email: String, password: String): Flow<Result<Unit>> = callbackFlow {
        auth.createUserWithEmailAndPassword(email, password)
            .addOnSuccessListener {
                myPrefs.isSignedIn = true
                myPrefs.currentUserName = userName
                trySend(Result.success(Unit))
            }
            .addOnFailureListener {
                trySend(Result.failure(it))
            }
        awaitClose()
    }

    override fun sigOut() {
        auth.signOut()
        myPrefs.isSignedIn = false
        myPrefs.currentUserName = null
    }
}