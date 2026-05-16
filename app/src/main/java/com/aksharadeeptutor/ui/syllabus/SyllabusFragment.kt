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
        observeSubjects()
    }

    private fun setupRecyclerView() {
        subjectAdapter = SubjectAdapter { subject ->
            viewModel.selectSubject(subject.id)
            showChaptersForSubject(subject.id)
        }
        binding.recyclerViewSubjects.apply {
            layoutManager = LinearLayoutManager(requireContext())
            adapter = subjectAdapter
        }
    }

    private fun observeSubjects() {
        viewLifecycleOwner.lifecycleScope.launch {
            viewModel.subjects.collectLatest { subjects ->
                subjectAdapter.submitList(subjects)
            }
        }
    }

    private fun showChaptersForSubject(subjectId: Int) {
        val action = SyllabusFragmentDirections.actionSyllabusToQuiz(subjectId, 0, "")
        findNavController().navigate(action)
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}
