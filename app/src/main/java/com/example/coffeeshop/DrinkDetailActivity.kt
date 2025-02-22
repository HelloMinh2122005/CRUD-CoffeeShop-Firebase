package com.example.coffeeshop

import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AlertDialog
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
            openUpdateDialog(
                intent.getStringExtra("drinkId").toString(),
                intent.getStringExtra("drinkName").toString(),
                intent.getStringExtra("drinkType").toString(),
                intent.getStringExtra("drinkPrice").toString()
            )
        }
    }

    private fun openUpdateDialog(id: String, name: String, type: String, price: String) {
        val mDialog = AlertDialog.Builder(this)
        val inflater = layoutInflater
        val mDialogView = inflater.inflate(R.layout.update_dialog, null)
        mDialog.setView(mDialogView)

        // update thong tin cho dialog
        val nameEditText = mDialogView.findViewById<EditText>(R.id.nameEditText)
        val typeEditText = mDialogView.findViewById<EditText>(R.id.typeEditText)
        val priceEditText = mDialogView.findViewById<EditText>(R.id.priceEditText)
        nameEditText.setText(name)
        typeEditText.setText(type)
        priceEditText.setText(price)


        mDialog.setTitle("Updating $name Record")
        val alertDialog = mDialog.create()
        alertDialog.show()

        // click update
        val updateButton = mDialogView.findViewById<Button>(R.id.saveButton)
        updateButton.setOnClickListener {
            updateDrinkData(
                id,
                nameEditText.text.toString(),
                typeEditText.text.toString(),
                priceEditText.text.toString()
            )
        }
        // click cancel
        val cancelButton = mDialogView.findViewById<Button>(R.id.cancelButton)
        cancelButton.setOnClickListener {
            alertDialog.dismiss()
        }
    }

    private fun updateDrinkData(id: String, name: String, type: String, price: String) {
        val dbRef = FirebaseDatabase.getInstance().getReference("Drinks").child(id)
        val drinkInfo = DrinkModel(id, name, type, price)
        dbRef.setValue(drinkInfo)
        Toast.makeText(this, "Update success", Toast.LENGTH_SHORT).show()
        finish()
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