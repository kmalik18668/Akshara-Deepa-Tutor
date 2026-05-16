package com.aksharadeeptutor.ui.dailygoal

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import com.aksharadeeptutor.MainActivity
import com.aksharadeeptutor.R
import com.aksharadeeptutor.databinding.FragmentDailyGoalBinding
import com.aksharadeeptutor.viewmodel.TutorViewModel
import com.aksharadeeptutor.viewmodel.TutorViewModelFactory
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch

class DailyGoalFragment : Fragment() {

    private var _binding: FragmentDailyGoalBinding? = null
    private val binding get() = _binding!!

    private val viewModel: TutorViewModel by viewModels {
        TutorViewModelFactory((requireActivity() as MainActivity).database)
    }

    private var streakDays = 0

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentDailyGoalBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        loadDailyGoal()
        setupReminderButton()
    }

    private fun loadDailyGoal() {
        viewLifecycleOwner.lifecycleScope.launch {
            viewModel.subjects.collectLatest { subjects ->
                var totalCompleted = 0
                var totalChapters = 0

                for (subject in subjects) {
                    val completed = viewModel.getCompletedChaptersCount(subject.id)
                    val total = viewModel.getTotalChaptersCount(subject.id)

                    completed.collectLatest { c ->
                        totalCompleted = c
                        val progressPercentage = if (totalChapters > 0) (totalCompleted * 100) / totalChapters else 0
                        binding.progressBarOverall.progress = progressPercentage
                        binding.textViewProgressText.text = "$totalCompleted/$totalChapters Chapters Completed"
                    }

                    total.collectLatest { t ->
                        totalChapters = t
                        val progressPercentage = if (totalChapters > 0) (totalCompleted * 100) / totalChapters else 0
                        binding.progressBarOverall.progress = progressPercentage
                        binding.textViewProgressText.text = "$totalCompleted/$totalChapters Chapters Completed"
                    }
                }

                val goalCompleted = totalCompleted > 0
                binding.textViewGoalStatus.text = if (goalCompleted) {
                    "Today's Goal: Completed!"
                } else {
                    "Today's Goal: Complete at least one chapter"
                }

                updateStreak()
            }
        }
    }

    private fun updateStreak() {
        streakDays = (streakDays + 1).coerceAtMost(30)
        binding.textViewStreak.text = "$streakDays ${getString(R.string.days)}"
    }

    private fun setupReminderButton() {
        binding.buttonSetReminder.setOnClickListener {
            binding.textViewReminderStatus.text = "Reminder set for 6:00 PM daily"
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}
