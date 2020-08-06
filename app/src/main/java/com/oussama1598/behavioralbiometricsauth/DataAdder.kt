package com.oussama1598.behavioralbiometricsauth

import com.github.kittinunf.fuel.Fuel
import org.json.JSONObject

class DataAdder {
    fun addDataToUser(
        userId: String,
        dataType: String,
        rawData: String,
        myCallback: (data: JSONObject) -> Unit
    ) {
        val json = JSONObject()
        json.put("user_id", userId)
        json.put("data_type", dataType)
        json.put("raw_data", rawData)

        Fuel.post("http://192.168.1.11:8000/users_data")
            .body(json.toString())
            .responseString() { _, _, result ->
                val data = JSONObject(result.get())
                myCallback.invoke(data)
            }
    }
}