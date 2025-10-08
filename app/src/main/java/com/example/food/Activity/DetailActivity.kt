package com.example.food.Activity

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.bumptech.glide.Glide
import com.example.food.Domain.Foods
import com.example.food.Helper.ManagmentCart
import com.example.food.R
import com.example.food.databinding.ActivityDetailBinding

class DetailActivity : BaseActivity() {

    private lateinit var binding: ActivityDetailBinding
    private lateinit var foodObject: Foods
    private var num: Int = 1
    private lateinit var managementCart: ManagmentCart


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityDetailBinding.inflate(layoutInflater)
        setContentView(binding.root)
        window.statusBarColor = resources.getColor(R.color.black)
        getIntentExtra()
        setVariable()
    }

    private fun getIntentExtra() {
        foodObject = intent.getSerializableExtra("object") as? Foods ?: Foods()
    }

    private fun setVariable() {
        managementCart = ManagmentCart(this)
        binding.backBtn.setOnClickListener { finish() }

        Glide.with(this)
            .load(foodObject.imagePath)
            .into(binding.pic)

        binding.priceTxt.text = "$${foodObject.price}"
        binding.titleTxt.text = foodObject.title
        binding.descriptionTxt.text = foodObject.description
        binding.rateTxt.text = "${foodObject.star} Rating"
        binding.ratingBar.rating = foodObject.star.toFloat()
        binding.totalTxt.text = "${num * foodObject.price}$"

        binding.plusBtn.setOnClickListener {
            num += 1
            binding.numTxt.text = "$num"
            binding.totalTxt.text = "$${num * foodObject.price}"
        }

        binding.minusBtn.setOnClickListener {
            if (num>1) {
                num -= 1
                binding.numTxt.text = "$num"
                binding.totalTxt.text = "$${num * foodObject.price}"
            }
        }

        binding.addBtn.setOnClickListener {
            foodObject.numberInCart = num
            managementCart.insertFood(foodObject)
        }


    }
}
