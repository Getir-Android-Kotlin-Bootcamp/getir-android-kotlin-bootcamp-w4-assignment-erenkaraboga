package com.week4.getir.apicallexample.data.remote

import com.week4.getir.apicallexample.BuildConfig
import com.week4.getir.apicallexample.data.model.request.LoginRequestModel
import com.week4.getir.apicallexample.common.combineWithPath
import com.week4.getir.apicallexample.common.toJsonObject
import com.week4.getir.apicallexample.common.toProfileResponseModel
import com.week4.getir.apicallexample.data.model.response.ProfileResponseModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import java.io.BufferedReader
import java.io.DataOutputStream
import java.io.InputStreamReader
import java.net.HttpURLConnection
import java.net.URL

class ApiClient {

    suspend fun login(loginRequestModel: LoginRequestModel): String {
        return withContext(Dispatchers.IO) {
            val url = BuildConfig.BASE_URL.combineWithPath("login")
            val jsonObject = loginRequestModel.toJsonObject()
            val urlObject = URL(url)
            val postData = jsonObject.toString()
            val connection = urlObject.openConnection() as HttpURLConnection

            connection.setRequestProperty("Content-Type", "application/json; charset=utf8")
            connection.requestMethod = "POST"
            connection.doOutput = true

            val outputStream = DataOutputStream(connection.outputStream)
            outputStream.write(postData.toByteArray(Charsets.UTF_8))
            outputStream.flush()
            outputStream.close()
            val responseCode = connection.responseCode
            if (responseCode == HttpURLConnection.HTTP_OK) {
                val inputStream = BufferedReader(InputStreamReader(connection.inputStream))
                var inputLine: String?
                val response = StringBuffer()
                while (inputStream.readLine().also { inputLine = it } != null) {
                    response.append(inputLine)
                }
                inputStream.close()
                response.toString()
            } else {
                ""
            }
        }
    }
    suspend fun getProfile(userId: String): ProfileResponseModel? {
        return withContext(Dispatchers.IO) {
            val url = BuildConfig.BASE_URL.combineWithPath("profile/${userId}")
            val urlObject = URL(url)
            val connection = urlObject.openConnection() as HttpURLConnection

            connection.setRequestProperty("Content-Type", "application/json; charset=utf8")
            connection.requestMethod = "GET"
            val responseCode = connection.responseCode
            if (responseCode == HttpURLConnection.HTTP_OK) {
                val inputStream = BufferedReader(InputStreamReader(connection.inputStream))
                var inputLine: String?
                val response = StringBuffer()
                while (inputStream.readLine().also { inputLine = it } != null) {
                    response.append(inputLine)
                }
                inputStream.close()
                response.toString().toProfileResponseModel()
            } else {
                null
            }
        }
    }
}