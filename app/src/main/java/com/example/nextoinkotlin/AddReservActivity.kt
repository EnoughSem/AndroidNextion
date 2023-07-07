package com.example.nextoinkotlin


import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import io.ktor.client.HttpClient
import io.ktor.client.request.get

import io.ktor.client.request.request
import io.ktor.http.ContentType
import io.ktor.http.HttpMethod
import io.ktor.http.contentType
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import java.util.Date

class AddReservActivity : AppCompatActivity() {

    private lateinit var dateStart: Date
    private lateinit var dateEnd: Date

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_add_reserv)

        val buttonAdd = findViewById<Button>(R.id.button6)
        buttonAdd.setOnClickListener {
            CoroutineScope(Dispatchers.Default).launch {
                addTime()
            }
        }

    }

    private suspend fun addTime() {
        val editTextDate6 = findViewById<EditText>(R.id.editTextDate6)
        val editTextDate5 = findViewById<EditText>(R.id.editTextDate5)
        val editTextDate3 = findViewById<EditText>(R.id.editTextDate3)
        val editTextTime6 = findViewById<EditText>(R.id.editTextTime6)
        val editTextTime4 = findViewById<EditText>(R.id.editTextTime4)

        @Suppress("DEPRECATION")
        dateStart = Date(
            editTextDate6.text.toString().toInt(),
            editTextDate5.text.toString().toInt(),
            editTextDate3.text.toString().toInt(),
            editTextTime6.text.toString().toInt(),
            editTextTime4.text.toString().toInt()
        )

        val editTextDate9 = findViewById<EditText>(R.id.editTextDate9)
        val editTextDate8 = findViewById<EditText>(R.id.editTextDate8)
        val editTextDate7 = findViewById<EditText>(R.id.editTextDate7)
        val editTextTime5 = findViewById<EditText>(R.id.editTextTime5)
        val editTextTime3 = findViewById<EditText>(R.id.editTextTime3)

        @Suppress("DEPRECATION")
        dateEnd = Date(
            editTextDate9.text.toString().toInt(),
            editTextDate8.text.toString().toInt(),
            editTextDate7.text.toString().toInt(),
            editTextTime5.text.toString().toInt(),
            editTextTime3.text.toString().toInt()
        )


        val url = "https://mamont-server.ru:8888/api/schedule/788d3103-8a54-4aae-86fb-f19b5c09db58/$dateStart/$dateEnd"
        HttpClient().use { client ->
            val date = client.get<String>(url)
            if (date == "[]") {
                client.request("https://mamont-server.ru:8888/api/schedule"){
                    contentType(ContentType.Application.Json)
                    method = HttpMethod.Post
                    body = {"\"studio_id\": \"788d3103-8a54-4aae-86fb-f19b5c09db58\","+
                            "\"start\": \"$dateStart\"," +
                            "\"end\": \"$dateEnd\""}
                }
            }
            else{
                Toast.makeText(this, "Данное время занято. Пожалуйста, выберете другое", Toast.LENGTH_SHORT)
            }
        }
    }
}
