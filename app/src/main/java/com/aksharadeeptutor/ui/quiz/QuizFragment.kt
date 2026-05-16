package com.aksharadeeptutor.ui.quiz

import android.graphics.Color
import android.os.Bundle
import android.os.CountDownTimer
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import com.aksharadeeptutor.MainActivity
import com.aksharadeeptutor.R
import com.aksharadeeptutor.data.model.Question
import com.aksharadeeptutor.databinding.FragmentQuizBinding
import com.aksharadeeptutor.viewmodel.TutorViewModel
import com.aksharadeeptutor.viewmodel.TutorViewModelFactory
import kotlinx.coroutines.launch

class QuizFragment : Fragment() {

    private var _binding: FragmentQuizBinding? = null
    private val binding get() = _binding!!

    private val viewModel: TutorViewModel by viewModels {
        TutorViewModelFactory((requireActivity() as MainActivity).database)
    }

    private val args: QuizFragmentArgs by navArgs()

    private var questions: List<Question> = emptyList()
    private var currentQuestionIndex = 0
    private var score = 0
    private var selectedAnswers = mutableMapOf<Int, String>()
    private var quizTimer: CountDownTimer? = null
    private var timeRemaining: Long = 300000

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentQuizBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.textViewChapterName.text = args.chapterName
        loadQuestions()
        setupClickListeners()
    }

    private fun loadQuestions() {
        viewLifecycleOwner.lifecycleScope.launch {
            questions = viewModel.getQuizQuestions(args.chapterId)
            if (questions.isEmpty()) {
                Toast.makeText(requireContext(), "No questions available for this chapter", Toast.LENGTH_SHORT).show()
                findNavController().popBackStack()
                return@launch
            }
            currentQuestionIndex = 0
            score = 0
            selectedAnswers.clear()
            displayQuestion()
            startTimer()
        }
    }

    private fun displayQuestion() {
        val question = questions[currentQuestionIndex]
        binding.textViewQuestionNumber.text = "Question ${currentQuestionIndex + 1} of ${questions.size}"
        binding.textViewQuestionText.text = question.questionText
        binding.buttonOptionA.text = question.optionA
        binding.buttonOptionB.text = question.optionB
        binding.buttonOptionC.text = question.optionC
        binding.buttonOptionD.text = question.optionD

        clearSelections()
        selectedAnswers[currentQuestionIndex]?.let { selectedOption ->
            when (selectedOption) {
                question.optionA -> selectButton(binding.buttonOptionA, true)
                question.optionB -> selectButton(binding.buttonOptionB, true)
                question.optionC -> selectButton(binding.buttonOptionC, true)
                question.optionD -> selectButton(binding.buttonOptionD, true)
            }
        }

        binding.buttonPrevious.visibility = if (currentQuestionIndex > 0) View.VISIBLE else View.GONE
        binding.buttonNext.text = if (currentQuestionIndex == questions.size - 1) "Submit" else "Next"
    }

    private fun clearSelections() {
        resetButton(binding.buttonOptionA)
        resetButton(binding.buttonOptionB)
        resetButton(binding.buttonOptionC)
        resetButton(binding.buttonOptionD)
    }

    private fun selectButton(button: com.google.android.material.button.MaterialButton, selected: Boolean) {
        if (selected) {
            button.backgroundTintList = ContextCompat.getColorStateList(requireContext(), R.color.primaryContainer)
            button.strokeColor = ContextCompat.getColorStateList(requireContext(), R.color.primary)
            button.setTextColor(ContextCompat.getColor(requireContext(), R.color.onPrimaryContainer))
        }
    }

    private fun resetButton(button: com.google.android.material.button.MaterialButton) {
        button.backgroundTintList = ContextCompat.getColorStateList(requireContext(), android.R.color.transparent)
        button.strokeColor = ContextCompat.getColorStateList(requireContext(), R.color.outline)
        button.setTextColor(ContextCompat.getColor(requireContext(), R.color.onSurface))
    }

    private fun setupClickListeners() {
        binding.buttonOptionA.setOnClickListener { selectOption(questions[currentQuestionIndex].optionA) }
        binding.buttonOptionB.setOnClickListener { selectOption(questions[currentQuestionIndex].optionB) }
        binding.buttonOptionC.setOnClickListener { selectOption(questions[currentQuestionIndex].optionC) }
        binding.buttonOptionD.setOnClickListener { selectOption(questions[currentQuestionIndex].optionD) }

        binding.buttonNext.setOnClickListener {
            if (currentQuestionIndex < questions.size - 1) {
                currentQuestionIndex++
                displayQuestion()
            } else {
                submitQuiz()
            }
        }

        binding.buttonPrevious.setOnClickListener {
            if (currentQuestionIndex > 0) {
                currentQuestionIndex--
                displayQuestion()
            }
        }
    }

    private fun selectOption(option: String) {
        selectedAnswers[currentQuestionIndex] = option
        clearSelections()
        val question = questions[currentQuestionIndex]
        when (option) {
            question.optionA -> selectButton(binding.buttonOptionA, true)
            question.optionB -> selectButton(binding.buttonOptionB, true)
            question.optionC -> selectButton(binding.buttonOptionC, true)
            question.optionD -> selectButton(binding.buttonOptionD, true)
        }
    }

    private fun startTimer() {
        quizTimer?.cancel()
        quizTimer = object : CountDownTimer(timeRemaining, 1000) {
            override fun onTick(millisUntilFinished: Long) {
                timeRemaining = millisUntilFinished
                val minutes = millisUntilFinished / 60000
                val seconds = (millisUntilFinished % 60000) / 1000
                binding.textViewTimer.text = String.format("%02d:%02d", minutes, seconds)
            }

            override fun onFinish() {
                submitQuiz()
            }
        }.start()
    }

    private fun submitQuiz() {
        quizTimer?.cancel()
        score = 0
        questions.forEachIndexed { index, question ->
            if (selectedAnswers[index] == question.correctAnswer) {
                score++
            }
        }

        val answersJson = selectedAnswers.map { (key, value) -> "$key:$value" }.joinToString(",")

        viewLifecycleOwner.lifecycleScope.launch {
            viewModel.submitQuiz(args.chapterId, score, questions.size, answersJson)

            val percentage = (score * 100) / questions.size
            val message = if (percentage >= 60) {
                getString(R.string.chapter_completed)
            } else {
                getString(R.string.needs_practice)
            }

            Toast.makeText(requireContext(), "$message\nScore: $score/${questions.size}", Toast.LENGTH_LONG).show()
            findNavController().popBackStack()
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        quizTimer?.cancel()
        _binding = null
    }
}
