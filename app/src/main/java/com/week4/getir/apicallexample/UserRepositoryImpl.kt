package com.week4.getir.apicallexample

import javax.inject.Inject

class UserRepositoryImpl @Inject constructor(private val api: ApiClient) : UserRepository {
    override suspend fun login(loginRequestModel: LoginRequestModel): String {
       return api.login(loginRequestModel)
    }


}