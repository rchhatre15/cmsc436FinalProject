package com.example.bankapp

import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import androidx.appcompat.app.AppCompatActivity
import android.content.Context
import android.widget.CalendarView
import android.widget.DatePicker
import android.widget.Toast
import java.util.Calendar

class CreateAccountActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_create_account)

        val usernameEditText = findViewById<EditText>(R.id.newUsername)
        val passwordEditText = findViewById<EditText>(R.id.newPassword)
        val createButton = findViewById<Button>(R.id.createButton)
        val datePicker = findViewById<DatePicker>(R.id.datePicker)

        createButton.setOnClickListener {
            val username = usernameEditText.text.toString()
            val password = passwordEditText.text.toString()

            val selectedYear = datePicker.year
            val selectedMonth = datePicker.month
            val selectedDay = datePicker.dayOfMonth
            val selectedDate = Calendar.getInstance().apply {
                set(selectedYear, selectedMonth, selectedDay)
            }
            val currentDateMinus18Years = Calendar.getInstance().apply {
                add(Calendar.YEAR, -18)
            }


            if (username.isNotEmpty() && password.isNotEmpty() && !selectedDate.after(currentDateMinus18Years)) {
                val newAccount = Account(username, password, 0.0, 0.0)
                MainActivity.accounts.add(newAccount)
                saveAccounts()
                finish()
                // finishAfterTransition()
            }

            if (selectedDate.after(currentDateMinus18Years)) {
                Toast.makeText(this, "You must be 18 to create an account", Toast.LENGTH_LONG).show()
            }
        }
    }

    private fun saveAccounts() {
        val sharedPref = getSharedPreferences("BankAppPrefs", Context.MODE_PRIVATE)
        with(sharedPref.edit()) {
            val accountsData = MainActivity.accounts.joinToString(";") { it.toJson() }
            putString("accounts", accountsData)
            apply()
        }
    }
}
