package com.example.capstonetrial

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.capstonetrial.api.ApiConfig
import com.example.capstonetrial.response.Equipment
import com.example.capstonetrial.response.EquipmentResponse
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class ListViewModel : ViewModel() {

    private val _listEquipment = MutableLiveData<EquipmentResponse>()
    val listEquipment: LiveData<EquipmentResponse> = _listEquipment

    private val _isLoading = MutableLiveData<Boolean>()
    val isLoading: LiveData<Boolean> = _isLoading

    companion object{
        private const val TAG = "ListViewModel"
        private const val NAME = "a"
    }

    init{
        getEquipment()
    }

    fun getEquipment() {
        _isLoading.value = true
        val client = ApiConfig.getApiService().getEquipment()
        client.enqueue(object : Callback<EquipmentResponse>{
            override fun onResponse(
                call: Call<EquipmentResponse>,
                response: Response<EquipmentResponse>
            ) {
                _isLoading.value = false
                if (response.isSuccessful) {
                    _listEquipment.value = response.body()
                } else {
                    Log.e(TAG, "onFailure: ${response.message()}")
                }
            }

            override fun onFailure(call: Call<EquipmentResponse>, t: Throwable) {
                _isLoading.value = false
                Log.e(TAG, "onFailure: ${t.message.toString()}")
            }
        })
    }
}