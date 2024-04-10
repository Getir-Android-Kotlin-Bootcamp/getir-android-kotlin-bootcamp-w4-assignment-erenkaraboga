package com.week4.getir.apicallexample.domain.use_cases

import com.week4.getir.apicallexample.common.Resource
import com.week4.getir.apicallexample.data.model.request.LoginRequestModel
import com.week4.getir.apicallexample.data.model.response.ProfileResponseModel
import com.week4.getir.apicallexample.domain.repositories.UserRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import java.io.IOException
import javax.inject.Inject

class ProfileUseCase @Inject constructor(
    private val repository: UserRepository
) {
    operator fun invoke(userId: String): Flow<Resource<ProfileResponseModel?>> = flow {
        try {
            emit(Resource.Loading())
            val response = repository.getProfile(userId)
            emit(Resource.Success(data = response))
        }  catch (e: IOException) {
            emit(Resource.Error(e.message.toString()))
        }
    }
}