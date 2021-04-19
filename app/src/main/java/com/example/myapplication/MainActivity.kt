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

    private var wifiItemList = mutableListOf<WifiItem>()
    private lateinit var adapter: WifiItemAdapter

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
        adapter = WifiItemAdapter(
            wifiItemList,
            this
        )
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
            mRvWifiList.adapter = adapter
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
                if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.M) {
                    wifiItemList.add(
                        WifiItem(
                            result.SSID,
                            "${result.BSSID} : ${result.capabilities} : ${result.frequency} : " +
                                    "${result.venueName} : ${result.operatorFriendlyName} "
                        )
                    )
                }
            }

            adapter.notifyDataSetChanged()
            false
        }
    }
}
