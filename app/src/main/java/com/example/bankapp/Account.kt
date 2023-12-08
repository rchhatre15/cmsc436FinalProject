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
            val parts = json.split(",").map { it.trim() }
            return Account(
                username = parts.getOrNull(0) ?: "",
                password = parts.getOrNull(1) ?: "",
                checkingBalance = parts.getOrNull(2)?.toDoubleOrNull() ?: 0.0,
                savingsBalance = parts.getOrNull(3)?.toDoubleOrNull() ?: 0.0
            )
        }
    }

}
