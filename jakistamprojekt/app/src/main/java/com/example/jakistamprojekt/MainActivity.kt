package com.example.jakistamprojekt

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.main_layout)

        val editName: EditText = findViewById(R.id.editName)
        val editSecondName: EditText = findViewById(R.id.editSecondName)
        val editAge: EditText = findViewById(R.id.editAge)
        val editHeight: EditText = findViewById(R.id.editHeight)
        val editWeight: EditText = findViewById(R.id.editWeight)
        val saveButton: Button = findViewById(R.id.Save)
        val secondScreenButton: Button = findViewById(R.id.button)

        saveButton.setOnClickListener {
            if (validateInputs(editName, editSecondName, editAge, editHeight, editWeight)) {
                saveData(editName, editSecondName, editAge, editHeight, editWeight)
            }
        }

        secondScreenButton.setOnClickListener {
            val intent = Intent(this, SecondScreen::class.java)
            startActivity(intent)
        }
    }

    private fun validateInputs(
        editName: EditText,
        editSecondName: EditText,
        editAge: EditText,
        editHeight: EditText,
        editWeight: EditText
    ): Boolean {
        val name = editName.text.toString().trim()
        val secondName = editSecondName.text.toString().trim()
        val age = editAge.text.toString().trim()
        val height = editHeight.text.toString().trim()
        val weight = editWeight.text.toString().trim()

        if (name.isEmpty()) {
            showToast("Imię jest obowiązkowe")
            return false
        }

        if (secondName.isEmpty()) {
            showToast("Nazwisko jest obowiązkowe")
            return false
        }

        val ageInt = age.toIntOrNull()
        if (age.isEmpty() || ageInt == null || ageInt <= 0 || ageInt > 122) {
            showToast("Uzupełnij wiek. 0>=122")
            return false
        }

        val heightInt = height.toIntOrNull()
        if (height.isEmpty() || heightInt == null || heightInt < 50 || heightInt > 250) {
            showToast("Uzupełnij wzrost. 50>=250")
            return false
        }

        val weightInt = weight.toIntOrNull()
        if (weight.isEmpty() || weightInt == null || weightInt < 3 || weightInt > 200) {
            showToast("Uzupełnij wagę. 3>=200")
            return false
        }

        return true
    }

    private fun showToast(message: String) {
        Toast.makeText(this, message, Toast.LENGTH_SHORT).show()
    }

    private fun saveData(
        editName: EditText,
        editSecondName: EditText,
        editAge: EditText,
        editHeight: EditText,
        editWeight: EditText
    ) {
        val sharedPreferences = getSharedPreferences("UserData", Context.MODE_PRIVATE)
        val editor = sharedPreferences.edit()

        val existingData = sharedPreferences.getStringSet("USER_DATA", mutableSetOf()) ?: mutableSetOf()
        val newData = "Name: ${editName.text}, Second Name: ${editSecondName.text}, Age: ${editAge.text}, Height: ${editHeight.text}, Weight: ${editWeight.text}"
        existingData.add(newData)

        editor.putStringSet("USER_DATA", existingData)
        editor.apply()

        showToast("Pomyślnie dodano")
    }
}