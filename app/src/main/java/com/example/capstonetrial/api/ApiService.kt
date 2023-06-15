package com.example.capstonetrial.api

import com.example.capstonetrial.response.Equipment
import com.example.capstonetrial.response.EquipmentResponse
import okhttp3.MultipartBody
import okhttp3.RequestBody
import retrofit2.Call
import retrofit2.http.*

interface ApiService {

    @GET("/equipment")
    fun getEquipment(
    ): Call<EquipmentResponse>

    @GET("/equipment/{id}")
    fun getEquipmentDetail(
        @Path("id") id:Int
    ): Call<EquipmentResponse>

}

