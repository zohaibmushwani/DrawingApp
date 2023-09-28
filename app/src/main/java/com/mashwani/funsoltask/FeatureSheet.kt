package com.mashwani.funsoltask

import android.content.Context
import android.graphics.Color
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.bottomsheet.BottomSheetBehavior
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import com.google.android.material.slider.Slider
import com.mashwani.funsoltask.adapters.ColorAdapter
import com.mashwani.funsoltask.databinding.FeatureSheetLayoutBinding
import com.mashwani.funsoltask.utils.SharedPreferencesHelper

class FeatureSheet : BottomSheetDialogFragment() {
    private lateinit var binding: FeatureSheetLayoutBinding

    interface FeatureSheetListener {
        fun onStrokeChanged(strokeWidth: Float)
        fun onColorChanged(color: Int)
        fun onSaveCamvas()
        fun onCanvasClear()
    }


    private var selectedColor: Int = Color.WHITE
    private var listener: FeatureSheetListener? = null

    private var selectedColorPosition: Int = 0


    override fun onAttach(context: Context) {
        super.onAttach(context)
        listener = context as? FeatureSheetListener
    }

    override fun onDetach() {
        super.onDetach()
        listener = null
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FeatureSheetLayoutBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val colors = intArrayOf(
            Color.DKGRAY,
            Color.GRAY,
            Color.LTGRAY,
            Color.WHITE,
            Color.RED,
            Color.GREEN,
            Color.BLUE,
            Color.YELLOW,
            Color.CYAN,
            Color.MAGENTA,
        )



        binding.recyclerViewColor.layoutManager =
            LinearLayoutManager(requireContext(), RecyclerView.HORIZONTAL, false)

        selectedColorPosition = SharedPreferencesHelper.getInt(
            SharedPreferencesHelper.SELECTED_COLOR,
            selectedColorPosition
        )

        binding.recyclerViewColor.adapter = ColorAdapter(colors, selectedColorPosition) {
            selectedColor = it
            listener?.onColorChanged(it)
        }


        val bottomSheetBehavior = BottomSheetBehavior.from(view.parent as View)
        bottomSheetBehavior.isDraggable = true

        binding.featureSheetHandle.setOnClickListener {
            dismiss()
        }

        binding.slider.addOnChangeListener { _, value, _ ->
            listener?.onStrokeChanged(value)
        }

        binding.saveBtn.setOnClickListener { listener?.onSaveCamvas() }
        binding.clearBtn.setOnClickListener { listener?.onCanvasClear() }
    }
}

