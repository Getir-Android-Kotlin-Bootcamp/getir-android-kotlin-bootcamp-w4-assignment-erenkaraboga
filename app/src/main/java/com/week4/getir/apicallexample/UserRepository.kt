package com.week4.getir.apicallexample

interface UserRepository {
    suspend fun login(loginRequestModel: LoginRequestModel): String
}