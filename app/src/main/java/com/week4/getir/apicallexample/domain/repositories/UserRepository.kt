package com.week4.getir.apicallexample.domain.repositories

import com.week4.getir.apicallexample.data.model.request.LoginRequestModel
import com.week4.getir.apicallexample.data.model.response.ProfileResponseModel

interface UserRepository {
    suspend fun login(loginRequestModel: LoginRequestModel): String
    suspend fun getProfile(userId: String): ProfileResponseModel?
}