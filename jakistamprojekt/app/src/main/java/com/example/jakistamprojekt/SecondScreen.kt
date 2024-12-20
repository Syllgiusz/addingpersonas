package com.example.jakistamprojekt

import android.content.Context
import android.os.Bundle
import android.widget.Button
import android.widget.LinearLayout
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity

class SecondScreen : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.second_screen)

        val sharedPreferences = getSharedPreferences("UserData", Context.MODE_PRIVATE)
        val userDataSet = sharedPreferences.getStringSet("USER_DATA", setOf())?.toMutableSet() ?: mutableSetOf()

        val linearLayout: LinearLayout = findViewById(R.id.linearLayout)

        for (userData in userDataSet) {
            val userLayout = LinearLayout(this)
            userLayout.orientation = LinearLayout.VERTICAL
            userLayout.setPadding(16, 16, 16, 16)

            val dataEntries = userData.split(",")

            for (entry in dataEntries) {
                val textView = TextView(this)
                textView.text = entry.trim()
                textView.textSize = 16f
                textView.setTextColor(0xFF7C7C7C.toInt())
                userLayout.addView(textView)
            }

            val deleteButton = Button(this)
            deleteButton.text = "USUŃ"
            deleteButton.setBackgroundColor(0xFF6200EE.toInt())
            deleteButton.setTextColor(0xFFFFFFFF.toInt())
            deleteButton.setPadding(8, 4, 8, 4)
            val layoutParams = LinearLayout.LayoutParams(
                LinearLayout.LayoutParams.WRAP_CONTENT,
                LinearLayout.LayoutParams.WRAP_CONTENT
            )
            layoutParams.setMargins(0, 16, 0, 0)
            layoutParams.gravity = android.view.Gravity.END
            deleteButton.layoutParams = layoutParams


            deleteButton.setOnClickListener {
                linearLayout.removeView(userLayout)
                userDataSet.remove(userData)
                sharedPreferences.edit().putStringSet("USER_DATA", userDataSet).apply()
            }

            userLayout.addView(deleteButton)
            linearLayout.addView(userLayout)
        }
    }
}
