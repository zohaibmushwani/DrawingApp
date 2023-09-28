package com.mashwani.funsoltask.adapters


import android.graphics.drawable.ShapeDrawable
import android.graphics.drawable.shapes.OvalShape
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.mashwani.funsoltask.R
import com.mashwani.funsoltask.utils.SharedPreferencesHelper



class ColorAdapter(
    private val colors: IntArray,
    private var selectedColorPosition: Int = 0,
    private val onColorSelected: (Int) -> Unit
) :
    RecyclerView.Adapter<ColorAdapter.ColorViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ColorViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.item_color, parent, false)
        return ColorViewHolder(view)
    }

    override fun onBindViewHolder(holder: ColorViewHolder, position: Int) {
        val color = colors[position]
        holder.colorCircle.setBackgroundColor(color)

        val isSelected = position == selectedColorPosition
        holder.border.visibility = if (isSelected) View.VISIBLE else View.INVISIBLE

        holder.itemView.background = createCircularDrawable(color)

        holder.itemView.setOnClickListener {
            val clickedPosition = holder.adapterPosition
            if (clickedPosition != RecyclerView.NO_POSITION) {
                selectedColorPosition = clickedPosition
                notifyDataSetChanged()
                SharedPreferencesHelper.saveInt(
                    SharedPreferencesHelper.SELECTED_COLOR,
                    clickedPosition
                )
                onColorSelected(colors[clickedPosition])
            }
        }
    }

    override fun getItemCount(): Int {
        return colors.size
    }

    private fun createCircularDrawable(color: Int): ShapeDrawable {
        val oval = ShapeDrawable(OvalShape())
        oval.paint.color = color
        oval.setBounds(0, 0, oval.intrinsicWidth, oval.intrinsicHeight)
        return oval
    }

    inner class ColorViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val colorCircle: View = itemView.findViewById(R.id.color_circle)
        val border: View = itemView.findViewById(R.id.border)
    }
}
