package com.example.capstonetrial.response

import com.google.gson.annotations.SerializedName

class EquipmentResponse (
    @field:SerializedName("payload")
    val payload: List<Equipment>
)
data class Equipment(
    @field:SerializedName("equipment_id")
    val equipment_id: Int? = null,

    @field:SerializedName("name")
    val name: String? = null,

    @field:SerializedName("description")
    val description: String? = null,

    @field:SerializedName("image_url")
    val image_url: String? = null
)



