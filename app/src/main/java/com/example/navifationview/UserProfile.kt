package com.example.navifationview

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.viewModels
import androidx.navigation.findNavController
import com.example.navifationview.databinding.FragmentUserProfileBinding
import com.example.navifationview.entities.UserProfile
import com.example.navifationview.userViewModel.UserViewModel
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore


class UserProfile : Fragment() {

    private lateinit var binding: FragmentUserProfileBinding

    private val auth: FirebaseAuth = FirebaseAuth.getInstance()

    private val db: FirebaseFirestore = FirebaseFirestore.getInstance()

    private val viewModel by viewModels<UserViewModel>()

    private var datamodel: UserProfile? = null


//    val sharedPreferences: SharedPreferences?=null


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentUserProfileBinding.inflate(inflater)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val sharedPreferences =
            requireActivity().getSharedPreferences("MyPrefs", AppCompatActivity.MODE_PRIVATE)
        datamodel = viewModel.getUserProfile(requireContext())
        binding.userInfo = datamodel


        // Observe changes in userProfileData LiveData
//        viewModel.userProfileData.observe(viewLifecycleOwner) { userProfile ->
//            // Handle the updated user profile data
//            if (userProfile != null) {
//                binding.name.text = userProfile.username
//                binding.email.text = userProfile.usereamil
//                binding.number.text = userProfile.userphonenumber
//            }
//        }
//


        // here i get the value from sharedpreference
        // than storing the value in the variable getEmail and getPass
        // then just check it if not null then set it again

//        val getEmail = sharedPreferences?.getString("email", "")
//        val getPass = sharedPreferences?.getString("password", "")
//
//        if (getEmail != null && getEmail.isNotBlank() && getPass != null && getPass.isNotBlank()) {
//            db.collection("USERS").document(getEmail).get()
//                .addOnCompleteListener {
//                        task ->
//                    Log.d("doc_Db", "onViewCreated: name: ${task.result.get("name").toString()}")
//                    binding.name.text = task.result.get("name").toString()
//                    binding.email.text=task.result.get("email").toString()
//                    binding.number.text=task.result.get("phone number").toString()
//                }
//        }
//
//
//        // here i assigning the value for the first time and setting it into the textview to show the valu of user
//        // given as input.
//
//        val email: String? = arguments?.getString("email")
//        val pass: String? = arguments?.getString("password")
//        Log.d("doc_Db", "onViewCreated: email: $email, pass: $pass")
//        if (email != null) {
//            db.collection("USERS").document(email).get()
//                .addOnCompleteListener {
//                    task ->
//                    Log.d("doc_Db", "onViewCreated: name: ${task.result.get("name").toString()}")
//                    binding.name.text = task.result.get("name").toString()
//                    binding.email.text=task.result.get("email").toString()
//                    binding.number.text=task.result.get("phone number").toString()
//                }
//        }

        binding.logout.setOnClickListener {

            val currentUser = auth.currentUser
            if (currentUser != null) {
                // Sign out
                auth.signOut()
            }

            // Clear values from SharedPreferences
            val editor = sharedPreferences.edit()
            editor.remove("email")
            editor.remove("password")
            editor.apply()

            // Navigate to the login screen or perform other necessary actions
            requireView().findNavController().navigate(R.id.action_userProfile_to_loginFragment)

        }
    }


}