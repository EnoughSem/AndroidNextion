package com.example.nextoinkotlin

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.ArrayAdapter
import android.widget.Button
import android.widget.ListView
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import io.ktor.client.HttpClient
import io.ktor.client.request.get
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class ListStudiosActivity : AppCompatActivity() {

    private var numberStudios = ArrayList<Int>()
    private var studios = ArrayList<Studios>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_list_studios)

        val listView =findViewById<ListView>(R.id.listView)
        CoroutineScope(Dispatchers.Default).launch {
             startActivite(numberStudios, listView)
        }

        listView.setOnItemClickListener{_, _, position, _ ->
            val studio = studios[position]
            openReservActivity(studio.id.toString())
        }

        val buttonBack = findViewById<Button>(R.id.button7)
        buttonBack.setOnClickListener {
            openLoginActivity()
        }

    }

    private suspend fun startActivite(numberStudios: ArrayList<Int>, listView:ListView) {
        val url = "https://mamont-server.ru:8888/api/studio"
        HttpClient().use { client ->
            val dateString = client.get<String>(url)
            val typeToken = object : TypeToken<ArrayList<Studios>>() {}.type
            studios = Gson().fromJson(dateString, typeToken)
            studios.forEach {
                numberStudios.add(it.numberStudio!!)
            }
        }
        CoroutineScope(Dispatchers.Main).launch {
            val adapter =
                ArrayAdapter(
                    applicationContext,
                    android.R.layout.simple_list_item_1,
                    numberStudios
                )
            listView.adapter = adapter
        }
    }

    private fun openReservActivity(uuid :String) {
        val intent = Intent(this, ReservActivity::class.java)
        intent.putExtra(ReservActivity.UUID, uuid)
        startActivity(intent)
    }
    private fun openLoginActivity() {
        val intent = Intent(this, LoginActivity::class.java)
        startActivity(intent)
    }
}