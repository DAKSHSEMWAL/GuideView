package com.dakshsemwal.guideviewsample

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.RelativeLayout
import android.widget.Toast
import androidx.recyclerview.widget.RecyclerView
import com.dakshsemwal.guideviewsample.databinding.SummaryRvBinding
import com.dakshsemwal.guideviewsample.tutoriallib.GuideView
import com.dakshsemwal.guideviewsample.tutoriallib.config.TargetType
import com.dakshsemwal.guideviewsample.tutoriallib.listener.IOnTourButtonClicked
import com.dakshsemwal.guideviewsample.tutoriallib.listener.SkipPressed

class SummaryAdapter(
    val mContext: Context,
    val sleeps: MutableList<SenseSleep>,
    val from: Boolean
) : RecyclerView.Adapter<SummaryAdapter.SummaryHolder>() {

    var mSleeps = sleeps
    var lastSleepShownAt = sleeps.count() + 1
    var guideView: GuideView? = null
    var summaryRvBinding: SummaryRvBinding? = null
    var viewcontext: Context? = null


    inner class SummaryHolder(val binding: SummaryRvBinding) : RecyclerView.ViewHolder(binding.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): SummaryHolder {
        viewcontext = parent.context
        return SummaryHolder(
            SummaryRvBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            )
        )
    }

    override fun onBindViewHolder(holder: SummaryHolder, position: Int) {
        with(holder) {
            setGuideView(binding.rlHeartReport)
        }
    }

    private fun setGuideView(rlHeartReport: RelativeLayout) {

        startTour(rlHeartReport)
    }

    private fun startTour(rlHeartReport: RelativeLayout) {
        val subtitle1 =
            "On the sleep summary screen, click on\nany of the icons to view\ntrends across the entire sleep"
        guideView = GuideView.Builder(viewcontext!!)
            .setMessageView(9, iOnTourButton, rlHeartReport, "", subtitle1, "", false, skipPressed)
            .setCircleIndicatorSize(14f)
            .setCircleInnerIndicatorSize(09f)
            .setCircleStrokeIndicatorSize(3.29f)
            .setTargetViewType(TargetType.CIRCLE)
            .setContentTextSize(16) //optional
            .setTitleTextSize(18) //optional
            .build()
        guideView!!.show()
    }

    private var skipPressed = object : SkipPressed {
        override fun skipPressed(tourStep: Int) {

        }
    }

    private var iOnTourButton = object : IOnTourButtonClicked {
        override fun onTourButtonClicked(step: Int) {
            if (step == 9) {
                if (guideView!!.isShowing) guideView!!.dismiss()
                Toast.makeText(mContext, "Clicked", Toast.LENGTH_LONG).show()
            }
        }
    }

    override fun getItemCount(): Int {
        return mSleeps.count()
    }

    fun updateSleeps(sleeps: MutableList<SenseSleep>) {
        mSleeps = sleeps
        notifyDataSetChanged()
    }

    fun getCurrentSleep(position: Int): SenseSleep {
        return mSleeps[position]
    }

    fun getLastSleepShowAt(): Int {
        return lastSleepShownAt
    }

    fun setLastPageShownAt(lastSleepShownAt: Int) {
        this.lastSleepShownAt = lastSleepShownAt + 1
    }

}


