package com.example.bankapp

data class Account(
    val username: String,
    val password: String,
    var checkingBalance: Double,
    var savingsBalance: Double
) {
    fun toJson(): String {
        return "$username,$password,$checkingBalance,$savingsBalance"
    }

    companion object {
        fun fromJson(json: String): Account {
            val parts = json.split(",")
            return Account(
                username = parts[0],
                password = parts[1],
                checkingBalance = parts[2].toDoubleOrNull() ?: 0.0,
                savingsBalance = parts[3].toDoubleOrNull() ?: 0.0
            )
        }
    }
}
