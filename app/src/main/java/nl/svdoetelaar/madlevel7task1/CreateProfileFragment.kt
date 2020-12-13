package nl.svdoetelaar.madlevel7task1

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import nl.svdoetelaar.madlevel7task1.databinding.FragmentCreateProfileBinding

class CreateProfileFragment : Fragment() {

    companion object{
        const val GALLERY_REQUEST_CODE = 100
    }

    private lateinit var binding: FragmentCreateProfileBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentCreateProfileBinding.inflate(inflater, container, false)
        return binding.root
    }



}