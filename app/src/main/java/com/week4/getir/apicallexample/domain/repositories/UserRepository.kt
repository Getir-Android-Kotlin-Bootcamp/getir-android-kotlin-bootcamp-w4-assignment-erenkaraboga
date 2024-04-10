package com.week4.getir.apicallexample.domain.repositories

import com.week4.getir.apicallexample.data.model.LoginRequestModel

interface UserRepository {
    suspend fun login(loginRequestModel: LoginRequestModel): String
}