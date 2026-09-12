package ru.social.demo.services

import android.util.Log
import com.google.firebase.Firebase
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.firestore
import ru.social.demo.data.model.BaseModel

object FsPath {
    const val POSTS = "posts"
    const val USERS = "users"
}

class FirestoreClient(
    val db: FirebaseFirestore
) {

    companion object {

        @Volatile
        private var instance: FirestoreClient? = null

        fun getInstance() = instance ?: synchronized(this) {
            FirestoreClient(db = Firebase.firestore).also { instance = it }
        }

    }

    fun <T : BaseModel> setData(
        path: String,
        data: T,
        id: String? = null,
        onSuccess: () -> Unit = {},
        onError: (String) -> Unit = {},
    ) {
        val key = id ?: db.collection(path).document().id
        db.collection(path)
            .document(key).set(data.apply { this.id = key })
            .addOnSuccessListener { result ->
                Log.d("TEST", "set result: $result ")
                onSuccess()
            }
            .addOnFailureListener { e ->
                Log.e("TEST","Firebase.firestore set error: $e")
                onError(e.message ?: "Can't set data to Firebase")
            }
    }

    inline fun <reified T> readData(
        path: String,
        crossinline onSuccess: (List<T>?) -> Unit,
        crossinline onError: (Exception) -> Unit = {},
    ) {
        db.collection(path).get()
            .addOnSuccessListener { result ->
                Log.d("TEST", "$path - read success ")
                onSuccess(result.toObjects(T::class.java))
            }
            .addOnFailureListener { e ->
                Log.e("TEST","$path - Firebase.firestore read error: $e")
                onError(e)
            }
    }

    inline fun <reified T> readData(
        path: String,
        docId: String,
        crossinline onSuccess: (T?) -> Unit,
        crossinline onError: (Exception) -> Unit = {},
    ) {
        db.collection(path).document(docId).get()
            .addOnSuccessListener { result ->
                Log.d("TEST", "$path - read success ")
                onSuccess(result.toObject(T::class.java))
            }
            .addOnFailureListener { e ->
                Log.e("TEST","$path - Firebase.firestore read error: $e")
                onError(e)
            }
    }

//    inline fun <reified T> readDataContinuously(path: String) {
//        val listener = {snapShot, e ->
//            postsList.value = snapShot?.toObjects(Post::class.java)?.sortedByDescending { it.createDate } ?: emptyList()
//        }
//        db.collection(FsPath.POSTS).addSnapshotListener(listener)
//        // !!! remove listener if it's unuseful: listener.remove()
//    }


    fun <T : BaseModel> updateData(
        path: String,
        data: T,
        onSuccess: () -> Unit = {},
        onError: () -> Unit = {},
    ) {
        val key = data.id
        db.collection(path).document(key).set(data)
            .addOnSuccessListener { result ->
                Log.d("TEST", "set result: $result ")
                onSuccess()
            }
            .addOnFailureListener { e ->
                Log.e("TEST","Firebase.firestore set error: $e")
                onError()
            }
    }

}