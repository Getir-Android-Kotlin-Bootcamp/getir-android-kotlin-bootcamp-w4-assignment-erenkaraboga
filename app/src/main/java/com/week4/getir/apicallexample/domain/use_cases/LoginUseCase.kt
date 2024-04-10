package com.week4.getir.apicallexample.domain.use_cases

import com.week4.getir.apicallexample.data.model.request.LoginRequestModel
import com.week4.getir.apicallexample.common.Resource
import com.week4.getir.apicallexample.domain.repositories.UserRepository
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