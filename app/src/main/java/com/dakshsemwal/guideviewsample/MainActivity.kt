package com.dakshsemwal.guideviewsample

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.dakshsemwal.guideviewsample.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {
    private lateinit var summaryAdapter: SummaryAdapter
    private lateinit var binding: ActivityMainBinding
    lateinit var list: MutableList<SenseSleep>
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        list = mutableListOf()
        list.add(SenseSleep())
        list.add(SenseSleep())
        list.add(SenseSleep())
        list.add(SenseSleep())
        list.add(SenseSleep())
        list.add(SenseSleep())
        list.add(SenseSleep())
        list.add(SenseSleep())
        list.add(SenseSleep())
        list.add(SenseSleep())
        list.add(SenseSleep())
        list.add(SenseSleep())
        list.add(SenseSleep())
        list.add(SenseSleep())
        list.add(SenseSleep())
        list.add(SenseSleep())
        list.add(SenseSleep())
        list.add(SenseSleep())
        list.add(SenseSleep())
        list.add(SenseSleep())
        list.add(SenseSleep())
        list.add(SenseSleep())
        list.add(SenseSleep())
        summaryAdapter = SummaryAdapter(this, list,false)
        binding.viewPager.adapter = summaryAdapter
    }
}