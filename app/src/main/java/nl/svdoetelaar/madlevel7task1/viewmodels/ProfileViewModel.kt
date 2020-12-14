package nl.svdoetelaar.madlevel7task1.viewmodels

import android.app.Application
import android.util.Log
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.launch
import nl.svdoetelaar.madlevel7task1.models.Profile
import nl.svdoetelaar.madlevel7task1.repositories.ProfileRepository

class ProfileViewModel(application: Application) : AndroidViewModel(application) {
    private val TAG = "FIRESTORE"
    private val profileRepository = ProfileRepository()

    val profile = profileRepository.profile
    val createSuccess = profileRepository.createSuccess

    private val _errorText: MutableLiveData<String> = MutableLiveData()
    val errorText: LiveData<String> get() = _errorText

    fun getProfile() {
        viewModelScope.launch {
            try {
                profileRepository.getProfile()
            } catch (e: ProfileRepository.ProfileRetrievalError) {
                val errorMsg = "Something went wrong while retrieving profile"
                Log.e(TAG, e.message ?: errorMsg)
                _errorText.value = errorMsg
            }
        }
    }

    fun createProfile(profile: Profile) {
        viewModelScope.launch {
            try {
                profileRepository.createProfile(profile)
            } catch (e: ProfileRepository.ProfileSaveError) {
                val errorMsg = "Something went wrong while saving profile"
                Log.e(TAG, e.message ?: errorMsg)
                _errorText.value = errorMsg
            }
        }
    }


}