package com.example.nextoinkotlin

import android.annotation.SuppressLint
import android.content.Intent
import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.TextView
import androidx.annotation.RequiresApi
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import io.ktor.client.HttpClient
import io.ktor.client.request.get
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import java.time.LocalDateTime
import java.time.ZoneOffset
import java.time.format.DateTimeFormatter
import java.util.Date

class ReservActivity : AppCompatActivity() {

    companion object {
        const val UUID = "UUID_Studio"
    }

    @SuppressLint("MissingInflatedId")
    @RequiresApi(Build.VERSION_CODES.O)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_reserv)

        val uuid = intent.getStringExtra(UUID)
        CoroutineScope(Dispatchers.Default).launch {
            startConnect(uuid!!)
        }
        val buttonOpenAddReservActivity: Button = findViewById(R.id.button4)
        buttonOpenAddReservActivity.setOnClickListener {

            openAddReservActivity(uuid!!)
        }
        val buttonOpenListStudiosActivity: Button = findViewById(R.id.button8)
        buttonOpenListStudiosActivity.setOnClickListener {
            openListStudiosActivity()
        }

    }

    @SuppressLint("SetTextI18n")
    @RequiresApi(Build.VERSION_CODES.O)
    private suspend fun startConnect(uuid: String) {
        val textViewStart: TextView = findViewById(R.id.textView3)
        val textViewEnd: TextView = findViewById(R.id.textView4)
        val url = "https://mamont-server.ru:8888/api/schedule/$uuid"
        HttpClient().use { client ->
            val dateString = client.get<String>(url)
            val typeToken = object : TypeToken<ArrayList<Schedules>>() {}.type
            val schedules = Gson().fromJson<ArrayList<Schedules>>(dateString, typeToken)
            schedules.forEach {
                val dateFormat = DateTimeFormatter.ofPattern("dd.MM.yyyy HH:mm")
                val dateNow = Date.from(LocalDateTime.now().atOffset(ZoneOffset.UTC).toInstant())
                if (it.timeStart!! <= dateNow && it.timeEnd!! >= dateNow) {
                    CoroutineScope(Dispatchers.Main).launch {
                        val textView: TextView = findViewById(R.id.textView7)
                        textView.text = "Студия занята"
                        textViewStart.text = "С: " + it.timeStart!!.toInstant()
                                .atOffset(ZoneOffset.UTC)
                                .toLocalDateTime().format(dateFormat)
                        textViewEnd.text = "До: " + it.timeEnd!!.toInstant().
                                    atOffset(ZoneOffset.UTC)
                                    .toLocalDateTime().format(dateFormat)
                    }
                }
            }
        }
    }




    private fun openAddReservActivity(uuid : String) {
        val intent = Intent(this, AddReservActivity::class.java)
        intent.putExtra(AddReservActivity.UUID, uuid)
        startActivity(intent)
    }

    private fun openListStudiosActivity() {
        val intent = Intent(this, ListStudiosActivity::class.java)
        startActivity(intent)
    }
}

