package com.example.myapplication

import android.Manifest
import android.content.Context
import android.net.wifi.WifiManager
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.core.app.ActivityCompat
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.myapplication.adapter.WifiItemAdapter
import com.example.myapplication.databinding.ActivityMainBinding
import com.example.myapplication.model.WifiItem

class MainActivity : AppCompatActivity() {

    private lateinit var activityMainBinding: ActivityMainBinding
    private lateinit var wifiManager: WifiManager;

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        activityMainBinding = ActivityMainBinding.inflate(layoutInflater)

        setContentView(activityMainBinding.root)

        setUpManagers()

        setUpPermissions()

        setUpWifiAdapter()

        setListeners()
    }

    private fun setUpManagers() {
        wifiManager =
            applicationContext.getSystemService(Context.WIFI_SERVICE) as WifiManager
    }

    private fun setUpPermissions() {
        ActivityCompat.requestPermissions(
            this,
            arrayOf(Manifest.permission.ACCESS_FINE_LOCATION),
            101
        )
    }

    private fun setUpWifiAdapter() {
        activityMainBinding.rvWifiList.let { mRvWifiList ->
            mRvWifiList.layoutManager = LinearLayoutManager(this)
            mRvWifiList.adapter = WifiItemAdapter(
                mutableListOf(WifiItem(1, "Testing"), WifiItem(2, "Testing2")),
                this
            )
        }
    }

    private fun setListeners() {
        activityMainBinding.bStartScan.setOnClickListener {
            wifiManager.startScan()
        }

        activityMainBinding.bStartScan.setOnLongClickListener {
            val scanResults = wifiManager.scanResults
            println("Printing Devices...")
            for (result in scanResults) {
                Toast.makeText(this, "Scan $result", Toast.LENGTH_SHORT).show()
                println("Device is $result")
            }

            false
        }
    }
}
