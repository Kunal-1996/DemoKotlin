package com.example.demokotlin.fragments.profile

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.DialogFragment
import coil.load
import coil.transform.CircleCropTransformation
import com.example.demokotlin.R
import com.example.demokotlin.databinding.FragmentProfileDialogBinding
import com.google.android.gms.auth.api.signin.GoogleSignInClient
import com.google.firebase.auth.FirebaseAuth
import javax.inject.Inject

// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"

/**
 * A simple [Fragment] subclass.
 * Use the [ProfileFragment.newInstance] factory method to
 * create an instance of this fragment.
 */
class ProfileDialogFragment(private val signOut : (() -> Unit)? = null) : DialogFragment() {

    @Inject
    var firebaseAuth= FirebaseAuth.getInstance()
    @Inject
    lateinit var mGoogleSignInClient : GoogleSignInClient

    private lateinit var binding : FragmentProfileDialogBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentProfileDialogBinding.inflate(inflater)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        setHasOptionsMenu(true)
        (activity as AppCompatActivity).supportActionBar?.title = "Profile"

        binding.lytSignOut.setOnClickListener {
            dismiss()
            signOut?.invoke()
        }

        firebaseAuth.currentUser?.let {

            for (profile in it.providerData) {

                val name = profile.displayName
                val email = profile.email
                val photoUrl = profile.photoUrl

                binding.tvUserName.text = name
                binding.tvEmail.text = email
                binding.imgProfile.load(photoUrl) {
                    crossfade(true)
                    placeholder(R.drawable.ic_account_circle_24)
                    transformations(CircleCropTransformation())
                }

            }
        }
    }
}