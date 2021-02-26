package com.dakshsemwal.guideviewsample.tutoriallib

import android.app.Activity
import android.content.Context
import android.graphics.*
import android.text.Spannable
import android.view.MotionEvent
import android.view.View
import android.view.ViewGroup
import android.view.animation.AlphaAnimation
import android.widget.FrameLayout
import com.dakshsemwal.guideviewsample.tutoriallib.listener.SkipPressed
import com.dakshsemwal.guideviewsample.tutoriallib.config.DismissType
import com.dakshsemwal.guideviewsample.tutoriallib.config.GRAVITY
import com.dakshsemwal.guideviewsample.tutoriallib.config.TargetType
import com.dakshsemwal.guideviewsample.tutoriallib.listener.GuideListener
import com.dakshsemwal.guideviewsample.tutoriallib.listener.IOnTourButtonClicked
import android.view.ViewTreeObserver.OnGlobalLayoutListener as OnGlobalLayoutListener1


class GuideView private constructor(context: Context, view: View?, type: TargetType?, tourStep: Int, iOnTourButton: IOnTourButtonClicked?) : FrameLayout(context) {
    private val selfPaint = Paint()
    private val paintLine = Paint()
    private val paintCircle = Paint()
    private val paintCircle2 = Paint()
    private val paintCircleInner = Paint()
    private val targetPaint = Paint(Paint.ANTI_ALIAS_FLAG)
    private val X_FER_MODE_CLEAR: Xfermode = PorterDuffXfermode(PorterDuff.Mode.CLEAR)
    private val target: View?
    var tourStep: Int = 0

    private var targetRect: RectF? = null
    private val selfRect = Rect()
    private val density: Float
    private var stopY = 0f
    private var isTop = false
    var isShowing = false
        private set
    private var yMessageView = 0
    private var startYLineAndCircle = 0f
    private var circleIndicatorSize = 0f
    private var circleIndicatorSizeFinal = 0f
    private var circleInnerIndicatorSize = 0f
    private var lineIndicatorWidthSize = 0f
    private var messageViewPadding = 0
    private var marginGuide = 0f
    private var strokeCircleWidth = 0f
    private var indicatorHeight = 0f
    private var mGuideListener: GuideListener? = null
    private var iOnTourButton: IOnTourButtonClicked? = null
    private var skipPresse: SkipPressed? = null
    private var mGravity: GRAVITY? = null
    private var mTargetType: TargetType? = null
    private var dismissType: DismissType? = null
    private val mMessageView: GuideMessageView
    private var isNeeded: Boolean? = true

    private fun init() {
        lineIndicatorWidthSize = LINE_INDICATOR_WIDTH_SIZE * density
        marginGuide = MARGIN_INDICATOR * density
        indicatorHeight = INDICATOR_HEIGHT * density
        messageViewPadding = (MESSAGE_VIEW_PADDING * density).toInt()
        strokeCircleWidth = STROKE_CIRCLE_INDICATOR_SIZE * density
        circleIndicatorSizeFinal = CIRCLE_INDICATOR_SIZE * density
    }

    override fun onDraw(canvas: Canvas) {
        super.onDraw(canvas)
        if (target != null) {
            selfPaint.color = BACKGROUND_COLOR
            selfPaint.style = Paint.Style.FILL
            selfPaint.isAntiAlias = true
            canvas.drawRect(selfRect, selfPaint)
            paintLine.style = Paint.Style.FILL
            paintLine.color = LINE_INDICATOR_COLOR
            paintLine.strokeWidth = lineIndicatorWidthSize
            paintLine.isAntiAlias = true
            paintCircle.style = Paint.Style.FILL
            paintCircle2.style = Paint.Style.STROKE
            paintCircle.color = CIRCLE_INDICATOR_COLOR
            paintCircle2.color = Color.parseColor("#236EA5")
            paintCircle.strokeCap = Paint.Cap.ROUND
            paintCircle2.strokeCap = Paint.Cap.ROUND
            paintCircle.strokeWidth = strokeCircleWidth
            paintCircle2.strokeWidth = strokeCircleWidth
            paintCircle.isAntiAlias = true
            paintCircle2.isAntiAlias = true
            paintCircleInner.style = Paint.Style.FILL
            paintCircleInner.color = CIRCLE_INNER_INDICATOR_COLOR
            paintCircleInner.isAntiAlias = true
            val x = (target.left / 2 + target.right / 2).toFloat()
            val location = IntArray(2)
            target.getLocationOnScreen(location)
            val location1 = IntArray(2)
            val view: View
            var circleposX = location[0] + target.width.toFloat()
            var circleposY = location[1].toFloat() + target.height / 2
            var x2 = selfRect.right.toFloat()
            var x3 = selfRect.left.toFloat()
            var y2 = selfRect.bottom.toFloat()
            targetPaint.xfermode = X_FER_MODE_CLEAR
            targetPaint.isAntiAlias = true
            if (target is Targetable) {
                (target as Targetable).guidePath()?.let { canvas.drawPath(it, targetPaint) }
            } else {
                if (mTargetType == TargetType.CIRCLE) {
                    canvas.drawCircle(targetRect!!.centerX(), targetRect!!.centerY(), target.width / 2.toFloat(), targetPaint)
                    canvas.drawCircle(targetRect!!.centerX(), targetRect!!.centerY(), target.width / 2 + 5.toFloat(), paintCircle2)
                } else
                    canvas.drawRoundRect(targetRect!!, RADIUS_SIZE_TARGET_RECT.toFloat(), RADIUS_SIZE_TARGET_RECT.toFloat(), targetPaint)
            }

            when (tourStep) {
                1 -> {
                    view = mMessageView.getTitleTextView()
                    view.getLocationOnScreen(location1)
                    val x1 = location1[0].toFloat()
                    val y1 = location1[1] + view.height.toFloat()
                    circleposX = location[0].toFloat()
                    circleposY = location[1].toFloat() + target.height / 2
                    canvas.drawLine(x1, y1, x1, circleposY, paintLine)
                    canvas.drawLine(x1, circleposY, circleposX - circleIndicatorSize - 10, circleposY, paintLine)
                    canvas.drawCircle(circleposX, circleposY, circleIndicatorSize, paintCircle)
                    canvas.drawCircle(circleposX, circleposY, circleInnerIndicatorSize, paintCircleInner)
                }
                2 -> {
                    view = mMessageView.getTitleTextView()
                    view.getLocationOnScreen(location1)
                    val x1 = location1[0].toFloat()
                    val y1 = location1[1] + view.height / 2.toFloat()
                    circleposX = location[0] - 30.toFloat()
                    canvas.drawLine(x1, y1, circleposX, y1, paintLine)
                    canvas.drawLine(circleposX, y1, circleposX, circleposY - circleIndicatorSize - 10, paintLine)
                    canvas.drawCircle(circleposX, circleposY, circleIndicatorSize, paintCircle)
                    canvas.drawCircle(circleposX, circleposY, circleInnerIndicatorSize, paintCircleInner)
                }
                3 -> {
                    view = mMessageView.getTitleTextView()
                    view.getLocationOnScreen(location1)
                    val x1 = location1[0].toFloat()
                    val y1 = location1[1] + view.height.toFloat()
                    circleposX = location[0].toFloat()
                    circleposY = location[1].toFloat() + target.height / 2
                    canvas.drawLine(x1, y1, x1, circleposY, paintLine)
                    canvas.drawLine(x1, circleposY, circleposX - circleIndicatorSize - 10, circleposY, paintLine)
                    canvas.drawCircle(circleposX, circleposY, circleIndicatorSize, paintCircle)
                    canvas.drawCircle(circleposX, circleposY, circleInnerIndicatorSize, paintCircleInner)
                }
                4 -> {
                    view = mMessageView.getTitleTextView()
                    view.getLocationOnScreen(location1)
                    val x1 = location1[0].toFloat()
                    val y1 = location1[1] + view.height / 2.toFloat()
                    circleposX = location[0] - 30.toFloat()
                    canvas.drawLine(x1, y1, x1, circleposY, paintLine)
                    canvas.drawLine(x1, circleposY, circleposX - circleIndicatorSize - 10, circleposY, paintLine)
                    canvas.drawCircle(circleposX, circleposY, circleIndicatorSize, paintCircle)
                    canvas.drawCircle(circleposX, circleposY, circleInnerIndicatorSize, paintCircleInner)
                }
                5 -> {
                    view = mMessageView.getTitleTextView()
                    view.getLocationOnScreen(location1)
                    val x1 = location1[0].toFloat()
                    val y1 = location1[1] + view.height / 2.toFloat()
                    circleposX = location[0] - 30.toFloat()
                    canvas.drawLine(x1, y1, x1, circleposY, paintLine)
                    canvas.drawLine(x1, circleposY, circleposX - circleIndicatorSize - 10, circleposY, paintLine)
                    canvas.drawCircle(circleposX, circleposY, circleIndicatorSize, paintCircle)
                    canvas.drawCircle(circleposX, circleposY, circleInnerIndicatorSize, paintCircleInner)
                }
                6 -> {
                    view = mMessageView.getContextTextView()
                    view.getLocationOnScreen(location1)
                    val x1 = location1[0].toFloat()
                    val y1 = location1[1] + view.height.toFloat()
                    circleposX = x3 + location[0] + 100*density
                    circleposY = location[1] + 100*density
                    /*canvas.drawLine(x1, y1, circleposX, y1, paintLine)*/
                    canvas.drawLine(circleposX, y1, circleposX, circleposY - circleIndicatorSize - 10, paintLine)
                    canvas.drawCircle(circleposX, circleposY, circleIndicatorSize, paintCircle)
                    canvas.drawCircle(circleposX, circleposY, circleInnerIndicatorSize, paintCircleInner)
                }
                7 -> {
                    view = mMessageView.getTitleTextView()
                    view.getLocationOnScreen(location1)
                    val x1 = location1[0] + (view.width.toFloat() / 2)
                    val y1 = location1[1].toFloat()
                    circleposX = location[0] + target.width / 2.toFloat()
                    circleposY = location[1] + target.height.toFloat()
                    canvas.drawLine(circleposX, y1, circleposX, circleposY + circleIndicatorSize + 10, paintLine)
                    canvas.drawCircle(circleposX, circleposY, circleIndicatorSize, paintCircle)
                    canvas.drawCircle(circleposX, circleposY, circleInnerIndicatorSize, paintCircleInner)
                }
                8 -> {
                    view = mMessageView.getContextTextView()
                    view.getLocationOnScreen(location1)
                    val x1 = location1[0].toFloat()
                    val y1 = location1[1] + view.height / 2.toFloat()
                    circleposX = x1 - 20.toFloat()
                    circleposY = location[1].toFloat()
                    canvas.drawLine(x1, y1, circleposX, y1, paintLine)
                    canvas.drawLine(circleposX, y1, circleposX, circleposY - circleIndicatorSize - 10, paintLine)
                    canvas.drawCircle(circleposX, circleposY, circleIndicatorSize, paintCircle)
                    canvas.drawCircle(circleposX, circleposY, circleInnerIndicatorSize, paintCircleInner)
                }
                9 -> {
                    view = mMessageView.getContextTextView()
                    view.getLocationOnScreen(location1)
                    val y1 = location1[1] + view.height.toFloat()
                    circleposX = location[0] + target.width.toFloat()
                    canvas.drawLine(circleposX, y1, circleposX, circleposY - circleIndicatorSize - 10, paintLine)
                    canvas.drawCircle(circleposX, circleposY, circleIndicatorSize, paintCircle)
                    canvas.drawCircle(circleposX, circleposY, circleInnerIndicatorSize, paintCircleInner)
                }
                10 -> {
                    view = mMessageView.getContextTextView()
                    view.getLocationOnScreen(location1)
                    val x1 = location1[0].toFloat()
                    val y1 = location1[1] + view.height / 2.toFloat()
                    circleposX = x1 - 20.toFloat()
                    canvas.drawLine(x1, y1, circleposX, y1, paintLine)
                    canvas.drawLine(circleposX, y1, circleposX, circleposY - circleIndicatorSize - 10, paintLine)
                    canvas.drawCircle(circleposX, circleposY, circleIndicatorSize, paintCircle)
                    canvas.drawCircle(circleposX, circleposY, circleInnerIndicatorSize, paintCircleInner)
                }
            }
        }
    }

    fun dismiss() {
        ((context as Activity).window.decorView as ViewGroup).removeView(this)
        isShowing = false
        if (mGuideListener != null) {
            mGuideListener!!.onDismiss(target)
        }
        if (iOnTourButton != null) {
            iOnTourButton = null
            mMessageView.getButton().setOnClickListener { iOnTourButton }
            target!!.setOnClickListener { iOnTourButton }
        }
    }

    override fun onTouchEvent(event: MotionEvent): Boolean {
        val x = event.x
        val y = event.y
        if (event.action == MotionEvent.ACTION_DOWN) {
            target!!.performClick()
            return true
        }
        return false
    }

    private fun isViewContains(view: View, rx: Float, ry: Float): Boolean {
        val location = IntArray(2)
        view.getLocationOnScreen(location)
        val x = location[0]
        val y = location[1]
        val w = view.width
        val h = view.height
        return !(rx < x || rx > x + w || ry < y || ry > y + h)
    }

    private fun setMessageLocation(p: Point) {
        mMessageView.x = p.x.toFloat()
        mMessageView.y = p.y.toFloat()
        postInvalidate()
    }

    /*fun updateGuideViewLocation() {
        requestLayout()
    }*/

    private fun resolveMessageViewLocation(): Point {

        var xMessageView = 0
        val locationTarget = IntArray(2)
        target!!.getLocationOnScreen(locationTarget)
        val x_pos = locationTarget[0] + target.width
        val y_pos = locationTarget[1] + target.height
        val dis_y = selfRect.bottom
        val dis_x = selfRect.right
        val dis_x1 = selfRect.left
        val targetRect_x = targetRect!!.top
        val targetRect_y = targetRect!!.right
        when {
            tourStep == 1 ->{
                xMessageView = (locationTarget[0] - target.width/2)
                yMessageView = (target.top - 50).toInt()
            }
            tourStep == 2  -> {
                xMessageView = locationTarget[0] - 20
                yMessageView = y_pos - target.height * 4 - mMessageView.getButton().height
            }
            tourStep == 3 -> {
                xMessageView = (locationTarget[0] - 40*density).toInt()
                yMessageView = y_pos - target.height * 4 - mMessageView.getButton().height
            }
            tourStep == 4 -> {
                xMessageView = dis_x + 40
                yMessageView = y_pos - target.height * 4 - mMessageView.getButton().height
            }
            tourStep == 5  -> {
                xMessageView = dis_x + 40
                yMessageView = y_pos - target.height * 4 - mMessageView.getButton().height
            }
            tourStep == 6 -> {
                xMessageView = (targetRect!!.left - 100 + target.width / 2).toInt()
                yMessageView = locationTarget[1] - target.height / 2 - 150
            }
            tourStep == 7 -> {
                xMessageView = (targetRect!!.left - mMessageView.width / 2 + target.width / 2).toInt()
                yMessageView = y_pos + target.height
            }
            tourStep == 8 -> {
                xMessageView = locationTarget[0] + 90
                yMessageView = y_pos - target.height * 3
            }
            tourStep == 9 -> {
                xMessageView = (targetRect!!.left - 60 + target.width / 2).toInt()
                yMessageView = y_pos - target.height * 4 - mMessageView.getButton().height
            }
            tourStep == 10 -> {
                xMessageView = (targetRect!!.left - mMessageView.width / 2 + target.width / 2).toInt()
                yMessageView = (target.top - 150)
            }
        }
        return Point(xMessageView, yMessageView)
    }

    fun show() {
        this.layoutParams = ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT,
                ViewGroup.LayoutParams.MATCH_PARENT)
        this.isClickable = false
        ((context as Activity).window.decorView as ViewGroup).addView(this)
        val startAnimation = AlphaAnimation(0.0f, 1.0f)
        startAnimation.duration = APPEARING_ANIMATION_DURATION.toLong()
        startAnimation.fillAfter = true
        startAnimation(startAnimation)
        isShowing = true
    }

    fun setTitle(str: String?) {
        mMessageView.setTitle(str)
    }

    fun setButton(isNeeded: Boolean?) {
        if (!isNeeded!!) {
            mMessageView.getButton().visibility = INVISIBLE
            target!!.setOnClickListener { iOnTourButton!!.onTourButtonClicked(tourStep) }
        }
    }

    fun setButtonTitle(str: String?) {
        mMessageView.setButtonText(str)
    }

    fun setContentText(str: String?) {
        mMessageView.setContentText(str)
    }

    fun setContentSpan(span: Spannable?) {
        mMessageView.setContentSpan(span)
    }

    fun setTitleTypeFace(typeFace: Typeface?) {
        mMessageView.setTitleTypeFace(typeFace)
    }

    fun setContentTypeFace(typeFace: Typeface?) {
        mMessageView.setContentTypeFace(typeFace)
    }

    fun setTitleTextSize(size: Int) {
        mMessageView.setTitleTextSize(size)
    }

    fun setContentTextSize(size: Int) {
        mMessageView.setContentTextSize(size)
    }

    class Builder(private val context: Context) {
        private var targetView: View? = null
        private var title: String? = null
        private var contentText: String? = null
        private var buttonText: String? = null
        private var gravity: GRAVITY? = null
        private var targetType: TargetType? = null
        private var dismissType: DismissType? = null
        private var contentSpan: Spannable? = null
        private var titleTypeFace: Typeface? = null
        private var contentTypeFace: Typeface? = null
        private var guideListener: GuideListener? = null
        private var titleTextSize = 0
        private var contentTextSize = 0
        private var lineIndicatorHeight = 0f
        private var lineIndicatorWidthSize = 0f
        private var circleIndicatorSize = 0f
        private var circleInnerIndicatorSize = 0f
        private var strokeCircleWidth = 0f
        private var tourStep = 0
        private var isNeeded: Boolean? = true
        private var iOnTourButton: IOnTourButtonClicked? = null
        private var skipPresse: SkipPressed? = null

        fun setMessageView(tourStep: Int, iOnTourButton: IOnTourButtonClicked, view: View?, title: String?, contentText: String?, buttonText: String?, isNeeded: Boolean?, skipPresse: SkipPressed?): Builder {
            this.tourStep = tourStep
            this.targetView = view
            this.iOnTourButton = iOnTourButton
            this.skipPresse = skipPresse
            this.title = title
            this.contentText = contentText
            this.buttonText = buttonText
            this.isNeeded = isNeeded
            return this
        }

        /**
         * gravity GuideView
         *
         * @param gravity it should be one type of Gravity enum.
         */
        fun setGravity(gravity: GRAVITY?): Builder {
            this.gravity = gravity
            return this
        }

        /**
         * target TargetType
         *
         * @param targetType it should be one type of Gravity enum.
         */
        fun setTargetViewType(targetType: TargetType?): Builder {
            this.targetType = targetType
            return this
        }


        /**
         * setting spannable type
         *
         * @param span a instance of spannable
         */
        fun setContentSpan(span: Spannable?): Builder {
            contentSpan = span
            return this
        }

        /**
         * setting font type face
         *
         * @param typeFace a instance of type face (font family)
         */
        fun setContentTypeFace(typeFace: Typeface?): Builder {
            contentTypeFace = typeFace
            return this
        }

        /**
         * adding a listener on show case view
         *
         * @param guideListener a listener for events
         */
        fun setGuideListener(guideListener: GuideListener?): Builder {
            this.guideListener = guideListener
            return this
        }

        /**
         * setting font type face
         *
         * @param typeFace a instance of type face (font family)
         */
        fun setTitleTypeFace(typeFace: Typeface?): Builder {
            titleTypeFace = typeFace
            return this
        }

        /**
         * the defined text size overrides any defined size in the default or provided style
         *
         * @param size title text by sp unit
         * @return builder
         */
        fun setContentTextSize(size: Int): Builder {
            contentTextSize = size
            return this
        }

        /**
         * the defined text size overrides any defined size in the default or provided style
         *
         * @param size title text by sp unit
         * @return builder
         */
        fun setTitleTextSize(size: Int): Builder {
            titleTextSize = size
            return this
        }

        /**
         * this method defining the type of dismissing function
         *
         * @param dismissType should be one type of DismissType enum. for example: outside -> Dismissing with click on outside of MessageView
         */
        fun setDismissType(dismissType: DismissType?): Builder {
            this.dismissType = dismissType
            return this
        }

        /**
         * changing line height indicator
         *
         * @param height you can change height indicator (Converting to Dp)
         */
        fun setIndicatorHeight(height: Float): Builder {
            lineIndicatorHeight = height
            return this
        }

        /**
         * changing line width indicator
         *
         * @param width you can change width indicator
         */
        fun setIndicatorWidthSize(width: Float): Builder {
            lineIndicatorWidthSize = width
            return this
        }

        /**
         * changing circle size indicator
         *
         * @param size you can change circle size indicator
         */
        fun setCircleIndicatorSize(size: Float): Builder {
            circleIndicatorSize = size
            return this
        }

        /**
         * changing inner circle size indicator
         *
         * @param size you can change inner circle indicator size
         */
        fun setCircleInnerIndicatorSize(size: Float): Builder {
            circleInnerIndicatorSize = size
            return this
        }

        /**
         * changing stroke circle size indicator
         *
         * @param size you can change stroke circle indicator size
         */
        fun setCircleStrokeIndicatorSize(size: Float): Builder {
            strokeCircleWidth = size
            return this
        }

        fun build(): GuideView {
            val guideView = GuideView(context, targetView, targetType, tourStep, iOnTourButton)
            guideView.mGravity = if (gravity != null) gravity else GRAVITY.auto
            guideView.tourStep = tourStep
            guideView.mTargetType = if (targetType != null) targetType else TargetType.CIRCLE
            guideView.dismissType = if (dismissType != null) dismissType else DismissType.targetView
            guideView.isNeeded = isNeeded
            guideView.iOnTourButton = iOnTourButton
            guideView.skipPresse = skipPresse
            val density = context.resources.displayMetrics.density
            guideView.setTitle(title)
            guideView.setButtonTitle(buttonText)
            if (contentText != null) guideView.setContentText(contentText)
            if (titleTextSize != 0) guideView.setTitleTextSize(titleTextSize)
            if (contentTextSize != 0) guideView.setContentTextSize(contentTextSize)
            if (contentSpan != null) guideView.setContentSpan(contentSpan)
            if (!isNeeded!!) guideView.setButton(isNeeded)
            if (titleTypeFace != null) {
                guideView.setTitleTypeFace(titleTypeFace)
            }
            if (contentTypeFace != null) {
                guideView.setContentTypeFace(contentTypeFace)
            }
            /*if (guideListener != null) {
                guideView.mGuideListener = guideListener
            }*/
            if (lineIndicatorHeight != 0f) {
                guideView.indicatorHeight = lineIndicatorHeight * density
            }
            if (lineIndicatorWidthSize != 0f) {
                guideView.lineIndicatorWidthSize = lineIndicatorWidthSize * density
            }
            if (circleIndicatorSize != 0f) {
                guideView.circleIndicatorSize = circleIndicatorSize * density
            }
            if (circleInnerIndicatorSize != 0f) {
                guideView.circleInnerIndicatorSize = circleInnerIndicatorSize * density
            }
            if (strokeCircleWidth != 0f) {
                guideView.strokeCircleWidth = strokeCircleWidth * density
            }
            return guideView
        }
    }

    companion object {
        const val TAG = "GuideView"
        private const val INDICATOR_HEIGHT = 40
        private const val MESSAGE_VIEW_PADDING = 5
        private const val SIZE_ANIMATION_DURATION = 700
        private const val APPEARING_ANIMATION_DURATION = 400
        private const val CIRCLE_INDICATOR_SIZE = 6
        private const val LINE_INDICATOR_WIDTH_SIZE = 3
        private const val STROKE_CIRCLE_INDICATOR_SIZE = 3
        private const val RADIUS_SIZE_TARGET_RECT = 15
        private const val MARGIN_INDICATOR = 15
        private const val BACKGROUND_COLOR = -872415232
        private const val CIRCLE_INNER_INDICATOR_COLOR = -1720271361
        private const val CIRCLE_INDICATOR_COLOR = -2139701761
        private const val LINE_INDICATOR_COLOR = -9391377

    }

    init {
        setWillNotDraw(false)
        setLayerType(LAYER_TYPE_HARDWARE, null)
        target = view
        density = context.resources.displayMetrics.density
        init()
        targetRect = if (view is Targetable) {
            (view as Targetable).boundingRect()
        } else {
            val locationTarget = IntArray(2)
            target!!.getLocationOnScreen(locationTarget)
            RectF(locationTarget[0].toFloat(),
                    locationTarget[1].toFloat(),
                    (locationTarget[0] + target.width).toFloat(),
                    (locationTarget[1] + target.height).toFloat())
        }
        mMessageView = GuideMessageView(getContext(), tourStep, iOnTourButton, isNeeded)
        mMessageView.setPadding(messageViewPadding, messageViewPadding, messageViewPadding, messageViewPadding)
        mMessageView.getSkipButton().setOnClickListener {
            /*val shareTour = SharedPref(context, SHARED_PREF_TOUR_GUIDE)
            if (tourStep in 1..6 || tourStep == 10 || tourStep == 9) {
                shareTour.setBoolean(DAY1, false)
            }
            if (tourStep == 7) {
                shareTour.setBoolean(DAY2, false)
            }
            if (tourStep == 11) {
                shareTour.setBoolean(DAY3, false)
            }*/
            dismiss()
            skipPresse!!.skipPressed(tourStep)
        }
        if (tourStep == 10 || tourStep == 7 || tourStep == 11)
            mMessageView.getSkipButton().visibility = GONE
        mMessageView.setColor(Color.TRANSPARENT)
        addView(mMessageView, LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT))
        setMessageLocation(resolveMessageViewLocation())
        val layoutListener: OnGlobalLayoutListener1 = object : OnGlobalLayoutListener1 {
            override fun onGlobalLayout() {
                viewTreeObserver.removeOnGlobalLayoutListener(this)
                setMessageLocation(resolveMessageViewLocation())
                targetRect = if (target is Targetable) {
                    (target as Targetable).boundingRect()
                } else {
                    val locationTarget = IntArray(2)
                    target!!.getLocationOnScreen(locationTarget)
                    RectF(locationTarget[0].toFloat(),
                            locationTarget[1].toFloat(),
                            (locationTarget[0] + target.width).toFloat(),
                            (locationTarget[1] + target.height).toFloat())
                }
                selfRect[paddingLeft, paddingTop, width - paddingRight] = height - paddingBottom
                marginGuide = (if (isTop) marginGuide else -marginGuide)
                startYLineAndCircle = (if (isTop) targetRect!!.bottom else targetRect!!.top) + marginGuide
                stopY = yMessageView + indicatorHeight
                /*startAnimationSize()*/
                viewTreeObserver.addOnGlobalLayoutListener(this)
            }
        }
        viewTreeObserver.addOnGlobalLayoutListener(layoutListener)
    }


}