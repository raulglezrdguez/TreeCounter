package com.example.treecounter

import android.content.Intent
import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Button
import android.widget.TextView
import android.widget.Toast
import androidx.annotation.RequiresApi
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter

class MainActivity : AppCompatActivity() {

    @RequiresApi(Build.VERSION_CODES.O)
    val formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss")

    var counter = mutableListOf<String>()
    lateinit var tvCount: TextView
    lateinit var btnReset: Button
    lateinit var btnSend: Button
    lateinit var btnAdd: Button

    @RequiresApi(Build.VERSION_CODES.O)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        tvCount = findViewById(R.id.tvCount)
        btnAdd = findViewById(R.id.btnAdd)
        btnReset = findViewById(R.id.btnReset)
        btnSend = findViewById(R.id.btnSend)

        btnAdd.setOnClickListener {
            val current = LocalDateTime.now().format(formatter)
            Log.d("Testing", current)
            println(current)
            counter.add(current)
            tvCount.text = counter.size.toString()
        }

        btnReset.setOnClickListener {
            counter.clear()
            tvCount.text = "0"
        }

        btnSend.setOnClickListener {
            try {
                val email = "glieutier@sensordata.com.uy"
                val subject = "Tree report"
                val message = counter.joinToString("\r\n")
                Log.d("testing", message)
                val emailIntent = Intent(Intent.ACTION_SEND)
                emailIntent.type = "plain/text"
                emailIntent.putExtra(Intent.EXTRA_EMAIL, arrayOf(email))
                emailIntent.putExtra(Intent.EXTRA_SUBJECT, subject)
                emailIntent.putExtra(Intent.EXTRA_TEXT, message)
                this.startActivity(Intent.createChooser(emailIntent, "Sending email..."))
            }
            catch (t: Throwable) {
                Toast.makeText(this, "Request failed try again: $t", Toast.LENGTH_LONG).show()
            }
        }

    }
}