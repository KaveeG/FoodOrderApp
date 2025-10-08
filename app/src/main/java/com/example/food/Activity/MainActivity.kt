package com.example.food.Activity

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.ArrayAdapter
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.food.Adapter.BestFoodsAdapter
import com.example.food.Adapter.CategoryAdapter
import com.example.food.Domain.Category
import com.example.food.Domain.Foods
import com.example.food.Domain.Location
import com.example.food.Domain.Price
import com.example.food.Domain.Time
import com.example.food.R
import com.example.food.databinding.ActivityMainBinding
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.Query
import com.google.firebase.database.ValueEventListener

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding;
    private lateinit var database: FirebaseDatabase
    private lateinit var locationRef: DatabaseReference
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        enableEdgeToEdge()
        setContentView(binding.root)
        database = FirebaseDatabase.getInstance()

        initLocation();
        intTime();
        initPrice();
        initBestFood();
        initCategory();
        setVariable();

    }

    private fun setVariable() {
        binding.logoutBtn.setOnClickListener {
            FirebaseAuth.getInstance().signOut()
            startActivity(Intent(this@MainActivity, LoginActivity::class.java))
        }

        binding.searchBtn.setOnClickListener {
            val text = binding.searchEdt.text.toString()
            if (text.isNotEmpty()) {
                val intent = Intent(this@MainActivity, ListFoodActivity::class.java)
                intent.putExtra("text", text)
                intent.putExtra("isSearch", true)
                startActivity(intent)
            }
        }
        binding.cartBtn.setOnClickListener {
            startActivity(Intent(this@MainActivity, CartActivity::class.java))
        }


    }


    private fun initBestFood() {
        val myRef = database.getReference("Foods")
        binding.progressBarBestFood.visibility = View.VISIBLE
        val list = ArrayList<Foods>()

        val query = myRef.orderByChild("BestFood").equalTo(true)
        query.addListenerForSingleValueEvent(object : ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                if (snapshot.exists()) {
                    for (issue in snapshot.children) {
                        issue.getValue(Foods::class.java)?.let { list.add(it) }
                    }
                    if (list.isNotEmpty()) {
                        binding.bestFoodView.layoutManager = LinearLayoutManager(this@MainActivity, LinearLayoutManager.HORIZONTAL, false)
                        val adapter = BestFoodsAdapter(list)
                        binding.bestFoodView.adapter = adapter
                    }
                    binding.progressBarBestFood.visibility = View.GONE
                }
            }

            override fun onCancelled(error: DatabaseError) {
                binding.progressBarBestFood.visibility = View.GONE
            }
        })
    }

    private fun initCategory() {
        val myRef = database.getReference("Category")
        binding.progressBarCategory.visibility = View.VISIBLE
        val list = ArrayList<Category>()


        myRef.addListenerForSingleValueEvent(object : ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                if (snapshot.exists()) {
                    for (issue in snapshot.children) {
                        issue.getValue(Category::class.java)?.let { list.add(it) }
                    }
                    if (list.isNotEmpty()) {
                        binding.categoryView.layoutManager = GridLayoutManager(this@MainActivity,4)
                        val adapter = CategoryAdapter(list)
                        binding.categoryView.adapter = adapter
                    }
                    binding.progressBarCategory.visibility = View.GONE
                }
            }

            override fun onCancelled(error: DatabaseError) {
                binding.progressBarBestFood.visibility = View.GONE
            }
        })
    }


    private fun initLocation() {
        val myRef = database.reference.child("Location")
        val list = ArrayList<Location>()

        myRef.addListenerForSingleValueEvent(object : ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                if (snapshot.exists()) {
                    for (issue in snapshot.children) {
                        issue.getValue(Location::class.java)?.let { list.add(it) }
                    }
                    val adapter = ArrayAdapter(this@MainActivity, R.layout.sp_item, list)
                    adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
                    binding.locationSp.adapter = adapter
                }
            }

            override fun onCancelled(error: DatabaseError) {
                    Log.e("Firebase", "Error fetching locations: ${error.message}")
            }
        })
    }

    private fun intTime() {
        val myRef = database.reference.child("Time")
        val list = ArrayList<Time>()

        myRef.addListenerForSingleValueEvent(object : ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                if (snapshot.exists()) {
                    for (issue in snapshot.children) {
                        issue.getValue(Time::class.java)?.let { list.add(it) }
                    }
                    val adapter = ArrayAdapter(this@MainActivity, R.layout.sp_item, list)
                    adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
                    binding.timeSp.adapter = adapter
                }
            }

            override fun onCancelled(error: DatabaseError) {
                Log.e("Firebase", "Error fetching time: ${error.message}")
            }
        })
    }

    private fun initPrice() {
        val myRef = database.reference.child("Price")
        val list = ArrayList<Price>()

        myRef.addListenerForSingleValueEvent(object : ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                if (snapshot.exists()) {
                    for (issue in snapshot.children) {
                        issue.getValue(Price::class.java)?.let { list.add(it) }
                    }
                    val adapter = ArrayAdapter(this@MainActivity, R.layout.sp_item, list)
                    adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
                    binding.priceSp.adapter = adapter
                }
            }

            override fun onCancelled(error: DatabaseError) {
                Log.e("Firebase", "Error fetching price: ${error.message}")
            }
        })
    }

}