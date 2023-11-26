package com.example.navifationview

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.PopupMenu
import android.widget.Toast
import androidx.fragment.app.viewModels
import androidx.navigation.findNavController
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.navifationview.adapter.MyAdapter
import com.example.navifationview.entities.ExpenseInfo
import com.example.navifationview.userViewModel.UserViewModel
import com.example.navifationview.databinding.FragmentDisplayBinding
import com.google.gson.Gson
import java.text.SimpleDateFormat
import java.util.Date


class DisplayFragment : Fragment(), MyAdapter.ItemClickListener {
    private lateinit var binding: FragmentDisplayBinding
    private lateinit var expansename: String
    private lateinit var expanseamount: String
    private lateinit var expenseAdapter: MyAdapter


    private var position: Int = -1
    val expensDataList: ArrayList<ExpenseInfo> by lazy { arrayListOf() }

    private val viewModel by viewModels<UserViewModel>()


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        binding = FragmentDisplayBinding.inflate(inflater)
        return binding.root

    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        init()
        getData()
        observers()
//        observers2()
        observers3()
//        val expenseList: List<ExpenseInfo> = viewModel.getAllData(requireContext())
//        totalexpense(expenseList)


        binding.button.setOnClickListener {

            expansename = binding.expanseName.text.toString()
            expanseamount = binding.expanseAmount.text.toString()
            var description: String = binding.expanseDescription.text.toString()
            val time: String = time()

            if (isValidated(expansename, expanseamount)) {
                binding.expanseName.setText("")
                binding.expanseAmount.setText("")
                binding.expanseDescription.setText("")


                viewModel.insertData(
                    ExpenseInfo(0, expansename, expanseamount.toLong(), time, description),
                    requireContext()
                )
                getData()
            }
        }

    }

    private fun observers() {
//        viewModel.highestExpenseLiveData.observe(viewLifecycleOwner) {
//            binding.textView4.text = "Max ${it}"
//
//            Log.d("high_value", "observers: highest: $it")
//        }
    }
//    private fun observers2(){
//        viewModel.expenseAmount.observe(viewLifecycleOwner){
//            var total=0L
//            for(pos in it){
//                val amount=pos
//                total+=amount
//                Log.d("total__", "check the amount ${amount}")
//            }
//            Toast.makeText(context, "Your Total Expense = $total", Toast.LENGTH_SHORT).show()
//        }
//    }

    private fun observers3() {
        viewModel.expenseSum.observe(viewLifecycleOwner) {
            Toast.makeText(context, "Your Total Expense = $it", Toast.LENGTH_SHORT).show()
        }
    }

    private fun getData() {
        val data = viewModel.getAllData(requireContext())
        expenseAdapter.updateList(data)

    }


    private fun time(): String {

        val sdf = SimpleDateFormat("dd-MM-yyyy HH:mm:ss")

        val currentDateAndTime = sdf.format(Date())

        return currentDateAndTime

    }

    private fun init() {
        binding.userViewModel = viewModel
        binding.lifecycleOwner = viewLifecycleOwner

        expenseAdapter = MyAdapter(expensDataList, this)
        binding.recyclerview.layoutManager = LinearLayoutManager(requireContext())
        binding.recyclerview.adapter = expenseAdapter
        getBackStackData()
        viewModel.getAllData(requireContext())

    }

    private fun getBackStackData() {
        findNavController().currentBackStackEntry?.savedStateHandle?.getLiveData<Bundle>("data_")
            ?.observe(viewLifecycleOwner) {
                val str = it.getString("data_model")
                val dataModel = Gson().fromJson(str, ExpenseInfo::class.java)
                Log.d("LiveDataObserver", "LiveData Observer triggered with data: $dataModel")

//                Toast.makeText(context, "clicked", Toast.LENGTH_SHORT).show()
                viewModel.updateData(dataModel, requireContext())
                getData()
                //expensDataList[position] = dataModel
                //expenseAdapter.updateList(expensDataList)
                Log.d("bundle_db", "getBackStackData: dataModel:  $str")
            }
    }

    private fun isValidated(expansename: String, expanseamount: String): Boolean {
        if (expansename.isEmpty()) {
            Toast.makeText(requireContext(), "Name field is empty", Toast.LENGTH_SHORT).show()
            return false
        }
        if (expanseamount.isEmpty()) {
            Toast.makeText(requireContext(), "Amount field is empty", Toast.LENGTH_SHORT).show()
            return false
        }

        return true
    }

    override fun moreItemClicked(imageView: ImageView, item: ExpenseInfo, position: Int) {
        this.position = position
        val popupMenu = PopupMenu(context, imageView)
        popupMenu.menuInflater.inflate(R.menu.recyler_menu, popupMenu.menu)
        popupMenu.show()
        popupMenu.setOnMenuItemClickListener { it ->

            when (it.itemId) {
                R.id.delete -> {
                    viewModel.deleteData(item, requireContext())
                    getData()
                }

                R.id.edit -> {
//                    findNavController().navigate(R.id.fragmentEditInformation)
                    requireView().findNavController().navigate(
                        R.id.action_displayFragment_to_fragmentEditInformation2,
                        Bundle().apply {
                            putString("expensename", item.expenseType)
                            putString("expenseamount", "${item.expenseAmount}")
                            putString("expensedescription", item.expenseDescription)
                            putString("data_model", Gson().toJson(item))


                        })
                }
            }
            false

        }
    }

}