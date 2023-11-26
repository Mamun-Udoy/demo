package com.example.navifationview.recyclerview

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.example.navifationview.entities.ExpenseInfo
import com.example.navifationview.databinding.FragmentEditInformationBinding
import com.example.navifationview.userViewModel.UserViewModel
import com.google.gson.Gson


class FragmentEditInformation : Fragment() {

    private val viewModel by viewModels<UserViewModel>()


    private lateinit var binding: FragmentEditInformationBinding
    private var dataModel: ExpenseInfo? = null


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        binding = FragmentEditInformationBinding.inflate(layoutInflater)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)



        val dataStr = arguments?.getString("data_model")
        dataModel = Gson().fromJson(dataStr,ExpenseInfo::class.java)

        // setting data
//        binding.apply {
//            expanseName.setText(dataModel?.expenseType)
//            expanseAmount.setText(dataModel?.expenseAmount.toString())
//            expanseDescription.setText(dataModel?.expenseDescription)
//        }
        binding.editFragment=dataModel




        binding.button.setOnClickListener {

            val updatedExpenseName = binding.expanseName.text.toString()
            val updatedExpenseAmount = binding.expanseAmount.text.toString()
            val updatedExpenseDescription = binding.expanseDescription.text.toString()

            dataModel?.expenseType = updatedExpenseName
            dataModel?.expenseAmount = updatedExpenseAmount.toLong()
            dataModel?.expenseDescription = updatedExpenseDescription

            val updatedBundle = Bundle()
            updatedBundle.putString("data_model", Gson().toJson(dataModel))

            findNavController().previousBackStackEntry?.savedStateHandle?.set("data_", updatedBundle)
            findNavController().popBackStack()

        }


    }


}