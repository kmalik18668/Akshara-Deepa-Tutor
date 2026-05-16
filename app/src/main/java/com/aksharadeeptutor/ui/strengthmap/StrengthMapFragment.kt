package com.aksharadeeptutor.ui.strengthmap

import android.graphics.Color
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import com.aksharadeeptutor.MainActivity
import com.aksharadeeptutor.databinding.FragmentStrengthMapBinding
import com.aksharadeeptutor.viewmodel.TutorViewModel
import com.aksharadeeptutor.viewmodel.TutorViewModelFactory
import com.github.mikephil.charting.components.Legend
import com.github.mikephil.charting.data.RadarData
import com.github.mikephil.charting.data.RadarDataSet
import com.github.mikephil.charting.data.RadarEntry
import com.github.mikephil.charting.formatter.ValueFormatter
import kotlinx.coroutines.launch
import java.text.DecimalFormat

class StrengthMapFragment : Fragment() {

    private var _binding: FragmentStrengthMapBinding? = null
    private val binding get() = _binding!!

    private val viewModel: TutorViewModel by viewModels {
        TutorViewModelFactory((requireActivity() as MainActivity).database)
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentStrengthMapBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setupRadarChart()
        loadStrengthData()
    }

    private fun setupRadarChart() {
        binding.radarChart.apply {
            description.isEnabled = false
            setTouchEnabled(true)
            webAlpha = 150

            val legend = legend
            legend.verticalAlignment = Legend.LegendVerticalAlignment.TOP
            legend.horizontalAlignment = Legend.LegendHorizontalAlignment.CENTER
            legend.orientation = Legend.LegendOrientation.HORIZONTAL
            legend.setDrawInside(false)
            legend.xEntrySpace = 7f
            legend.yEntrySpace = 5f

            xAxis.apply {
                textSize = 14f
            }

            yAxis.apply {
                labelCount = 5
                axisMinimum = 0f
                axisMaximum = 100f
                setDrawLabels(false)
            }
        }
    }

    private fun loadStrengthData() {
        viewLifecycleOwner.lifecycleScope.launch {
            val scienceMastery = viewModel.getSubjectMastery(1)
            val mathMastery = viewModel.getSubjectMastery(2)
            val socialMastery = viewModel.getSubjectMastery(3)

            updateChart(scienceMastery, mathMastery, socialMastery)
        }
    }

    private fun updateChart(science: Double, math: Double, social: Double) {
        val entries = listOf(
            RadarEntry(science.toFloat() * 100),
            RadarEntry(math.toFloat() * 100),
            RadarEntry(social.toFloat() * 100)
        )

        val dataSet = RadarDataSet(entries, "Mastery %").apply {
            color = Color.parseColor("#1976D2")
            fillColor = Color.parseColor("#1976D2")
            fillAlpha = 100
            lineWidth = 2f
            valueTextSize = 12f
            valueTextColor = Color.parseColor("#212121")
            valueFormatter = object : ValueFormatter() {
                private val format = DecimalFormat("#")
                override fun getFormattedValue(value: Float): String {
                    return "${format.format(value)}%"
                }
            }
        }

        binding.radarChart.data = RadarData(dataSet)
        binding.radarChart.setData(binding.radarChart.data)
        binding.radarChart.invalidate()

        binding.radarChart.xAxis.apply {
            valueFormatter = object : ValueFormatter() {
                private val labels = arrayOf("Science", "Mathematics", "Social Studies")
                override fun getFormattedValue(value: Float): String {
                    return labels[value.toInt() % labels.size]
                }
            }
        }
    }

    override fun onResume() {
        super.onResume()
        loadStrengthData()
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}
