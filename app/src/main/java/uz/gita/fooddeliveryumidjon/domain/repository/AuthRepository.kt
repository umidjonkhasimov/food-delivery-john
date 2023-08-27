package uz.gita.fooddeliveryumidjon.domain.repository

import kotlinx.coroutines.flow.Flow

interface AuthRepository {
    fun signIn(email: String, password: String): Flow<Result<Unit>>
    fun signUp(userName: String, email: String, password: String): Flow<Result<Unit>>
    fun sigOut()
}