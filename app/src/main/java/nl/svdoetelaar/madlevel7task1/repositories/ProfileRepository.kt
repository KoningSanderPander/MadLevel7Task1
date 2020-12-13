package nl.svdoetelaar.madlevel7task1.repositories

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.google.firebase.firestore.FirebaseFirestore
import kotlinx.coroutines.tasks.await
import kotlinx.coroutines.withTimeout
import nl.svdoetelaar.madlevel7task1.models.Profile

class ProfileRepository {
    private var firestore: FirebaseFirestore = FirebaseFirestore.getInstance()
    private var profileDocument = firestore.collection("profiles").document("profile")

    private val _profile: MutableLiveData<Profile> = MutableLiveData()
    val profile: LiveData<Profile> get() = _profile

    private val _createSuccess: MutableLiveData<Boolean> = MutableLiveData()
    val createSuccess: LiveData<Boolean> get() = _createSuccess

    suspend fun getProfile() {
        try {
            withTimeout(5_000L) {
                val data = profileDocument
                    .get()
                    .await()

                val firstName = data.getString("firstName").toString()
                val lastName = data.getString("lastName").toString()
                val description = data.getString("description").toString()
                val imageUri = data.getString("imageUri").toString()

                _profile.value = Profile(
                    firstName,
                    lastName,
                    description,
                    imageUri
                )
            }
        } catch (e: Exception) {
            throw ProfileRetrievalError("Retrieval-firebase-task was unsuccessful")
        }
    }

    suspend fun createProfile(profile: Profile) {
        try {
            withTimeout(5_000) {
                profileDocument
                    .set(profile)
                    .await()

                _createSuccess.value = true
            }
        } catch (e: Exception) {
            throw ProfileSaveError(e.message.toString(), e)
        }
    }


    class ProfileRetrievalError(s: String) : Exception(s)
    class ProfileSaveError(s: String, cause: Throwable) : Throwable(s, cause)
}