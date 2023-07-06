package com.example.nextoinkotlin

import com.google.gson.annotations.SerializedName
import java.util.Date
import java.util.UUID


data class Schedules (

  @SerializedName("id"         ) var id        : UUID? = null,
  @SerializedName("id_studio"  ) var idStudio  : UUID? = null,
  @SerializedName("time_start" ) var timeStart : Date? = null,
  @SerializedName("time_end"   ) var timeEnd   : Date? = null

)