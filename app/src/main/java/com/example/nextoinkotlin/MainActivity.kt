package com.example.nextoinkotlin

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.CountDownTimer

import android.widget.Button
import android.widget.TextView
import java.text.SimpleDateFormat
import java.util.Date

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val textView: TextView = findViewById(R.id.textView)
        val timer = object: CountDownTimer(1000, 1000){
            override fun onTick(p0: Long) {
                textView.text = getCurrentDate()
            }
            override fun onFinish() {
                this.start()
            }
        }
        timer.start()

        val button: Button = findViewById(R.id.button)
        button.setOnClickListener {
            openLoginActiviti()
        }

    }



    private fun getCurrentDate():String{
        val sdf = SimpleDateFormat("HH:mm:ss")
        return sdf.format(Date())
    }

    private fun openLoginActiviti(){
        val intent = Intent(this, LoginActivity::class.java)
        startActivity(intent)
    }
}