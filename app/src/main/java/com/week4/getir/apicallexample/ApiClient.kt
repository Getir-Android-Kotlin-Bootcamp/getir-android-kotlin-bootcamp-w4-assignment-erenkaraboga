package com.week4.getir.apicallexample

import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import org.json.JSONObject
import java.io.BufferedReader
import java.io.DataOutputStream
import java.io.InputStreamReader
import java.net.HttpURLConnection
import java.net.URL

class ApiClient() {

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
            println("Response Code: $responseCode")

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
}