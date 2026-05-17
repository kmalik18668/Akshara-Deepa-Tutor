package com.aksharadeeptutor.ui.dailygoal

import android.os.Build
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.activity.result.contract.ActivityResultContracts
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.work.ExistingPeriodicWorkPolicy
import androidx.work.PeriodicWorkRequestBuilder
import androidx.work.WorkManager
import com.aksharadeeptutor.MainActivity
import com.aksharadeeptutor.R
import com.aksharadeeptutor.databinding.FragmentDailyGoalBinding
import com.aksharadeeptutor.receiver.DailyReminderWorker
import com.aksharadeeptutor.viewmodel.TutorViewModel
import com.aksharadeeptutor.viewmodel.TutorViewModelFactory
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale
import java.util.concurrent.TimeUnit

class DailyGoalFragment : Fragment() {

    private var _binding: FragmentDailyGoalBinding? = null
    private val binding get() = _binding!!

    private val viewModel: TutorViewModel by viewModels {
        TutorViewModelFactory((requireActivity() as MainActivity).database)
    }

    private val notificationPermissionLauncher = registerForActivityResult(
        ActivityResultContracts.RequestPermission()
    ) { isGranted ->
        if (isGranted) {
            scheduleReminder()
        } else {
            Toast.makeText(requireContext(), "Notification permission denied", Toast.LENGTH_SHORT).show()
        }
    }

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
        loadStreak()
        setupReminderButton()
    }

    private fun loadDailyGoal() {
        viewLifecycleOwner.lifecycleScope.launch {
            // Use the combined totalProgressFlow from ViewModel — no nested collectors
            viewModel.totalProgressFlow.collectLatest { (totalCompleted, totalChapters) ->
                val progressPercentage = if (totalChapters > 0) (totalCompleted * 100) / totalChapters else 0
                binding.progressBarOverall.progress = progressPercentage
                binding.textViewProgressText.text = "$totalCompleted/$totalChapters Chapters Completed"

                val goalCompleted = totalCompleted > 0
                binding.textViewGoalStatus.text = if (goalCompleted) {
                    "🎉 Today's Goal: Completed!"
                } else {
                    "Complete at least one chapter today"
                }

                // Update streak based on today's activity
                updateStreak(goalCompleted)
            }
        }
    }

    private fun loadStreak() {
        val prefs = requireContext().getSharedPreferences("akshara_prefs", 0)
        val streak = prefs.getInt("streak_count", 0)
        binding.textViewStreak.text = "$streak ${getString(R.string.days)}"
    }

    private fun updateStreak(hasCompletedToday: Boolean) {
        val prefs = requireContext().getSharedPreferences("akshara_prefs", 0)
        val today = SimpleDateFormat("yyyy-MM-dd", Locale.getDefault()).format(Date())
        val lastActiveDate = prefs.getString("last_active_date", "")
        val currentStreak = prefs.getInt("streak_count", 0)

        if (hasCompletedToday) {
            val newStreak = when (lastActiveDate) {
                today -> currentStreak // Already counted today
                getYesterdayDate() -> currentStreak + 1 // Consecutive day
                "" -> 1 // First time
                else -> 1 // Streak broken, restart
            }
            prefs.edit()
                .putString("last_active_date", today)
                .putInt("streak_count", newStreak)
                .apply()
            binding.textViewStreak.text = "$newStreak ${getString(R.string.days)}"
        } else {
            // Check if streak was broken (last active day wasn't yesterday or today)
            if (lastActiveDate != today && lastActiveDate != getYesterdayDate() && lastActiveDate != "") {
                prefs.edit().putInt("streak_count", 0).apply()
                binding.textViewStreak.text = "0 ${getString(R.string.days)}"
            }
        }
    }

    private fun getYesterdayDate(): String {
        val cal = java.util.Calendar.getInstance()
        cal.add(java.util.Calendar.DAY_OF_YEAR, -1)
        return SimpleDateFormat("yyyy-MM-dd", Locale.getDefault()).format(cal.time)
    }

    private fun setupReminderButton() {
        val prefs = requireContext().getSharedPreferences("akshara_prefs", 0)
        val isSet = prefs.getBoolean("reminder_set", false)
        if (isSet) {
            binding.textViewReminderStatus.text = getString(R.string.reminder_set)
        }

        binding.buttonSetReminder.setOnClickListener {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
                notificationPermissionLauncher.launch(android.Manifest.permission.POST_NOTIFICATIONS)
            } else {
                scheduleReminder()
            }
        }
    }

    private fun scheduleReminder() {
        val workRequest = PeriodicWorkRequestBuilder<DailyReminderWorker>(24, TimeUnit.HOURS)
            .build()
        WorkManager.getInstance(requireContext()).enqueueUniquePeriodicWork(
            DailyReminderWorker.WORK_NAME,
            ExistingPeriodicWorkPolicy.KEEP,
            workRequest
        )
        val prefs = requireContext().getSharedPreferences("akshara_prefs", 0)
        prefs.edit().putBoolean("reminder_set", true).apply()
        binding.textViewReminderStatus.text = getString(R.string.reminder_set)
        Toast.makeText(requireContext(), "Daily reminder set for 6:00 PM!", Toast.LENGTH_SHORT).show()
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}
