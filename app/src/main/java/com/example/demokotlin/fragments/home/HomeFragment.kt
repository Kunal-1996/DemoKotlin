package com.example.demokotlin.fragments.home

import android.annotation.SuppressLint
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.FragmentTransaction
import androidx.navigation.NavController
import androidx.navigation.Navigation
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.demokotlin.R
import com.example.demokotlin.R.id.action_homeFragment_to_addDataFragment
import com.example.demokotlin.adapter.UserAdapter
import com.example.demokotlin.databinding.ActivityMainBinding
import com.example.demokotlin.databinding.FragmentHomeBinding
import com.example.demokotlin.model.User
import com.google.android.material.floatingactionbutton.FloatingActionButton
import com.google.firebase.database.*


class HomeFragment : Fragment() {

    private lateinit var dbref : DatabaseReference

    private lateinit var userArrayList : ArrayList<User>

    private lateinit var addDataFragment: AddDataFragment

    private lateinit var recyclerView: RecyclerView

    private lateinit var navController: NavController


    var title=""
    var message=""

    private lateinit var binding: FragmentHomeBinding


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        binding=FragmentHomeBinding.inflate(inflater, container, false)


        return binding.root
    }


    @SuppressLint("UseRequireInsteadOfGet")
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        navController = Navigation.findNavController(
            activity!!,
            R.id.Frag_Container
        )
        navController.navigateUp()
        recyclerView = requireView().findViewById<RecyclerView>(R.id.rvMainfragment)
        recyclerView.layoutManager = LinearLayoutManager(requireContext())
        userArrayList = arrayListOf<User>()

       // val floatingactionbutton = requireView().findViewById<FloatingActionButton>(R.id.floatingactionbutton)
        // GetUserData Function called
        getUserData()

        setHasOptionsMenu(true)
        (activity as AppCompatActivity).supportActionBar?.title = "Home"

        // Intent to the Adddata Fragment from here
        binding.floatingactionbutton.setOnClickListener {


           findNavController().navigate(R.id.action_homeFragment_to_addDataFragment)

//            navController.navigateUp()


            /*  val transaction = activity!!.supportFragmentManager
                  .beginTransaction()
                  .replace(R.id.Frag_Container, addDataFragment, "name2")
                  .setTransition(FragmentTransaction.TRANSIT_FRAGMENT_OPEN)
                  .setReorderingAllowed(true)
                  .addToBackStack("name")
                  .commit()*/
        }
    }
    // GetUserData to the firebase from here
    private fun getUserData() {
        dbref = FirebaseDatabase.getInstance().getReference("Users")

        dbref.addValueEventListener(object : ValueEventListener {

            override fun onDataChange(snapshot: DataSnapshot) {

                if (snapshot.exists()){
                    for (userSnapshot in snapshot.children){
                        val user = userSnapshot.getValue(User::class.java)
                        userArrayList.add(user!!)
                    }
                    recyclerView.adapter = UserAdapter(userArrayList)
                }
            }
            override fun onCancelled(error: DatabaseError) {
            }
        })

    }

}