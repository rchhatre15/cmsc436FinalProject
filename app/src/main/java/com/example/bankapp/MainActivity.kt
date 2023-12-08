package com.example.bankapp

import android.content.Intent
import android.content.Context
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import androidx.appcompat.app.AppCompatActivity

class MainActivity : AppCompatActivity() {
    companion object {
        var accounts: MutableList<Account> = mutableListOf()
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        loadAccounts()

        val usernameEditText = findViewById<EditText>(R.id.username)
        val passwordEditText = findViewById<EditText>(R.id.password)
        val loginButton = findViewById<Button>(R.id.loginButton)
        val createAccountButton = findViewById<Button>(R.id.createAccountButton)

        loginButton.setOnClickListener {
            val username = usernameEditText.text.toString()
            val password = passwordEditText.text.toString()
            val account = accounts.find { it.username == username && it.password == password }

            if (account != null) {
                val intent = Intent(this, AccountsActivity::class.java)
                intent.putExtra("accountIndex", accounts.indexOf(account))
                startActivity(intent)
            }
        }

        createAccountButton.setOnClickListener {
            val intent = Intent(this, CreateAccountActivity::class.java)
            startActivity(intent)
        }
    }

    private fun loadAccounts() {
        val sharedPref = getSharedPreferences("BankAppPrefs", Context.MODE_PRIVATE)
        val accountsJson = sharedPref.getString("accounts", "") ?: ""
        accounts = accountsJson.split(";").filter { it.isNotBlank() }
            .map { Account.fromJson(it) }
            .toMutableList()
    }


}
