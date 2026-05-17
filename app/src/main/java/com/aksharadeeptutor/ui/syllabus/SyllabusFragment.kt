package com.aksharadeeptutor.ui.syllabus

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.aksharadeeptutor.MainActivity
import com.aksharadeeptutor.R
import com.aksharadeeptutor.data.model.Chapter
import com.aksharadeeptutor.data.model.ChapterStatus
import com.aksharadeeptutor.databinding.FragmentSyllabusBinding
import com.aksharadeeptutor.viewmodel.TutorViewModel
import com.aksharadeeptutor.viewmodel.TutorViewModelFactory
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch

class SyllabusFragment : Fragment() {

    private var _binding: FragmentSyllabusBinding? = null
    private val binding get() = _binding!!

    private val viewModel: TutorViewModel by viewModels {
        TutorViewModelFactory((requireActivity() as MainActivity).database)
    }

    private lateinit var subjectAdapter: SubjectAdapter

    // Track which subjects are expanded
    private val expandedSubjects = mutableSetOf<Int>()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentSyllabusBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setupRecyclerView()
        observeSyllabusState()
        observeOverallProgress()
    }

    private fun setupRecyclerView() {
        subjectAdapter = SubjectAdapter(
            onSubjectClick = { subject ->
                toggleSubjectExpansion(subject.id)
            },
            onChapterQuiz = { chapter ->
                val bundle = android.os.Bundle().apply {
                    putInt("chapterId", chapter.id)
                    putString("chapterName", chapter.name)
                }
                findNavController().navigate(R.id.action_syllabus_to_quiz, bundle)
            },
            onMarkComplete = { chapter ->
                handleMarkComplete(chapter)
            }
        )
        binding.recyclerViewSubjects.apply {
            layoutManager = LinearLayoutManager(requireContext())
            adapter = subjectAdapter
        }
    }

    private fun handleMarkComplete(chapter: Chapter) {
        when (chapter.status) {
            ChapterStatus.COMPLETED -> {
                // Toggle off: mark as not started
                viewModel.markChapterNotStarted(chapter.id)
            }
            else -> {
                // Mark as completed
                viewModel.markChapterComplete(chapter.id)
            }
        }
    }

    private fun toggleSubjectExpansion(subjectId: Int) {
        if (expandedSubjects.contains(subjectId)) {
            expandedSubjects.remove(subjectId)
        } else {
            expandedSubjects.add(subjectId)
        }
        // Trigger the adapter to re-render with updated expansion state
        rebuildList()
    }

    private var currentUiModels: List<SubjectUiModel> = emptyList()

    private fun observeSyllabusState() {
        viewLifecycleOwner.lifecycleScope.launch {
            // Single combined flow — no nested collectors
            viewModel.syllabusUiState.collectLatest { models ->
                // Apply expansion state
                currentUiModels = models
                rebuildList()
            }
        }
    }

    private fun rebuildList() {
        val withExpansion = currentUiModels.map { model ->
            model.copy(isExpanded = expandedSubjects.contains(model.subject.id))
        }
        subjectAdapter.submitList(withExpansion)
    }

    private fun observeOverallProgress() {
        viewLifecycleOwner.lifecycleScope.launch {
            viewModel.totalProgressFlow.collectLatest { (completed, total) ->
                val pct = if (total > 0) (completed * 100) / total else 0
                binding.progressBarOverall.progress = pct
                binding.textViewOverallProgress.text = "$completed of $total chapters completed ($pct%)"
            }
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}
