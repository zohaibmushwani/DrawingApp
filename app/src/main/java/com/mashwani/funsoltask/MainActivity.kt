package com.mashwani.funsoltask

import android.graphics.Color
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.mashwani.funsoltask.databinding.ActivityMainBinding
import com.mashwani.funsoltask.utils.SharedPreferencesHelper

class MainActivity : AppCompatActivity(), FeatureSheet.FeatureSheetListener {

    private lateinit var binding: ActivityMainBinding


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
}
