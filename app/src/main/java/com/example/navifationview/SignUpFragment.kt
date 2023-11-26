package com.example.navifationview

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.viewModels
import androidx.navigation.findNavController
import com.example.navifationview.databinding.FragmentSignUpBinding
import com.example.navifationview.entities.UserProfile
import com.example.navifationview.userViewModel.UserViewModel
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore


class SignUpFragment : Fragment() {
    private lateinit var binding: FragmentSignUpBinding

    private val auth: FirebaseAuth = FirebaseAuth.getInstance()

    private val db: FirebaseFirestore= FirebaseFirestore.getInstance()


    private val viewModel by viewModels<UserViewModel>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentSignUpBinding.inflate(inflater)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)


        binding.signIn.setOnClickListener {
            requireView().findNavController().navigate(R.id.action_signUp2_to_loginFragment)
        }




        binding.signUp.setOnClickListener {
            val userName: String? = binding.username.text.toString().trim()
            val userEmail: String? = binding.useremail.text.toString().trim()
            val userNumber: String? = binding.Userphonenumber.editText?.text?.toString()?.trim()
            val userPassword: String? = binding.userpassword.text.toString()
            val userConfrimPassword: String? = binding.userconfirmpassword.text.toString().trim()


            viewModel.insertUserData(
                UserProfile(0, userName,userEmail,userNumber),
                requireContext()

            )


            // well here i used firbasefirestore to store the value
            // use a variable user that is a hashmap and containing user data and documentation says its a collection
            // and collection containing with few data's that known as document and this few data is known as data which is kept inside of document
            // so creadted a hashmap
            // then taken a variable that storing the the data ase collection and given the path of collection
            // then make a query to search in my collection of email is taht exist in previous.
            val user= hashMapOf(
                "name" to userName,
                "email" to userEmail,
                "phone number" to userNumber
            )

            val users=db.collection("USERS")
            val query = users.whereEqualTo("email",userEmail).get()
                .addOnSuccessListener { Task->
                    if(Task.isEmpty){

                        if (userName.isNullOrEmpty() or userEmail.isNullOrEmpty() or userNumber.isNullOrEmpty() or userPassword.isNullOrEmpty() or userConfrimPassword.isNullOrEmpty()) {
                            Log.d("name", "print the name $userName")
                            Toast.makeText(context, "plz fill all the required info", Toast.LENGTH_SHORT).show()
                        } else if (userPassword != userConfrimPassword) {

                            Toast.makeText(context, "password does not match", Toast.LENGTH_SHORT).show()
                        } else {
                            if (userEmail != null) {
                                if (userPassword != null) {

                                    users.document(userEmail).set(user)
                                    auth.createUserWithEmailAndPassword(userEmail, userPassword)
                                        .addOnCompleteListener { task ->
                                            if (task.isSuccessful) {
                                                Toast.makeText(
                                                    context,
                                                    "Registration sucssesful",
                                                    Toast.LENGTH_SHORT
                                                ).show()
                                                requireView().findNavController()
                                                    .navigate(R.id.action_signUp2_to_loginFragment)
                                            } else {
                                                Toast.makeText(
                                                    context,
                                                    "Registration Failed: ${task.exception?.message}",
                                                    Toast.LENGTH_SHORT
                                                ).show()
                                            }

                                        }
                                }
                            }

                        }



                    }
                    else{
                        Toast.makeText(context, "User Already Registered", Toast.LENGTH_SHORT).show()
                    }
                }


//            if (userName.isNullOrEmpty() or userEmail.isNullOrEmpty() or userNumber.isNullOrEmpty() or userPassword.isNullOrEmpty() or userConfrimPassword.isNullOrEmpty()) {
//                Log.d("name", "print the name $userName")
//                Toast.makeText(context, "plz fill all the required info", Toast.LENGTH_SHORT).show()
//            } else if (userPassword != userConfrimPassword) {
//
//                Toast.makeText(context, "password does not match", Toast.LENGTH_SHORT).show()
//            } else {
//                if (userEmail != null) {
//                    if (userPassword != null) {
//                        auth.createUserWithEmailAndPassword(userEmail, userPassword)
//                            .addOnCompleteListener { task ->
//                                if (task.isSuccessful) {
//                                    Toast.makeText(
//                                        context,
//                                        "Registration sucssesful",
//                                        Toast.LENGTH_SHORT
//                                    ).show()
//                                    requireView().findNavController()
//                                        .navigate(R.id.action_signUp2_to_loginFragment)
//                                } else {
//                                    Toast.makeText(
//                                        context,
//                                        "Registration Failed: ${task.exception?.message}",
//                                        Toast.LENGTH_SHORT
//                                    ).show()
//                                }
//
//                            }
//                    }
//                }
//
//            }

        }

    }


}