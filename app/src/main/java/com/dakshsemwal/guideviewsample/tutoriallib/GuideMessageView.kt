package com.dakshsemwal.guideviewsample.tutoriallib

import android.content.Context
import android.graphics.*
import android.text.Spannable
import android.util.TypedValue
import android.view.ViewGroup
import android.widget.Button
import android.widget.LinearLayout
import android.widget.TextView
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.constraintlayout.widget.ConstraintSet
import androidx.core.content.ContextCompat
import com.dakshsemwal.guideviewsample.R
import com.dakshsemwal.guideviewsample.tutoriallib.listener.IOnTourButtonClicked

/**
 * Created by Mohammad Reza Eram  on 20/01/2018.
 */
internal class GuideMessageView(context: Context, tourStep: Int, iOnTourButton: IOnTourButtonClicked?, isNeeded: Boolean?) : LinearLayout(context) {
    private val mPaint: Paint
    private val mRect: RectF
    private val mTitleTextView: TextView
    private val mContentTextView: TextView
    var layout: ConstraintLayout
    private val button: Button
    private val skip: Button

    fun getTitleTextView(): TextView {
        return mTitleTextView;
    }

    fun getContextTextView(): TextView {
        return mContentTextView;
    }

    fun getButton(): Button {
        return button;
    }

    fun getSkipButton(): Button {
        return skip;
    }

    fun removeButton(isNeeded: Boolean?) {
        if (isNeeded!!) {
            removeView(button)
            return
        }
        else{
            return
        }
    }

    fun setTitle(title: String?) {
        if (title == null) {
            removeView(mTitleTextView)
            return
        }
        mTitleTextView.text = title
    }

    fun setButtonText(text: String?) {
        if (text == null) {
            removeView(button)
            return
        }
        button.text = text
    }

    fun setContentText(content: String?) {
        mContentTextView.text = content
    }

    fun setContentSpan(content: Spannable?) {
        mContentTextView.text = content
    }

    fun setContentTypeFace(typeFace: Typeface?) {
        mContentTextView.typeface = typeFace
    }

    fun setTitleTypeFace(typeFace: Typeface?) {
        mTitleTextView.typeface = typeFace
    }

    fun setTitleTextSize(size: Int) {
        mTitleTextView.setTextSize(TypedValue.COMPLEX_UNIT_SP, size.toFloat())
    }

    fun setContentTextSize(size: Int) {
        mContentTextView.setTextSize(TypedValue.COMPLEX_UNIT_SP, size.toFloat())
    }

    fun setColor(color: Int) {
        mPaint.alpha = 255
        mPaint.color = color
        invalidate()
    }

    var location = IntArray(2)
    override fun onDraw(canvas: Canvas) {
        super.onDraw(canvas)
        getLocationOnScreen(location)
        mRect[paddingLeft.toFloat(), paddingTop.toFloat(), width - paddingRight.toFloat()] = height - paddingBottom.toFloat()
        canvas.drawRoundRect(mRect, 15f, 15f, mPaint)
    }

    init {
        val density = context.resources.displayMetrics.density
        setWillNotDraw(false)
        orientation = VERTICAL
        mRect = RectF()
        mPaint = Paint(Paint.ANTI_ALIAS_FLAG)
        mPaint.strokeCap = Paint.Cap.ROUND
        val padding = (10 * density).toInt()
        val paddingBetween = (3 * density).toInt()
        layout = ConstraintLayout(context)
        layout.id = generateViewId()
        addView(layout, LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT))
        mTitleTextView = TextView(context)
        mTitleTextView.id = generateViewId()
        mTitleTextView.setPadding(padding, padding, padding, paddingBetween)
        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.M) {
            mTitleTextView.setTextAppearance(R.style.fontForGuideView)
        }
        else {
            mTitleTextView.setTextAppearance(context,R.style.fontForGuideView)
        }
        mTitleTextView.setTextSize(TypedValue.COMPLEX_UNIT_DIP, 18f)
        mTitleTextView.setTextColor(ContextCompat.getColor(context, R.color.jordy_blue))
        val clTitle = ConstraintLayout.LayoutParams(
                ConstraintLayout.LayoutParams.WRAP_CONTENT, ConstraintLayout.LayoutParams.WRAP_CONTENT)
        mTitleTextView.layoutParams = clTitle
        layout.addView(mTitleTextView)
        mContentTextView = TextView(context)
        mContentTextView.id = generateViewId()
        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.M) {
            mContentTextView.setTextAppearance(R.style.fontForGuideView)
        }
        else {
            mContentTextView.setTextAppearance(context,R.style.fontForGuideView)
        }
        mContentTextView.setTextColor(Color.WHITE)
        mContentTextView.setTextSize(TypedValue.COMPLEX_UNIT_DIP, 14f)
        mContentTextView.setPadding(padding, paddingBetween, padding, padding)
        val clSubtitle = ConstraintLayout.LayoutParams(
                ConstraintLayout.LayoutParams.WRAP_CONTENT, ConstraintLayout.LayoutParams.WRAP_CONTENT)
        mContentTextView.layoutParams = clSubtitle
        layout.addView(mContentTextView)
        button = Button(context)
        button.id = generateViewId()
        button.setTextColor(Color.WHITE)
        button.isAllCaps = false
        button.background = ContextCompat.getDrawable(context, R.drawable.btn_guidedview)
        button.setTextSize(TypedValue.COMPLEX_UNIT_DIP, 12f)
        button.setOnClickListener {
            iOnTourButton!!.onTourButtonClicked(tourStep)
        }
        /* button.setPadding(padding, paddingBetween, padding, padding);*/
        val clButton = ConstraintLayout.LayoutParams(
                ConstraintLayout.LayoutParams.WRAP_CONTENT, ConstraintLayout.LayoutParams.WRAP_CONTENT)
        button.layoutParams = clButton
        layout.addView(button)

        skip = Button(context)
        skip.id = generateViewId()
        skip.setTextColor(Color.BLACK)
        skip.isAllCaps = false
        skip.text="Skip"
        skip.background = ContextCompat.getDrawable(context, R.drawable.btn_guidedview_skip)
        skip.setTextSize(TypedValue.COMPLEX_UNIT_DIP, 12f)
        skip.setOnClickListener {
            iOnTourButton!!.onTourButtonClicked(tourStep)
        }
        /* button.setPadding(padding, paddingBetween, padding, padding);*/
        val skButton = ConstraintLayout.LayoutParams(
                ConstraintLayout.LayoutParams.WRAP_CONTENT, ConstraintLayout.LayoutParams.WRAP_CONTENT)

        skip.layoutParams = skButton
        layout.addView(skip)

        val constraintSet = ConstraintSet()
        constraintSet.clone(layout)
        constraintSet.connect(mTitleTextView.id, ConstraintSet.TOP, layout.id, ConstraintSet.TOP, 18)
        constraintSet.connect(mTitleTextView.id, ConstraintSet.LEFT, layout.id, ConstraintSet.LEFT, 18)
        constraintSet.connect(mContentTextView.id, ConstraintSet.TOP, mTitleTextView.id, ConstraintSet.BOTTOM, 18)
        constraintSet.connect(mContentTextView.id, ConstraintSet.LEFT, layout.id, ConstraintSet.LEFT, 18)
        constraintSet.connect(mContentTextView.id, ConstraintSet.RIGHT, layout.id, ConstraintSet.RIGHT, 18)
        constraintSet.connect(skip.id, ConstraintSet.TOP, mContentTextView.id, ConstraintSet.BOTTOM, 18)
        constraintSet.connect(skip.id, ConstraintSet.RIGHT, layout.id, ConstraintSet.RIGHT, 18)
        constraintSet.connect(button.id, ConstraintSet.TOP, skip.id, ConstraintSet.TOP, 0)
        constraintSet.connect(button.id, ConstraintSet.RIGHT, skip.id, ConstraintSet.LEFT, 18)
        constraintSet.applyTo(layout)
    }

}