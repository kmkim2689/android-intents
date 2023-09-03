package com.practice.intent_practice

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.provider.Settings
import android.util.Log

class AirPlaneModeReceiver: BroadcastReceiver() {
    companion object {
        const val TAG = "AirPlaneModeReceiver"
    }
    override fun onReceive(context: Context?, intent: Intent?) {
        if (intent?.action == Intent.ACTION_AIRPLANE_MODE_CHANGED) {
            val isTurnedOn = Settings.Global.getInt(
                context?.contentResolver,
                Settings.Global.AIRPLANE_MODE_ON
            ) != 0
            Log.d(TAG, "airplane mode : $isTurnedOn")
        }
    }

}