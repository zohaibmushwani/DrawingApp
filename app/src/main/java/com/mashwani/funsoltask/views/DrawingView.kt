package com.mashwani.funsoltask.views


import android.content.Context
import android.graphics.Bitmap
import android.graphics.Canvas
import android.graphics.Color
import android.graphics.Paint
import android.graphics.Path
import android.util.AttributeSet
import android.view.MotionEvent
import android.view.View
import android.view.ViewConfiguration
import kotlin.math.abs

private const val STROKE_WIDTH = 12f

class DrawingView(context: Context, attrs: AttributeSet) : View(context, attrs) {

    private var path = Path()

    private var drawColor = Color.WHITE
    private var strokeWidth = STROKE_WIDTH

    private val backgroundColor = Color.BLACK


    private lateinit var extraCanvas: Canvas
    private lateinit var extraBitmap: Bitmap


    private val paint = Paint().apply {
        color = drawColor

        isAntiAlias = true

        isDither = true
        style = Paint.Style.STROKE
        strokeJoin = Paint.Join.ROUND
        strokeCap = Paint.Cap.ROUND
        strokeWidth = STROKE_WIDTH
    }

    private val touchTolerance = ViewConfiguration.get(context).scaledTouchSlop

    private var currentX = 0f
    private var currentY = 0f

    private var motionTouchEventX = 0f
    private var motionTouchEventY = 0f

    override fun onSizeChanged(width: Int, height: Int, oldWidth: Int, oldHeight: Int) {
        super.onSizeChanged(width, height, oldWidth, oldHeight)

        if (::extraBitmap.isInitialized) extraBitmap.recycle()
        extraBitmap = Bitmap.createBitmap(width, height, Bitmap.Config.ARGB_8888)
        extraCanvas = Canvas(extraBitmap)
        extraCanvas.drawColor(backgroundColor)
    }

    // Method to set the drawing color
    fun setDrawColor(color: Int) {
        drawColor = color
        paint.color = drawColor
    }

    // Method to set the stroke width
    fun setStrokeWidth(width: Float) {
        strokeWidth = width
        paint.strokeWidth = strokeWidth
    }
    // Method to clear the canvas
    fun clearCanvas() {
        // Draw the background color over the bitmap
        extraCanvas.drawColor(backgroundColor)
        // Invalidate the view to refresh the canvas
        invalidate()
    }


    override fun onDraw(canvas: Canvas) {
        canvas.drawBitmap(extraBitmap, 0f, 0f, null)
    }

    override fun onTouchEvent(event: MotionEvent): Boolean {
        motionTouchEventX = event.x
        motionTouchEventY = event.y

        when (event.action) {
            MotionEvent.ACTION_DOWN -> touchStart()
            MotionEvent.ACTION_MOVE -> touchMove()
            MotionEvent.ACTION_UP -> touchUp()
        }
        return true
    }

    private fun touchStart() {
        path.reset()
        path.moveTo(motionTouchEventX, motionTouchEventY)
        currentX = motionTouchEventX
        currentY = motionTouchEventY

        paint.style = Paint.Style.FILL
        extraCanvas.drawCircle(currentX, currentY, strokeWidth / 2, paint)
        paint.style = Paint.Style.STROKE
        invalidate()
    }

    private fun touchMove() {
        val dx = abs(motionTouchEventX - currentX)
        val dy = abs(motionTouchEventY - currentY)
        if (dx >= touchTolerance || dy >= touchTolerance) {

            path.quadTo(
                currentX,
                currentY,
                (motionTouchEventX + currentX) / 2,
                (motionTouchEventY + currentY) / 2
            )
            currentX = motionTouchEventX
            currentY = motionTouchEventY

            extraCanvas.drawPath(path, paint)
        }

        invalidate()
    }

    private fun touchUp() {
        path.reset()
    }
}
