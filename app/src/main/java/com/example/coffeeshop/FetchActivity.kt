package com.example.coffeeshop

import android.os.Bundle
import android.view.View
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.coffeeshop.adapter.DrinkAdapter
import com.example.coffeeshop.databinding.ActivityFetchBinding
import com.google.firebase.Firebase
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener

class FetchActivity : AppCompatActivity() {

    private lateinit var binding: ActivityFetchBinding
    private lateinit var list: ArrayList<DrinkModel>
    private lateinit var dbRef: DatabaseReference

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()

        binding = ActivityFetchBinding.inflate(layoutInflater)
        setContentView(binding.root)

        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        binding.rvDrink.layoutManager = LinearLayoutManager(this)
        binding.rvDrink.setHasFixedSize(true)

        list = arrayListOf<DrinkModel>()

        getDrinkData()
    }

    private fun getDrinkData() {
        binding.rvDrink.visibility = View.GONE
        binding.loadingData.visibility = View.VISIBLE
        dbRef = FirebaseDatabase.getInstance().getReference("Drinks")
        dbRef.addValueEventListener(object : ValueEventListener{
            override fun onDataChange(snapshot: DataSnapshot) {
                list.clear()
                if (snapshot.exists()){
                    for (drinkSnap in snapshot.children) {
                        val drinkData = drinkSnap.getValue(DrinkModel::class.java)
                        list.add(drinkData!!)
                    }
                    val mAdapter = DrinkAdapter(list)
                    binding.rvDrink.adapter = mAdapter
                    binding.rvDrink.visibility = View.VISIBLE
                    binding.loadingData.visibility = View.GONE
                }
            }

            override fun onCancelled(error: DatabaseError) {

            }
        })
    }
}