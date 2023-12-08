package com.example.bankapp

import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import androidx.appcompat.app.AppCompatActivity
import android.content.Context

class CreateAccountActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_create_account)

        val usernameEditText = findViewById<EditText>(R.id.newUsername)
        val passwordEditText = findViewById<EditText>(R.id.newPassword)
        val createButton = findViewById<Button>(R.id.createButton)

        createButton.setOnClickListener {
            val username = usernameEditText.text.toString()
            val password = passwordEditText.text.toString()

            if (username.isNotEmpty() && password.isNotEmpty()) {
                val newAccount = Account(username, password, 0.0, 0.0)
                MainActivity.accounts.add(newAccount)
                saveAccounts()
                finish()
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
