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
    val formatter: DateTimeFormatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss")

    private var counter : String = ""
    private var count = 0
    private lateinit var tvCount: TextView
    private lateinit var btnReset: Button
    private lateinit var btnSend: Button
    private lateinit var btnAdd: Button

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
            counter += "\r\n" + current
            count++
            tvCount.text = count.toString()
        }

        btnReset.setOnClickListener {
            counter = ""
            count = 0
            tvCount.text = "0"
        }

        btnSend.setOnClickListener {
            try {
                val email = "glieutier@sensordata.com.uy"
                val subject = "Tree report"
                val message = counter
                Log.d("testing", message)
                val emailIntent = Intent(Intent.ACTION_SEND)
                emailIntent.type = "plain/text"
                emailIntent.putExtra(Intent.EXTRA_EMAIL, arrayOf(email))
                emailIntent.putExtra(Intent.EXTRA_SUBJECT, subject)
                emailIntent.putExtra(Intent.EXTRA_TEXT, message)
                this.startActivity(Intent.createChooser(emailIntent, "Sending email..."))
                count = 0
                counter = ""
                tvCount.text = "0"
            }
            catch (t: Throwable) {
                Toast.makeText(this, "Request failed try again: $t", Toast.LENGTH_LONG).show()
            }
        }

        if (savedInstanceState != null){
            counter = savedInstanceState.getString("counter").toString()
            count = savedInstanceState.getInt("count")
            tvCount.text = count.toString()
        }
    }

    override fun onSaveInstanceState(outState: Bundle) {
        super.onSaveInstanceState(outState)

        Log.d("testing", "rotate")
        outState.putString("counter", counter)
        outState.putInt("count", count)
    }

    override fun onRestoreInstanceState(savedInstanceState: Bundle) {
        super.onRestoreInstanceState(savedInstanceState)

        Log.d("testing", "restore")
        counter = savedInstanceState.getString("counter").toString()
        count = savedInstanceState.getInt("count")
        tvCount.text = count.toString()
    }
}