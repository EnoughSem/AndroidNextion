package com.example.nextoinkotlin

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Button
import android.widget.TextView
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import io.ktor.client.HttpClient
import io.ktor.client.request.get
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import java.util.Date

class ReservActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_reserv)

        CoroutineScope(Dispatchers.Default).launch {
            startConnect()
        }

        val buttonOpenAddReservActivity: Button = findViewById(R.id.button4)
        buttonOpenAddReservActivity.setOnClickListener {
            openAddReservActivity()
        }
    }

    private suspend fun startConnect() {
        val textViewStart: TextView = findViewById(R.id.textView3)
        val textViewEnd: TextView = findViewById(R.id.textView4)
        val url = "https://mamont-server.ru:8888/api/schedule/788d3103-8a54-4aae-86fb-f19b5c09db58"
        HttpClient().use {client ->
            val dateString = client.get<String>(url)
            val typeToken = object : TypeToken<ArrayList<Schedules>>() {}.type
            val schedules = Gson().fromJson<ArrayList<Schedules>>(dateString, typeToken)
            schedules.forEach {
                if (it.timeStart!! <= Date() && it.timeEnd!! >= Date())
                    CoroutineScope(Dispatchers.Main).launch {
                        textViewStart.text = it.timeStart.toString()
                        textViewEnd.text = it.timeEnd.toString()
                    }
            }
        }
    }

    private fun openAddReservActivity() {
        val intent = Intent(this, AddReservActivity::class.java)
        startActivity(intent)
    }
}

