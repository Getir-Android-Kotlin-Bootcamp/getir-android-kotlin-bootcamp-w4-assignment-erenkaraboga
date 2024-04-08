package com.week4.getir.apicallexample

import android.net.http.HttpException
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import java.io.IOException
import javax.inject.Inject

class LoginUseCase @Inject constructor(
    private val repository: UserRepository
) {
    operator fun invoke(loginRequestModel: LoginRequestModel): Flow<Resource<String>> = flow {
        try {
            emit(Resource.Loading())
            val response = repository.login(loginRequestModel)
            emit(Resource.Success(data = response))
        }  catch (e: IOException) {
            emit(Resource.Error(e.message.toString()))
        }
    }
}