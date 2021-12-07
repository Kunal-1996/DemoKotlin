package com.example.demokotlin.fragments.home

import android.annotation.SuppressLint
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.FragmentActivity
import androidx.fragment.app.FragmentTransaction
import androidx.navigation.NavController
import androidx.navigation.Navigation
import androidx.navigation.fragment.findNavController


import com.example.demokotlin.R
import com.example.demokotlin.R.id.action_addDataFragment_to_homeFragment
import com.example.demokotlin.databinding.FragmentAddDataBinding
import com.example.demokotlin.databinding.FragmentHomeBinding
import com.example.demokotlin.fragments.extentions.snack
import com.example.demokotlin.model.User
import com.google.android.material.snackbar.Snackbar
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.remoteconfig.FirebaseRemoteConfig


class AddDataFragment : Fragment() {



    private lateinit var remoteConfig: FirebaseRemoteConfig
    private lateinit var database : DatabaseReference
    private lateinit var homeFragment: HomeFragment
    private lateinit var binding: FragmentAddDataBinding
    lateinit var navController: NavController

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        binding= FragmentAddDataBinding.inflate(inflater, container, false)
        return binding.root

    }


    @SuppressLint("UseRequireInsteadOfGet")
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)



        setHasOptionsMenu(true)
        (activity as AppCompatActivity).supportActionBar?.title = "AddData"
            binding.btBack.setOnClickListener {
            findNavController().navigate(action_addDataFragment_to_homeFragment)
        }


        // Add data to firebase button from here
        binding.btAdd.setOnClickListener{

            val AddName = binding.etAddName.text.toString();
            val AddEmail = binding.etAddEmail.text.toString();
            val AddEducation = binding.etAddEducation.text.toString();
            val AddDesignation = binding.etAddDesignation.text.toString();
            val AddMobile = binding.etAddMobile.text.toString();
            val AddProfile = binding.etAddProfile.text.toString();


            //Database instance is created from here
            database = FirebaseDatabase.getInstance().getReference("Users")

            val User = User (AddName,AddEmail,AddEducation,AddDesignation,AddMobile,AddProfile)
            database.child(AddName).setValue(User).addOnSuccessListener {

                // Clear text from here
                binding.etAddName.text.clear();
                binding.etAddEmail.text.clear();
                binding.etAddEducation.text.clear();
                binding.etAddDesignation.text.clear();
                binding.etAddMobile.text.clear();
                binding.etAddProfile.text.clear();

                view?.snack("Successfully Saved")
            }.addOnFailureListener{

                view.snack("Failed")
            }
        }

    }




}