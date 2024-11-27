package com.example.alarmclock

import android.app.AlarmManager
import android.app.AlarmManager.RTC_WAKEUP
import android.app.PendingIntent
import android.content.Intent
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.widget.Button
import android.widget.TextView
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.google.android.material.timepicker.MaterialTimePicker
import com.google.android.material.timepicker.TimeFormat
import java.text.SimpleDateFormat
import java.util.Calendar
import java.util.Locale

class MainActivity : AppCompatActivity() {

    private var calendar: Calendar? = null
    private var materialTimePicker: MaterialTimePicker? = null
    private lateinit var alarmButtonBTN: Button
    private lateinit var showAlarmClockTV: TextView
    private lateinit var toolbarMain: Toolbar

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_main)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }
        val dateFormat = SimpleDateFormat("HH:mm", Locale.getDefault())
        alarmButtonBTN = findViewById(R.id.alarmButtonBTN)
        showAlarmClockTV = findViewById(R.id.showAlarmClockTV)
        toolbarMain = findViewById(R.id.toolbarMain)

        setSupportActionBar(toolbarMain)
        title = "Будильник"
        toolbarMain.subtitle = "by Rocky"

        alarmButtonBTN.setOnClickListener{
            materialTimePicker = MaterialTimePicker.Builder()
                .setTimeFormat(TimeFormat.CLOCK_24H)
                .setHour(12)
                .setMinute(0)
                .setTitleText("Выберите время будильника")
                .build()
            materialTimePicker!!.addOnPositiveButtonClickListener {
                calendar = Calendar.getInstance()
                calendar?.set(Calendar.SECOND, 0)
                calendar?.set(Calendar.MILLISECOND, 0)
                calendar?.set(Calendar.MINUTE, materialTimePicker!!.minute)
                calendar?.set(Calendar.HOUR_OF_DAY, materialTimePicker!!.hour)

                val alarmManager = getSystemService(ALARM_SERVICE) as AlarmManager
                alarmManager.setExact(
                    RTC_WAKEUP,
                    calendar?.timeInMillis!!,
                    getAlarmPendingIntent()!!
                )
                Toast.makeText(this, "Будильник установленна на ${dateFormat.format(calendar!!.time)}",
                    Toast.LENGTH_SHORT).show()
                showAlarmClockTV.text = dateFormat.format(calendar!!.time)
                showAlarmClockTV.textSize.plus(20)
            }
            materialTimePicker!!.show(supportFragmentManager, "tag_picker")

        }
    }

    private fun getAlarmPendingIntent(): PendingIntent? {
        val intent = Intent(this, AlarmReceiver::class.java)
        intent.flags = Intent.FLAG_ACTIVITY_CLEAR_TOP or Intent.FLAG_ACTIVITY_NEW_TASK
        return PendingIntent.getBroadcast(
            this,
            1,
            intent,
            PendingIntent.FLAG_IMMUTABLE or PendingIntent.FLAG_UPDATE_CURRENT
        )
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