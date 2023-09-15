package com.example.kamerka

import android.content.Intent
import android.content.pm.PackageManager
import android.graphics.Bitmap
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.provider.MediaStore
import android.util.Log
import androidx.core.app.ActivityCompat
import com.example.kamerka.databinding.ActivityMainBinding

@Suppress("DEPRECATION")
class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding
    private var PERMISSION_ID = 1000

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.buttonPicture.isEnabled = false
        if(ActivityCompat.checkSelfPermission(this,android.Manifest.permission.CAMERA) != PackageManager.PERMISSION_GRANTED)
            ActivityCompat.requestPermissions(this, arrayOf(android.Manifest.permission.CAMERA),111)
        else
            binding.buttonPicture.isActivated = true

        binding.buttonPicture.setOnClickListener {
            val i = Intent(MediaStore.ACTION_IMAGE_CAPTURE)
            startActivityForResult(i, 101)
        }
    }

    @Deprecated("Deprecated in Java")
    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)

        if(requestCode == 101) {
            val pic: Bitmap? = data?.getParcelableExtra("data")
            binding.ivCameraPic.setImageBitmap(pic)
        }
    }

    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<out String>,
        grantResults: IntArray
    ) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        if(requestCode == 111 && grantResults[0] == PackageManager.PERMISSION_GRANTED)
            binding.buttonPicture.isEnabled = true

        if(requestCode == PERMISSION_ID){
            if(grantResults.isNotEmpty() && grantResults[0] == PackageManager.PERMISSION_GRANTED){
                Log.d("Debug", "Permission Granted")
            }
        }
    }

}