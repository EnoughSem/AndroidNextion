package com.example.nextoinkotlin

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import io.ktor.client.HttpClient
import io.ktor.client.request.get
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class LoginActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login2)

        val buttonBack: Button = findViewById(R.id.button2)
        buttonBack.setOnClickListener {
            openMainActivity()
        }

        val buttonConnect: Button = findViewById(R.id.button3)
        buttonConnect.setOnClickListener {
            CoroutineScope(Dispatchers.IO).launch {
                click()
            }
        }
    }

    suspend fun click() {
        val editTextText: EditText = findViewById(R.id.editTextText)
        val editTextTextPassword: EditText = findViewById(R.id.editTextTextPassword)
        val login = editTextText.text.toString()
        val password = editTextTextPassword.text.toString()
        val url = "https://www.mamont-server.ru:8888/api/user"
        val client = HttpClient()
        HttpClient().use {
            val dateString = client.get<String>(url)
            val typeToken = object : TypeToken<ArrayList<Users>>() {}.type
            var users = Gson().fromJson<ArrayList<Users>>(dateString, typeToken)
            users.forEach{
                if (it.userLogin == login && it.userPassword == password){
                    openReservActivity()
                }
            }
        }
    }

    private fun openReservActivity() {
        val intent = Intent(this, ReservActivity::class.java)
        startActivity(intent)
    }

    private fun openMainActivity() {
        val intent = Intent(this, MainActivity::class.java)
        startActivity(intent)
    }
}

