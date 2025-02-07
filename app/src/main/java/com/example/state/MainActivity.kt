package com.example.state

import android.Manifest
import android.os.Build
import android.os.Bundle
import android.widget.Toast
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.result.ActivityResultCallback
import androidx.activity.result.contract.ActivityResultContracts
import androidx.annotation.RequiresApi
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Surface
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import com.example.state.core.navigation.NavigationWrapper

class MainActivity : ComponentActivity() {

    @RequiresApi(Build.VERSION_CODES.TIRAMISU)
    private val requestPermissionLauncher = registerForActivityResult(
        ActivityResultContracts.RequestMultiplePermissions(),
        ActivityResultCallback { permissions ->
            val audioPermissionGranted = permissions[Manifest.permission.RECORD_AUDIO] == true
            //val readAudioPermissionGranted = permissions[Manifest.permission.READ_MEDIA_AUDIO] == true
            val dataPermissionGranted = permissions[Manifest.permission.READ_EXTERNAL_STORAGE] == true
            val dataAudioPermissionGranted = permissions[Manifest.permission.WRITE_EXTERNAL_STORAGE] == true
            val dataManageAudioPermissionGranted = permissions[Manifest.permission.MANAGE_EXTERNAL_STORAGE] == true

            if (audioPermissionGranted && dataPermissionGranted && dataAudioPermissionGranted && dataManageAudioPermissionGranted) {
                Toast.makeText(this, "Permisos concedidos", Toast.LENGTH_SHORT).show()
            } else {
                Toast.makeText(this, "Permisos denegados", Toast.LENGTH_SHORT).show()
            }
        }
    )

    @RequiresApi(Build.VERSION_CODES.TIRAMISU)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        requestPermissionLauncher.launch(
            arrayOf(
                Manifest.permission.RECORD_AUDIO,
                //Manifest.permission.READ_MEDIA_AUDIO
                Manifest.permission.WRITE_EXTERNAL_STORAGE,
                Manifest.permission.READ_EXTERNAL_STORAGE,
                Manifest.permission.MANAGE_EXTERNAL_STORAGE
            )
        )

        setContent {
            Surface(
                modifier = Modifier.fillMaxSize(),
                color = Color.Black
            ) {
                NavigationWrapper()
            }
        }
    }
}

