package com.week4.getir.apicallexample.data.repositories

import com.week4.getir.apicallexample.data.model.request.LoginRequestModel
import com.week4.getir.apicallexample.data.model.response.ProfileResponseModel
import com.week4.getir.apicallexample.data.remote.ApiClient
import com.week4.getir.apicallexample.domain.repositories.UserRepository
import javax.inject.Inject

class UserRepositoryImpl @Inject constructor(private val api: ApiClient) : UserRepository {
    override suspend fun login(loginRequestModel: LoginRequestModel): String {
       return api.login(loginRequestModel)
    }

    override suspend fun getProfile(userId: String): ProfileResponseModel? {
      return api.getProfile(userId)
    }


}