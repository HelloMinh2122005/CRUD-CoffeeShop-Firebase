package com.example.coffeeshop

import android.os.Bundle
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.example.coffeeshop.databinding.ActivityDrinkDetailBinding
import com.google.firebase.database.FirebaseDatabase

class DrinkDetailActivity : AppCompatActivity() {

    private lateinit var binding: ActivityDrinkDetailBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()

        binding = ActivityDrinkDetailBinding.inflate(layoutInflater)
        setContentView(binding.root)

        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        setValueToView()

        binding.btnDelete.setOnClickListener {
            deleteRecord(intent.getStringExtra("drinkId").toString())
        }

        binding.btnUpdate.setOnClickListener {
        }
    }

    private fun deleteRecord(id: String) {
        val dbRef = FirebaseDatabase.getInstance().getReference("Drinks").child(id)
        val mTask = dbRef.removeValue()
        mTask.addOnSuccessListener {
            Toast.makeText(this, "Delete success", Toast.LENGTH_SHORT).show()
            finish()
        }.addOnFailureListener { error ->
            Toast.makeText(this, "Lỗi: ${error.message}", Toast.LENGTH_SHORT).show()
        }
    }

    private fun setValueToView() {
        binding.editDrinkId.setText(intent.getStringExtra("drinkId"))
        binding.editDrinkName.setText(intent.getStringExtra("drinkName"))
        binding.editDrinkType.setText(intent.getStringExtra("drinkType"))
        binding.editDrinkPrice.setText(intent.getStringExtra("drinkPrice"))
    }


}