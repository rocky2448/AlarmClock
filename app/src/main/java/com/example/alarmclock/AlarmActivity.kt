package com.example.alarmclock

import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.widget.Button
import android.widget.ImageView
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import kotlin.system.exitProcess

class AlarmActivity : AppCompatActivity() {

    private lateinit var stopAlarmButtonBTN: Button
    private lateinit var toolbarMain: Toolbar
    private lateinit var imageAlarmClockIV: ImageView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_alarm)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        stopAlarmButtonBTN = findViewById(R.id.stopAlarmButtonBTN)
        toolbarMain = findViewById(R.id.toolbarMain)
        imageAlarmClockIV = findViewById(R.id.imageAlarmClockIV)

        setSupportActionBar(toolbarMain)
        title = "Будильник"
        toolbarMain.subtitle = "by Rocky"

        stopAlarmButtonBTN.setOnClickListener {
            finish()
            exitProcess(0)
        }
        imageAlarmClockIV.setImageResource(R.drawable.alarm_clock)
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.menu, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            R.id.exitMenuMain -> finishAffinity()
        }
        return super.onOptionsItemSelected(item)
    }
}