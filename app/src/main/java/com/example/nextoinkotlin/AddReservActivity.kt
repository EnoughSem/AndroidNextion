package com.example.nextoinkotlin


import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.annotation.RequiresApi
import io.ktor.client.HttpClient
import io.ktor.client.request.get

import io.ktor.client.request.request
import io.ktor.http.ContentType
import io.ktor.http.HttpMethod
import io.ktor.http.contentType
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import java.time.LocalDate
import java.time.LocalDateTime
import java.time.ZoneOffset
import java.time.format.DateTimeFormatter
import java.util.Date

class AddReservActivity : AppCompatActivity() {

    private lateinit var dateStart: LocalDateTime
    private lateinit var dateEnd: LocalDateTime

    @RequiresApi(Build.VERSION_CODES.O)
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

    @RequiresApi(Build.VERSION_CODES.O)
    private suspend fun addTime() {
        val dateFormat = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss")
        setDateTime(dateFormat)

        val url =
            "https://mamont-server.ru:8888/api/schedule/788d3103-8a54-4aae-86fb-f19b5c09db58/$dateStart/$dateEnd"
        HttpClient().use { client ->
            val date = client.get<String>(url)
            if (date == "[]") {
                try {
                    client.request("https://mamont-server.ru:8888/api/schedule") {
                        contentType(ContentType.Application.Json)
                        method = HttpMethod.Post
                        body = "{\"studio_id\": \"788d3103-8a54-4aae-86fb-f19b5c09db58\"," +
                                "\"start\": \"$dateStart+3\"," +
                                "\"end\": \"$dateEnd+3\"}"
                        CoroutineScope(Dispatchers.Main).launch {
                            Toast.makeText(
                                applicationContext,
                                "Успешно",
                                Toast.LENGTH_SHORT
                            ).show()
                        }
                    }
                }
                catch (e : Exception)
                {
                    Log.i("INFO", e.message.toString())
                }
            } else {
                CoroutineScope(Dispatchers.Main).launch {
                    Toast.makeText(
                        applicationContext,
                        "Данное время занято. Пожалуйста, выберете другое",
                        Toast.LENGTH_SHORT
                    ).show()
                }
            }
        }
    }

    @RequiresApi(Build.VERSION_CODES.O)
    private fun setDateTime(dateFormat: DateTimeFormatter) {
        val editTextDate6 = findViewById<EditText>(R.id.editTextDate6)
        val editTextDate5 = findViewById<EditText>(R.id.editTextDate5)
        val editTextDate3 = findViewById<EditText>(R.id.editTextDate3)
        val editTextTime6 = findViewById<EditText>(R.id.editTextTime6)
        val editTextTime4 = findViewById<EditText>(R.id.editTextTime4)

        var dateStr =
            editTextDate6.text.toString().padStart(4, '0') + "-" + editTextDate5.text.toString()
                .padStart(2, '0') + "-" +
                    editTextDate3.text.toString()
                        .padStart(2, '0') + " " + editTextTime6.text.toString().padStart(2, '0') +
                    ":" + editTextTime4.text.toString().padStart(2, '0') + ":00"
        dateStart = LocalDateTime.parse(dateStr, dateFormat)


        val editTextDate9 = findViewById<EditText>(R.id.editTextDate9)
        val editTextDate8 = findViewById<EditText>(R.id.editTextDate8)
        val editTextDate7 = findViewById<EditText>(R.id.editTextDate7)
        val editTextTime5 = findViewById<EditText>(R.id.editTextTime5)
        val editTextTime3 = findViewById<EditText>(R.id.editTextTime3)

        dateStr =
            editTextDate9.text.toString().padStart(4, '0') + "-" + editTextDate8.text.toString()
                .padStart(2, '0') + "-" +
                    editTextDate7.text.toString()
                        .padStart(2, '0') + " " + editTextTime5.text.toString()
                .padStart(2, '0') + ":" +
                    editTextTime3.text.toString().padStart(2, '0') + ":00"
        dateEnd = LocalDateTime.parse(dateStr, dateFormat)
    }
}