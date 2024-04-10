package com.week4.getir.apicallexample.common

import com.week4.getir.apicallexample.data.model.request.LoginRequestModel
import com.week4.getir.apicallexample.data.model.response.ProfileResponseModel
import org.json.JSONObject

fun String.combineWithPath(path: String): String {
    val baseUrl = if (this.endsWith("/")) this else "$this/"
    val adjustedPath = if (path.startsWith("/")) path.substring(1) else path
    return baseUrl + adjustedPath
}
fun LoginRequestModel.toJsonObject(): JSONObject {
    val jsonObject = JSONObject()
    jsonObject.put("email", this.email)
    jsonObject.put("password", this.password)
    return jsonObject
}

fun String.toProfileResponseModel(): ProfileResponseModel {
    val jsonObject = JSONObject(this)
    return ProfileResponseModel(
        country = jsonObject.opt("country"),
        email = jsonObject.getString("email"),
        employer = jsonObject.opt("employer"),
        fullName = jsonObject.getString("fullName"),
        id = jsonObject.getInt("id"),
        latitude = jsonObject.getDouble("latitude"),
        longitude = jsonObject.getDouble("longitude"),
        occupation = jsonObject.opt("occupation"),
        password = jsonObject.getString("password"),
        phoneNumber = jsonObject.opt("phoneNumber"),
        userId = jsonObject.getString("userId")
    )
}
