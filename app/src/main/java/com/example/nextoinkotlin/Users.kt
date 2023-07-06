package com.example.nextoinkotlin

import com.google.gson.annotations.SerializedName
import java.util.UUID


data class Users (

  @SerializedName("id"            ) var id           : UUID? = null,
  @SerializedName("full_name"     ) var fullName     : String? = null,
  @SerializedName("user_login"    ) var userLogin    : String? = null,
  @SerializedName("user_password" ) var userPassword : String? = null

)