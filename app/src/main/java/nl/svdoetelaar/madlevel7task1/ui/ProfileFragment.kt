package nl.svdoetelaar.madlevel7task1.ui

import android.net.Uri
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import nl.svdoetelaar.madlevel7task1.R
import nl.svdoetelaar.madlevel7task1.databinding.FragmentProfileBinding
import nl.svdoetelaar.madlevel7task1.viewmodels.ProfileViewModel

class ProfileFragment : Fragment() {

    private val profileViewModel: ProfileViewModel by activityViewModels()

    companion object {
        const val GALLERY_REQUEST_CODE = 100
    }

    private var profileImageUri: Uri? = null

    private lateinit var binding: FragmentProfileBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        binding = FragmentProfileBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        observeProfile()
    }

    private fun observeProfile() {
        profileViewModel.profile.observe(viewLifecycleOwner, {
            binding.tvName.text = getString(R.string.profile_name, it.firstName, it.lastName)
            binding.tvDescription.text = it.description
            if (it.imageUri!!.isNotEmpty()) {
                binding.ivProfileImage.setImageURI(Uri.parse(it.imageUri))
            }
        })

        profileViewModel.getProfile()
    }

}