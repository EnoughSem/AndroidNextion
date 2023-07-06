package com.example.nextoinkotlin

import com.google.gson.annotations.SerializedName
import java.util.UUID


data class Studios (

  @SerializedName("id"            ) var id           : UUID? = null,
  @SerializedName("name_studio"   ) var nameStudio   : String? = null,
  @SerializedName("number_studio" ) var numberStudio : Int?    = null

)