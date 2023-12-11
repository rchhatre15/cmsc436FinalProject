package com.example.bankapp

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity

class AccountsActivity : AppCompatActivity() {
    private var accountIndex: Int = -1

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_accounts)

        accountIndex = intent.getIntExtra("accountIndex", -1)
        val account = MainActivity.accounts[accountIndex]

        val checkingBalanceText = findViewById<TextView>(R.id.checkingBalance)
        val savingsBalanceText = findViewById<TextView>(R.id.savingsBalance)
        val amountEditText = findViewById<EditText>(R.id.amountEditText)
        val transferAmountEditText = findViewById<EditText>(R.id.transferAmountEditText)
        val depositButton = findViewById<Button>(R.id.depositButton)
        val withdrawButton = findViewById<Button>(R.id.withdrawButton)
        val transferToSavingsButton = findViewById<Button>(R.id.transferToSavingsButton)
        val transferToCheckingButton = findViewById<Button>(R.id.transferToCheckingButton)
        val signOutButton = findViewById<Button>(R.id.signOutButton)
        val atmButton = findViewById<Button>(R.id.mapButton)

        updateBalances(account, checkingBalanceText, savingsBalanceText)

        atmButton.setOnClickListener {
            val mapIntent = Intent(this, MapsActivity::class.java)
            startActivity(mapIntent)
        }

        depositButton.setOnClickListener {
            val amount = amountEditText.text.toString().toDoubleOrNull() ?: 0.0
            account.checkingBalance += amount
            updateBalances(account, checkingBalanceText, savingsBalanceText)
            saveAccounts()
        }

        withdrawButton.setOnClickListener {
            val amount = amountEditText.text.toString().toDoubleOrNull() ?: 0.0
            if (account.checkingBalance >= amount) {
                account.checkingBalance -= amount
                updateBalances(account, checkingBalanceText, savingsBalanceText)
                saveAccounts()
            }
        }

        transferToSavingsButton.setOnClickListener {
            val amount = transferAmountEditText.text.toString().toDoubleOrNull() ?: 0.0
            if (account.checkingBalance >= amount) {
                account.checkingBalance -= amount
                account.savingsBalance += amount
                updateBalances(account, checkingBalanceText, savingsBalanceText)
                saveAccounts()
            }
        }

        transferToCheckingButton.setOnClickListener {
            val amount = transferAmountEditText.text.toString().toDoubleOrNull() ?: 0.0
            if (account.savingsBalance >= amount) {
                account.savingsBalance -= amount
                account.checkingBalance += amount
                updateBalances(account, checkingBalanceText, savingsBalanceText)
                saveAccounts()
            }
        }

        signOutButton.setOnClickListener {
            finish()
        }
    }

    private fun updateBalances(account: Account, checkingText: TextView, savingsText: TextView) {
        checkingText.text = "Checking: $${account.checkingBalance}"
        savingsText.text = "Savings: $${account.savingsBalance}"
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
