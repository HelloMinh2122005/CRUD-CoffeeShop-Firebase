package com.example.coffeeshop

import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.example.coffeeshop.databinding.ActivityInsertBinding
import com.example.coffeeshop.databinding.ActivityMainBinding
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase

class InsertActivity : AppCompatActivity() {

    private lateinit var binding: ActivityInsertBinding
    private lateinit var dbRef: DatabaseReference

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()

        binding = ActivityInsertBinding.inflate(layoutInflater)
        setContentView(binding.root)
        dbRef = FirebaseDatabase.getInstance().getReference("Drinks")

        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        // save
        binding.button.setOnClickListener {
            Log.d("InsertActivity", "Button clicked")
            saveDrinkData()
        }

    }

    private fun saveDrinkData() {
        val drinkName = binding.edtDrinkName.text.toString()
        val drinkType = binding.edtDrinkType.text.toString()
        val drinkPrice = binding.edtDrinkPrice.text.toString()

        var errors = false
        if (drinkName.isEmpty()) {
            binding.edtDrinkName.error = "Please enter drink name"
            errors = true
        }
        if (drinkType.isEmpty()) {
            binding.edtDrinkType.error = "Please enter drink type"
            errors = true
        }
        if (drinkPrice.isEmpty()) {
            binding.edtDrinkPrice.error = "Please enter drink price"
            errors = true
        }

        if (errors) {
            return
        }

        // day du lieu
        val drinkId = dbRef.push().key!!
        val drink = DrinkModel(drinkId, drinkName, drinkType, drinkPrice)

        dbRef.child(drinkId).setValue(drink)
            .addOnCompleteListener {
                binding.edtDrinkName.text.clear()
                binding.edtDrinkType.text.clear()
                binding.edtDrinkPrice.text.clear()
                Toast.makeText(this, "Data inserted successfully", Toast.LENGTH_LONG).show()
            } .addOnFailureListener { err ->
                Toast.makeText(this, "Error ${err.message}", Toast.LENGTH_LONG).show()
            }
    }
}