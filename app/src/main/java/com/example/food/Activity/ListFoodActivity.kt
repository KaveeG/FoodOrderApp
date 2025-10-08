package com.example.food.Activity

import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.food.Adapter.FoodListAdapter
import com.example.food.Domain.Foods
import com.example.food.databinding.ActivityListFoodBinding
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.Query
import com.google.firebase.database.ValueEventListener

class ListFoodActivity : BaseActivity() {

    private lateinit var binding: ActivityListFoodBinding
    private lateinit var adapterListFood: RecyclerView.Adapter<*>
    private var categoryId: Int = 0
    private var categoryName: String = ""
    private var searchText: String = ""
    private var isSearch: Boolean = false


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityListFoodBinding.inflate(layoutInflater)
        setContentView(binding.root)
        enableEdgeToEdge()

        getIntentExtra()

        initList()


    }

    private fun initList() {
        val myRef = super.database.getReference("Foods")
        binding.progressBar.visibility = View.VISIBLE
        val list = ArrayList<Foods>()

        val query: Query = if (isSearch) {
            myRef.orderByChild("Title").startAt(searchText).endAt(searchText + "\uf8ff")
        } else {
            myRef.orderByChild("CategoryId").equalTo(categoryId.toDouble())
        }

        query.addListenerForSingleValueEvent(object : ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                if (snapshot.exists()) {
                    for (issue in snapshot.children) {
                        issue.getValue(Foods::class.java)?.let { list.add(it) }
                    }
                    if (list.size>0) {
                        binding.foodListView.layoutManager = GridLayoutManager(this@ListFoodActivity, 2)
                        adapterListFood = FoodListAdapter(list)
                        binding.foodListView.adapter = adapterListFood
                    }else {
                        binding.progressBar.visibility = View.GONE
                    }
                }
            }

            override fun onCancelled(error: DatabaseError) {
                binding.progressBar.visibility = View.GONE
                Toast.makeText(this@ListFoodActivity, "Error: ${error.message}", Toast.LENGTH_SHORT).show()
            }
        })
    }


    private fun getIntentExtra() {
        val intent = intent
        categoryId = intent.getIntExtra("CategoryId", 0)
        categoryName = intent.getStringExtra("CategoryName") ?: ""
        searchText = intent.getStringExtra("text") ?: ""
        isSearch = intent.getBooleanExtra("isSearch", false)

        binding.titleTxt.text = categoryName
        binding.backBtn.setOnClickListener { finish() }

    }

}