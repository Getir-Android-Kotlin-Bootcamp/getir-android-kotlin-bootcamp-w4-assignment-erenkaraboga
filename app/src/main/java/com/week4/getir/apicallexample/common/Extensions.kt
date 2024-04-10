package com.week4.getir.apicallexample.common

import com.week4.getir.apicallexample.data.model.LoginRequestModel
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
