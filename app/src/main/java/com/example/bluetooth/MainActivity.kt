package com.example.bluetooth

import android.Manifest
import android.app.Activity
import android.bluetooth.BluetoothAdapter
import android.bluetooth.BluetoothManager
import android.content.Context
import android.content.Intent
import android.content.pm.PackageManager
import android.os.Bundle
import android.os.CountDownTimer
import android.view.View
import a5.com.a5bluetoothlibrary.A5DeviceManager
import a5.com.a5bluetoothlibrary.A5BluetoothCallback
import a5.com.a5bluetoothlibrary.A5Device
import android.support.v4.app.ActivityCompat
import android.support.v4.content.ContextCompat
import android.support.v7.app.AppCompatActivity
import android.widget.Toast
import kotlinx.android.synthetic.main.activity_main.*
import java.util.*

class MainActivity : AppCompatActivity(), A5BluetoothCallback {

    private var device: A5Device? = null
    private var counter: Int = 0
    private var countDownTimer: CountDownTimer? = null

    override fun bluetoothIsSwitchedOff() {
    }

    override fun searchCompleted() {
        progressBar.visibility = View.GONE
    }

    override fun didReceiveIsometric(device: A5Device, value: Int) {
        runOnUiThread {
            pressureChangedTextView.text =
                String.format(
                    Locale.US, "Pressure Value: %d", value
                )
        }
    }

    override fun onWriteCompleted(device: A5Device, value: String) {
    }

    override fun didReceiveMessage(device: A5Device, message: String, messageType: String) {
    }

    override fun deviceConnected(device: A5Device) {
        this.device = device

        runOnUiThread {
            progressBar.visibility = View.GONE
            setConnectDisconnectButtonsVisibility(false)
            setSendStopButtonsVisibility(true)
            onPairedTextView.text =
                String.format(
                    Locale.US, "Device name: %s\nDevice address: %s",
                    device.device.address, device.device.name
                )
        }
    }

    override fun deviceFound(device: A5Device) {
        this.device = device
        progressBar.visibility = View.INVISIBLE
        Toast.makeText(this, "done", Toast.LENGTH_SHORT).show()
    }

    override fun deviceDisconnected(device: A5Device) {
        runOnUiThread {
            onPairedTextView.text = getString(R.string.disconnected)
            setConnectDisconnectButtonsVisibility(true)
        }
    }

    override fun on133Error() {
    }

    object Values {
        const val REQUEST_ENABLE_INTENT = 999
        const val MY_PERMISSIONS_REQUEST_ACCESS_COARSE_LOCATION = 998
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        requestPermission()

        connectButton.setOnClickListener {
            val device = this.device
            if (device != null) {
                A5DeviceManager.connect(this, device)
                progressBar.visibility = View.VISIBLE
            }
        }

        disconnectButton.setOnClickListener {
            device?.disconnect()
        }

        sendStopCommandButton.setOnClickListener {
            device?.stop()
            setSendStopButtonsVisibility(false)
            startTimer()
        }

        abortStopCommandButton.setOnClickListener {
            device?.startIsometric()
            setSendStopButtonsVisibility(true)
            stopTimer()
        }

        button1.setOnClickListener {
            device?.startIsometric()
        }
    }

    private fun requestPermission() {
        if (ContextCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION)
            != PackageManager.PERMISSION_GRANTED
        ) {
            if (ContextCompat.checkSelfPermission(
                    this,
                    Manifest.permission.ACCESS_COARSE_LOCATION
                )
                != PackageManager.PERMISSION_GRANTED
            ) {
                // Permission is not granted
                // Should we show an explanation?
                if (ActivityCompat.shouldShowRequestPermissionRationale(
                        this,
                        Manifest.permission.ACCESS_COARSE_LOCATION
                    )
                ) {
                    // Show an explanation to the user *asynchronously* -- don't block
                    // this thread waiting for the user's response! After the user
                    // sees the explanation, try again to request the permission.
                } else {
                    // No explanation needed, we can request the permission.
                    ActivityCompat.requestPermissions(
                        this,
                        arrayOf(Manifest.permission.ACCESS_COARSE_LOCATION),
                        Values.MY_PERMISSIONS_REQUEST_ACCESS_COARSE_LOCATION
                    )

                    // MY_PERMISSIONS_REQUEST_READ_CONTACTS is an
                    // app-defined int constant. The callback method gets the
                    // result of the request.
                }
            } else {
                startBluetooth()
            }
        } else {
            startBluetooth()
        }
    }

    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<String>, grantResults: IntArray
    ) {
        when (requestCode) {
            Values.MY_PERMISSIONS_REQUEST_ACCESS_COARSE_LOCATION -> {
                // If request is cancelled, the result arrays are empty.
                if ((grantResults.isNotEmpty() && grantResults[0] == PackageManager.PERMISSION_GRANTED)) {
                    // permission was granted, yay! Do the
                    // contacts-related task you need to do.
                    startBluetooth()
                } else {
                    // permission denied, boo! Disable the
                    // functionality that depends on this permission.
                }
                return
            }

            // Add other 'when' lines to check for other
            // permissions this app might request.
            else -> {
                // Ignore all other requests.
            }
        }
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)

        if (requestCode == Values.REQUEST_ENABLE_INTENT) {
            if (resultCode == Activity.RESULT_OK) {
                startBluetooth()
            }
        }
    }

    private fun startBluetooth() {
        val bluetoothManager = A5App().getInstance().getSystemService(Context.BLUETOOTH_SERVICE) as BluetoothManager
        val bluetoothAdapter = bluetoothManager.adapter

        if (bluetoothAdapter == null || !bluetoothAdapter.isEnabled) {
            val enableIntent = Intent(BluetoothAdapter.ACTION_REQUEST_ENABLE)
            startActivityForResult(enableIntent, Values.REQUEST_ENABLE_INTENT)
        } else {
            A5DeviceManager.setCallback(this)
            A5DeviceManager.scanForDevices()
        }
    }

    private fun setConnectDisconnectButtonsVisibility(isConnectButtonVisible: Boolean) {
        if (isConnectButtonVisible) {
            connectButton.visibility = View.VISIBLE
            disconnectButton.visibility = View.INVISIBLE
        } else {
            connectButton.visibility = View.INVISIBLE
            disconnectButton.visibility = View.VISIBLE
        }
    }

    private fun setSendStopButtonsVisibility(isSendStopButtonVisible: Boolean) {
        if (isSendStopButtonVisible) {
            sendStopCommandButton.visibility = View.VISIBLE
            abortStopCommandButton.visibility = View.INVISIBLE
        } else {
            sendStopCommandButton.visibility = View.INVISIBLE
            abortStopCommandButton.visibility = View.VISIBLE
        }
    }

    private fun startTimer() {
        counter = 0
        countDownTimer = object : CountDownTimer(420000, 1000) {
            override fun onTick(millisUntilFinished: Long) {
                counter++
                timerTextView.text = String.format(Locale.US, "%d", counter)
            }

            override fun onFinish() {
                timerTextView.text = "7 mins elapsed"
            }
        }.start()
    }

    private fun stopTimer() {
        countDownTimer?.cancel()
    }
}
