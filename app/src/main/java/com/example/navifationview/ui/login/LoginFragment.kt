package com.example.navifationview.ui.login

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.navigation.findNavController
import com.example.navifationview.R
import com.example.navifationview.databinding.FragmentLoginBinding
import com.google.firebase.auth.FirebaseAuth

class LoginFragment : Fragment() {
    lateinit var binding: FragmentLoginBinding

    private val auth: FirebaseAuth = FirebaseAuth.getInstance()
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentLoginBinding.inflate(inflater)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)


        val sharedPreferences =
            activity?.getSharedPreferences("MyPrefs", AppCompatActivity.MODE_PRIVATE)
        val getEmail = sharedPreferences?.getString("email", "")
        val getPass = sharedPreferences?.getString("password", "")

        if (getEmail != "" && getPass != "") {
            requireView().findNavController().navigate(R.id.action_loginFragment_to_userProfile)
        }





        binding.login.setOnClickListener {

            val email: String = binding.useremail.text.toString().trim()
            val password: String = binding.userpassword.text.toString().trim()


            if (email.isEmpty() || password.isEmpty()) {
                Toast.makeText(context, "plz fill all the required info", Toast.LENGTH_SHORT).show()
            } else {
                auth.signInWithEmailAndPassword(email, password).addOnCompleteListener { task ->
                    if (task.isSuccessful) {
                        val sharedPreferences = requireContext().getSharedPreferences(
                            "MyPrefs",
                            AppCompatActivity.MODE_PRIVATE
                        )
                        val editor = sharedPreferences?.edit()

                        if (editor != null) {
                            editor.putString("email", email)
                            editor.putString("password", password)
                            editor.apply()
                        }

                        val bundle = Bundle()
                        bundle.putString("email", email)
                        bundle.putString("password", password)
                        Toast.makeText(context, "Login Succesfull", Toast.LENGTH_SHORT).show()
                        requireView().findNavController()
                            .navigate(R.id.action_loginFragment_to_userProfile, bundle)
                    } else {
                        Toast.makeText(
                            context,
                            "sign in failed: ${task.exception?.message}",
                            Toast.LENGTH_SHORT
                        ).show()
                    }
                }
            }


        }

        binding.signUp.setOnClickListener {
            requireView().findNavController().navigate(R.id.action_loginFragment_to_signUp2)
        }
        binding.continueWithoutSignup.setOnClickListener {
            requireView().findNavController().navigate(R.id.action_loginFragment_to_displayFragment)
        }

//        binding.login.setOnClickListener {
//            if(TextUtils.isEmpty(email.toString())){
//                Toast.makeText(context,"Enter Your Email",Toast.LENGTH_SHORT).show()
//            }
//            if(TextUtils.isEmpty(password.toString())){
//                Toast.makeText(context,"plz enter your password",Toast.LENGTH_SHORT).show()
//            }
//        }

    }
}