package com.example.food.Activity

import android.os.Bundle
import android.view.View
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.RecyclerView.Adapter
import com.example.food.Adapter.CartAdapter
import com.example.food.Helper.ChangeNumberItemsListener
import com.example.food.Helper.ManagmentCart
import com.example.food.R
import com.example.food.databinding.ActivityCartBinding
import com.example.food.databinding.ActivityDetailBinding
import com.example.food.databinding.ActivityListFoodBinding

class CartActivity : BaseActivity() {

    private lateinit var binding: ActivityCartBinding
    private lateinit var adapter: RecyclerView.Adapter<*>
    private lateinit var managementCart: ManagmentCart
    private var tax: Double = 0.0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityCartBinding.inflate(layoutInflater)
        setContentView(binding.root)
        enableEdgeToEdge()

        managementCart = ManagmentCart(this)

        setVariable();
        calculateCart();
        initList();
    }

    private fun initList() {
        if (managementCart.listCart.isEmpty()) {
            binding.emptyTxt.visibility = View.VISIBLE
            binding.scrollviewCart.visibility = View.GONE
        } else {
            binding.emptyTxt.visibility = View.GONE
            binding.scrollviewCart.visibility = View.VISIBLE
        }

        val linearLayoutManager = LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false)
        binding.cardView.layoutManager = linearLayoutManager
        adapter = CartAdapter(managementCart.listCart, this, object : ChangeNumberItemsListener {
            override fun change() {
                calculateCart()
            }
        })

        binding.cardView.adapter = adapter

    }


    private fun calculateCart() {
        val percentTax = 0.02
        val delivery = 10.0

        tax = Math.round(managementCart.totalFee * percentTax * 100.0) / 100.0

        val total = Math.round((managementCart.totalFee + tax + delivery) * 100) / 100.0

        val itemTotal = Math.round(managementCart.totalFee * 100.0) / 100.0

        binding.totalFeeTxt.text = "$$itemTotal"
        binding.taxTxt.text = "$$tax"
        binding.deliveryTxt.text = "$$delivery"
        binding.totalTxt.text = "$$total"
    }


    private fun setVariable() {

        binding.backBtn.setOnClickListener { finish(); }
    }
}