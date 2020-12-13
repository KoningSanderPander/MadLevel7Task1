package nl.svdoetelaar.madlevel7task1.ui

import android.app.Activity
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.navigation.fragment.findNavController
import com.google.android.material.snackbar.Snackbar
import nl.svdoetelaar.madlevel7task1.R
import nl.svdoetelaar.madlevel7task1.databinding.FragmentCreateProfileBinding
import nl.svdoetelaar.madlevel7task1.models.Profile
import nl.svdoetelaar.madlevel7task1.viewmodels.ProfileViewModel

class CreateProfileFragment : Fragment() {

    private val profileViewModel: ProfileViewModel by activityViewModels()

    companion object {
        const val GALLERY_REQUEST_CODE = 100
    }

    private var profileImageUri: Uri? = null

    private lateinit var binding: FragmentCreateProfileBinding


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentCreateProfileBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.btnGallery.setOnClickListener { onGalleryClick() }
        binding.btnConfirm.setOnClickListener { onConfirmClick() }
    }

    private fun onGalleryClick() {
        val galleryIntent = Intent(Intent.ACTION_PICK)

        galleryIntent.type = "image/*"

        startActivityForResult(galleryIntent, GALLERY_REQUEST_CODE)
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        if (resultCode == Activity.RESULT_OK) {
            when (requestCode) {
                GALLERY_REQUEST_CODE -> {
                    profileImageUri = data?.data
                    binding.ivProfileImage.setImageURI(profileImageUri)
                }
            }
        }
    }

    private fun onConfirmClick() {
        profileViewModel.createProfile(
            Profile(
                binding.etFirstName.text.ifNullOrEmpty(""),
                binding.etLastName.text.ifNullOrEmpty(""),
                binding.etProfileDescription.text.ifNullOrEmpty(""),
                profileImageUri.ifNullOrEmpty()
            )
        )

        observeProfileCreation()

        findNavController().navigate(R.id.action_createProfileFragment_to_profileFragment)
    }

    private fun CharSequence?.ifNullOrEmpty(default: String) =
        if (this.isNullOrEmpty()) default else this.toString()

    private fun Uri?.ifNullOrEmpty() = this.toString()

    private fun observeProfileCreation() {
        profileViewModel.createSucces.observe(viewLifecycleOwner, {
            Snackbar.make(
                requireView(),
                getText(R.string.successfully_created_profile),
                Snackbar.LENGTH_SHORT
            ).show()

            findNavController().popBackStack()
        })

        profileViewModel.errorText.observe(viewLifecycleOwner, {
            Snackbar.make(requireView(), it, Snackbar.LENGTH_SHORT).show()
        })
    }
}