package com.example.bankapp

import android.app.ActivityOptions
import android.content.Intent
import android.content.Context
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.LinearLayout
import androidx.annotation.RequiresApi
import androidx.appcompat.app.AppCompatActivity
import com.google.android.gms.ads.AdRequest
import com.google.android.gms.ads.AdSize
import com.google.android.gms.ads.AdView
import com.google.android.gms.ads.MobileAds

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
        val linLayout = findViewById<LinearLayout>(R.id.layoutid)

        loginButton.setOnClickListener {
            val username = usernameEditText.text.toString()
            val password = passwordEditText.text.toString()
            val account = accounts.find { it.username == username && it.password == password }

            if (account != null) {
                val intent = Intent(this, AccountsActivity::class.java)
                intent.putExtra("accountIndex", accounts.indexOf(account))
                startActivity(intent)
                overridePendingTransition(R.anim.login_anim, R.anim.login_anim)
            }
        }

        createAccountButton.setOnClickListener {
            val intent = Intent(this, CreateAccountActivity::class.java)
            //startActivity(intent, ActivityOptions.makeSceneTransitionAnimation(this).toBundle())
            startActivity(intent)
            overridePendingTransition(R.anim.creataccount_anim, R.anim.creataccount_anim)
        }


        var adView : AdView = AdView( this )
        adView.setAdSize(AdSize( AdSize.FULL_WIDTH, AdSize.AUTO_HEIGHT))

        adView.adUnitId = "ca-app-pub-3940256099942544/6300978111"
        var builder : AdRequest.Builder = AdRequest.Builder()
        builder.addKeyword("bank").addKeyword("money").addKeyword("invest")
        var request : AdRequest = builder.build()


        linLayout.addView( adView )
        adView.loadAd( request )

    }

    private fun loadAccounts() {
        val sharedPref = getSharedPreferences("BankAppPrefs", Context.MODE_PRIVATE)
        val accountsJson = sharedPref.getString("accounts", "") ?: ""
        accounts = accountsJson.split(";").filter { it.isNotBlank() }
            .map { Account.fromJson(it) }
            .toMutableList()
    }


}
