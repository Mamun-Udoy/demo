package com.example.navifationview

import CheckoutViewModel
import android.annotation.SuppressLint
import android.content.DialogInterface
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.Menu
import android.widget.Toast
import androidx.activity.viewModels
import androidx.appcompat.app.AlertDialog
import androidx.navigation.NavController
import androidx.navigation.findNavController
import com.example.navifationview.checkout_database.CheckOutItemInsertViewModel
import com.example.navifationview.databinding.ActivityMainBinding
import com.example.navifationview.databinding.DrawerHeaderBinding
import com.example.navifationview.userViewModel.UserViewModel
import com.google.android.material.badge.BadgeDrawable
import com.google.android.material.badge.BadgeUtils
import com.google.android.material.badge.ExperimentalBadgeUtils
import com.google.firebase.database.FirebaseDatabase

@ExperimentalBadgeUtils class MainActivity : AppCompatActivity(){

    private lateinit var binding: ActivityMainBinding

    private val badge: BadgeDrawable by lazy { BadgeDrawable.create(this) }


    private val viewModel by viewModels<UserViewModel>()

    private val viewModel3 by viewModels<CheckoutViewModel>()



    private val viewmodel4 by viewModels<CheckOutItemInsertViewModel>()






    lateinit var navController: NavController


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)


//        supportFragmentManager.beginTransaction()
//            .replace(R.id.mainfragment, DisplayFragment())
//            .commit()

        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        navController = findNavController(R.id.mainfragment)
        getDataCount()
//        badge = BadgeDrawable.create(this)
//        setSupportActionBar(binding.topAppBar)

        setSupportActionBar(binding.topAppBar)
        //BadgeUtils.attachBadgeDrawable(badge, binding.topAppBar, R.id.fav)

        viewmodel4.dbSize.observe(this) { size ->
            Log.d("observer11","mainactivity value ${size}")
            updateBadge(size)
        }



//        navController = findNavController(R.id.mainfragment)
        navdrawer()

        topappbar()

        bottombar()

        drawerheader()

        testFirebase()


    }

    private fun getDataCount() {
        val count = viewModel.getCheckoutItemsSize(this)
        updateBadge(count?:0)
    }


    @SuppressLint("UnsafeOptInUsageError")
    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        val inflater = menuInflater
        inflater.inflate(R.menu.top_app_bar, menu)
        BadgeUtils.attachBadgeDrawable(badge, binding.topAppBar, R.id.fav)
//        badge.number=0
        return super.onCreateOptionsMenu(menu)

    }

//    @SuppressLint("UnsafeOptInUsageError")
//    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
//        val inflater = menuInflater
//        inflater.inflate(R.menu.top_app_bar, menu)
//
//        // Initialize badge and set initial value
////        badge = BadgeDrawable.create(this)
//        BadgeUtils.attachBadgeDrawable(badge, binding.topAppBar, R.id.fav)
//
//
//        return super.onCreateOptionsMenu(menu)
//    }
    private fun updateBadge(size: Int) {
        // Update the badge with the new database size
//        BadgeUtils.attachBadgeDrawable(badge, binding.topAppBar, R.id.fav)
        badge.number = size
        Log.d("badgeValue", "check badge value ${badge.number}")
        invalidateOptionsMenu()
    }




    private fun testFirebase() {
        val databaseReference = FirebaseDatabase.getInstance().getReference("test")

        val firstName = "John"
        val lastName = "Doe"

        databaseReference.child("user1").child("firstName").setValue(firstName)
        databaseReference.child("user1").child("lastName").setValue(lastName)
    }

    fun showtoast(message: String) {
        Toast.makeText(this, message, Toast.LENGTH_SHORT).show()

    }

    fun topappbar() {

        binding.topAppBar.setOnMenuItemClickListener {
            when (it.itemId) {
                R.id.fav -> {
//                    var navController: NavController = findNavController(R.id.mainfragment)
//                    navController.navigateTo(R.id.checkOut)

                    //val navController: NavController = findNavController(R.id.mainfragment)
                    navController.navigate(R.id.checkOut)


                    showtoast("you clicked favourite icon")
                    true
                }

                R.id.search -> {
                    showtoast("you clicked search icon")
                    true
                }

                R.id.more -> {
                    showtoast("you clicked more icon")
                    true
                }

                else -> false
            }
        }

    }

    fun bottombar() {
        //val navHostFragment = supportFragmentManager.findFragmentById(R.id.mainfragment) as NavHostFragment
        // val navController = navHostFragment.navController


        binding.bottomNavigationView.background = null
        binding.bottomNavigationView.setOnNavigationItemSelectedListener {
            when (it.itemId) {
                R.id.home -> {
                    showtoast("you clicked home")
                    true
                }

                R.id.contact -> {

                    var navController: NavController = findNavController(R.id.mainfragment)
                    navController.navigateTo(R.id.retrofitFragment)
                    showtoast("you clicked contact")
                    true
                }

                R.id.person -> {
                    var navController: NavController = findNavController(R.id.mainfragment)
                    //navController.navigate(R.id.action_displayFragment_to_loginFragment)
                    navController.navigateTo(R.id.loginFragment)
                    showtoast("you clicked person")
                    true
                }

                R.id.settings -> {

                    navController.navigate(R.id.paginationFragment)
                    showtoast("you clicked settings")
                    true
                }

                else -> false

            }
        }

    }

    fun navdrawer() {
        binding.topAppBar.setNavigationOnClickListener {
            binding.drawerlayout.open()
        }
        binding.sidenav.setNavigationItemSelectedListener {
            when (it.itemId) {
                R.id.nhome -> {
                    showtoast("you selected home")
                    it.isChecked = true
                    true
                }

                R.id.android -> {
                    showtoast("you selected android")
                    it.isChecked = true
                    true
                }

                R.id.settings -> {
                    showtoast("you selected settings")
                    it.isChecked = true
                    true
                }

                R.id.email -> {
                    showtoast("you selected email us")
                    it.isChecked = true
                    true
                }

                R.id.power -> {
                    AlertDialog.Builder(this)
                        .setTitle("Quit Confirmation")
                        .setMessage("Are you sure, you want to close the app?")
                        .setPositiveButton("Yes") { dialogInterface: DialogInterface, i: Int ->
                            finish()
                        }
                        .setNegativeButton("NO", null)
                        .show()
                    true


                }

                else -> false
            }
        }
    }

    fun drawerheader() {
        val headerbinding: DrawerHeaderBinding =
            DrawerHeaderBinding.bind(binding.sidenav.getHeaderView(0))

        headerbinding.imageview.setOnClickListener {
            showtoast("you clicked logo")
        }
        headerbinding.textview1.setOnClickListener {
            showtoast("you clicked text1")
        }
        headerbinding.textview2.setOnClickListener {
            showtoast("you clicked text2")
        }
    }


//    override fun onCheckoutClicked() {
//        c++
//        Log.d("what", "check c value $c")
//        badge(c)
//    }

    // Implement the CheckoutClickListener interface


}