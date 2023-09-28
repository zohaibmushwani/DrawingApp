package com.mashwani.funsoltask

import android.content.pm.PackageManager
import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import com.mashwani.funsoltask.databinding.ActivityMainBinding
import com.mashwani.funsoltask.utils.SharedPreferencesHelper

class MainActivity : AppCompatActivity(), FeatureSheet.FeatureSheetListener {

    private lateinit var binding: ActivityMainBinding

    companion object {
        private const val REQUEST_WRITE_EXTERNAL_STORAGE = 1
    }


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        SharedPreferencesHelper.init(this)

        // Set up Bottom Sheet
        val bottomSheetFragment = FeatureSheet()

        binding.featureSheetHandle.setOnClickListener {
            bottomSheetFragment.show(supportFragmentManager, "BottomSheetDialog")
        }

        bottomSheetFragment.dialog?.setOnDismissListener {
            // This code runs when the bottom sheet is dismissed.
        }
    }

    override fun onStrokeChanged(strokeWidth: Float) {
        binding.drawingView.setStrokeWidth(strokeWidth)
    }

    override fun onColorChanged(color: Int) {
        binding.drawingView.setDrawColor(color)
    }

    override fun onSaveCamvas() {
        if (isWriteExternalStoragePermissionGranted()) {
            binding.drawingView.saveCanvasToGallery()

        } else {
            requestWriteExternalStoragePermission()
        }
    }

    private fun isWriteExternalStoragePermissionGranted(): Boolean {
        return ContextCompat.checkSelfPermission(
            this,
            android.Manifest.permission.WRITE_EXTERNAL_STORAGE
        ) == PackageManager.PERMISSION_GRANTED
    }

    private fun requestWriteExternalStoragePermission() {
        ActivityCompat.requestPermissions(
            this,
            arrayOf(android.Manifest.permission.WRITE_EXTERNAL_STORAGE),
            REQUEST_WRITE_EXTERNAL_STORAGE
        )
    }

    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<out String>,
        grantResults: IntArray
    ) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)

        if (requestCode == REQUEST_WRITE_EXTERNAL_STORAGE) {
            if (grantResults.isNotEmpty() && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                binding.drawingView.saveCanvasToGallery()
            } else {
                Toast.makeText(
                    this,
                    "app need this permission to save the drawing!!",
                    Toast.LENGTH_LONG
                ).show()
            }
        }
    }

    override fun onCanvasClear() {
        binding.drawingView.clearCanvas()
    }
}
