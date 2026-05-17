package com.aksharadeeptutor.ui.strengthmap

import android.graphics.Color
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import com.aksharadeeptutor.MainActivity
import com.aksharadeeptutor.R
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
            webColor = Color.parseColor("#E5E7EB")
            webColorInner = Color.parseColor("#F3F4F6")
            webLineWidthInner = 1f
            webLineWidth = 1f

            val legend = legend
            legend.verticalAlignment = Legend.LegendVerticalAlignment.BOTTOM
            legend.horizontalAlignment = Legend.LegendHorizontalAlignment.CENTER
            legend.orientation = Legend.LegendOrientation.HORIZONTAL
            legend.setDrawInside(false)
            legend.xEntrySpace = 7f
            legend.yEntrySpace = 5f
            legend.textSize = 12f

            xAxis.apply {
                textSize = 13f
                textColor = Color.parseColor("#374151")
                valueFormatter = object : ValueFormatter() {
                    private val labels = arrayOf("Science", "Mathematics", "Social")
                    override fun getFormattedValue(value: Float): String {
                        return labels[value.toInt() % labels.size]
                    }
                }
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
            // getSubjectMastery returns 0.0–1.0 (avg score ratio), multiply by 100 for percentage
            val scienceMastery = (viewModel.getSubjectMastery(1) * 100).toInt()
            val mathMastery = (viewModel.getSubjectMastery(2) * 100).toInt()
            val socialMastery = (viewModel.getSubjectMastery(3) * 100).toInt()

            updateChart(scienceMastery, mathMastery, socialMastery)
            updateMasteryLabels(scienceMastery, mathMastery, socialMastery)
            updateGapAreas(scienceMastery, mathMastery, socialMastery)
        }
    }

    private fun updateChart(science: Int, math: Int, social: Int) {
        val entries = listOf(
            RadarEntry(science.toFloat()),
            RadarEntry(math.toFloat()),
            RadarEntry(social.toFloat())
        )

        // Color based on average mastery
        val avgMastery = (science + math + social) / 3
        val chartColor = when {
            avgMastery >= 80 -> Color.parseColor("#059669") // Strong — green
            avgMastery >= 60 -> Color.parseColor("#2563EB") // Moderate — blue
            else             -> Color.parseColor("#DC2626") // Weak — red
        }

        val dataSet = RadarDataSet(entries, "Mastery %").apply {
            color = chartColor
            fillColor = chartColor
            setDrawFilled(true)
            fillAlpha = 80
            lineWidth = 2.5f
            valueTextSize = 11f
            valueTextColor = Color.parseColor("#111827")
            isDrawHighlightCircleEnabled = true
            valueFormatter = object : ValueFormatter() {
                private val format = DecimalFormat("#")
                override fun getFormattedValue(value: Float): String {
                    return "${format.format(value)}%"
                }
            }
        }

        binding.radarChart.data = RadarData(dataSet)
        binding.radarChart.animateXY(600, 600)
        binding.radarChart.invalidate()
    }

    private fun updateMasteryLabels(science: Int, math: Int, social: Int) {
        binding.textViewScienceMastery.text = "$science%"
        binding.textViewMathMastery.text = "$math%"
        binding.textViewSocialMastery.text = "$social%"

        // Color code the labels
        binding.textViewScienceMastery.setTextColor(getMasteryColor(science))
        binding.textViewMathMastery.setTextColor(getMasteryColor(math))
        binding.textViewSocialMastery.setTextColor(getMasteryColor(social))
    }

    private fun updateGapAreas(science: Int, math: Int, social: Int) {
        val gaps = mutableListOf<String>()
        if (science < 60) gaps.add("Science ($science%)")
        if (math < 60) gaps.add("Mathematics ($math%)")
        if (social < 60) gaps.add("Social Studies ($social%)")

        if (gaps.isEmpty()) {
            binding.cardGapAreas.visibility = View.GONE
            binding.cardAllStrong.visibility = View.VISIBLE
        } else {
            binding.cardGapAreas.visibility = View.VISIBLE
            binding.cardAllStrong.visibility = View.GONE
            binding.textViewGapList.text = gaps.joinToString("\n") { "⚠️  $it needs more practice" }
        }
    }

    private fun getMasteryColor(pct: Int): Int {
        return when {
            pct >= 80 -> ContextCompat.getColor(requireContext(), R.color.tertiary)
            pct >= 60 -> ContextCompat.getColor(requireContext(), R.color.primary)
            else      -> ContextCompat.getColor(requireContext(), R.color.error)
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
